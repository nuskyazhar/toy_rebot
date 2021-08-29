package com.nusky.iress01.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BotException extends RuntimeException {
    private final BotError error;
}
