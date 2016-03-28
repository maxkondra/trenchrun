import java.util.Scanner;
import java.util.Stack;

public class TrenchRun {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private int[][] GameBoard;
    private Stack<int[]> movesOnBoard;
    private boolean GameOver = false;
    private int Winner = 0; // 0 = no one, 1 = human, 2 = computer

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

        movesOnBoard = new Stack<>();
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
        while (!GameOver){
            //Computer decides and makes a move
            makeFinalMove(computerDecideAMove(), false);
            GameOver = isGameOver(true);

            //Display Board
            displayBoard();

            //Get Player Input
            getAndMakeMove();
            GameOver = isGameOver(false);
        }

        //determine winner
    }

    private String computerDecideAMove() {
        return miniMax(generateMoves(false, PlayerTieJustMovedSideways, ComputerTieJustMovedSideways).getMovesC());
    }

    private String miniMax(int[][] moves) {
        int[] bestMove;
        int bestScore = -10000;
        int moveScore;
        int i = 0;
        while(moves[i][0] != 0 || moves[i][1] != 0 || moves[i][2] != 0 || moves[i][3] != 0){
            makeTempMove(moves[i], false);
            moveScore = min(0);
            if(moveScore > bestScore){
                bestScore = moveScore;
                bestMove = moves[i];
            }
            undoTempMove();
        }
        return "";//TODO int[] to string
    }

    private int min(int depth) {
        if (isGameOver(true)){
            return 10000;
        }
        if (depth = maxDepth){
            return evaluatePosition(true, GameBoard, PlayerTieJustMovedSideways, ComputerTieJustMovedSideways).getScore();
        }
        int bestScore = 10000;
        int[][] moves =
    }

    private void makeTempMove(int[] move, boolean humanMove) {
        int w = move[0], x = move[1], y = move[2], z = move[3];
        movesOnBoard.push(new int[]{y, z, w, x, GameBoard[z][y], PlayerTieJustMovedSideways ? 1 : 0, ComputerTieJustMovedSideways ? 1 : 0});
        GameBoard[z][y] = GameBoard[x][w];
        GameBoard[x][w] = 0;

        if(humanMove){
            if(GameBoard[z][y] == 1){
                if(z == x && w != y){
                    PlayerTieJustMovedSideways = true;
                }else{
                    PlayerTieJustMovedSideways = false;
                }
            }else{
                PlayerTieJustMovedSideways = false;
            }
        }else {
            if(GameBoard[z][y] == 5){
                if(z == x && w != y){
                    ComputerTieJustMovedSideways = true;
                }else{
                    ComputerTieJustMovedSideways = false;
                }
            }else{
                ComputerTieJustMovedSideways = false;
            }
        }
    }


    private MoveAndScoreHolder evaluatePosition(boolean humanMove, int[][] GameBoard, boolean playerTieJustMovedSideways, boolean computerTieJustMovedSideways){
        int score = 0;
        int[][] moves;
        int[][] movesC;
        int[][] movablePiecesC;
        int[][] movablePiecesH;
        MoveAndMovablePiecesHolder moveAndMovablePiecesHolder;

        if(GameBoard[1][3] != 3){
            return new MoveAndScoreHolder(10000, null);
        }
        if(GameBoard[5][3] != 7){
            return new MoveAndScoreHolder(-10000, null);
        }
        moveAndMovablePiecesHolder = generateMoves(humanMove, playerTieJustMovedSideways, computerTieJustMovedSideways);
        movesC = moveAndMovablePiecesHolder.getMovesC();
        movablePiecesC = moveAndMovablePiecesHolder.getMovablePiecesC();
        movablePiecesH = moveAndMovablePiecesHolder.getMovablePiecesH();

        if(movesC[0][0] == 0 && movesC[0][1] == 0 && movesC[0][2] == 0 && movesC[0][3] == 0 ){
            return new MoveAndScoreHolder(-10000, null);
        }

        for(int i=0;i<movablePiecesC.length;i++){
            if(GameBoard[movablePiecesC[i][0]][movablePiecesC[i][1]] == 5){
                score += 50;
            }
            if(GameBoard[movablePiecesC[i][0]][movablePiecesC[i][1]] == 6){
                score += 40;
            }
        }
        for(int i=0;i<movablePiecesH.length;i++){
            if(GameBoard[movablePiecesH[i][0]][movablePiecesH[i][1]] == 5){
                score -= 50;
            }
            if(GameBoard[movablePiecesH[i][0]][movablePiecesH[i][1]] == 6){
                score -= 40;
            }
        }

        for (int[] move1 : movesC) {
            if (move1[0] != 0 || move1[1] != 0 || move1[2] != 0 || move1[3] != 0) {
                for (int j = 0; j < move1.length; j++) {
                    score += 1;
                }
            }
        }

        if(humanMove){
            moves = moveAndMovablePiecesHolder.getMovesH();
        }else{
            moves = movesC;
        }
        return new MoveAndScoreHolder(score, moves);
    }

    private void printMoves(int[][] moves) {
        for (int[] move1 : moves) {
            String move = "";
            if (move1[0] != 0 || move1[1] != 0 || move1[2] != 0 || move1[3] != 0) {
                for (int j = 0; j < move1.length; j++) {
                    if (j == 0 || j == 2) {
                        move += intAxisToLetter(move1[j]);
                    } else {
                        move += move1[j] + 1 + " ";
                    }
                }
                System.out.println(move);
            }
        }
    }

    private MoveAndMovablePiecesHolder generateMoves(boolean humanMove, boolean playerTieJustMovedSideways, boolean computerTieJustMovedSideways){
        int[][] movesC = new int[100][4];
        int[][] movesH = new int[100][4];
        int[][] movablePieces;
        int[][] movablePiecesC = new int[8][2];
        int[][] movablePiecesH = new int[8][2];
        int k = 0, l = 0;

        //Get all pieces from board
        for(int i=0; i<7; i++){
            for(int j=0; j<7;j++){
                if(GameBoard[i][j] == 5 || GameBoard[i][j] == 6){
                    movablePiecesC[k][0] = i;
                    movablePiecesC[k][1] = j;
                    k++;
                }
            }
        }
        for(int i=0; i<7; i++){
            for(int j=0; j<7;j++){
                if(GameBoard[i][j] == 1 || GameBoard[i][j] == 2){
                    movablePiecesH[k][0] = i;
                    movablePiecesH[k][1] = j;
                    k++;
                }
            }
        }

        if(humanMove){
            movablePieces = movablePiecesH;
        }else{
            movablePieces = movablePiecesC;
        }

        //Determine all moves for pieces
        for(int i =0; i<k; i++){
            if(GameBoard[movablePieces[i][0]][movablePieces[i][1]] == 1 || GameBoard[movablePieces[i][0]][movablePieces[i][1]] == 5){
                //Tie Fighters
                if(humanMove){
                    int x = movablePieces[i][0]+1;
                    int y = movablePieces[i][1];
                    //Forward for human Tie's
                    while(x < 7 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 5 || GameBoard[x][y] == 6)){
                        movesH[l][0] = movablePieces[i][1];
                        movesH[l][1] = movablePieces[i][0];
                        movesH[l][2] = y;
                        movesH[l][3] = x;
                        l++;
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x++;
                    }
                    x = movablePieces[i][0]-1;
                    //Back for human Tie's
                    while(x > -1 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 5 || GameBoard[x][y] == 6 || GameBoard[x][y] == 7)){
                        if(GameBoard[x][y] == 5 || GameBoard[x][y] == 6 || GameBoard[x][y] == 7){
                            movesH[l][0] = movablePieces[i][1];
                            movesH[l][1] = movablePieces[i][0];
                            movesH[l][2] = y;
                            movesH[l][3] = x;
                            l++;
                        }
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x--;
                    }
                    //Sideways for human Tie's
                    if(!playerTieJustMovedSideways){
                        x = movablePieces[i][0];
                        y = movablePieces[i][1]+1;
                        //Right
                        while(y < 7 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 5 || GameBoard[x][y] == 6)){
                            movesH[l][0] = movablePieces[i][1];
                            movesH[l][1] = movablePieces[i][0];
                            movesH[l][2] = y;
                            movesH[l][3] = x;
                            l++;
                            if(GameBoard[x][y] != 0){
                                break;
                            }                            
                            y++;
                        }
                        y = movablePieces[i][1]-1;
                        //Left
                        while(y > -1 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 5 || GameBoard[x][y] == 6)){
                            movesH[l][0] = movablePieces[i][1];
                            movesH[l][1] = movablePieces[i][0];
                            movesH[l][2] = y;
                            movesH[l][3] = x;
                            l++;
                            if(GameBoard[x][y] != 0){
                                break;
                            }                            
                            y--;
                        }
                    }
                }else{
                    int x = movablePieces[i][0]-1;
                    int y = movablePieces[i][1];

                    //Forward for Computer Tie's
                    while(x > -1 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 1 || GameBoard[x][y] == 2)){
                        movesC[l][0] = movablePieces[i][1];
                        movesC[l][1] = movablePieces[i][0];
                        movesC[l][2] = y;
                        movesC[l][3] = x;
                        l++;
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x--;
                    }

                    x = movablePieces[i][0]+1;
                    //Back for Computer Tie's
                    while(x < 7 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 1 || GameBoard[x][y] == 2 || GameBoard[x][y] == 3)){
                        if(GameBoard[x][y] == 1 || GameBoard[x][y] == 2 || GameBoard[x][y] == 3){
                            movesC[l][0] = movablePieces[i][1];
                            movesC[l][1] = movablePieces[i][0];
                            movesC[l][2] = y;
                            movesC[l][3] = x;
                            l++;
                        }
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x++;
                    }
                    //Sideways for Computer Tie's
                    if(!computerTieJustMovedSideways) {
                        x = movablePieces[i][0];
                        y = movablePieces[i][1] + 1;
                        //Right
                        while (y < 7 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 1 || GameBoard[x][y] == 2)) {
                            movesC[l][0] = movablePieces[i][1];
                            movesC[l][1] = movablePieces[i][0];
                            movesC[l][2] = y;
                            movesC[l][3] = x;
                            l++;
                            if(GameBoard[x][y] != 0){
                                break;
                            }                            
                            y++;
                        }
                        y = movablePieces[i][1] - 1;
                        //Left
                        while (y > -1 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 1 || GameBoard[x][y] == 2)) {
                            movesC[l][0] = movablePieces[i][1];
                            movesC[l][1] = movablePieces[i][0];
                            movesC[l][2] = y;
                            movesC[l][3] = x;
                            l++;
                            if(GameBoard[x][y] != 0){
                                break;
                            }                            
                            y--;
                        }
                    }
                }
            }else if(GameBoard[movablePieces[i][0]][movablePieces[i][1]] == 2 || GameBoard[movablePieces[i][0]][movablePieces[i][1]] == 6){
                //X Wings
                if(humanMove){

                    //Human Forward (Up) Right X Wing
                    int x = movablePieces[i][0]+1;
                    int y = movablePieces[i][1]+1;
                    while(x < 7 && y < 7 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 5 || GameBoard[x][y] == 6)){
                        movesH[l][0] = movablePieces[i][1];
                        movesH[l][1] = movablePieces[i][0];
                        movesH[l][2] = y;
                        movesH[l][3] = x;
                        l++;
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x++;
                        y++;
                    }

                    //Human Forward (Up) Left X Wing
                    x = movablePieces[i][0]+1;
                    y = movablePieces[i][1]-1;
                    while(x < 7 && y > -1 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 5 || GameBoard[x][y] == 6)){
                        movesH[l][0] = movablePieces[i][1];
                        movesH[l][1] = movablePieces[i][0];
                        movesH[l][2] = y;
                        movesH[l][3] = x;
                        l++;
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x++;
                        y--;
                    }

                    //Human Backward (Down) Right X Wing
                    x = movablePieces[i][0]-1;
                    y = movablePieces[i][1]+1;
                    while(x > -1 && y < 7 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 5 || GameBoard[x][y] == 6 || GameBoard[x][y] == 7)){
                        if(GameBoard[x][y] == 5 || GameBoard[x][y] == 6 || GameBoard[x][y] == 7){
                            movesH[l][0] = movablePieces[i][1];
                            movesH[l][1] = movablePieces[i][0];
                            movesH[l][2] = y;
                            movesH[l][3] = x;
                            l++;
                        }
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x--;
                        y++;
                    }

                    //Human Backward (Down) Left X Wing
                    x = movablePieces[i][0]-1;
                    y = movablePieces[i][1]-1;
                    while(x > -1 && y > -1 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 5 || GameBoard[x][y] == 6 || GameBoard[x][y] == 7)){
                        if(GameBoard[x][y] == 5 || GameBoard[x][y] == 6 || GameBoard[x][y] == 7){
                            movesH[l][0] = movablePieces[i][1];
                            movesH[l][1] = movablePieces[i][0];
                            movesH[l][2] = y;
                            movesH[l][3] = x;
                            l++;
                        }
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x--;
                        y--;
                    }
                }else{
                    //Computer Forward (Down) Right X Wing
                    int x = movablePieces[i][0]-1;
                    int y = movablePieces[i][1]+1;
                    while(x > -1 && y < 7 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 1 || GameBoard[x][y] == 2)){
                        movesC[l][0] = movablePieces[i][1];
                        movesC[l][1] = movablePieces[i][0];
                        movesC[l][2] = y;
                        movesC[l][3] = x;
                        l++;
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x--;
                        y++;
                    }

                    //Computer Forward (Down) Left X Wing
                    x = movablePieces[i][0]-1;
                    y = movablePieces[i][1]-1;
                    while(x > -1 && y > -1 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 1 || GameBoard[x][y] == 2)){
                        movesC[l][0] = movablePieces[i][1];
                        movesC[l][1] = movablePieces[i][0];
                        movesC[l][2] = y;
                        movesC[l][3] = x;
                        l++;
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x--;
                        y--;
                    }

                    //Computer Backward (Up) Right X Wing
                    x = movablePieces[i][0]+1;
                    y = movablePieces[i][1]+1;
                    while(x < 7 && y < 7 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 1 || GameBoard[x][y] == 2 || GameBoard[x][y] == 3)){
                        if(GameBoard[x][y] == 1 || GameBoard[x][y] == 2 || GameBoard[x][y] == 3){
                            movesC[l][0] = movablePieces[i][1];
                            movesC[l][1] = movablePieces[i][0];
                            movesC[l][2] = y;
                            movesC[l][3] = x;
                            l++;
                        }
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x++;
                        y++;
                    }

                    //Computer Backward (Up) Left X Wing
                    x = movablePieces[i][0]+1;
                    y = movablePieces[i][1]-1;
                    while(x < 7 && y > -1 && (GameBoard[x][y] == 0 || GameBoard[x][y] == 1 || GameBoard[x][y] == 2 || GameBoard[x][y] == 3)){
                        if(GameBoard[x][y] == 1 || GameBoard[x][y] == 2 || GameBoard[x][y] == 3){
                            movesC[l][0] = movablePieces[i][1];
                            movesC[l][1] = movablePieces[i][0];
                            movesC[l][2] = y;
                            movesC[l][3] = x;
                            l++;
                        }
                        if(GameBoard[x][y] != 0){
                            break;
                        }
                        x++;
                        y--;
                    }
                }
            }
        }

        return new MoveAndMovablePiecesHolder(movesC, movesH, movablePiecesC, movablePiecesH);
    }

    private boolean isGameOver(boolean humanMove) {
        int[][] moves;
        if(GameBoard[1][3] != 3 || GameBoard[5][3] != 7){
            return true;
        }

        if(humanMove){
            moves = generateMoves(humanMove, PlayerTieJustMovedSideways, ComputerTieJustMovedSideways).movablePiecesH;
            if(moves[0][0] == 0 && moves[0][1] == 0 && moves[0][2] == 0 && moves[0][3] == 0){
                Winner = 2;
                return true;
            }
        }else{
            moves = generateMoves(humanMove, PlayerTieJustMovedSideways, ComputerTieJustMovedSideways).movablePiecesH;
            if(moves[0][0] == 0 && moves[0][1] == 0 && moves[0][2] == 0 && moves[0][3] == 0){
                Winner = 1;
                return true;
            }
        }

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

        makeFinalMove(move, true);
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

    private void makeFinalMove(String move, boolean humanMove) {
        int w = letterAxisToInt(move.charAt(0));
        int x = Character.getNumericValue(move.charAt(1))-1;
        int y = letterAxisToInt(move.charAt(2));
        int z = Character.getNumericValue(move.charAt(3))-1;

        GameBoard[z][y] = GameBoard[x][w];
        GameBoard[x][w] = 0;

        if(humanMove){
            if(GameBoard[z][y] == 1){
                if(z == x && w != y){
                    PlayerTieJustMovedSideways = true;
                }else{
                    PlayerTieJustMovedSideways = false;
                }
            }else{
                PlayerTieJustMovedSideways = false;
            }
        }else {
            if(GameBoard[z][y] == 5){
                if(z == x && w != y){
                    ComputerTieJustMovedSideways = true;
                }else{
                    ComputerTieJustMovedSideways = false;
                }
            }else{
                ComputerTieJustMovedSideways = false;
            }
        }

    }
/*
    private void makeFinalMove(String move, boolean humanTurn){
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

    private char intAxisToLetter(int c) {
        switch (c){
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            default:
                return 'X';
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

class MoveAndScoreHolder{
    int score;
    int[][] moves;

    public MoveAndScoreHolder(int score, int[][] moves) {
        this.score = score;
        this.moves = moves;
    }

    public int getScore() {
        return score;
    }

    public int[][] getMoves() {
        return moves;
    }
}

class MoveAndMovablePiecesHolder{
    int[][] movesC;
    int[][] movesH;
    int[][] movablePiecesC;
    int[][] movablePiecesH;

    public MoveAndMovablePiecesHolder(int[][] movesC, int[][] movesH, int[][] movablePiecesC, int[][] movablePiecesH) {
        this.movesC = movesC;
        this.movesH = movesH;
        this.movablePiecesC = movablePiecesC;
        this.movablePiecesH = movablePiecesH;
    }

    public int[][] getMovesC() {
        return movesC;
    }

    public int[][] getMovesH() {
        return movesH;
    }

    public int[][] getMovablePiecesC() {
        return movablePiecesC;
    }

    public int[][] getMovablePiecesH() {
        return movablePiecesH;
    }
}
