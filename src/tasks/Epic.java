package tasks;

import java.util.ArrayList;

public class Epic extends Task {

    protected ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String title, String content) {
        super(title, content);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void setStatus(Status status) {

        this.status = status;
    }
}
