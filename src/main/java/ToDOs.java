public class ToDos extends Task{
    public ToDos(String description) throws InputException{
        super(description);
        if (description.isBlank()) {
            throw new InputException("OOPS!!! The description for todo cannot be empty");
        }
    }
    public String toString() {
        return ("[T]" + super.toString());
    }
}
