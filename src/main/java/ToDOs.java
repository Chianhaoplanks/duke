/**
 * ToDos is a type of class inherited from Task where there is no date to be followed,
 * and it is just a task that needs to be completed.
 * @author Aw Chian Hao
 */
public class ToDos extends Task{
    /**
     * Constructs the ToDos class using user input
     * @param description description of the ToDos Task
     * @throws InputException thrown if input format for ToDos is not followed
     */
    public ToDos(String description) throws InputException{
        super(description);
        if (description.isBlank()) {
            throw new InputException("OOPS!!! The description for todo cannot be empty");
        }
    }

    /**
     * Returns a line that is used when displaying the task in Duke during
     * list or when registering the task into Duke
     * @return String that displays the ToDos type, its status and description
     */
    public String toString() {
        return ("[T]" + super.toString());
    }
}
