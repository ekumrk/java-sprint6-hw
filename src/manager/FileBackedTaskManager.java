package manager;

import tasks.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private static final String HOME = System.getProperty("user.home");
    private String fileName;
    private final Path historyFile = Paths.get(HOME, fileName);

    HistoryManager historyManager = Managers.getDefaultHistory();

    public FileBackedTaskManager(String fileName) throws IOException {
        this.fileName = fileName;
        readListFromFile();
    }

    private void save() throws IOException {
        try {
            List<String> allTasksToString = new ArrayList<>();
            for (Task task : tasks.values()) {
                allTasksToString.add(task.toString());
            }
            for (Epic epic : epics.values()) {
                allTasksToString.add(epic.toString());
            }
            for (Subtask subtask : subtasks.values()) {
                allTasksToString.add(subtask.toString());
            }
            List<String> historyToString = Collections.singletonList(historyToString(historyManager));
            writeToFile(allTasksToString, historyToString);
        } catch (ManagerSaveException e) {
            System.out.println("Ошибка сохранения в файл");
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

    public void readListFromFile() throws IOException {
        if (Files.size(historyFile) != 0) {
            try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(historyFile),
                    StandardCharsets.UTF_8))) {

                while (br.ready()) {
                    String lines = br.readLine();
                    if (!(lines.isBlank())) {
                        String[] line = lines.split(",");
                        if (line[1].equals(TaskTypes.TASK.toString())) {
                            Task task = fromString(Arrays.toString(line));
                            super.createNewTask(task);
                        } else if (line[1].equals(TaskTypes.EPIC.toString())) {
                            Epic epic = (Epic) fromString(Arrays.toString(line));
                            super.createNewEpic(epic);
                        } else if (line[1].equals(TaskTypes.SUBTASK.toString())) {
                            Subtask subtask = (Subtask) fromString(Arrays.toString(line));
                            super.createNewSubtask(subtask);
                        } else if (!(line[1].equals(TaskTypes.TASK.toString()) && line[1].equals(TaskTypes.EPIC.toString())
                                && line[1].equals(TaskTypes.SUBTASK.toString()) && line[1].equals("[id"))) {
                            List<Integer> historyId = historyFromString(Arrays.toString(line));
                            for (Integer e : historyId) {
                                if (tasks.containsKey(e)) {
                                    Task task = tasks.get(e);
                                    historyManager.addToHistoryTask(task);
                                } else if (epics.containsKey(e)) {
                                    Epic epic = epics.get(e);
                                    historyManager.addToHistoryTask(epic);
                                } else if (subtasks.containsKey(e)) {
                                    Subtask subtask = subtasks.get(e);
                                    historyManager.addToHistoryTask(subtask);
                                }
                            }
                        }
                    }
                }

            } catch (IOException e) {
                System.out.println("Произошла ошибка во время чтения файла.");

            }
        }
    }

    public void writeToFile(List<String> tasks, List<String> history) throws IOException {
        try (FileWriter writer = new FileWriter(String.valueOf(historyFile), StandardCharsets.UTF_8)) {
            writer.write("id,type,name,status,description,epicID \n");
            for (String element : tasks) {
                writer.write(element + "\n");
            }
            writer.write("\n");
            for (String element : history) {
                writer.write(element + ",");
            }
        }
    }

    public Task fromString(String value) {
        String[] stringToTask = value.split(",");
        if (stringToTask[0].equals(TaskTypes.TASK.toString())) {
            Task task = new Task(stringToTask[2], stringToTask[3]);
            return task;
        } else if (stringToTask[0].equals(TaskTypes.EPIC.toString())) {
            Epic epic = new Epic(stringToTask[2], stringToTask[3]);
            return epic;
        } else if (stringToTask[0].equals(TaskTypes.SUBTASK.toString())) {
            Subtask subtask = new Subtask(stringToTask[2], stringToTask[3], Integer.parseInt(stringToTask[5]));
            return subtask;
        }
        return null;
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> taskIds = new ArrayList<>();
        for (String v : value.split(", ")) {
            taskIds.add(Integer.parseInt(v));
        }
        return taskIds;
    }

    static String historyToString(HistoryManager manager) {
        List<Task> tasksHistory = manager.getHistory();
        StringBuilder builder = new StringBuilder();
        for (Task t : tasksHistory) {
            builder.append(t.getId() + ", ");
        }
        return builder.toString();
    }
}
