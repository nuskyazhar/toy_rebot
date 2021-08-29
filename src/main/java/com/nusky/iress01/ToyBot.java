package com.nusky.iress01;

import com.nusky.iress01.exception.BotException;
import com.nusky.iress01.model.Command;
import com.nusky.iress01.model.Point;
import com.nusky.iress01.model.Robot;
import com.nusky.iress01.model.Table;
import lombok.Builder;
import lombok.Data;
import lombok.extern.java.Log;

import static com.nusky.iress01.exception.BotError.INVALID_COMMAND;

@Builder
@Data
public class ToyBot {
    Table table;
    Robot robot;

    public void walk(Command command) {
        robot.setFirstAction(false);

        switch (command.getAction()) {
            case PLACE:
                Point point = command.getPoint();
                if (table.isValidPosition(point)) {
                    robot.setPoint(point);
                    robot.setCurrentDirection(command.getDirection());
                    System.out.printf("placed at %s. facing %s%n", robot.getPoint(), robot.getCurrentDirection());
                }
                break;
            case LEFT:
                robot.setCurrentDirection(robot.rotate(-1));
                System.out.printf("turned left. placed at %s. facing %s%n", robot.getPoint(), robot.getCurrentDirection());
                break;
            case RIGHT:
                robot.setCurrentDirection(robot.rotate(1));
                System.out.printf("turned right. placed at %s. facing %s%n", robot.getPoint(), robot.getCurrentDirection());
                break;
            case MOVE:
                Point newPosition = robot.getNextPosition();
                if (table.isNotValidPosition(newPosition)) {
                    System.out.printf("can't move. placed at %s. facing %s%n", robot.getPoint(), robot.getCurrentDirection());
                    break;
                }
                robot.setPoint(newPosition);
                System.out.printf("moved. placed at %s. facing %s%n", robot.getPoint(), robot.getCurrentDirection());
                break;
            case REPORT:
                robot.report();
                break;
            default:
                System.out.println("invalid command found");
                throw new BotException(INVALID_COMMAND);
        }
    }
}
