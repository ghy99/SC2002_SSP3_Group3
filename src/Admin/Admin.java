package Admin;

import Cineplex.Cineplex;
import Movie.Movie;
import Movie.TicketCharges;
import Service.GetNumberInput;
import Service.SHA256;
import Service.TextDB;
import Review.Review;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import static Service.TextDB.ReadFromFile;
import static Service.TextDB.UpdateAdmin;

/**
 * @author CHEW ZHI QI, GAN HAO YI, SANSKKRITI JAIN, TAN JUE LIN
 * This is the Admin class. It stores the username and password of the current admin user.
 */
public class Admin {
	private String username;
	private String password;

	/**
	 * This method is used to return the username and hashed password
	 * @param username = username the admin has entered
	 * @param password = password the admin has entered
	 * @param hashed = used to keep the password secure
	 * @throws NoSuchAlgorithmException This exception is thrown to ensure SHA256 is available
	 */
	public Admin(String username, String password, boolean hashed) throws NoSuchAlgorithmException {
		this.username = username;
		if(hashed)
		{
			this.password = password;
		}else {
			this.password = SHA256.toString(password);
		}
	}

	/**
	 * Accessor of Username
	 * @return username as string
	 */
	public String getUsername(){
		return username;
	}

	/**
	 * Accessor of Password
	 * @return password as string
	 */
	public String getPassword(){
		return password;
	}

	public void addAdmin(Admin admin) throws IOException, NoSuchAlgorithmException {
		TextDB.WriteToTextDB(TextDB.Files.Admin.toString(), admin);
	}

	/**
	 * This is a function for the user to login
	 * @param username the username to be verified
	 * @param password the password to be verified
	 * @return boolean value depicting whether login was successful or not
	 * @throws IOException if file not found
	 * @throws NoSuchAlgorithmException if SHA256 algorithm not available
	 */
	public static int login(String username, String password) throws IOException, NoSuchAlgorithmException {
		//fetch data of admin info from txt storage
		ArrayList<Admin> emptyAdminList = new ArrayList<Admin>();
		ArrayList<Admin> filledAdminList = ReadFromFile(emptyAdminList, "admin.txt");

		password = SHA256.toString(password);

		//for debugging checking output
		for (int i = 0; i<filledAdminList.size();i++) {
			System.out.println(filledAdminList.get(i).getUsername() + filledAdminList.get(i).getPassword());
		}

		int flagNum = 0; //used to indicate if successfully logged in

		String dataName;
		String dataPassword;

		for (int i = 0; i<filledAdminList.size();i++) {
			dataName = filledAdminList.get(i).getUsername();
			dataPassword = filledAdminList.get(i).getPassword();
			if(username.equals(dataName) && password.equals(dataPassword)) {
				System.out.println("Welcome " + username + ". You have logged in successfully to the admin portal.");
				flagNum = 1; //logged in successfully
			}
		}

		if(flagNum==0) {
			System.out.println("Incorrect Username or Password.");
		}

		return flagNum;

	}

	//implement function for ticket prices HERE
	/**
	 * This function is to change the ticket pricing according
	 * @throws IOException thrown if reading data from TicketPrice causes error
	 */
	public void EditTicket() throws IOException {
		Scanner sc = new Scanner(System.in);
		String file = "TicketPrice.txt";
		System.out.println("Tickets are charged in the following manner:");
		TicketCharges charges = new TicketCharges();
		charges.printTicketCharges();
		int cat = 0;
		do {
			System.out.println("Select category that you want to change: (Enter -1 to exit)");
			System.out.println("1) Age");
			System.out.println("2) Day of the week");
			System.out.println("3) Movie Dimension");
			System.out.println("4) Type of Cinema");
			cat = GetNumberInput.getInt();
			if (cat == -1) {
				break;
			}
			System.out.println("Which do you want to edit:");
			switch (cat) {
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
			int choice = GetNumberInput.getInt();
			System.out.println("What is the new value:");
			Double newvalue = GetNumberInput.getDouble();
			TextDB.UpdateToTextDB(file, cat, choice, newvalue);
		} while (cat != -1);
	}

	/**
	 * This function is used to manage the add/edit and delete functions for the Holiday Dates
	 * @param choice2 for the user to input what they want to do
	 * @throws IOException to ensure the input has no error
	 */
	public void HolidayDateFunctions(int choice2) throws IOException {
		Scanner scan = new Scanner(System.in);
		switch(choice2){
			case 1->{
				System.out.println("\t 1. Add Holiday Dates");
				System.out.println("Input Date in YYYY-MM-DD format");
				String date = scan.nextLine();
				AddHoliday(date);
			}
			case 2->{
				System.out.println("\t 2. Edit holiday dates");
				System.out.println("Input Old Date to be edited in YYYY-MM-DD format");
				String oldDate = scan.nextLine();
				System.out.println("Input New Date in YYYY-MM-DD format");
				String newDate = scan.nextLine();
				editHoliday(oldDate,newDate);
			}
			case 3->{
				System.out.println("\t 3. Delete Holiday Dates");
				System.out.println("Input Date in YYYY-MM-DD format");
				String date = scan.nextLine();
				deleteHoliday(date);
			}
		}
	}

	/**
	 * This function is to add the date into the HolidayDates database
	 * @param date = the user input of the date they want to add in the database
	 * @throws IOException This Exception is thrown if reading file causes error.
	 */
	public void AddHoliday(String date) throws IOException {
		TextDB.WriteToTextDB("HolidayDates.txt", date);
	}

	/**
	 * This function is used to update/change any dates in the database
	 * @param oldDate = the original data in the database before changing
	 * @param newDate = the input that needs to be changed in the database
	 * @throws IOException This Exception is thrown if reading file causes error.
	 */
	public void editHoliday(String oldDate, String newDate) throws IOException {
		ArrayList<String> holidayList = (ArrayList<String>) TextDB.Read("HolidayDates.txt"); //extract list of holiday dates from storage
		for (int i = 0; i<holidayList.size();i++) {
			if (oldDate.toString().equals(holidayList.get(i))) {
				holidayList.remove(i);
				holidayList.add(i,newDate);
			}
		}
		TextDB textDB = new TextDB();
		TextDB.Update("HolidayDates.txt",holidayList);

	}

	/**
	 * This function is used to delete any stored holiday date in the database
	 * @param date = the users input of the date they want to delete from the database
	 * @throws IOException this is thrown if the reading from the file results in error
	 */
	public void deleteHoliday(String date) throws IOException {
		ArrayList<String> holidayList = (ArrayList<String>) TextDB.Read("HolidayDates.txt"); //extract list of holiday dates from storage
		for (int i = 0; i<holidayList.size();i++) {
			if (date.toString().equals(holidayList.get(i))) {
				holidayList.remove(i);
			}
		}
<<<<<<< Updated upstream
		TextDB textDB = new TextDB();
=======

>>>>>>> Stashed changes
		TextDB.Update("HolidayDates.txt",holidayList);

	}

<<<<<<< Updated upstream

	//implement function for MOVIES HERE

//	public void RankingFunctions(int choice2) throws IOException{
//		switch(choice2){
//			case 1->{
//				System.out.println("\t 1.Display Top 5 movie rankings by rating");
//				Review.RankingByRating();
//			}
//			case 2->{
//				System.out.println("\t 2.Display Top 5 movie rankings by ticket sales");
//				Review.RankingByTicketSales();
//			}
//		}
//	}
	/**
	 * This function is used to get the users input on what they want to do in the setting menu
	 * @param choice2 = the input of the function they want to do
	 * @throws IOException is thrown if the reading of the input causes error
	 * @throws NoSuchAlgorithmException is thrown if the function is not found
	 */
=======
>>>>>>> Stashed changes
	public void SettingFunctions(int choice2) throws IOException, NoSuchAlgorithmException {
		Scanner scan = new Scanner(System.in);
		switch(choice2){
			case 1->{
				System.out.println("\t 1. Control the display of movie rankings to customers");
				System.out.println("\t \t - Enter 1 to display by rating\n" +
						"\t \t - Enter 2 to display by ticket sales\n" +
						"\t \t - Enter 3 to display both\n");
				ControlRankingDisplay();
			}
			case 2->{
				System.out.println("\t 2.Help new staffs to register new Admin Account");
				CreateAdmin();
			}

		}
	}

	/**
	 * This function is used for the admin to control how they want to display the rankings
	 * @throws IOException if the user input read from the file causes error
	 */
	public void ControlRankingDisplay() throws IOException {
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		ArrayList<String> env = new ArrayList<>();
		env.add(String.valueOf(choice));
		TextDB.Update("env.txt",env);
	}

	/**
	 * This function is used to create new admin accounts
	 * @throws IOException is thrown to ensure theh
	 * @throws NoSuchAlgorithmException
	 */
	private void CreateAdmin() throws IOException, NoSuchAlgorithmException {
		String filename = "admin.txt";
		ArrayList<Admin> AdminList = null;

		AdminList = getAdminList(filename);
		Scanner sc = new Scanner(System.in);
		Admin newAdmin = null;
		newAdmin = AddnewAdmin();
		ArrayList<Admin> admins = new ArrayList<Admin>();
		AdminList.add(newAdmin);
		UpdateAdmin(filename, AdminList);
	}

	/**
	 * This function is used to read the data from the Admin.txt database
	 * @param filename = the file admin.txt that needs to be accessed
	 * @return the new created admin list object
	 * @throws IOException is thrown if the reading the content of the file causes error
	 * @throws NoSuchAlgorithmException is thrown if accessing teh database causes error
	 */
	public static ArrayList<Admin> getAdminList(String filename) throws IOException, NoSuchAlgorithmException {
		ArrayList<Admin> AdminList = new ArrayList<Admin>();
		AdminList = ReadFromFile(AdminList, filename);
		return AdminList;
	}

	/**
	 * This is used to get the details from the admin of the new account they want to create
	 * @return Admin Object with the new username and password
	 * @throws NoSuchAlgorithmException is thrown if the reading the SHA256 algorithm causes an error
	 */
	private static Admin AddnewAdmin() throws  NoSuchAlgorithmException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Create New Admin");
		String username, password;
		System.out.println("\t Enter the new admin username");
		username = scan.nextLine();
		System.out.println("\t Enter the new admin password");
		password = SHA256.toString(scan.nextLine());
		return new Admin(username, password, true);
	}

	/**
	 * This is used to create a default admin account in the main system
	 * @param args = used to accept the given username and password as a String Array
	 * @throws NoSuchAlgorithmException thrown if implementing the login causes error
	 * @throws IOException is thrown if reading the given string value causes error
	 */
	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		System.out.println(Admin.login("ant" , "12345"));
	}



}
