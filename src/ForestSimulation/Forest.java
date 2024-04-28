package ForestSimulation;

import java.io.*;
import java.util.ArrayList;

public class Forest {

    private final String name;
    private int numberOfTrees;
    private ArrayList<Tree> treeArrayList;

    public Forest(String name) {
        this.name = name;
        numberOfTrees = 0;
        treeArrayList = new ArrayList<>();
    } // end of forest constructor

    public String getName() {
        return name;
    }   // end of getName method

    public void printForest() {
        Tree currentTree;
        double totalHeight = 0.0;

        if (treeArrayList.size() != 0) {
            for (int index = 0; index < treeArrayList.size(); index++) {
                currentTree = treeArrayList.get(index);
                System.out.printf("%6d ", index);
                System.out.println(currentTree.toString());
                totalHeight += currentTree.getHeightInFeet();
            }

            double averageHeight = totalHeight / numberOfTrees;
            System.out.printf("There are %d trees, with an average height of %.2f%n", numberOfTrees, averageHeight);
        } else {
            System.out.println("There are no trees in this forest");
        }
    } // end of printForest method

    public void addTree() {
        Tree randomTree = new Tree();
        treeArrayList.add(randomTree);
        ++numberOfTrees;
    } // end of the addTree method with no parameters

    public void addTree(Tree newTree) {
        treeArrayList.add(newTree);
        ++numberOfTrees;
    } // end of the addTree method for tree parameter

    public int getNumberOfTrees() {
        return numberOfTrees;
    } // end of the getNumberOfTrees method

    public void cutTree(int treeIndex) {
        treeArrayList.remove(treeIndex);
        --numberOfTrees;
    } // end of cutTree method

    public void growForest() {
        for (int index = 0; index < treeArrayList.size(); index++) {
            Tree currentTree = treeArrayList.get(index);
            currentTree.growTree();
        }
    } // end of the growForest method

    public void reapTrees(double heightToReapFrom) {
        if (heightToReapFrom > findGreatestHeight()) {
            System.out.printf("There are no trees with height %.2f; the maximum height is currently %.2f%n",
                    heightToReapFrom, findGreatestHeight());
        } else {
            for (int index = 0; index < treeArrayList.size(); index++) {
                Tree currentTree = treeArrayList.get(index);

                if (currentTree.getHeightInFeet() >= heightToReapFrom) {
                    System.out.println("Reaping the tall tree " + currentTree);
                    Tree newTree = new Tree();
                    System.out.println("Replacing with new tree " + newTree);
                    treeArrayList.set(index, newTree);
                }
            }
        }
    } // end of reapTrees method

    public void saveForest() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(name + ".db"))) {
            outputStream.writeObject(treeArrayList);
        } catch (IOException e) {
            System.out.println("Error in saving forest: " + e.getMessage());
        }
    } // end of saveForest method

    public void loadForest(String forestName) {
        forestName += ".db";

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(forestName))) {
            treeArrayList = (ArrayList<Tree>) inputStream.readObject();
            numberOfTrees = treeArrayList.size();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error in opening/reading forest: " + e.getMessage());
            System.out.println("Old forest retained");
        }
    } // end of loadForest method

    private double findGreatestHeight() {
        double greatestHeight = treeArrayList.get(0).getHeightInFeet();
        for (Tree currentTree : treeArrayList) {
            if (currentTree.getHeightInFeet() > greatestHeight) {
                greatestHeight = currentTree.getHeightInFeet();
            }
        }
        return greatestHeight;
    } // end of findGreatestHeight method
} // end of Forest class
