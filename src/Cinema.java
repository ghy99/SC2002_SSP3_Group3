import Movie.Movie;

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

    public String AddNewMovies() {
        listOfMovies = new ArrayList<Movie>();
        File filename = new File("C:\\Users\\ganha\\IdeaProjects\\SC2002_SSP3_Group3\\src\\DataStorage\\Movies.txt");
        listOfMovies = parseMoviesTxtFile(filename);
        for (int i = 0; i < listOfMovies.size(); i++) {
            listOfMovies.get(i).printMovieDetails();
        }
        return "Movies are added!";
    }

    public static ArrayList<Movie> parseMoviesTxtFile(File filename) {
        ArrayList<Movie> listofMovies = new ArrayList<Movie>();
        try {
            Scanner sc = new Scanner(filename);
            while(sc.hasNextLine()) {
                Movie addmovie = new Movie();
                String[] line = sc.nextLine().split(" [|] ");
//                System.out.println(Arrays.toString(line));

                String[] temp = line[3].split(",");
                ArrayList<String> casts = new ArrayList<String>();
                Collections.addAll(casts, temp);
//                System.out.printf("status: %s", Movie.MovieStatus.valueOf(line[1]));

                addmovie.setMovieTitle(line[0]);
                addmovie.setShowingStatus(Movie.MovieStatus.valueOf(line[1]));
                addmovie.setSynopsis(line[2]);
                addmovie.setCast(casts);
                addmovie.setMovieType(Movie.MovieType.valueOf(line[4]));
                addmovie.setMovieCat(Movie.MovieCategory.valueOf(line[5]));
                addmovie.setMovie3D(Movie.MovieDimension.valueOf(line[6]));

                listofMovies.add(addmovie);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listofMovies;
    }

    public String getCinemaName() {
        return this.cinemaName;
    }
}
