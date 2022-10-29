package UserInterface;

import java.io.IOException;
import java.util.*;
import Admin.*;
import Service.*;

public class AdminUI {
	
	public void login() throws IOException {
		
		//fetch data of admin info from txt storage
		ArrayList<Admin> emptyAdminList = new ArrayList<Admin>();
	    TextDB textDB = new TextDB();
	    ArrayList<Admin> filledAdminList = textDB.ReadFromFile(emptyAdminList, "admin.txt");
	    
	    
	    //for debugging checking output
//	    for (int i = 0; i<filledAdminList.size();i++) {
//	    	System.out.println(filledAdminList.get(i).getName()+ filledAdminList.get(i).getPassword());
//	    }
	    
	    boolean flag = true;
	    
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
					return;
				}
			}
			
			System.out.println("Incorrect Username or Password.");
			System.out.println("You may enter 1 to re-login or Enter 0 if you dont want to log in anymore.");
			int temp = scan.nextInt();
			if(temp==0) {
				flag = false;
				System.out.println("Goodbye!");
			}
			
		
	    }while (flag);	
	}
	
	
	
}

