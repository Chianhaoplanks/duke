import java.util.Scanner;
import java.util.StringTokenizer;
public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        String greeting = "Hello! I'm Duke\n"
                + "What can I do for you?\n";

        System.out.println(greeting);

        Task[] CommandList = new Task[100];
        Scanner userInput = new Scanner(System.in);
        boolean goodbye = false;
        int idx = 0;
        while (!goodbye) {
            if (userInput.hasNextLine()) {
                String phrase = userInput.nextLine();
                if (phrase.equals("bye")){
                    System.out.println("\tBye. Hope to see you again soon!");
                    goodbye = true;
                }
                else if (phrase.equals("list")) {
                    System.out.println("\tHere are all the tasks in your list: ");
                    //List out all the words in CommandList;
                    for (int i = 0; CommandList[i] != null; i++){
                        System.out.println("\t " + (i + 1) + ". " + '[' + CommandList[i].getStatusIcon() + "] " + CommandList[i].description);
                    }
                }
                else if (phrase.contains("done ")) {
                    System.out.println("\tNice! I've marked this task as done:");
                    String cmd = phrase;
                    String[] donecmd = cmd.split(" ");
                    int listNo = Integer.parseInt(donecmd[1]) - 1;
                    if (CommandList[listNo] != null) {
                        CommandList[listNo].MarkasDone();
                    }
                    System.out.println("\t[" + CommandList[listNo].getStatusIcon() + "] " + CommandList[listNo].description);
                }
                else {
                    Task t = new Task(phrase);
                    System.out.println("\tadded: " + phrase);
                    CommandList[idx] = t;
                    idx++;
                }
            }
        }
    }

}
