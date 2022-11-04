package Cineplex;

import Movie.Movie;
<<<<<<< Updated upstream
import Service.GetNumberInput;
=======
import Review.AllReviews;
import Review.Review;
>>>>>>> Stashed changes
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AllCineplex {
    public  enum MovieSort{
        Top5Rating,
        Top5Sales
    }

    private final String filename = "movies.txt";
    private ArrayList<Cineplex> cineplexes;

    private ArrayList<Movie> listOfMovies ;

    private boolean isSale;
    private boolean isRating;
    public AllCineplex() throws IOException {
        InitializeCineplexes();
    }

    public ArrayList<Cineplex> getCineplexes() {
        return this.cineplexes;
    }

    public void setListOfMovies(ArrayList<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    public void addMovies(Movie movie) throws IOException {
        this.listOfMovies.add(movie);
        TextDB.UpdateTextDB(filename, listOfMovies);
        //write to file
    }

    public ArrayList<Movie> getListOfMovies() {
        return this.listOfMovies;
    }

    public void setRating(boolean rating) {
        isRating = rating;
    }

    public void setSale(boolean sale) {
        isSale = sale;
    }

    public boolean isRating() {
        return isRating;
    }

    public boolean isSale() {
        return isSale;
    }

    public void updateListOfMovies(int index, Movie movie) throws IOException {
        //write to file
        this.listOfMovies.set(index, movie);
        TextDB.UpdateTextDB(filename, this.listOfMovies);

    }

    public void removeMovie(int index, Movie movie) throws IOException {
        this.listOfMovies.set(index, movie);
        TextDB.UpdateTextDB(filename, this.listOfMovies);
    }

    public ArrayList<Movie> sortReview(MovieSort sortType)
    {
        ArrayList<Movie> tempMovie = (ArrayList<Movie>) this.listOfMovies.clone();

        switch (sortType)
        {
            case Top5Rating -> {
                tempMovie.sort(Comparator.comparing(Movie::getOverallRating));
                Collections.reverse(tempMovie);
                return tempMovie;
            }
            case Top5Sales -> {
                tempMovie.sort(Comparator.comparing(Movie::getMovieTotalSales));
                Collections.reverse(tempMovie);
                return tempMovie;
            }
            default -> {return this.listOfMovies;}
        }
    }

    public void InitializeCineplexes() throws IOException {
        Boolean[] env = TextDB.ReadFromFile(File.separator + TextDB.Files.Env.toString());
        setSale(env[0]);
        setRating(env[1]);
        System.out.println("EVN variable loaded!! \n\n");
        System.out.println("Initializing Cineplexes...\n...\n...");
        try {
            this.cineplexes = TextDB.readFromFile(File.separator + TextDB.Files.Cineplex.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        File movieFile = new File(TextDB.getCurrentDirectory() + File.separator + TextDB.Files.Movies.toString());
        if (!movieFile.exists()) movieFile.createNewFile();

        // movie instance
        ArrayList<Movie> movieList = TextDB.readFromFile(File.separator + TextDB.Files.Movies.toString(), new ArrayList<>());

        for (Cineplex cineplex : this.cineplexes) {
            setListOfMovies(movieList);
            cineplex.InitializeMovies(this.listOfMovies);
        }

        System.out.println("Cineplexes are initialized\n");
    }

    /**
     * Done by : Eddy Cheng, Gan Hao Yi
     * This Method displays the list of cineplex(Branches) available.
     */
    public void displayCineplexList() {
        for (int i = 0; i < this.cineplexes.size(); i++) {
            System.out.printf("\t%d) %s\n", i + 1, this.cineplexes.get(i).getCineplexName());
        }
        System.out.println();
    }

    /**
     * Done by : Gan Hao Yi
     * This Method displays the List of Movies currently available. User will only see Movie Title.
     * After this method, user will be able to select which movie to display more details.
     */
    public static void displayMovieList(ArrayList<Movie> list ) {
        ArrayList<Movie> movielist = list;
        for (int j = 0; j < movielist.size(); j++) {
            System.out.printf("%d) %s\n", j + 1, movielist.get(j).getMovieTitle());
        }
        int choice = 0;
        do {
            System.out.println("Enter movie number to view more movie details. (Enter -1) to return to main page.");
            choice = GetNumberInput.getInt() - 1;
            if (choice == -2) {
                break;
            } else if (choice >= movielist.size()) {
                continue;
            }
            else {
                movielist.get(choice).printMovieDetails();
                // PRINT MOVIE REVIEWS***************************************************
            }
        } while (choice >= 0);
    }
}
