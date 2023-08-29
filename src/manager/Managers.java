package manager;

import tasks.Task;

import java.io.IOException;

public class Managers {

    public static HistoryManager getDefaultHistory() throws IOException {
        return new InMemoryHistoryManager();
    }
    public static TaskManager getDefault() throws IOException {
        return new InMemoryTaskManager();
    }
}
