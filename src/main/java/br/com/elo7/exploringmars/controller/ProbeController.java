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

import br.com.elo7.exploringmars.bean.ProbeIdentifierReq;
import br.com.elo7.exploringmars.bean.ProbeReq;
import br.com.elo7.exploringmars.bean.ProbeResp;
import br.com.elo7.exploringmars.bean.ProbeIdentifierResp;
import br.com.elo7.exploringmars.service.ProbeService;

@RestController
@RequestMapping("/probes")
public class ProbeController {

    @Autowired
    private ProbeService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProbeResp> findAll() {
        return service.getAllProbes();
    }

    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProbeResp findById(@PathVariable long id) {
        return service.getProbe(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProbeIdentifierResp addProbe(@Valid @RequestBody ProbeReq probeReq) {    
        return service.addProbe(probeReq);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path ={"/{id}"})
    public void deleteProbe(@PathVariable long id, @Valid @RequestBody ProbeIdentifierReq probeIdentifierReq) {
        service.deleteProbe(id, probeIdentifierReq.getResourceId());
    }
    
}
