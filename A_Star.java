import java.util.*;

//Marcus Katalenas
// Date: 6/2/2020
// A-star pathfinding program
/* The purpose of this program is to find the most efficent path between two points given by the user on the game board using an A-star algorithim 
*/

public class A_Star {

    //--------------------------------------------------------------------
    static void printBoard(Node[][] playBoard){
        Node currentNode;
        for(int row = 0; row < 15; row++){
            for(int col = 0; col < 15; col++){
                currentNode = playBoard[row][col];
                if(currentNode == null){
                    System.out.print(0 + " ");
                }
                else if(currentNode.getType() == 0){
                    System.out.print("X ");
                }
                else if(currentNode.getType() == 1){
                    System.out.print("S ");
                }
                else if(currentNode.getType() == 2){
                    System.out.print("G ");
                }
                else if(currentNode.getType() == 4){
                    System.out.print("P ");
                }
                else{
                    System.out.print(0 + " ");
                }
             }
             System.out.println();
        }
    }
    static void resetBoard(Node[][] playBoard){
        Node currentNode;
        for(int row = 0; row < 15; row++){
            for(int col = 0; col < 15; col++){
                currentNode = playBoard[row][col];
                if(currentNode == null){

                }
                else if(currentNode.getType() == 0){

                }
                else{
                    playBoard[row][col] = null;
                }
            }
        }
        System.out.println();
    }
    /*
    ---------------------------------------------------------------------------------------
    This creates a new node that has not yet been found in the open or closed list
    */
    static void addNewNode(Node[][] playBoard, Node[] sAndg, int r, int c, Node Parent){
        playBoard[r][c] = new Node(r, c, 3);
        playBoard[r][c].setParent(Parent);
        playBoard[r][c].setG(generateG(playBoard[r][c], sAndg[0]));
        playBoard[r][c].setH(generateH(playBoard[r][c], sAndg[1]));
        playBoard[r][c].setF();
    }
    //---------------------------------------------------------------------------------
    // Will calulate the node class varialbe g
    static int generateG(Node currentNode, Node startNode){
        int gScore = 0;
        int currentRow = currentNode.getRow();
        int currentCol = currentNode.getCol();

        //Diag up-left (-,-)
        if(currentRow > startNode.getRow() && currentCol > startNode.getCol()){
            while(currentRow != startNode.getRow() && currentCol != startNode.getCol()){
                currentRow--;
                currentCol--;
                gScore += 14;
            }
        }
        
        //Diag up-right (-,+)
        else if(currentRow > startNode.getRow() && currentCol < startNode.getCol()){
            while(currentRow != startNode.getRow() && currentCol != startNode.getCol()){
                currentRow--;
                currentCol++;
                gScore += 14;
            }
        }
        //Diag Down-left (+,-)
        else if(currentRow < startNode.getRow() && currentCol > startNode.getCol()){
            while(currentRow != startNode.getRow() && currentCol != startNode.getCol()){
                currentRow++;
                currentCol--;
                gScore += 14;
            }
        }
        //Diag Down-right (+,+)
        else{
            while(currentRow != startNode.getRow() && currentCol != startNode.getCol()){
                currentRow++;
                currentCol++;
                gScore += 14;
            }
        }

        if(currentRow == startNode.getRow()){
            if(currentCol < startNode.getCol()){
                while(currentCol != startNode.getCol()){
                    currentCol++;
                    gScore += 10;
                }
            }
            else{
                while(currentCol != startNode.getCol()){
                    currentCol--;
                    gScore += 10;
                }
            }
        }
        //currentCol == gc
        else{
            if(currentRow < startNode.getRow()){
                while(currentRow != startNode.getRow()){
                    currentRow++;
                    gScore += 10;
                }
            }
            else{
                while(currentRow != startNode.getRow()){
                    currentRow--;
                    gScore += 10;
                }
            }
        }

        return gScore;
    }
    //--------------------------------------------------------------------------
    //Will calulate the node class varialbe H
    static int generateH(Node currentNode, Node goalNode){
        int hScore = 0;
        int currentRow = currentNode.getRow();
        int currentCol = currentNode.getCol();

        if(currentRow < goalNode.getRow()){
            while(currentRow != goalNode.getRow()){
                currentRow++;
                hScore += 10;
            }
        }
        else{
            while(currentRow != goalNode.getRow()){
                currentRow--;
                hScore += 10;
            }
        }

        if(currentCol < goalNode.getCol()){
            while(currentCol != goalNode.getCol()){
                currentCol++;
                hScore += 10;
            }
        }
        else{
            while(currentCol != goalNode.getCol()){
                currentCol--;
                hScore += 10;
            }
        }
        return hScore;
    }

    //-------------------------------------------------------------------------
    //Generate random "Unpassable blocks on to the board"
    static void putBadBlocks(Node[][] playBoard){
        int badBlocksplaced = 0;
        int col, row;
        Node badBlock;

        while(badBlocksplaced != 25){
            Random rand = new Random();
            row = rand.nextInt(15);
            col = rand.nextInt(15);

            badBlock = new Node(row, col, 0);
            if(playBoard[row][col] == null){
                playBoard[row][col] = badBlock;
                badBlocksplaced++;
            
            }
        }
    }
    //--------------------------------------------------------------------
    // Takes the board, scanner , and the open list
    // Will ask user for row and column position of both Start and goal
    // Place start and goal node on the board
    // Then add the start node to the open list
    static void putStartAndGoalSpot(Node[][] playBoard, Scanner input, ArrayList<Node> open){
        boolean confirm = false;
        boolean startConfirm, goalConfirm;
        startConfirm = false;
        goalConfirm = false;
        int sr, sc; // COORD VARIABLES FOR START
        int gr, gc; // COORD VARIALBES FOR GOAL
        sr = 0;
        sc = 0;
        gr = 0;
        gc = 0;

        Node Start, Goal;

        //StartSPOT
        while(confirm != true){   
            while(startConfirm == false){
                System.out.println("Please Enter A Number Between 1-15 for the START Row");
                try{
                    sr = input.nextInt();
                    if(sr < 0 || sr > 15){
                        System.out.println("INVALID INPUT (NUMBER NOT IN VALID RANGE)");
                    }
                    else{
                        startConfirm = true;
                    }
                }
                catch(Exception e){
                    System.out.println("INVALID INPUT");
                    input.nextLine();
                }
    
            }

            startConfirm = false;
            while(startConfirm == false){
                System.out.println("Please Enter A Number Between 1-15 for the START Column");
                try{
                    sc = input.nextInt();
                    
                    if(sc < 0 || sc > 15){
                        System.out.println("INVALID INPUT (NUMBER NOT IN VALID RANGE)");
                    }
                    else{
                       startConfirm = true;
                    }
                }
                catch(Exception e){
                    System.out.println("INVALID INPUT");
                    input.nextLine();
                }
            }
        
            if(playBoard[sr-1][sc-1] == null){
                System.out.println("Spot available setting Start position");
                Start = new Node(sr -1, sc - 1, 1);
                Start.setG(0);
                playBoard[sr-1][sc-1] = Start;
                confirm = true;
            }
            else{
                System.out.println("SPOT already filled ");
            }
        }
        confirm = false;

        //GOAL SPOT
        while(confirm != true){
            System.out.println("Please Enter A Number Between 1-15 for the GOAL Row");
            while(goalConfirm == false){
                try{
                    gr = input.nextInt();
                    if(gr < 0 || gr > 15){
                        System.out.println("INVALID INPUT (NUMBER NOT IN VALID RANGE)");
                    }
                    else{
                        goalConfirm = true;
                    }
                    
                }
                catch(Exception e){
                    System.out.println("INVALID INPUT");
                    input.nextLine();
                }
            }
            goalConfirm = false;
            while(goalConfirm == false){
                System.out.println("Please Enter A Number Between 1-15 for the GOAL Column");
                try{
                    gc = input.nextInt();
                    if(gc < 0 || gc > 15){
                        System.out.println("INVALID INPUT (NUMBER NOT IN VALID RANGE)");
                    }
                    else{
                        goalConfirm = true;
                    }
                   
                }
                catch(Exception e){
                    System.out.println("INVALID INPUT");
                    input.nextLine();
                }
            }
           
            if(playBoard[gr-1][gc-1] == null){
                System.out.println("Spot available setting Goal position");
                Goal = new Node(gr -1, gc - 1, 2);
                Goal.setH(0);
                playBoard[gr-1][gc-1] = Goal;
                confirm = true;
            }
            else{
                System.out.println("Spot already filled ");
                input.nextLine();
            }
        }

        Start = playBoard[sr-1][sc-1];
        Goal = playBoard[gr-1][gc-1];

        Start.setH(generateH(Start, Goal));
        Goal.setG(generateG(Goal, Start));

        Start.setF();
        Goal.setF();

        open.add(Start);
        open.add(Goal);
        
        playBoard[sr-1][sc-1] = Start;
        playBoard[gr-1][gc-1] = Goal;
    }
    //--------------------------------------------------------------------
    public static void main(String[] args) {
        System.currentTimeMillis();
       
        Scanner keyboard = new Scanner(System.in);

        //MAKE OPEN AND CLOSED LISTS
        ArrayList<Node> openList = new ArrayList<Node>(); //Min-heap for nodes sorted by node variable of F
        ArrayList<Node> closedList = new ArrayList<Node>(); //List of nodes program has visited
        Node[] startAndGoal = new Node[2]; // hold where the goal and start are to help calulate the other nodes f,g,h
        Node[][] board = new Node[15][15]; //play board that holds all nodes info
        Node currentNode;
        boolean quitFlag = false; //used for check of if user wants to continue setting start and goal spots of the current generated board
        boolean inputFlag = false; // used for checking of valid user input

        char input; // takes input for the continue request response
        
        
        int currentNodeR, currentNodeC;

        //SET BAD BLOCKS

        putBadBlocks(board);
        while(quitFlag == false){
            
            //Ask for position of startspot and goal spot
            putStartAndGoalSpot(board, keyboard, openList);
            startAndGoal[0] = openList.get(0); //start
            startAndGoal[1] = openList.get(1); //goal

            openList.remove(1); //remove goal node 

            currentNode = openList.get(0);


            while(openList.size() != 0 && currentNode.getType() != 2){

                currentNode = openList.get(0);
                openList.remove(0);
                int pos = 0;
                
                if(currentNode.getType() == 2){
                    System.out.println("PATH FOUND");
                }
                else{
                    currentNodeR = currentNode.getRow();
                    currentNodeC = currentNode.getCol();       

                    //UP check
                    currentNodeR --;
                    if(currentNodeR == -1){
                
                        currentNodeR++;
                    }
                    else{
                  //      System.out.print("UP ");
                        if(board[currentNodeR][currentNodeC] == null){
    
                            addNewNode(board, startAndGoal, currentNodeR, currentNodeC, currentNode);
                            if(openList.size() == 0){
                                openList.add(board[currentNodeR][currentNodeC]);
                            }
                            else if(board[currentNodeR][currentNodeC].getF() < openList.get(0).getF()){
                        //        System.out.println(board[currentNodeR][currentNodeC].getF() + " is less than " + openList.get(0).getF());
                                openList.add(0, board[currentNodeR][currentNodeC]);
                            }
                            else{
                                while(pos != openList.size() && board[currentNodeR][currentNodeC].getF() > openList.get(pos).getF()){
                                    pos++;
                                }
                       //         System.out.println("ADDING NODE TO POS " + pos + " in open list");
                                openList.add(pos, board[currentNodeR][currentNodeC]);
                            }
                        //    System.out.println("ADDED NODE");
                        }
                        else{
                            if(board[currentNodeR][currentNodeC].getType() == 0 || board[currentNodeR][currentNodeC].getType() == 2){
                                if(board[currentNodeR][currentNodeC].getType() == 0){
                            //        System.out.println("SPOT TAKEN BY AN UNBLOCKABLE SPOT");
                                }
                                else{
                             //       System.out.println("GOAL FOUND");
                                    board[currentNodeR][currentNodeC].setParent(currentNode);
                                    openList.add(0,board[currentNodeR][currentNodeC]);
                                }
                            }
                            else if(closedList.contains(board[currentNodeR][currentNodeC])){
                         //       System.out.println("SPOT ALREADY IN CLOSED LIST");
                            }
                            else if(openList.contains(board[currentNodeR][currentNodeC])){
                          //      System.out.println("SPOT ALREADY IN OPEN LIST");
                            }
                        }
                        //go back to current node pos
                        currentNodeR++;
                    }

                    //reset
                    pos = 0;
                    //DOWN
                    currentNodeR++;
                    if(currentNodeR == 15){
                    //    System.out.println("OUT OF BOUNDS");
                        currentNodeR--;
                    }
                    else{
                       // System.out.print("Down ");
                        if(board[currentNodeR][currentNodeC] == null){
                            addNewNode(board, startAndGoal, currentNodeR, currentNodeC, currentNode);
                            if(openList.size() == 0 || openList.size() == 1){
                                openList.add(board[currentNodeR][currentNodeC]);
                            }
                            else if(board[currentNodeR][currentNodeC].getF() < openList.get(0).getF()){
                           //     System.out.println(board[currentNodeR][currentNodeC].getF() + " is less than " + openList.get(0).getF());
                                openList.add(0, board[currentNodeR][currentNodeC]);
                            }
                            else{
                                while(pos != openList.size() && board[currentNodeR][currentNodeC].getF() > openList.get(pos).getF()){
                                    pos++;
                                }
                            //    System.out.println("ADDING NODE TO POS " + pos + " in open list");
                                openList.add(pos, board[currentNodeR][currentNodeC]);
                            }
                         //   System.out.println("ADDED NODE");
                        }
                        else{
                            if(board[currentNodeR][currentNodeC].getType() == 0 || board[currentNodeR][currentNodeC].getType() == 2){
                                if(board[currentNodeR][currentNodeC].getType() == 0){
                               //     System.out.println("SPOT TAKEN BY AN UNBLOCKABLE SPOT");
                                }
                                else{
                                //    System.out.println("GOAL FOUND");
                                    board[currentNodeR][currentNodeC].setParent(currentNode);
                                    openList.add(0,board[currentNodeR][currentNodeC]);
                                }
                            }
                            else if(closedList.contains(board[currentNodeR][currentNodeC])){
                              //  System.out.println("SPOT ALREADY IN CLOSED LIST");
                            }
                            else if(openList.contains(board[currentNodeR][currentNodeC])){
                       //         System.out.println("SPOT ALREADY IN OPEN LIST");
                            }
                        }
                        currentNodeR--;
                    }
    
                    pos = 0;
                    //LEFT CHECK
                    currentNodeC--;
                    if(currentNodeC == -1){
                     //   System.out.println("OUT OF BOUNDS");
                        currentNodeC++;
                    }
                    else{
                     //   System.out.print("Left ");
                        if(board[currentNodeR][currentNodeC] == null){
                            addNewNode(board, startAndGoal, currentNodeR, currentNodeC, currentNode);
                            if(openList.size() == 0){
                                openList.add(board[currentNodeR][currentNodeC]);
                            }
                            else if(board[currentNodeR][currentNodeC].getF() < openList.get(0).getF()){
                              //  System.out.println(board[currentNodeR][currentNodeC].getF() + " is less than " + openList.get(0).getF());
                                openList.add(0, board[currentNodeR][currentNodeC]);
                            }
                            else{
                                while(pos != openList.size() && board[currentNodeR][currentNodeC].getF() > openList.get(pos).getF()){
                                    pos++;
                                }
                        //        System.out.println("ADDING NODE TO POS " + pos + " in open list");
                                openList.add(pos, board[currentNodeR][currentNodeC]);
                            }
                       //     System.out.println("ADDED NODE");
                        }
                        else{
                            if(board[currentNodeR][currentNodeC].getType() == 0){
                                if(board[currentNodeR][currentNodeC].getType() == 0){
                         //           System.out.println("SPOT TAKEN BY AN UNBLOCKABLE SPOT");
                                }
                                else{
                          //          System.out.println("GOAL FOUND");
                                    board[currentNodeR][currentNodeC].setParent(currentNode);
                                    openList.add(0,board[currentNodeR][currentNodeC]);
                                }
                            }
                            else if(closedList.contains(board[currentNodeR][currentNodeC])){
                          //      System.out.println("SPOT ALREADY IN CLOSED LIST");
                            }
                            else if(openList.contains(board[currentNodeR][currentNodeC])){
                         //       System.out.println("SPOT ALREADY IN OPEN LIST");
                            }
                        }
                        currentNodeC++;
                    }
    
                    pos = 0;
                    //RIGHT CHECK
                    currentNodeC++;
                    if(currentNodeC == 15){
                 //       System.out.println("OUT OF BOUNDS");
                        currentNodeC--;
                    }
                    else{
                     //   System.out.print("RIGHT ");
                        if(board[currentNodeR][currentNodeC] == null){
                           addNewNode(board, startAndGoal, currentNodeR, currentNodeC, currentNode);
                            if(openList.size() == 0 || openList.size() == 1){
                            openList.add(board[currentNodeR][currentNodeC]);
                            }
                            else if(board[currentNodeR][currentNodeC].getF() < openList.get(0).getF()){
                        //        System.out.println(board[currentNodeR][currentNodeC].getF() + " is less than " + openList.get(0).getF());
                                openList.add(0, board[currentNodeR][currentNodeC]);
                            }
                            else{
                                while(pos != openList.size() && board[currentNodeR][currentNodeC].getF() > openList.get(pos).getF()){
                                    pos++;
                                }
                          //      System.out.println("ADDING NODE TO POS " + pos + " in open list");
                                openList.add(pos, board[currentNodeR][currentNodeC]);
                            }
                         //   System.out.println("ADDED NODE");
                        }
                        else{
                            if(board[currentNodeR][currentNodeC].getType() == 0 || board[currentNodeR][currentNodeC].getType() == 2){
                                if(board[currentNodeR][currentNodeC].getType() == 0){
                                //    System.out.println("SPOT TAKEN BY AN UNBLOCKABLE SPOT");
                                }
                                else{
                         //           System.out.println("GOAL FOUND");
                                    board[currentNodeR][currentNodeC].setParent(currentNode);
                                    openList.add(0,board[currentNodeR][currentNodeC]);
                                }
                            }
                            else if(closedList.contains(board[currentNodeR][currentNodeC])){
                    //            System.out.println("SPOT ALREADY IN CLOSED LIST");
                            }
                            else if(openList.contains(board[currentNodeR][currentNodeC])){
                   //             System.out.println("SPOT ALREADY IN OPEN LIST");
                            }
                        }
                        currentNodeC--;
                    }
    
                    pos = 0;
                    //UP-LEFT -,-
                    currentNodeR--;
                    currentNodeC--;
                    if(currentNodeR == -1 || currentNodeC == -1){
                    //    System.out.println("OUT OF BOUNDS");
                        currentNodeR++;
                        currentNodeC++;
                    }
                    else{
                       // System.out.print("UP-LEFT ");
                        if(board[currentNodeR][currentNodeC] == null){
                            addNewNode(board, startAndGoal, currentNodeR, currentNodeC, currentNode);
                            if(openList.size() == 0 || openList.size() == 1){
                                openList.add(board[currentNodeR][currentNodeC]);
                                }
                            else if(board[currentNodeR][currentNodeC].getType() == 0 || board[currentNodeR][currentNodeC].getType() == 2){
                              //   System.out.println(board[currentNodeR][currentNodeC].getF() + " is less than " + openList.get(0).getF());
                                 openList.add(0,board[currentNodeR][currentNodeC]);
                             }
                            else{
                                 while(pos != openList.size() && board[currentNodeR][currentNodeC].getF() > openList.get(pos).getF()){
                                     pos++;
                                 }
                              //   System.out.println("ADDING NODE TO POS " + pos + " in open list");
                                 openList.add(pos, board[currentNodeR][currentNodeC]);
                             }
                        //    System.out.println("ADDED NODE");
                         }
                         else{
                            if(board[currentNodeR][currentNodeC].getType() == 0){
                                if(board[currentNodeR][currentNodeC].getType() == 0){
                              //      System.out.println("SPOT TAKEN BY AN UNBLOCKABLE SPOT");
                                }
                                else{
                           //         System.out.println("GOAL FOUND");
                                    board[currentNodeR][currentNodeC].setParent(currentNode);
                                    openList.add(0,board[currentNodeR][currentNodeC]);
                                }
                            }
                            else if(closedList.contains(board[currentNodeR][currentNodeC])){
                           //     System.out.println("SPOT ALREADY IN CLOSED LIST");
                            }
                            else if(openList.contains(board[currentNodeR][currentNodeC])){
                         //       System.out.println("SPOT ALREADY IN OPEN LIST");
                            }
                        }
                        currentNodeR++;
                        currentNodeC++;
                    }
    
                    pos = 0;
                    //UP -RIGHT -,+
                    currentNodeR--;
                    currentNodeC++;
                    if(currentNodeR == -1 || currentNodeC == 15){
                      //  System.out.println("OUT OF BOUNDS");
                        currentNodeR++;
                        currentNodeC--;
                    }
                    else{
                       // System.out.print("UP-Right ");
                        if(board[currentNodeR][currentNodeC] == null){
                            addNewNode(board, startAndGoal, currentNodeR, currentNodeC, currentNode);
                            if(openList.size() == 0 || openList.size() == 1){
                                openList.add(board[currentNodeR][currentNodeC]);
                                }
                             else if(board[currentNodeR][currentNodeC].getF() < openList.get(0).getF()){
                             //    System.out.println(board[currentNodeR][currentNodeC].getF() + " is less than " + openList.get(0).getF());
                                 openList.add(board[currentNodeR][currentNodeC]);
                             }
                             else{
                                 while(pos != openList.size() && board[currentNodeR][currentNodeC].getF() > openList.get(pos).getF()){
                                     pos++;
                                 }
                           //      System.out.println("ADDING NODE TO POS " + pos + " in open list");
                                 openList.add(pos, board[currentNodeR][currentNodeC]);
                             }
                         //    System.out.println("ADDED NODE");
                         }
                         else{
                            if(board[currentNodeR][currentNodeC].getType() == 0 || board[currentNodeR][currentNodeC].getType() == 2){
                                if(board[currentNodeR][currentNodeC].getType() == 0){
                             //       System.out.println("SPOT TAKEN BY AN UNBLOCKABLE SPOT");
                                }
                                else{
                            //        System.out.println("GOAL FOUND");
                                    board[currentNodeR][currentNodeC].setParent(currentNode);
                                    openList.add(0,board[currentNodeR][currentNodeC]);
                                }
                            }
                            else if(closedList.contains(board[currentNodeR][currentNodeC])){
                         //       System.out.println("SPOT ALREADY IN CLOSED LIST");
                            }
                            else if(openList.contains(board[currentNodeR][currentNodeC])){
                         //       System.out.println("SPOT ALREADY IN OPEN LIST");
                            }
                        }
                        currentNodeR++;
                        currentNodeC--;
                    }
    
                    pos = 0;
                    //DOWN-LEFt +,-
                    currentNodeR++;
                    currentNodeC--;
                    if(currentNodeR == 15 || currentNodeC == -1){
                       // System.out.println("OUT OF BOUNDS");
                        currentNodeR--;
                        currentNodeC++;
                    }
                    else{
                       // System.out.print("Down-LEFT ");
                        if(board[currentNodeR][currentNodeC] == null){
                            addNewNode(board, startAndGoal, currentNodeR, currentNodeC, currentNode);
                            if(openList.size() == 0 || openList.size() == 1){
                                openList.add(board[currentNodeR][currentNodeC]);
                                }
                             else if(board[currentNodeR][currentNodeC].getF() < openList.get(0).getF()){
                              //   System.out.println(board[currentNodeR][currentNodeC].getF() + " is less than " + openList.get(0).getF());
                                 openList.add(board[currentNodeR][currentNodeC]);
                             }
                             else{
                                 while(pos != openList.size() && board[currentNodeR][currentNodeC].getF() > openList.get(pos).getF()){
                                     pos++;
                                 }
                               //  System.out.println("ADDING NODE TO POS " + pos + " in open list");
                                 openList.add(pos, board[currentNodeR][currentNodeC]);
                             }
                           //  System.out.println("ADDED NODE");
                         }
                         else{
                            if(board[currentNodeR][currentNodeC].getType() == 0 || board[currentNodeR][currentNodeC].getType() == 2){
                                if(board[currentNodeR][currentNodeC].getType() == 0){
                                 //   System.out.println("SPOT TAKEN BY AN UNBLOCKABLE SPOT");
                                }
                                else{
                                 //   System.out.println("GOAL FOUND");
                                    board[currentNodeR][currentNodeC].setParent(currentNode);
                                    openList.add(board[currentNodeR][currentNodeC]);
                                }
                            }
                            else if(closedList.contains(board[currentNodeR][currentNodeC])){
                              //  "SPOT ALREADY IN CLOSED LIST"
                            }
                            else if(openList.contains(board[currentNodeR][currentNodeC])){
                            //   "SPOT ALREADY IN OPEN LIST"
                            }
                        }
                         currentNodeR--;
                         currentNodeC++;
                    }
    
                    pos = 0;
                    //DOWN -right +,+
                    currentNodeR++;
                    currentNodeC++;
                    if(currentNodeR == 15 || currentNodeC == 15){
                     //   System.out.println("OUT OF BOUNDS");
                        currentNodeR--;
                        currentNodeC--;
                    }
                    else{
                    //    System.out.print("Down-Right ");
                        if(board[currentNodeR][currentNodeC] == null){
                            addNewNode(board, startAndGoal, currentNodeR, currentNodeC, currentNode);
                            if(openList.size() == 0 || openList.size() == 1){
                                openList.add(board[currentNodeR][currentNodeC]);
                                }
                             else if(board[currentNodeR][currentNodeC].getF() < openList.get(0).getF()){
                             //    System.out.println(board[currentNodeR][currentNodeC].getF() + " is less than " + openList.get(0).getF());
                                 openList.add(board[currentNodeR][currentNodeC]);
                             }
                             else{
                                 while(pos != openList.size() && board[currentNodeR][currentNodeC].getF() > openList.get(pos).getF()){
                                     pos++;
                                 }
                             //    System.out.println("ADDING NODE TO POS " + pos + " in open list");
                                 openList.add(pos, board[currentNodeR][currentNodeC]);
                             }
                           //  System.out.println("ADDED NODE");
                         }
                         else{
                            if(board[currentNodeR][currentNodeC].getType() == 0 || board[currentNodeR][currentNodeC].getType() == 2){
                                if(board[currentNodeR][currentNodeC].getType() == 0){
                              //      System.out.println("SPOT TAKEN BY AN UNBLOCKABLE SPOT");
                                }
                                else{
                            //        System.out.println("GOAL FOUND");
                                    board[currentNodeR][currentNodeC].setParent(currentNode);
                                    openList.add(0,board[currentNodeR][currentNodeC]);
                                }
                            }
                            else if(closedList.contains(board[currentNodeR][currentNodeC])){
                           //     System.out.println("SPOT ALREADY IN CLOSED LIST");
                            }
                            else if(openList.contains(board[currentNodeR][currentNodeC])){
                          //      System.out.println("SPOT ALREADY IN OPEN LIST");
                            }
                        }
                        currentNodeR--;
                        currentNodeC--;
                    }
                }
    
                pos = 0;
                closedList.add(currentNode);
               // System.out.println("NODE DONE CHECKING NEXT NODE ");
              //  System.out.println();
              //  System.out.println();
            }
            
            if(currentNode.getParent() == null){
                System.out.println("No Path COULD NOT BE FOUND");
            }
            else{
                while(currentNode.getParent()  != null){
                   // System.out.println("PARENT NOT NULL");
                    currentNode = currentNode.getParent();
                    if(currentNode.getType()== 1){
                        System.out.println("Start Node");
                    }
                    else{
                        board[currentNode.getRow()][currentNode.getCol()].setType();
                    }
                }
            }
            System.out.println();
            printBoard(board);
            System.out.println();

            resetBoard(board);
            keyboard.nextLine();
            inputFlag = false;
            while(inputFlag == false){
                System.out.println("Would you like to set new start and goal path Enter Y for YES or N for NO (CASE SENSTIVE)");
                try{
                    
                    input = keyboard.next().charAt(0);
                    
                    if(input == 'Y'){
                        openList.clear();
                        closedList.clear();
                        resetBoard(board);
                        inputFlag = true;
                    }
                    else if(input == 'N'){
                        openList.clear();
                        closedList.clear();
                        inputFlag = true;
                        quitFlag = true;
                    }
                    else{
                        System.out.print(input + " ");
                        System.out.println("INVALID LETTER INPUT ");
                        keyboard.nextLine();
                    }
                }
                catch(Exception e){
                    System.out.println("INVALID INPUT");
                    keyboard.nextLine();
                }
            }
        }

        System.out.println("THANK YOU FOR USING THE A_STAR PROGRAM");
        System.exit(0);
    }
}