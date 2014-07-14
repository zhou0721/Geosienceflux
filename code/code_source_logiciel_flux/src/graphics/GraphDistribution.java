package graphics;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

import tools.Element;
import tools.Init;

public class GraphDistribution extends JFrame
{
	private static final long serialVersionUID = 1L;

	public GraphDistribution(String s, int i)
	{
		super(s);
		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());
		JPanel jpanel = createDemoPanel(i, s);
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static IntervalXYDataset createDataset(int i)
	{
		HistogramDataset histogramdataset = new HistogramDataset();
		double[] ad;
		if(i == 1) {
			ad = new double[Init.getHydro().size()];
			int cpt = 0;
			for(Element e : Init.getHydro()) {
				ad[cpt] = Math.log10(e.getValue());
				cpt++;
			}
			int k = (int) (Math.log(ad.length) / Math.log(2));
			histogramdataset.addSeries("Débit", ad, k);
		}
		else if (i == 2) {
			ad = new double[Init.getRnb().size()];
			int cpt = 0;
			for(Element e : Init.getRnb()) {
				ad[cpt] = e.getValue();
				cpt++;
			}
			int k = (int) (Math.log(ad.length) / Math.log(2));
			histogramdataset.addSeries(MainFrame.getCombo_col_value_rnb().getSelectedItem().toString(), ad, k);
		}
		return histogramdataset;
	}

	private static JFreeChart createChart(IntervalXYDataset intervalxydataset, String s)
	{
		JFreeChart jfreechart = ChartFactory.createHistogram(Init.getInfo().getS_rnb() + " / " + Init.getInfo().getS_hydro() + " : " + Init.getInfo().getPolluant(), "Valeur", "Nombre de relevés",
				intervalxydataset, PlotOrientation.VERTICAL, true, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		xyplot.setDomainPannable(true);
		xyplot.setRangePannable(true);
		xyplot.setForegroundAlpha(0.85F);
		NumberAxis numberaxis = (NumberAxis)xyplot.getRangeAxis();
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		XYBarRenderer xybarrenderer = (XYBarRenderer)xyplot.getRenderer();
		xybarrenderer.setDrawBarOutline(false);
		xybarrenderer.setBarPainter(new StandardXYBarPainter());
		xybarrenderer.setShadowVisible(false);
		return jfreechart;
	}

	public static JPanel createDemoPanel(int i, String s)
	{
		JFreeChart jfreechart = createChart(createDataset(i), s);
		ChartPanel chartpanel = new ChartPanel(jfreechart);
		chartpanel.setMouseWheelEnabled(true);
		return chartpanel;
	}

}