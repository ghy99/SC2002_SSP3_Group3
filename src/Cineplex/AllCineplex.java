package Cineplex;

import Customer.Customer;
import Movie.Movie;
import Review.AllReviews;
import Service.GetNumberInput;
import Service.Settings;
import Service.TextDB;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * * @author CHEW ZHI QI, GAN HAO YI
 * * This is the Admin class inherits from Settings.
 * It stores list of cineplex,list of movies and settings for the user.
 */
public class AllCineplex extends Settings {
    /**
     * enum to check type of sorting (Rating / Sales)
     */
    public enum MovieSort {
        Top5Rating,
        Top5Sales
    }

    /**
     * Filename for as directory path
     * ArrayList of Cineplexes to store in Global
     * ArrayList of Movies to store in Global
     */
    private final String filename = "movies.txt";
    private ArrayList<Cineplex> cineplexes;
    private ArrayList<Movie> listOfMovies;

    /**
     * Constructor for AllCineplex
     *
     * @throws IOException
     */
    public AllCineplex() throws IOException {
        InitializeCineplexes();
    }

    /**
     * Get Method
     *
     * @return ArrayList of Cineplex
     */
    public ArrayList<Cineplex> getCineplexes() {
        return this.cineplexes;
    }

    /**
     * Set Method
     *
     * @param listOfMovies - Set the List of Movies the Cineplexes are showing
     */
    public void setListOfMovies(ArrayList<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    /**
     * This method initializes the cineplex.
     * It reads the cineplex names stored and load it into the Cineplex ArrayList.
     *
     * @throws IOException to check if Cineplexes exist in Database
     */
    public void InitializeCineplexes() throws IOException {
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
        this.setListOfMovies(movieList);
        updateUpdateMovieStat();

        for (Cineplex cineplex : this.cineplexes) {
            cineplex.InitializeMovies(this.listOfMovies);
        }
        System.out.println("Cineplexes are initialized!\n");
        dailyTask();
    }

    /**
     * This method adds new movies created by Admin to Global list of movies and updates the database.
     *
     * @param movie - Newly created movie to be added.
     * @throws IOException - Checks if Movie Database exists.
     */
    public void addMovies(Movie movie) throws IOException {
        this.listOfMovies.add(movie);
        TextDB.WriteToTextDB(filename, movie);
    }

    /**
     * This method returns full list of movies including Coming Soon and End Of Showing Movies.
     *
     * @return List of All Movies.
     */
    public ArrayList<Movie> getListOfMoviesforAdmin() {
        return this.listOfMovies;
    }

    /**
     * This method gets a list of filtered movie excluding Coming Soon and End Of Showing Movies.
     *
     * @return Filtered list of movies, only show Preview and Now Showing status.
     */
    public ArrayList<Movie> getListOfMovies() {
        ArrayList<Movie> filteredMovies = new ArrayList<Movie>();
        for (Movie movie : this.listOfMovies) {
            if (movie.getShowingStatus() != Movie.MovieStatus.EndOfShowing) {
                if (movie.getShowingStatus() != Movie.MovieStatus.ComingSoon) {
                    filteredMovies.add(movie);
                }
            }
        }
        return filteredMovies;
    }

    /**
     * This method updates the list of movie with the latest updates changed by Admin.
     *
     * @param index - Index of the movie that was changed.
     * @param movie - Movie that was changed.
     * @throws IOException - Check if movie database exists.
     */
    public void updateListOfMovies(int index, Movie movie) throws IOException {
        this.listOfMovies.set(index, movie);
        TextDB.UpdateTextDB(filename, this.listOfMovies);

    }

    /**
     * This method changes the Showing Status of a movie into a EndOfShowing Status.
     * It will not be displayed anymore unless edited by Admin.
     *
     * @param index - Index of Movie in a list of all movies.
     * @param movie - Selected movie to be removed from Showing Status.
     * @throws IOException - Exception if movie database does not exist.
     */
    public void removeMovie(int index, Movie movie) throws IOException {
        this.listOfMovies.set(index, movie);
        TextDB.UpdateTextDB(filename, this.listOfMovies);
    }

    /**
     * This method sorts the List of Top 5 movies by either the Rating or the Sales.
     *
     * @param sortType - and ArrayList of sorted movies.
     * @return - A resorted list of movie.
     */
    private ArrayList<Movie> sortReview(MovieSort sortType) {
        ArrayList<Movie> tempMovie = (ArrayList<Movie>) this.listOfMovies.clone();

        switch (sortType) {
            case Top5Rating -> {
                System.out.println(Settings.ANSI_RED);
                System.out.println("*************************************************");
                System.out.println("*             Top 5 Movies by Rating            *");
                System.out.println("*************************************************");
                System.out.println(Settings.ANSI_RESET);
                tempMovie.sort(Comparator.comparing(Movie::getOverallRating));
                Collections.reverse(tempMovie);
                return tempMovie;
            }
            case Top5Sales -> {
                System.out.println(Settings.ANSI_RED);
                System.out.println("*************************************************");
                System.out.println("*          Top 5 Movies by Ticket Sale          *");
                System.out.println("*************************************************");
                System.out.println(Settings.ANSI_RESET);
                tempMovie.sort(Comparator.comparing(Movie::getMovieTotalSales));
                Collections.reverse(tempMovie);
                return tempMovie;
            }
            default -> {
                return this.listOfMovies;
            }
        }
    }

    /**
     * This method prints the sorted list of movie according to Rating / Sales
     *
     * @param movieSort - A sorted ArrayList of the Top 5 Movies
     */
    public void printSortedList(MovieSort movieSort) {
        ArrayList<Movie> movies = sortReview(movieSort);
        if (movieSort.equals(MovieSort.Top5Sales)) {
            if (movies.size() > 5) {
                for (int i = 0; i < 5; i++) {
                    System.out.printf("\t%s %s %d\n", i + 1, movies.get(i).getMovieTitle(), movies.get(i).getMovieTotalSales());
                }
            } else {
                for (int i = 0; i < movies.size(); i++) {
                    System.out.printf("\t%s %s %d\n", i + 1, movies.get(i).getMovieTitle(), movies.get(i).getMovieTotalSales());
                }
            }
        } else {
            if (movies.size() > 5) {
                for (int i = 0; i < 5; i++) {
                    System.out.printf("\t%s %s %.1f\n", i + 1, movies.get(i).getMovieTitle(), movies.get(i).getOverallRating());
                }
            } else {
                for (int i = 0; i < movies.size(); i++) {
                    System.out.printf("\t%s %s %.1f\n", i + 1, movies.get(i).getMovieTitle(), movies.get(i).getOverallRating());
                }
            }
        }
        System.out.println();
    }

    /**
     * This method updates the Showing Status of the movie daily at 12AM.
     *
     * @throws IOException - Updates database on the Showing Status of the movie.
     */
    private void updateUpdateMovieStat() throws IOException {
        for (Movie currMovie : this.getListOfMovies()) {
            if (currMovie.getShowingStatus() != Movie.MovieStatus.EndOfShowing) {
                Date currDate = new Date();
                if (currMovie.getStartDate().getTime() <= currDate.getTime()) {
                    currMovie.setShowingStatus(Movie.MovieStatus.NowShowing);
                } else if (currMovie.getStartDate().getTime() - currDate.getTime() < 604800000) {
                    currMovie.setShowingStatus(Movie.MovieStatus.Preview);
                } else {
                    currMovie.setShowingStatus(Movie.MovieStatus.ComingSoon);
                }
            }
        }
        TextDB.UpdateTextDB(filename, this.listOfMovies);
    }

    /**
     * Updates current list of Movies' Showing Status at 12AM.
     */
    private void dailyTask() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
// every night at 2am you run your task
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Show Time of Movies are updated at 12AM.");
                try {
                    updateUpdateMovieStat();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)); // period: 1 day
        System.out.println(today.getTime());
    }

    /**
     * This Method displays the list of cineplex(Branches) available.
     */
    public void displayCineplexList() {
        for (int i = 0; i < this.cineplexes.size(); i++) {
            System.out.printf("\t%d) %s\n", i + 1, this.cineplexes.get(i).getCineplexName());
        }
        System.out.println();
    }

    /**
     * This method adds a newly created customer account into the database.
     *
     * @param customer - New customer
     * @throws IOException - Exception if opening customers.txt has error.
     */
    public void createCustomerAccount(Customer customer) throws IOException {
        this.getCustomerlist().add(customer);
        TextDB.WriteToTextDB("Customers.txt", customer);
        System.out.println("An account has been created for you!");
        System.out.println("You may login from the main page with the name and phone number that you entered. Thank you.");
    }

    /**
     * This Method displays the List of Movies currently available. User will only see Movie Title.
     * After this method, user will be able to select which movie to display more details.
     */
    public static void displayMovieList(ArrayList<Movie> list) {
        ArrayList<Movie> movielist = list;

        int choice = 0;
        do {
            for (int j = 0; j < movielist.size(); j++) {
                System.out.printf("\t%d) %s\t(Ratings: %.1f)\n", j + 1, movielist.get(j).getMovieTitle(), movielist.get(j).getOverallRating());
            }
            System.out.println("\nEnter movie number to view more movie details:");
            choice = GetNumberInput.getInt(1, movielist.size(), -1) - 1;
            if (choice == -2) {
                break;
            } else if (choice >= movielist.size()) {
                continue;
            } else {
                movielist.get(choice).printMovieDetails();

                System.out.println("\nIn what order would you like to see the reviews?");
                System.out.println("\t1) Newest to oldest");
                System.out.println("\t2) Oldest to newest");
                System.out.println("\t3) Highest rating to lowest rating");
                System.out.println("\t4) Lowest rating to highest rating");
                switch (GetNumberInput.getInt(1, 4, -1)) {
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