package com.nkvl.app.classes;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;

public class Chart {
    //new int[] { 130, 117, 124, 135, 154, 110, 111, 180, 105, 100 }
    private static void CreateChart(String chartTitle, String xLabel, String yLabel, String rowKey, int imageWidth,
                                    int imageHeight, String fileName, int[] values) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        for (int i = 0; i < values.length; i++) {
            dataset.addValue(values[i], rowKey, i+"");
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
                xLabel,yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false);

        File chartFile = new File(fileName);
        ChartUtils.saveChartAsJPEG(chartFile, lineChart, imageWidth, imageHeight);
    }
}
