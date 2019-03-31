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
//    private Canvas canvas;
    private DataCurve data;

    public GraphicView() {
        Properties props = new Properties();
        ConfigParameters config = new ConfigParameters(new PropertiesBasedConfigData(props));
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

//        add(createControlPanel(), BorderLayout.SOUTH);
//        canvas = new GraphicCanvas(new Dimension(getSize().width - 20, getSize().height -20), 40);
//        canvas.setSize(getSize());
//
//        add(canvas, BorderLayout.CENTER);
    }


    public Canvas getCanvas() {
        return canvas;
    }
    public void addPoint(int x, int y){
        data.addElement(new DataPoint(x, y));
    }

    private GraphicsPlotCanvas createPlotCanvas() {
        //Adjustments:
        Properties props = new Properties();
        ConfigParameters config = new ConfigParameters(new PropertiesBasedConfigData(props));
        props.put("plot/legendVisible", "false");
        props.put("plot/coordinateSystem/xAxis/minimum", "0");
        props.put("plot/coordinateSystem/xAxis/maximum", "40000");
        props.put("plot/coordinateSystem/xAxis/axisLabel", "Cell Division");
        props.put("plot/coordinateSystem/xAxis/ticLabelFormat/map", "%d");
        props.put("plot/coordinateSystem/yAxis/axisLabel", "Cancer cells");
        props.put("plot/coordinateSystem/yAxis/maximum", "1000");
        props.put("plot/coordinateSystem/yAxis/minimum", "0");
        props.put("plot/coordinateSystem/yAxis/ticLabelFormat", "%d");
        props.put("plot/curveFactory/definitions", "curve, curve2");
        props.put("plot/curveFactory/curve/withLine", "true");
        props.put("plot/curveFactory/curve/symbolFactory/className", "jcckit.plot.CircleSymbolFactory");
        props.put("plot/curveFactory/curve/symbolFactory/attributes/className", "jcckit.graphic.ShapeAttributes");
        props.put("plot/curveFactory/curve/symbolFactory/attributes/fillColor", "0xfe8000");
        props.put("plot/curveFactory/curve/symbolFactory/attributes/lineColor", "0");
        props.put("plot/curveFactory/curve/symbolFactory/size", "0.01");
        props.put("plot/initialHintForNextCurve/className", "jcckit.plot.PositionHint");
        props.put("plot/initialHintForNextCurve/position", "0 0.1");
        return new GraphicsPlotCanvas(config);
    }
}
