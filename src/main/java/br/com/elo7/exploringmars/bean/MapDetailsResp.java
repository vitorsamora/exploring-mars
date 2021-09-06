package br.com.elo7.exploringmars.bean;

import java.util.Set;

public class MapDetailsResp extends MapResp {
    
    private Set<ProbeResp> probes;

    public Set<ProbeResp> getProbes() {
        return probes;
    }

    public void setProbes(Set<ProbeResp> probes) {
        this.probes = probes;
    }

}
