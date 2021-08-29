package com.nusky.iress01;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class BotAppTest {
    private ByteArrayOutputStream outputStream;

    private static Stream<Arguments> inputSequenceAndExpectedOutput() {
        // make sure expected output has the line break at the end because we are using println to print
        return Stream.of(
                Arguments.of("report\nright\nreport\nexit", "first command should be a valid PLACE\n"),
                Arguments.of("place 3,4,west\nright\nreport\nexit", "3,4,NORTH\n"),
                Arguments.of("place 3,4,west\nright\nright\nright\nright\nreport\nexit", "3,4,WEST\n"),
                Arguments.of("place 3     ,4,west\nright\nreport\nexit", "first command should be a valid PLACE\n")
        );
    }

    @BeforeEach
    public void setup() {
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream); //overriding the standard output to be able to assert the output printed to the console
    }

    @ParameterizedTest
    @MethodSource("inputSequenceAndExpectedOutput")
    public void output_test(String inputSequence, String expectedOutput) {
        System.setIn(new ByteArrayInputStream(inputSequence.getBytes()));
        BotApp.main(new String[0]);

        String outputText = outputStream.toString();

        assertTrue(outputText.endsWith(expectedOutput));
    }

}
