package Service;

import java.util.Scanner;

public class GetNumberInput {
    public static int getInt() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("That's not a number!");
                sc.next(); // this is important!
            }
            choice = sc.nextInt();
            sc.nextLine();
        } while (choice < -1);
        return choice;
    }
    public static double getDouble() {
        Scanner sc = new Scanner(System.in);
        double choice;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("That's not a number!");
                sc.next(); // this is important!
            }
            choice = sc.nextDouble();
            sc.nextLine();
        } while (choice < -1);
        return choice;
    }
}
