package org.eco.mubisoft;

import org.eco.mubisoft.generator.control.GenerationController;

import java.util.Locale;

public class DataGeneration {

    private static final int GENERATION_QUANTITY = 10000;
    private final GenerationController controller;

    public DataGeneration() {
        controller = new GenerationController();
    }

    public void generateDatabaseData(int quantity, Boolean generateBasicValues) {
        if (generateBasicValues) {
            controller.generateBasic();
        }
        controller.generate(quantity);
    }

    public static void main(String[] args) {
        DataGeneration dataGeneration = new DataGeneration();
        boolean generateBasic = true;

        if (args.length > 0) {
            generateBasic = !args[0].toLowerCase(Locale.ROOT).equals("false");
        }
        dataGeneration.generateDatabaseData(GENERATION_QUANTITY, generateBasic);
    }

}
