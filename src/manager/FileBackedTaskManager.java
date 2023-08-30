package manager;

import tasks.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

       private final FileManager fileManager = new FileManager("dev/java-sprint6-hw/resources/history.csv");
       //при инициализации в конструктор сразу запускается readListFromFile()

    public FileBackedTaskManager() throws IOException {
    }

    private void save() throws ManagerSaveException {
        try {
            List<String> allTasksToString = new ArrayList<>();
            for (Task task : allTasks) {
                allTasksToString.add(task.toString());
            }
            List<String> historyToString = List.of(getHistory().toString());
            fileManager.writeToFile(allTasksToString, historyToString);
        } catch (ManagerSaveException e) {
           e.getMessage();
       } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения менеджером");
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

    public Task fromString(String value) {
        String[] stringToTask = value.split(",");
        if (stringToTask[0].equals(TaskTypes.TASK)) {
            Task task = new Task(stringToTask[2], stringToTask[3]);
            return task;
        } else if (stringToTask[0].equals(TaskTypes.EPIC)) {
            Epic epic = new Epic(stringToTask[2], stringToTask[3]);
            return epic;
        } else if (stringToTask[0].equals(TaskTypes.SUBTASK)) {
                Subtask subtask = new Subtask(stringToTask[2], stringToTask[3], Integer.parseInt(stringToTask[5]));
                return subtask;
        }
        return null;
    }

    //я запутался, где применить этот метод(
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
