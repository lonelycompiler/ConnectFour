//seven column six row

import java.util.Scanner;
import java.util.InputMismatchException;

public class ConnectFour {

    private final char[] colors = {'R', 'Y'};
    int currentColor;

    private Scanner sc = new Scanner(System.in);

    private class Board {
        
        private char[][] gameBoard;
        private int numRows, numColumns;
        
        private Board(int numRows, int numColumns) {
            this.numRows = numRows;
            this.numColumns = numColumns;
            gameBoard = new char[numRows][numColumns];
        }

        private String getDividingLine() {
            return "-----------------------------";
        }
        
        private void displayBoard() {
            
            for(int i = 0; i < numRows; i++) {

                System.out.println(getDividingLine());
                System.out.print("|");

                for(int j = 0; j < numColumns; j++) {
                    System.out.print(" " + (gameBoard[i][j]=='\0' ? ' ' : gameBoard[i][j]) + " |");
                }
                System.out.println();
            }

            System.out.println(getDividingLine()); 
        	
        	System.out.println("  0   1   2   3   4   5   6  \n");

        }

        /* Returns true if diskColor won, false if no result */ 
        private boolean dropDisk(char diskColor, int col) {
            //get the next free spot on y-axis
            int i;

            for(i = 0; i < 6; i++) {
                if(this.inBounds(i+1,col) && !this.isFree(i+1,col)) break;

                if(!this.inBounds(i+1, col)) break;
            }

            gameBoard[i][col] = diskColor;

            displayBoard();

            //call checkBoard at end to see if anyone won
            return checkBoard(diskColor,i,col);
        }

        /* Returns true if diskColor won, false if no result */ 
        private boolean checkBoard(char diskColor, int row, int col) {
        
			int posx = row;
			int posy = col;
			
			int streakx = 0;
			int streaky = 0;
			
			int streakPOSxy = 0;
			int streakNEGxy = 0;
			
			for(int i = -3; i <= 3; i++) {
				if( inBounds(posx+i, posy) && gameBoard[posx+i][posy] == gameBoard[posx][posy] ) { streakx++;}
				else { streakx = 0;}
				
				if( inBounds(posx, posy+i) && gameBoard[posx][posy+i] == gameBoard[posx][posy]) { streaky++;}
				else { streaky = 0;}
				
				if( inBounds(posx+i, posy+i) && gameBoard[posx+i][posy+i] == gameBoard[posx][posy] ) { streakPOSxy++; }
				else { streakPOSxy = 0; }
				
				if( inBounds(posx+i, posy-i) && gameBoard[posx+i][posy-i] == gameBoard[posx][posy] ) {streakNEGxy++;}
				else { streakNEGxy = 0; }
				
				if( streakx == 4 || streaky == 4 || streakPOSxy == 4 || streakNEGxy == 4) { return true; }
				
			}
			
			return false;
        }
        
        //is it in bounds? There are 6 rows and 7 columns total!
        private boolean inBounds(int row, int col) {
            if(row > 5 || row < 0) return false;
            
            else if(col > 6 || col < 0) return false;

            else return true;
        }

        //is there a disk on that spot? R for Red Y for Yellow
        private boolean isFree(int row, int col) {
            if(gameBoard[row][col] == 'R') return false;

            else if(gameBoard[row][col] == 'Y') return false;

            else return true;
        }
    }

    private void start() {

        Board board = new Board(6,7);

        currentColor = 0;
        int colToDropIn = 0;
        boolean result;
        board.displayBoard();

        
        while(true) {

            while(true){
                System.out.print("Drop a " + colors[currentColor] + " disk at column (0-6): ");

                try {
                    colToDropIn = sc.nextInt();
                } catch(InputMismatchException ex) {
                    System.out.println("That's not a valid integer input!");
                    System.exit(0);
                }

                if(!board.inBounds(0,colToDropIn)) System.out.println("That's not a valid column! Try again!");

                else if(!board.isFree(0,colToDropIn)) System.out.println("That column is already full of disks! Try again!");
               
                else break;
            }

            result = board.dropDisk(colors[currentColor], colToDropIn);
            

            if(result) {
                System.out.println( (getColor(colors[currentColor]) ? "Red" : "Yellow") + " wins. Game End!" );
                break;
            }

            //change current color to next color
            currentColor=(currentColor+1)%2;

        }
    }
    
    //if char color given, boolean char returned
    private boolean getColor(char currentColor) {
        if(currentColor == 'R') return true;
        else return false;
    }

    public static void main(String[] args) {        
        ConnectFour game = new ConnectFour();
        game.start();
    }
}
