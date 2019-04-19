import java.awt.*;

public class CellsCanvas extends Canvas {

    private int xOffset;
    private int yOffset;
    private Cell[][] cells;
    private Dimension screenDimensions;

    public CellsCanvas(Cell[][] cells, Dimension screenDimensions) {
        this.cells = cells;
        this.screenDimensions = screenDimensions;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        xOffset = 0;
        yOffset = 0;

        int cellHeight = (int) (screenDimensions.getHeight()) / cells.length;
        int cellWidth = (int) (screenDimensions.getWidth()) / cells[0].length;


            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
                    if (cells[i][j].cellType == Cell.CANCEROUS_CELL) {
                        g2.setColor(Color.RED);
                    } else {
                        g2.setColor(Color.GREEN);
                    }
                    g2.fillRect(xOffset, yOffset, cellWidth, cellHeight);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(xOffset, yOffset, cellWidth, cellHeight);

                    xOffset += cellWidth;

                }
                xOffset = 0;
                yOffset += cellHeight;
            }

    }

    @Override
    public void update(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        xOffset = 0;
        yOffset = 0;

        int cellHeight = (int) (screenDimensions.getHeight()) / cells.length;
        int cellWidth = (int) (screenDimensions.getWidth()) / cells[0].length;

        double fitness;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].cellType == Cell.CANCEROUS_CELL) {
                    g2.setColor(Color.RED);
                    fitness = Cell.fitnessC;

                }else{
                    g2.setColor(Color.GREEN);
                    fitness = Cell.fitnessH;

                }

                    g2.fillRect(xOffset, yOffset, cellWidth, cellHeight);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(xOffset, yOffset, cellWidth, cellHeight);
                    g2.drawString(fitness+"", xOffset+cellWidth/2, yOffset + (cellHeight));
                    xOffset += cellWidth;

            }
            xOffset = 0;
            yOffset += cellHeight;
        }
    }
}
