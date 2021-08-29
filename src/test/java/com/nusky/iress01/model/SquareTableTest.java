package com.nusky.iress01.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SquareTableTest {

    SquareTable table = SquareTable.builder().length(5).width(5).build();

    @ParameterizedTest
    @CsvSource(value = {
            "0,0",
            "4,0",
            "4,4",
            "0,4",
            "3,3",
            "2,3",
            "2,1",
    })
    public void isValidPosition_valid_pass(int x, int y) {
        assertTrue(table.isValidPosition(Point.builder().x(x).y(y).build()));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "-1,0",
            "4,-1",
            "5,3",
            "3,5",
    })
    public void isValidPosition_invalid_fail(int x, int y) {
        assertFalse(table.isValidPosition(Point.builder().x(x).y(y).build()));
        assertTrue(table.isNotValidPosition(Point.builder().x(x).y(y).build()));
    }

    @Test
    public void isValidPosition_null_fail(){
        assertFalse(table.isValidPosition(null));
    }

}