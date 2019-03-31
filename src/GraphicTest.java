import javax.swing.*;
import java.awt.*;

public class GraphicTest extends JFrame {

    private Canvas canvas;
    public GraphicTest(){
        setSize(new Dimension(1800,1000));
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        canvas = new GraphicCanvas(new Dimension(getSize().width - 20, getSize().height -20), 40);
        canvas.setSize(getSize());

        add(canvas, BorderLayout.CENTER);
    }


    public Canvas getCanvas(){
     return canvas;
    }
}
