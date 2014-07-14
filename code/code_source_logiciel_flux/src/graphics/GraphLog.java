package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;

import tools.Data;
import tools.Init;

public class GraphLog extends JFrame {

	private static final long serialVersionUID = 1L;

	public GraphLog(String s)
	{
		super(s);

		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	public static XYDataset createDataset(ArrayList<Data> data)
	{
		XYSeries dots_one = new XYSeries("1");
		XYSeries dots_two = new XYSeries("2");
		XYSeries line_one = new XYSeries("3");
		XYSeries line_two = new XYSeries("4");
		XYSeries tmp = new XYSeries("");
		ArrayList<Data> working = new ArrayList<Data>();
		for(Data a : data)
			if(a.getFlow() > 0 && a.getValue() > 0) {
				working.add(a);
			}
		int mediane = working.size() / 2;
		for(Data a : working)
			tmp.add(Math.log10(a.getFlow()), Math.log10(a.getValue()));
		for(int i = 0; i < tmp.getItemCount(); i++) {
			if(i < mediane)
				dots_one.add(tmp.getDataItem(i).getXValue(), tmp.getDataItem(i).getYValue());
			else
				dots_two.add(tmp.getDataItem(i).getXValue(), tmp.getDataItem(i).getYValue());

		}

		double sumx = 0, sumx2 = 0, sumy = 0, sumxy = 0;
		for(int i = 0; i < tmp.getItemCount(); i++) {
			if(i < mediane) {
				sumx += tmp.getDataItem(i).getXValue();
				sumx2 += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getXValue();
				sumy += tmp.getDataItem(i).getYValue();
				sumxy += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getYValue();
			}
		}

		int n = dots_one.getItemCount();
		double meanX = sumx / n;
		double meanY = sumy / n;
		double slope = (sumxy - sumx * meanY) / (sumx2 - sumx * meanX);
		double intercept = (meanY - slope * meanX);
		double btwn = (dots_one.getDataItem(dots_one.getItemCount() - 1).getXValue() + dots_two.getDataItem(0).getXValue()) / 2;
		
		line_one.add(dots_one.getDataItem(0).getXValue(), dots_one.getDataItem(0).getXValue() * slope + intercept);
		line_one.add(btwn, btwn * slope + intercept);

		sumx = sumx2 = sumy = sumxy = 0;
		for(int i = 0; i < tmp.getItemCount(); i++) {
			if(!(i < mediane)) {
				sumx += tmp.getDataItem(i).getXValue();
				sumx2 += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getXValue();
				sumy += tmp.getDataItem(i).getYValue();
				sumxy += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getYValue();
			}
		}
		
		n = dots_two.getItemCount();
		meanX = sumx / n;
		meanY = sumy / n;

		slope = (sumxy - sumx * meanY) / (sumx2 - sumx * meanX);
		intercept = (meanY - slope * meanX);
		
		line_two.add(btwn, btwn * slope + intercept);
		line_two.add(dots_two.getDataItem(dots_two.getItemCount() - 1).getXValue(), dots_two.getDataItem(dots_two.getItemCount() - 1).getXValue() * slope + intercept);

		XYSeriesCollection xyseriescollection = new XYSeriesCollection();
		xyseriescollection.addSeries(dots_one);
		xyseriescollection.addSeries(dots_two);
		xyseriescollection.addSeries(line_one);
		xyseriescollection.addSeries(line_two);
		
		sumx = sumx2 = sumy = sumxy = 0;
		for(int i = 0; i < tmp.getItemCount(); i++) {
				sumx += tmp.getDataItem(i).getXValue();
				sumx2 += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getXValue();
				sumy += tmp.getDataItem(i).getYValue();
				sumxy += tmp.getDataItem(i).getXValue() * tmp.getDataItem(i).getYValue();
		}
		
		n = tmp.getItemCount();
		meanX = sumx / n;
		meanY = sumy / n;

		slope = (sumxy - sumx * meanY) / (sumx2 - sumx * meanX);
		
		return xyseriescollection;
	}

	private static JFreeChart createChart(XYDataset xydataset)
	{

		Shape[] result = new Shape[1];
		double size = 6;
		double delta = size / 2;
		result[0] = new Ellipse2D.Double(-delta, -delta, size, size);
		JFreeChart jfreechart = ChartFactory.createXYLineChart(Init.getInfo().getS_rnb() + " / " + Init.getInfo().getS_hydro() + " : " + Init.getInfo().getPolluant(), "log Débit (m3/s)", "log " + MainFrame.getCombo_col_value_rnb().getSelectedItem().toString(), xydataset, PlotOrientation.VERTICAL, false, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
		xylineandshaperenderer.setSeriesLinesVisible(0, false);
		xylineandshaperenderer.setSeriesShapesVisible(0, true);
		xylineandshaperenderer.setSeriesShape(0, result[0]);
		xylineandshaperenderer.setSeriesPaint(1, Color.GREEN);
		xylineandshaperenderer.setSeriesLinesVisible(1, false);
		xylineandshaperenderer.setSeriesShapesVisible(1, true);
		xylineandshaperenderer.setSeriesShape(1, result[0]);
		xylineandshaperenderer.setSeriesPaint(0, Color.MAGENTA);
		xylineandshaperenderer.setSeriesLinesVisible(2, true);
		xylineandshaperenderer.setSeriesShapesVisible(2, false);
		xylineandshaperenderer.setSeriesPaint(2, Color.MAGENTA);
		xylineandshaperenderer.setSeriesLinesVisible(3, true);
		xylineandshaperenderer.setSeriesShapesVisible(3, false);
		xylineandshaperenderer.setSeriesPaint(3, Color.GREEN);
		xylineandshaperenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
		xyplot.setRenderer(xylineandshaperenderer);
		NumberAxis numberaxis = (NumberAxis)xyplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		return jfreechart;
	}

	public static JPanel createDemoPanel()
	{
		JFreeChart jfreechart = createChart(createDataset(Init.getData()));
		ChartPanel chart = new ChartPanel(jfreechart);
		chart.setMouseWheelEnabled(true);
		return chart;
	}
	
}
