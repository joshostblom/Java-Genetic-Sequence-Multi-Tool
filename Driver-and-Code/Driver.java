import jdk.swing.interop.SwingInterOpUtils;

import java.io.*;
import java.util.Scanner;

public class Driver {
    //Declares variables
    public static Scanner input = new Scanner(System.in);
    public static Sequence sequence;
    public static int choice;

    //Starts the start method
    public static void main(String[] args) throws IOException {
        start();
    }

    public static void start() throws IOException {
        //Prints title and goes through sequence prompt and the takes the user to the menu
        System.out.println("------------------------------------\n| Java Genetic Sequence Multi-Tool |\n------------------------------------\n");
        sequencePrompt();
        mainMenu();
    }

    public static void sequencePrompt() throws FileNotFoundException {
        //Asks the user how they will input their sequence
        System.out.print(" 1. Enter a file\n 2. Enter sequence manually\n\nChoose where you will get your sequence: ");
        choice = input.nextInt();
        //Checks to see whether the input was valid
        while (choice != 1 && choice != 2) {
            System.out.print("Invalid input!\n Enter valid option (1-2): ");
            choice = input.nextInt();
        }
        boolean usedFile;
        File file = new File("");
        String sequenceInput = "";
        //If the user selects to add a file, it asks the user to input the directory
        if(choice == 1) {
            System.out.print("Enter the file path (No spaces allowed in file name, include file type at the end such as \".txt\", or just drag file onto window): ");
            file = new File(input.next());
            usedFile = true;
            //If the user selects a manual input, it asks the user to manually input
        } else {
            System.out.print("Enter the nucleotide sequence (all caps, no spaces, all one line): ");
            sequenceInput = input.next();
            usedFile = false;
        }

        //Asks the user what the reading frame of the sequence is
        System.out.print("What is the reading frame of the sequence? (1 to 3 or -1 to -3): ");
        int readingFrame = input.nextInt();
        //Checks for a valid reading frame
        while ((readingFrame < 1 || readingFrame > 3) && (readingFrame < -3 || readingFrame > -1)) {
            //Asks the user for a new input
            System.out.print("Invalid response!\n Enter valid option (1 to 3 or -1 to -3): ");
            readingFrame = input.nextInt();
        }

        //Ask the user if the sequence is forward
        System.out.print("Is this a forward sequence (5' to 3')? (y/n): ");
        //Sets answer to lower case
        String response = input.next().toLowerCase();
        //Checks if a valid answer
        while (!(response.equals("y") || response.equals("n") || response.equals("yes") || response.equals("no"))) {
            //Asks the user for a new input
            System.out.print("Invalid response!\n Enter valid option (y/n): ");
            response = input.next().toLowerCase();
        }

        //Sets whether forward
        boolean forward;
        forward = response.equals("y") || response.equals("yes");

        //Asks the user for the sequence type
        System.out.print("What type of sequence is this? (DNA or RNA): ");
        //Sets the answer to lower case
        String sequenceType = input.next().toLowerCase();
        //Checks for valid answer
        while (!(sequenceType.equals("rna") || sequenceType.equals("dna"))) {
            //Asks the user for a new input
            System.out.print("Invalid response!\n Enter valid option (DNA or RNA): ");
            sequenceType = input.next().toLowerCase();
        }

        //Creates sequence based on their input
        if (usedFile) {
            sequence = new Sequence(forward, readingFrame, sequenceType, file);
        } else {
            sequence = new Sequence(forward, readingFrame, sequenceType, sequenceInput);
        }

        //Reports successful input
        System.out.println("\nSequence submitted successfully!\n");
    }

    public static void mainMenu() throws IOException {
        //Prints title
        System.out.println("------------------\n    Main Menu\n------------------");
        //Checks the sequence type for option 2
        String otherType = "RNA";
        if (sequence.getSequenceType().equals("RNA")) {
            otherType = "DNA";
        }
        //Prints sequence info
        System.out.printf("\nSequence info - Forward: %s, Type: %s, Reading frame: %s, Length: %snt\n", sequence.isForwardStrand(), sequence.getSequenceType(), sequence.getReadingFrame(), sequence.getSequenceData().length());
        //Print options
        System.out.printf("\n1. Print a version of the sequence\n2. Transform sequence to %s\n3. Search sequence for subsequence\n4. Restart with new sequence\n5. Exit\n\nChoose an option: ", otherType);
        choice = input.nextInt();
        //Checks for valid input
        while (choice < 1 || choice > 5) {
            //Asks the user for a new input
            System.out.print("Invalid input!\n Enter valid option (1-5): ");
            choice = input.nextInt();
        }

        //Executes commands based on option chosen
        switch(choice){
            case 1:
                //Goes to print options
                printOptions();
                break;
            case 2:
                //Checks current sequence type and switches accordingly
                if(sequence.getSequenceType().equals("RNA")) {
                    sequence.toDNA();
                } else {
                    sequence.toRNA();
                }
                //Notifies user and returns to menu
                System.out.println("\nSequence changed successfully!\n");
                mainMenu();
                break;
            case 3:
                //Asks user for a subsequence and runs command
                System.out.print("\nEnter a subsequence: ");
                int result = sequence.getIndex(input.next());
                //Reports results
                if (result == -1) {
                    System.out.println("Subsequence not found!");
                } else {
                    System.out.println("Sequence found at index: " + result);
                }
                //Waits for user input and returns to menu
                System.out.println("Press enter to return to menu...");
                System.in.read();
                mainMenu();
                break;
            case 4:
                //Restarts program
                start();
                break;
            case 5:
                //Ends program
                break;
        }
    }


    public static void printOptions() throws IOException {
        //Prints title
        System.out.println("\n------------------\n  Print options\n------------------");
        //Prints sequence info
        System.out.printf("\nSequence info - Forward: %s, Type: %s, Reading frame: %s, Length: %snt\n", sequence.isForwardStrand(), sequence.getSequenceType(), sequence.getReadingFrame(), sequence.getSequenceData().length());
        //Prints options
        System.out.print("\n1. Print sequence\n2. Print reverse\n3. Print complimentary strand\n4. Print reverse complimentary strand\n5. Print nucleotide count\n6. Print amino acid count\n7. Back\n\n");
        choice = input.nextInt();
        //Checks for valid input
        while (choice < 1 || choice > 7) {
            //Asks the user to input again
            System.out.print("Invalid input!\n Enter valid option (1-7): ");
            choice = input.nextInt();
        }

        //Executes commands based on chosen option
        switch (choice) {
            case 1 -> {
                //Prints sequence info
                System.out.printf("\nSequence info - Forward: %s, Type: %s, Reading frame: %s, Length: %snt\n", sequence.isForwardStrand(), sequence.getSequenceType(), sequence.getReadingFrame(), sequence.getSequenceData().length());
                System.out.println();
                //Prints sequence
                sequence.printSequence();
                System.out.println();
                //Waits for user input and returns to menu
                System.out.println("Press enter to return to print options...");
                System.in.read();
                printOptions();
            }
            case 2 -> {
                //Gets reverse
                Sequence output = sequence.getReverse();
                //Prints sequence info
                System.out.printf("\nSequence info - Forward: %s, Type: %s, Reading frame: %s, Length: %snt\n", output.isForwardStrand(), output.getSequenceType(), output.getReadingFrame(), output.getSequenceData().length());
                //Prints reverse sequence
                System.out.println("\n" + output.getSequenceData() + "\n");
                //Waits for user input and returns to menu
                System.out.println("Press enter to return to print options...");
                System.in.read();
                printOptions();
            }
            case 3 -> {
                //Gets complimentary
                Sequence output = sequence.getComplimentary();
                //Prints sequence info
                System.out.printf("\nSequence info - Forward: %s, Type: %s, Reading frame: %s, Length: %snt\n", output.isForwardStrand(), output.getSequenceType(), output.getReadingFrame(), output.getSequenceData().length());
                //Prints complimentary sequence
                System.out.println("\n" + output.getSequenceData() + "\n");
                //Waits for user input and returns to menu
                System.out.println("Press enter to return to print options...");
                System.in.read();
                printOptions();
            }
            case 4 -> {
                //Gets complimentary reverse
                Sequence output = sequence.getComplimentaryReverse();
                //Prints sequence info
                System.out.printf("\nSequence info - Forward: %s, Type: %s, Reading frame: %s, Length: %snt\n", output.isForwardStrand(), output.getSequenceType(), output.getReadingFrame(), output.getSequenceData().length());
                //Prints complimentary reverse sequence
                System.out.println("\n" + output.getSequenceData() + "\n");
                //Waits for user input and returns to menu
                System.out.println("Press enter to return to print options...");
                System.in.read();
                printOptions();
            }
            case 5 -> {
                System.out.println();
                //Prints nucleotide count
                sequence.printNucleotides();
                System.out.println();
                //Waits for user input and returns to menu
                System.out.println("Press enter to return to print options...");
                System.in.read();
                printOptions();
            }
            case 6 -> {
                System.out.println();
                //Prints amino acid count
                sequence.printAminoAcids();
                System.out.println();
                //Waits for user input and returns to menu
                System.out.println("Press enter to return to print options...");
                System.in.read();
                printOptions();
            }
            //Returns to main menu
            case 7 -> mainMenu();
        }
    }
}