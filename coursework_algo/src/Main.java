// ** NAME - MOHAMMED HASHIM KALAM ** //
// ** STUDENT ID - 20211291 **  //
// ** UOW NUMBER - W1957407 ** //

import datastructure.Graph;
import datastructure.Node;
import slidingpuzzlesolver.Parser;
import slidingpuzzlesolver.AStarAlgo;

// importing all individually for maintaining standard rules
import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean validInput= false;

        while (!validInput) {
            System.out.print("Enter the file name to be read: ");
            String filename = scanner.nextLine();

            try {
                File file = new File(filename + ".txt");
                if(file.exists()) {
                    validInput= true;

                    // parser to read through the text files and store the grid in a hashmap
                    Graph graph = Parser.parseMap(filename + ".txt");

                    // System.out.println("Here is the one from the main -> \n" + graph);

                    // System.out.println("neighbor -> " + graph.getNeighbors(graph.getStartingNode()));
                    // System.out.println("adjacency list -> " + graph.adjacencyList);

                    // System.out.println("Obstacle count -> "+ graph.obstacleCount());
                    // System.out.println("Path count -> "+ graph.pathCount());

                    // start time for determining the time complexity
                    long startTime= System.currentTimeMillis();

                    // pass the file read data to the pathfinder method
                    List<Node> shortestPathFound = AStarAlgo.findShortestPath(graph);

                    // System.out.println("Starting Node of S => " + graph.getStartingNode());
                    // System.out.println("Ending Node of F => " + graph.getEndingNode());
                    System.out.println(shortestPathFound);

                    // printing the shortest path found -> the steps
                    AStarAlgo.printPathSteps(shortestPathFound, graph);

                    // ending the time
                    long endTime= System.currentTimeMillis();

                    // getting the difference to check how long it took to execute (read & find the path and print the steps)
                    long timeDiff= endTime-startTime;

                    // printing the time taken
                    System.out.println("Time took => " + timeDiff);

                } else System.out.println("File do not exist! Try Again!\n"); // if invalid path - then print error
            } catch (Exception e) {

                // catch any exception and return only the exact error by using the .getMessage - best practices
                System.out.println("Error: " + e.getMessage());
            }

        }
        // maze10_5 maze15_1 maze25_1 puzzle_10
    }
}