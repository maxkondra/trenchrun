import java.util.Scanner;

public class TrenchRun {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    int[][] GameBoard;
    int[][] TempBoard;

    //boolean

    boolean PlayerTieJustMovedSideways = false;
    boolean ComputerTieJustMovedSideways = false;

    public TrenchRun() {
        initializeBoard();
    }

    private void initializeBoard() {
        //- = 0, t = 1, x = 2, @ = 3, + = 4, T = 5, X = 6, * = 7, ~ = 8

        GameBoard = new int[][]{
                { 0, 1, 1, 0, 1, 1, 0 },
                { 0, 0, 4, 3, 4, 0, 0 },
                { 2, 2, 0, 0, 0, 2, 2 },
                { 0, 0, 0, 0, 0, 0, 0 },
                { 6, 6, 0, 0, 0, 6, 6 },
                { 0, 0, 8, 7, 8, 0, 0 },
                { 0, 5, 5, 0, 5, 5, 0 }
        };

        TempBoard = GameBoard;
    }

    public void Play(){
        //declare some stuff

        //Ask who goes first
        boolean humanFirst = humanFirst();

        //Display Board
        displayBoard();

        //if player, ask for move
        if(humanFirst){
            getAndMakeMove();
        }
        while (!gameOver()){
            //Computer decides a move
            //Update board state
            //Display Board
            getAndMakeMove();
        }

        //determine winner
    }

    private int[][] generateMoves(boolean humanMove, int[][] boardState, boolean playerTieJustMovedSideways, boolean computerTieJustMovedSideways){
        int[][] moves = new int[300][4];
        int[][] movablePieces = new int[8][2];
        int k = 0, l = 0;

        //Get ally pieces from board
        if(humanMove){
            for(int i=0; i<7; i++){
                for(int j=0; j<7;j++){
                    if(boardState[i][j] == 1 || boardState[i][j] == 2){
                        movablePieces[k][0] = i;
                        movablePieces[k][1] = j;
                        k++;
                    }
                }
            }
        }else{
            for(int i=0; i<7; i++){
                for(int j=0; j<7;j++){
                    if(boardState[i][j] == 5 || boardState[i][j] == 6){
                        movablePieces[k][0] = i;
                        movablePieces[k][1] = j;
                        k++;
                    }
                }
            }
        }

        //Determine all moves for pieces
        for(int i =0; i<k; i++){
            if(boardState[movablePieces[k][0]][movablePieces[k][1]] == 1 || boardState[movablePieces[k][0]][movablePieces[k][1]] == 5){
                //Tie Fighters
                if(humanMove){
                    int x = movablePieces[k][0]+1;
                    int y = movablePieces[k][1];
                    //Up for human Tie's
                    while(x < 7 && (boardState[x][y] == 0 || boardState[x][y] == 5 || boardState[x][y] == 6)){
                        moves[l][0] = movablePieces[k][0];
                        moves[l][1] = movablePieces[k][1];
                        moves[l][2] = x;
                        moves[l][3] = y;
                        l++;
                        x++;
                    }
                    x = movablePieces[k][0]-1;
                    //Down for human Tie's
                    while(x > -1 && (boardState[x][y] == 0 || boardState[x][y] == 5 || boardState[x][y] == 6 || boardState[x][y] == 7)){
                        if(boardState[x][y] == 5 || boardState[x][y] == 6 || boardState[x][y] == 7){
                            moves[l][0] = movablePieces[k][0];
                            moves[l][1] = movablePieces[k][1];
                            moves[l][2] = x;
                            moves[l][3] = y;
                            l++;
                        }
                        x--;
                    }
                    //Sideways for human Tie's
                    if(!playerTieJustMovedSideways){
                        x = movablePieces[k][0];
                        y = movablePieces[k][1]+1;
                        //Right
                        while(y < 7 && (boardState[x][y] == 0 || boardState[x][y] == 5 || boardState[x][y] == 6)){
                            moves[l][0] = movablePieces[k][0];
                            moves[l][1] = movablePieces[k][1];
                            moves[l][2] = x;
                            moves[l][3] = y;
                            l++;
                            y++;
                        }
                        y = movablePieces[k][1]-1;
                        //Left
                        while(y > -1 && (boardState[x][y] == 0 || boardState[x][y] == 5 || boardState[x][y] == 6)){
                            moves[l][0] = movablePieces[k][0];
                            moves[l][1] = movablePieces[k][1];
                            moves[l][2] = x;
                            moves[l][3] = y;
                            l++;
                            y--;
                        }
                    }
                }else{
                    int x = movablePieces[k][0]+1;
                    int y = movablePieces[k][1];
                    //Up for Computer Tie's
                    //TODO DOOOOOOO THIIIISSSS
                    while(x < 7 && (boardState[x][y] == 0 || boardState[x][y] == 1 || boardState[x][y] == 2 || boardState[x][y] == 3)){
                        if(boardState[x][y] == 1 || boardState[x][y] == 2 || boardState[x][y] == 3) {
                            moves[l][0] = movablePieces[k][0];
                            moves[l][1] = movablePieces[k][1];
                            moves[l][2] = x;
                            moves[l][3] = y;
                            l++;
                        }
                        x++;
                    }
                    x = movablePieces[k][0]-1;
                    //Down for human Tie's
                    while(x > -1 && (boardState[x][y] == 0 || boardState[x][y] == 5 || boardState[x][y] == 6 || boardState[x][y] == 7)){
                        if(boardState[x][y] == 5 || boardState[x][y] == 6 || boardState[x][y] == 7){
                            moves[l][0] = movablePieces[k][0];
                            moves[l][1] = movablePieces[k][1];
                            moves[l][2] = x;
                            moves[l][3] = y;
                            l++;
                        }
                        x--;
                    }
                    //Sideways for human Tie's
                    if(!playerTieJustMovedSideways) {
                        x = movablePieces[k][0];
                        y = movablePieces[k][1] + 1;
                        //Right
                        while (y < 7 && (boardState[x][y] == 0 || boardState[x][y] == 5 || boardState[x][y] == 6)) {
                            moves[l][0] = movablePieces[k][0];
                            moves[l][1] = movablePieces[k][1];
                            moves[l][2] = x;
                            moves[l][3] = y;
                            l++;
                            y++;
                        }
                        y = movablePieces[k][1] - 1;
                        //Left
                        while (y > -1 && (boardState[x][y] == 0 || boardState[x][y] == 5 || boardState[x][y] == 6)) {
                            moves[l][0] = movablePieces[k][0];
                            moves[l][1] = movablePieces[k][1];
                            moves[l][2] = x;
                            moves[l][3] = y;
                            l++;
                            y--;
                        }
                    }
                }
            }else if(boardState[movablePieces[k][0]][movablePieces[k][1]] == 2 || boardState[movablePieces[k][0]][movablePieces[k][1]] == 6){
                //X Wings
                if(humanMove){

                }else{

                }
            }
        }




        return moves;
    }

    private boolean gameOver() {
        if(GameBoard[1][3] != 3 || GameBoard[5][3] != 7){
            return true;
        }
        //TODO no moves return true
        return false;
    }

    private void getAndMakeMove(){
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter your move in format \"A1B1\": ");
        String move = input.next();

        //Check legal
        while(!legalMove(move)){
            System.out.print("Please enter your move in format \"A1B1\": ");
            input.nextLine();
            move = input.next();
        }

        makeMove(move);
        displayBoard();
    }

    private boolean legalMove(String move) {
        //Wrong format or length
        if(move.length() != 4){
            System.out.print("ERROR: Wrong format. Move too long.");
            return false;
        }
        int w = letterAxisToInt(move.charAt(0));
        int x = Character.getNumericValue(move.charAt(1))-1;
        int y = letterAxisToInt(move.charAt(2));
        int z = Character.getNumericValue(move.charAt(3))-1;

        //Out of bounds move
        if(w < 0 || w > 6 || x < 0 || x > 6 || y < 0 || y > 6 || z < 0 || z > 6){
            System.out.print("ERROR: Move out of bounds.");
            return false;
        }

        //Trying to move something other than human tie fighter or x wing
        if(GameBoard[x][w] != 1 && GameBoard[x][w] != 2){
            System.out.print("ERROR: Can only move your own pieces.");
            return false;
        }

        //Moving backwards and not capturing a piece.
        if(x > z && (GameBoard[z][y] != 5 && GameBoard[z][y] != 6 && GameBoard[z][y] != 7)){
            System.out.println("ERROR: Cannot move backwards if not capturing a piece.");
            return false;
        }

        //Capturing your own piece or a wall
        if(GameBoard[z][y] == 1 || GameBoard[z][y] == 2 || GameBoard[z][y] == 3 || GameBoard[z][y] == 4 || GameBoard[z][y] == 8){
            System.out.println("ERROR: Cannot capture or own piece or land on a wall.");
            return false;
        }

        //Trying to kill the Death Star head on you cheater?
        if(GameBoard[z][y] == 7 && x < z){
            System.out.println("ERROR: Can't destroy the Death Star head on.");
            return false;
        }

        //Tie fighter moving illegally
        if(GameBoard[x][w] == 1){

            //Tie fighter just moved sideways last turn
            if(z == x && w != y && PlayerTieJustMovedSideways){
                System.out.println("ERROR: Just moved Tie Fighter sideways last turn.");
                return false;
            }

            //Not moving horizontally or vertically
            if(w != y && x != z) {
                System.out.println("ERROR: Must move Tie Fighter horizontally or vertically.");
                return false;
            }

            //Jumping over a piece
            if(w == y){
                for(int i = x+1; i < z; i++){
                    if (GameBoard[i][w] != 0) {
                        System.out.println("ERROR: Cannot jump over a piece.");
                        return false;
                    }
                }
            }
            if(x == z){
                for(int i = w+1; i < y; i++){
                    if(GameBoard[x][i] != 0){
                        System.out.println("ERROR: Cannot jump over a piece.");
                        return false;
                    }
                }
            }
        }

        //X Wing moving illegally
        if(GameBoard[x][w] == 2){

            //Not moving vertically
            if(Math.abs(z-x) != Math.abs(y-w)){
                System.out.println("ERROR: Must move X Wing diagonally.");
                return false;
            }

            //Jumping over a piece
            int i = x;
            int j = w;
            while(i != z-1 && j != y-1){
                if(i < z-1){
                    i++;
                }else if(i > z-1){
                    i--;
                }if(j < y-1){
                    j++;
                }else if(j > y-1){
                    j--;
                }
                if(GameBoard[i][j] != 0){
                    System.out.println("ERROR: Cannot jump over a piece.");
                    return false;
                }
            }
        }


        return true;
    }

    private void makeMove(String move) {
        int w = letterAxisToInt(move.charAt(0));
        int x = Character.getNumericValue(move.charAt(1))-1;
        int y = letterAxisToInt(move.charAt(2));
        int z = Character.getNumericValue(move.charAt(3))-1;

        GameBoard[z][y] = GameBoard[x][w];
        GameBoard[x][w] = 0;

        if(GameBoard[z][y] == 1){
            if(z == x && w != y){
                PlayerTieJustMovedSideways = true;
            }else{
                PlayerTieJustMovedSideways = false;
            }
        }else{
            PlayerTieJustMovedSideways = false;
        }
    }
/*
    private void makeMove(String move, boolean humanTurn){
        int w = letterAxisToInt(move.charAt(0));
        int x = Character.getNumericValue(move.charAt(1))-1;
        int y = letterAxisToInt(move.charAt(2));
        int z = Character.getNumericValue(move.charAt(3))-1;

        TempBoard[z][y] = TempBoard[x][w];
        TempBoard[x][w] = 0;

        if(TempBoard[z][y] == 1){
            if(z == x && w != y){
                if(humanTurn){
                    playerMovedTieSideways = true;
                }else{

                }
            }
            }else{
                PlayerTieJustMovedSideways = false;
            }
        }else{
            PlayerTie
    }*/

    private int letterAxisToInt(char c) {
        switch (c){
            case 'A':case 'a':
                return 0;
            case 'B':case 'b':
                return 1;
            case 'C':case 'c':
                return 2;
            case 'D':case 'd':
                return 3;
            case 'E':case 'e':
                return 4;
            case 'F':case 'f':
                return 5;
            case 'G':case 'g':
                return 6;
            default:
                return -1;
        }
    }

    private boolean humanFirst(){
        Scanner input = new Scanner(System.in);
        System.out.print("Do you want to go first? (yes no): ");
        String ans = input.next();
        if(ans.equals("yes")){
            return true;
        }
        return false;
    }

    private void displayBoard(){
        System.out.println("     COMPUTER    ");
        for(int i = 6; i>=0; i--){
            System.out.print(i+1+"  ");
            String line = "";
            for(int j = 0; j<7; j++){
                line += getPieceChar(GameBoard[i][j]) + " ";
            }
            System.out.println(line + "\n");
        }
        System.out.println("   A B C D E F G");
        System.out.println("      HUMAN     ");
    }

    private String getPieceChar(int i){
        //- = 0, t = 1, x = 2, @ = 3, + = 4, T = 5, X = 6, * = 7, ~ = 8
        switch (i){
            case 0:
                return "-";
            case 1:
                return ANSI_YELLOW + "t" + ANSI_RESET;
            case 2:
                return ANSI_YELLOW + "x" + ANSI_RESET;
            case 3:
                return ANSI_RED + "@" + ANSI_RESET;
            case 4:
                return ANSI_GREEN + "+" + ANSI_RESET;
            case 5:
                return ANSI_CYAN + "T" + ANSI_RESET;
            case 6:
                return ANSI_CYAN + "X" + ANSI_RESET;
            case 7:
                return ANSI_BLUE + "*" + ANSI_RESET;
            case 8:
                return ANSI_GREEN + "~" + ANSI_RESET;
            default:
                return "-";
        }
    }

}
