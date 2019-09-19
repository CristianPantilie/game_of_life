import java.util.ArrayList;

public class Cell
{
    private int x;
    private int y;
    private Boolean state;

    Grid g;

    private ArrayList<Cell> neighbours = new ArrayList<>();


    Cell(Grid g, int x, int y, Boolean state){
        this.g = g;
        this.x = x;
        this.y = y;
        this.state = state;
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")" + state;
    }

    void computeNeighbours() {
        for(int i = -1; i <= 1; i++){
            for (int j = -1; j <= 1; j++) {
                if(!(i == 0 && j == 0) ){    //trebuie sa verific si sa nu fie aceleasi numere inca o data
                    neighbours.add(g.getNeighbour(x + i, y + j));
                }
            }
        }
    }

    private int aliveNeighbours()
    {
        int aliveNo = 0;
        for(Cell n: neighbours) {
            if(n.state) {
                aliveNo++;
            }
        }
        return aliveNo;
    }

    public Boolean isAlive(){
        return state;
    }

    public void setState(Boolean state){
        this.state = state;
    }


    Boolean checkRules() {
        int aliveNo = aliveNeighbours();
        if(state) {
////        1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
////        2. Any live cell with two or three live neighbours lives on to the next generation.
////        3. Any live cell with more than three live neighbours dies, as if by overpopulation.
            if(aliveNo < 2 || aliveNo > 3)
                return false;
            else
                return true;
        }
        else {
//          4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
            if(aliveNo == 3)
                return true;
        }
        return false;

    }

}
