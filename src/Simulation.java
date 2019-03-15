import java.io.*;
import java.util.Calendar;
import java.util.Random;

public class Simulation {
    static Cell[][] organism;
    static int[][] payoff = {{3, 0}, {5, 1}};
    static boolean isSaturated = false;
    static int lines = 100;
    static int columns = 10;

    static Long currentOffset = 0L;
    static int n = lines*columns;
    static int i = 0;

    public static void main(String[] args) {
        organism = new Cell[lines][columns];
        createOrganism();


        while (!isSaturated) {
            query();
            killCell();
            mutateCell();

            System.out.println("Cancerigenas: " + i);
            System.out.println("Saudaveis: " + (n-i));
            i = 0;
        }
    }

    public static void query() {
        for (int j = 0; j < lines; j++) {

            for (int q = 0; q < columns; q++) {
                calculatePayoff(j, q);
                if (organism[j][q].cellType == Cell.HEALTHY_CELL) {
                    isSaturated = false;
                }
                if(organism[j][q].cellType == Cell.CANCEROUS_CELL){
                    i++;
                }
            }
        }
    }

    public static void createOrganism() {

        for (int j = 0; j < lines; j++) {
            for (int q = 0; q < columns; q++) {
                organism[j][q] = new Cell(Cell.HEALTHY_CELL);
            }
        }
        organism[new Random().nextInt(lines)][new Random().nextInt(columns)] = new Cell(Cell.CANCEROUS_CELL);
        i++;
    }

    public static void killCell() {
        int j = new Random().nextInt(lines);
        int q = new Random().nextInt(columns);

        organism[j][q] = null;

        reproductCell(j, q);
    }

    static void mutateCell() {
        int j = new Random().nextInt(lines);
        int q = new Random().nextInt(columns);

        organism[j][q].mutate();

    }

    public static void reproductCell(int j, int q) {

        Cell newCell;
        int column;
        int line;

        do {
            line = new Random().nextInt(lines);
            column = new Random().nextInt(columns);
            newCell = organism[line][column];
        } while (line == j && column == q);

        organism[j][q] = newCell;
    }

    static int calculatePayoff(int j, int q) {
        Cell currentCell = organism[j][q];
        int totalPayoff = 0;

        if (j - 1 > 0) {
            totalPayoff += payoff[currentCell.cellType][organism[j - 1][q].cellType];
        }

        if (q - 1 > 0) {
            totalPayoff += payoff[currentCell.cellType][organism[j][q - 1].cellType];
        }

        if (j - 1 > 0 && q - 1 > 0) {
            totalPayoff += payoff[currentCell.cellType][organism[j - 1][q - 1].cellType];
        }
        if (j + 1 < lines) {
            totalPayoff += payoff[currentCell.cellType][organism[j + 1][q].cellType];
        }

        if (q + 1 < columns) {
            totalPayoff += payoff[currentCell.cellType][organism[j][q + 1].cellType];
        }

        if (j + 1 < lines && q + 1 < columns) {
            totalPayoff += payoff[currentCell.cellType][organism[j + 1][q + 1].cellType];
        }

        if (j + 1 < lines && q - 1 > 0) {
            totalPayoff += payoff[currentCell.cellType][organism[j + 1][q - 1].cellType];
        }

        if (j - 1 > 0 && q + 1 < columns) {
            totalPayoff += payoff[currentCell.cellType][organism[j - 1][q + 1].cellType];
        }
        return totalPayoff;
    }



}
