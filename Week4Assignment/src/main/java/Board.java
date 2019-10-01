import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Board implements Comparable<Board>{

    private final int[][] board;
    public Board(int[][] tiles){
        this.board = tiles.clone();
    }

    public String toString(){
        String print = this.board.length +"\n";
        for (int i =0; i< board.length; i++){
            for (int j =0; j<board.length; j++){
                print = print+board[i][j]+ " ";
            }
            print = print+"\n";
        }

        return print;
    }

    // board dimension n
    public int dimension(){
        return this.board.length;
    }

    // number of tiles out of place
    public int hamming(){
        int hamming = 0;
        int position = 1;
        for (int i =0; i< board.length; i++){
            for (int j =0; j<board.length; j++){
               if (board[i][j] !=0 && board[i][j] != position){
                   hamming++;
               }
               position++;
            }
        }

        return hamming;
    }

    public int manhattan(){
        int manhattan = 0;
        int position = 1;
        for (int i =0; i< board.length; i++){
            for (int j =0; j<board.length; j++){
                if (board[i][j] !=0 && board[i][j] != position){
                    int boardValue = board[i][j];
                    int rowactual = (boardValue-1)/board.length;
                    int colActual = boardValue - 1 - rowactual*board.length;
                    manhattan = manhattan + Math.abs(rowactual -i)+Math.abs(colActual -j);
                }
                position++;
            }
        }

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal(){
        if (manhattan() == 0){
            return true;
        }

        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (!(y instanceof Board)) {
            return false;
        }

        if (board.length != ((Board) y).dimension()) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != ((Board) y).board[i][j]) {
                    return false;
                }
            }
        }

        return !y.equals(this);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int rowPos = 0;
        int colPos = 0;
        boolean found = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    rowPos = i;
                    colPos = j;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }
        ArrayList<Board> neigbours = new ArrayList<Board>();

        if (rowPos - 1 >= 0) {
            neigbours.add(CreateNeighbour(rowPos, colPos, rowPos - 1, colPos));
        }
        if (rowPos + 1 < board.length) {
            neigbours.add(CreateNeighbour(rowPos, colPos, rowPos + 1, colPos));
        }
        if (colPos + 1 < board.length) {
            neigbours.add(CreateNeighbour(rowPos, colPos, rowPos, colPos + 1));
        }
        if (colPos -1 >= 0) {
            neigbours.add(CreateNeighbour(rowPos, colPos, rowPos, colPos - 1));
        }
        Iterable<Board> iterable = new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator() {
                return neigbours.iterator();
            }
        };

        return iterable;

    }



    private int FindIndex(int i, int j){
        return i*board.length+j;
    }

    private Board CreateNeighbour(int roeToSwap, int colGoSwap, int rowFromSwap, int colFromSwap){
        int[][] newBoard = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);
        int temp = newBoard[roeToSwap][colGoSwap];
        newBoard[roeToSwap][colGoSwap] = newBoard[rowFromSwap][colFromSwap];
        newBoard[rowFromSwap][colFromSwap] = temp;
        return new Board(newBoard);
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int row1 = StdRandom.uniform(board.length);
        int col1 = StdRandom.uniform(board.length);
        int row2 = StdRandom.uniform(board.length);
        int col2 = StdRandom.uniform(board.length);
        while (true){
            if(board[row1][col1] == 0)
            {
                row1 = StdRandom.uniform(board.length);
                col1 = StdRandom.uniform(board.length);
            }
            if(board[row2][col2] == 0 || (row1 == row2 && col1 == col2))
            {
                row2 = StdRandom.uniform(board.length);
                col2 = StdRandom.uniform(board.length);
            }
            else {
                break;
            }
        }

        int[][] newBoard = board.clone();
        int temp = newBoard[row1][col1];
        newBoard[row1][col1] = newBoard[row2][col2];
        newBoard[row2][col2] = temp;
        return new Board(newBoard);

    }

    // unit testing (not graded)
    public static void main(String[] args){

        int[][] ints = new int[3][3];
        ints = new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };

        Board board = new Board(ints);
        System.out.println(board.manhattan());
        System.out.println(board.neighbors());
        System.out.println(board.dimension());
        System.out.println(board.hamming());
    }

    private int noOfNeighbours(int x, int y){
        if ((x ==0 && y ==0) || (x == 0 && y== board.length) || (x == board.length && y ==0) ||( x ==board.length && y == board.length) ){
            return 2;
        }
        if(x ==0 || y ==0 || y == board.length || x == board.length){
            return 3;
        }
        else {
            return 4;
        }
    }

    @Override
    public int compareTo(Board board) {
        return 0;
    }
}
