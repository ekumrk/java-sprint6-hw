package tasks;

public class Task {
    protected int id;
    protected String title;
    protected String content;
    protected Status status = Status.NEW;

    public Task (String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {

        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Task, %s, %s, %s, %s", id, title, content, status );
    }
}
