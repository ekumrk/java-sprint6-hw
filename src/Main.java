import manager.FileBackedTaskManager;
import tasks.*;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {


        FileBackedTaskManager f = new FileBackedTaskManager("//dev//java-sprint6-hw//resources//history.csv");
        Task task;
        Epic epic;
        Subtask subtask;


        //Тестирование
        // 1. создайте две задачи, эпик с тремя подзадачами и эпик без подзадач
        task = new Task("Почитать книгу", "Гарри Поттер");
        f.createNewTask(task);
        task = new Task("Посмотреть фильм", "Звёздные воины");
        f.createNewTask(task);

        epic = new Epic("Сходить в магазин", "Сделать покупки");
        f.createNewEpic(epic);
        subtask = new Subtask("Купить помидоры", "3 кг", 3);
        f.createNewSubtask(subtask);
        subtask = new Subtask("Купить яблоки", "2 кг", 3);
        f.createNewSubtask(subtask);
        subtask = new Subtask("Купить огурцы", "2 кг", 3);
        f.createNewSubtask(subtask);

        epic = new Epic("Сходить в культурные места", "Театры");
        f.createNewEpic(epic);

        //запросите созданные задачи несколько раз в разном порядке;
        //после каждого запроса выведите историю и убедитесь, что в ней нет повторов;
        f.getTaskFromId(1);
        f.getHistory();
        f.getTaskFromId(2);
        f.getTaskFromId(1);
        f.getHistory();

        f.getTaskFromId(2);
        f.getSubtaskFromId(4);
        f.getEpicFromId(3);
        f.getEpicFromId(7);
        f.getHistory();

        f.getSubtaskFromId(4);
        f.getEpicFromId(7);
        f.getTaskFromId(1);
        f.getSubtaskFromId(5);
        f.getHistory();

        //удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться;
        f.deleteTaskFromId(1);
        f.getHistory();

        //удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
        f.deleteEpicFromId(3);
        f.getHistory();
    }
}
