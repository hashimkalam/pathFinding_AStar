// ** NAME - MOHAMMED HASHIM KALAM ** //
// ** STUDENT ID - 20211291 **  //
// ** UOW NUMBER - W1957407 ** //

package slidingpuzzlesolver;

import datastructure.Graph;
import datastructure.Node;

// importing all individually for maintaining standard rules
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

public class AStarAlgo {

    // setting all the static variables to final since it would not change in the latter process of the algo
    private static final int[] rowDirection = {-1, 1, 0, 0}; // Up, down, left, right for row
    private static final int[] colDirection = {0, 0, -1, 1}; // Up, down, left, right for column

    private static final int slidingCost = 1; // Cost for sliding
    private static final int directionMoveCost = 1; // Cost for moving to adjacent cell

    public static List<Node> findShortestPath(Graph graph) {
        Node startingNode = graph.getStartingNode(); // setting up the startingNode
        Node endingNode = graph.getEndingNode();  // setting up the endingNode

        List<Node> path = new ArrayList<>(); // setting up an arrayList for the path
        path.add(startingNode); // adding the start node 'S' to the path to start the search from 'S'

        // setting up a priority queue with the fScore
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.fScore));
        openSet.add(startingNode); // adding the startingNode to the pq openset -> so that it starts from the right node

        System.out.println("open set ->" + openSet );

        // setting up the necessary map
        Map<Node, Integer> fScore = new HashMap<>();
        Map<Node, Integer> gScore = new HashMap<>();

        // setting up a map for cameFrom to store all the nodes visited
        Map<Node, Node> cameFrom = new HashMap<>();

        // setting up a set for visitedNodes to store all the nodes visited
        Set<Node> visitedNodes = new HashSet<>();

        // passing the starting node to both the maps - gScore and (fScore (along with heuristic cost esti function))
        gScore.put(startingNode, 0);
        fScore.put(startingNode, heuristicCostEstimate(startingNode, endingNode));

        // printing statements to help me debug
        System.out.println("Total no of nodes excluding 'S' and 'F' -> " + (graph.pathCount() + graph.obstacleCount()));
        System.out.println("Start Node: " + startingNode);
        System.out.println("End Node: " + endingNode);
        System.out.println("Heuristic cost: " + heuristicCostEstimate(startingNode, endingNode));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

       //     System.out.println("Exploring node: " + current);

            if (current.equals(endingNode)) {
                //openSet.add(graph.getStartingNode());
                openSet.remove(current);
                return pathReconstructed(cameFrom, current);
            } else {
                for (int i = 0; i < 4; i++) {
                    int newRow = current.getRow();
                    int newCol = current.getCol();
                    int steps = 0;

                    // System.out.println("Is valid move -> " + isValidMove(graph, newRow + DR[i], newCol + DC[i]));
                    // passin the graph along with all possible row and col node jump to check if valid move
                    while (isValidMove(graph, newRow + rowDirection[i], newCol + colDirection[i])) {
                        // if yes -> increment the row and col by one
                        newRow += rowDirection[i];
                        newCol += colDirection[i];
                        steps++; // increment the steps by one
                    }

                    // basically if movement cost is greater than 1 which means its sliding so now multiple
                    // number of steps by slidingCost or just pass the directionMoveCost
                    int movementCost = (steps > 1) ? slidingCost * steps : directionMoveCost;

                    Node neighbor = graph.getNode(newRow, newCol);
                    int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + movementCost;

                    // System.out.println("Neighbor: " + neighbor);
                    // System.out.println("Tentative G Score: " + tentativeGScore);

                    // if tentativeGScore is less than the current gScore (neighboring nodes) which means a better path
                    if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        // System.out.println("Updating scores for neighbor: " + neighbor);

                        // updating the scores (maps)
                        cameFrom.put(neighbor, current);
                        gScore.put(neighbor, tentativeGScore);
                        fScore.put(neighbor, tentativeGScore + heuristicCostEstimate(neighbor, endingNode));

                        // a check whether the neighbor is already visited -> ensure shortest path
                        if (!visitedNodes.contains(neighbor)) {
                            // System.out.println("Adding neighbor to open set: " + neighbor);

                            // if no -> add to both the maps
                            visitedNodes.add(neighbor);
                            openSet.add(neighbor);
                        }
                    }
                }
            }
        }
        return Collections.emptyList(); // returning an empty collection resembling no path found
    }


    // method to calculate heuristic cost using manhatten distance (most common way to find in an A* algo)
    private static int heuristicCostEstimate(Node startingNode, Node endingNode) {

        // getting the difference in the rows
        int diffInRow = Math.abs(startingNode.getRow() - endingNode.getRow());

        // getting the difference in the cols
        int diffInCol= Math.abs(startingNode.getCol() - endingNode.getCol());

        // returning the sum of the diff - manhaten distance
        return diffInRow + diffInCol;
    }


    // method ot reconstruct the path - by getting the cameFrom map and the current Node as arguments
    private static List<Node> pathReconstructed(Map<Node, Node> cameFrom, Node current) {
        List<Node> path = new ArrayList<>(); // initialising an arraylist for the re-constructed path

        //path.add(0, graph.getStartingNode());
        //path.remove(graph.getEndingNode());

        // checkin while the current node is  in the cameFrom map -> add it to the path arraylist
        while (cameFrom.containsKey(current)) {
            path.add(current);
            current = cameFrom.get(current);
        }

        // reversing the path - to get from the start to end nodes
        Collections.reverse(path);

        // returning the path
        return path;
    }

    // method to check whether the next move is valid
    private static boolean isValidMove(Graph graph, int row, int col) {
        if (row < 0 || row > graph.maxRow() || col < 0 || col > graph.maxCol()) {
            return false; // out of bounds so return false
        } else if (graph.getNode(row, col).getType() == '0') return false; // an obstacle so return false again

        return true; // else it is a valid move so return true
    }


    // method to print the path
    public static void printPathSteps(List<Node> path, Graph graph) {
        // path.add(0, graph.getStartingNode());
        path.remove(graph.getEndingNode());

        if (path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        // setting up a grid to create the grid with the stars added - easy visualizing the path
        char[][] grid = graph.createGridFromAdjacencyList();

        System.out.println("Path represented by stars -*-");
        for (Node node : path) {
            grid[node.getRow()][node.getCol()] = '*'; // Mark the path with '*'
        }

        for (char[] row : grid) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }

        path.add(0, graph.getStartingNode());
        System.out.println(path);

        System.out.println("\nStep taken to reach the path: ");

        for (int i = 0; i < path.size(); i++) {
            Node currentNode = path.get(i);
            if (i == 0) {
                System.out.println("\n"+(i + 1) + ". Start at " + graph.getStartingNode());

            } else {
                Node previousNode = path.get(i-1);
                String direction = getDirection(previousNode, currentNode);

                System.out.println((i + 1) + ". Move " + direction + " to " + currentNode);
                if (i == path.size() - 1) {
                    System.out.println((i + 2) + ". Done!");
                }
            }
        }
    }

    // method to check the direction by getting the current node and the next node as arguments
    private static String getDirection(Node prevNode, Node currentNode) {

        // getting the diff in the column of the current to the next node
        int diffInCol = currentNode.getCol() - prevNode.getCol();

        // getting the diff in the row of the current to the next node
        int diffInRow = currentNode.getRow() - prevNode.getRow();

        if (diffInCol > 0) { // if column diff is more than 0 -> means to the right
            return "right";
        } else if (diffInCol < 0) { // if column diff is less than 0 -> means to the left
            return "left";
        } else if (diffInRow > 0) { // if row diff is more than 0 -> means down
            return "down";
        } else return "up"; // else means ups

    }
}
