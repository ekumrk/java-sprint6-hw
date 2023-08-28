import manager.InMemoryTaskManager;
import tasks.*;

public class Main {
    public static void main(String[] args) {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task;
        Epic epic;
        Subtask subtask;


        //Тестирование
        // 1. создайте две задачи, эпик с тремя подзадачами и эпик без подзадач
        task = new Task("Почитать книгу", "Гарри Поттер");
        inMemoryTaskManager.createNewTask(task);
        task = new Task("Посмотреть фильм", "Звёздные воины");
        inMemoryTaskManager.createNewTask(task);

        epic = new Epic("Сходить в магазин", "Сделать покупки");
        inMemoryTaskManager.createNewEpic(epic);
        subtask = new Subtask("Купить помидоры", "3 кг", 3);
        inMemoryTaskManager.createNewSubtask(subtask);
        subtask = new Subtask("Купить яблоки", "2 кг", 3);
        inMemoryTaskManager.createNewSubtask(subtask);
        subtask = new Subtask("Купить огурцы", "2 кг", 3);
        inMemoryTaskManager.createNewSubtask(subtask);

        epic = new Epic("Сходить в культурные места", "Театры");
        inMemoryTaskManager.createNewEpic(epic);

        //запросите созданные задачи несколько раз в разном порядке;
        //после каждого запроса выведите историю и убедитесь, что в ней нет повторов;
        inMemoryTaskManager.getTaskFromId(1);
        inMemoryTaskManager.getTaskFromId(2);
        inMemoryTaskManager.getTaskFromId(1);
        inMemoryTaskManager.getHistory();

        inMemoryTaskManager.getTaskFromId(2);
        inMemoryTaskManager.getSubtaskFromId(4);
        inMemoryTaskManager.getEpicFromId(3);
        inMemoryTaskManager.getEpicFromId(7);
        inMemoryTaskManager.getHistory();

        inMemoryTaskManager.getSubtaskFromId(4);
        inMemoryTaskManager.getEpicFromId(7);
        inMemoryTaskManager.getTaskFromId(1);
        inMemoryTaskManager.getSubtaskFromId(5);
        inMemoryTaskManager.getHistory();

        //удалите задачу, которая есть в истории, и проверьте, что при печати она не будет выводиться;
        inMemoryTaskManager.deleteTaskFromId(1);
        inMemoryTaskManager.getHistory();

        //удалите эпик с тремя подзадачами и убедитесь, что из истории удалился как сам эпик, так и все его подзадачи.
        inMemoryTaskManager.deleteEpicFromId(3);
        inMemoryTaskManager.getHistory();
    }
}
