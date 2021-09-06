package br.com.elo7.exploringmars.helper;

import java.util.HashSet;
import java.util.Set;

import br.com.elo7.exploringmars.bean.MapReq;
import br.com.elo7.exploringmars.bean.MapResp;
import br.com.elo7.exploringmars.bean.MapDetailsResp;
import br.com.elo7.exploringmars.bean.ProbeResp;
import br.com.elo7.exploringmars.db.entity.MapEntity;

public class MapHelper {
    
    public static MapResp fromEntityToResp(MapEntity entity) {
        MapResp resp = new MapResp();
        resp.setId(entity.getId());
        resp.setMaxX(entity.getMaxX());
        resp.setMaxY(entity.getMaxY());
        return resp;
    }

    public static MapDetailsResp fromEntityToDetailsResp(MapEntity entity) {
        MapDetailsResp resp = new MapDetailsResp();
        resp.setId(entity.getId());
        resp.setMaxX(entity.getMaxX());
        resp.setMaxY(entity.getMaxY());
        Set<ProbeResp> probes = new HashSet<ProbeResp>();
        entity.getProbeSet().forEach(probeEntity -> {
            ProbeResp probeResp = ProbeHelper.fromEntityToResp(probeEntity);
            probes.add(probeResp);
        });
        resp.setProbes(probes);
        return resp;
    }

    public static MapEntity fromReqToEntity(MapReq req) {
        MapEntity entity = new MapEntity();
        entity.setMaxX(req.getMaxX());
        entity.setMaxY(req.getMaxY());
        return entity;
    }
    
}
