import java.lang.Enum;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

enum update{
    DONE, REMOVE,
}

public class Duke {
    public static void main(String[] args) throws Exception {
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
        boolean goodbye = false;
        while (!goodbye) {
            try {
                if (userInput.hasNextLine()) {
                    String phrase = userInput.nextLine();
                    //says bye and exits the program
                    if (phrase.equals("bye")) {
                        System.out.println("\tBye. Hope to see you again soon!");
                        goodbye = true;
                    }
                    //ToDos task type
                    else if (phrase.startsWith("todo")) {
                        String[] todotask = phrase.split(" ", 2);
                        if (todotask.length < 2) {
                            throw new InputException("OOPS!!! The description for todo cannot be empty");
                        } else {
                            ToDos tds = new ToDos(todotask[1]);
                            CommandList.add(tds);
                            System.out.println("\tGot it. I've added this task: ");
                            System.out.println("\t\t" + tds.toString());
                            System.out.println(storage.numberofTasks(CommandList.size()));
                            storage.addtoFile(tds);
                        }
                    }
                    //Deadline task type
                    else if (phrase.startsWith("deadline")) {
                        String[] cmd = phrase.split("deadline ", 2);
                        if (cmd[1].isBlank() || !cmd[1].contains("/by") || cmd.length < 2) {
                            throw new InputException("\tOOPS!!! The description for deadline cannot be empty!");
                        }
                        String[] dltask = cmd[1].split(" /by ");
                        if (dltask.length < 2) {
                            throw new InputException("eh your deadline by when arh LOL");
                        }
                        else if (dateFormatValid(dltask[1])){
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                            LocalDateTime ldt = LocalDateTime.parse(dltask[1], formatter);
                            Deadline dls = new Deadline(dltask[0], ldt);
                            CommandList.add(dls);
                            System.out.println("\tGot it. I've added this task: ");
                            System.out.println("\t\t" + dls.toString());
                            System.out.println(storage.numberofTasks(CommandList.size()));
                            storage.addtoFile(dls);
                        }
                    }
                    //Event task type
                    else if (phrase.startsWith("event")) {
                        String[] cmd = phrase.split("event ", 2);
                        if (cmd[1].isBlank() || !cmd[1].contains("/at") || cmd.length < 2) {
                            throw new InputException("\tOOPS!!! The description for event cannot be empty!");
                        }
                        String[] evtask = cmd[1].split(" /at ");
                        if (evtask.length < 2) {
                            throw new InputException("eh your event when arh LOL");
                        }
                        else if (dateFormatValid(evtask[1])) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
                            LocalDateTime ldt = LocalDateTime.parse(evtask[1], formatter);
                            Event evs = new Event(evtask[0], ldt);
                            CommandList.add(evs);
                            System.out.println("\tGot it. I've added this task: ");
                            System.out.println("\t\t" + evs.toString());
                            System.out.println(storage.numberofTasks(CommandList.size()));
                            storage.addtoFile(evs);
                        }
                    }
                    //list out all the tasks
                    else if (phrase.equals("list")) {
                        System.out.println("\tHere are all the tasks in your list: ");
                        storage.readFile();
                    }
                    //mark a task/event as done
                    else if (phrase.contains("done ")) {
                        System.out.println("\tNice! I've marked this task as done:");
                        String cmd = phrase;
                        String[] donecmd = cmd.split(" ");
                        int listNo = Integer.parseInt(donecmd[1]) - 1;
                        if (CommandList.get(listNo) != null) {
                            storage.UpdateFile(CommandList.get(listNo), update.DONE);
                            CommandList.get(listNo).MarkasDone();
                        }
                        System.out.println('\t' + CommandList.get(listNo).toString());
                    }
                    else if (phrase.contains("find ")) {
                        System.out.println("\tHere are the matching tasks in your list:");
                        String[] cmd = phrase.split(" ", 2);
                        if (cmd.length < 2) {
                            throw new InputException("\tOOPS!!! keyword(s) not found!");
                        }
                        else {
                            int idx = 0;
                            for (Task task : CommandList) {
                                if (task.toString().contains(cmd[1])) {
                                    idx++;
                                    System.out.println("\t" + idx + ". " + task.toString());
                                }
                            }
                        }
                    }
                    //if input is incorrect show error msg
                    else {
                        System.out.println("\tOOPS!!! I'm sorry I don't know what that means :-(");
                    }
                }
            }
            catch(InputException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    static Boolean dateFormatValid(String line) {
        String value = "dd/MM/yyyy HHmm";
        LocalDateTime ldt = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(value);
        try {
            ldt = LocalDateTime.parse(line, formatter);
            return true;
        }
        catch (DateTimeParseException e) {
            System.out.println("Date format not valid. Please try again :)");
            e.getStackTrace();
            return false;
        }
    }
}
