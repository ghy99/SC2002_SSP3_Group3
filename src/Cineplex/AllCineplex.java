package Cineplex;

import Movie.Movie;
import Review.AllReviews;
import Service.GetNumberInput;
import Service.Settings;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * * @author CHEW ZHI QI, GAN HAO YI
 * * This is the Admin class inherits from Settings. It stores list of cineplex,list of movies and settings for the user.
 */
public class AllCineplex extends Settings {
    public enum MovieSort {
        Top5Rating,
        Top5Sales
    }

    private final String filename = "movies.txt";
    private ArrayList<Cineplex> cineplexes;

    private ArrayList<Movie> listOfMovies;

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


    public void updateListOfMovies(int index, Movie movie) throws IOException {
        //write to file
        this.listOfMovies.set(index, movie);
        TextDB.UpdateTextDB(filename, this.listOfMovies);

    }

    public void removeMovie(int index, Movie movie) throws IOException {
        this.listOfMovies.set(index, movie);
        TextDB.UpdateTextDB(filename, this.listOfMovies);
    }

    private ArrayList<Movie> sortReview(MovieSort sortType) {
        ArrayList<Movie> tempMovie = (ArrayList<Movie>) this.listOfMovies.clone();

        switch (sortType) {
            case Top5Rating -> {
                System.out.println("############Top 5 sale#############");
                tempMovie.sort(Comparator.comparing(Movie::getOverallRating));
                Collections.reverse(tempMovie);
                return tempMovie;
            }
            case Top5Sales -> {
                System.out.println("############Top 5 rating############");
                tempMovie.sort(Comparator.comparing(Movie::getMovieTotalSales));
                Collections.reverse(tempMovie);
                return tempMovie;
            }
            default -> {
                return this.listOfMovies;
            }
        }
    }

    public void printSortedList(MovieSort movieSort) {
        ArrayList<Movie> movies = sortReview(movieSort);

        if (movieSort.equals(MovieSort.Top5Sales)) {
            if (movies.size() > 5) {
                for (int i = 0; i < 5; i++) {
                    System.out.printf("%s %s %d \n", i + 1, movies.get(i).getMovieTitle(), movies.get(i).getMovieTotalSales());
                }
            } else {
                for (int i = 0; i < movies.size(); i++) {
                    System.out.printf("%s %s %d \n", i + 1, movies.get(i).getMovieTitle(), movies.get(i).getMovieTotalSales());
                }
            }
        } else {
            if (movies.size() > 5) {
                for (int i = 0; i < 5; i++) {
                    System.out.printf("%s %s %.1f \n", i + 1, movies.get(i).getMovieTitle(), movies.get(i).getOverallRating());
                }
            } else {
                for (int i = 0; i < movies.size(); i++) {
                    System.out.printf("%s %s %.1f \n", i + 1, movies.get(i).getMovieTitle(), movies.get(i).getOverallRating());
                }
            }
        }
        System.out.println();
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
    public static void displayMovieList(ArrayList<Movie> list) {
        ArrayList<Movie> movielist = list;

        int choice = 0;
        do {
            for (int j = 0; j < movielist.size(); j++) {
                System.out.printf("%d) %s\n", j + 1, movielist.get(j).getMovieTitle());
            }
            System.out.println("Enter movie number to view more movie details. (Enter -1) to return to main page.");
            choice = GetNumberInput.getInt() - 1;
            if (choice == -2) {
                break;
            } else if (choice >= movielist.size()) {
                continue;
            } else {
                movielist.get(choice).printMovieDetails();

                System.out.println("\tIn what order would you like to see the reviews?  (Enter -1) to return to main page.");
                System.out.println("\t1) Newest to oldest");
                System.out.println("\t2) Oldest to newest");
                System.out.println("\t3) Highest rating to lowest rating");
                System.out.println("\t4) Lowest rating to highest rating");
                switch (GetNumberInput.getInt()) {
                    case 1 -> {
                        movielist.get(choice).printSortedReview(AllReviews.ReviewSort.NewToOld);
                    }
                    case 2 -> {
                        movielist.get(choice).printSortedReview(AllReviews.ReviewSort.OldToNew);
                    }
                    case 3 -> {
                        movielist.get(choice).printSortedReview(AllReviews.ReviewSort.HighestToLowest);
                    }
                    case 4 -> {
                        movielist.get(choice).printSortedReview(AllReviews.ReviewSort.LowestToHigest);
                    }
                    default -> {
                        return;
                    }
                }
            }
        } while (choice >= 0);
    }
}
