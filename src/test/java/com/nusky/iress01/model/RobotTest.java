package com.nusky.iress01.model;

import com.nusky.iress01.model.enums.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RobotTest {

    @ParameterizedTest
    @CsvSource(value = {
            "NORTH,3,3,3,4",
            "SOUTH,3,3,3,2",
            "WEST,3,3,2,3",
            "EAST,3,3,4,3",
    })
    public void getNextPosition_test(String direction, int currentX, int currentY, int nextX, int nextY) {
        Robot robot = Robot.builder().currentDirection(Direction.valueOf(direction))
                .point(Point.builder()
                        .x(currentX)
                        .y(currentY)
                        .build())
                .build();
        Point nextPosition = robot.getNextPosition();
        assertEquals(nextY, nextPosition.getY());
        assertEquals(nextX, nextPosition.getX());
    }


    @ParameterizedTest
    @CsvSource(value = {
            // Current direction: current x: current y: expected string to report
            "NORTH:3:3:3,3,NORTH",
            "SOUTH:0:1:0,1,SOUTH",
            "WEST:8:6:8,6,WEST",
            "EAST:12:13:12,13,EAST",
    }, delimiter = ':')
    public void report_test(String direction, int currentX, int currentY, String expected) {
        Robot robot = Robot.builder().currentDirection(Direction.valueOf(direction))
                .point(Point.builder()
                        .x(currentX)
                        .y(currentY)
                        .build())
                .build();
        assertEquals(expected, robot.report());
    }


    @ParameterizedTest
    @CsvSource(value = {
            "NORTH,-1,WEST",
            "NORTH,1,EAST",
            "WEST,-1,SOUTH",
            "WEST,1,NORTH",
            "SOUTH,1,WEST",
            "SOUTH,-1,EAST",
            "EAST,-1,NORTH",
            "EAST,1,SOUTH",
    })
    public void rotate_test(String currentDirection, int towards, String expectedDirection) {
        Robot robot = Robot.builder().currentDirection(Direction.valueOf(currentDirection))
                .point(Point.builder().x(3).y(3).build())
                .build();
        assertEquals(Direction.valueOf(expectedDirection), robot.rotate(towards));
    }
}