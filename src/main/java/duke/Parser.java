package duke;

import java.util.ArrayList;

public class Parser {

    public Parser(){
        Storage.parser = this;
    }

    public static boolean parseIsBye(String input,TaskList tasklist){
        if (input.equals("bye")){
            return true;
        } else {
            parseInput(input, tasklist);
            return false;
        }
    }

    public static void parseInput(String input, TaskList taskList) {
        if (input.equals("list")) {
            Ui.printAllTasks(taskList);
        } else if (input.startsWith("delete")) {
            taskList.deleter(Integer.parseInt(input.substring(7)));
        } else if (input.startsWith("mark") || input.startsWith("unmark")) {
            taskList.markTask(input);
        } else if (input.startsWith("event") || input.startsWith("todo") || input.startsWith("deadline")) {
            Task task = parseCreateNewTask(input);
            if (task != null) {
                taskList.tasklist.add(task);
            }
        } else if (input.startsWith("find ")){
            taskList.findTask(input.substring(6));
        } else {
            Ui.printWhatDoesThatMean();
        }
    }

    public static Task parseCreateNewTask(String input){
        Task task = null;
        try {
            if (input.startsWith("todo")) {
                task = new ToDo(input.substring(4), Task.totalTask, false);
            } else if (input.startsWith("deadline")){
                String[] inputArr = input.split("/by ");
                if (inputArr.length == 1) {
                    throw new EmptyDescriptorExceptions();
                }
                task = new Deadline(inputArr[0].substring(8),Task.totalTask, inputArr[1], false);
            } else {
                String[] inputArr = input.split("/at ");
                if (inputArr.length == 1) {
                    throw new EmptyDescriptorExceptions();
                }
                task = new Event(inputArr[0].substring(5), Task.totalTask, inputArr[1], false);
            }
        } catch (EmptyDescriptorExceptions e) {
            e.printStackTrace();
        }
        return task;
    }

    public static Task parseFileData(String input){
        if (input == null || input == ""){
            return null;
        }
        String[] stringArr = input.split("---");
        Task task;
        if (stringArr[0].equals("T")){
            task = new ToDo(stringArr[2],Task.totalTask, true);
        } else if (stringArr[0].equals("D")){
            task = new Deadline(stringArr[2],Task.totalTask, stringArr[3], true);
        } else {
            task = new Event(stringArr[2],Task.totalTask, stringArr[3], true);
        }
        if (stringArr[1].equals("true")){
            task.mark();
        }
        return task;
    }

}
