import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Iterator;

public class Solver {

    private class searchnode implements Comparable<searchnode>{
        private final int moves ;

        private final Board board;

        private final searchnode previusNode;

        public searchnode(int moves, Board board, searchnode previusNode) {
            this.board = board;
            this.moves = moves;
            this.previusNode = previusNode;
        }

        @Override
        public int compareTo(searchnode board) {
            if(this.board.manhattan()< board.board.manhattan()){
                return -1;
            }
            if (this.board.manhattan() > board.board.manhattan()){
                return 1;
            }
            return 0;
        }
    }

    private final Board board;
    private MinPQ<searchnode> solution;
    public Solver(Board initial){
        if (initial == null){
            throw new IllegalArgumentException("Invalid arguments");
        }
        solution = new MinPQ<searchnode>();
        this.board = initial;
        searchnode node = new searchnode(1, board, null);
        solution.insert(node);

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return  (board.manhattan()+board.hamming())%2==0;
    }

    // min number of moves to solve initial board
    public int moves(){
        return board.manhattan();
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
        ArrayList<Board> list = new ArrayList<Board>();

        if (!isSolvable()) {
            list.add(solution.min().board);
            return list;
        }

        while (true) {
            searchnode element = solution.delMin();
            list.add(element.board);
            if (element.board.isGoal()){
                break;
            }
            Iterator<Board> nn = element.board.neighbors().iterator();
            while (nn.hasNext()){
                Board nextNode = nn.next();
                searchnode previous = element.previusNode;
                boolean skip = false;
                while (previous!=null){
                    if (previous.board == nextNode){
                        skip = true;
                        break;
                    }
                    previous = previous.previusNode;
                }
                if (!skip)
                {
                    solution.insert(new searchnode(element.moves+1, nextNode, element));
                }

            }

        }
return new Iterable<Board>() {
    @Override
    public Iterator<Board> iterator() {
        return list.iterator();
    }
};
    }

    // test client (see below)
    public static void main(String[] args){
        int[][] ints = new int[3][3];
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
        Board board = new Board(ints);
        Solver solution = new Solver(board);
        for(Board el :solution.solution())
        {
            System.out.println(el);
        }

    }
}
