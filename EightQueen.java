//Marcus Katalenas
// Date: 5/25
// Eight Queens Project
/* This programm will be able to solve the eight queen challenge by generating a heuristic based on queen collisions on the chess board
     The program will move one queen at a time per move moving them along their respective columns and checking the herustic of that move to the current herustic
     The program will choose the move with the lowest heuristic and then will update the board and repeat.
     If no moves in the current state of the board will result in a lower heuristic score, then the program will begin again with a fresh new board position of queens
     This process continues until the program solves the puzzle.

*/
import java.util.*;
public class EightQueen {
    //--------------------------------------------------------------------------------------------------
    static void printBoard(int playBoard[][], int hScore){
        System.out.println("Current H = " + hScore);
        System.out.println("Current State");
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                 System.out.print(playBoard[row][col]);
                 System.out.print(" ");
             }
             System.out.println();
        }
    }
    static void printReset(int playBoard[][], int hScore){
        System.out.println("Current H = " + hScore);
        System.out.println("Current State");
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                 System.out.print(playBoard[row][col]);
                 System.out.print(" ");
             }
             System.out.println();
        }
    }
    static void printFinalBoard(int playBoard[][], int stateChanges, int restarts){
        System.out.println("Current State");
        for(int row = 0; row < 8; row++){
            for(int col = 0; col < 8; col++){
                 System.out.print(playBoard[row][col]);
                 System.out.print(" ");
             }
             System.out.println();
        }
        System.out.println("Solution Found");
        System.out.println("State Changes: " + stateChanges);
        System.out.println("Restarts: " + restarts);
    }
    //--------------------------------------------------------------------------------------------------
    static Boolean checkGoal(int hScore){
        if(hScore == 0){
            return true;
        }
        else{
            return false;
        }
        
    }
    static Boolean restartFlag(){
        return true;
    }
    //--------------------------------------------------------------------------------------------------
    static int checkAllHorizontal(int[][] Board, int[] qPos) {
        int collisions = 0;
        int row;
        int currentCol;
        int startCol;

        for(int col = 0; col < 8; col++){
            
          
            //WHAT ROW IS THE CURRENT COL IN
            row = qPos[col];

            //JUST NEED TO CHECK RIGHT OF ROW
             if(col == 0){
                currentCol = 1;
                while(currentCol < 8){
                    if(Board[row][currentCol] == 1){
                       
                        collisions++;
                    }
                    currentCol++;
                }
             }
             else if(col == 7){
                currentCol = 6;
                while(currentCol > -1){
                    if(Board[row][currentCol] == 1){
                      
                        collisions++;
                    }
                    currentCol--;
                }
             }
             else{
                startCol = col;
                currentCol = startCol + 1;
                while(currentCol < 8){
                    if(Board[row][currentCol] == 1){   
                       collisions++;
                    }
                    currentCol++;
                }

                currentCol = startCol - 1;
                while(currentCol > -1){
                    if(Board[row][currentCol] == 1){             
                        collisions++;
                    }
                    currentCol--;
                }
             }
        }
       
        return collisions;
    }
    //--------------------------------------------------------------------------------------------------
    static int checkDiag(int[][] Board, int[]qPos){
        

        int startrow;
        int startCol;
        int currentCol, currentRow;
        int collisions = 0;
        
            for(int col = 0; col < 8; col++){
                startCol = col;
                currentCol = startCol;
                startrow = qPos[startCol];
                currentRow = startrow;
              
                if(col == 0 || col == 7){
                    //LEFT-EDGE UP-RIGHT DOWN-RIGHT
                    if(col == 0){
                        //Up-Right Row-- col++
                        currentRow--;
                        currentCol++;
                        while(currentRow > -1 && currentCol < 8){
                            if(Board[currentRow][currentCol] == 1){
                                collisions++;
        
                            }
                            currentRow--;
                            currentCol++;
                        }

                        //RESET
                        currentCol = startCol;
                        currentRow = startrow;

                        //DOWN RIGHT ROW++ col++
                        currentRow++;
                        currentCol++;

                        while(currentRow > 8 && currentCol > 8){
                            if(Board[currentRow][currentCol] == 1){
                                collisions++;
                               
                            }
                            currentRow++;
                            currentCol++;
                        }
                    }

                    //RIGHT EDGE UP-LEFT DOWN-LEFT   
                    else{
                        // UP-LEFT ROW-- COL--
                        currentRow--;
                        currentCol--;
                        while(currentRow > -1 && currentCol > -1){
                            if(Board[currentRow][currentCol] == 1){
                                collisions++;
                            
                            }
                            currentRow--;
                            currentCol--;
                        }
                        //RESET
                        currentRow = startrow;
                        currentCol = startCol;
                        currentRow++;
                        currentCol--;
                        //DOWN-LEFT ROW++ COL--
                        while(currentRow < 8 && currentCol > -1){
                            if(Board[currentRow][currentCol] == 1){
                                collisions++;
                               
                            }
                            currentRow++;
                            currentCol--;
                        }
                    }
                }
                
                else{
                    if(startrow == 0 || startrow == 7){
                        //UPPER EDGE PIECE DOWN-RIGHT, DOWN LEFT
                        if(startrow == 0){
                            currentRow++;
                            currentCol++;

                            while(currentRow > 8 && currentCol > 8){
                                if(Board[currentRow][currentCol] == 1){
                                    collisions++;
                                  
                                }
                                currentRow++;
                                currentCol++;
                            }
                            currentRow = startrow;
                            currentCol = startCol;
                            currentRow++;
                            currentCol--;

                            //DOWN-LEFT ROW++ COL--
                            while(currentRow < 8 && currentCol > -1){
                                if(Board[currentRow][currentCol] == 1){
                                    collisions++;
                                }
                                currentRow++;
                                currentCol--;
                            }
                        }

                        //LOWER EDGE PIECE UP-LEFT, UP-RIGHT
                        else{
                            currentRow--;
                            currentCol--;
                            while(currentRow > -1 && currentCol > -1){
                                if(Board[currentRow][currentCol] == 1){
                                    collisions++;
                                   
                                }
                                currentRow--;
                                currentCol--;
                            }
                            //RESET
                            currentCol = startCol;
                            currentRow = startrow;

                            //UP-RIGHT
                            currentRow--;
                            currentCol++;
                            while(currentRow > -1 && currentCol < 8){
                                if(Board[currentRow][currentCol] == 1){
                                    collisions++;
                                   
                                }
                                currentRow--;
                                currentCol++;
                            }
                        }
                    }
                    
                    else{
                        //UP-LEFT
                        currentRow--;
                        currentCol--;
                        while(currentRow > -1 && currentCol > -1){
                            
                            if(Board[currentRow][currentCol] == 1){
                                collisions++;
                              
                            }
                            currentRow--;
                            currentCol--;
                            }
                        //reset
                        currentRow = startrow;
                        currentCol = startCol;

                        //DOWN-LEFT
                        currentRow++;
                        currentCol--;
                            while(currentRow < 8 && currentCol > -1){
                                
                                if(Board[currentRow][currentCol] == 1){
                                        collisions++;
                                       
                                    }
                                    currentRow++;
                                    currentCol--;
                                }
                            //reset
                            currentRow = startrow;
                            currentCol = startCol;
                            //UP-RIGHT
                            currentRow--;
                            currentCol++;
                            while(currentRow > -1 && currentCol < 8){
                               
                              
                                if(Board[currentRow][currentCol] == 1){
                                    collisions++;
                                  
                                }
                                currentRow--;
                                currentCol++;
                            }
                            //reset
                            currentRow = startrow;
                            currentCol = startCol;
                            
                            //DOWN-right
                            currentRow++;
                            currentCol++;

                            while(currentRow > 8 && currentCol > 8){
                               
                                if(Board[currentRow][currentCol] == 1){
                                    collisions++;
                                   
                                }
                                currentRow++;
                                currentCol++;
                            }
                        }
                   } 
               }
         
               return collisions;
    }   
    
    
    //--------------------------------------------------------------------------------------------------

     public static void main(String[] args) {

        System.currentTimeMillis();
        Random rand = new Random();

        int[] queenRowPostions = new int[8];
        int[] bestQueensRowPostions = new int[8];
        int[][] playBoard = new int[8][8];
        
        int bestHscore;
        

        int movesMade = 0;
        int restarts = 0;

        int currentHScore = 0;
        int randomspot;
        int problems = 0;

        ArrayList<Integer> possibleHScores = new ArrayList<Integer>();

        

        for (int startCol = 0; startCol < 8; startCol++) {
            for (int startRow = 0; startRow < 8; startRow++) {
                playBoard[startCol][startRow] = 0;
            }
        }

        for (int colNum = 0; colNum < 8; colNum++) {
            randomspot = rand.nextInt(8);
            playBoard[randomspot][colNum] = 1;
            queenRowPostions[colNum] = randomspot;
        }
  
        problems += checkAllHorizontal(playBoard, queenRowPostions);
       
        problems += checkDiag(playBoard, queenRowPostions);
        
        currentHScore = problems;
        bestHscore = currentHScore;
        
        Boolean resetflag = false;
        //While the eight queens puzzle isnt solved
        while(checkGoal(currentHScore) == false){
            if(resetflag == true){
                //empty the board
                for(int i = 0; i < 8; i++){
                    playBoard[queenRowPostions[i]][i] = 0;
                    queenRowPostions[i] = 0;
                    bestQueensRowPostions[i]= 0;
                }
                //refill the board
                for (int colNum = 0; colNum < 8; colNum++) {
                    randomspot = rand.nextInt(8);
                    playBoard[randomspot][colNum] = 1;
                    queenRowPostions[colNum] = randomspot;
                }

                currentHScore = 0;
                
                currentHScore += checkAllHorizontal(playBoard, queenRowPostions);
                currentHScore += checkDiag(playBoard, queenRowPostions);
                
                bestHscore = currentHScore;
                if(checkGoal(currentHScore)){
                    printBoard(playBoard, currentHScore);
                    System.exit(0);
                }
                resetflag = false;
            }//------------------------END RESET-----------------------------------

            //check moves
            for(int i = 0; i < 8; i++){
                int startRow = queenRowPostions[i];
                int startCol = i;
                int newHscore = 0;
    
                int currentRow;
                currentRow = startRow;
    
                int previousRow = currentRow;
               
    
          
                    currentRow--;
                    while(currentRow != -1){
                        
                        playBoard[currentRow][startCol] = 1;
                        playBoard[previousRow][startCol] = 0;
                        queenRowPostions[startCol] = currentRow;
                        newHscore += checkAllHorizontal(playBoard, queenRowPostions);
                        newHscore += checkDiag(playBoard, queenRowPostions);
         
                       
                        if(newHscore < currentHScore){
                            possibleHScores.add(newHscore);
                            if(newHscore < bestHscore){
                                bestQueensRowPostions = queenRowPostions.clone();
                                bestHscore = newHscore;
                            }
                        }
                        
                        previousRow = currentRow;
                        currentRow--;
                        newHscore = 0;
                        
                    }
                    
                    playBoard[startRow][startCol] = 1;
                    queenRowPostions[startCol] = startRow;
                    currentRow = startRow;
                    
                    //move down 1 space until we hit lower edge
                    currentRow++;
                    while(currentRow != 8){
                        
                        playBoard[currentRow][startCol] = 1;
                        playBoard[previousRow][startCol] = 0;
                        newHscore += checkAllHorizontal(playBoard, queenRowPostions);
                        newHscore += checkDiag(playBoard, queenRowPostions);
                   
                       
                        if(newHscore < currentHScore){
                            possibleHScores.add(newHscore);
                            if(newHscore < bestHscore){
                                bestQueensRowPostions = queenRowPostions.clone();
                                bestHscore = newHscore;
                            }
                        }
                        
                        previousRow = currentRow;
                        currentRow++;
                        newHscore = 0;
                    }
                    
                    playBoard[previousRow][startCol] = 0;
                    playBoard[startRow][startCol] = 1;
                    queenRowPostions[startCol] = startRow;
                
            }///END OF MOVE CHECKS 

            if(possibleHScores.size() == 0){
                printReset(playBoard, currentHScore);
                System.out.println("Neighboors found with less H: " + possibleHScores.size());
                System.out.println("RESTART");
                resetflag = true;
                restarts++;
                

            }
            else{
                 System.out.println();
                 printBoard(playBoard, currentHScore);
                 System.out.println("Neighboors found with less H: " + possibleHScores.size());
                 Collections.sort(possibleHScores);
                 currentHScore = bestHscore;

                 System.out.println("Setting new current state");
                 for(int i = 0; i < 8; i++){
                    if(queenRowPostions[i] != bestQueensRowPostions[i]){
                        playBoard[queenRowPostions[i]][i] = 0;
                        playBoard[bestQueensRowPostions[i]][i] = 1;
                        movesMade++;
                    }      
                }
                 //update the row positions and clear the arraylist for next iteration
                queenRowPostions = bestQueensRowPostions.clone();
                possibleHScores.clear();
                
            }
        }//END OF WHILE LOOP
        if(checkGoal(currentHScore)){
            printFinalBoard(playBoard, movesMade, restarts);
        }
    } // END OF MAIN   
} //END OF CLASS