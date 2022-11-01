package Service;


import Customer.Customer;
import Admin.*;
import Cineplex.Cinema;
import Cineplex.Cineplex;
import Cineplex.ShowTime;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import Movie.*;
import Review.*;

public class TextDB {

    public enum Files {
        Cineplex("Cineplex.txt"),
        Movies("Movies.txt"),
        ShowTime("ShowTime.txt"),
        Customers("Customers.txt");

        public final String Files;

        Files(String files) {
            this.Files = files;
        }

        public String ToString() {
            return Files;
        }
    }
    public static final String SEPARATOR = "|";
    private static final Path CurrentRelativePath = Paths.get("");
    private static final String CurrentDirectory = CurrentRelativePath.toAbsolutePath().toString() + File.separator +"src" +File.separator +"DataStorage" + File.separator;

    // an example of reading
    public static ArrayList<Customer> readFromFile(String fileName, ArrayList<Customer> customers, Customer temp) throws IOException {

        // read String from text file
        ArrayList stringArray = null;
        stringArray = (ArrayList) Read(fileName);

        ArrayList alr = new ArrayList();

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);

            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);    // pass in the string to the string tokenizer using delimiter "|"

            String movieGoerName = star.nextToken().trim();
            String mobileNumber = star.nextToken().trim();
            String email = star.nextToken().trim();
//            Double TID = Double.parseDouble(star.nextToken().trim());


            // create Professor object from file data
            Customer customer = new Customer(movieGoerName, mobileNumber, email , false);
            // add to Professors list
            customers.add(customer);
        }
        return customers;
    }

    public static ArrayList<Movie> readFromFile(String fileName, ArrayList<Movie> movies) throws IOException {
        ArrayList<String> listofMovies = (ArrayList) TextDB.Read(fileName);
        ArrayList<Movie> alr = new ArrayList<Movie>();

        for (String listofMovie : listofMovies) {
            String st = listofMovie;

            StringTokenizer star = new StringTokenizer(st, SEPARATOR);

            String title = star.nextToken().trim();
            Movie.MovieStatus status = Movie.MovieStatus.valueOf(star.nextToken().trim());
            String synopsis = star.nextToken().trim();
            String[] temp = star.nextToken().trim().split(",");
            ArrayList<String> casts = new ArrayList<>();
            Collections.addAll(casts, temp);
            Cinema.CinemaType type = Cinema.CinemaType.valueOf(star.nextToken().trim());
            MovieType.Genre genre = MovieType.Genre.valueOf(star.nextToken().trim());
            MovieType.Dimension dim = MovieType.Dimension.valueOf(star.nextToken().trim());
            MovieType.Class movieClass = MovieType.Class.valueOf(star.nextToken().trim());
            int movieTotalSales = Integer.parseInt(star.nextToken().trim()) ;

            Movie movie = new Movie(
                    title, status, synopsis, casts, type, genre, dim, movieClass,movieTotalSales
            );
            alr.add(movie);
        }
        return alr;
    }

    public static ArrayList<Cineplex> readFromFile(String filename) throws IOException {
        ArrayList<String> listofCineplexes = (ArrayList) TextDB.Read(filename);
        ArrayList<Cineplex> alr = new ArrayList<>();

        for (String listofCineplex : listofCineplexes) {

            String st = listofCineplex;
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
            String name = star.nextToken().trim();

            Cineplex cineplex = new Cineplex(name);

            String[] cinemas = star.nextToken().trim().split(", ");
            for (String cinema : cinemas) {
                String[] cinTypes = cinema.split(":");
                Cinema c = new Cinema(cinTypes[0], cinTypes[1] , Cinema.CinemaType.valueOf(cinTypes[2]));
                cineplex.addCinema(c);
            }
            alr.add(cineplex);
        }
        return alr;

    }

    public static ArrayList<ShowTime> readFromFile(ArrayList<Movie> movie, String fileName) throws IOException {
        ArrayList<String> listOfShowTime = (ArrayList) TextDB.Read(fileName);
        ArrayList<ShowTime> alr = new ArrayList<>();

        for (int i = 0; i < listOfShowTime.size(); i++) {
            int rowCount = 0;
            ArrayList<ArrayList<String>> temp = new ArrayList<>();
            String st = listOfShowTime.get(i);
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
            String movieName = star.nextToken().trim();
            String time = star.nextToken().trim();
            int[] aisle = new int[2];
            int count = 0;
            //Read the 2d array seats
            while (!Objects.equals(listOfShowTime.get(i), "]")) {
                if (i + 1 < listOfShowTime.size()) {
                    i++;
                    if (!Objects.equals(listOfShowTime.get(i), "]") && !Objects.equals(listOfShowTime.get(i), "[")) {
                        String[] t1 = listOfShowTime.get(i).split(","); //seperate line by ","
                        temp.add(new ArrayList<>()); //add in new row
                        ArrayList<String> currentRow = temp.get(rowCount);//get the column that just created
                        //For each val seperated by ","
                        for (String s : t1) {
                            //Check is current column an aisle
                            if (Objects.equals(s, "@ |") && count < 2) {
                                aisle[count++] = rowCount;
                            }
                            if (Objects.equals(s, "null")) {
                                currentRow.add(null);
                            } else {
                                currentRow.add(s);
                            }
                        }
                        rowCount++;
                    }
                }
            }
            //Refrence the current Showtime to our list of movies in cinexplex
            for (Movie m : movie) {
                if (Objects.equals(m.getMovieTitle(), movieName)) {
                    alr.add(new ShowTime(DateTime.StringToDate(time), m, temp, aisle));
                }
            }
        }
        return alr;
    }

    public static ArrayList<String[][]> readFromFile(String fileName, TicketCharges charges) throws IOException {
        // Implement read ticket price txtfile
        ArrayList<String> listOfTicketPrice = (ArrayList) TextDB.Read(fileName);
        ArrayList<String[][]> alr = new ArrayList<>();
        for (String prices : listOfTicketPrice) {
            String st = prices;
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
            String[] pricebyAge = star.nextToken().trim().split(", ");
            String[][] ageprice = new String[pricebyAge.length][];
            for (int i = 0; i < pricebyAge.length; i++) {
                String[] ageCat = pricebyAge[i].split(":");
                ageprice[i] = ageCat;
            }
            alr.add(ageprice);

            String[] dayofWeek = star.nextToken().trim().split(", ");
            String[][] dayprice = new String[dayofWeek.length][];
            for (int i = 0; i < dayofWeek.length; i++) {
                String[] dayCat = dayofWeek[i].split(":");
                dayprice[i] = dayCat;
            }
            alr.add(dayprice);

            String[] movieDim = star.nextToken().trim().split(", ");
            String[][] dimprice = new String[movieDim.length][];
            for (int i = 0; i < movieDim.length; i++) {
                String[] dimCat = movieDim[i].split(":");
                dimprice[i] = dimCat;
            }
            alr.add(dimprice);

            String[] CinemaType = star.nextToken().trim().split(", ");
            String[][] typeprice = new String[CinemaType.length][];
            for (int i = 0; i < CinemaType.length; i++) {
                String[] dimCat = CinemaType[i].split(":");
                typeprice[i] = dimCat;
            }
            alr.add(typeprice);
        }
        return alr;
    }

    public static ArrayList<Admin> ReadFromFile(ArrayList<Admin> adminList, String fileName) throws IOException, NoSuchAlgorithmException {

        // read String from text file
        //for reading from admin.txt to extract admin username and passwords
        ArrayList<String> stringArray = (ArrayList) TextDB.Read(fileName);

        for (String str : stringArray) {
            String st = str;

            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer using
            // delimiter "|"

            String userName = star.nextToken().trim();
            String password = star.nextToken().trim();

            Admin tempAdmin = new Admin(userName, password , true);
            // add to Professors list
            adminList.add(tempAdmin);
        }
        return adminList;
    }
    
    public static ArrayList<OverallReview> ReadFromFile(String fileName) throws IOException {
	
	//read from Consolidatedreview.txt
    	
    	ArrayList<String> oldData = (ArrayList<String>) Read(fileName);
		ArrayList<OverallReview> overallReviewList = new ArrayList<OverallReview>();
		
		
		for (int i = 0; i<oldData.size();i++) {
			String string = (String) oldData.get(i);

            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(string, SEPARATOR);    // pass in the string to the string tokenizer using delimiter "|"

            String movieTitle = star.nextToken().trim();
            String avgRating = star.nextToken().trim();
            String count = star.nextToken().trim();
            
            
            OverallReview overallReview = new OverallReview(movieTitle,avgRating,count);
            overallReviewList.add(overallReview);
		}
		
		overallReviewList.sort(Comparator.comparing(OverallReview::getavgRating));
		Collections.reverse(overallReviewList);
		
		return overallReviewList;
    }
    
    public static void WriteToTextDB(String fileName1,String fileName2, Review review) throws IOException {
    	
    	//write to ALLreview.txt
    	List alw = new ArrayList();
    	List alw2 = new ArrayList();
    	String rating = review.getRating();
    	String parareview = review.getReview();
    	String title =  review.getTitle();
    	
    	StringBuilder st = new StringBuilder();
    	st.append(title);
    	st.append(SEPARATOR);
    	st.append(rating);
    	st.append(SEPARATOR);
    	st.append(parareview);
    	alw.add(st.toString());
    	
    	Write(fileName1,alw);
    	
    	//convert read data into arraylists to identify where to modify
    	TextDB textDB = new TextDB();
    	ArrayList<OverallReview> overallReviewList = textDB.ReadFromFile(fileName2);
    			
        boolean found = false;
        for (int i = 0; i<overallReviewList.size();i++) {
            if (overallReviewList.get(i).getMovieTitle().equals(title)) {
                found = true;
                double oldrating = Double.parseDouble(overallReviewList.get(i).getavgRating());
                double count = Double.parseDouble(overallReviewList.get(i).getCount());
                double newRating = Double.parseDouble(rating);

                double newAvgRating = (oldrating*count+newRating)/(count+1);
                String s = String.valueOf(newAvgRating);
                overallReviewList.get(i).setavgRating(s);
                String c = String.valueOf(count+1);
                overallReviewList.get(i).setCount(c);
            }
        }

        if (found!=true) {
            OverallReview overallReview = new OverallReview(title,rating,"1");
            overallReviewList.add(overallReview);
        }

        for (int i = 0; i < overallReviewList.size(); i++) {
            OverallReview overallReview = overallReviewList.get(i);
            StringBuilder st2 = new StringBuilder();
            st2.append(overallReview.getMovieTitle().trim());
            st2.append(SEPARATOR);
            st2.append(overallReview.getavgRating().trim());
            st2.append(SEPARATOR);
            st2.append(overallReview.getCount().trim());
            st2.append(SEPARATOR);
            alw2.add(st2.toString());
        }
        //write to consolidated.txt to update ratings

        Update(fileName2,alw2);
    }

    public static void WriteToTextDB(String fileName, ArrayList<Movie> moveis) throws IOException {
        List alw = new ArrayList();// to store Professors data

        StringBuilder st = new StringBuilder();
        for (Movie movie : moveis) {
            st.append(movie.getMovieTitle().trim());
            st.append(SEPARATOR);
            st.append(movie.getShowingStatus().toString().trim());
            st.append(SEPARATOR);
            st.append(movie.getSynopsis().trim());
            st.append(SEPARATOR);
            for (int i = 0; i < movie.getCast().size(); i++) {
                st.append(movie.getCast().get(i));
                if (i + 1 < movie.getCast().size()) st.append(",");
            }
            st.append(SEPARATOR);
            st.append(movie.getTypeOfCinema().toString().trim());
            st.append(SEPARATOR);
            st.append(movie.getMovieGenre().toString().trim());
            st.append(SEPARATOR);
            st.append(movie.getMovie3D().toString().trim());
            st.append(SEPARATOR);
            st.append(movie.getMovieClass().toString().trim());
            st.append(SEPARATOR);
            st.append(String.valueOf(movie.getMovieTotalSales()));


        }
        alw.add(st.toString());

        Write(fileName, alw);
    }

    public static void WriteToTextDB(String fileName, Admin admin) throws IOException, NoSuchAlgorithmException {
        List alw = new ArrayList();// to store Professors data



        StringBuilder st = new StringBuilder();
        st.append(admin.getUsername());
        st.append(SEPARATOR);
        st.append(SHA256.toString(admin.getPassword()));
        alw.add(st.toString());

        Write(fileName, alw);
    }

    public static void WriteToTextDB(String fileName, Cineplex cineplex) throws IOException {
        List alw = new ArrayList();// to store Professors data

        StringBuilder st = new StringBuilder();

        st.append(SEPARATOR);

        for (int i = 0; i < cineplex.getListOfCinemas().size(); i++) {
            Cinema cinema = cineplex.getListOfCinemas().get(i);
            st.append(cinema.getCinemaName().trim());
            st.append(':');
            st.append(cinema.getCinemaType().toString().trim());

            if (i + 1 < cineplex.getListOfCinemas().size()) st.append(',');

        }
        alw.add(st.toString());

        Write(fileName, alw);
    }

    public static void WriteToTextDB(String fileName, Movie movie, ArrayList<ShowTime> showTimes) throws IOException {
        List alw = new ArrayList();// to store Professors data

        StringBuilder st = new StringBuilder();
        for (ShowTime showTime : showTimes) {
            st.append(movie.getMovieTitle());
            st.append(SEPARATOR);
            st.append(DateTime.convertTime(showTime.time.getTime()));
            st.append(SEPARATOR);
            alw.add(st.toString());
            alw.add("[");

            for (String[] row : showTime.getSeats()) {
                st = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    st.append(row[i]);
                    if (i + 1 < row.length) st.append(",");
                }
                alw.add(st);
            }

            alw.add("]");
        }

        Write(fileName, alw);
    }

    /*
    TEST THIS FUNCTION WHEN I COME BACK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    * */
    public static void WriteToTextDB(String fileName, int cat, int choice, Double newTicketPrice) throws IOException {
        List alw = new ArrayList();
        StringBuilder st = new StringBuilder();
        ArrayList<String[][]> ticketPrices = new ArrayList<>();
        ticketPrices = TextDB.readFromFile(fileName, (TicketCharges) null);
        String[][] changingCat = ticketPrices.get(cat - 1);
        changingCat[choice - 1][1] = newTicketPrice.toString();
        for (int i = 0; i < ticketPrices.size(); i++) {
            if (i == cat - 1) {
                for (int j = 0; j < changingCat.length; j++) {
                    st.append(changingCat[j][0]);
                    st.append(":");
                    st.append(changingCat[j][1]);
                    if (j + 1 < changingCat.length) st.append(", ");
                }
            }
            else {
                String[][] temp = ticketPrices.get(i);
                for (int j = 0; j < temp.length; j++) {
                    st.append(temp[j][0]);
                    st.append(":");
                    st.append(temp[j][1]);
                    if (j + 1 < temp.length) st.append(", ");
                }
            }
            if (i + 1 < ticketPrices.size()) st.append(" " + SEPARATOR + " ");
        }

        alw.add(st.toString());
        Update(fileName, alw);
    }

    public static void WriteToTextDB(String fileName, String date) throws IOException {

        //for admin to write to add in dates into HolidayDates.txt file
        ArrayList<String> holidayList = (ArrayList<String>) Read("HolidayDates.txt");
        holidayList.add(date);
        Update(fileName, holidayList);

    }

    public static void WriteToTextDB(String fileName ,Customer customer) throws IOException {
        List alw = new ArrayList();// to store Professors data

        StringBuilder st = new StringBuilder();
        st.append(customer.getMovieGoerName().trim());
        st.append(SEPARATOR);
        st.append(customer.getMobileNumber().trim());
        st.append(SEPARATOR);
        st.append(customer.getEmail());
        alw.add(st.toString());

        Write(fileName, alw);
    }

    public static void Write(String fileName, List data) throws IOException {


        ArrayList<String> oldData = (ArrayList<String>) Read(fileName);

        if (oldData.size() > 0) {
            for (Object d : data) {
                oldData.add(d.toString());
            }
        }

        for (Object d : data) {
            oldData.add((String) d);
        }

        PrintWriter out = new PrintWriter(new FileWriter(CurrentDirectory + fileName));

        try {
            for (int i = 0; i < oldData.size(); i++) {
                out.println((String) oldData.get(i));
            }
        } finally {
            out.close();
        }
    }

    public static void Update(String fileName, List data) throws IOException {


        PrintWriter out = new PrintWriter(new FileWriter(CurrentDirectory + fileName));
        try {
            for (int i = 0; i < data.size(); i++) {
                out.println((String) data.get(i));
            }
        } finally {
            out.close();
        }
    }

    /**
     * Read the contents of the given file.
     */
    public static List Read(String fileName) throws IOException {
        List data = new ArrayList();
        Scanner scanner = new Scanner(new FileInputStream(CurrentDirectory + fileName));
        try {
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
        } finally {
            scanner.close();
        }
        return data;
    }

    public static String getCurrentDirectory() {
        return CurrentDirectory;
    }

    public static void main(String[] args) throws IOException {
    }

}