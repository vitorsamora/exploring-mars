package br.com.elo7.exploringmars.db.entity;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.elo7.exploringmars.db.converter.DirectionAttributeConverter;
import br.com.elo7.exploringmars.utils.Direction;

@Entity
@Table(name = "probe",
    indexes = {
        @Index(name="ix_map_id", columnList = "map_id", unique = false)
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uq_attributes", columnNames ={"map_id", "x", "y"})
    }
)
public class ProbeEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "resource_id", nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    private UUID resourceId;
    
    @Column(name = "x", nullable = false)
    private int x;

    @Column(name = "y", nullable = false)
    private int y;

    @Column(name = "direction", nullable = false)
    @Convert(converter = DirectionAttributeConverter.class)
    private Direction direction;

    @Column(name = "map_id", nullable = false)
    private long mapId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date createDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modify_date")
    private Date modifyDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
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

    public boolean checkResourceId(UUID receivedId) {
        if (receivedId == null) {
            return false;
        }
        return resourceId.compareTo(receivedId) == 0;
    }

    public void turnRight() {
        direction = Direction.getDirectionByCode((direction.getCode() + 1) % 4);
    }

    public void turnLeft() {
        direction = Direction.getDirectionByCode((direction.getCode() + 3) % 4);
    }

    public void move() {
        switch (direction) {
            case NORTH:
                y++;
                break;
            case EAST:
                x++;
                break;
            case SOUTH:
                y--;
                break;
            case WEST:
                x--;
                break;
        }
    }

    public void moveBack() {
        switch (direction) {
            case NORTH:
                y--;
                break;
            case EAST:
                x--;
                break;
            case SOUTH:
                y++;
                break;
            case WEST:
                x++;
                break;
        }
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
