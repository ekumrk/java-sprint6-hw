package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import tasks.*;

public class FileManager {
    private static final String HOME = System.getProperty("user.home");
    private String filename;

    Path historyFile = Paths.get(HOME, filename);
    FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

    public FileManager(String filename) throws IOException {
        this.filename = filename;
        readListFromFile();
    }

    public void readListFromFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(historyFile), StandardCharsets.UTF_8))) {

            while (br.ready()) {
                String lines = br.readLine();
                String[] line = lines.split(",");
                if (line[1].equals(TaskTypes.TASK)) {
                    Task task = fileBackedTaskManager.fromString(Arrays.toString(line));
                    fileBackedTaskManager.createNewTask(task);
                } else if (line[1].equals(TaskTypes.EPIC)) {
                    Epic epic = (Epic) fileBackedTaskManager.fromString(Arrays.toString(line));
                    fileBackedTaskManager.createNewEpic(epic);
                } else if (line[1].equals(TaskTypes.SUBTASK)) {
                    Subtask subtask = (Subtask) fileBackedTaskManager.fromString(Arrays.toString(line));
                    fileBackedTaskManager.createNewSubtask(subtask);
                } else if (line[1].isBlank()) {
                    continue;
                } else {
                    fileBackedTaskManager.historyFromString(Arrays.toString(line));
                }
            }


        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла.");

        }
    }

    public void writeToFile(List<String> tasks, List<String> history) throws IOException {
        try (FileWriter writer = new FileWriter(String.valueOf(historyFile), StandardCharsets.UTF_8)) {
            writer.write("id,type,name,status,description,epic \n");
            for (String element : tasks) {
                writer.write(element + "\n");
            }
            writer.write("\n");
            for (String element : history) {
                writer.write(element + ",");
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
    }
}
