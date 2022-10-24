import Movie.Movie;

import java.util.ArrayList;

public class Cinema {
    // This will be for Cinema, to be declared in a list in cineplex
    private String cinemaName;
    private ArrayList<Movie> listOfMovies = new ArrayList<Movie>();

    public Cinema(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    //public AddNewMovies()

    public String getCinemaName() {
        return this.cinemaName;
    }
}
