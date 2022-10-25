package Cineplex;
import Movie.Movie;
import Service.TextDB;

import java.util.*;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Cinema {
    // This will be for Cinema, to be declared in a list in cineplex
    private String cinemaName;
    private ArrayList<Movie> listOfMovies;

    public Cinema(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public String getCinemaName() {
        return this.cinemaName;
    }
}
