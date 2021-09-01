package br.com.elo7.exploringmars.db.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import br.com.elo7.exploringmars.utils.Direction;

@Converter
public class DirectionAttributeConverter implements AttributeConverter<Direction, Integer> {
 
    @Override
    public Integer convertToDatabaseColumn(Direction direction) {
        if (direction == null)
            return null;

        return direction.getCode();
    }
 
    @Override
    public Direction convertToEntityAttribute(Integer dbData) {
        if (dbData == null)
            return null;

        return Direction.getDirectionByCode(dbData);
    }
 
}
