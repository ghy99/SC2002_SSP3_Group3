package Service;//package Service;

import java.util.Scanner;

/**
 * @author Gan Hao Yi
 * This class is written to get number inputs and check for character inputs as well as
 * to check if entered value is within range.
 */
public class GetNumberInput {
    /**
     * this gets an Integer input, accounting for error inputs as well
     * @param range1 - Lower range limit
     * @param range2 - Upper range limit
     * @param exit - Value to enter to exit loop
     * @return an integer value Choice
     */
    public static int getInt(int range1, int range2, int exit) {
        System.out.printf("Enter %d to exit:\n", exit);
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            while (!sc.hasNextInt()) {
                System.out.println("That's not a number!");
                sc.next(); // this is important!
            }
            choice = sc.nextInt();
            sc.nextLine();
            if (choice == exit) {
                break;
            }
            if (choice < range1 || choice > range2) {
                System.out.printf("Value out of range! Please enter a value between %d and %d\n", range1, range2);
            }
        } while (choice < range1 || choice > range2);
        return choice;
    }

    /**
     * this gets a Double input, accounting for error input below 0.
     * Mainly used for ticket price edition.
     * @param range1 - Lower range limit
     * @param exit - Value to enter to exit loop
     * @return a double value Choice
     */
    public static double getDouble(double range1, double range2, int exit) {
        Scanner sc = new Scanner(System.in);
        double choice;
        do {
            while (!sc.hasNextDouble()) {
                System.out.println("That's not a number!");
                sc.next(); // this is important!
            }
            choice = sc.nextDouble();
            sc.nextLine();
            if (choice == exit) {
                System.out.println("Break condition fulfilled. Exiting.");
                break;
            }
            if (choice < range1 || choice > range2) {
                System.out.printf("Value out of range! Please enter a value above %.1f\n", range1);
            }
        } while (choice < range1 || choice > range2);
        return choice;
    }

}
