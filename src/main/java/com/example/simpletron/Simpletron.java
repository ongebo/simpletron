package com.example.simpletron;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Simpletron {
    private Scanner scanner;

    private int[] memory;
    private int accumulator;
    private int instructionCounter;
    private int instructionRegister;
    private int operationCode;
    private int operand;

    private static final int READ = 10;
    private static final int WRITE = 11;
    private static final int LOAD = 20;
    private static final int STORE = 21;
    private static final int ADD = 30;
    private static final int SUBTRACT = 31;
    private static final int DIVIDE = 32;
    private static final int MULTIPLY = 33;
    private static final int BRANCH = 40;
    private static final int BRANCHNEG = 41;
    private static final int BRANCHZERO = 42;
    private static final int HALT = 43;

    public static void main(String[] args) {
        Simpletron simpletron = new Simpletron();
        simpletron.begin();
    }

    public Simpletron() {
        scanner = new Scanner(System.in);
        memory = new int[100];
        accumulator = 0;
        instructionCounter = 0;
        instructionRegister = 0;
        operationCode = 0;
        operand = 0;
    }

    public void begin() {
        printWelcomeMessage();
        loadToMemory();
        executeMachineCode();
        printDump();
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
                System.out.println();
                return;
            }
            memory[address++] = userInput;
        }
    }

    private int getUserInput(String prompt) {
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
        } else {
            return !Pattern.matches("[-+]?[0-9]{4}", input);
        }
    }

    private void executeMachineCode() {
        while (true) {
            instructionRegister = memory[instructionCounter];
            operationCode = instructionRegister / 100;
            operand = instructionRegister % 100;

            switch (operationCode) {
                case READ:
                    memory[operand] = getUserInput("? ");
                    break;
                case WRITE:
                    System.out.println(memory[operand]);
                    break;
                case LOAD:
                    accumulator = memory[operand];
                    break;
                case STORE:
                    memory[operand] = accumulator;
                    break;
                case ADD:
                    accumulator += memory[operand];
                    break;
                case SUBTRACT:
                    accumulator -= memory[operand];
                    break;
                case DIVIDE:
                    if (memory[operand] == 0) {
                        System.out.println();
                        System.out.println("*** Attempt to divide by zero ***");
                        System.out.println("*** Simpletron execution abnormally terminated ***");
                        return;
                    } else {
                        accumulator /= memory[operand];
                        break;
                    }
                case MULTIPLY:
                    accumulator *= memory[operand];
                    break;
                case BRANCH:
                    instructionCounter = operand;
                    continue;
                case BRANCHNEG:
                    if (accumulator < 0) {
                        instructionCounter = operand;
                        continue;
                    }
                    break;
                case BRANCHZERO:
                    if (accumulator == 0) {
                        instructionCounter = operand;
                        continue;
                    }
                    break;
                case HALT:
                    System.out.println();
                    System.out.println("*** Simpletron execution terminated ***");
                    return;
                default:
                    System.out.println("*** Invalid operation code ***");
                    System.out.println("*** Simpletron execution abnormally terminated ***");
                    return;
            }
            instructionCounter++;
        }
    }

    private void printDump() {
        printRegisters();
        System.out.println("MEMORY:");
        System.out.printf("%3s", "");
        for (int c = 0; c < 10; c++) {
            System.out.printf("%8d", c);
        }
        for (int i = 0; i < 100; i++) {
            if (i % 10 == 0) {
                System.out.println();
                System.out.printf("%3d", i);
            }
            System.out.printf("%8s", formatInteger(memory[i], 4, true));
        }
        System.out.println();
    }

    private void printRegisters() {
        System.out.println();
        System.out.println("REGISTERS:");
        System.out.printf("%-30s%5s\n", "accumulator", formatInteger(accumulator, 4, true));
        System.out.printf("%-30s%5s\n", "instructionCounter", formatInteger(instructionCounter, 2, false));
        System.out.printf("%-30s%5s\n", "instructionRegister", formatInteger(instructionRegister, 4, true));
        System.out.printf("%-30s%5s\n", "operationCode", formatInteger(operationCode, 2, false));
        System.out.printf("%-30s%5s\n", "operand", formatInteger(operand, 2, false));
        System.out.println();
    }

    private String formatInteger(int word, int length, boolean includeSign) {
        String sign = includeSign ? word < 0 ? "-" : "+" : "";
        String wordString = Integer.toString(word);
        int diff = length - wordString.length();

        if (diff == 0)
            return sign + wordString;
        else if (diff == 1)
            return sign + "0" + wordString;
        else if (diff == 2)
            return sign + "00" + wordString;
        else if (diff == 3)
            return sign + "000" + wordString;
        return sign + wordString;
    }
}
