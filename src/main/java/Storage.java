import java.lang.Enum;
import java.io.*;
import java.nio.Buffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Storage {
    private static String filepath;
    private static File file;
    public Storage (String filepath) {
        this.filepath = filepath;
        this.file = new File(filepath);
    }
    public static ArrayList<Task> load() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filepath));
            String line;
            ArrayList<Task> list = new ArrayList<Task>(100);
            while ((line = br.readLine()) != null) {
                String listedtask = line.trim();
                String[] task = listedtask.split(" ", 3);
                String value = "dd/MM/yyyy HHmm";
                LocalDateTime ldt = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(value);
                switch (task[0]) {
                    //Done first then undone
                    case "[T][\u2713]":
                        Task td = new ToDos(task[1]);
                        td.MarkasDone();
                        list.add(td);
                        break;
                    case "[T][\u2718]":
                        Task todo = new ToDos(task[1]);
                        list.add(todo);
                        break;
                    case "[D][\u2713]":
                        ldt = LocalDateTime.parse(task[2].substring(5, task[2].length() - 1));
                        Task dl = new Deadline(task[1], ldt);
                        dl.MarkasDone();
                        list.add(dl);
                        break;
                    case "[D][\u2718]":
                        ldt = LocalDateTime.parse(task[2].substring(5, task[2].length() - 1));
                        Task deadline = new Deadline(task[1], ldt);
                        list.add(deadline);
                        break;
                    case "[E][\u2713]":
                        ldt = LocalDateTime.parse(task[2].substring(5, task[2].length() - 1));
                        Task event = new Event(task[1], ldt);
                        event.MarkasDone();
                        list.add(event);
                        break;
                    case "[E][\u2718]":
                        ldt = LocalDateTime.parse(task[2].substring(5, task[2].length() - 1));
                        Task ev = new Event(task[1], ldt);
                        list.add(ev);
                        break;
                }
            }
            return list;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InputException e) {
            e.printStackTrace();
        }
        return new ArrayList<Task>(100);
    }
    public static void addtoFile(Task task) {
        try {
            FileWriter writer  = new FileWriter(file, true);
            writer.write("\t" + task.toString() + "\n");
            writer.close();
        }
        catch(IOException error) {
            System.err.format("IOException: %s%n", error);
        }
    }
    public static void readFile() {
        try {
            FileReader reader  = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String trimline = line.trim();
                i++;
                System.out.println("\t\t" + (i) + ". " + trimline);
            }
            reader.close();
        }
        catch(IOException error) {
            System.err.format("IOException: %s%n", error);
        }
    }
    public static void UpdateFile(Task task, update Update) {
        try {
            Boolean ammended = false;
            File tempFile = new File(file.getAbsolutePath() + ".tmp");
            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter pw = new BufferedWriter(new FileWriter(tempFile));
            String line;
            while ((line = br.readLine()) != null) {
                String trimline = line.trim();
                if (trimline.equals(task.toString()) && !ammended) {
                    switch (Update) {
                        case DONE:
                            Task donetask = task;
                            donetask.MarkasDone();
                            pw.write("\t" + donetask.toString() + "\n");
                        case REMOVE:
                            continue;
                    }
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

    public static String numberofTasks(int idx) {
        return ("\tNow you have " + idx + " tasks in the list.");
    }
}
