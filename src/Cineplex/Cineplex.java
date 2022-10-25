package Cineplex;
import Movie.Movie;
import Service.TextDB;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
public class Cineplex {
    // This will be for Cineplexes
    private String cineplexName;
    private int noOfCinema;
    private ArrayList<Movie> listOfMovies;

    public Cineplex(String name, int noOfCinema) {
        this.cineplexName = name;
        this.noOfCinema = noOfCinema;
    }

    public String getCineplexName() {
        return this.cineplexName;
    }

    public int getNoOfCinemas() {
        return this.noOfCinema;
    }

    public ArrayList<Movie> getListOfMovies() {
//        for (int i = 0; i < this.listOfMovies.size(); i++) {
//            this.listOfMovies.get(i).printMovieDetails();
//        }
        return this.listOfMovies;
    }

    public void InitializeMovies() {
        System.out.println("Initializing list of movies in cinemas\n...\n...");
        TextDB db = new TextDB();
        this.listOfMovies = new ArrayList<Movie>();
        String filename = "Movies.txt";
        try {
            this.listOfMovies = db.readFromFile(filename, this.listOfMovies);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Movies are initialized.\n");
    }

}
