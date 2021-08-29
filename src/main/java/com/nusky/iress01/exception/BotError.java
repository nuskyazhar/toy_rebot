package com.nusky.iress01.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BotError {
    INVALID_COMMAND("10001", "Invalid command", "Command should be either PLACE, LEFT, RIGHT, MOVE or REPORT"),
    INVALID_POSITION("10002", "Invalid position", "Position should be within the table");

    private final String code;
    private final String title;
    private final String detail;
}
