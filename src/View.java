import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private Cell[][] cells;
    private Canvas canvas;
    private JPanel panel;
    private JLabel fitnessLabel;
    private JLabel generationLabel;
    private JLabel cancerCellsLabel;
    private JLabel healthyCellsLabel;

    public View(Cell[][] cells){
        setSize(new Dimension(1400,1000));
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.cells = cells;
        setupCanvas();
        setupPanel();

    }
    private void setupCanvas(){
        canvas = new CellsCanvas(cells, getSize());

        canvas.setSize(new Dimension( new Dimension(getSize().width - 400, getSize().height)));
        add(canvas, BorderLayout.CENTER);
    }
    private void setupPanel(){
       panel = new JPanel();
        panel.setSize(400, getSize().height);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        add(panel, BorderLayout.EAST);

        fitnessLabel = new JLabel("Fitness: 0");
        generationLabel = new JLabel("Geração: 0");
        cancerCellsLabel = new JLabel("Cancerigenas: 0");
        healthyCellsLabel = new JLabel("Saudaveis: 0");

        panel.add(fitnessLabel);
        panel.add(generationLabel);
        panel.add(cancerCellsLabel);
        panel.add(healthyCellsLabel);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void updateLabels(double fitness, int generation, int cancerous, int healthy){
        fitnessLabel.setText("Fitness: "+ fitness);
        generationLabel.setText("Generation: "+ generation);
        cancerCellsLabel.setText("Cancerigenas: "+ cancerous);
        healthyCellsLabel.setText("Saudaveis: " + healthy);
    }
}
