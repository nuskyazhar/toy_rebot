package com.nusky.iress01.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class SquareTable implements Table {
    private int width;
    private int length;

    @Override
    public boolean isValidPosition(Point point) {
        if (Objects.isNull(point)) {
            return false;
        }
        return point.getX() >= 0 && point.getY() >= 0 && point.getX() < width && point.getY() < length;
    }

    @Override
    public boolean isNotValidPosition(Point point) {
        return !isValidPosition(point);
    }
}
