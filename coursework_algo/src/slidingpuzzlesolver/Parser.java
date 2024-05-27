// ** NAME - MOHAMMED HASHIM KALAM ** //
// ** STUDENT ID - 20211291 **  //
// ** UOW NUMBER - W1957407 ** //

package slidingpuzzlesolver;

// importing all individually for maintaining standard rules
import datastructure.Graph;
import datastructure.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static Graph parseMap(String filename) throws IOException {
        // creating an instance of graph class
        Graph graph = new Graph();

        // setting up the buffer reader to read the data from the text file
        BufferedReader reader = new BufferedReader(new FileReader(filename));

        // initialising a string to read the lines and setting up the row count to 0 (start position)
        String line;
        int row = 0;

        // setting up an array list to map the nodes
        List<List<Node>> mapNodes = new ArrayList<>();

        while ((line = reader.readLine()) != null) {
            List<Node> rowNodes = new ArrayList<>();
            for (int col = 0; col < line.length(); col++) {
                char symbol = line.charAt(col);
                Node node = new Node(symbol, row, col);
                rowNodes.add(node);
                graph.addNodes(node, new ArrayList<>());

                // getting the neighbors and passing it
                // if symbol is '0' - obstacle - do not consider as neighboring node
                if (symbol != '0') {
                    if (row > 0 && line.charAt(col) != '0') {
                        Node topNeighbor = mapNodes.get(row - 1).get(col); // top neighboring nodes

                        graph.getNeighbors(node).add(topNeighbor);
                        graph.getNeighbors(topNeighbor).add(node);
                    }
                    if (col > 0 && line.charAt(col - 1) != '0') {
                        Node leftNeighbor = rowNodes.get(col - 1); // left neighboring nodes
                        graph.getNeighbors(node).add(leftNeighbor);
                        graph.getNeighbors(leftNeighbor).add(node);
                    }
                }

                    // setting the start row and col to be used later in the path finding algo
                    if (symbol == 'S') {
                        graph.setStartRow(row + 1);
                        graph.setStartCol(col + 1);

                        // setting up the end row and col to be used later in path finding algo
                    } else if (symbol == 'F') {
                        graph.setEndRow(row + 1);
                        graph.setEndCol(col + 1);
                    }
                }
                mapNodes.add(rowNodes);
                row++;
            }

        // print the start - end row and cols just to check whether it read and stored properly
        graph.printInfo(graph.getStartRow(), graph.getStartCol(), graph.getEndRow(), graph.getEndCol());
        System.out.println(graph);

        // returning the graph
        return graph;
    }
}
