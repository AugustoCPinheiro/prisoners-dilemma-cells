import java.util.HashMap;
import java.util.Random;

public class Simulation {
    static Cell[][] organism;
    static int[][] payoff = {{3, 0}, {5, 1}};
    static boolean isSaturated = false;
    static int lines = 100;
    static int columns = 10;

    static final String PLUS_EVENT = "plus";
    static final String SAME_EVENT = "same";
    static final String LESS_EVENT = "less";


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
    static Distribution<String> deathDistribution;
    static Distribution<String> birthDistribution;
    static HashMap<String, Double> deathMap = new HashMap<>();
    static HashMap<String, Double> birthMap = new HashMap<>();

    public static void main(String[] args) {
        organism = new Cell[lines][columns];
        createOrganism();
        deathDistribution = new Distribution<>(deathMap);
        birthDistribution = new Distribution<>(birthMap);
        cellsView = new CellsView(organism);
        graphicView = new GraphicView();

        while (!isSaturated) {
            query();
            calculateFitness();
            computeProbabilities();
            doSelection();

            cellsView.updateLabels(fitnessC, fitnessH, generation, i, n - i);
            graphicView.addPoint(generation, i);

//            if (i == 1000)
//                isSaturated = true;

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
    static void doSelection(){
//        String event = distribution.sample();
//
//        switch (event){
//            case PLUS_EVENT:
//                killCell(Cell.HEALTHY_CELL);
//                reproductCell(Cell.CANCEROUS_CELL);
//                break;
//            case LESS_EVENT:
//                killCell(Cell.CANCEROUS_CELL);
//                reproductCell(Cell.HEALTHY_CELL);
//                break;
//            case SAME_EVENT:
//                if (i>0) {
//                    killCell(Cell.CANCEROUS_CELL);
//                    reproductCell(Cell.CANCEROUS_CELL);
//                }else {
//                    killCell(Cell.HEALTHY_CELL);
//                    reproductCell(Cell.HEALTHY_CELL);
//                }
//                break;
//        }
        String birthEvent = birthDistribution.sample();
        String deathEvent = deathDistribution.sample();
        switch (deathEvent){
            case "C":
                killCell(Cell.CANCEROUS_CELL);
                break;
            case "H":
                killCell(Cell.HEALTHY_CELL);
                break;
        }
        switch (birthEvent){
            case "C":
                reproductCell(Cell.CANCEROUS_CELL);
                break;
            case "H":
                reproductCell(Cell.HEALTHY_CELL);
                break;
        }
    }
    public static void teste(){
        double x = (i*fitnessC)/(i*fitnessC + (n-i)*fitnessH);
        double y = (n-1)/(double)n;
        System.out.println("Pi,i+1 = "+ x + " + " + y);
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

    public static double calculateBirth(int type){
        if (type == Cell.CANCEROUS_CELL){
            return (i*fitnessC)/(i*fitnessC + (n-i)*fitnessH);
        }
        return ((n-i)*fitnessH)/(fitnessC+(n-i)*fitnessH);
    }

    public static double calculateDeath(int type){
        if (type == Cell.CANCEROUS_CELL){
            return i/(double)n;
        }
        return (n-i)/(double)n;
    }

    static void computeProbabilities(){
        double deathH = calculateDeath(Cell.HEALTHY_CELL);
        double deathC = calculateDeath(Cell.CANCEROUS_CELL);
        double birthC = calculateBirth(Cell.CANCEROUS_CELL);
        double birthH = calculateBirth(Cell.HEALTHY_CELL);

//        System.out.println("deathH: " + deathH);
//        System.out.println("deathC: " + deathC);
//        System.out.println("birthC: " + birthC);
//        System.out.println("birthH: " + birthH);


//        map.put(PLUS_EVENT, deathH * birthC);
//        map.put(SAME_EVENT, 1 - (deathH*birthC) - (deathC*birthH));
//        map.put(LESS_EVENT, deathC * birthH);

        deathMap.put("C", deathC);
        deathMap.put("H", deathH);

        birthMap.put("C", birthC);
        birthMap.put("H", birthH);

        birthDistribution.updateDistribution(birthMap);
        deathDistribution.updateDistribution(deathMap);
    }

    public static void killCell(int type) {
        for (int j = 0; j < lines; j++) {

            for (int q = 0; q < columns; q++) {
               if (organism[j][q].cellType == type){
                   organism[j][q] = null;
                   return;
               }
            }
        }

    }

    public static double calculatePayoffH() {
        payoffH = (payoff[0][0] * (n - i - 1) + payoff[0][1] * i) / (n - 1);
        return payoffH;
    }

    public static double calculatePayoffC() {

        payoffC = (payoff[1][0] * (n - i) + payoff[1][1] * (i - 1)) / (n - 1);
        return payoffC;
    }

    public static void reproductCell(int type) {
        Cell cell = null;
        for (int j = 0; j < lines; j++) {

            for (int q = 0; q < columns; q++) {
                if (organism[j][q] != null) {
                    if (organism[j][q].cellType == type) {
                        cell = organism[j][q];
                        break;
                    }
                }
            }
        }

        if (cell == null){
            cell = new Cell(Cell.CANCEROUS_CELL);
        }
        for (int j = 0; j < lines; j++) {

            for (int q = 0; q < columns; q++) {
                if (organism[j][q] == null){
                    organism[j][q] = cell;
                    organism[j][q].mutate();
                    break;
                }
            }
        }
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
