package p02_GenericBoxOfInteger;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		int n = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < n; i++) {
			Box<Integer> box = new Box<Integer>(Integer.parseInt(scanner.nextLine()));
			System.out.println(box);
		}
		scanner.close();

	}

}
