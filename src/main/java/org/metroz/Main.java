package org.metroz;


import com.google.gson.JsonSyntaxException;
import org.metroz.io.JsonFileParser;
import org.metroz.io.UserInputParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    static String ASCII_ART = """
                 /\\  \\         /\\__\\                       /\\  \\         /\\  \\         /\\__\\   \s
                |::\\  \\       /:/ _/_         ___         /::\\  \\       /::\\  \\       /::|  |  \s
                |:|:\\  \\     /:/ /\\__\\       /\\__\\       /:/\\:\\__\\     /:/\\:\\  \\     /:/:|  |  \s
              __|:|\\:\\  \\   /:/ /:/ _/_     /:/  /      /:/ /:/  /    /:/  \\:\\  \\   /:/|:|  |__\s
             /::::|_\\:\\__\\ /:/_/:/ /\\__\\   /:/__/      /:/_/:/__/___ /:/__/ \\:\\__\\ /:/ |:| /\\__\\
             \\:\\~~\\  \\/__/ \\:\\/:/ /:/  /  /::\\  \\      \\:\\/:::::/  / \\:\\  \\ /:/  / \\/__|:|/:/  /
              \\:\\  \\        \\::/_/:/  /  /:/\\:\\  \\      \\::/~~/~~~~   \\:\\  /:/  /      |:/:/  /\s
               \\:\\  \\        \\:\\/:/  /   \\/__\\:\\  \\      \\:\\~~\\        \\:\\/:/  /       |::/  / \s
                \\:\\__\\        \\::/  /         \\:\\__\\      \\:\\__\\        \\::/  /        |:/  /  \s
                 \\/__/         \\/__/           \\/__/       \\/__/         \\/__/         |/__/ \s
            """;
    public static void main(String[] args) {
        System.out.println(ASCII_ART);
        System.out.println("Welcome to Metroz. A metro navigation application");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the json file name containing the metro network data:");
        try {
            JsonFileParser.initiateNetworkFromJsonFile(scanner.nextLine());
        } catch (IOException|URISyntaxException e) {
            System.out.println("The specified file could not be opened.");
            System.exit(0);
        } catch (JsonSyntaxException e) {
            System.out.println("The specified file has invalid syntax.");
            System.exit(0);
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
        boolean shutdown = false;
        while (!shutdown) {
            System.out.println("Enter a command, '/help' for help or CTRL+D to exit:");
            if (scanner.hasNext()) {
                try {
                    UserInputParser.parseCommand(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                shutdown = true;
            }
        }
        System.out.println("Goodbye!");
        scanner.close();
    }
}