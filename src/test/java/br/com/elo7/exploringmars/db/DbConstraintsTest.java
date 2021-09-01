package br.com.elo7.exploringmars.db;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.elo7.exploringmars.db.entity.MapEntity;
import br.com.elo7.exploringmars.db.entity.ProbeEntity;
import br.com.elo7.exploringmars.utils.Direction;

@SpringBootTest
public class DbConstraintsTest {
    
    @Autowired
    private ProbeRepository probeRepository;

    @Autowired
    private MapRepository mapRepository;

    private static final int MAP_SIZE = 100;

    private long initMap() {
        MapEntity mapEntity = new MapEntity();
        mapEntity.setMaxX(MAP_SIZE);
        mapEntity.setMaxY(MAP_SIZE);
        MapEntity savedMapEntity = mapRepository.save(mapEntity);
        assertNotNull(savedMapEntity);
        assertNotNull(savedMapEntity.getId());
        return savedMapEntity.getId();
    }

    private ProbeEntity createProbe(long mapId, int x, int y, Direction direction) {
        ProbeEntity entity = new ProbeEntity();
        entity.setMapId(mapId);
        entity.setX(x);
        entity.setY(y);
        entity.setDirection(direction);
        return entity;
    }
    
    @Test
    public void givenMapAndProbe_whenConstraintsNotViolated_thenSave() throws Exception {
        long mapId = initMap();
        ProbeEntity entity = createProbe(mapId, 1, 1, Direction.NORTH);
        ProbeEntity savedEntity = probeRepository.save(entity);
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getId());
    }
    
    @Test
    public void givenMapAndProbe_whenDirectionIsNull_thenConstraintViolationException() throws Exception {
        long mapId = initMap();
        assertThrows(
            DataIntegrityViolationException.class, 
            () -> {
                ProbeEntity entity = createProbe(mapId, 1, 1, null);
                probeRepository.save(entity);
            }
        );
    }

    @Test
    public void givenMapAndProbe_whenNotEmptyPosition_thenConstraintViolationException() throws Exception {
        long mapId = initMap();
        ProbeEntity entity = createProbe(mapId, 1, 1, Direction.NORTH);
        ProbeEntity savedEntity = probeRepository.save(entity);
        assertNotNull(savedEntity);
        assertNotNull(savedEntity.getId());
        assertThrows(
            DataIntegrityViolationException.class, 
            () -> {
                ProbeEntity entity2 = createProbe(mapId, 1, 1, Direction.EAST);
                probeRepository.save(entity2);
            }
        );
    }

    @Test
    public void givenProbe_whenMapDoesNotExist_thenConstraintViolationException() throws Exception {
        assertThrows(
            DataIntegrityViolationException.class, 
            () -> {
                ProbeEntity entity = createProbe(-1, 1, 1, Direction.NORTH);
                probeRepository.save(entity);
            }
        );
    }

}
