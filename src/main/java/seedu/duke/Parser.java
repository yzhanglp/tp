package seedu.duke;

import java.time.LocalDateTime;
import java.util.List;

public class Parser {


    public static void parseRecord(String description, Ui ui) {
        String[] tokens = description.split(" ");
        int spdSortOp = 0;
        int dateSortOp = 0;
        int accSortOp = 0;
        int probSortOp = 0;
        boolean probShowDetails = false;
        for (String token : tokens) {
            if (token.equals("-details")) {
                probShowDetails = true;
            } else if (token.startsWith("-s")) {
                spdSortOp = 1;
                if(token.length() == 3 && token.endsWith("r")){
                    spdSortOp = 2;
                }
            } else if (token.startsWith("-d")) {
                dateSortOp = 1;
                if(token.length() == 3 && token.endsWith("r")){
                    dateSortOp = 2;
                }
            } else if (token.startsWith("-a")) {
                dateSortOp = 1;
                if(token.length() == 3 && token.endsWith("r")){
                    dateSortOp = 2;
                }
            } else if(token.startsWith("-p")) {
                probSortOp = 1;
                if(token.length() == 3 && token.endsWith("r")){
                    probSortOp = 2;
                }
            }
        }
        ui.printRecords(Storage.sortRecords(dateSortOp, spdSortOp, accSortOp, probSortOp), probShowDetails);
    }
    public static void parse(String command, Ui ui) {

        /*
         * Example of commands:
         *
         * Generate problem sets:
         * gen -t 1 -n 2 -d 3
         *
         * Help function:
         * help
         */

        // Split the command into two parts: action and description
        String[] parts = command.split(" ", 2);
        String action = parts[0];
        String description = "";
        if (parts.length > 1) {
            description = parts[1];
        }

        switch (action) {
        // notice: write your parser function by your own
        case "": // by default, it will be "gen"
        case "gen":
        case "generate":
            //ProblemGenerator ;
            ProblemGenerator pb = new ProblemGenerator();
            Test test = pb.typeChoose(command);
            Checker checker = new Checker(test);
            checker.getUserAnswer();
            String accRate = String.format("%.2f", checker.getAccuracy()*100);
            System.out.println("Acc: " + accRate +"%");
            System.out.println("Spend Time: " + checker.getTime() + "s");
            List<String> wrongAnswer = checker.getWrongAnswer();
            List<Problem> wrongProblem = checker.getWrongProblem();
            System.out.println("The following problem you have solved are wrong:\n");
            for(int i=0;i<wrongProblem.size();i++){
                Problem problem = wrongProblem.get(i);
                System.out.println("Your answer: "+ problem.getDescription()+" = "+wrongAnswer.get(i));
                System.out.println("Correct Answer: "+problem.solved());
            }
            // Storage write to file
            double speed = (double) test.getNumber() / checker.getTime();
            Storage.addRecord(new Record(LocalDateTime.now(), speed, checker.getAccuracy(), test.getProblem()));
            Storage.writeFile();

            break;
        case "records":
            parseRecord(description, ui);
            //ui.records(command);
            break;
        case "help":
            ui.help(description);
            break;
        case "exit":
            ui.exit();
            break;
        default:
            ui.invalidCommand();
            break;
        }
    }
}
