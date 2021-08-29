package com.nusky.iress01.model;

import com.nusky.iress01.exception.BotError;
import com.nusky.iress01.exception.BotException;
import com.nusky.iress01.model.enums.Direction;
import lombok.Builder;
import lombok.Getter;

import static com.nusky.iress01.exception.BotError.INVALID_COMMAND;
import static com.nusky.iress01.model.enums.Action.PLACE;

@Getter
public class Robot {
    private final SquareTable table;
    private Direction currentDirection; // represents the direction bot is faced at the moment
    private Point point;
    private boolean isFirstAction = true;

    @Builder
    public Robot(SquareTable table, Direction currentDirection, Point point) {
        this.table = table;
        this.currentDirection = currentDirection;
        this.point = point;
    }

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
     * @param clockwise if false, turn left. if true, turn right
     * @return direction after turning to the given side
     */
    public Direction rotate(boolean clockwise) {
        int numberOfDirections = Direction.numberOfDirections();
        int towards = clockwise ? 1 : -1;
        //could be a negative value if you turn left from north.
        int signedDirection = currentDirection.getIndex() + towards;

        if (signedDirection < 0) {
            return Direction.fromIndex(numberOfDirections - Math.abs(towards));
        }
        return Direction.fromIndex(signedDirection % numberOfDirections);
    }

    public void walk(Command command) {
        if (this.isFirstAction() && (PLACE != command.getAction() || table.isNotValidPosition(command.getPoint()))) {
            System.out.println("first command should be a valid PLACE. i.e: PLACE 3,4,WEST");
            return;
        }

        isFirstAction = false;

        switch (command.getAction()) {
            case PLACE:
                Point point = command.getPoint();
                if (table.isValidPosition(point)) {
                    this.point = point;
                    currentDirection = command.getDirection();
                    System.out.printf("placed at %s. facing %s%n", this.getPoint(), this.getCurrentDirection());
                }
                break;
            case LEFT:
                currentDirection = rotate(false);
                System.out.printf("turned left. placed at %s. facing %s%n", this.getPoint(), this.getCurrentDirection());
                break;
            case RIGHT:
                currentDirection = this.rotate(true);
                System.out.printf("turned right. placed at %s. facing %s%n", this.getPoint(), this.getCurrentDirection());
                break;
            case MOVE:
                Point newPosition = this.getNextPosition();
                if (table.isNotValidPosition(newPosition)) {
                    System.out.printf("can't move. placed at %s. facing %s%n", this.getPoint(), this.getCurrentDirection());
                    break;
                }
                this.point = newPosition;
                System.out.printf("moved. placed at %s. facing %s%n", this.getPoint(), this.getCurrentDirection());
                break;
            case REPORT:
                this.report();
                break;
            default:
                System.out.println("invalid command found");
                throw new BotException(INVALID_COMMAND);
        }
    }
}
