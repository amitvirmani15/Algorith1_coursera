import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Board
{
    private final int[][] board;
    private int row1;
    private int col1;
    public Board(int[][] tiles) {
        this.board = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
        row1 = StdRandom.uniform(0, board.length);
        col1 = StdRandom.uniform(0, board.length);
        while (true) {
            if (board[row1][col1] != 0) {
                break;
            }
            row1 = StdRandom.uniform(0, board.length);
            col1 = StdRandom.uniform(0, board.length);
        }
        if (board.length==2 && board[row1][(col1+1)%board.length] == 0) {
            row1 = (row1 + 1) % board.length;
        }
    }

    @Override
    public String toString()
    {
        StringBuilder print = new StringBuilder(this.board.length +"\n");
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                print.append(board[i][j]);
                print.append(" ");
            }
            print.append("\n");
        }
        return print.toString();
    }

    // board dimension n
    public int dimension()
    {
        return this.board.length;
    }

    // number of tiles out of place
    public int hamming()
    {
        int hamming = 0;
        int position = 1;
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
               if (board[i][j] != 0 && board[i][j] != position)
               {
                   hamming++;
               }
               position++;
            }
        }

        return hamming;
    }

    public int manhattan()
    {
        int manhattan = 0;
        int position = 1;
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                if (board[i][j] != 0 && board[i][j] != position)
                {
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
    public boolean isGoal()
    {
        if (manhattan() == 0)
        {
            return true;
        }

        return false;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if (!(y instanceof Board))
        {
            return false;
        }

        if (board.length != ((Board) y).dimension())
        {
            return false;
        }
        Board newBoard = (Board)y;
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                if (board[i][j] != newBoard.board[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        int rowPos = 0;
        int colPos = 0;
        boolean found = false;
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board.length; j++)
            {
                if (board[i][j] == 0)
                {
                    rowPos = i;
                    colPos = j;
                    found = true;
                    break;
                }
            }
            if (found)
            {
                break;
            }
        }
        ArrayList<Board> neigbours = new ArrayList<Board>();

        if (rowPos - 1 >= 0)
        {
            neigbours.add(createNeighbour(rowPos, colPos, rowPos - 1, colPos));
        }
        if (rowPos + 1 < board.length)
        {
            neigbours.add(createNeighbour(rowPos, colPos, rowPos + 1, colPos));
        }
        if (colPos + 1 < board.length)
        {
            neigbours.add(createNeighbour(rowPos, colPos, rowPos, colPos + 1));
        }
        if (colPos -1 >= 0)
        {
            neigbours.add(createNeighbour(rowPos, colPos, rowPos, colPos - 1));
        }
        Iterable<Board> iterable = new Iterable<Board>()
        {
            @Override
            public Iterator<Board> iterator() {
                return neigbours.iterator();
            }
        };

        return iterable;

    }

    private Board createNeighbour(int roeToSwap, int colGoSwap, int rowFromSwap, int colFromSwap)
    {
        int[][] newBoard = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);
        int temp = newBoard[roeToSwap][colGoSwap];
        newBoard[roeToSwap][colGoSwap] = newBoard[rowFromSwap][colFromSwap];
        newBoard[rowFromSwap][colFromSwap] = temp;
        return new Board(newBoard);
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int col2 = 0;
        int rowTemp =row1;
        if (col1 == board.length-1){
            if (board[rowTemp][col1-1] != 0){
                col2 = col1-1;
            }
            else if (col1-1>0){
                col2=col1-2;
            }
            else {
                rowTemp = (rowTemp+1)%board.length;
                col2 = rowTemp;
            }
        }
        else if (col1+1== board.length-1 && board[row1][col1+1]==0){
            if (col1-1>=0){
                col2 = col1-1;
            }
            else {
                rowTemp = board.length-1;
                col2 = board.length-1;
            }
        }
        else if (board[row1][col1+1]==0){
            col2 = col1+2;
        }
        else {
            col2= col1+1;
        }
        int[][] newBoard = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);

        int temp = newBoard[row1][col1];
        newBoard[row1][col1] = newBoard[rowTemp][col2];
        newBoard[rowTemp][col2] = temp;
        return new Board(newBoard);

    }

    // unit testing (not graded)
    public static void main(String[] args)
    {

        int[][] ints;




        ints = new int[][]{
                {1, 2, 3},
                {4, 6, 5},
                {7, 8, 0}
        };
        ints = new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };



        ints = new int[][]{
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {10, 11, 12, 13, 14, 15, 16, 17, 18},
                {19, 20, 21, 22, 23, 24, 25, 26, 27},
                {28, 29, 30, 31, 32, 33, 34, 35, 36},
                {37, 38, 39, 40, 41, 42, 43, 44, 45},
                {46, 47, 48, 49, 50, 51, 52, 53, 54},
                {55, 56, 57, 58, 59, 60, 61, 62, 63},
                {64, 65, 66, 67, 68, 69, 70, 0, 71},
                {73, 74, 75, 76, 77, 78, 79, 80, 72}
        };




        ints = new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };
        ints = new int[][]{
                {5, 3, 2},
                {1, 7, 4},
                {6, 8, 0}
        };

        ints = new int[][]{
                {2,3},
                {0,1},
        };
        Board board = new Board(ints);
        Board twin1 = board.twin();
        Board twin2 = board.twin();
        System.out.println(twin1.toString());
        System.out.println(twin1.equals(twin2));

    }
}
