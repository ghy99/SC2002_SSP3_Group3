package UserInterface;

import java.io.IOException;
import java.util.*;

import Cineplex.Cinema;
import Cineplex.*;
import Movie.Movie;
import Movie.MovieType;
import Service.GetNumberInput;
import Service.TextDB;

import static Service.TextDB.readFromFile;

/**
 * This is the Movie Listing Interface.
 * This is imported to call the interface for the admin to be able to add/edit/delete the movie listing
 * @author GAN HAO YI, SANSKKRITI JAIN
 */
public class MovieListingUI {
    /**
     * This is the Movie Listing Interface for the admin to access the Movie database and make any changes
     * @param cineplexes = includes the movies listed at the various cineplexes
     * @throws Exception is thrown if there is an error in the implementation of any method.
     */
    public static void MovieListingInterface(AllCineplex cineplexes) throws Exception {
        int num = -1;
        ArrayList<Movie> listOfMovies = cineplexes.getListOfMoviesforAdmin();

//		System.out.println("Current list of movies:");
//		printMovieList(listOfMovies);

        do {
            System.out.println("-----------------------------------");
            System.out.println("          Movie Functions          ");
            System.out.println("-----------------------------------");
            System.out.println("1: Create a Movie Listing");
            System.out.println("2: Update a Movie Listing");
            System.out.println("3: Delete a Movie Listing");
            System.out.println("4: Main Menu (Select this to exit)");
            System.out.print("Please enter a choice: ");
            num = GetNumberInput.getInt(1, 4, -1);

            switch(num) {
                case 1 -> {
                    Movie newMovie = createMovie();
                    cineplexes.addMovies(newMovie);
                    CinemaUI.UserInterface(cineplexes, 1, newMovie);
                }
                case 2 -> {
                    System.out.println("-----------------------------------");
                    System.out.println("        Modify Movie Details       ");
                    System.out.println("-----------------------------------");
                    int choice;
                    System.out.println("Which movie would you like to edit?");
                    printMovieList(listOfMovies);
                    do {
                        choice = GetNumberInput.getInt(1, listOfMovies.size(), -1) - 1;
                        if (choice < 0 || choice >= listOfMovies.size()) {
                            System.out.println("Invalid choice. Try again!");
                        }
                    } while (choice < 0 || choice >= listOfMovies.size());

                    Movie movie = cineplexes.getListOfMovies().get(choice);
                    Movie newmovie = modifyMovie(listOfMovies, movie);
                    newmovie.printMovieDetails();

                    cineplexes.updateListOfMovies(choice, newmovie);
                    System.out.printf("Would you like to update the ShowTime of this movie %s as well?\n", newmovie.getMovieTitle());
                    System.out.println("1) Yes\n2) No");
                    if (GetNumberInput.getInt(1, 2, -1) == 1) {
                        CinemaUI.UserInterface(cineplexes, 2, newmovie);
                    }
                }
                case 3 -> {
                    System.out.println("-----------------------------------");
                    System.out.println("           Removing Movie          ");
                    System.out.println("-----------------------------------");
                    int choice;
                    System.out.println("Which movie would you like to edit?");
                    printMovieList(listOfMovies);
                    do {
                        choice = GetNumberInput.getInt(1, listOfMovies.size(), -1) - 1;
                        if (choice < 0 || choice >= listOfMovies.size()) {
                            System.out.println("Invalid choice. Try again!");
                        }
                    } while (choice < 0 || choice >= listOfMovies.size());
                    Movie deletingMovie = deleteMovie(listOfMovies.get(choice));
                    cineplexes.removeMovie(choice, deletingMovie);
                }
            }
        } while (num != 4);
        System.out.println();
    }

    /**
     * This function is used to read the data from the movies.txt database
     * @param filename = the txt file that the database is retrieved from
     * @return the movies list from the movies.txt database
     * @throws IOException is thrown if reading the data from the file causes an error
     */
    public static ArrayList<Movie> getMovieList(String filename) throws IOException {
        Movie temp = null;
        ArrayList<Movie> listOfMovies = new ArrayList<Movie>();
        listOfMovies = readFromFile(filename, listOfMovies);
        return listOfMovies;
    }

    /**
     * This function is implemented to print the entire movies database
     * @param listOfMovies = contains the movies list and all details
     * @throws IOException is thrown if there is an error in reading the file contents
     */
    public static void printMovieList(ArrayList<Movie> listOfMovies) throws IOException {
        for (int i = 0; i < listOfMovies.size(); i++) {
            System.out.printf("%d)\n", i + 1);
            listOfMovies.get(i).printMovieDetails();
            System.out.println();
        }
    }

    /**
     * This method creates a new movie and returns an ArrayList of 2 movies
     * @return ArrayList of 2 movies, the first a 3D Movie, the second a 2D Movie.
     */
    public static Movie createMovie() {
        Scanner sc = new Scanner(System.in);
        System.out.println("------------------------");
        System.out.println("    Create New Movie    ");
        System.out.println("------------------------");


        String movieTitle, director, synopsis;
        System.out.print("Enter movie title: "); //movie title
        movieTitle = sc.nextLine();


        System.out.println();
        System.out.println("Select option for Movie Status (Enter -1 to exit): ");
        System.out.println("1: Coming soon!");
        System.out.println("2: Preview");
        System.out.println("3: Now showing");
        System.out.println("4: End of showing");
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

        System.out.println();
        System.out.print("Enter the name of the Movie Director: ");
        director = sc.nextLine();


        System.out.println();
        System.out.print("Enter Movie Synopsis: ");
        synopsis = sc.nextLine();


        // set cast
        ArrayList<String> cast = null;
        System.out.println();
        System.out.print("Enter casts, separate with semicolon(,)");
        String[] castArray = sc.nextLine().split(",");
        cast = new ArrayList<>();
        Collections.addAll(cast, castArray);


        System.out.println();
        System.out.println("Select Cinema Type:");
        System.out.println("1: Regular");
        System.out.println("2: Premium");
        System.out.println("Enter your option:");
        Cinema.CinemaType cinemaType = null;

        int cinChoice;
        do {
            cinChoice = GetNumberInput.getInt(1, 2, -1);
            if (cinChoice == -1) {
                System.out.println("Try again.");
            }
            else if (cinChoice == 1) {
                cinemaType = Cinema.CinemaType.Regular;
            } else {
                cinemaType = Cinema.CinemaType.Premium;
            }
        } while (cinemaType == null);

        System.out.println();
        System.out.println("Select Movie Genre:");
        System.out.println("1: Action");
        System.out.println("2: Comedy");
        System.out.println("3: Drama");
        System.out.println("4: Fantasy");
        System.out.println("5: Horror");
        System.out.println("6: Mystery");
        System.out.println("7: Romance");
        System.out.println("8: Thriller");
        System.out.println("9: Western");
        MovieType.Genre genre = null;
        int genrechoice;
        do {
            genrechoice = GetNumberInput.getInt(1, 9, -1);
//			sc.nextLine();
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
            if (genre == null) continue;
        } while (genrechoice > 8 || genrechoice < 1);


        System.out.println();
        System.out.println("Is the movie a BLOCKBUSTER?");
        System.out.println("1: Yes");
        System.out.println("2: No");
        System.out.println("Enter your option:");
        MovieType.Blockbuster blockbuster = null;
        int dimChoice;
        do {
            dimChoice = GetNumberInput.getInt(1, 2, -1);
            if (dimChoice == 1) {
                blockbuster = MovieType.Blockbuster.BLOCKBUSTER;
            } else if (dimChoice == 2) {
                blockbuster = MovieType.Blockbuster.NOTBLOCKBUSTER;
            }
            else {
                System.out.println("Try again.");
            }
        } while (blockbuster == null);


        System.out.println();
        System.out.println(" Movie Age Rating ");
        System.out.println("1: G ");
        System.out.println("2: PG");
        System.out.println("3: PG13 ");
        System.out.println("4: NC16");
        System.out.println("5: M18");
        System.out.println("6: R21");
        int ratingchoice;
        MovieType.Class ratings = null;

        do {
            ratingchoice = GetNumberInput.getInt(1, 6, -1);
//			sc.nextLine();
            switch (ratingchoice) {
                case 1 -> ratings = MovieType.Class.G;
                case 2 -> ratings = MovieType.Class.PG;
                case 3 -> ratings = MovieType.Class.PG13;
                case 4 -> ratings = MovieType.Class.NC16;
                case 5 -> ratings = MovieType.Class.M18;
                case 6 -> ratings = MovieType.Class.R21;
            }
        } while (ratings == null);

        return new Movie(movieTitle, moviestatus, director,
                synopsis, cast, genre, blockbuster, ratings);
    }

    /**
     * This function is used to get the admin user to modify any movie details in the Movies.txt database.
     * @param listOfMovies = the movies list with all the details
     * @param movie = movie whose details need to be changed
     * @return the updated movie object
     */
    public static Movie modifyMovie(ArrayList<Movie> listOfMovies, Movie movie) {
        Scanner sc = new Scanner(System.in);

        int option;
        do {
            System.out.println("-----------------------------------");
            System.out.println("           What to Change          ");
            System.out.println("-----------------------------------");
            System.out.println("1: Change Title");
            System.out.println("2: Showing Status");
            System.out.println("3: Change Director");
            System.out.println("4: Change Synopsis");
            System.out.println("5: Change Cast");
            System.out.println("6: Change Movie Genre");
            System.out.println("7: Change Blockbuster status");
            System.out.println("8: Change Movie Ratings");
            System.out.println("-1: Done Editing");
            System.out.println();

            option = GetNumberInput.getInt(1, 8, -1);
            switch (option) {
                case 1 -> {
                    System.out.println("Enter new Movie Title:");
                    movie.setMovieTitle(sc.nextLine());
                }
                case 2 -> {
                    System.out.println("Select new option for Movie Status (Enter -1 to exit): ");
                    System.out.println("1: Coming soon!");
                    System.out.println("2: Preview");
                    System.out.println("3: Now showing");
                    System.out.println("4: End of showing");
                    switch (GetNumberInput.getInt(1, 4, -1)) {
                        case 1 -> movie.updateShowingStatus(Movie.MovieStatus.ComingSoon);
                        case 2 -> movie.updateShowingStatus(Movie.MovieStatus.Preview);
                        case 3 -> movie.updateShowingStatus(Movie.MovieStatus.NowShowing);
                        case 4 -> movie.updateShowingStatus(Movie.MovieStatus.EndOfShowing);
                    }
                }
                case 3 -> {
                    System.out.println("Enter new Director Name:");
                    movie.setDirector(sc.nextLine());
                }
                case 4 -> {
                    System.out.println("Enter new Synopsis:");
                    movie.setSynopsis(sc.nextLine());
                }
                case 5 -> {
                    System.out.println("Enter new Casts (Separated by ',':");
                    String[] tempcasts = sc.nextLine().split(",");
                    ArrayList<String> casts = new ArrayList<String>();
                    Collections.addAll(casts, tempcasts);
                    movie.setCast(casts);
                }
                case 6 -> {
                    System.out.println();
                    System.out.println("Select new Movie Genre (Enter -1 if no change):");
                    System.out.println("1: Action");
                    System.out.println("2: Comedy");
                    System.out.println("3: Drama");
                    System.out.println("4: Fantasy");
                    System.out.println("5: Horror");
                    System.out.println("6: Mystery");
                    System.out.println("7: Romance");
                    System.out.println("8: Thriller");
                    System.out.println("9: Western");
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
                    System.out.println();
                    System.out.println("Is the movie a BLOCKBUSTER? (Enter -1 if no change)");
                    System.out.println("1: Yes");
                    System.out.println("2: No");
                    System.out.println("Enter your option:");
                    switch (GetNumberInput.getInt(1, 2, -1)) {
                        case 1 -> movie.setBlockBuster(MovieType.Blockbuster.BLOCKBUSTER);
                        case 2 -> movie.setBlockBuster(MovieType.Blockbuster.NOTBLOCKBUSTER);
                    }
                }
                case 8 -> {
                    System.out.println(" Select new Movie Age Rating ");
                    System.out.println("1: G ");
                    System.out.println("2: PG");
                    System.out.println("3: PG13 ");
                    System.out.println("4: NC16");
                    System.out.println("5: M18");
                    System.out.println("6: R21");
                    switch (GetNumberInput.getInt(1, 6, -1)) {
                        case 1 -> movie.setMovieClass(MovieType.Class.G);
                        case 2 -> movie.setMovieClass(MovieType.Class.PG);
                        case 3 -> movie.setMovieClass(MovieType.Class.PG13);
                        case 4 -> movie.setMovieClass(MovieType.Class.NC16);
                        case 5 -> movie.setMovieClass(MovieType.Class.M18);
                        case 6 -> movie.setMovieClass(MovieType.Class.R21);
                    }
                }
                case -1 -> {
                    System.out.println("Exiting.");
                }
            }
        } while (option != -1);
        return movie;
    }

    /**
     * This function is used to get the admin user to input the movie details that they want to
       delete from the movies.txt database by changing the status to end of showing
     * @param movie = the movie they want to delete
     * @return the movie object
     */
    public static Movie deleteMovie(Movie movie) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.printf("Changing Movie Status of %s to EndOfShowing...", movie.getMovieTitle());
        Movie.MovieStatus moviestatus = Movie.MovieStatus.EndOfShowing;
        movie.updateShowingStatus(moviestatus);
        System.out.println("New Movie Details:");
        movie.printMovieDetails();
        return movie;
    }
}
