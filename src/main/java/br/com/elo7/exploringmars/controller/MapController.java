package br.com.elo7.exploringmars.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.elo7.exploringmars.bean.MapReq;
import br.com.elo7.exploringmars.bean.MapResp;
import br.com.elo7.exploringmars.bean.MapDetailsResp;
import br.com.elo7.exploringmars.service.MapService;

@RestController
@RequestMapping("/maps")
public class MapController {
    
    @Autowired
    private MapService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MapResp> findAll() {
        return service.getAllMaps();
    }

    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public MapDetailsResp findById(@PathVariable long id) {
        return service.getMap(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MapResp addMap(@Valid @RequestBody MapReq mapReq) {  
        return service.addMap(mapReq); 
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path ={"/{id}"})
    public void deleteMap(@PathVariable long id) {
        service.deleteMap(id);
    }

}
