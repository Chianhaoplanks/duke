import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * enum Update is used to tell the method UpdateFile if the program should mark a task
 * as done or delete it from the file
 */
enum update{
    DONE, REMOVE;
}

/**
 * InputHandler handles the user input into Duke and what to do with it
 * @author Aw Chian Hao
 */
public class InputHandler {
    private Scanner userInput;
    private Boolean goodbye;
    private Storage storage;
    /**
     * Constructs InputHandler. Boolean variable goodbye determines if the Duke program should
     * continue receiving user's input
     * @param userInput type Scanner which is used to read the user's input to Duke
     * @param storage contains the file and filepath where Duke will write and update to in the program
     */
    public InputHandler(Scanner userInput, Storage storage) {
        this.userInput = userInput;
        this.storage = storage;
        this.goodbye = false;
    }

    /**
     * This method handles the user input depending on what the user's commands are.
     * This method will continuously receive input until the user enters "bye" to the program
     * whereby goodbye will turn true and the method exits the while loop
     * @param CommandList the ArrayList at which the tasks from the file are stored as a collection of
     *                    type Task
     */
    public void HandleInput(ArrayList<Task> CommandList) {
        while (!goodbye) {
            try {
                if (userInput.hasNextLine()) {
                    String phrase = userInput.nextLine();
                    /**
                     * Bids user goodbye and ends the program
                     */
                    if (phrase.equals("bye")) {
                        System.out.println("\tBye. Hope to see you again soon!");
                        goodbye = true;
                    }
                    /**
                     * Registering tasks of type ToDos
                     */
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
                    /**
                     * Registering tasks of type Deadline
                     */
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
                    /**
                     * Registering tasks of type Event
                     */
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
                    /**
                     * List out all the tasks written in the file
                     */
                    else if (phrase.equals("list")) {
                        System.out.println("\tHere are all the tasks in your list: ");
                        storage.readFile();
                    }
                    /**
                     * Mark a task/event/deadline as done
                     */
                    else if (phrase.contains("done ")) {
                        System.out.println("\tNice! I've marked this task as done:");
                        String[] donecmd = phrase.split(" ");
                        int listNo = Integer.parseInt(donecmd[1]) - 1;
                        if (CommandList.get(listNo) != null) {
                            storage.UpdateFile(CommandList.get(listNo), update.DONE);
                            CommandList.get(listNo).MarkasDone();
                            System.out.println('\t' + CommandList.get(listNo).toString());
                        }
                    }
                    /**
                     * Delete a task from the local hard disk
                     */
                    else if (phrase.contains("delete ")) {
                        System.out.println("\tNoted. I've removed the task:");
                        String[] deletecmd = phrase.split(" ");
                        int listNo = Integer.parseInt(deletecmd[1]) - 1;
                        if (CommandList.get(listNo) != null) {
                            storage.UpdateFile(CommandList.get(listNo), update.REMOVE);
                            System.out.println('\t' + CommandList.get(listNo).toString());
                            CommandList.remove(CommandList.get(listNo));
                        }
                    }
                    /**
                     * List out all the tasks that contain certain keyword(s)
                     */
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
                    /**
                     * If the input does not adhere to the above formats, print out an error message
                     * and continue running the program
                     */
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

    /**
     * This method checks if the format for inputting the date and time for Event/Deadline is valid
     * @param line String of input that is to be assessed
     * @return returns false if the format of the line is invalid
     */
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
