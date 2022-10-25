package Service;
import Customer.Customer;
import Movie.Movie;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TextDB {
    public static final String SEPARATOR = "|";
    private static final Path CurrentRelativePath = Paths.get("");
    private static final String CurrentDirectory = CurrentRelativePath.toAbsolutePath().toString();

    // an example of reading
    public static ArrayList<Customer> ReadFromFile(String fileName) {
        // read String from text file
        ArrayList stringArray = null;
        try {
            stringArray = (ArrayList)Read(fileName);

            ArrayList alr = new ArrayList() ;

            for (int i = 0 ; i < stringArray.size() ; i++) {
                String st = (String)stringArray.get(i);

                // get individual 'fields' of the string separated by SEPARATOR
                StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter "|"

                String  movieGoerName = star.nextToken().trim();
                String  mobileNumber = star.nextToken().trim();
                String  email = star.nextToken().trim();
                int  TID = Integer.parseInt(star.nextToken().trim());

                // create Professor object from file data
                Customer customer = new Customer(movieGoerName,mobileNumber, email,TID);
                // add to Professors list
                alr.add(customer) ;
            }
            return alr ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Movie> ReadFromFile(String fileName){
        // read String from text file
        ArrayList stringArray = null;
        try {
            stringArray = (ArrayList)Read(fileName);
            ArrayList alr = new ArrayList() ;

            for (int i = 0 ; i < stringArray.size() ; i++) {
                String st = (String)stringArray.get(i);

                // get individual 'fields' of the string separated by SEPARATOR
                StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter "|"

                String  movieGoerName = star.nextToken().trim();
                String  mobileNumber = star.nextToken().trim();
                String  email = star.nextToken().trim();
                int  TID = Integer.parseInt(star.nextToken().trim());

                // create Professor object from file data
                Customer customer = new Customer(movieGoerName,mobileNumber, email,TID);
                // add to Professors list
                alr.add(customer) ;
            }
            return alr ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void WriteToTextDB(String fileName, List<Customer> customerList) throws IOException {
        List alw = new ArrayList() ;// to store Professors data

        for (int i = 0 ; i < customerList.size() ; i++) {
            Customer customer = (Customer)customerList.get(i);
            StringBuilder st =  new StringBuilder() ;
            st.append(customer.getMovieGoerName().trim());
            st.append(SEPARATOR);
            st.append(customer.getMobileNumber().trim());
            st.append(SEPARATOR);
            st.append(customer.getEmail());
            st.append(SEPARATOR);
            st.append(customer.getTID());
            alw.add(st.toString()) ;
        }
        Write(fileName,alw);
    }

    /** Write fixed content to the given file. */
    public static void Write(String fileName, List customerData) throws IOException  {

        PrintWriter out = new PrintWriter(new FileWriter(CurrentDirectory +"\\src\\DataStorage\\"+ fileName));
        try {
            for (int i =0; i < customerData.size() ; i++) {
                out.println((String)customerData.get(i));
            }
        }
        finally {
            out.close();
        }
    }

    /** Read the contents of the given file. */
    public static List Read(String fileName) throws IOException {
        List data = new ArrayList() ;
        Scanner scanner = new Scanner(new FileInputStream(CurrentDirectory +"\\src\\DataStorage\\"+ fileName));
        try {
            while (scanner.hasNextLine()){
                data.add(scanner.nextLine());
            }
        }
        finally{
            scanner.close();
        }
        return data;
    }

    //DB test
    public static void main(String[] args) throws IOException {
        ArrayList al = new ArrayList();
        al.add(new Customer("Ant","12","ant@h.com", 0));
        al.add(new Customer("gdf","234","ant@h.com", 1));
        al.add(new Customer("xcv","756","ant@h.com", 2));

        //write test
        WriteToTextDB("test.txt" , al);

        //read test
        for (Customer cs : ReadFromFile("test.txt"))
        {
            System.out.println(cs.getMovieGoerName() + " " + cs.getMobileNumber() + " " + cs.getEmail()  + " " + cs.getTID());
        }
    }
}