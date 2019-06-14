package com.example.simpletron;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Simpletron {
    private int[] memory;

    public static void main(String[] args) {
        Simpletron simpletron = new Simpletron();
        simpletron.begin();
    }

    public Simpletron() {
        memory = new int[100];
    }

    public void begin() {
        printWelcomeMessage();
        loadToMemory();
    }

    private void printWelcomeMessage() {
        System.out.println();
        System.out.println("*** Welcome to Simpletron! ***");
        System.out.println("*** Please enter your program one instruction ***");
        System.out.println("*** (or data word) at a time. I will type the ***");
        System.out.println("*** location number and a question mark (?). ***");
        System.out.println("*** You then type the word for that location. ***");
        System.out.println("*** Type the sentinel -99999 to stop entering ***");
        System.out.println("*** your program. ***");
        System.out.println();
    }

    private void loadToMemory() {
        int address = 0;
        while (true) {
            String promptString = address < 10 ? "0" + address + " ? " : address + " ? ";
            int userInput = getUserInput(promptString);
            if (userInput == -99999) {
                System.out.println();
                System.out.println("*** Program loading completed ***");
                System.out.println("*** Program execution begins ***");
                return;
            }
            memory[address++] = userInput;
        }
    }

    private int getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        String userInput = scanner.nextLine();
        while (inputInvalid(userInput)) {
            System.out.print("(Enter a valid SML word) " + prompt);
            userInput = scanner.nextLine();
        }
        return Integer.parseInt(userInput);
    }

    private boolean inputInvalid(String input) {
        String data = input.trim();
        if (data.equals("-99999")) {
            return false;
        } else if (Pattern.matches("[-+]?[0-9]{4}", input)) {
            return false;
        } else {
            return true;
        }
    }
}
