package manager;

import tasks.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

       FileManager fileManager = new FileManager();

    public FileBackedTaskManager() throws IOException {
    }

    private void save() throws IOException {
        List<String> history = new ArrayList<>();
        try {
            try {
                for (Task e : getHistory()) {
                history.add(e.toString());
            }
            fileManager.writeToFile(history);
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения менеджером");
        }
    } catch (ManagerSaveException e) {
           e.getMessage();
       }

    }

    @Override
    public int createNewTask(Task task) throws IOException {
        super.createNewTask(task);
        save();
        return task.getId();
    }

    @Override
    public int createNewEpic(Epic epic) throws IOException {
        super.createNewEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public Integer createNewSubtask(Subtask subtask) throws IOException {
        super.createNewSubtask(subtask);
        save();
        return subtask.getId();
    }

    private String toString (Task task) {
        return task.toString();
    }
    private Task fromString(String value) {
        String[] stringToTask = value.split(",");
        switch (stringToTask[0]) {
            case "Task":
                Task task = new Task(stringToTask[2], stringToTask[3]);
                return task;
            case "Epic":
                Epic epic = new Epic(stringToTask[2], stringToTask[3]);
                return epic;
            case "Subtask":
                Subtask subtask = new Subtask(stringToTask[2], stringToTask[3], Integer.parseInt(stringToTask[5]));
                return subtask;
        }
        return null;
    }

    static String historyToString(HistoryManager manager) {
        List<Task> tasksHistory = manager.getHistory();
        StringBuilder builder = new StringBuilder();
        for (Task t : tasksHistory) {
           builder.append(t.getId() + ", ");
        }
        return builder.toString();
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> taskIds = new ArrayList<>();
        for (String v : value.split(", ")) {
            taskIds.add(Integer.parseInt(v));
        }
        return taskIds;
    }
}
