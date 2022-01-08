package org.eco.mubisoft.generator;

import org.eco.mubisoft.generator.control.GenerationController;
import org.eco.mubisoft.generator.control.Log;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Locale;

@SpringBootApplication
public class GeneratorApplication {

    private static final int GENERATION_QUANTITY = 100;
    private final GenerationController controller;

    public GeneratorApplication() {
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
        GeneratorApplication dataGeneration = new GeneratorApplication();
        boolean generateBasic = true;

        if (args.length > 0) {
            generateBasic = !args[0].toLowerCase(Locale.ROOT).equals("false");
        }
        dataGeneration.generateDatabaseData(GENERATION_QUANTITY, generateBasic);
    }

}
