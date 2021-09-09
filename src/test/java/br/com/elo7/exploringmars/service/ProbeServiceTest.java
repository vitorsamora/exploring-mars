package br.com.elo7.exploringmars.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.elo7.exploringmars.bean.CommandsReq;
import br.com.elo7.exploringmars.bean.CommandsResp;
import br.com.elo7.exploringmars.bean.MapReq;
import br.com.elo7.exploringmars.bean.MapResp;
import br.com.elo7.exploringmars.bean.ProbeIdentifierResp;
import br.com.elo7.exploringmars.bean.ProbeReq;
import br.com.elo7.exploringmars.bean.ProbeResp;
import br.com.elo7.exploringmars.exception.NotFoundException;
import br.com.elo7.exploringmars.exception.ProbeCollisionException;
import br.com.elo7.exploringmars.exception.ProbeOutOfBoundsException;
import br.com.elo7.exploringmars.exception.UnauthorizedException;
import br.com.elo7.exploringmars.utils.Direction;

@SpringBootTest
public class ProbeServiceTest {
    
    @Autowired
    private ProbeService service;

    @Autowired
    private MapService mapService;

    private static int MAP_SIZE = 100;

    private long initMap() {
        MapReq map = new MapReq();
        map.setMaxX(MAP_SIZE);
        map.setMaxY(MAP_SIZE);
        MapResp resp = mapService.addMap(map);
        assertNotNull(resp);
        assertNotNull(resp.getId());
        return resp.getId();
    }

    private ProbeReq createProbeReq(UUID resourceId, long mapId, int x, int y, Direction direction) {
        if (resourceId == null) {
            resourceId = UUID.randomUUID();
        }
        ProbeReq req = new ProbeReq();
        req.setMapId(mapId);
        req.setX(x);
        req.setY(y);
        req.setDirection(direction.getSymbol());
        return req;
    }

    @Test
    @Transactional
    public void givenProbe_whenAddProbe_thenAddProbe() throws Exception {
        long mapId = initMap();
        ProbeReq req = createProbeReq(null, mapId, 1, 1, Direction.NORTH);
        ProbeIdentifierResp probe = service.addProbe(req);
        assertNotNull(probe.getId());
    }

    @Test
    public void givenProbe_whenMapDoesntExist_thenNotFoundException() throws Exception {
        long mapId = -1;
        ProbeReq req = createProbeReq(null, mapId, 1, 1, Direction.NORTH);
        assertThrows(
            NotFoundException.class, 
            () -> {
                service.addProbe(req);
            }
        );
    }

    @Test
    @Transactional
    public void givenProbe_whenOutOfMapBounds_thenProbeOutOfBoundsException() throws Exception {
        long mapId = initMap();
        ProbeReq req = createProbeReq(null, mapId, MAP_SIZE + 1, MAP_SIZE + 1, Direction.NORTH);
        assertThrows(
            ProbeOutOfBoundsException.class, 
            () -> {
                service.addProbe(req);
            }
        );
    }

    @Test
    @Transactional
    public void givenProbes_whenSameCoordinates_thenProbeCollisionException() throws Exception {
        long mapId = initMap();
        ProbeReq req = createProbeReq(null, mapId, 1, 1, Direction.NORTH);
        ProbeReq req2 = createProbeReq(null, mapId, 1, 1, Direction.SOUTH);
        ProbeIdentifierResp probe = service.addProbe(req);
        assertNotNull(probe.getId());
        assertThrows(
            ProbeCollisionException.class, 
            () -> {
                service.addProbe(req2);
            }
        );
    }

    @Test
    @Transactional
    public void givenId_whenProbeExists_thenReturnProbe() throws Exception {
        long mapId = initMap();
        ProbeReq req = createProbeReq(null, mapId, 1, 1, Direction.NORTH);
        ProbeIdentifierResp probe = service.addProbe(req);
        assertNotNull(probe.getId());
        
        ProbeResp foundProbe = service.getProbe(probe.getId());
        assertNotNull(foundProbe);
        assertEquals(probe.getId(), foundProbe.getId());
        assertEquals(probe.getX(), foundProbe.getX());
        assertEquals(probe.getY(), foundProbe.getY());
        assertEquals(probe.getDirection(), foundProbe.getDirection());
    }


    @Test
    public void givenId_whenProbeDoesntExist_thenNotFoundException() throws Exception {
        long id = -1;
        assertThrows(
            NotFoundException.class, 
            () -> {
                service.getProbe(id);
            }
        );
    }

    @Test
    @Transactional
    public void givenIdToDelete_whenMapExists_thenDelete() throws Exception {
        long mapId = initMap();
        ProbeReq req = createProbeReq(null, mapId, 1, 1, Direction.NORTH);
        ProbeIdentifierResp probe = service.addProbe(req);
        assertNotNull(probe.getId());
        
        long id = probe.getId();
        UUID resourceId = probe.getResourceId();
        service.deleteProbe(id, resourceId);

        assertThrows(
            NotFoundException.class, 
            () -> {
                service.getProbe(id);
            }
        );
    }

    @Test
    @Transactional
    public void givenIdToDelete_whenInvalidUUID_thenUnauthorizedException() throws Exception {
        long mapId = initMap();
        ProbeReq req = createProbeReq(null, mapId, 1, 1, Direction.NORTH);
        ProbeIdentifierResp probe = service.addProbe(req);
        assertNotNull(probe.getId());
        
        long id = probe.getId();

        assertThrows(
            UnauthorizedException.class, 
            () -> {
                service.deleteProbe(id, UUID.randomUUID());
            }
        );
    }

    @Test
    public void givenIdToDelete_whenMapDoesntExist_thenNotFoundException() throws Exception {
        long id = -1;
        assertThrows(
            NotFoundException.class, 
            () -> {
                service.deleteProbe(id, UUID.randomUUID());
            }
        );
    }

    @Test
    @Transactional
    public void givenProbeToUpdate_whenOk_thenUpdate() throws Exception {
        long mapId = initMap();
        ProbeReq req = createProbeReq(null, mapId, 1, 1, Direction.NORTH);
        ProbeIdentifierResp probe = service.addProbe(req);
        assertNotNull(probe.getId());
        
        long id = probe.getId();
        CommandsReq commandsReq = new CommandsReq();
        commandsReq.setResourceId(probe.getResourceId());
        commandsReq.setCommands("MRMLMLLM");
        CommandsResp resp = service.updateProbe(id, commandsReq);

        assertEquals(Direction.SOUTH.getSymbol(), resp.getDirection());
        assertEquals(true, resp.isCompleted());
        assertEquals(id, resp.getId());
        assertEquals(mapId, resp.getMapId());
        assertEquals(2, resp.getX());
        assertEquals(2, resp.getY());
    }

    @Test
    @Transactional
    public void givenProbeToUpdate_whenInvalidUUID_thenUnauthorizedException() throws Exception {
        long mapId = initMap();
        ProbeReq req = createProbeReq(null, mapId, 1, 1, Direction.NORTH);
        ProbeIdentifierResp probe = service.addProbe(req);
        assertNotNull(probe.getId());
        
        long id = probe.getId();
        CommandsReq commandsReq = new CommandsReq();
        commandsReq.setResourceId(UUID.randomUUID());
        commandsReq.setCommands("MRMLMLLM");

        assertThrows(
            UnauthorizedException.class, 
            () -> {
                service.updateProbe(id, commandsReq);
            }
        );
    }

    @Test
    public void givenProbeToUpdate_whenMapDoesntExist_thenNotFoundException() throws Exception {
        long id = -1;
        CommandsReq commandsReq = new CommandsReq();
        commandsReq.setResourceId(UUID.randomUUID());
        commandsReq.setCommands("MRMLMLLM");

        assertThrows(
            NotFoundException.class, 
            () -> {
                service.updateProbe(id, commandsReq);
            }
        );
    }

}
