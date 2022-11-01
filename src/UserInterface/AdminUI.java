package UserInterface;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import Admin.*;
import Movie.MovieTicket;
import Service.*;
import Review.*;



public class AdminUI {
	
	public static void AdminInterface() throws IOException, NoSuchAlgorithmException {
        System.out.println("Welcome to the Admin Portal. ");
        //only success log in then can access other services

        Scanner scan = new Scanner(System.in);
        TextDB textDB = new TextDB();
        int loginFlag = 0;

        System.out.println("Please Provide Username");
        String usernameInput = scan.nextLine();
        System.out.println("Please Provide Password");
        String passwordInput = scan.nextLine();
        Admin admin = new Admin(usernameInput, passwordInput);
        loginFlag = admin.login();

        if (loginFlag == 0){
            System.out.println("Please go back to the main portal to re-login");
            return;
        }

        int choice = 0;

		do {
			System.out.println("Choose the following options for admin services");
			System.out.println("\t 1) Add Holiday Dates");
            System.out.println("\t 2) Delete holiday dates");
            System.out.println("\t 3) Edit holiday dates");
			System.out.println("\t 4) Display Top 5 movie rankings by rating");
            System.out.println("\tEnter '11' to exit!");

            do {
                while (!scan.hasNextInt()) {
                    System.out.println("That's not a number!");
                    scan.next(); // this is important!
                }
                choice = scan.nextInt();
                scan.nextLine();
            } while (choice < -1);


            switch (choice) {
                case 1 -> {
                	System.out.println("Add holiday dates");
                	System.out.println("Input Date in YYYY-MM-DD format");
                	String date = scan.nextLine();
                    admin.AddHoliday(date);

                }

                case 2 -> {
                    System.out.println("Delete holiday dates");
                    System.out.println("Input Date in YYYY-MM-DD format");
                    String date = scan.nextLine();
                    admin.deleteHoliday(date);

                }

                case 3 -> {
                    System.out.println("Edit holiday dates");
                    System.out.println("Input Old Date to be edited in YYYY-MM-DD format");
                    String oldDate = scan.nextLine();
                    System.out.println("Input New Date in YYYY-MM-DD format");
                    String newDate = scan.nextLine();
                    admin.editHoliday(oldDate,newDate);

                }



                case 4 -> {
                	System.out.println("Display Top 5 movie rankings by rating");
                	admin.RankingByRating();
                }


                default -> {

                    System.out.println("Invalid Input. Try again.");
                }
            }
        } while (choice < 10);
    }















}




