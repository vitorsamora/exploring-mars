package br.com.elo7.exploringmars.helper;

import br.com.elo7.exploringmars.bean.ProbeReq;
import br.com.elo7.exploringmars.bean.ProbeResp;
import br.com.elo7.exploringmars.bean.CommandsResp;
import br.com.elo7.exploringmars.bean.ProbeIdentifierResp;
import br.com.elo7.exploringmars.db.entity.ProbeEntity;
import br.com.elo7.exploringmars.utils.Direction;

public class ProbeHelper {
    
    public static ProbeResp fromEntityToResp(ProbeEntity entity) {
        ProbeResp resp = new ProbeResp();
        resp.setId(entity.getId());
        resp.setX(entity.getX());
        resp.setY(entity.getY());
        resp.setDirection(entity.getDirection().getSymbol());
        resp.setMapId(entity.getMapId());
        return resp;
    }

    public static ProbeEntity fromReqToEntity(ProbeReq req) {
        ProbeEntity entity = new ProbeEntity();
        entity.setX(req.getX());
        entity.setY(req.getY());
        entity.setDirection(Direction.getDirectionBySymbol(req.getDirection()));
        entity.setMapId(req.getMapId());
        return entity;
    }

    public static ProbeIdentifierResp fromEntityToIdentifierResp(ProbeEntity entity) {
        ProbeIdentifierResp resp = new ProbeIdentifierResp();
        resp.setId(entity.getId());
        resp.setResourceId(entity.getResourceId());
        resp.setX(entity.getX());
        resp.setY(entity.getY());
        resp.setDirection(entity.getDirection().getSymbol());
        resp.setMapId(entity.getMapId());
        return resp;
    }

    public static CommandsResp fromEntityToCommandsResp(ProbeEntity entity, 
            boolean completed, String message) {
        CommandsResp resp = new CommandsResp();
        resp.setId(entity.getId());
        resp.setX(entity.getX());
        resp.setY(entity.getY());
        resp.setDirection(entity.getDirection().getSymbol());
        resp.setMapId(entity.getMapId());
        resp.setCompleted(completed);
        resp.setMessage(message);
        return resp;
    }

}
