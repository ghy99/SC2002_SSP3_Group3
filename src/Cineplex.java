import java.util.Scanner;
public class Cineplex {
    // This will be for Cineplexes
    private int noOfCinema;
    private Cinema[] listOfCinema;

    public Cineplex(int noOfCinema) {
        this.noOfCinema = noOfCinema;
        listOfCinema = new Cinema[noOfCinema];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < noOfCinema; i++) {
            System.out.println("Enter Cinema names:");
            listOfCinema[i] = new Cinema(sc.nextLine());
        }
    }

    public void printCinemas() {
//        System.out.println("Cinemas available are:");
        for (int i = 0; i < noOfCinema; i++) {
            System.out.printf("\t%s\n", listOfCinema[i].getCinemaName());
        }
    }

    public void InitializeMovies() {
        System.out.println("Initializing list of movies in this cinema\n...\n...");
        listOfCinema[0].AddNewMovies();
    }
}
