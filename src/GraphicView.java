import jcckit.GraphicsPlotCanvas;
import jcckit.data.DataCurve;
import jcckit.data.DataPlot;
import jcckit.data.DataPoint;
import jcckit.util.ConfigParameters;
import jcckit.util.PropertiesBasedConfigData;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class GraphicView extends JFrame {

    private DataPlot _dataPlot;
    private DataCurve data;

    public GraphicView() {
        setSize(new Dimension(1800, 1000));
        setVisible(true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        data = new DataCurve("");
        _dataPlot = new DataPlot();
        _dataPlot.addElement(data);
        GraphicsPlotCanvas plotCanvas = createPlotCanvas();

        plotCanvas.connect(_dataPlot);
        setLayout(new BorderLayout());
        add(plotCanvas.getGraphicsCanvas(), BorderLayout.CENTER);

    }

    public void addPoint(int x, int y){
        data.addElement(new DataPoint(x, y));
    }

    private GraphicsPlotCanvas createPlotCanvas() {

        Properties props = new Properties();
        ConfigParameters config = new ConfigParameters(new PropertiesBasedConfigData(props));
        props.put("plot/legendVisible", "false");
        props.put("plot/coordinateSystem/xAxis/minimum", "0");
        props.put("plot/coordinateSystem/xAxis/maximum", "40000");
        props.put("plot/coordinateSystem/xAxis/axisLabel", "Generation");
        props.put("plot/coordinateSystem/xAxis/ticLabelFormat/map", "%d");
        props.put("plot/coordinateSystem/yAxis/axisLabel", "Cancer cells");
        props.put("plot/coordinateSystem/yAxis/maximum", "1000");
        props.put("plot/coordinateSystem/yAxis/minimum", "0");
        return new GraphicsPlotCanvas(config);
    }
}
