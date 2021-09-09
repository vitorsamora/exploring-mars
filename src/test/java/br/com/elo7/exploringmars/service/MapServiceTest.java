package br.com.elo7.exploringmars.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.elo7.exploringmars.bean.MapDetailsResp;
import br.com.elo7.exploringmars.bean.MapReq;
import br.com.elo7.exploringmars.bean.MapResp;
import br.com.elo7.exploringmars.exception.NotFoundException;

@SpringBootTest
public class MapServiceTest {
    
    @Autowired
    private MapService service;

    private static MapReq createMap(int maxX, int maxY) {
        MapReq map = new MapReq();
        map.setMaxX(maxX);
        map.setMaxY(maxY);
        return map;
    }

    @Test
    public void givenMap_whenAddMap_thenAddMap() throws Exception {
        MapReq mapReq = createMap(100, 100);
        MapResp map = service.addMap(mapReq);
        assertNotNull(map.getId());
    }

    @Test
    @Transactional
    public void givenId_whenMapExists_thenReturnMap() throws Exception {
        MapReq mapReq = createMap(100, 100);
        MapResp map = service.addMap(mapReq);
        assertNotNull(map.getId());
        
        MapResp foundMap = service.getMap(map.getId());
        assertNotNull(foundMap);
        assertEquals(map.getId(), foundMap.getId());
        assertEquals(map.getMaxX(), foundMap.getMaxX());
        assertEquals(map.getMaxY(), foundMap.getMaxY());
    }

    @Test
    public void givenId_whenMapDoesntExist_thenNotFoundException() throws Exception {
        long id = -1;
        assertThrows(
            NotFoundException.class, 
            () -> {
                service.getMap(id);
            }
        );
    }

    @Test
    public void givenIdToDelete_whenMapExists_thenDelete() throws Exception {
        MapReq mapReq = createMap(100, 100);
        MapResp map = service.addMap(mapReq);
        assertNotNull(map.getId());
        
        long id = map.getId();
        service.deleteMap(id);

        assertThrows(
            NotFoundException.class, 
            () -> {
                service.getMap(id);
            }
        );
    }

    @Test
    public void givenIdToDelete_whenMapDoesntExist_thenNotFoundException() throws Exception {
        long id = -1;
        assertThrows(
            NotFoundException.class, 
            () -> {
                service.deleteMap(id);
            }
        );
    }

    @Test
    public void givenMapToUpdate_whenMapDoesntExist_thenNotFoundException() throws Exception {
        long id = -1;
        MapReq mapReq = createMap(100, 100);
        assertThrows(
            NotFoundException.class, 
            () -> {
                service.updateMap(id, mapReq);
            }
        );
    }

    @Test
    @Transactional
    public void givenMapToUpdate_whenNewSizeIsBigger_thenUpdate() throws Exception {
        MapReq mapReq = createMap(10, 20);
        MapResp map = service.addMap(mapReq);
        assertNotNull(map.getId());
        
        MapReq newMapReq = createMap(20, 40);
        MapDetailsResp resp = service.updateMap(map.getId(), newMapReq);
        
        assertNotNull(resp);
        assertEquals(map.getId(), resp.getId());
        assertEquals(newMapReq.getMaxX(), resp.getMaxX());
        assertEquals(newMapReq.getMaxY(), resp.getMaxY());
    }

}
