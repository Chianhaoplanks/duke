import java.nio.Buffer;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
        Task[] CommandList = new Task[100];
        Scanner userInput = new Scanner(System.in);
        boolean goodbye = false;
        int idx = 0;
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
                            CommandList[idx] = tds;
                            idx++;
                            System.out.println("\tGot it. I've added this task: ");
                            System.out.println("\t\t" + tds.toString());
                            System.out.println(numberofTasks(idx));
                            addtoFile(tds);
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
                            CommandList[idx] = dls;
                            idx++;
                            System.out.println("\tGot it. I've added this task: ");
                            System.out.println("\t\t" + dls.toString());
                            System.out.println(numberofTasks(idx));
                            addtoFile(dls);
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
                            CommandList[idx] = evs;
                            idx++;
                            System.out.println("\tGot it. I've added this task: ");
                            System.out.println("\t\t" + evs.toString());
                            System.out.println(numberofTasks(idx));
                            addtoFile(evs);
                        }
                    }
                    //list out all the tasks
                    else if (phrase.equals("list")) {
                        System.out.println("\tHere are all the tasks in your list: ");
                        readfromFile();
                    }
                    //mark a task/event as done
                    else if (phrase.contains("done ")) {
                        System.out.println("\tNice! I've marked this task as done:");
                        String cmd = phrase;
                        String[] donecmd = cmd.split(" ");
                        int listNo = Integer.parseInt(donecmd[1]) - 1;
                        if (CommandList[listNo] != null) {
                            RemoveLinefromFile(CommandList[listNo]);
                            CommandList[listNo].MarkasDone();
                        }
                        System.out.println('\t' + CommandList[listNo].toString());
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
    static String numberofTasks(int idx) {
        return ("\tNow you have " + idx + " tasks in the list.");
    }


    private static void addtoFile(Task task) {
        File file = new File("/Users/chianhaoaw/Documents/GitHub/duke/src/main/duke.txt");
        try {
            FileWriter writer  = new FileWriter(file, true);
            writer.write("\t" + task.toString() + "\n");
            writer.close();
        }
        catch(IOException error) {
            System.err.format("IOException: %s%n", error);
        }
    }
    private static void readfromFile() {
        File file = new File("/Users/chianhaoaw/Documents/GitHub/duke/src/main/duke.txt");
        try {
            FileReader reader  = new FileReader(file);
            int i;
            while ((i = reader.read())!= -1) {
                System.out.print((char)i);
            }
            reader.close();
        }
        catch(IOException error) {
            System.err.format("IOException: %s%n", error);
        }
    }
    private static void RemoveLinefromFile(Task task) {
        try {
            Boolean ammended = false;
            File file = new File("/Users/chianhaoaw/Documents/GitHub/duke/src/main/duke.txt");
            File tempFile = new File(file.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader("/Users/chianhaoaw/Documents/GitHub/duke/src/main/duke.txt"));
            BufferedWriter pw = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = br.readLine()) != null) {
                String trimline = line.trim();
                if (trimline.equals(task.toString()) && !ammended) {
                    Task donetask = task;
                    donetask.MarkasDone();
                    pw.write("\t" + donetask.toString() + "\n");
                }
                else {
                    pw.write(line + "\n");
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            if (!file.delete()) {
                System.out.println("Cannot delete file");
                return;
            }
            if (!tempFile.renameTo(file)) {
                System.out.println("Could not rename file");
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
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
