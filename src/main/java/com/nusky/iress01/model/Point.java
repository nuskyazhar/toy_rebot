package com.nusky.iress01.model;

import lombok.Builder;
import lombok.Data;

/**
 * point is a location with its x,y coordinates
 */
@Data
@Builder
public class Point {
    private int x;
    private int y;
}
