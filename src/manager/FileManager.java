package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final String HOME = System.getProperty("user.home");

    Path historyFile = Paths.get(HOME + "dev/java-sprint6-hw/resources/history.csv");

    public List<String> readListFromFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(String.valueOf(historyFile), StandardCharsets.UTF_8))) {

            List<String> subList = new ArrayList<>();

            while (br.ready()) {
                String line = br.readLine();
                subList.add(line);
            }

            return subList;
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время чтения файла.");
            return null;
        }
    }

    public void writeToFile(List<String> history) throws IOException {
        try (FileWriter writer = new FileWriter(String.valueOf(historyFile), StandardCharsets.UTF_8)) {
            writer.write("id,type,name,status,description,epic \n");
            for (String element : history) {
                writer.write(element + "\n");
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка во время записи файла.");
        }
    }
}