// ** NAME - MOHAMMED HASHIM KALAM ** //
// ** STUDENT ID - 20211291 **  //
// ** UOW NUMBER - W1957407 ** //

package datastructure;

// importing all individually for maintaining standard rules
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {

    public Map<Node, List<Node>> adjacencyList; // setting up a map for the adjacent list nodes

    // setting up the necessary variable to store crucial info abt rows and cols
    private int startRow; private int endRow; private int startCol; private int endCol;


    // constructor for the adjacency list
    public Graph() {
        this.adjacencyList = new HashMap<>();
    }


    // setters for start row/col & end row/col
    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }
    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }
    public void setStartCol(int startCol) {
        this.startCol = startCol;
    }

    public void setEndCol(int endCol) {
        this.endCol = endCol;
    }

    // getters for start row/col & end row/col
    public int getStartRow() {
        return startRow;
    }
    public int getEndRow() {
        return endRow;
    }
    public int getStartCol() {
        return startCol;
    }
    public int getEndCol() {
        return endCol;
    }



    // method to add nodes -> create the graph structure
    public void addNodes(Node node, List<Node> neighbors) {
        adjacencyList.put(node, neighbors);
    }


    // return the neighboring nodes
    public List<Node> getNeighbors(Node node) {
        return adjacencyList.get(node);
    }


    // method to print the row and col of start-end positions - to make it more clear for me
    public void printInfo(int startRow, int startCol, int endRow, int endCol) {
        System.out.println("\nStart Position row => " + startRow + " and column => " + startCol);
        System.out.println("End Position row => " + endRow + " and column => " + endCol);
    }


     // method to get all the nodes from the list
     public Node[] getAllNodes() {
        Node[] arrayForNodes = new Node[adjacencyList.size()];

        int index=0;
        for (Node node : adjacencyList.keySet()) {
            arrayForNodes[index] = node;
            index++;
        }
        return arrayForNodes;
    }

    // method to ge the starting node - row and col
    public Node getStartingNode() {
        Node startNode= null;
        for (Node node: getAllNodes()) {
            if (node.getType() == 'S') {
                startNode=node;
                break;
            }
        }
        return startNode;
    }

    // to check whether am I reading and storing the obstacles properly
    public int obstacleCount() {
        int count=0;
        for (Node node: getAllNodes()) {
            if(node.getType()== '0') {
                count++;
            }
        }

        return count;
    }

    // to check whether am I reading and storing the available paths properly
    public int pathCount() {
        int count=0;
        for (Node node: getAllNodes()) {
            if(node.getType()== '.') {
                count++;
            }
        }
        return count;
    }

    // method to get the ending node - row and col
    public Node getEndingNode() {
        Node endingNode=null;

        for (Node node: getAllNodes()) {
            if (node.getType()== 'F') {
                endingNode= node;
                break;
            }
        }
        return endingNode;
    }

    // Get a node by passing the row and col as arguments
    public Node getNode(int row, int col) {
        for (Node node : adjacencyList.keySet()) {
            if (node.getRow() == row && node.getCol() == col) {
                return node;
            }
        }
        return null;
    }

    // method to get the max row count
    public int maxRow() {
        int maxRow = 0;

        for (Node node : adjacencyList.keySet()) {
            maxRow = Math.max(maxRow, node.getRow());
        }

        return maxRow;
    }

    // method to get the max col count of the grid
    public int maxCol() {
        int maxCol = 0;

        for (Node node : adjacencyList.keySet()) {
            maxCol = Math.max(maxCol, node.getCol());
        }
        return maxCol;
    }

    // Helper method to create a grid from the adjacency list
    public char[][] createGridFromAdjacencyList() {
        // Determine grid dimensions
        int maxRow = 0;
        int maxCol = 0;

        for (Node node : adjacencyList.keySet()) {
            maxRow = Math.max(maxRow, node.getRow());
            maxCol = Math.max(maxCol, node.getCol());
        }
        System.out.println("Max Row -> " + maxRow);
        System.out.println("Max Col -> " + maxCol);

        // Initialize grid with empty cells
        char[][] grid = new char[maxRow + 1][maxCol + 1];
        for (int i = 0; i <= maxRow; i++) {
            for (int j = 0; j <= maxCol; j++) {
                grid[i][j] = '.';
            }
        }

        // Fill grid with nodes
        for (Node node : adjacencyList.keySet()) {
            grid[node.getRow()][node.getCol()] = node.getType();
        }

        return grid;
    }


    // to get the formatting I need just to inspect whether it read properly
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        char[][] grid = createGridFromAdjacencyList();

        // Print the grid
        for (char[] chars : grid) {
            for (int j = 0; j < chars.length; j++) {
                sb.append(chars[j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
