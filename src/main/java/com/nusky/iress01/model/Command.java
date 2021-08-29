package com.nusky.iress01.model;


import com.nusky.iress01.exception.BotException;
import com.nusky.iress01.model.enums.Action;
import com.nusky.iress01.model.enums.Direction;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

import static com.nusky.iress01.exception.BotError.INVALID_COMMAND;
import static com.nusky.iress01.model.enums.Action.*;

@Getter
@Builder
public class Command {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("^((PLACE\\s+\\d+,\\d+,(NORTH|SOUTH|EAST|WEST))|LEFT|RIGHT|MOVE|REPORT|EXIT)$");

    private final Action action;
    private final Point point;
    private final Direction direction;

    /**
     * @param command a command to validate
     * @return boolean true if command is valid, else false
     */
    public static boolean validateCommand(String command) {
        return COMMAND_PATTERN.matcher(command).matches();
    }

    /**
     * @param command Examples: <br/>
     *                MOVE <br/>
     *                RIGHT <br/>
     *                REPORT <br/>
     *                PLACE 4,3,WEST
     * @return com.nusky.iress01.model.Command
     * @throws BotException
     */
    public static Command parseCommand(String command) {
        command = StringUtils.trim(command).toUpperCase();

        if (!validateCommand(command)) {
            throw new BotException(INVALID_COMMAND);
        }

        if (StringUtils.startsWith(command, PLACE.toString())) {
            return parsePlace(command);
        }

        CommandBuilder commandBuilder = Command.builder();
        Action action = Action.valueOf(command);
        switch (action) {
            case LEFT:
                return commandBuilder.action(LEFT).build();
            case RIGHT:
                return commandBuilder.action(RIGHT).build();
            case MOVE:
                return commandBuilder.action(MOVE).build();
            case REPORT:
                return commandBuilder.action(REPORT).build();
            case EXIT:
                return commandBuilder.action(EXIT).build();
        }

        throw new BotException(INVALID_COMMAND);
    }

    /**
     * @param command this should in PLACE x coordinate,y coordinate,Direction format
     * @return com.nusky.iress01.model.Command
     */
    private static Command parsePlace(String command) {
        String[] placeParams = StringUtils.substringAfter(command, PLACE.toString()).split(",");
        return Command.builder().action(PLACE)
                .point(Point.builder()
                        .x(Integer.parseInt(StringUtils.trim(placeParams[0])))
                        .y(Integer.parseInt(StringUtils.trim(placeParams[1])))
                        .build()
                ).direction(Direction.valueOf(StringUtils.trim(placeParams[2])))
                .build();
    }

}
