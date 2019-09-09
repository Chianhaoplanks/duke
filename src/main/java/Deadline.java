import java.time.LocalDateTime;

/**
 * Deadline is a class inherited from Task that records the date and time at which the
 * the task needs to be completed by.
 * @author Aw Chian Hao
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Constructs the Deadline class using user input.
     * @param description the description of what is due
     * @param by the date and time at which the deadline is due
     * @throws InputException if format of user input is not "deadline (description) /by (datetime)"
     */
    public Deadline(String description, LocalDateTime by) throws InputException{
        super(description);
        this.by = by;
    }

    /**
     * Returns a line that is used when displaying the task in Duke during
     * list or when registering the task into Duke.
     * @return String that displays the Deadline type, its status and description
     */
    public String toString() {
        return ("[D]" + super.toString() + " (by: " + by + ")");
    }
}

