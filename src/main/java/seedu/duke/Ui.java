package seedu.duke;

import java.util.Scanner;
import java.util.ArrayList;
/**
 * Represents the user interface for interacting with the chatbot.
 */
public class Ui {

    // Pre-defined sentences
    private static final String PROBLEM_FORM =
            "Please type the number and difficulty you like in following form: \n" +
                    "< generate -t [type] -n [number] -d [maximum digit] -l [length of formula] >";
    private static final String INPUT_INSTRUCTION =
            "Input Instructions:\n" +
                    "[operators]: can be + - * /, you can combine any of them.\n" +
                    "[number]: number of problem set generated\n" +
                    "[maximum digit]: how big can the calculation be\n\n" +
                    "For example: generate -t + -n 10 -d 2 -l 2\n" +
                    "-> generate 10 problems with + and - operator, each has 2 numbers taking operations\n"+
                    "and the maximum number of digits is 2 (99 max)";
    private static final String GEN_COMMAND =
            "Generate problem sets: \t" + "gen -t [type] -n [number] -d [maximum digits]";
    private static final String HELP_COMMAND =
            "Help function: \t" + "help [type], type can be 'gen'/'command'/...";
    private static final String RECORDS_COMMAND =
            "View past records(in-brackets parameters are optional): \t" + "records [-d] [-s] [-a] [-p] [-details]\n" +
                    "[-d]: sort the records based on dateTime in increasing order. Add r to reverse order (-dr)\n" +
                    "[-s]: sort the records based on speed in increasing order. Add r to reverse order (-sr)\n" +
                    "[-a]: sort the records based on accuracy in increasing order. Add r to reverse order (-ar)\n" +
                    "[-p]: sort the records based on problemSetID in increasing order. Add r to reverse order (-pr)\n" +
                    "[-details]: show the details of the problem set(each individual problem).";
    private final String name;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Creates a new Ui with the given name.
     *
     * @param name The name of the chatbot.
     */
    public Ui(String name) {
        this.name = name;
    }

    static void missingMessage(String parameters){
        String message = "parameter missing! using default" ;
        System.out.println("parameter missing! using default "+parameters);
    }

    /**
     * Displays a greeting message.
     */

    public void greet() {
        this.showLine();
        String logo = "__   __       _   _      ____            _\n" +
                "|  \\/  | __ _| |_| |__  / ___| ___ _ __ (_)_   _ ___\n" +
                "| |\\/| |/ _` | __| '_ \\| |  _ / _ \\ '_ \\| | | | / __|\n" +
                "| |  | | (_| | |_| | | | |_| |  __/ | | | | |_| \\__ \\\n" +
                "|_|  |_|\\__,_|\\__|_| |_|\\____|\\___|_| |_|_|\\__,_|___/\n";
        System.out.println(logo);
        System.out.println("Hello! I'm " + name);
        System.out.println("Type 'help' to see the instructions. \n");
        this.showLine();
        System.out.println(PROBLEM_FORM);
    }

    public String readCommand() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return "";
    }

    public void showLine() {
        System.out.println("=========================");
    }

    public void help(String helpType) {
        switch (helpType) {
        case "": // by default, user asks Help Instruction
        case "command":
            System.out.println(HELP_COMMAND);
            break;
        case "gen":
        case "generate":
            System.out.println(GEN_COMMAND);
            System.out.println(INPUT_INSTRUCTION);
            break;
        case "records":
            System.out.println(RECORDS_COMMAND);
            break;
        default:
            break;
        }
        this.showLine();
    }

    // records input
    public void printRecords(ArrayList<Record> records, boolean showProbDetails) {
        for (Record record : records) {
            showLine();
            record.print(showProbDetails);
        }
        showLine();
    }

    // invalid input

    /**
     * Displays an error message for an invalid command.
     */
    public void invalidCommand() {
        System.out.println("Invalid command! Please try again.");
    }

    public void exit() {
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }
}
