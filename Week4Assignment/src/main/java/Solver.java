import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Solver
{
    private SearchNode goalNode;
    private class SearchNode
    {

        private final int moves;

        private int manhattanSCore;

        private final Board board;

        private final SearchNode previusNode;

        public SearchNode(int moves, Board board, SearchNode previusNode, int manhattanScore)
        {
            this.board = board;
            this.moves = moves;
            this.previusNode = previusNode;
            this.manhattanSCore = manhattanScore;
        }
    }

    private final Board board;

    public Solver(Board initial)
    {
        if (initial == null)
        {
            throw new IllegalArgumentException("Invalid arguments");
        }

        this.board = initial;
        IsSolvable(board.twin());
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
        if (goalNode!=null && goalNode.board.isGoal()){
            return true;
        }
        return false;

    }

    // min number of moves to solve initial board
    public int moves()
    {

        if (goalNode!=null && goalNode.board.isGoal()){
            return goalNode.moves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution()
    {
        SearchNode element = getSolutionNode();

        Stack<Board> returnList= new Stack<>();
        returnList.push(element.board);
        while (element!=null &&element.previusNode!=null){

            element = element.previusNode;
            returnList.push(element.board);
        }

        return new Iterable<Board>() {
            @Override
            public Iterator<Board> iterator()
            {
                return returnList.iterator();
            }
        };
    }

    private SearchNode getSolutionNode() {

        if (!isSolvable()){
            return new SearchNode(0, null, null, -1);
        }

        return goalNode;
    }

    private boolean IsSolvable(Board twinBoard) {
        int count=0;
        ArrayList<SearchNode> manhattanKeys = new ArrayList<>();
        Comparator<SearchNode> cc = new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                int key1 = manhattanKeys.indexOf(o1);

                if (key1 == -1) {
                    o1.manhattanSCore = o1.board.manhattan();
                    manhattanKeys.add(o1);
                    key1 = manhattanKeys.indexOf(o1);
                }

                int key2 = manhattanKeys.indexOf(o2);
                if (key2 == -1) {
                    o2.manhattanSCore = o2.board.manhattan();
                    manhattanKeys.add(o2);
                    key2 = manhattanKeys.indexOf(o2);
                }

                SearchNode o1SearchObject = manhattanKeys.get(key1);
                SearchNode o2SearchObject = manhattanKeys.get(key2);
                if (o1SearchObject.manhattanSCore + o1.moves < o2SearchObject.manhattanSCore + o2.moves) {
                    return -1;
                }
                if (o1SearchObject.manhattanSCore + o1.moves > o2SearchObject.manhattanSCore + o2.moves) {
                    return 1;
                }
                if (o1SearchObject.manhattanSCore < o2SearchObject.manhattanSCore) {
                    return -1;
                }
                if (o1SearchObject.manhattanSCore > o2SearchObject.manhattanSCore) {
                    return 1;
                }

                return 0;
            }
        };

        MinPQ<SearchNode> solution = new MinPQ<SearchNode>(cc);
        MinPQ<SearchNode> twinMinPQ = new MinPQ<SearchNode>(cc);

        SearchNode node = new SearchNode(0, board, null, board.manhattan());
        SearchNode twinNode = new SearchNode(0, twinBoard, null, twinBoard.manhattan());
        SearchNode element = new SearchNode(0, null, null, 0);
        solution.insert(node);
        twinMinPQ.insert(twinNode);
        while (true) {
            if (solution.isEmpty() || twinMinPQ.isEmpty()) {
                break;
            }

            element = solution.delMin();
            twinNode = twinMinPQ.delMin();
            count++;
            if (element.board.isGoal()) {
                goalNode = element;
                break;
            }

            if (twinNode.board.isGoal()) {
                goalNode = null;
                break;
            }

            Iterator<Board> nn = element.board.neighbors().iterator();
            Iterator<Board> twinList = twinNode.board.neighbors().iterator();

            List<Board> neighbours = new ArrayList<>();
            List<Board> twinneighbours = new ArrayList<>();
            while (nn.hasNext() || twinList.hasNext()) {
                if (nn.hasNext()) {
                    Board nextNOde = nn.next();

                    if (element.previusNode != null && element.previusNode.board.equals(nextNOde)) {
                        continue;
                    }
                    if (nextNOde != null) {
                        neighbours.add(nextNOde);
                    }
                }

                if(twinList.hasNext()) {
                    Board twinnext = twinList.next();
                    if (twinNode.previusNode != null && twinNode.previusNode.board.equals(twinnext)) {
                        continue;
                    }
                    if (twinnext != null) {
                        twinneighbours.add(twinnext);
                    }
                }

            }


            if (twinneighbours.size() > 0) {
                for (Board n1 : twinneighbours) {
                    twinMinPQ.insert(new SearchNode(twinNode.moves+1, n1, twinNode, twinNode.manhattanSCore));
                }

            }
            if (neighbours.size() > 0) {
                for (Board n1 : neighbours) {
                    solution.insert(new SearchNode(element.moves+1, n1, element, element.manhattanSCore));

                }

            }
        }
        return goalNode !=null;
    }

    // test client (see below)
    public static void main(String[] args)
    {
        int[][] ints;
        int[][] ints1;




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
                {1,0},
                {2,3},
        };
        ints1 = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}
        };

        ints1 = new int[][]{
                {1, 2, 3},
                {4, 8, 5},
                {7, 0, 6}
        };
        ints = new int[][]{
                {5, 0, 4},
                {2, 3, 8},
                {7,1,6}
        };
        ints1 = new int[][]{
                {1, 6, 4},
                {7, 0, 8},
                {2, 3, 5}
        };

        ints1 = new int[][]{
                {6, 0, 5},
                {8, 7, 4},
                {3, 2, 1}
        };
        ints1 = new int[][]{
                {11, 0, 4, 7},
                {2, 15, 1, 8},
                {5, 14, 9, 3},
                {13, 6, 12, 10 }
        };


        ints1 = new int[][]{
                {14, 13, 5, 3},
                {0, 1, 8, 12},
                {6, 2, 4, 10},
                {11, 9, 15, 7 }
        };

        ints1 = new int[][]{
                {8, 4, 7},
                {1,5,6},
                {3,2,0}
        };


        ints1 = new int[][]{
                {8, 4, 7},
                {1,5,6},
                {3,2,0}
        };


        ints = new int[][]{
                {1, 2, 3},
                {4, 6,5},
                {7, 8,0}
        };
        ints1 = new int[][]{
                {8, 4, 7},
                {1,5,6},
                {3,2,0}
        };
        ints = new int[][]{
                {1,0},
                {2,3},
        };
        System.out.println(System.currentTimeMillis());
        Board board = new Board(ints);
        Solver solution = new Solver(board);

        for (Board el :solution.solution())
        {
            System.out.println(el);
        }
        System.out.println(System.currentTimeMillis());


    }
}
