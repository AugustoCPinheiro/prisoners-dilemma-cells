import javax.swing.*;
import java.awt.*;

public class View extends JFrame {

    private Cell[][] cells;
    private Canvas canvas;
    public View(Cell[][] cells){
        setSize(new Dimension(1200,1200));
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.cells = cells;
        setupCanvas();
    }
    private void setupCanvas(){
        canvas = new CellsCanvas(cells, getSize());

        canvas.setSize(this.getSize());
        add(canvas, BorderLayout.CENTER);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
