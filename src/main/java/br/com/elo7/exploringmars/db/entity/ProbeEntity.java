package br.com.elo7.exploringmars.db.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.com.elo7.exploringmars.db.converter.DirectionAttributeConverter;
import br.com.elo7.exploringmars.utils.Direction;

@Entity
@Table(name = "probe",
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_attributes", columnNames ={"map_id", "x", "y"})
    }
)
public class ProbeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "x", nullable = false)
    private int x;

    @Column(name = "y", nullable = false)
    private int y;

    @Column(name = "direction", nullable = false)
    @Convert(converter = DirectionAttributeConverter.class)
    private Direction direction;

    @Column(name = "map_id", nullable = false)
    private long mapId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public long getMapId() {
        return mapId;
    }

    public void setMapId(long mapId) {
        this.mapId = mapId;
    }
    
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() != ProbeEntity.class) {
            return false;
        }
        
        ProbeEntity other = (ProbeEntity) object;
        if (this.getMapId() != other.getMapId()) {
            return false;
        }
        if (this.getX() != other.getX()) {
            return false;
        }
        if (this.getY() != other.getY()) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

}
