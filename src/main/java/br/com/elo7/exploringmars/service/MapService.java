package br.com.elo7.exploringmars.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.elo7.exploringmars.bean.MapDetailsResp;
import br.com.elo7.exploringmars.bean.MapReq;
import br.com.elo7.exploringmars.bean.MapResp;
import br.com.elo7.exploringmars.concurrency.MapCache;
import br.com.elo7.exploringmars.db.MapRepository;
import br.com.elo7.exploringmars.db.entity.MapEntity;
import br.com.elo7.exploringmars.db.entity.ProbeEntity;
import br.com.elo7.exploringmars.exception.MapNotEmptyException;
import br.com.elo7.exploringmars.exception.NotFoundException;
import br.com.elo7.exploringmars.helper.MapHelper;

@Service
public class MapService {
    
    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private MapCache mapCache;

    public List<MapResp> getAllMaps() {
        List<MapResp> maps = new ArrayList<MapResp>();
        mapRepository.findAll().forEach(mapEntity -> {
            MapResp map = MapHelper.fromEntityToResp(mapEntity);
            maps.add(map);
        });
        return maps;
    }
    
    public MapDetailsResp getMap(long id) {
        Optional<MapEntity> optMap = mapRepository.findById(id);
        if (!optMap.isPresent()) {
            throw new NotFoundException("Map not found");
        }
        return MapHelper.fromEntityToDetailsResp(optMap.get());
    }

    public MapResp addMap(MapReq mapReq) { 
        MapEntity map = MapHelper.fromReqToEntity(mapReq);   
        map = mapRepository.save(map);
        return MapHelper.fromEntityToResp(map); 
    }

    public void deleteMap(long id) {
        MapEntity cachedMap = mapCache.getMapReference(id);
        try {
            synchronized (cachedMap) {
                mapRepository.deleteById(cachedMap.getId());
                mapCache.removeMap(id);
            }
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Map not found");
        } catch (DataIntegrityViolationException e) {
            throw new MapNotEmptyException("Map is not empty");
        } finally {
            mapCache.releaseMapReference(id);
        }
    }

    public MapDetailsResp updateMap(long id, MapReq mapReq) {
        MapEntity cachedMap = mapCache.getMapReference(id);
        try {
            synchronized (cachedMap) {
                if (cachedMap.getMaxX() <= mapReq.getMaxX() && 
                        cachedMap.getMaxY() <= mapReq.getMaxY()) {
                    cachedMap.setMaxX(mapReq.getMaxX());
                    cachedMap.setMaxY(mapReq.getMaxY());
                    MapEntity updatedMap = mapRepository.save(cachedMap);
                    return MapHelper.fromEntityToDetailsResp(updatedMap);
                }

                for (ProbeEntity probe : cachedMap.getProbeSet()) {
                    if (probe.getX() > mapReq.getMaxX() || probe.getY() > mapReq.getMaxY()) {
                        throw new MapNotEmptyException("Invalid new map size");
                    }
                }

                cachedMap.setMaxX(mapReq.getMaxX());
                cachedMap.setMaxY(mapReq.getMaxY());
                MapEntity updatedMap = mapRepository.save(cachedMap);
                return MapHelper.fromEntityToDetailsResp(updatedMap);
            }
        } finally {
            mapCache.releaseMapReference(id);
        }
    }
}
