package com.nkvl.app.classes;

import com.nkvl.app.App;
import com.nkvl.app.database.DBSpecies;
import org.apache.log4j.Level;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Enumeration;

public class Chart {
    public static void main(String[] args) throws IOException {

    }

    public static void createChallResultChart(long id, String mode, int range) throws IOException {
        createChart("Ваши результаты:", "испытания", "секунды", "",
                2000, 1000, Long.toString(id), DBSpecies.getUserStatValues(id, mode, range));
    }

    private static void createChart(String chartTitle, String xLabel, String yLabel, String rowKey,
                                    int imageWidth, int imageHeight, String fileName,
                                    Dictionary<String, Integer> values) throws IOException {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        Enumeration<String> tmpEnum = values.keys();
        while (tmpEnum.hasMoreElements()) {
            String key = tmpEnum.nextElement();
            dataset.addValue(values.get(key), rowKey, key.replace("--e", "")
                    .replace("--h", ""));
        }


        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                xLabel,yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false,false,false);

        App.logger.log(Level.INFO, String.format("New chart with [%s] was created", fileName));

        lineChart.getPlot().setBackgroundPaint(Color.WHITE);

        File chartFile = new File(fileName + ".jpeg");
        ChartUtils.saveChartAsJPEG(chartFile, lineChart, imageWidth, imageHeight);
        App.logger.log(Level.INFO, String.format("Chart [%s] was saved", fileName));
    }
}
