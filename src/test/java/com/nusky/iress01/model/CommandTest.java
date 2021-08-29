package com.nusky.iress01.model;

import com.nusky.iress01.exception.BotException;
import com.nusky.iress01.model.enums.Action;
import com.nusky.iress01.model.enums.Direction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.nusky.iress01.exception.BotError.INVALID_COMMAND;
import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "MOVE",
            "LEFT",
            "RIGHT",
            "REPORT",
            "PLACE 4,5,WEST",
            "PLACE 2,3,SOUTH",
            "PLACE 12,33,NORTH",
            "PLACE 0,0,EAST",
            "PLACE        0,0,EAST",
    })
    public void testValidateCommand_valid_pass(String input) {
        assertTrue(Command.validateCommand(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "move",
            "Left",
            "riGHt",
            "PLACE 4,5,WEST   ",
            "PLACE 2,3,south",
            "  PLACE 12,33,NORTH",
            "PLACE 0 ,0,EAST",
            "PLACE 0, 0,EAST",
            "PLACE 0,0, EAST",
    })
    public void testValidateCommand_invalid_fail(String input) {
        assertFalse(Command.validateCommand(input));
    }

    @ParameterizedTest
    @CsvSource(value = {
            //Input command:expected command:expected x:expected y:expected Direction
            "PLACE 4,5,WEST:PLACE:4:5:WEST",
            "PLACE 0,5,EAST:PLACE:0:5:EAST",
            "PLACE 0,0,SOUTH:PLACE:0:0:SOUTH",
            "PLACE 2,1,NORTH:PLACE:2:1:NORTH",
            "Place 2,1,North:PLACE:2:1:NORTH",

    }, delimiter = ':')
    public void testParseCommand_place_valid_pass(String input, String expectedCommand, int expectedX, int expectedY, String expectedDirection) {
        Command actual = Command.parseCommand(input);
        assertEquals(Action.valueOf(expectedCommand), actual.getAction());
        assertEquals(expectedX, actual.getPoint().getX());
        assertEquals(expectedY, actual.getPoint().getY());
        assertEquals(Direction.valueOf(expectedDirection), actual.getDirection());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "MOVE,MOVE",
            "move,MOVE",
            "LEFT,LEFT",
            "RIGHT,RIGHT",
            "Right,RIGHT",
            "REPORT,REPORT",
            "EXIT,EXIT",
    })
    public void testParseCommand_valid_pass(String input, String expectedCommand) {
        Command actual = Command.parseCommand(input);
        assertEquals(Action.valueOf(expectedCommand), actual.getAction());
        assertNull(actual.getDirection());
        assertNull(actual.getPoint());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "joe",
            "go",
            "come",
    })
    public void testParseCommand_invalid_fail(String input) {
        BotException botException = assertThrows(BotException.class, () -> Command.parseCommand(input));
        assertEquals(INVALID_COMMAND.getCode(), botException.getError().getCode());
    }


}