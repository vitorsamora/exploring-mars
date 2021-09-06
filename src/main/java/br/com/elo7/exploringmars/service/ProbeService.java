package br.com.elo7.exploringmars.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.elo7.exploringmars.bean.ProbeIdentifierResp;
import br.com.elo7.exploringmars.bean.ProbeReq;
import br.com.elo7.exploringmars.bean.ProbeResp;
import br.com.elo7.exploringmars.db.MapRepository;
import br.com.elo7.exploringmars.db.ProbeRepository;
import br.com.elo7.exploringmars.db.entity.MapEntity;
import br.com.elo7.exploringmars.db.entity.ProbeEntity;
import br.com.elo7.exploringmars.exception.ProbeOutOfBoundsException;
import br.com.elo7.exploringmars.exception.UnauthorizedException;
import br.com.elo7.exploringmars.helper.ProbeHelper;
import br.com.elo7.exploringmars.exception.NotFoundException;
import br.com.elo7.exploringmars.exception.ProbeCollisionException;

@Service
public class ProbeService {

    @Autowired
    private ProbeRepository probeRepository;

    @Autowired
    private MapRepository mapRepository;

    private MapEntity getMap(long id) {
        Optional<MapEntity> optMap = mapRepository.findById(id);
        if (optMap.isPresent()) {
            return optMap.get();
        }
        throw new NotFoundException("Map not found");
    }
    
    public ProbeResp getProbe(long id) {
        Optional<ProbeEntity> optProbe = probeRepository.findById(id);
        if (!optProbe.isPresent()) {
            throw new NotFoundException("Probe not found");
        }
        return ProbeHelper.fromEntityToResp(optProbe.get());
    }

    private ProbeEntity getProbeAndValidateId(long id, UUID resourceId) {
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

    private void checkPosition(MapEntity map, ProbeEntity probe) {
        if (map.isOutOfBounds(probe.getX(), probe.getY())) {
            throw new ProbeOutOfBoundsException(probe.getX(), probe.getY());
        }
        if (map.getProbeSet().contains(probe)) {
            throw new ProbeCollisionException(probe.getX(), probe.getY());
        }
    }

    private ProbeEntity saveEntity(ProbeEntity probe) {
        try {
            return probeRepository.save(probe);
        } catch (DataIntegrityViolationException e) {
            throw new ProbeCollisionException(probe.getX(), probe.getY());
        } 
    }

    public List<ProbeResp> getAllProbes() {
        List<ProbeResp> probes = new ArrayList<ProbeResp>();
        probeRepository.findAll().forEach(probeRecord -> {
            probes.add(ProbeHelper.fromEntityToResp(probeRecord));
        });
        return probes;
    }

    public ProbeIdentifierResp addProbe(ProbeReq req) {
        ProbeEntity probe = ProbeHelper.fromReqToEntity(req);
        MapEntity map = getMap(probe.getMapId());
        checkPosition(map, probe);
        probe.setResourceId(UUID.randomUUID());
        probe = saveEntity(probe);
        return ProbeHelper.fromEntityToIdentifierResp(probe);
    }

    public void deleteProbe(long id, UUID resourceId) {
        ProbeEntity probe = getProbeAndValidateId(id, resourceId);
        try {
            probeRepository.deleteById(probe.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Probe not found");
        }
    }

}
