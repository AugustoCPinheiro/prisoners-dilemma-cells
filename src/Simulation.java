import java.util.Random;

public class Simulation {
    static Cell[][] organism;
    static int[][] payoff = {{3, 0}, {5, 1}};
    static boolean isSaturated = false;
    static int lines = 100;
    static int columns = 10;

    static int generation = 0;
    static int n = lines * columns;
    static int i = 0;
    static CellsView cellsView;
    static GraphicView graphicView;
    static double w = 0.5;

    static double fitnessH;
    static double fitnessC;

    static double payoffH;
    static double payoffC;

    public static void main(String[] args) {
        organism = new Cell[lines][columns];
        createOrganism();

        cellsView = new CellsView(organism);
        graphicView = new GraphicView();

        while (!isSaturated) {
            query();
            calculateFitness();
            killCell();
            teste();

            cellsView.updateLabels(fitnessC, fitnessH, generation, i, n - i);
            graphicView.addPoint(generation, i);

            if (i == 1000)
                isSaturated = true;

            i = 0;
            generation++;
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {

            }
        }
    }

    public static void query() {
        for (int j = 0; j < lines; j++) {

            for (int q = 0; q < columns; q++) {
                if (organism[j][q].cellType == Cell.HEALTHY_CELL) {
                    isSaturated = false;
                }
                if (organism[j][q].cellType == Cell.CANCEROUS_CELL) {
                    i++;
                }
            }
        }
        cellsView.getCanvas().repaint();
    }

    public static void teste(){
        double x = (i*fitnessC)/(i*fitnessC + (n-i)*fitnessH);
        double y = (n-1)/(double)n;
        System.out.println("Pi,i+1 = "+ x + " + " + y);
//System.out.println(fitnessH);
        double z = ((n-i)*fitnessH)/(i*fitnessC + (n-i)*fitnessH);
        double w = i/(double)n;
        System.out.println("Pi,i-1 = " + z/w + " + " + w);
        System.out.println("Pi,i = " + (1 - (x - z)));
    }

    public static void createOrganism() {
        for (int j = 0; j < lines; j++) {
            for (int q = 0; q < columns; q++) {
                organism[j][q] = new Cell(Cell.HEALTHY_CELL);
            }
        }
    }

    public static void killCell() {
        int j = new Random().nextInt(lines);
        int q = new Random().nextInt(columns);
        double deathH = (n-1)/(double)n;
        double deathC = i/(double)n;

        organism[j][q] = null;

        reproductCell(j, q);
    }

    public static double calculatePayoffH() {
        payoffH = (payoff[0][0] * (n - i - 1) + payoff[0][1] * i) / (n - 1);
        return payoffH;
    }

    public static double calculatePayoffC() {

        payoffC = (payoff[1][0] * (n - i) + payoff[1][1] * (i - 1)) / (n - 1);
        return payoffC;
    }

    public static void reproductCell(int j, int q) {

        Cell newCell;
        int column;
        int line;

        double birthC = (i*fitnessC)/(i*fitnessC + (n-i)*fitnessH);

        do {
            line = new Random().nextInt(lines);
            column = new Random().nextInt(columns);
            newCell = organism[line][column];
        } while (line == j && column == q);

        if (new Random().nextInt(100) < 10)
            newCell.mutate();


        organism[j][q] = newCell;
    }



    public static void calculateFitness() {
        fitnessH = 0;
        fitnessC = 0;
        fitnessH = 1 - w + w * calculatePayoffH();
        fitnessC = 1 - w + w * calculatePayoffC();

        Cell.fitnessC = fitnessC;
        Cell.fitnessH = fitnessH;
    }




}
