/**
 * This is the main Duke program that will be able to process a Task of 3 types: todo, deadline and task
 * Duke is able to list, delete and mark/unmark tasks as done/undone.
 *
 * @author Toh Zhan Qing
 */

import java.util.*;

public class Duke {

    public static void main(String[] args){
        Ui ui = new Ui();
        Scanner sc = new Scanner(System.in);
        boolean isBye = false;
        TaskList tasklist = new TaskList();
        Storage storage = new Storage();
        storage.load();
        Parser parser = new Parser();
        Storage.parser = parser;
        while(!isBye) {
            try {
                String input = sc.nextLine();
                isBye = Parser.parseInput(input);
            }
            catch (EmptyDescriptorExceptions e){
                System.out.println("☹ OOPS!!! The description of a task cannot be empty.");
            }
        }
    }

}
