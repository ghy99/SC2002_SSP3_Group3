package UserInterface;

import Admin.*;
import Cineplex.*;
import Service.*;


/**
 * This is the AdminUI class.it controls the Admin Interface.
 * @author TAN JUE LIN, GAN HAO YI
 */
public class AdminUI {
    /**
     * This is the Admin Interface. This is used for the admin to choose the function that they want to perform
     * @param loginFlag This flag is to edit the access for customers to display Top 5 movies.
     * @param usernameInput This input is for successful admin login by checking if the username exists in the database
     * @param passwordInput This input is for successful login by checking if the corresponding password exists in the database
     * @throws Exception is thrown if any interruption is detected in the reading admin database causes error
     */
	public static void AdminInterface(int loginFlag, String usernameInput,
                                      String passwordInput, AllCineplex cineplexes) throws Exception {
        if (loginFlag == 0){
            System.out.println("Please go back to the main portal to re-login");
            return;
        }
        System.out.println("*************************************************");
        System.out.println("*          Welcome to the Admin Portal          *");
        System.out.println("*************************************************");
        Admin admin = new Admin(usernameInput, passwordInput , true);
        int choice = 0;
		do {
			System.out.println("\nChoose the following options for Admin services:");
			System.out.println("\t1) Ticket Prices");
            System.out.println("\t2) Holiday Dates");
            System.out.println("\t3) Movie's Functions");
            System.out.println("\t4) Movie Listing by rank");
            System.out.println("\t5) Add Cinema");
            System.out.println("\t6) Other Settings");

            choice = GetNumberInput.getInt(1, 5, 11);
            cineplexes.clearScreen();
            switch (choice) {
                case 1 -> {
                    admin.EditTicket(cineplexes);
                }
                case 2 -> {
                    admin.HolidayDateFunctions(cineplexes);
                }
                case 3 -> {
                    MovieListingUI.MovieListingInterface(cineplexes);
                }
                case 4 -> {
                    System.out.println("*************************************************");
                	System.out.println("*            List Movies by Ranking             *");
                    System.out.println("*************************************************");
                    System.out.println("\nPlease select one of the following functions:");
                    System.out.println("\t1) Display Top 5 movie rankings by rating");
                    System.out.println("\t2) Display Top 5 movie rankings by ticket sales");

                    if(GetNumberInput.getInt(1, 2, -1) == 1)
                    {
                        cineplexes.printSortedList(AllCineplex.MovieSort.Top5Rating);
                    }
                    else
                    {
                        cineplexes.printSortedList(AllCineplex.MovieSort.Top5Sales);
                    }
                }
                case 5-> {
                    Cineplex chosenCineplex = null;
                    do{
                        chosenCineplex = CineplexUI.CineplexInterface(cineplexes);
                    }
                    while (chosenCineplex == null);
                    AddCinemaUI.AddCinemaInterface(chosenCineplex , cineplexes);
                }
                case 6-> {
                    admin.SettingFunctions(cineplexes);
                }
                default -> {
                    System.out.println("Invalid Input. Try again.");
                }
            }
        } while (choice < 10);
    }
}




