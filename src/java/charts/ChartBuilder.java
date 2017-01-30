package charts;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author 13232238
 */
public class ChartBuilder {

    /**
     * Creates a new instance of ChartBuilder
     */
    public ChartBuilder() {
    }

    static public BarChartModel BuildVerBarChart(String chartTitle, String legendPosition, String xaxisLabel, String yaxisLabel, ChartSeries chartSeries, String seriesColor, Integer xTickAngle, Integer yTickAngle, boolean animate) {
        BarChartModel barModel = new BarChartModel();
        barModel.setTitle(chartTitle);
        barModel.setLegendPosition(legendPosition);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(xaxisLabel);
        xAxis.setTickAngle(xTickAngle);
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel(yaxisLabel);
        yAxis.setTickAngle(yTickAngle);
        barModel.addSeries(chartSeries);
        barModel.setSeriesColors(seriesColor);
        barModel.setExtender("EnableChartVaryColor");
        barModel.setAnimate(animate);
        return barModel;
    }

    static public HorizontalBarChartModel BuildHorBarChart(String chartTitle, String legendPosition, String xaxisLabel, String yaxisLabel, ChartSeries chartSeries, String seriesColor, boolean animate) {
        HorizontalBarChartModel horBarModel = new HorizontalBarChartModel();
        horBarModel.setTitle(chartTitle);
        horBarModel.setLegendPosition(legendPosition);
        Axis xAxis = horBarModel.getAxis(AxisType.X);
        xAxis.setLabel(xaxisLabel);
        Axis yAxis = horBarModel.getAxis(AxisType.Y);
        yAxis.setLabel(yaxisLabel);
        horBarModel.addSeries(chartSeries);
            horBarModel.setSeriesColors(seriesColor);
                horBarModel.setExtender("EnableChartVaryColor");
        horBarModel.setAnimate(animate);
        return horBarModel;
    }

    static public PieChartModel BuildPieChart(String chartTitle, String legendPosition, String[] labels, String seriesColor, Object[] values) {
        PieChartModel pieModel = new PieChartModel();
        pieModel.setTitle(chartTitle);
        pieModel.setLegendPosition(legendPosition);

        for (int i = 0; i < labels.length; i++) {
            pieModel.set(labels[i], Integer.parseInt(values[i].toString()));
        }
        if (!seriesColor.equals("")) {
            pieModel.setSeriesColors(seriesColor);
        }
        return pieModel;
    }

    static public BubbleChartModel BuildBubbleChart(String chartTitle, String xAxisLabel, String yAxisLabel, String[] labels, Object[] xSeries, Object[] ySeries, Object[] bubbleSize) {
        BubbleChartModel bubbleModel = new BubbleChartModel();
        for (int i = 0; i < labels.length; i++) {
            bubbleModel.add(new BubbleChartSeries(labels[i], Integer.parseInt(xSeries[i].toString()), Integer.parseInt(ySeries[i].toString()), Integer.parseInt(bubbleSize[i].toString())));
        }
        bubbleModel.setTitle(chartTitle);
        bubbleModel.getAxis(AxisType.X).setLabel(xAxisLabel);
        bubbleModel.getAxis(AxisType.Y).setLabel(yAxisLabel);
        Axis yAxis = bubbleModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        return bubbleModel;
    }

    static public ChartSeries GnenerateBarChartSeries(String[] labels, Object[] values) {

        ChartSeries chartSeries = new ChartSeries();
        chartSeries.setLabel("");

        for (int i = 0; i < labels.length; i++) {
            chartSeries.set(labels[i], Integer.parseInt(values[i].toString()));
        }
        return chartSeries;
    }

}
