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
import br.com.elo7.exploringmars.db.MapRepository;
import br.com.elo7.exploringmars.db.entity.MapEntity;
import br.com.elo7.exploringmars.exception.MapNotEmptyException;
import br.com.elo7.exploringmars.exception.NotFoundException;
import br.com.elo7.exploringmars.helper.MapHelper;

@Service
public class MapService {
    
    @Autowired
    private MapRepository mapRepository;

    public List<MapResp> getAllMaps() {
        List<MapResp> maps = new ArrayList<MapResp>();
        mapRepository.findAll().forEach(mapEntity -> {
            MapResp map = MapHelper.fromEntityToResp(mapEntity);
            maps.add(map);
        });
        return maps;
    }

    public MapResp addMap(MapReq mapReq) { 
        MapEntity map = MapHelper.fromReqToEntity(mapReq);   
        map = mapRepository.save(map);
        return MapHelper.fromEntityToResp(map); 
    }

    public MapDetailsResp getMap(long id) {
        Optional<MapEntity> record = mapRepository.findById(id);
        if (!record.isPresent()) {
            throw new NotFoundException("Map not found");
        }
        return MapHelper.fromEntityToDetailsResp(record.get());
    }

    public void deleteMap(long id) {
        try {
            mapRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Map not found");
        } catch (DataIntegrityViolationException e) {
            throw new MapNotEmptyException("Map is not empty");
        }
    }

}
