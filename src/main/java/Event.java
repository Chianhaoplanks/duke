import java.time.LocalDateTime;

public class Event extends Task {
    protected LocalDateTime at;
    public Event(String description, LocalDateTime at) throws InputException{
        super(description);
        this.at = at;
    }
    public String toString() {
        return ("[E]" + super.toString() + " (at: " + at + ")");
    }
}
