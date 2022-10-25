package Cineplex;
import Movie.Movie;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
public class Cineplex {
    // This will be for Cineplexes
    private String cineplexName;
    private int regularCinemas;
    private int premiumCinemas;
    private ArrayList<Movie> listOfMovies;

    public Cineplex(String name, int regular, int premium) {
        this.cineplexName = name;
        this.regularCinemas = regular;
        this.premiumCinemas = premium;
    }

    public String getCineplexName() {
        return this.cineplexName;
    }

    public int getNoOfCinemas() {
        return this.regularCinemas + this.premiumCinemas;
    }
    public int getNoOfRegularCinemas() { return this.regularCinemas; }
    public int getNoOfPremiumCinemas() { return this.premiumCinemas; }

    public ArrayList<Movie> getListOfMovies() {
//        for (int i = 0; i < this.listOfMovies.size(); i++) {
//            this.listOfMovies.get(i).printMovieDetails();
//        }
        return this.listOfMovies;
    }

    public void InitializeMovies() throws IOException {
        System.out.println("Initializing list of movies in cinemas\n...\n...");
        TextDB db = new TextDB();
        this.listOfMovies = new ArrayList<Movie>();

        //Create dir and movie.txt if entered cineplex name doesn't exist in DataStorage dir
        File cineplexDir = new File(TextDB.getCurrentDirectory() + "\\" + this.getCineplexName().replace(' ','_'));
        if (!cineplexDir.exists()){
            cineplexDir.mkdirs();
            File movieFile = new File(cineplexDir+"\\" + TextDB.Files.Movies.ToString());
            movieFile.createNewFile();

            System.out.println("Created directory and Movies.txt. Path :" + cineplexDir+"\\" + TextDB.Files.Movies.ToString());
        }

        try {
            this.listOfMovies = db.readFromFile(  getCineplexName().replace(' ','_') + "\\" +TextDB.Files.Movies.ToString(), this.listOfMovies);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Movies are initialized.\n");
    }

    //Class Test
    public static void main(String[] args) throws IOException {
        Cineplex myC = new Cineplex("Cineplex3" ,1, 2);

        System.out.println("Initializing list of movies in cinemas\n...\n...");
        TextDB db = new TextDB();
        myC.listOfMovies = new ArrayList<Movie>();

        File cineplexDir = new File(TextDB.getCurrentDirectory() + "\\" + myC.getCineplexName().replace(' ','_'));
        if (!cineplexDir.exists()){
            cineplexDir.mkdirs();
            File movieFile = new File(cineplexDir+"\\" + TextDB.Files.Movies.ToString());
            movieFile.createNewFile();

            System.out.println("Created directory and Movies.txt. Path :" + cineplexDir+"\\" + TextDB.Files.Movies.ToString());
        }

        try {
            myC.listOfMovies = db.readFromFile(  myC.getCineplexName().replace(' ','_') + "\\" +TextDB.Files.Movies.ToString(), myC.listOfMovies);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Movie data : myC.getListOfMovies())
        {
            System.out.println(data.getMovieTitle());
        }

        System.out.println("Movies are initialized.\n");
    }
}
