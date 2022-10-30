package Service;

import Customer.Customer;
import Admin.*;
import Cineplex.Cinema;
import Cineplex.Cineplex;
import Cineplex.ShowTime;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Semaphore;

import Movie.*;

public class TextDB {

    public enum Files {
        Movies("Movies.txt"),
        ShowTime("ShowTime.txt");

        public final String Files;

        Files(String files) {
            this.Files = files;
        }

        public String ToString() {
            return Files;
        }
    }

    public enum StroageDir {
        Cusomter("Customer\\");

        public final String StroageDir;

        StroageDir(String stroageDir) {
            this.StroageDir = stroageDir;
        }

        public String ToString() {
            return StroageDir;
        }
    }

    public static final String SEPARATOR = "|";
    private static final Path CurrentRelativePath = Paths.get("");
    private static final String CurrentDirectory = CurrentRelativePath.toAbsolutePath().toString() + "\\src\\DataStorage\\";

    private static Semaphore sem = new Semaphore(1);

    // an example of reading
    public ArrayList<Customer> ReadFromFile(String fileName, ArrayList<Customer> customers) throws IOException {

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
            int TID = Integer.parseInt(star.nextToken().trim());


            // create Professor object from file data
            Customer customer = new Customer(movieGoerName, mobileNumber, email, TID);
            // add to Professors list
            customers.add(customer);
        }
        return customers;
    }

    public ArrayList<Movie> readFromFile(String fileName, ArrayList<Movie> movies) throws IOException {
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
            MovieType.Type type = MovieType.Type.valueOf(star.nextToken().trim());
            MovieType.Genre genre = MovieType.Genre.valueOf(star.nextToken().trim());
            MovieType.Dimension dim = MovieType.Dimension.valueOf(star.nextToken().trim());
            MovieType.Class movieClass = MovieType.Class.valueOf(star.nextToken().trim());

            Movie movie = new Movie(
                    title, status, synopsis, casts, type, genre, dim, movieClass
            );
            movies.add(movie);
        }
        return movies;
    }

    public ArrayList<Cineplex> readFromFile(String filename) throws IOException {
        ArrayList<String> listofCineplexes = (ArrayList) TextDB.Read(filename);
        ArrayList<Cineplex> alr = new ArrayList<>();

        for (String listofCineplex : listofCineplexes) {

            String st = listofCineplex;
            StringTokenizer star = new StringTokenizer(st, SEPARATOR);
            String name = star.nextToken().trim();

            Cineplex cineplex = new Cineplex(name);

            String[] cinemas = star.nextToken().trim().split(",");
            for (String cinema : cinemas) {
                Cinema c = new Cinema(cinema.split(":")[0], Cinema.CinemaType.valueOf(cinema.split(":")[1]));
                cineplex.addListOfCinema(c);
            }
            alr.add(cineplex);
        }
        return alr;

    }

    public ArrayList<ShowTime> readFromFile(ArrayList<Movie> movie, String fileName) throws IOException {
        //Temp variable for stroing data
        ArrayList<String> listOfShowTime = (ArrayList) TextDB.Read(fileName); //Read file line by line
        ArrayList<ShowTime> alr = new ArrayList<>(); //Temp Showtime list to link to object
        ArrayList<ArrayList<String>> temp = new ArrayList<>(); //Temp 2D arraylist for seats
        ShowTime tempST = null; //Temp Showtime object
        int rowCount = 0; //For updating current row count

        //Loop trough all lines
        for (int i = 0; i < listOfShowTime.size(); i++) {

            //Get each line
            String st = listOfShowTime.get(i);

            StringTokenizer star = new StringTokenizer(st, SEPARATOR); //For current with seprator (|)
            String movieName = star.nextToken().trim(); //Take string from start to (|)
            String time = star.nextToken().trim(); //Take 2nd value from 1st (|) to 2nd (|)
            int[] aisle = new int[2];//For storing aisle
            int count = 0;//To update aisle count

            //Read the 2d array seats until first ] found
            while (!Objects.equals(listOfShowTime.get(i), "]")) {
                if (i + 1 < listOfShowTime.size()) { //if i not bigger than total line size keep increasing
                    i++;
                    //if line is not "[" or "]"
                    if (!Objects.equals(listOfShowTime.get(i), "]") && !Objects.equals(listOfShowTime.get(i), "[")) {
                        String[] t1 = listOfShowTime.get(i).split(","); //seperate line by ","
                        temp.add(new ArrayList<>()); //add in new row
                        ArrayList<String> currentRow = temp.get(rowCount);//get the column that just created
                        //For each val seperated by ","
                        for (String s : t1) {
                            //Check is current column an aisle
                            if (Objects.equals(s, "@|") && count < 2) {
                                aisle[count++] = rowCount;
                            }

                            //if current val is null add in null else add in value
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
                    tempST = new ShowTime(DateTime.StringToDate(time), m, temp, aisle);
                    break;
                }
            }
            alr.add(tempST);
        }
        return alr;
    }

    public static ArrayList<ArrayList<Double>> readFromFile(String fileName, MovieTicket ticket) throws IOException {
        // Implement read ticket price txtfile
        ArrayList<String> listOfTicketPrice = (ArrayList) TextDB.Read(fileName);
        ArrayList<ArrayList<Double>> alr = new ArrayList<>();

        for (String prices : listOfTicketPrice) {
            ArrayList<Double> storePriceTypes = new ArrayList<Double>();
            StringTokenizer star = new StringTokenizer(prices, SEPARATOR);
            String stringChargingPrices = star.nextToken().trim();
            String[] temp = star.nextToken().trim().split(",");
            for (String priceType : temp) {
                storePriceTypes.add(Double.parseDouble(priceType));
            }
            alr.add(storePriceTypes);
        }

        return alr;
    }

    public ArrayList<Admin> ReadFromFile(ArrayList<Admin> adminList, String fileName) throws IOException {

        // read String from text file
        ArrayList<String> stringArray = (ArrayList) TextDB.Read(fileName);

        for (String str : stringArray) {
            String st = str;

            // get individual 'fields' of the string separated by SEPARATOR
            StringTokenizer star = new StringTokenizer(st, SEPARATOR); // pass in the string to the string tokenizer using
            // delimiter "|"

            String userName = star.nextToken().trim();
            String password = star.nextToken().trim();

            Admin tempAdmin = new Admin(userName, password);
            // add to Professors list
            adminList.add(tempAdmin);
        }
        return adminList;
    }


    public static void WriteToTextDB(String fileName, Cineplex cineplex) throws IOException {
        List alw = new ArrayList();// to store Professors data

        StringBuilder st = new StringBuilder();
        st.append(cineplex.getCineplexName().trim());
        st.append(SEPARATOR);

        for (int i = 0; i < cineplex.getListOfCinemas().size(); i++) {
            Cinema cinema = cineplex.getListOfCinemas().get(i);
            st.append(cinema.getCinemaName().trim());
            st.append(':');
            st.append(cinema.getCinemaType().toString().trim());

            if (i + 1 < cineplex.getListOfCinemas().size()) st.append(',');

            alw.add(st.toString());
        }
        Write(fileName, alw);
    }

    public static void WriteToTextDB(String fileName, List<Customer> customerList) throws IOException {
        List alw = new ArrayList();// to store Professors data

        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = (Customer) customerList.get(i);
            StringBuilder st = new StringBuilder();
            st.append(customer.getMovieGoerName().trim());
            st.append(SEPARATOR);
            st.append(customer.getMobileNumber().trim());
            st.append(SEPARATOR);
            st.append(customer.getEmail());
            st.append(SEPARATOR);
            st.append(customer.getTID());
            alw.add(st.toString());
        }
        Write(fileName, alw);
    }

    public static void Write(String fileName, List data) throws IOException {

        ArrayList<String> oldData = (ArrayList<String>) Read(fileName);

        for (Object d : data) {
            oldData.add((String) d);
        }

        FileWriter t = new FileWriter(CurrentDirectory + fileName);
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

    public static boolean isFileUnlocked(File file) {
        try {
            FileInputStream in = new FileInputStream(file);
            if (in != null) in.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public static String getCurrentDirectory() {
        return CurrentDirectory;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> test = new ArrayList<>();
        test.add("IT|19-08-2022; 03:34:23");
        test.add("[");
        test.add("null,1,2,3,4,5,null,null");
        test.add("E,|,@|,X|,@ X|,|,|,E");
        test.add("D,|,@|,|,@ |,|,|,");
        test.add("C,|,@|,|,@ |,|,|,");
        test.add("B,|,@|,|,@ |,|,X|,B");
        test.add("A,|,@|,X|,@|,X|,|,A");
        test.add("]");
        Write("Shaw_Theatre\\cinema2.txt", test);
    }

}