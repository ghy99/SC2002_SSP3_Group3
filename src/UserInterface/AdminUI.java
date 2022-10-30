package UserInterface;

import java.io.IOException;
import java.util.*;
import Admin.*;
import Movie.MovieTicket;
import Service.*;


public class AdminUI {
	
	public void AdminInterface()throws IOException{
		System.out.println("Welcome to the Admin Portal. ");
		//only success log in then can access other services
		int flag = login(); 
		if (flag==0) {
			return;
		}
	    
		int choice = 0;
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.println("Choose the following options for other services");
			System.out.println("\t 1) Add Holiday Dates");
			
			
            System.out.println("\tEnter '11' to exit!");

            do {
                while (!sc.hasNextInt()) {
                    System.out.println("That's not a number!");
                    sc.next(); // this is important!
                }
                choice = sc.nextInt();
                sc.nextLine();
            } while (choice < -1);
            switch (choice) {
                case 1 -> {
                	System.out.println("To Add holiday dates");
                	System.out.println("Input Date in (YYYY-MM-DD) format");
                	String date = sc.nextLine();
                	AddHoliday(date);
                	
                }
                
                default -> {
                    System.out.println("Invalid Input. Try again.");
                }
            }
        } while (choice < 10);
    }
	
	
	
	public int login() throws IOException {
			
			//fetch data of admin info from txt storage
			ArrayList<Admin> emptyAdminList = new ArrayList<Admin>();
		    TextDB textDB = new TextDB();
		    ArrayList<Admin> filledAdminList = textDB.ReadFromFile(emptyAdminList, "admin.txt");
		    
		    
		    //for debugging checking output
	//	    for (int i = 0; i<filledAdminList.size();i++) {
	//	    	System.out.println(filledAdminList.get(i).getName()+ filledAdminList.get(i).getPassword());
	//	    }
		    
		    boolean flag = true; //used for looping while loop
		    int flagNum = 0; //used to indicate if successfully logged in
		    
		    do{
		    	
				System.out.println("Please Provide Username");
				Scanner scan = new Scanner(System.in);
				String userName = scan.nextLine();
				System.out.println("Please Provide Password");
				String passWord = scan.nextLine();
				
				String dataName; //can use .equals() because string is an object
				String dataPassword;
				for (int i = 0; i<filledAdminList.size();i++) {
					dataName = filledAdminList.get(i).getName();
					dataPassword = filledAdminList.get(i).getPassword();
					if(userName.equals(dataName) && passWord.equals(dataPassword)) {
						System.out.println("Welcome " + dataName + ". You have logged in successfully to the admin portal.");
						flagNum = 1; //logged in successfully
						flag = false; //end while loop
						break; //break out of for loop, not while loop
					}
				}
				
				if(flagNum!=1) {
					System.out.println("Incorrect Username or Password.");
					System.out.println("You may enter 1 to re-login or Enter 0 if you dont want to log in anymore.");
					int temp = scan.nextInt();
					if(temp==0) {
						flagNum = 0; //Unsuccessful log in
						flag = false; //end while loop
						System.out.println("Goodbye!");
					}
				}
				
		    }while (flag);
		    
			//to return after the while loop incase while is never executed
		    return flagNum;
			
		}
	
	public void AddHoliday(String date) throws IOException {
    	TextDB.WriteToTextDB("HolidayDates.txt", date);
	}
	
	

	
}



