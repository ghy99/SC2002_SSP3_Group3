package Admin;

import Review.OverallReview;
import Service.TextDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
	private String username;
	private String password;

	public Admin(String username, String password){
		this.username = username;
	    this.password =  password;
	}

	public String getUsername(){
		return username;
	}

	public String getPassword(){
		return password;
	}


	public int login() throws IOException {

		//fetch data of admin info from txt storage
		ArrayList<Admin> emptyAdminList = new ArrayList<Admin>();
		TextDB textDB = new TextDB();
		ArrayList<Admin> filledAdminList = textDB.ReadFromFile(emptyAdminList, "admin.txt");


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
			if(this.username.equals(dataName) && this.password.equals(dataPassword)) {
				System.out.println("Welcome " + this.username + ". You have logged in successfully to the admin portal.");
				flagNum = 1; //logged in successfully
			}
		}

		if(flagNum==0) {
			System.out.println("Incorrect Username or Password.");
		}



		return flagNum;

	}

	public void AddHoliday(String date) throws IOException {
		TextDB.WriteToTextDB("HolidayDates.txt", date);
	}

	public void RankingByRating () throws IOException {
		TextDB textDB = new TextDB();
		ArrayList<OverallReview> overallReviewList = textDB.ReadFromFile("Consolidatedreview.txt");
		System.out.println("The top 5 movies by ratings are: ");
		if (overallReviewList.size()<5){
			for(int i = 0; i<overallReviewList.size();i++) {
				System.out.println((i+1)+")" +overallReviewList.get(i).getMovieTitle() + " -> Rating: "+ overallReviewList.get(i).getavgRating());
			}
		} else{
			for(int i = 0; i<5;i++) {
				System.out.println((i+1)+")" +overallReviewList.get(i).getMovieTitle() + " -> Rating: "+ overallReviewList.get(i).getavgRating());
			}
		}
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








}
