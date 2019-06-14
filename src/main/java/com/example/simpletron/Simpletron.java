package com.example.simpletron;

public class Simpletron {
	public static void main(String[] args) {
		Simpletron simpletron = new Simpletron();
		simpletron.begin();
	}
	
	public void begin() {
		printWelcomeMessage();
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
}
