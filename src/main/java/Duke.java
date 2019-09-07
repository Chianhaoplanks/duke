import java.lang.Enum;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Duke {
    public static void main(String[] args) {
        //Greetings
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        String greeting = "Hello! I'm Duke\n"
                + "What can I do for you?\n";

        System.out.println(greeting);
        //Taking in user input
        Storage storage = new Storage("/Users/chianhaoaw/Documents/GitHub/duke/src/main/duke.txt");
        ArrayList<Task> CommandList = storage.load();
        Scanner userInput = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(userInput, storage);
        inputHandler.HandleInput(CommandList);
    }
}