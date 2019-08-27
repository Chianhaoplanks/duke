import java.time.LocalDateTime;

public class Deadline extends Task {
    protected LocalDateTime by;
    public Deadline(String description, LocalDateTime by) throws InputException{
        super(description);
        this.by = by;
    }
    public String toString() {
        return ("[D]" + super.toString() + " (by: " + by + ")");
    }
}

