package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import tools.Data;
import tools.Dwc;
import tools.Element;
import tools.Indicateurs;
import tools.MDODWC;
import tools.RowDWC;
	
public class GraphFlux extends JFrame
{

	private static final long serialVersionUID = 1L;

	public GraphFlux(int saisons, String s, int mois, ArrayList<Data> data, ArrayList<Element> hydro)
	{
		super(s);
		this.setIconImage(new ImageIcon("./config/water.png" ).getImage());
		JPanel jpanel = createDemoPanel(saisons, s, mois, data, hydro);
		jpanel.setPreferredSize(new Dimension(500, 270));
		setContentPane(jpanel);
	}

	private static JFreeChart createChart(XYDataset xydataset, String s)
	{
		JFreeChart jfreechart;
		if(s.contains("année")) {
			jfreechart = ChartFactory.createTimeSeriesChart(s, "Date", "Flux", xydataset, false, true, false);
		}
		else
			jfreechart = ChartFactory.createXYLineChart(s, "Saison", "Flux", xydataset, PlotOrientation.VERTICAL, false, true, false);
		XYPlot xyplot = (XYPlot)jfreechart.getPlot();
		xyplot.setDomainPannable(true);
		xyplot.setRangePannable(false);
		xyplot.setDomainCrosshairVisible(true);
		xyplot.setRangeCrosshairVisible(true);
		org.jfree.chart.renderer.xy.XYLineAndShapeRenderer xyitemrenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
		xyitemrenderer.setSeriesPaint(0, Color.blue);
		xyitemrenderer.setSeriesPaint(1, Color.red);
		xyitemrenderer.setSeriesPaint(2, Color.red);
		xyitemrenderer.setSeriesShapesVisible(1, false);
		xyitemrenderer.setSeriesShapesVisible(2, false);
		
		if (xyitemrenderer instanceof XYLineAndShapeRenderer)
		{
			XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyitemrenderer;
			xylineandshaperenderer.setBaseShapesVisible(true);
		}
		if(s.contains("année")) {
			DateAxis dateaxis = (DateAxis)xyplot.getDomainAxis();
			String sdate = "yyyy";
			dateaxis.setDateFormatOverride(new SimpleDateFormat(sdate));
		}
		else {
			NumberAxis numberaxis = (NumberAxis)xyplot.getDomainAxis();
			numberaxis.setTickUnit(new NumberTickUnit(1));
		}
		return jfreechart;
	}

	private static XYDataset createDataset1(int offset, ArrayList<Data> data, ArrayList<Element> hydro)
	{
		if(offset != 0) {
			int month, year;
			for(int i = 0; i < data.size(); i++) {
				month = data.get(i).getMonth();
				year = data.get(i).getYear();

				month -= offset;
				if(month <= 0) {
					month += 12;
					year--;
				}

				data.get(i).setMonth(month);
				data.get(i).setYear(year);
			}
			for(int i = 0; i < hydro.size(); i++) {
				month = hydro.get(i).getMonth();
				year = hydro.get(i).getYear();

				month -= offset;
				if(month <= 0) {
					month += 12;
					year--;
				}

				hydro.get(i).setMonth(month);
				hydro.get(i).setYear(year);
			}
		}

		TimeSeries timeseries = new TimeSeries("Flux corrigés");
		TimeSeries flux_inf = new TimeSeries("Flux limite inférieure");
		TimeSeries flux_sup = new TimeSeries("Flux limite supérieure");
		ArrayList<Data> sub_data = new ArrayList<Data>();
		ArrayList<Element> sub_hydro = new ArrayList<Element>();
		int previous_year = data.get(0).getYear();

		JFrame frame = new JFrame("Flux par année");
		MDODWC modele = new MDODWC();
		JTable table = new JTable(modele);
		frame.setSize(500, 500);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setLocationRelativeTo(frame.getParent());

		for(int i = 0; i < data.size(); i++) {
			if(data.get(i).getYear() != previous_year || i == data.size() - 1) {
				for(Element e : hydro) {
					if(e.getYear() == previous_year)
						sub_hydro.add(e);
				}
				float flux = Dwc.Flux(sub_data, sub_hydro);
				int periode = 1 + data.get(sub_data.size() - 1).getYear() - sub_data.get(0).getYear();
				int recurrence = Math.round(365 / (sub_data.size() / (1 + sub_data.get(sub_data.size() - 1).getYear() - sub_data.get(0).getYear())));
				float b50sup = Indicateurs.b50sup(sub_data);
				float m2 = Indicateurs.m2(sub_hydro, b50sup);
				float biais = Dwc.Biais(periode, recurrence, m2, b50sup);
				float e10 = Dwc.impe10(periode, recurrence, m2);
				float e90 = Dwc.impe90(periode, recurrence, m2);
				flux = flux - (flux * biais / 100);
				float flux_bas = flux - (flux * Math.abs(e10) / 100);
				float flux_haut = flux + (flux * Math.abs(e90) / 100);

				timeseries.add(new Year(previous_year), flux);
				flux_inf.add(new Year(previous_year), flux_bas);
				flux_sup.add(new Year(previous_year), flux_haut);
				modele.addRow(new RowDWC(previous_year, (int) flux, (int) flux_bas, (int) flux_haut, biais, e10, e90));

				sub_data.clear();
				sub_hydro.clear();
			}
			sub_data.add(data.get(i));
			previous_year = data.get(i).getYear();			
		}

		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		timeseriescollection.addSeries(flux_inf);
		timeseriescollection.addSeries(flux_sup);
		
		data.clear();
		hydro.clear();
		
		return timeseriescollection;
	}

	private static XYDataset createDataset2(int saisons, int offset, ArrayList<Data> data, ArrayList<Element> hydro) {
		if(offset != 0) {
			int month, year;
			for(int i = 0; i < data.size(); i++) {
				month = data.get(i).getMonth();
				year = data.get(i).getYear();

				month -= offset;
				if(month <= 0) {
					month += 12;
					year--;
				}

				data.get(i).setMonth(month);
				data.get(i).setYear(year);
			}
			for(int i = 0; i < hydro.size(); i++) {
				month = hydro.get(i).getMonth();
				year = hydro.get(i).getYear();

				month -= offset;
				if(month <= 0) {
					month += 12;
					year--;
				}

				hydro.get(i).setMonth(month);
				hydro.get(i).setYear(year);
			}
		}

		XYSeries timeseries = new XYSeries("Flux corrigés");
		XYSeries flux_inf = new XYSeries("Flux limite inférieure");
		XYSeries flux_sup = new XYSeries("Flux limite supérieure");

		ArrayList<ArrayList<Data>> array_data = new ArrayList<ArrayList<Data>>();
		ArrayList<ArrayList<Element>> array_hydro = new ArrayList<ArrayList<Element>>();
		int divided = 12 / saisons, debut, fin;
		debut = 1;
		fin = divided;
		for(int i = 0; i < saisons; i++) {
			array_data.add(new ArrayList<Data>());
			array_hydro.add(new ArrayList<Element>());

			for(Data a : data) {
				if(a.getMonth() >= debut && a.getMonth() <= fin)
					array_data.get(i).add(a);
			}

			for(Element e : hydro) {
				if(e.getMonth() >= debut && e.getMonth() <= fin)
					array_hydro.get(i).add(e);
			}

			debut += divided;
			fin += divided;
		}

		JFrame frame = new JFrame("Flux par saison");
		MDODWC modele = new MDODWC();
		JTable table = new JTable(modele);
		frame.setSize(500, 500);
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setLocationRelativeTo(frame.getParent());

		float flux = 0, m2 = 0, b50sup = 0, flux_bas = 0, flux_haut = 0, e10 = 0, e90 = 0, biais = 0;
		int periode = 0, recurrence = 0;
		Boolean first = true;

		for(int i = 0; i < saisons; i++) {
			try {
				flux = Dwc.Flux(array_data.get(i), array_hydro.get(i));
				periode = 1 + data.get(array_data.get(i).size() - 1).getYear() - array_data.get(i).get(0).getYear();
				recurrence = Math.round(365 / (array_data.get(i).size() / (1 + array_data.get(i).get(array_data.get(i).size() - 1).getYear() - array_data.get(i).get(0).getYear())));
				b50sup = Indicateurs.b50sup(array_data.get(i));
				m2 = Indicateurs.m2(array_hydro.get(i), b50sup);
				
				biais = Dwc.Biais(periode, recurrence, m2, b50sup);
				e10 = Dwc.impe10(periode, recurrence, m2);
				e90 = Dwc.impe90(periode, recurrence, m2);
				flux = flux - (flux * biais / 100);
				flux_bas = flux - (flux * Math.abs(e10) / 100);
				flux_haut = flux + (flux * Math.abs(e90) / 100);
				
			}
			catch(Exception e) {
				if(first)
					JOptionPane.showMessageDialog(null, "Résultats approximés (absence de données).\nRéduisez la période", "Résultats approximés", JOptionPane.WARNING_MESSAGE);
				first = false;
			}

			timeseries.add(i + 1, flux);
			flux_inf.add(i + 1, flux_bas);
			flux_sup.add(i + 1, flux_haut);
			modele.addRow(new RowDWC(i + 1, (int) flux, (int) flux_bas, (int) flux_haut, biais, e10, e90));
		}

		XYSeriesCollection timeseriescollection = new XYSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		timeseriescollection.addSeries(flux_inf);
		timeseriescollection.addSeries(flux_sup);
		
		data.clear();
		hydro.clear();
		
		return timeseriescollection;
	}

	public static JPanel createDemoPanel(int saisons, String s, int mois, ArrayList<Data> data, ArrayList<Element> hydro)
	{
		JFreeChart jfreechart;
		if(s.contains("année"))
			jfreechart = createChart(createDataset1(mois, data, hydro), s);
		else
			jfreechart = createChart(createDataset2(saisons, mois, data, hydro), s);
		return new ChartPanel(jfreechart);
	}

}
