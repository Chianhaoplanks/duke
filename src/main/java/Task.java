public class Task {
    protected String description;
    protected boolean isDone;
    public Task(String description) throws InputException{
        if (description.equals("")){
            throw new InputException("error error");
        }
        else {
            this.description = description;
            this.isDone = false;
        }
    }
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718");
    }
    public void MarkasDone() {
        this.isDone = true;
    }

    public String toString() {
        return ("[" + getStatusIcon() + "] " + description);
    }
}
