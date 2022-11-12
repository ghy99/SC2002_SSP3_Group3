package Service;

import Customer.*;
import Admin.*;
import Cineplex.*;
import Movie.*;
import Review.*;

import java.io.*;
import java.util.*;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

/**
 * @authors CHEW ZHI QI, GAN HAO YI
 * This Class controls the Reading / Writing / Updating to the Database.
 */
public class TextDB {
    /**
     * Enum text files name for our Textdb
     */
    public enum Files {
        Cineplex(File.separator + "Cineplexes.txt"),
        Movies(File.separator + "Movies.txt"),
        Customers(File.separator + "Customers.txt"),
        Admin(File.separator + "Admin.txt"),
        TransactionHistory(File.separator + "TransactionHistory.txt"),
        Holiday(File.separator + "HolidayDates.txt"),
        TicketPrice(File.separator + "TicketPrice.txt"),
        Env(File.separator + "env.txt");

        public final String Files;
        Files(String files) {
            this.Files = files;
        }
        @Override
        public String toString() {
            return Files;
        }
    }

    /** Common separator in textdb */
    public static final String SEPARATOR = "|";

    /** Absolute path to DataStorage  */
    private static final String CurrentDirectory = Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "DataStorage" + File.separator;

    /**
     * Read from db to retrieve list of customer
     * @param fileName Customers.txt
     * @param customers ArrayList instance
     * @param customer Identifier for overloading methods
     * @throws IOException this is thrown if the reading from the file results in error
     */
    public static void readFromFile(String fileName, ArrayList<Customer> customers, Customer customer) throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList) Read(fileName);

        for (int i = 0; i < stringArray.size(); i++) {
            String st = (String) stringArray.get(i);

            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);    // pass in the string to the string tokenizer using delimiter "|"

            String movieGoerName = star.nextToken().trim();
            String mobileNumber = star.nextToken().trim();
            String email = star.nextToken().trim();

            // create Professor object from file data
            Customer newCustomer = new Customer(movieGoerName, mobileNumber, email);
            // add to Professors list
            customers.add(newCustomer);
        }
    }

    /**
     * Read from db to retrieve list of movies
     * @param fileName Movies.txt
     * @param movies Arraylist instance will be populated
     * @throws IOException this is thrown if the reading from the file results in error
     */
    public static void readFromFile(String fileName, ArrayList<Movie> movies) throws IOException {
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
            MovieType.Genre genre = MovieType.Genre.valueOf(star.nextToken().trim());
            MovieType.Blockbuster blockbuster = MovieType.Blockbuster.valueOf(star.nextToken().trim());
            MovieType.Class movieClass = MovieType.Class.valueOf(star.nextToken().trim());
            int movieTotalSales = Integer.parseInt(star.nextToken().trim());
            Date movieStartDte = DateTime.StringToDateOnly(star.nextToken().trim());


            Movie movie = new Movie(
                    title, status, director, synopsis, casts, genre, blockbuster, movieClass, movieTotalSales,movieStartDte
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
                            movie.setReview(t1[0], Float.parseFloat(t1[1]), sb.toString());
                        } else {
                            movie.setReview(t1[0], Float.parseFloat(t1[1]), t1[2]);
                        }
                    }
                }
            }
            movies.add(movie);
        }
    }

    /**
     * Read from db to retrieve list of cineplex
     * @param filename Cineplexex.txt
     * @return A list of cineplex in db
     * @throws IOException
     */
    public static ArrayList<Cineplex> readFromFile(String filename) throws IOException {
        ArrayList<String> listofCineplexes = (ArrayList) TextDB.Read(filename);
        ArrayList<Cineplex> alr = new ArrayList<>();

        for (String listofCineplex : listofCineplexes) {

            String st = listofCineplex;
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
            String name = star.nextToken().trim();

            Cineplex cineplex = new Cineplex(name);

            String[] cinemas = star.nextToken().trim().split(",");
            for (String cinema : cinemas) {
                String[] cinTypes = cinema.split(":");
                Cinema c = new Cinema(cinTypes[0].trim(), cinTypes[1].trim(), Cinema.CinemaType.valueOf(cinTypes[2]));
                cineplex.addCinema(c);
            }
            alr.add(cineplex);
        }
        return alr;

    }

    /**
     * from db to retrieve list of movie for cineplex to refer to, read construct an ArrayList of showtime from db
     * @param movie current list of movie instance
     * @param fileName get cineplex dir and specific cinema textdb
     * @return A list of shwo time for a specific cineplex cinema.
     * @throws IOException this is thrown if the reading from the file results in error
     */
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
            int count = 0, counter = 0;
            int dobuleCout = 0, columnCount = 0;
            ArrayList<String> selectedSits = new ArrayList<>();
            //Read the 2d array seats
            while (!Objects.equals(listOfShowTime.get(i), "]")) {
                if (i + 1 < listOfShowTime.size()) {
                    i++;
                    if (!Objects.equals(listOfShowTime.get(i), "]") && !Objects.equals(listOfShowTime.get(i), "[")) {
                        String[] t1 = listOfShowTime.get(i).split(","); //seperate line by ","
                        columnCount = t1.length;
                        //For each val seperated by ","
                        for (int j = 0; j < t1.length; j++) {
                            //Check is current column an aisle
                            if (Objects.equals(t1[j], "@") && count < 2) {
                                aisle[count++] = j;
                            }
                            if ((Objects.equals(t1[j], " |") || Objects.equals(t1[j], "X|")) && dobuleCout == 0) {
                                dobuleCout = rowCount - 1;
                            }
                            if (Objects.equals(t1[j], "X") || Objects.equals(t1[j], "X|")) {
                                StringBuilder sb = new StringBuilder();
                                sb.append((char)(rowCount  + 1));
                                sb.append(j + 1);
                                selectedSits.add(sb.toString());
                            }
                        }
                        rowCount++;
                    }
                }
            }

            for (int z = 0; z< selectedSits.size();z++)
            {
                StringBuilder sb = new StringBuilder();
                sb.append ( (char)((65 + rowCount ) - selectedSits.get(z).charAt(0)));
                sb.append(selectedSits.get(z).charAt(1));
                selectedSits.set(z ,sb.toString() );
            }

            //Refrence the current Showtime to our list of movies in cinexplex
            for (Movie m : movie) {
                if (Objects.equals(m.getMovieTitle(), movieName)) {
                    alr.add(new ShowTime(DateTime.StringToDate(time), m, selectedSits, rowCount, columnCount, dobuleCout, aisle, dim));
                }
            }
        }
        return alr;
    }

    /**
     * Read from db to retrieve list of ticket prices stored in db
     * @param fileName TicketPrice.txt
     * @param charges Identifier for overload method
     * @return Return list of ticket prices stored in db
     * @throws IOException this is thrown if the reading from the file results in error
     */
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

    /**
     * Read from db to retrieve holiday list
     * @param fileName Holiday.txt
     * @param settings Identifier for overload method
     * @return Retunr list of holiday
     * @throws IOException this is thrown if the reading from the file results in error
     */
    public static ArrayList<String> ReadFromFile(String fileName, Settings settings) throws IOException {
        ArrayList<String> oldData = (ArrayList<String>) Read(fileName);
        ArrayList<String> holiday = new ArrayList<String>();

        for (int i = 0; i < oldData.size(); i++) {
            String string = (String) oldData.get(i);

            holiday.add(string);
        }

        return holiday;
    }

    /**
     * Read from db to retrieve a list of transaction history for a particular user
     * @param fileName TransactionHistory
     * @param filterEmail User to filter
     * @return Return list of movie ticket
     * @throws IOException this is thrown if the reading from the file results in error
     */
    public static ArrayList<MovieTicket> readFromFile(String fileName, String filterEmail) throws IOException {
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
            Date date = DateTime.StringToDate(star.nextToken().trim());
            String seatID = star.nextToken().trim();
            IndividualSeats.SeatType seattype = IndividualSeats.SeatType.valueOf(star.nextToken().trim());
            Cinema.CinemaType cinType = Cinema.CinemaType.valueOf(star.nextToken().trim());
            MovieType.Dimension dim = MovieType.Dimension.valueOf(star.nextToken().trim());
            MovieType.Blockbuster blockbuster = MovieType.Blockbuster.valueOf(star.nextToken().trim());

            MovieTicket movieTicket = new MovieTicket(
                    email, choosenCineplex, choosenCinema, choosenMovie,
                    tid, seatID, date, seattype, cinType, dim, blockbuster);

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

    /**
     * Read from db to retrieve list of admin. Password is hashed using SHA256
     * @param adminList Current instance admin list
     * @param fileName Admin.txt
     * @return Return list of admins
     * @throws IOException this is thrown if the reading from the file results in error
     */
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

    /**
     * Read from db to retrieve environmental variable for application
     * @param fileName env.txt
     * @return Return  environmental variable
     * @throws IOException this is thrown if the reading from the file results in error
     */
    public static Boolean[] ReadFromFile(String fileName) throws IOException {
        ArrayList<String> oldData = (ArrayList<String>) Read(fileName);
        Boolean[] flags = new Boolean[2];

        for (int i = 0; i < oldData.size(); i++) {
            flags[i] = Boolean.parseBoolean(oldData.get(i));
        }

        return flags;
    }

    /**
     * Update movie textDB
     * @param fileName Movies.txt
     * @param movie Movie to append to db
     * @throws IOException this is thrown if the reading from the file results in error
     */
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
        st.append(movie.getMovieGenre().toString().trim());
        st.append(SEPARATOR);
        st.append(movie.getBlockBuster().toString().trim());
        st.append(SEPARATOR);
        st.append(movie.getMovieClass().toString().trim());
        st.append(SEPARATOR);
        st.append(String.valueOf(movie.getMovieTotalSales()));
        st.append(SEPARATOR);
        st.append(String.valueOf(DateTime.convertDate(movie.getStartDate().getTime())));


        alw.add(st.toString());
        alw.add("[");
        alw.add("]");

        Write(fileName, alw);
    }

    /**
     * Append new customer to textDB
     * @param fileName Cusomters.txt
     * @param customer Customer to append to db
     * @throws IOException this is thrown if the reading from the file results in error
     */
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

    /**
     * Append new holiday to textDB
     * @param fileName
     * @param date
     * @throws IOException this is thrown if the reading from the file results in error
     */
    public static void WriteToTextDB(String fileName, String date) throws IOException {

        //for admin to write to add in dates into HolidayDates.txt file
        ArrayList<String> holidayList = (ArrayList<String>) Read(fileName);
        holidayList.add(date);
        Update(fileName, holidayList);

    }

    /**
     * Append new transaction to textDB
     * @param fileName TransctionHistory.txt
     * @param customer Current customer
     * @param ticket Current ticket
     * @throws IOException
     */
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
        st.append(DateTime.convertTime(ticket.getShowtime().getTime()));
        st.append(SEPARATOR);
        st.append(ticket.getSeatID());
        st.append(SEPARATOR);
        st.append(ticket.getSeattype());
        st.append(SEPARATOR);
        st.append(ticket.getCinematype());
        st.append(SEPARATOR);
        st.append(ticket.getDim());
        st.append(SEPARATOR);
        st.append(ticket.getBlockbuster());
        alw.add(st.toString());

        Write(fileName, alw);
    }

    /**
     * Update env to textDB
     * @param fileName env.txt
     * @param setting Changed settings
     * @throws IOException
     */
    public static void UpdateToTextDB(String fileName, Settings setting) throws IOException {
        List alw = new ArrayList();// to store Professors data

        StringBuilder st = new StringBuilder();
        alw.add(String.valueOf(setting.isSale()));
        alw.add(String.valueOf(setting.isRating()));

        Update(fileName, alw);
    }

    /**
     * Update holiday dates to textDB
     * @param fileName Holiday.txt
     * @param holiday New hooliday
     * @param settings Identifier for overload methods
     * @throws IOException
     */
    public static void UpdateToTextDB(String fileName, ArrayList<String> holiday, Settings settings) throws IOException {
        List alw = new ArrayList();// to store Professors data

        for (String h : holiday) {
            alw.add(h);
        }

        Update(fileName, alw);
    }

    /**
     * Update cinema db with new showtime
     * @param fileName cineplexDir + Cinema.txt
     * @param showTimes New showtime
     * @param dim New showtime 2D/3D
     * @throws IOException
     */
    public static void UpdateToTextDB(String fileName, ArrayList<ShowTime> showTimes, MovieType.Dimension dim) throws IOException {
        List alw = new ArrayList();// to store Professors data

        for (int a = 0; a < showTimes.size(); a++) {
            StringBuilder st = new StringBuilder();
            st.append(showTimes.get(a).getMovie().getMovieTitle());
            st.append(SEPARATOR);
            st.append(DateTime.convertTime(showTimes.get(a).getTime().getTime()));
            st.append(SEPARATOR);
            st.append(showTimes.get(a).getDimension().toString());
            alw.add(st.toString());
            alw.add("[");


            for (int i = 0; i < showTimes.get(a).getArray2D().size(); i++) {
                st = new StringBuilder();
                for (int j = 0; j < showTimes.get(a).getArray2D().get(i).size(); j++) {
                    if (showTimes.get(a).getArray2D().get(i).get(j).getSeatType() == IndividualSeats.SeatType.SingleSeat) {
                        if (showTimes.get(a).getArray2D().get(i).get(j).getSeatOccupied()) {
                            st.append("X");
                        } else {
                            st.append(" ");
                        }
                    }

                    if (showTimes.get(a).getArray2D().get(i).get(j).getSeatType() == IndividualSeats.SeatType.DoubleSeat) {
                        if (showTimes.get(a).getArray2D().get(i).get(j).getSeatOccupied()) {
                            st.append("X|");
                        } else {
                            st.append(" |");
                        }
                    }

                    if(showTimes.get(a).getArray2D().get(i).get(j).getSeatType() == IndividualSeats.SeatType.Aisle)
                    {
                        st.append("@");
                    }


                    if (i + 1 <showTimes.get(a).getArray2D().get(i).size()) st.append(",");
                }


                alw.add(st.toString());
            }

            alw.add("]");
        }


        Update(fileName, alw);
    }

    /**
     * Update movie db with new data
     * @param movies New movie
     * @param fileName Movies.txt
     * @throws IOException
     */
    public static void UpdateToTextDB(ArrayList<Movie> movies ,String fileName ) throws IOException {
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
            st.append(movie.getMovieGenre().toString().trim());
            st.append(SEPARATOR);
            st.append(movie.getBlockBuster().toString().trim());
            st.append(SEPARATOR);
            st.append(movie.getMovieClass().toString().trim());
            st.append(SEPARATOR);
            st.append(String.valueOf(movie.getMovieTotalSales()));
            st.append(SEPARATOR);
            st.append(String.valueOf(DateTime.convertDate(movie.getStartDate().getTime())));


            alw.add(st.toString());
            alw.add("[");
            for (Review review : movie.getListOfReview()) {
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

//        Write(fileName, alw);
        Update(fileName, alw);
    }

    /**
     * Update customer db with new data
     * @param fileName Customer.txt
     * @param customer Customer
     * @throws IOException
     */
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

    /**
     * Update cineplex db with new cinema
     * @param fileName cineplex.txt
     * @param data new cinema
     * @param cineplex Identifier for overload method
     * @throws IOException
     */
    public static void UpdateToTextDB(String fileName, ArrayList<Cineplex> data , AllCineplex cineplex) throws IOException {
        List alw = new ArrayList();// to store Professors data

        for (int i = 0; i < cineplex.getCineplexes().size(); i++) {
            StringBuilder st = new StringBuilder();
            st.append(data.get(i).getCineplexName().trim());
            st.append(SEPARATOR);
            for (int j = 0; j < data.get(i).getListOfCinemas().size(); j++)
            {
                st.append(data.get(i).getListOfCinemas().get(j).getCinemaCode());
                st.append(":");
                st.append(data.get(i).getListOfCinemas().get(j).getCinemaName());
                st.append(":");
                st.append(data.get(i).getListOfCinemas().get(j).getCinemaType());
                if(j + 1 <  data.get(i).getListOfCinemas().size())st.append(",");
            }
            alw.add(st.toString());
        }

        Update(fileName, alw);
    }

    /**
     * Update TicketPrice db with change/new value
     * @param fileName TicketPirce.txt
     * @param cat Category to change
     * @param choice Specific choice
     * @param newTicketPrice New price
     * @throws IOException
     */
    public static void UpdateToTextDB(String fileName, int cat, int choice, Double newTicketPrice) throws IOException {
        List alw = new ArrayList();
        StringBuilder st = new StringBuilder();
        ArrayList<String[][]> ticketPrices = new ArrayList<>();
        ticketPrices = TextDB.readFromFile(fileName, (TicketCharges) null);
        String[][] changingCat = ticketPrices.get(cat - 1);
        changingCat[choice][1] = newTicketPrice.toString();
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
     * Update admin db with new admin
     * @param fileName admin.txt
     * @param admins New admin
     * @param admins Identifier for overload methods
     * @throws IOException
     */
    public static void UpdateToTextDB(String fileName, ArrayList<Admin> admins , Admin admin) throws IOException {
        List adm = new ArrayList();// to store Professors data

        for (Admin adminn : admins) {
            StringBuilder st = new StringBuilder();
            st.append(adminn.getUsername().trim());
            st.append(SEPARATOR);
            st.append(adminn.getPassword().trim());
            adm.add(st.toString());
        }
        Update(fileName, adm);
    }

    /**
     * Base function for all write methods append to db
     * @param fileName File to write to
     * @param data Data to write
     * @throws IOException
     */
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

    /**
     * Base function for all updat methods to rewrite whole db
     * @param fileName File to write to
     * @param data Data to write
     * @throws IOException
     */
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

    /**
     *  Base function for all read methods
     * @param fileName Fire to read
     * @return Return read data
     * @throws IOException
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

    /**
     * Get current current working directory
     * @return Return current current working directory
     */
    public static String getCurrentDirectory() {
        return CurrentDirectory;
    }

}