package Service;
import Customer.Customer;

import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.util.*;

public class TextDB {
    public static final String SEPARATOR = "|";

    // an example of reading
    public static ArrayList<Customer> readFromFile(String filename) throws IOException {
        // read String from text file
        ArrayList stringArray = (ArrayList)read(filename);
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
    }

    public static void writeToTextDB(String filename, List<Customer> al) throws IOException {
        List alw = new ArrayList() ;// to store Professors data

        for (int i = 0 ; i < al.size() ; i++) {
            Customer customer = (Customer)al.get(i);
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
        write(filename,alw);
    }

    /** Write fixed content to the given file. */
    public static void write(String fileName, List data) throws IOException  {
        PrintWriter out = new PrintWriter(new FileWriter(fileName));

        try {
            for (int i =0; i < data.size() ; i++) {
                out.println((String)data.get(i));
            }
        }
        finally {
            out.close();
        }
    }

    /** Read the contents of the given file. */
    public static List read(String fileName) throws IOException {
        List data = new ArrayList() ;
        Scanner scanner = new Scanner(new FileInputStream(fileName));
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
        writeToTextDB("test.txt" , al);

        //read test
        for (Customer cs : readFromFile("test.txt"))
        {
            System.out.println(cs.getMovieGoerName() + " " + cs.getMobileNumber() + " " + cs.getEmail()  + " " + cs.getTID());
        }
    }
}