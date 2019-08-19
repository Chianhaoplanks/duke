import java.util.Scanner;
public class Duke {
    public static void main(String[] args) {
        String[] CommandList = new String[100];
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);

        String greeting = "Hello! I'm Duke\n"
                + "What can I do for you?\n";

        System.out.println(greeting);

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
                        System.out.println("\t " + (i + 1) + ". " + CommandList[i]);
                    }
                }
                else {
                    CommandList[idx] = phrase;
                    System.out.println("\tadded: " + phrase);
                    idx++;
                }
            }
        }
    }

}
