package br.com.elo7.exploringmars.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.elo7.exploringmars.bean.ProbeIdentifierResp;
import br.com.elo7.exploringmars.bean.ProbeReq;
import br.com.elo7.exploringmars.bean.ProbeResp;
import br.com.elo7.exploringmars.db.entity.ProbeEntity;
import br.com.elo7.exploringmars.utils.Direction;

@SpringBootTest
public class ProbeHelperTest {
    
    @Test
    public void fromEntityToRespTest() throws Exception {
        ProbeEntity entity = new ProbeEntity();
        entity.setId(0);
        entity.setResourceId(UUID.randomUUID());
        entity.setMapId(1);
        entity.setX(2);
        entity.setY(3);
        entity.setDirection(Direction.NORTH);

        ProbeResp resp = ProbeHelper.fromEntityToResp(entity);
        assertEquals(entity.getId(), resp.getId());
        assertEquals(entity.getMapId(), resp.getMapId());
        assertEquals(entity.getX(), resp.getX());
        assertEquals(entity.getY(), resp.getY());
        assertEquals(entity.getDirection().getSymbol(), resp.getDirection());
    }

    @Test
    public void fromEntityToIdentifierRespTest() throws Exception {
        ProbeEntity entity = new ProbeEntity();
        entity.setId(0);
        entity.setResourceId(UUID.randomUUID());
        entity.setMapId(1);
        entity.setX(2);
        entity.setY(3);
        entity.setDirection(Direction.NORTH);

        ProbeIdentifierResp resp = ProbeHelper.fromEntityToIdentifierResp(entity);
        assertEquals(entity.getId(), resp.getId());
        assertEquals(entity.getResourceId(), resp.getResourceId());
        assertEquals(entity.getMapId(), resp.getMapId());
        assertEquals(entity.getX(), resp.getX());
        assertEquals(entity.getY(), resp.getY());
        assertEquals(entity.getDirection().getSymbol(), resp.getDirection());
    }

    @Test
    public void fromReqToEntityTest() throws Exception {
        ProbeReq req = new ProbeReq();
        req.setMapId(0L);
        req.setX(1);
        req.setY(2);
        req.setDirection(Direction.NORTH.getSymbol());

        ProbeEntity entity = ProbeHelper.fromReqToEntity(req);
        assertEquals(req.getMapId(), entity.getMapId());
        assertEquals(req.getX(), entity.getX());
        assertEquals(req.getY(), entity.getY());
        assertEquals(Direction.NORTH, entity.getDirection());
    }

}
