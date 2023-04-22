package org.metroz.io;

import org.metroz.command.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInputParser {
    // the pattern of the user input where parts are separated by space. in case the part contains a space, it is surrounded with quotes.
    // e.g. /add-head lineX "Karl Marx"
    static Pattern USER_INPUT_PATTERN = Pattern.compile("((\"(.+?)\")|(\\S+))");

    /**
     * parses a user input line and tries to execute the command
     * @param userInputString user input line
     * @throws IllegalArgumentException in case of invalid commands or arguments
     */
    public static void parseCommand(String userInputString) {
        Matcher matcher = USER_INPUT_PATTERN.matcher(userInputString);
        List<String> inputParts = new ArrayList<>();
        // in case of a match
        while (matcher.find()) {
            // check the group with quotes. if null add the group without quotes
            inputParts.add(matcher.group(3) == null ?
                    matcher.group(4) : matcher.group(3));
        }
        // the command keyword is the first part
        String commandKeyword = inputParts.get(0);
        Optional<Command> command = Command.getCommandByKeyword(commandKeyword);
        if (command.isPresent()) {
            // exclude command name
            List<String> commandArgs = inputParts.subList(1, inputParts.size());
            command.get().execute(commandArgs);
            System.out.println("Command executed successfully!");
        } else {
            throw new IllegalArgumentException("Invalid command: " + commandKeyword);
        }
    }
}
