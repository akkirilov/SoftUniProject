package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Map<String, String> phoneBook = new HashMap<String, String>();

        Scanner scanner = new Scanner(System.in);
        String[] input = Arrays.stream(scanner.nextLine().split(" ")).toArray(String[]::new);

        while (!input[0].equals("END")) {
            if(input[0].equals("A")){
                phoneBook.put(input[1], input[2]);
            }
            else if(input[0].equals("S")) {
                if(phoneBook.containsKey(input[1])){
                    System.out.println(input[1] + " -> " + phoneBook.get(input[1]));
                }
                else {
                    System.out.println("Contact " + input[1] + " does not exist.");
                }
            }

            input = Arrays.stream(scanner.nextLine().split(" ")).toArray(String[]::new);
        }
    }
}