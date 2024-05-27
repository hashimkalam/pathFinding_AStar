// ** NAME - MOHAMMED HASHIM KALAM ** //
// ** STUDENT ID - 20211291 **  //
// ** UOW NUMBER - W1957407 ** //

package datastructure;

import java.util.ArrayList;
import java.util.List;

public class Node {

    // setting up variable for the necessary requirement
    private int row; private int col;  private char type;
    public int fScore; public int gScore;
    List<Node> neighbors; // a list to store the neighboring nodes (help to find the path )

    public Node(char type, int row, int col) {
        this.type = type;
        this.row = row;
        this.col= col;

        // setting both the score as infinity at the start
        this.fScore= Integer.MAX_VALUE;
        this.gScore= Integer.MAX_VALUE;
        this.neighbors= new ArrayList<>();
    }

    // setters for the row - col and the type

    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public void setType(char type) {
        this.type = type;
    }

    // getters for row - col and the type

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public char getType() {
        return type;
    }


    // overriding the formatting the return toString - to properly print the data
    @Override
    public String toString() {
        return "(" + row+ ", " + col +")";
    }
}
