import java.time.LocalDateTime;

/**
 * Event is a type of class inherited from Task and records the date and time at which
 * the event is held.
 * @author Aw Chian Hao
 */
public class Event extends Task {
    protected LocalDateTime at;
    /**
     * Constructs the Event class using user input.
     * @param description the description of the event
     * @param at the date and time at which the event is held
     * @throws InputException if format of user input is not "event (description) /at (datetime)"
     */
    public Event(String description, LocalDateTime at) throws InputException{
        super(description);
        this.at = at;
    }

    /**
     * Returns a line that is used when displaying the task in Duke during
     * list or when registering the task into Duke.
     * @return String that displays the Event type, its status and description
     */
    public String toString() {
        return ("[E]" + super.toString() + " (at: " + at + ")");
    }
}
