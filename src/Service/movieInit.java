package Service;
import java.util.Scanner;

public class movieInit {


    public movieInit(){
    }

// Method to create matrix if it doesnt exist.
    public int[][] ifEmpty(int[][] cineplexMatrix, int noOfMovie){

            if (cineplexMatrix == null){
                int[][] newMatrix = new int[6][noOfMovie];
                for (int x = 0; x < 6; x ++){ // initialising array
                    for (int y = 0; y < noOfMovie; y++){
                        newMatrix[x][y] = 0; // 0 = no Cinema
                    }
                }

                return newMatrix;
                }

            else {
                return cineplexMatrix;
            }

            
                }


    public void getShowtime(int[][] cineplexMatrix, int whichMovie){ // trying to go in to the matrix and check if available
    
        int col = cineplexMatrix.length;

        for (int x = 0; x < col; x++){
                if (cineplexMatrix[x][whichMovie] > 0 && cineplexMatrix[x][whichMovie] < 3 ){ // regular movie timing
                    System.out.println("Regular moving timing"); 
                    displayTiming(x);
            } 
                else if (cineplexMatrix[x][whichMovie] > 2){
                    System.out.println("Premium moving timing"); 
                    displayTiming(x);
                } else {
                    System.out.println("There is no movie timing"); 
                }


        }
        
    }


    public void displayTiming(int x){
        switch(x){ 
            case 0:
                System.out.println(" 10 AM");
                break;
            case 1:
                System.out.println(" 12 PM");
                break;
            case 2:
                System.out.println(" 2 PM");
                break;
            case 3:
                System.out.println(" 4 PM");
                break;
            case 4:
                System.out.println(" 6 PM");
                break;
            case 5:
                System.out.println(" 8 PM");
                break;
            case 6: 
                System.out.println(" 10 PM");
                break;
        }

    }


    public int[][] adminCRUD(int [][] cineplexMatrix){
        Scanner sc = new Scanner(System.in);
        //System.out.println("Which cineplex?");
        // How do we pass which cineplex???
        System.out.println("Which movie?");
        int movNumber = sc.nextInt();
        getShowtime(cineplexMatrix,movNumber);
        System.out.println("which one do u want to choose?");



        return cineplexMatrix;
    }
    // admin Function, add timing. 
    // 

    public void addTiming(){
        
    }

    public void deleteTiming(){

    }
    



    public int checkDuplicate(int[][] cineplexMatrix, int whichMovie, int cinemaNo){
        // to check for duplicate. 

        for (int x  = 0; x < cineplexMatrix.length; x++){ // check row. 
            if (cineplexMatrix[x][whichMovie] == cinemaNo){ // if duplicate
                return 0;
            } 
            else { 
                for (int y = 0; y < cineplexMatrix[0].length; y++){
                    if (cineplexMatrix[x][y] == cinemaNo){
                        return 0;
                    } 
                }
            }
        }

        return 1; 
        





    }




    //delete showTime. 

            }
        




/*
x Axis. 
 * Timing
 * 0: 1000-1200
 * 1: 1200-1400
 * 2: 1400-1600
 * 3: 1600-1800
 * 4: 1800-2000
 * 5: 2000-2200
 * 6: 2200-0000
 * 
 * 
 * 
 */