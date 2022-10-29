package UserInterface;

import java.io.IOException;
import java.util.*;
import Admin.*;
import Service.*;

public class AdminUI {
	
	public void login() throws IOException {
		
		ArrayList<Admin> adminList = new ArrayList<Admin>();
	    TextDB textDB = new TextDB();
	    ArrayList<Admin> filledAdminList = textDB.ReadFromFile(adminList, "admin.txt");
	    
	    for (int i = 0; i<3;i++) {
	    	System.out.println(filledAdminList.get(i).getName()+ filledAdminList.get(i).getPassword());
	    }
	    
	    
	    
	    
//		System.out.println("Please Provide UserName");
//		Scanner scan = new Scanner(System.in);
//		String userName = scan.nextLine();
//		System.out.println("Please Provide Password");
//		String passWord = scan.nextLine();

    
    
		
	// 	main->customerUI,Admininterface ->admininterface object, login
	// 	log in fn first 
	// while 
		
		//check if in text file kick them out to back to main
		//if user name and password is in text file
//		System.out.println("Hello " + userName + "Welcome Back to the Admin Portal");
//		
//		//else if userName is in text file but not password, alr have an account
//		System.out.println("Hello " + userName + "Incorrect Password. Please try log in again");
//		
//		
//		//check both username and password is in text file AGAIN
//		
//		//else create new admin account
//		System.out.println("Please create new UserName");
//		String userName = scan.nextLine();
//		System.out.println("Please create new Password");
//		String passWord = scan.nextLine();
//		//pass info into text file
//		
//		
//		Admin admin1 = new Admin("Apple","Password");
//		
//		
	}
	
}

