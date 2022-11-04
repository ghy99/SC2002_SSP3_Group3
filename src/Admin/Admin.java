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
 * @author Jue Lin, Sanskkriti, Chew Zhi Qi, Hao Yi
 * This is the Admin class.
 * it stores the username and password of the current admin user.
 */
public class Admin {
	private String username;
	private String password;

	public Admin(String username, String password, boolean hashed) throws NoSuchAlgorithmException {
		this.username = username;
		if(hashed)
		{
			this.password = password;
		}else {
			this.password = SHA256.toString(password);
		}
	}

	public Admin(String username, String password) throws NoSuchAlgorithmException{
		this.username = username;
		this.password = password;
	}

	public String getUsername(){
		return username;
	}

	public String getPassword(){
		return password;
	}

	public void addAdmin(Admin admin) throws IOException, NoSuchAlgorithmException {
		TextDB.WriteToTextDB(TextDB.Files.Admin.toString(), admin);
	}

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

	public void AddHoliday(String date) throws IOException {
		TextDB.WriteToTextDB("HolidayDates.txt", date);
	}

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

	public void deleteHoliday(String date) throws IOException {
		ArrayList<String> holidayList = (ArrayList<String>) TextDB.Read("HolidayDates.txt"); //extract list of holiday dates from storage
		for (int i = 0; i<holidayList.size();i++) {
			if (date.toString().equals(holidayList.get(i))) {
				holidayList.remove(i);
			}
		}

		TextDB textDB = new TextDB();
		TextDB.Update("HolidayDates.txt",holidayList);

	}


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

	public void ControlRankingDisplay() throws IOException {
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		ArrayList<String> env = new ArrayList<>();
		env.add(String.valueOf(choice));
		TextDB.Update("env.txt",env);
	}

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

	public static ArrayList<Admin> getAdminList(String filename) throws IOException, NoSuchAlgorithmException {
		ArrayList<Admin> AdminList = new ArrayList<Admin>();
		AdminList = ReadFromFile(AdminList, filename);
		return AdminList;
	}

	private static Admin AddnewAdmin() throws IOException, NoSuchAlgorithmException {
		Scanner scan = new Scanner(System.in);
		System.out.println("Create New Admin ");
		String username, password;
		System.out.println("\t Enter the new admin username");
		username = scan.nextLine();

		System.out.println("\t Enter the new admin password");
		password = scan.nextLine();

		return new Admin(username, password);
	}



	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		System.out.println(Admin.login("ant" , "12345"));
	}




}
