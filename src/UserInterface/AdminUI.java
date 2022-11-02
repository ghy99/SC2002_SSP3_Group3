package UserInterface;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import Admin.*;
import Movie.MovieTicket;
import Service.*;
import Review.*;



public class AdminUI {
	
	public static void AdminInterface(int loginFlag ,String usernameInput , String passwordInput) throws Exception {
        System.out.println("Welcome to the Admin Portal. ");
        //only success log in then can access other services

        Scanner scan = new Scanner(System.in);

        if (loginFlag == 0){
            System.out.println("Please go back to the main portal to re-login");
            return;
        }

        Admin admin = new Admin(usernameInput, passwordInput , true);
        int choice = 0;
        int choice2 = 0;

		do {
			System.out.println("Choose the following options for admin services");
			System.out.println("\t 1) Ticket Prices");
            System.out.println("\t 2) Holiday Dates");
            System.out.println("\t 3) Movie's Functions");
            System.out.println("\t 4) Movie Listing by rank");
            System.out.println("\t 5) Other Settings");
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
                	System.out.println("1) Ticket Prices, Please select one of the following functions");
                    //call function to edit ticket prices
                }

                case 2 -> {
                    System.out.println("2) Holiday Dates, Please select one of the following functions");
                    System.out.println("\t 1. Add Holiday Dates");
                    System.out.println("\t 2. Edit Holiday Dates");
                    System.out.println("\t 3. Delete Holiday Dates");
                    choice2 = scan.nextInt();
                    admin.HolidayDateFunctions(choice2);
                }

                case 3 -> {
                    MovieListingUI.MovieListingInterface();
                    //function for movies

                }



                case 4 -> {
                	System.out.println("4) Movie Listing by rank. Please select one of the following functions ");
                    System.out.println("\t 1.Display Top 5 movie rankings by rating");
                    System.out.println("\t 2.Display Top 5 movie rankings by ticket sales");
                    choice2 = scan.nextInt();
                    admin.RankingFunctions(choice2);
                	//admin.RankingByRating();
                }


                default -> {

                    System.out.println("Invalid Input. Try again.");
                }
            }
        } while (choice < 10);
    }


















}




