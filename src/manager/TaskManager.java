package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    public int createNewTask(Task task) throws IOException;

    public void updateTask(Task task);

    public List<Task> getTaskList();

    public void deleteAllTasks();

    public Task getTaskFromId(int id);

    public void deleteTaskFromId(int id);

    public int createNewEpic(Epic epic) throws IOException;

    public ArrayList<Epic> getEpicList();

    public Epic getEpicFromId(int id);

    public ArrayList<Subtask> getSubtasksFromEpicId (int id);

    public void deleteAllEpics();

    public void deleteEpicFromId(int id);

    public Integer createNewSubtask(Subtask subtask) throws IOException;


    public ArrayList<Subtask> getSubtaskList();

    public void deleteAllSubtasks();

    public void updateEpicStatus(Epic epic);

    public Subtask getSubtaskFromId(int id);
    public void deleteSubtaskFromId(int id);

    public void updateSubtask (Subtask subtask);

    public List<Task> getHistory();
}
