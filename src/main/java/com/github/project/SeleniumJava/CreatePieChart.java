package com.github.project.SeleniumJava;

import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import org.apache.commons.codec.language.RefinedSoundex;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class CreatePieChart extends ApplicationFrame{

	static String xmlTitle;

    /**
     * Default constructor.
     *
     * @param title the frame title.
     * @throws IOException
     */
    public CreatePieChart(String title, String passedTC, String failedTC, String skippedTC) {
        super(title);
        xmlTitle = title;
        setContentPane(createDemoPanel(passedTC, failedTC, skippedTC));
    }

    /**
     * Creates a sample dataset.
     *
     * @return A sample dataset.
     */
    private static PieDataset createDataset(String passedTC, String failedTC, String skippedTC) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        if (!(Integer.parseInt(passedTC) == 0)) {
            dataset.setValue("Passed :" + passedTC, new Double(Integer.parseInt(passedTC)));
        }
        if (!(Integer.parseInt(failedTC) == 0)) {
            dataset.setValue("Failed :" + failedTC, new Double(Integer.parseInt(failedTC)));
        }
        if (!(Integer.parseInt(skippedTC) == 0)) {
            dataset.setValue("Skipped :" + skippedTC, new Double(Integer.parseInt(skippedTC)));
        }
        return dataset;
    }
	
    /**
     * Creates a chart.
     *
     * @param dataset the dataset.
     *
     * @return A chart.
     * @throws IOException
     */
    private static JFreeChart createChart(PieDataset dataset) {

        JFreeChart chart = ChartFactory.createPieChart(xmlTitle, // chart title
            dataset, // data
            true, // include legend
            true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(true);
        plot.setLabelGap(0.09);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ChartUtils.saveChartAsJPEG(new File(System.getProperty("user.dir") + "/Reports/pieChart.jpeg"),
                                       chart, 800, 400);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // ChartUtilities.writeChartAsPNG(new
        // File("/home/taha/Desktop/test213.png"), chart, 50, 100);

        return chart;
    }

    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     * @throws IOException
     */
    public static JPanel createDemoPanel(String passedTC, String failedTC, String skippedTC) {
        JFreeChart chart = createChart(createDataset(passedTC, failedTC, skippedTC));
        return new ChartPanel(chart);
    }
    
    /**
     * Starting point for the demonstration application.
     * 
     * */
    public static void main(String[] args) {
    	
    	CreatePieChart demo = new CreatePieChart("Pie Chart", "40", "30", "20");
    	demo.pack();
    	demo.setVisible(true);

    }
    
}
