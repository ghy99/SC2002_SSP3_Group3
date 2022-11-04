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

        Cineplex(File.separator + "Cineplexes.txt"),
        Movies(File.separator + "Movies.txt"),
        Customers(File.separator + "Customers.txt"),
        Admin(File.separator + "Admin.txt"),
        TransactionHistroy(File.separator + "TransactionHistory.txt"),
        Env(File.separator+"env.txt");


        public final String Files;

        Files(String files) {
            this.Files = files;
        }

        @Override
        public String toString() {
            return Files;
        }
    }

    public static final String SEPARATOR = "|";
    private static final Path CurrentRelativePath = Paths.get("");
    private static final String CurrentDirectory = CurrentRelativePath.toAbsolutePath().toString() + File.separator + "src" + File.separator + "DataStorage" + File.separator;

    public static ArrayList<Customer> readFromFile(String fileName, ArrayList<Customer> customers, Customer temp) throws IOException {

        // read String from text file
        ArrayList stringArray = (ArrayList) Read(fileName);

        ArrayList alr = new ArrayList();

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);

            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);    // pass in the string to the string tokenizer using delimiter "|"

            String movieGoerName = star.nextToken().trim();
            String mobileNumber = star.nextToken().trim();
            String email = star.nextToken().trim();

            // create Professor object from file data
            Customer customer = new Customer(movieGoerName, mobileNumber, email, false, false);
            // add to Professors list
            customers.add(customer);
        }
        return customers;
    }

    public static ArrayList<Movie> readFromFile(String fileName, ArrayList<Movie> movies) throws IOException {
        ArrayList<String> listofMovies = (ArrayList) TextDB.Read(fileName);
        ArrayList<Movie> alr = new ArrayList<Movie>();

        for (int i = 0; i < listofMovies.size(); i++) {
            String st = listofMovies.get(i);

            StringTokenizer star = new StringTokenizer(st, SEPARATOR);

            String title = star.nextToken().trim();
            Movie.MovieStatus status = Movie.MovieStatus.valueOf(star.nextToken().trim());
            String director = star.nextToken().trim();
            String synopsis = star.nextToken().trim();
            String[] temp = star.nextToken().trim().split(",");
            ArrayList<String> casts = new ArrayList<>();
            Collections.addAll(casts, temp);
            Cinema.CinemaType type = Cinema.CinemaType.valueOf(star.nextToken().trim());
            MovieType.Genre genre = MovieType.Genre.valueOf(star.nextToken().trim());
            MovieType.Blockbuster blockbuster = MovieType.Blockbuster.valueOf(star.nextToken().trim());
            MovieType.Class movieClass = MovieType.Class.valueOf(star.nextToken().trim());
            int movieTotalSales = Integer.parseInt(star.nextToken().trim());


            Movie movie = new Movie(
                    title, status, director, synopsis, casts, type, genre, blockbuster, movieClass, movieTotalSales
            );

            while (!Objects.equals(listofMovies.get(i), "]")) {
                if (i + 1 < listofMovies.size()) {
                    i++;
                    if (!Objects.equals(listofMovies.get(i), "]") && !Objects.equals(listofMovies.get(i), "[")) {
                        String[] t1 = listofMovies.get(i).split("\\|");
                        if (t1.length > 3) {
                            StringBuilder sb = new StringBuilder();
                            int j = 1;
                            while (j < t1.length) {
                                sb.append(t1[j]);
                                j++;
                            }
                            movie.setReview(t1[0],Float.parseFloat(t1[1]), sb.toString());
                        } else {
                            movie.setReview(t1[0],Float.parseFloat(t1[1]), t1[2]);
                        }
                    }
                }
            }


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
                Cinema c = new Cinema(cinTypes[0], cinTypes[1], Cinema.CinemaType.valueOf(cinTypes[2]));
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
            MovieType.Dimension dim = MovieType.Dimension.valueOf(star.nextToken().trim());
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
                    alr.add(new ShowTime(DateTime.StringToDate(time), m, temp, aisle, dim));
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

            Admin tempAdmin = new Admin(userName, password, true);
            // add to Professors list
            adminList.add(tempAdmin);
        }
        return adminList;
    }

    public static ArrayList<MovieTicket> ReadFromFile(String fileName, String filterEmail) throws IOException {
        ArrayList<String> oldData = (ArrayList<String>) Read(fileName);
        ArrayList<MovieTicket> movieTicketList = new ArrayList<MovieTicket>();

        for (int i = 0; i < oldData.size(); i++) {
            String string = (String) oldData.get(i);

            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(string, SEPARATOR);    // pass in the string to the string tokenizer using delimiter "|"

            String email = star.nextToken().trim();
            String tid = star.nextToken().trim();
            String choosenCineplex = star.nextToken().trim();
            String choosenCinema = star.nextToken().trim();
            String choosenMovie = star.nextToken().trim();
            String date = star.nextToken().trim();
            String seatID = star.nextToken().trim();

            MovieTicket movieTicket = new MovieTicket(email, choosenCineplex, choosenCinema, choosenMovie, seatID, tid, date);

            if (Objects.equals(email, "")) {
                movieTicketList.add(movieTicket);
            } else {
                if (Objects.equals(email, filterEmail)) {
                    movieTicketList.add(movieTicket);
                }
            }
        }

        return movieTicketList;
    }

    public static Boolean[] ReadFromFile(String fileName) throws IOException {
        ArrayList<String> oldData = (ArrayList<String>) Read(fileName);
        Boolean[] flags = new Boolean[2];

        for (int i = 0; i < oldData.size(); i++) {
          flags[i] = Boolean.parseBoolean(oldData.get(i)) ;
        }

        return flags;
    }

    public static void WriteToTextDB(String fileName, Movie movie) throws IOException {

        List alw = new ArrayList();// to store Professors data

        StringBuilder st = new StringBuilder();
        st.append(movie.getMovieTitle().trim());
        st.append(SEPARATOR);
        st.append(movie.getShowingStatus().toString().trim());
        st.append(SEPARATOR);
        st.append(movie.getDirector().trim());
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
        st.append(movie.getBlockBuster().toString().trim());
        st.append(SEPARATOR);
        st.append(movie.getMovieClass().toString().trim());
        st.append(SEPARATOR);
        st.append(String.valueOf(movie.getMovieTotalSales()));


        alw.add(st.toString());
        alw.add("[");
        alw.add("]");

        Write(fileName, alw);
    }

    public static void WriteToTextDB(String fileName, Customer customer) throws IOException {
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

    public static void UpdateToTextDB(String fileName , Boolean isSale , Boolean isRating) throws IOException {
        List alw = new ArrayList();// to store Professors data

        StringBuilder st = new StringBuilder();
        alw.add(isSale.toString());
        alw.add(isRating.toString());

        Update(fileName, alw);
    }

    public static void UpdateToTextDB(String fileName, ArrayList<Movie> movies, Review reviews) throws IOException {
        List alw = new ArrayList();// to store Professors data

        for (Movie movie : movies) {
            StringBuilder st = new StringBuilder();
            st.append(movie.getMovieTitle().trim());
            st.append(SEPARATOR);
            st.append(movie.getShowingStatus().toString().trim());
            st.append(SEPARATOR);
            st.append(movie.getDirector().trim());
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
            st.append(movie.getBlockBuster().toString().trim());
            st.append(SEPARATOR);
            st.append(movie.getMovieClass().toString().trim());
            st.append(SEPARATOR);
            st.append(String.valueOf(movie.getMovieTotalSales()));


            alw.add(st.toString());
            alw.add("[");
            for (Review review : movie.getListOfReview())
            {
                st = new StringBuilder();
                st.append(review.getUserName());
                st.append(SEPARATOR);
                st.append(review.getRating());
                st.append(SEPARATOR);
                st.append(review.getReview());
                alw.add(st.toString());
            }
            alw.add("]");
        }

        Update(fileName, alw);
    }

    public static void UpdateToTextDB(String fileName, ArrayList<ShowTime> showTimes, MovieType.Dimension dim) throws IOException {
        List alw = new ArrayList();// to store Professors data

        for (int a = 0; a < showTimes.size(); a++) {
            StringBuilder st = new StringBuilder();
            st.append(showTimes.get(a).getMovie().getMovieTitle());
            st.append(SEPARATOR);
            st.append(DateTime.convertTime(showTimes.get(a).getTime().getTime()));
            st.append(SEPARATOR);
            st.append(dim.toString());
            alw.add(st.toString());
            alw.add("[");

            for (String[] row : showTimes.get(a).getSeats()) {
                st = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    st.append(row[i]);
                    if (i + 1 < row.length) st.append(",");
                }
                alw.add(st.toString());
            }

            alw.add("]");
        }


        Update(fileName, alw);
    }

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
            } else {
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

    public static void UpdateTextDB(String fileName, ArrayList<Movie> movies) throws IOException {
        List alw = new ArrayList();// to store Professors data


        for (Movie movie : movies) {
            StringBuilder st = new StringBuilder();
            st.append(movie.getMovieTitle().trim());
            st.append(SEPARATOR);
            st.append(movie.getShowingStatus().toString().trim());
            st.append(SEPARATOR);
            st.append(movie.getDirector().trim());
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
            st.append(movie.getMovieClass().toString().trim());
            st.append(SEPARATOR);
            st.append(String.valueOf(movie.getMovieTotalSales()));
            alw.add(st.toString());
        }
        Update(fileName, alw);
    }

    public static void WriteToTextDB(String fileName, String date) throws IOException {

        //for admin to write to add in dates into HolidayDates.txt file
        ArrayList<String> holidayList = (ArrayList<String>) Read(fileName);
        holidayList.add(date);
        Update(fileName, holidayList);

    }

    public static void WriteToTextDB(String fileName, Customer customer, MovieTicket ticket) throws IOException {
        List alw = new ArrayList();// to store Professors data

        StringBuilder st = new StringBuilder();
        st.append(customer.getEmail());
        st.append(SEPARATOR);
        st.append(ticket.getTID());
        st.append(SEPARATOR);
        st.append(ticket.getChosenCineplex());
        st.append(SEPARATOR);
        st.append(ticket.getCinema());
        st.append(SEPARATOR);
        st.append(ticket.getChosenMovie());
        st.append(SEPARATOR);
        st.append(ticket.getShowtime());
        st.append(SEPARATOR);
        st.append(ticket.getSeatID());
        alw.add(st.toString());

        Write(fileName, alw);
    }

    public static void UpdateToTextDB(String fileName, ArrayList<Customer> customer) throws IOException {
        List alw = new ArrayList();// to store Professors data


        for (int i = 0; i < customer.size(); i++) {
            StringBuilder st = new StringBuilder();
            st.append(customer.get(i).getMovieGoerName().trim());
            st.append(SEPARATOR);
            st.append(customer.get(i).getMobileNumber().trim());
            st.append(SEPARATOR);
            st.append(customer.get(i).getEmail());
            alw.add(st.toString());
        }

        Update(fileName, alw);
    }

    public static void Write(String fileName, List data) throws IOException {

        PrintWriter out = new PrintWriter(new FileWriter(CurrentDirectory + fileName, true));

        try {
            for (int i = 0; i < data.size(); i++) {
                out.println((String) data.get(i));
            }
        } finally {
            out.close();
        }
    }

    public static void Update(String fileName, List data) throws IOException {

        PrintWriter out = new PrintWriter(new FileWriter(CurrentDirectory + fileName, false));
        try {
            for (int i = 0; i < data.size(); i++) {
                out.println((String) data.get(i));
            }
        } finally {
            out.close();
        }
    }

    public static void UpdateToTextDB(String fileName, Movie movie, ArrayList<ShowTime> showTimes) throws IOException {
        List alw = new ArrayList();// to store Professors data

        for (ShowTime showTime : showTimes) {
            StringBuilder st = new StringBuilder();
            st.append(showTime.getMovie().getMovieTitle());
            st.append(SEPARATOR);
            st.append(DateTime.convertTime(showTime.getTime().getTime()));
            st.append(SEPARATOR);
            st.append(showTime.getDimension());
            alw.add(st.toString());
            alw.add("[");

            for (String[] row : showTime.getSeats()) {
                st = new StringBuilder();
                for (int i = 0; i < row.length; i++) {
                    st.append(row[i]);
                    if (i + 1 < row.length) st.append(",");
                }
                alw.add(st.toString());
            }

            alw.add("]");
        }

        Update(fileName, alw);
    }

    /*
    TEST THIS FUNCTION WHEN I COME BACK!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    * */
    public static void UpdateToTextDB(String fileName, int cat, int choice, Double newTicketPrice) throws IOException {
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
            } else {
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

    public static void UpdateAdmin(String fileName,ArrayList <Admin> admins) throws IOException {
        List adm = new ArrayList();// to store Professors data

        for (Admin admin : admins) {
            StringBuilder st = new StringBuilder();
            st.append(admin.getUsername().trim());
            st.append(SEPARATOR);
            st.append(admin.getPassword().trim());
            adm.add(st.toString());
        }
        Update(fileName, adm);
    }


    public static String getCurrentDirectory() {
        return CurrentDirectory;
    }

}