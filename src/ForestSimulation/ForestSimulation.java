package ForestSimulation;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ForestSimulation {

    private static final String PRINT_KEY = "P";
    private static final String ADD_KEY = "A";
    private static final String CUT_KEY = "C";
    private static final String GROW_KEY = "G";
    private static final String REAP_KEY = "R";
    private static final String SAVE_KEY = "S";
    private static final String LOAD_KEY = "L";
    private static final String NEXT_KEY = "N";
    private static final String EXIT_KEY = "X";
    private static final String MENU_PROMPT = "\n(P)rint, (A)dd, (C)ut, (G)row, (R)eap, (S)ave, (L)oad, (N)ext, e(X)it :";
    private static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {

        if (args.length > 0) {

            int argsIndex = 0;
            String fileName = args[argsIndex] + ".csv";
            boolean exitKeyPressed = false;

            System.out.println("Welcome to the Forestry Simulation");
            System.out.println("----------------------------------");

            File forestFile = new File(fileName);
            Forest currentForest = new Forest(args[argsIndex]);

            while (!exitKeyPressed) {
                System.out.println("Initializing from " + args[argsIndex]);

                if (!forestFile.exists()) {
                    System.out.println("Error opening/reading " + fileName);
                    argsIndex++;

                    if (argsIndex < args.length) {
                        fileName = args[argsIndex] + ".csv";
                        forestFile = new File(fileName);
                        currentForest = new Forest(args[argsIndex]);
                        System.out.println("Initializing from " + args[argsIndex]);
                    } else {
                        System.out.println("There are no more valid files left to display: exiting program");
                        return;
                    }
                }

                try {
                    initializeForest(forestFile, currentForest);
                } catch (IOException e) {
                    System.out.println("Error opening/reading " + fileName);
                }

                System.out.print(MENU_PROMPT);
                String menuInput = keyboard.nextLine().toUpperCase();

                while (!menuInput.equals(EXIT_KEY)) {
                    switch (menuInput) {
                        case PRINT_KEY:
                            System.out.println();
                            System.out.println("Forest name: " + currentForest.getName());
                            currentForest.printForest();
                            break;

                        case ADD_KEY:
                            currentForest.addTree();
                            break;

                        case CUT_KEY:
                            if (currentForest.getNumberOfTrees() != 0) {
                                System.out.print("Tree number to cut down: ");
                                try {
                                    int treeIndex = keyboard.nextInt();
                                    keyboard.nextLine(); // consume the newline left after nextInt
                                    if (treeIndex >= 0 && treeIndex < currentForest.getNumberOfTrees()) {
                                        currentForest.cutTree(treeIndex);
                                    } else {
                                        System.out.println("Tree number " + treeIndex + " does not exist");
                                    }
                                } catch (InputMismatchException e) {
                                    System.out.println("That is not an integer");
                                    keyboard.nextLine();
                                }
                            } else {
                                System.out.println("There are no trees to cut down");
                            }
                            break;

                        case GROW_KEY:
                            currentForest.growForest();
                            break;

                        case REAP_KEY:
                            System.out.print("Height to reap from: ");
                            try {
                                double treeHeight = keyboard.nextDouble();
                                keyboard.nextLine(); // consume the newline left after nextDouble
                                if (treeHeight > 0.0) {
                                    currentForest.reapTrees(treeHeight);
                                } else {
                                    System.out.println(treeHeight + " is not a valid height");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("That is not a valid number");
                                keyboard.nextLine();
                            }
                            break;

                        case SAVE_KEY:
                            currentForest.saveForest();
                            break;

                        case LOAD_KEY:
                            System.out.print("Enter forest name: ");
                            String forestName = keyboard.nextLine();
                            currentForest.loadForest(forestName);
                            break;

                        case NEXT_KEY:
                            if ((argsIndex + 1) < args.length) {
                                argsIndex++;
                                fileName = args[argsIndex] + ".csv";
                                forestFile = new File(fileName);
                                currentForest = new Forest(args[argsIndex]);
                                System.out.println("Initializing from " + args[argsIndex]);
                            } else {
                                System.out.println("There are no more forests left to display");
                            }
                            break;

                        default:
                            System.out.println("Invalid menu option, try again");
                            break;
                    }
                    System.out.print(MENU_PROMPT);
                    menuInput = keyboard.nextLine().toUpperCase();
                }
                exitKeyPressed = true;
            }
        } else {
            System.out.println("No forest file provided");
        }
    }

    private static void initializeForest(File forestFile, Forest currentForest) throws IOException {
        Scanner fromForestFile = new Scanner(forestFile);
        while (fromForestFile.hasNextLine()) {
            String currentLine = fromForestFile.nextLine();
            String[] currentTreeData = currentLine.split(",");
            String treeSpecies = currentTreeData[0];
            int treeYearOfPlanting = Integer.parseInt(currentTreeData[1]);
            double treeHeightInFeet = Double.parseDouble(currentTreeData[2]);
            double treeGrowthRate = Double.parseDouble(currentTreeData[3]);
            Tree currentTree = new Tree(treeSpecies, treeYearOfPlanting, treeHeightInFeet, treeGrowthRate);
            currentForest.addTree(currentTree);
        }
        fromForestFile.close();
    }
}
