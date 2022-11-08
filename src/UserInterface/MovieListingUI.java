package UserInterface;

import java.io.IOException;
import java.util.*;
import Cineplex.*;
import Movie.*;
import Service.GetNumberInput;

/**
 * This is the Movie Listing Interface.
 * This is imported to call the interface for the admin to be able to add/edit/delete the movie listing
 *
 * @author GAN HAO YI, SANSKKRITI JAIN
 */
public class MovieListingUI {
    /**
     * This is the Movie Listing Interface for the admin to access the Movie database and make any changes
     *
     * @param cineplexes = includes the movies listed at the various cineplexes
     * @throws Exception is thrown if there is an error in the implementation of any method.
     */
    public static void MovieListingInterface(AllCineplex cineplexes) throws Exception {
        int num = -1;
        ArrayList<Movie> listOfMovies = cineplexes.getListOfMoviesforAdmin();
        do {
            System.out.println("*************************************************");
            System.out.println("*                Movie Functions                *");
            System.out.println("*************************************************");
            System.out.println("\nSelect option for Movie Function:");
            System.out.println("\t1) Create a Movie Listing");
            System.out.println("\t2) Update a Movie Listing");
            System.out.println("\t3) Delete a Movie Listing");
            System.out.println("\t4) Main Menu (Select this to exit)");
            System.out.print("Please enter a choice: ");
            num = GetNumberInput.getInt(1, 4, 4);

            switch (num) {
                case 1 -> {
                    Movie newMovie = createMovie();
                    cineplexes.addMovies(newMovie);
                    CinemaUI.UserInterface(cineplexes, 1, newMovie);
                }
                case 2 -> {
                    System.out.println("*************************************************");
                    System.out.println("*               Modify New Movie                *");
                    System.out.println("*************************************************");
                    int choice;
                    printMovieList(listOfMovies);
                    System.out.println("\nWhich movie would you like to edit?");
                    choice = GetNumberInput.getInt(1, listOfMovies.size(), -1) - 1;
                    if (choice == -2) break;
                    Movie movie = cineplexes.getListOfMovies().get(choice);
                    modifyMovie(movie);
                    movie.printMovieDetails();
                    cineplexes.updateListOfMovies(choice, movie);

                    System.out.printf("Would you like to change the ShowTime of this movie %s?\n", movie.getMovieTitle());
                    System.out.println("\t1) Yes\n\t2) No");
                    int showtimechoice = GetNumberInput.getInt(1, 2, -1);
                    if (showtimechoice == 1) {
                        System.out.printf("Would you like to add or update the ShowTime of this movie %s?\n", movie.getMovieTitle());
                        System.out.println("\t1) Add ShowTime\n\t2) Update ShowTime");
                        showtimechoice = GetNumberInput.getInt(1, 2, -1);
                        CinemaUI.UserInterface(cineplexes, showtimechoice, movie);
                    }
                }
                case 3 -> {
                    System.out.println("*************************************************");
                    System.out.println("*                Removing Movie                 *");
                    System.out.println("*************************************************");
                    int choice;
                    System.out.println("\nWhich movie would you like to edit?");
                    printMovieList(listOfMovies);
                    choice = GetNumberInput.getInt(1, listOfMovies.size(), -1) - 1;
                    if (choice == -2) break;
                    Movie deletingMovie = deleteMovie(listOfMovies.get(choice));
                    cineplexes.removeMovie(choice, deletingMovie);
                }
            }
        } while (num != 4);
        System.out.println();
    }

    /**
     * This function is implemented to print the entire movies database
     *
     * @param listOfMovies = contains the movies list and all details
     * @throws IOException is thrown if there is an error in reading the file contents
     */
    public static void printMovieList(ArrayList<Movie> listOfMovies) {
        for (int i = 0; i < listOfMovies.size(); i++) {
            System.out.printf("%d)\n", i + 1);
            listOfMovies.get(i).printMovieDetails();
            System.out.println();
        }
    }

    /**
     * This method creates a new movie and returns an ArrayList of 2 movies
     *
     * @return ArrayList of 2 movies, the first a 3D Movie, the second a 2D Movie.
     */
    public static Movie createMovie() {
        Scanner sc = new Scanner(System.in);
        System.out.println("*************************************************");
        System.out.println("*               Create New Movie                *");
        System.out.println("*************************************************");

        String movieTitle = null, director = null, synopsis = null;
        do {
            System.out.print("\nEnter movie title: "); //movie title
            movieTitle = sc.nextLine();
        } while (movieTitle == null);
        System.out.println("\nSelect option for Movie Status (Enter -1 to exit): ");
        System.out.println("\t1) Coming soon!");
        System.out.println("\t2) Preview");
        System.out.println("\t3) Now showing");
        System.out.println("\t4) End of showing");
        Movie.MovieStatus moviestatus = null;
        int statuschoice = 0;
        do {
            statuschoice = GetNumberInput.getInt(1, 4, -1);
            if (statuschoice == -1) {
                System.out.println("Try again.");
                continue;
            }
            switch (statuschoice) {
                case 1 -> moviestatus = Movie.MovieStatus.ComingSoon;
                case 2 -> moviestatus = Movie.MovieStatus.Preview;
                case 3 -> moviestatus = Movie.MovieStatus.NowShowing;
                case 4 -> moviestatus = Movie.MovieStatus.EndOfShowing;
            }
            if (moviestatus == null) continue;
        } while (statuschoice > 4 || statuschoice < 1);

        System.out.print("\nEnter the name of the Movie Director: ");
        director = sc.nextLine();

        System.out.print("\nEnter Movie Synopsis: ");
        synopsis = sc.nextLine();

        // set cast
        ArrayList<String> cast = null;
        System.out.print("\nEnter casts, separate with semicolon(,)");
        String[] castArray = sc.nextLine().split(",");
        cast = new ArrayList<>();
        Collections.addAll(cast, castArray);

        System.out.println("\nSelect Cinema Type:");
        System.out.println("\t1) Regular");
        System.out.println("\t2) Premium");
        System.out.println("Enter your option:");
        Cinema.CinemaType cinemaType = null;
        int cinChoice;
        do {
            cinChoice = GetNumberInput.getInt(1, 2, -1);
            if (cinChoice == -1) {
                System.out.println("Try again.");
                continue;
            }
            switch (cinChoice) {
                case 1 -> cinemaType = Cinema.CinemaType.Regular;
                case 2 -> cinemaType = Cinema.CinemaType.Premium;
            }
        } while (cinemaType == null);

        System.out.println("\nSelect Movie Genre:");
        System.out.println("\t1) Action");
        System.out.println("\t2) Comedy");
        System.out.println("\t3) Drama");
        System.out.println("\t4) Fantasy");
        System.out.println("\t5) Horror");
        System.out.println("\t6) Mystery");
        System.out.println("\t7) Romance");
        System.out.println("\t8) Thriller");
        System.out.println("\t9) Western");
        MovieType.Genre genre = null;
        int genrechoice;
        do {
            genrechoice = GetNumberInput.getInt(1, 9, -1);
            if (genrechoice == -1) {
                System.out.println("Try again.");
                continue;
            }
            switch (genrechoice) {
                case 1 -> genre = MovieType.Genre.Action;
                case 2 -> genre = MovieType.Genre.Comedy;
                case 3 -> genre = MovieType.Genre.Drama;
                case 4 -> genre = MovieType.Genre.Horror;
                case 5 -> genre = MovieType.Genre.Fantasy;
                case 6 -> genre = MovieType.Genre.Mystery;
                case 7 -> genre = MovieType.Genre.Romance;
                case 8 -> genre = MovieType.Genre.Thriller;
                case 9 -> genre = MovieType.Genre.Western;
            }
        } while (genre == null);

        System.out.println("\nIs the movie a BLOCKBUSTER?");
        System.out.println("\t1) Yes");
        System.out.println("\t2) No");
        System.out.println("Enter your option:");
        MovieType.Blockbuster blockbuster = null;
        int dimChoice;
        do {
            dimChoice = GetNumberInput.getInt(1, 2, -1);
            if (dimChoice == -1) {
                System.out.println("Try again.");
                continue;
            }
            switch (dimChoice) {
                case 1 -> blockbuster = MovieType.Blockbuster.BLOCKBUSTER;
                case 2 -> blockbuster = MovieType.Blockbuster.NOTBLOCKBUSTER;
            }
        } while (blockbuster == null);

        System.out.println("\nMovie Age Rating:");
        System.out.println("\t1) G");
        System.out.println("\t2) PG");
        System.out.println("\t3) PG13");
        System.out.println("\t4) NC16");
        System.out.println("\t5) M18");
        System.out.println("\t6) R21");
        int ratingchoice;
        MovieType.Class ratings = null;
        do {
            ratingchoice = GetNumberInput.getInt(1, 6, -1);
            switch (ratingchoice) {
                case 1 -> ratings = MovieType.Class.G;
                case 2 -> ratings = MovieType.Class.PG;
                case 3 -> ratings = MovieType.Class.PG13;
                case 4 -> ratings = MovieType.Class.NC16;
                case 5 -> ratings = MovieType.Class.M18;
                case 6 -> ratings = MovieType.Class.R21;
            }
        } while (ratings == null);

        return new Movie(movieTitle, moviestatus, director, synopsis, cast, genre, blockbuster, ratings);
    }

    /**
     * This function is used to get the admin user to modify any movie details in the Movies.txt database.
     *
     * @param movie = movie whose details need to be changed
     * @return the updated movie object
     */
    public static Movie modifyMovie(Movie movie) {
        Scanner sc = new Scanner(System.in);
        int option;
        do {
            System.out.println("*************************************************");
            System.out.println("*         Which part of Movie to edit:          *");
            System.out.println("*************************************************");
            System.out.println("\t1) Change Title");
            System.out.println("\t2) Showing Status");
            System.out.println("\t3) Change Director");
            System.out.println("\t4) Change Synopsis");
            System.out.println("\t5) Change Cast");
            System.out.println("\t6) Change Movie Genre");
            System.out.println("\t7) Change Blockbuster status");
            System.out.println("\t8) Change Movie Ratings");
            System.out.println("\t9) Add / Update Movie ShowTime");
            option = GetNumberInput.getInt(1, 9, -1);
            switch (option) {
                case 1 -> {
                    System.out.println("\nEnter new Movie Title:");
                    movie.setMovieTitle(sc.nextLine());
                }
                case 2 -> {
                    System.out.println("\nSelect new option for Movie Status: ");
                    System.out.println("\t1) Coming Soon!");
                    System.out.println("\t2) Preview");
                    System.out.println("\t3) Now Showing");
                    System.out.println("\t4) End Of Showing");
                    switch (GetNumberInput.getInt(1, 4, -1)) {
                        case 1 -> movie.updateShowingStatus(Movie.MovieStatus.ComingSoon);
                        case 2 -> movie.updateShowingStatus(Movie.MovieStatus.Preview);
                        case 3 -> movie.updateShowingStatus(Movie.MovieStatus.NowShowing);
                        case 4 -> movie.updateShowingStatus(Movie.MovieStatus.EndOfShowing);
                    }
                }
                case 3 -> {
                    System.out.println("\nEnter new Director Name:");
                    movie.setDirector(sc.nextLine());
                }
                case 4 -> {
                    System.out.println("\nEnter new Synopsis:");
                    movie.setSynopsis(sc.nextLine());
                }
                case 5 -> {
                    System.out.println("\nEnter new Casts (Separated by ',':");
                    String[] tempcasts = sc.nextLine().split(",");
                    ArrayList<String> casts = new ArrayList<String>();
                    Collections.addAll(casts, tempcasts);
                    movie.setCast(casts);
                }
                case 6 -> {
                    System.out.println("\nSelect new Movie Genre:");
                    System.out.println("\t1) Action");
                    System.out.println("\t2) Comedy");
                    System.out.println("\t3) Drama");
                    System.out.println("\t4) Fantasy");
                    System.out.println("\t5) Horror");
                    System.out.println("\t6) Mystery");
                    System.out.println("\t7) Romance");
                    System.out.println("\t8) Thriller");
                    System.out.println("\t9) Western");
                    switch (GetNumberInput.getInt(1, 9, -1)) {
                        case 1 -> movie.setMovieGenre(MovieType.Genre.Action);
                        case 2 -> movie.setMovieGenre(MovieType.Genre.Comedy);
                        case 3 -> movie.setMovieGenre(MovieType.Genre.Drama);
                        case 4 -> movie.setMovieGenre(MovieType.Genre.Fantasy);
                        case 5 -> movie.setMovieGenre(MovieType.Genre.Horror);
                        case 6 -> movie.setMovieGenre(MovieType.Genre.Mystery);
                        case 7 -> movie.setMovieGenre(MovieType.Genre.Romance);
                        case 8 -> movie.setMovieGenre(MovieType.Genre.Thriller);
                        case 9 -> movie.setMovieGenre(MovieType.Genre.Western);
                    }
                }
                case 7 -> {
                    System.out.println("\nIs the movie a BLOCKBUSTER?:");
                    System.out.println("\t1) Yes");
                    System.out.println("\t2) No");
                    System.out.println("Enter your option:");
                    switch (GetNumberInput.getInt(1, 2, -1)) {
                        case 1 -> movie.setBlockBuster(MovieType.Blockbuster.BLOCKBUSTER);
                        case 2 -> movie.setBlockBuster(MovieType.Blockbuster.NOTBLOCKBUSTER);
                    }
                }
                case 8 -> {
                    System.out.println("\nSelect new Movie Age Rating ");
                    System.out.println("\t1) G");
                    System.out.println("\t2) PG");
                    System.out.println("\t3) PG13");
                    System.out.println("\t4) NC16");
                    System.out.println("\t5) M18");
                    System.out.println("\t6) R21");
                    switch (GetNumberInput.getInt(1, 6, -1)) {
                        case 1 -> movie.setMovieClass(MovieType.Class.G);
                        case 2 -> movie.setMovieClass(MovieType.Class.PG);
                        case 3 -> movie.setMovieClass(MovieType.Class.PG13);
                        case 4 -> movie.setMovieClass(MovieType.Class.NC16);
                        case 5 -> movie.setMovieClass(MovieType.Class.M18);
                        case 6 -> movie.setMovieClass(MovieType.Class.R21);
                    }
                }
                case 9 -> {
                    return movie;
                }
                case -1 -> System.out.println("Exiting.");
            }
        } while (option != -1);
        return movie;
    }

    /**
     * This function is used to get the admin user to input the movie details that they want to
     * delete from the movies.txt database by changing the status to End Of Showing.
     *
     * @param movie = the movie they want to delete
     * @return the movie object
     */
    public static Movie deleteMovie(Movie movie) {
        System.out.printf("\nChanging Movie Status of %s to EndOfShowing...\n", movie.getMovieTitle());
        Movie.MovieStatus moviestatus = Movie.MovieStatus.EndOfShowing;
        movie.updateShowingStatus(moviestatus);
        System.out.println("New Movie Details:");
        movie.printMovieDetails();
        return movie;
    }
}
