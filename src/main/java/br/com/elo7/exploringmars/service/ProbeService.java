package br.com.elo7.exploringmars.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.elo7.exploringmars.bean.CommandsReq;
import br.com.elo7.exploringmars.bean.CommandsResp;
import br.com.elo7.exploringmars.bean.ProbeIdentifierResp;
import br.com.elo7.exploringmars.bean.ProbeReq;
import br.com.elo7.exploringmars.bean.ProbeResp;
import br.com.elo7.exploringmars.concurrency.MapCache;
import br.com.elo7.exploringmars.db.ProbeRepository;
import br.com.elo7.exploringmars.db.entity.MapEntity;
import br.com.elo7.exploringmars.db.entity.ProbeEntity;
import br.com.elo7.exploringmars.exception.ProbeOutOfBoundsException;
import br.com.elo7.exploringmars.exception.UnauthorizedException;
import br.com.elo7.exploringmars.helper.ProbeHelper;
import br.com.elo7.exploringmars.utils.Command;
import br.com.elo7.exploringmars.exception.NotFoundException;
import br.com.elo7.exploringmars.exception.ProbeCollisionException;

@Service
public class ProbeService {

    @Autowired
    private ProbeRepository probeRepository;

    @Autowired
    private MapCache mapCache;
    
    public List<ProbeResp> getAllProbes() {
        List<ProbeResp> probes = new ArrayList<ProbeResp>();
        probeRepository.findAll().forEach(probeRecord -> {
            probes.add(ProbeHelper.fromEntityToResp(probeRecord));
        });
        return probes;
    }
    
    public ProbeResp getProbe(long id) throws NotFoundException {
        Optional<ProbeEntity> optProbe = probeRepository.findById(id);
        if (!optProbe.isPresent()) {
            throw new NotFoundException("Probe not found");
        }
        return ProbeHelper.fromEntityToResp(optProbe.get());
    }

    public ProbeIdentifierResp addProbe(ProbeReq req) {
        ProbeEntity probe = ProbeHelper.fromReqToEntity(req);
        MapEntity cachedMap = mapCache.getMapReference(probe.getMapId());
        try {
            synchronized (cachedMap) {
                checkPosition(cachedMap, probe);
                probe.setResourceId(UUID.randomUUID());
                probe = saveEntity(probe);
                cachedMap.getProbeSet().add(probe);
            }
        } finally { 
            mapCache.releaseMapReference(cachedMap.getId());
        }
        return ProbeHelper.fromEntityToIdentifierResp(probe);
    }

    private void checkPosition(MapEntity map, ProbeEntity probe) 
            throws ProbeOutOfBoundsException, ProbeCollisionException {
        if (map.isOutOfBounds(probe.getX(), probe.getY())) {
            throw new ProbeOutOfBoundsException(probe.getX(), probe.getY());
        }
        if (map.getProbeSet().contains(probe)) {
            throw new ProbeCollisionException(probe.getX(), probe.getY());
        }
    }

    private ProbeEntity saveEntity(ProbeEntity probe) throws ProbeCollisionException {
        try {
            return probeRepository.save(probe);
        } catch (DataIntegrityViolationException e) {
            throw new ProbeCollisionException(probe.getX(), probe.getY());
        } 
    }

    public void deleteProbe(long id, UUID resourceId) {
        ProbeEntity probe = getProbeAndValidateId(id, resourceId);
        MapEntity cachedMap = mapCache.getMapReference(probe.getMapId());
        try {
            synchronized (cachedMap) {
                try {
                    probeRepository.deleteById(probe.getId());
                } catch (EmptyResultDataAccessException e) {
                    throw new NotFoundException("Probe not found");
                }
                cachedMap.getProbeSet().remove(probe);
            }
        } finally {
            mapCache.releaseMapReference(cachedMap.getId());
        }
    }
  
    private ProbeEntity getProbeAndValidateId(long id, UUID resourceId) 
            throws NotFoundException, UnauthorizedException {
        Optional<ProbeEntity> optProbe = probeRepository.findById(id);
        if (!optProbe.isPresent()) {
            throw new NotFoundException("Probe not found");
        }
        ProbeEntity probe = optProbe.get();
        if (!probe.checkResourceId(resourceId)) {
            throw new UnauthorizedException("Invalid resource id");
        }
        return probe;
    }

    public CommandsResp updateProbe(long id, CommandsReq commandsReq) {
        List<Command> commands = parseCommandsString(commandsReq.getCommands());
        ProbeEntity probe = getProbeAndValidateId(id, commandsReq.getResourceId());
        MapEntity cachedMap = mapCache.getMapReference(probe.getMapId());
        try {
            CommandsResp resp = executeCommands(cachedMap, probe, commands);
            return resp;
        } finally {
            mapCache.releaseMapReference(cachedMap.getId());
        }
    }

    private List<Command> parseCommandsString(String commandsStr) {
        List<Command> commands = new ArrayList<Command>();
        commandsStr.chars().forEach(symbolInt -> {
            char symbol = (char) symbolInt;
            commands.add(Command.getCommandBySymbol(symbol));
        });
        return commands;
    }

    private CommandsResp executeCommands(MapEntity cachedMap, ProbeEntity probe, 
            List<Command> commands) {
        
        // Update probe in memory
        for (Command command : commands) {
            switch (command) {
                case TURN_RIGHT:
                    probe.turnRight();
                    break;
                case TURN_LEFT:
                    probe.turnLeft();
                    break;
                case MOVE:
                    // Only the MOVE commands can violate the map constraints
                    synchronized(cachedMap) {
                        // Remove probe from set
                        cachedMap.getProbeSet().remove(probe);
                        probe.move();
                        try {
                            checkPosition(cachedMap, probe);
                            cachedMap.getProbeSet().add(probe);
                        } catch (ProbeOutOfBoundsException | ProbeCollisionException e) {
                            // Move back, save and return state
                            probe.moveBack();
                            cachedMap.getProbeSet().add(probe);
                            probe = saveEntity(probe);
                            return ProbeHelper.fromEntityToCommandsResp(probe, false, 
                                    e.getMessage());
                        }
                    }
                    break;
            }
        }

        // Save to DB and return
        probe = saveEntity(probe);
        return ProbeHelper.fromEntityToCommandsResp(probe, true, null);
    }

}
