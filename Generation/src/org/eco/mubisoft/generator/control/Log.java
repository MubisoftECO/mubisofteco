package org.eco.mubisoft.generator.control;

public class Log {

    public static void display(String message) {
        for (int i = 0; i < message.length() + 10; i++) {
            System.out.print('-');
        }
        System.out.println("\n# -- " + message + " -- #");
        for (int i = 0; i < message.length() + 10; i++) {
            System.out.print('-');
        }
        System.out.print("\n");
    }

    public static void info(String message) {
        System.out.println("INFO: " + message);
    }

}
