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

/**
 * Duke is an interactive checklist where tasks, deadlines and events can be recorded
 * and updated through user input.
 * @author Aw Chian Hao
 */
public class Duke {
    public static void main(String[] args) {
        /**
         * The opening logo and greetings from Duke is printed out here.
         */
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        String greeting = "Hello! I'm Duke\n"
                + "What can I do for you?\n";

        System.out.println(greeting);
        /**
         * Where in the local hard disk Duke writes to, how to handle user input and how the
         * program runs are done here
         * */
        Storage storage = new Storage("/Users/chianhaoaw/Documents/GitHub/duke/src/main/duke.txt");
        ArrayList<Task> CommandList = storage.load();
        Scanner userInput = new Scanner(System.in);
        InputHandler inputHandler = new InputHandler(userInput, storage);
        inputHandler.HandleInput(CommandList);
    }
}