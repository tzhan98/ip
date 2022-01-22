import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Storage {

    static File savedata;

    public static void writeAllToFile() {
        try {
            FileWriter fw = new FileWriter(savedata);
            for (int i = 0; i < Task.totalTask; i++) {
                fw.write(Duke.taskList.get(i).getDataRepresentation());
            }
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addLineToFile(String line) throws IOException {
        FileWriter fw = new FileWriter(savedata, true); // create a FileWriter in append mode
        fw.write(line);
        fw.close();
    }

    public static void load(){
        try {
            String currDir = System.getProperty("user.dir");
            java.nio.file.Path pathDir = java.nio.file.Paths.get(currDir, "data", "Duke");
            Path pathToFile = java.nio.file.Paths.get(currDir, "data", "Duke", "tasks.txt");
            boolean directoryExists = java.nio.file.Files.exists(pathToFile);
            if (!directoryExists) {
                System.out.println("Unable to find existing data. Creating new file(s)...");
                new File(pathDir.toString()).mkdirs();
                pathToFile.toFile().createNewFile();
                System.out.println("Successfully created " + pathToFile);
            } else {
                System.out.println("Resuming previous saved state.");
            }
            savedata = pathToFile.toFile();
            readSavedData();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readSavedData() throws FileNotFoundException {
        Scanner s = new Scanner(savedata);
        while(s.hasNext()){
            fileParse(s.nextLine());
        }
    }

    public static void fileParse(String input){
        if (input == null || input == ""){
            return;
        }
        String[] stringArr = input.split("---");
        int taskNum = Task.totalTask;
        if (stringArr[0].equals("T")){
            Duke.taskList.add(new ToDo(stringArr[2], taskNum, true));
            Duke.markTaskNum(taskNum, stringArr[1]);
        } else if (stringArr[0].equals("D")){
            Duke.taskList.add(new Deadline(stringArr[2], taskNum, stringArr[3], true));
            Duke.markTaskNum(taskNum, stringArr[1]);
        } else {
            Duke.taskList.add(new Event(stringArr[2], taskNum, stringArr[3], true));
            Duke.markTaskNum(taskNum, stringArr[1]);
        }
    }
}
