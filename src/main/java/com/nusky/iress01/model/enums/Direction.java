package com.nusky.iress01.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Getter
public enum Direction {

    NORTH(0),
    EAST(1),
    SOUTH(2),
    WEST(3);

    private static final Map<Integer, Direction> map = new HashMap<>();

    static {
        for (Direction direction : Direction.values()) {
            map.put(direction.index, direction);
        }
    }

    private final int index;

    public static Direction fromIndex(int index) {
        return map.get(index);
    }

    public static int numberOfDirections() {
        return map.size();
    }

}
