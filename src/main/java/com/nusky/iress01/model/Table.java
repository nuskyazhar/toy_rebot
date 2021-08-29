package com.nusky.iress01.model;

public interface Table {
    /**
     * @param point position that must be checked as a com.nusky.iress01.model.Point
     * @return boolean
     */
    boolean isValidPosition(Point point);
    boolean isNotValidPosition(Point point);
}
