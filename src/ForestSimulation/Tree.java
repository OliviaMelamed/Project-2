package ForestSimulation;

import java.io.Serializable;

public class Tree implements Serializable {

    public enum AllowedSpecies {BIRCH, FIR, MAPLE}

    private static final double MINIMUM_GROWTH_RATE = 10.0;

    private static final double MAXIMUM_GROWTH_RATE = 20.0;

    private static final double MINIMUM_INITIAL_HEIGHT = 10.0;

    private static final double MAXIMUM_INITIAL_HEIGHT = 20.0;

    private static final int MINIMUM_YEAR_ALLOWED = 2000;

    private static final int MAXIMUM_YEAR_ALLOWED = 2025;

    private AllowedSpecies species;
    private final int yearOPlanting;
    private double heightInFeet;
    private final double growthRate;

    public Tree(){

        int randomSpeciesNumber = (int)(Math.random() * 3);

        switch (randomSpeciesNumber) {
            case 0:
                species = AllowedSpecies.BIRCH;
                break;
            case 1:
                species = AllowedSpecies.FIR;
            case 2:
                species = AllowedSpecies.MAPLE;
                break;
            default:

                System.out.println("Error: Unrecognized number generated");
                break;
        } // end of switch case

        yearOPlanting = (int)(Math.random() * (MAXIMUM_YEAR_ALLOWED - MINIMUM_YEAR_ALLOWED)) + MINIMUM_YEAR_ALLOWED;

        heightInFeet = (Math.random() * (MAXIMUM_INITIAL_HEIGHT - MINIMUM_INITIAL_HEIGHT)) + MINIMUM_GROWTH_RATE;

        growthRate = (Math.random() * (MAXIMUM_GROWTH_RATE - MINIMUM_GROWTH_RATE)) + MINIMUM_GROWTH_RATE;

    } // end of default tree constructor

    public Tree(String species, int yearOPlanting, double heightInFeet, double growthRate) {
        this.species = AllowedSpecies.valueOf(species.toUpperCase());
        this.yearOPlanting = yearOPlanting;
        this.heightInFeet = heightInFeet;
        this.growthRate = growthRate;
    } // end of 4-parameter tree constructor

    @Override
    public String toString(){
        return String.format("%-7s %4d %7.2f' %5.1f%%", species, yearOPlanting, heightInFeet, growthRate);
    } // end of toString method

    public double getHeightInFeet(){
        return heightInFeet;
    } // end of getHeightInFeet method

    public void growTree(){
        heightInFeet = heightInFeet * (1 + (growthRate/100));
    } //end of growTree method

} // end of Tree class