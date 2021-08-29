package com.nusky.iress01.model;

import com.nusky.iress01.exception.BotError;
import com.nusky.iress01.exception.BotException;
import com.nusky.iress01.model.enums.Direction;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Robot {
    private Direction currentDirection; // represents the direction bot is faced at the moment
    private Point point;
    private boolean isFirstAction = true;

    /**
     * @return the next position after moving one step forward towards the currently facing direction
     * from the current point
     */
    public Point getNextPosition() {
        Point newPoint = Point.builder().x(point.getX()).y(point.getY()).build();

        switch (currentDirection) {
            case NORTH:
                newPoint.setY(point.getY() + 1);
                break;
            case WEST:
                newPoint.setX(point.getX() - 1);
                break;
            case SOUTH:
                newPoint.setY(point.getY() - 1);
                break;
            case EAST:
                newPoint.setX(point.getX() + 1);
                break;
            default:
                throw new BotException(BotError.INVALID_POSITION);
        }
        return newPoint;
    }

    public String report() {
        String reporter = String.format("%d,%d,%s", point.getX(), point.getY(), getCurrentDirection());
        System.out.println(reporter);
        return reporter;
    }

    /**
     * @param towards negative to turn left. Positive to turn right
     * @return direction after turning to the given side
     */
    public Direction rotate(int towards) {
        int numberOfDirections = Direction.numberOfDirections();
        int signedDirection = Objects.isNull(currentDirection) ? 0 : currentDirection.getIndex() + towards;

        if (signedDirection < 0) {
            return Direction.fromIndex(numberOfDirections - Math.abs(towards));
        }
        return Direction.fromIndex(signedDirection % numberOfDirections);
    }
}
