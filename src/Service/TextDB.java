package Service;

import Customer.Customer;
import Movie.*;
import Cineplex.*;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
        ArrayList<String> listOfShowTime = (ArrayList) TextDB.Read(fileName);
        ArrayList<ShowTime> alr = new ArrayList<>();
        ArrayList<ArrayList<String>> temp = new ArrayList<>();
        ShowTime tempST = null;
        int rowCount = 0;

        for (int i = 0; i < listOfShowTime.size(); i++) {

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
                        String[] t1 = listOfShowTime.get(i).split(",");
                        temp.add(new ArrayList<>());
                        ArrayList<String> currentRow = temp.get(rowCount);
                        for(String s : t1)
                        {
                            if(Objects.equals( s,"@|") && count < 2)
                            {
                                aisle[count++] = rowCount;
                            }

                            if(Objects.equals(s , "null"))
                            {
                                currentRow.add(null);
                            }
                            else
                            {
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
                    tempST = new ShowTime(DateTime.StringToDate(time), m , temp , aisle);
                    break;
                }
            }

            alr.add(tempST);
        }

        return alr;

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

    //DB test
    public void main(String[] args) throws IOException {
        ArrayList<Customer> al = new ArrayList();
        al.add(new Customer("Ant", "12", "ant@h.com", 0));
        al.add(new Customer("gdf", "234", "ant@h.com", 1));
        al.add(new Customer("xcv", "756", "ant@h.com", 2));

        //write test
        WriteToTextDB("test.txt", al);

        //read test
        for (Customer cs : ReadFromFile("test.txt", al)) {
            System.out.println(cs.getMovieGoerName() + " " + cs.getMobileNumber() + " " + cs.getEmail() + " " + cs.getTID());
        }
    }

    public static String getCurrentDirectory() {
        return CurrentDirectory;
    }
}