import java.awt.*;

public class GraphicCanvas extends Canvas {

    private Dimension dimension;
    private int margin;

    public GraphicCanvas(Dimension dimension, int margin){
        this.dimension = dimension;
        this.margin = margin;

    }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.drawLine(margin, dimension.height - margin, margin,0);
        g2.drawLine(margin, dimension.height - margin, dimension.width - margin, dimension.height - margin);

        int space = dimension.height/ 10;
        int start = dimension.height;

        for (int i = 0; i < 1000; i++){
            if (i % 100 == 0) {
                g2.drawString(""+i, 10, start - margin);
                start -= space;

            }
        }

        space = dimension.width / 10;
        start = 0;
        for (int i = 0; i < 10000; i++){
            if (i % 1000 == 0) {
                g2.drawString(""+i, start + margin, dimension.height - 20);
                start += space;
            }
        }
    }

    @Override
    public void repaint(int x, int y, int width, int height) {
        getGraphics().drawLine(x+ margin,(dimension.height - y) - margin,x + margin,(dimension.height - y) - margin);

    }
}
