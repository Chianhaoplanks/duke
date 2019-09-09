/**
 * Abstract Class that is the parent class for ToDos, Event, and Deadline
 * Variables consist of the description and status of the task
 * @author Aw Chian Hao
 */
public class Task {
    protected String description;
    protected boolean isDone;
    /**
     * Constructs the task. Status isDone is set to false as task has yet
     * to be completed upon entering it into Duke
     * @param description description of the task
     * @throws InputException thrown when description passed into constructor is blank
     */
    public Task(String description) throws InputException{
        if (description.isBlank()){
            throw new InputException("error error");
        }
        else {
            this.description = description;
            this.isDone = false;
        }
    }

    /**
     * Returns a character representation to see if a Task has been completed
     * @return a string represented by a tick if done and a cross otherwise
     */
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }

    /**
     * Set isDone to be true.
     */
    public void MarkasDone() {
        this.isDone = true;
    }

    /**
     * Returns the Task's status and description.
     * @return a line showing the Task's status and description
     */
    public String toString() {
        return ("[" + getStatusIcon() + "] " + description);
    }
}
