package AdminUI;

import java.io.*;
import java.util.*;

// import controllers.*;

public class CreateMovie {

	public static void main(String[] args) throws IOException,Exception {
		Scanner sc = new Scanner(System.in);
		
		 //Movie Name
		 //Movie Type
		 //Movie
		 //Age restriction 
		 //Synopsis 
		 //Director
		 //3d or Digital 
		 //Actors 
		
		String movieName;
		String movieType;
		String movieStatus; // can convert it to int and specify each int's meaning
		String movieRating;// rating can be out of 10 or 100
		String movieSynopsis;
		String movieDirector;
		boolean if3D;
		String[] movieCast; // need to have 2 or more cast members of the movie
		String temp; String tempString;
		
		System.out.println("------------------------");
		System.out.println("Create Movie");
		System.out.println("------------------------");
		
		System.out.print("Movie Name: ");
		movieName = sc.nextLine();
		
		System.out.println();
		System.out.println("Is the movie 3D?");
		System.out.println("1: Yes");
		System.out.println("2: No");
		System.out.println("Enter your option:");
		temp = sc.nextLine();
		
		switch(Integer.valueOf(temp)) {
		case 1: if3D = true; break;
		case 2: if3D = false; break;
		default:if3D = false;
		}
		
		System.out.println();
		System.out.println("Movie Type:");
		System.out.println("1: Action");
		System.out.println("2: Comedy");
		System.out.println("3: Drama");
		System.out.println("4: Fantasy");
		System.out.println("5: Horror");
		System.out.println("6: Mystery");
		System.out.println("7: Romance");
		System.out.println("8: Thriller");
		System.out.println("9: Western");
		temp = sc.nextLine();
		
	
		switch(Integer.valueOf(temp)) {
		case 1: movieType = "Action"; break;
		case 2: movieType = "Comedy"; break;
		case 3: movieType = "Drama"; break;
		case 4: movieType = "Fantasy"; break;
		case 5: movieType = "Mystery"; break;
		case 6: movieType = "Romance"; break;
		case 7: movieType = "Thriller"; break;
		case 8: movieType = "Western"; break;
		default: movieType = "Error"; 
		}
		
		
		System.out.println();
		System.out.println("Movie Status: ");
		System.out.println("1: Coming soon!");
		System.out.println("2: Preview");
		System.out.println("3: Now showing");
		System.out.println("4: End of showing");
		temp = sc.nextLine();
		

		switch(Integer.valueOf(temp)) {
		case 1: movieStatus = "Coming soon!"; break;
		case 2: movieStatus = "Preview"; break;
		case 3: movieStatus = "Now showing"; break;
		case 4: movieStatus = "End of showing"; break;
		default: movieStatus = "Not defined"; 
		}
		
	}

}