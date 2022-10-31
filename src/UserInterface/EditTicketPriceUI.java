package UserInterface;

import Movie.TicketCharges;
import Service.TextDB;
import java.io.IOException;
import java.util.Scanner;

public class EditTicketPriceUI {
    public static void EditTicket() throws IOException {
        Scanner sc = new Scanner(System.in);
        String file = "TicketPrice.txt";
        System.out.println("Show how tickets are charged:");
        TicketCharges charges = new TicketCharges();
        charges.printTicketCharges();
        System.out.println("Select category that you want to change: ");
        System.out.println("1) Age");
        System.out.println("2) Day of the week");
        System.out.println("3) Movie Dimension");
        System.out.println("4) Type of Cinema");
        int cat = sc.nextInt();
        System.out.println("Which do you want to edit:");
        switch(cat) {
            case 1 -> {
                System.out.println("1) Student price");
                System.out.println("2) Adult price");
                System.out.println("3) Senior Citizen price");
            }
            case 2 -> {
                System.out.println("1) Monday - Friday");
                System.out.println("2) Saturday - Sunday");
                System.out.println("3) Public Holiday");
            }
            case 3 -> {
                System.out.println("1) 2D Movie");
                System.out.println("2) 3D Movie");
            }
            case 4 -> {
                System.out.println("1) Regular Cinema");
                System.out.println("2) Premium Cinema");
            }
        }
        int choice = sc.nextInt();
        System.out.println("What is the new value:");
        Double newvalue = sc.nextDouble();
        TextDB.WriteToTextDB(file, cat, choice, newvalue);
    }
}
