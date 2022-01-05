package org.eco.mubisoft;

import org.eco.mubisoft.generator.control.GenerationController;
import org.eco.mubisoft.generator.control.Log;

import java.util.Locale;

public class DataGeneration {

    private static final int GENERATION_QUANTITY = 100;
    private final GenerationController controller;

    public DataGeneration() {
        controller = new GenerationController();
    }

    public void generateDatabaseData(int quantity, Boolean generateBasicValues) {
        if (generateBasicValues) {
            Log.display("Generating Basic Elements");
            controller.generateBasic();
        }
        Log.display("Generating Random Elements");
        controller.generate(quantity);
        Log.display("Generation process finished.");
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
