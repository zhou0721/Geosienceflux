package graphics;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import tools.Element;
import tools.Init;

public class GraphChronologique extends JFrame
{
	private static final long serialVersionUID = 1L;

	public GraphChronologique(String s)
	{
		super(s);
		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());
		JPanel jpanel = createDemoPanel();
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static XYDataset createDataset()
	{
		TimeSeries timeseries = createTimeSeriesHydro();
		TimeSeries timeseries1 = createTimeSeriesRnb();
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries1);
		timeseriescollection.addSeries(timeseries);
		return timeseriescollection;
	}

	private static TimeSeries createTimeSeriesHydro()
	{
		TimeSeries timeseries = new TimeSeries("Débit");
		try
		{
			for(Element e : Init.getHydro())
				timeseries.add(new Day(e.getDay(), e.getMonth(), e.getYear()), new Double(e.getValue()));

		}
		catch (Exception exception)
		{
			System.err.println(exception.getMessage());
		}
		return timeseries;
	}

	private static TimeSeries createTimeSeriesRnb()
	{
		Float coef = Init.getInfo().getMaxHydro() / Init.getInfo().getMaxRnb();
		TimeSeries timeseries = new TimeSeries(MainFrame.getCombo_col_value_rnb().getSelectedItem().toString());
		try
		{
			for(Element e : Init.getRnb())
				timeseries.add(new Day(e.getDay(), e.getMonth(), e.getYear()), new Double(e.getValue()*coef));
		}
		catch (Exception exception)
		{
			System.err.println(exception.getMessage());
		}
		return timeseries;
	}

	private static JFreeChart createChart(XYDataset xydataset)
	{
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(Init.getInfo().getS_rnb() + " / " + Init.getInfo().getS_hydro() + " : " + Init.getInfo().getPolluant(), "Date", "Débit (m3/s)", xydataset, true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		NumberAxis numberaxis = new NumberAxis(MainFrame.getCombo_col_value_rnb().getSelectedItem().toString());
		numberaxis.setAutoRange(true);
		numberaxis.setDefaultAutoRange(new Range(Init.getInfo().getMinRnb(), Init.getInfo().getMaxRnb()));
		xyplot.setRangeAxis(1, numberaxis);
		xyplot.mapDatasetToRangeAxis(0, 0);
		xyplot.mapDatasetToRangeAxis(1, 1);
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairVisible(true);
		XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
		xylineandshaperenderer.setAutoPopulateSeriesStroke(false);
		xylineandshaperenderer.setBaseShapesVisible(true);
		xylineandshaperenderer.setBaseShapesFilled(true);
		xylineandshaperenderer.setBaseItemLabelsVisible(true);
		xylineandshaperenderer.setBaseStroke(new BasicStroke(1.5F, 1, 1));
		xylineandshaperenderer.setDrawSeriesLineAsPath(true);
		xylineandshaperenderer.setSeriesShapesVisible(1, false);
		StandardXYToolTipGenerator standardxytooltipgenerator = new StandardXYToolTipGenerator("{0}: ({1}, {2})", new SimpleDateFormat("d-MMM-yyyy"), new DecimalFormat("0.00"));
		xylineandshaperenderer.setBaseToolTipGenerator(standardxytooltipgenerator);
		ChartUtilities.applyCurrentTheme(jfreechart);
		return jfreechart;
	}

	public static JPanel createDemoPanel()
	{
		JFreeChart jfreechart = createChart(createDataset());
		ChartPanel chart = new ChartPanel(jfreechart);
		chart.setMouseWheelEnabled(true);
		return chart;
	}

}
