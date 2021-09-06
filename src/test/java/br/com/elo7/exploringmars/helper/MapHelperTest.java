package br.com.elo7.exploringmars.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.elo7.exploringmars.bean.MapDetailsResp;
import br.com.elo7.exploringmars.bean.MapReq;
import br.com.elo7.exploringmars.bean.MapResp;
import br.com.elo7.exploringmars.db.entity.MapEntity;
import br.com.elo7.exploringmars.db.entity.ProbeEntity;
import br.com.elo7.exploringmars.utils.Direction;

@SpringBootTest
public class MapHelperTest {
    
    @Test
    public void fromEntityToRespTest() throws Exception {
        MapEntity entity = new MapEntity();
        entity.setId(0);
        entity.setMaxX(100);
        entity.setMaxY(150);
        entity.getProbeSet().add(new ProbeEntity());

        MapResp resp = MapHelper.fromEntityToResp(entity);
        assertEquals(entity.getId(), resp.getId());
        assertEquals(entity.getMaxX(), resp.getMaxX());
        assertEquals(entity.getMaxY(), resp.getMaxY());
    }

    @Test
    public void fromEntityToDetailsRespTest() throws Exception {
        MapEntity entity = new MapEntity();
        entity.setId(0);
        entity.setMaxX(100);
        entity.setMaxY(150);

        ProbeEntity probe = new ProbeEntity();
        probe.setId(0);
        probe.setMapId(0);
        probe.setX(0);
        probe.setY(0);
        probe.setDirection(Direction.NORTH);
        entity.getProbeSet().add(probe);

        MapDetailsResp resp = MapHelper.fromEntityToDetailsResp(entity);
        assertEquals(entity.getId(), resp.getId());
        assertEquals(entity.getMaxX(), resp.getMaxX());
        assertEquals(entity.getMaxY(), resp.getMaxY());
        assertNotNull(resp.getProbes());
        assertTrue(resp.getProbes().size() > 0);
    }

    @Test
    public void fromReqToEntityTest() throws Exception {
        MapReq req = new MapReq();
        req.setMaxX(100);
        req.setMaxY(150);
        
        MapEntity entity = MapHelper.fromReqToEntity(req);
        assertEquals(req.getMaxX(), entity.getMaxX());
        assertEquals(req.getMaxY(), entity.getMaxY());
        assertEquals(0, entity.getId());
    }

}
