package com.nusky.iress01;


import com.nusky.iress01.model.Command;
import com.nusky.iress01.model.Robot;
import com.nusky.iress01.model.SquareTable;

import java.util.Scanner;

import static com.nusky.iress01.model.Command.parseCommand;
import static com.nusky.iress01.model.enums.Action.EXIT;

public class BotApp {
    public static void main(String[] args) {
        System.out.println("Stared the toy robot up. Start giving commands...");

        SquareTable table = SquareTable.builder().length(5).width(5).build();
        Robot robot = Robot.builder().table(table).build();

        Scanner console = new Scanner(System.in);
        String inputCommand = null;

        boolean isRunning = true;
        while (isRunning) {
            try {
                inputCommand = console.nextLine();
                Command command = parseCommand(inputCommand);

                if (EXIT == command.getAction()) {
                    isRunning = false;
                    continue;
                }
                robot.walk(command);
            } catch (Exception e) {
                System.out.printf("could not process the user input %s%n", inputCommand);
            }
        }
    }


}
