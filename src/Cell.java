import java.util.Random;

public class Cell {

    static final int HEALTHY_CELL = 0;
    static final int CANCEROUS_CELL = 1;

    boolean[] genes;
    int cellType;
    double fitness;

    public Cell(int cellType){
        genes = new boolean[]{false, false, false, false};
        this.cellType = cellType;
    }

    public void mutate(){
        int gene = new Random().nextInt(4);
        if (cellType == HEALTHY_CELL) {
            genes[gene] = !genes[gene];
        }

        if (genes[0] && genes[1]){
            cellType = CANCEROUS_CELL;
        }else if(genes[0] && genes[2] && genes[3]){
            cellType = CANCEROUS_CELL;
        }

        }
}
