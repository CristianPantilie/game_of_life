import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Iterator;

public class Grid {

    private final int columns;
    private final int lines;
    private Cell[][] grid;
    private Cell[][] newGrid;

    Grid(int lines, int columns) {
        this.columns = columns;
        this.lines = lines;
        grid = new Cell[lines][columns];
        newGrid = new Cell[lines][columns];


        for(int i = 0; i < lines; i++) {
            for(int j = 0; j < columns; j++) {
                grid[i][j] = new Cell(this, i, j, false);
                newGrid[i][j] = new Cell(this, i, j, false);
            }
        }

        for(int i = 0; i < lines; i++) {
            for(int j = 0; j < columns; j++) {
                grid[i][j].computeNeighbours();
                newGrid[i][j].computeNeighbours();
            }
        }
    }

    void initializeWithMatrix(Boolean [][] mat)
    {
        if(mat.length != lines && mat[0].length != columns)
            return;

        for(int i = 0; i < mat.length; i++){
            for (int j = 0; j < mat[i].length; j++){
                if(mat[i][j])
                    grid[i][j].setState(true);
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for(int i = 0 ; i < lines; i++) {
            for(int j = 0; j < columns; j++) {
                if (grid[i][j].isAlive())
                    ret.append("*");
                else
                    ret.append(".");
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    void nextGeneration() {
        //iau fiecare celula din gridul ajutator si verific daca respecta regulile
        for(int i = 0; i < lines; i++){
            for(int j = 0; j < columns; j++){
                newGrid[i][j].setState(grid[i][j].checkRules());
            }
        }
        //copiez gridul ajutator in gridul principal
        for(int i = 0; i < lines; i++){
            for (int j = 0; j < columns; j++){
                grid[i][j].setState(newGrid[i][j].isAlive());
            }
        }
    }

    Cell getNeighbour(int x, int y)
    {
//        if(x < 0)
//            x = columns - 1;
//        else if(x > columns - 1)
//            x = x % columns;
//
//        if(y < 0)
//            y  = lines - 1;
//        else if(y > lines - 1)
//            y = y % lines;

        x = (x + lines) % lines;
        y = (y + columns) % columns;

        return grid[x][y];
    }

    Boolean[][] getCurrentGrid()
    {
        Boolean currentGrid[][] = new Boolean[lines][columns];

        for(int i = 0; i < lines; i++){
            for (int j = 0; j < columns; j++){
                currentGrid[i][j] = grid[i][j].isAlive();
            }
        }
        return currentGrid;
    }


    void run(int ticks)
    {
        for (int i = 0; i < ticks; i++)
        {
            System.out.println(this);
            nextGeneration();
        }
    }

    Boolean[][] run()
    {
        nextGeneration();
        return getCurrentGrid();
    }
}





















