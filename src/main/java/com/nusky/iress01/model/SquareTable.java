package com.nusky.iress01.model;

import lombok.Builder;

import java.util.Objects;

@Builder
public class SquareTable {
    private final int width;
    private final int length;

    public boolean isValidPosition(Point point) {
        if (Objects.isNull(point)) {
            return false;
        }
        return point.getX() >= 0 && point.getY() >= 0 && point.getX() < width && point.getY() < length;
    }

    public boolean isNotValidPosition(Point point) {
        return !isValidPosition(point);
    }
}
