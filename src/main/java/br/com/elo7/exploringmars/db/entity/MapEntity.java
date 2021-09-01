package br.com.elo7.exploringmars.db.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "map")
public class MapEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "max_x", nullable = false)
    private int maxX;

    @Column(name = "max_y", nullable = false)
    private int maxY;

    @OneToMany(targetEntity=ProbeEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "map_id")
    private Set<ProbeEntity> probeSet = new HashSet<ProbeEntity>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public Set<ProbeEntity> getProbeSet() {
        return probeSet;
    }

    public void setProbeSet(Set<ProbeEntity> probeList) {
        this.probeSet = probeList;
    }

    public boolean isOutOfBounds(int x, int y) {
        return x > maxX || y > maxY;
    }
    
}
