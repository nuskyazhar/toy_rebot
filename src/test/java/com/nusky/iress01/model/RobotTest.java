package com.nusky.iress01.model;

import com.nusky.iress01.model.enums.Action;
import com.nusky.iress01.model.enums.Direction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RobotTest {

    SquareTable table = SquareTable.builder().length(5).width(5).build();

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
            "NORTH,false,WEST",
            "NORTH,true,EAST",
            "WEST,false,SOUTH",
            "WEST,true,NORTH",
            "SOUTH,true,WEST",
            "SOUTH,false,EAST",
            "EAST,false,NORTH",
            "EAST,true,SOUTH",
    })
    public void rotate_test(String currentDirection, boolean clockwise, String expectedDirection) {
        Robot robot = Robot.builder().currentDirection(Direction.valueOf(currentDirection))
                .point(Point.builder().x(3).y(3).build())
                .build();
        assertEquals(Direction.valueOf(expectedDirection), robot.rotate(clockwise));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "SOUTH,3,3",
            "WEST,0,1",
            "EAST,0,0",
            "NORTH,4,4",
    })
    public void walk_place_valid_pass(String direction, int x, int y) {
        Robot robot = Robot.builder().table(table).build();
        Command command = Command.builder().action(Action.PLACE)
                .direction(Direction.valueOf(direction))
                .point(Point.builder()
                        .x(x)
                        .y(y)
                        .build())
                .build();
        robot.walk(command);
        assertEquals(x, robot.getPoint().getX());
        assertEquals(y, robot.getPoint().getY());
        assertEquals(Direction.valueOf(direction), robot.getCurrentDirection());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "SOUTH,-1,3",
            "WEST,0,5",
            "EAST,5,5",
            "NORTH,4,-1",
    })
    public void walk_place_invalid_fail(String direction, int x, int y) {
        Robot robot = Robot.builder().table(table).build();
        Command command = Command.builder().action(Action.PLACE)
                .direction(Direction.valueOf(direction))
                .point(Point.builder()
                        .x(x)
                        .y(y)
                        .build())
                .build();
        robot.walk(command);
        assertNull(robot.getPoint());
        assertNull(robot.getCurrentDirection());
    }

    //
    @ParameterizedTest
    @CsvSource(value = {
            "SOUTH,3,3,LEFT,EAST",
            "WEST,0,1,LEFT,SOUTH",
            "WEST,1,1,LEFT,SOUTH",
            "EAST,0,0,LEFT,NORTH",
            "EAST,2,3,LEFT,NORTH",
            "NORTH,4,4,LEFT,WEST",

            "SOUTH,3,3,RIGHT,WEST",
            "WEST,0,1,RIGHT,NORTH",
            "NORTH,4,4,RIGHT,EAST",
            "EAST,0,0,RIGHT,SOUTH",
    })
    public void walk_left_right_valid_pass(String currentDirection, int x, int y, String action, String expectedDirection) {
        Robot robot = Robot.builder().table(table).build();
        Command placeCommand = Command.builder().action(Action.PLACE)
                .direction(Direction.valueOf(currentDirection))
                .point(Point.builder().x(x).y(y).build())
                .build();
        Command command = Command.builder().action(Action.valueOf(action)).build();
        robot.walk(placeCommand);
        robot.walk(command);
        assertEquals(x, robot.getPoint().getX());
        assertEquals(y, robot.getPoint().getY());
        assertEquals(Direction.valueOf(expectedDirection), robot.getCurrentDirection());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "SOUTH,3,3,LEFT",
            "WEST,0,1,LEFT",
            "WEST,1,1,LEFT",
            "EAST,0,0,RIGHT",
            "EAST,2,3,RIGHT",
            "NORTH,4,4,RIGHT",
            "NORTH,4,4,REPORT",
            "NORTH,4,4,MOVE",
    })
    public void walk_left_right_no_place_invalid_fail(String currentDirection, int x, int y, String action) {
        Robot robot = Robot.builder().table(table).point(Point.builder().x(x).y(y).build()).currentDirection(Direction.valueOf(currentDirection)).build();
        Command command = Command.builder().action(Action.valueOf(action))
                .direction(Direction.valueOf(currentDirection))
                .point(Point.builder()
                        .x(x)
                        .y(y)
                        .build())
                .build();
        robot.walk(command);
        assertEquals(x, robot.getPoint().getX());
        assertEquals(y, robot.getPoint().getY());
        //Place command is not issued. So, any action should not do anything. Therefore, direction/point should remain the same
        assertEquals(Direction.valueOf(currentDirection), robot.getCurrentDirection());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "SOUTH,0,0,0,0",
            "SOUTH,1,0,1,0",
            "SOUTH,4,0,4,0",
            "SOUTH,0,1,0,0",
            "SOUTH,0,4,0,3",

            "WEST,0,0,0,0",
            "WEST,0,1,0,1",
            "WEST,0,4,0,4",
            "WEST,1,0,0,0",

            "NORTH,0,4,0,4",
            "NORTH,1,4,1,4",
            "NORTH,4,4,4,4",
            "NORTH,2,3,2,4",

            "EAST,4,0,4,0",
            "EAST,4,1,4,1",
            "EAST,4,4,4,4",
            "EAST,3,3,4,3",
    })
    public void walk_move_valid_pass(String currentDirection, int x, int y, int expectedX, int expectedY) {
        Robot robot = Robot.builder().table(table).build();
        Command placeCommand = Command.builder().action(Action.PLACE)
                .direction(Direction.valueOf(currentDirection))
                .point(Point.builder().x(x).y(y).build())
                .build();
        Command command = Command.builder().action(Action.MOVE).build();
        robot.walk(placeCommand);
        robot.walk(command);
        assertEquals(expectedX, robot.getPoint().getX());
        assertEquals(expectedY, robot.getPoint().getY());
        assertEquals(Direction.valueOf(currentDirection), robot.getCurrentDirection());
    }

    @Test
    public void walk_report_do_not_change_point() {
        Robot robot = Robot.builder().table(table).build();
        Command placeCommand = Command.builder().action(Action.PLACE)
                .direction(Direction.SOUTH)
                .point(Point.builder().x(3).y(3).build())
                .build();
        Command command = Command.builder().action(Action.REPORT).build();
        robot.walk(placeCommand);
        robot.walk(command);
        assertEquals(3, robot.getPoint().getX());
        assertEquals(3, robot.getPoint().getY());
        assertEquals(Direction.SOUTH, robot.getCurrentDirection());
    }
}