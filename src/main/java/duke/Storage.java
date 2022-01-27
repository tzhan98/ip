package duke;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Class responsible for writing to and reading from disk
 */

public class Storage {

    static File saveData;
    static Parser parser;

    public Storage(){
        load();
    }

    /**
     * Write all current information in Tasklist to disk
     */
    public static void writeAllToFile() {
        try {
            FileWriter fw = new FileWriter(saveData);
            for (int i = 0; i < Task.totalTask; i++) {
                fw.write(TaskList.tasklist.get(i).getDataRepresentation());
            }
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * append a line of task information to disk
     *
     * @param line Task information to write to disk
     * @throws IOException if there is an error writing to disk
     */
    public static void addLineToFile(String line) throws IOException {
        FileWriter fw = new FileWriter(saveData, true); // create a FileWriter in append mode
        fw.write(line);
        fw.close();
    }

    /**
     * Finds previous save data if exists. If not found, creates new save data
     * If found, calls readSavedData
     */
    public static void load(){
        try {
            String currDir = System.getProperty("user.dir");
            java.nio.file.Path pathDir = java.nio.file.Paths.get(currDir, "data", "duke.Duke");
            Path pathToFile = java.nio.file.Paths.get(currDir, "data", "duke.Duke", "tasks.txt");
            boolean directoryExists = java.nio.file.Files.exists(pathToFile);
            if (!directoryExists) {
                System.out.println("Unable to find existing data. Creating new file(s)...");
                new File(pathDir.toString()).mkdirs();
                pathToFile.toFile().createNewFile();
                System.out.println("Successfully created " + pathToFile);
            } else {
                System.out.println("Resuming previous saved state.");
            }
            saveData = pathToFile.toFile();
            readSavedData();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * reads information from disk to taskList
     * @throws FileNotFoundException when file is not found
     */
    public static void readSavedData() throws FileNotFoundException {
        Scanner s = new Scanner(saveData);
        while(s.hasNext()){
            TaskList.tasklist.add(parser.parseFileData(s.nextLine()));
        }
    }

}
