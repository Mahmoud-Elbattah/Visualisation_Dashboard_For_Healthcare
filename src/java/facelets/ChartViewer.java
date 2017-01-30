/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facelets;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import charts.ChartSelector;
import common.CommonObjects;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.PieChartModel;
/**
 *
 * @author dxdiag
 */
@ManagedBean
@SessionScoped
public class ChartViewer implements Serializable {
    private String selectedNIMSChart = "-1";
    private String selectedIHFDChart = "-1";
    private boolean verBarChartSelected = false;
    private boolean horBarChartSelected = false;
    private boolean pieChartSelected = false;
    private boolean bubbleChartSelected = false;
    private boolean maleSelected = true;
    private boolean femaleSelected = true;
    private java.util.Date  dateFrom=null;
    private java.util.Date dateTo=null;
    private String selectedOrganization = "root";
    private String selectedTab="NIMS";
    private String chartStyle = "height:400px";
    private int lowerAge=0;
    private int upperAge=112;

    public String getChartStyle() {
        return chartStyle;
    }
    public void setChartStyle(String chartStyle) {
        this.chartStyle = chartStyle;
    }

    public boolean isVerBarChartSelected() {
        return verBarChartSelected;
    }
    public void setVerBarChartSelected(boolean verBarChartSelected) {
        this.verBarChartSelected = verBarChartSelected;
    }

    public boolean isHorBarChartSelected() {
        return horBarChartSelected;
    }
    public void setHorBarChartSelected(boolean horBarChartSelected) {
        this.horBarChartSelected = horBarChartSelected;
    }

    public boolean isPieChartSelected() {
        return pieChartSelected;
    }
    public void setPieChartSelected(boolean pieChartSelected) {
        this.pieChartSelected = pieChartSelected;
    }

    public boolean isBubbleChartSelected() {
        return bubbleChartSelected;
    }
    public void setBubbleChartSelected(boolean bubbleChartSelected) {
        this.bubbleChartSelected = bubbleChartSelected;
    }
    public String getSelectedNIMSChart() {
        return selectedNIMSChart;
    }
    public void setSelectedNIMSChart(String selectedNIMSChart) {
        this.selectedNIMSChart = selectedNIMSChart;            
    }
    public String getSelectedIHFDChart() {
        return selectedIHFDChart;
    }
    public void setSelectedIHFDChart(String selectedIHFDChart) {
        this.selectedIHFDChart = selectedIHFDChart;
    }
    public String getSelectedChart(){
        if(selectedTab.equals("NIMS"))
            return this.selectedNIMSChart;
        else if(selectedTab.equals("IHFD"))
            return this.selectedIHFDChart;
        else 
            return "";
    }
     public void onCountryChange(String selectedTab) {
        this.selectedTab=selectedTab;
     }
    public String getSelectedOrganization() {
        return selectedOrganization;
    }
    public void setSelectedOrganization(String selectedOrganization) {
        this.selectedOrganization = selectedOrganization;
        if (selectedNIMSChart.equalsIgnoreCase("NIMSSummary") && this.selectedOrganization.startsWith("CareHomesCHO", 0)) {
            this.chartStyle = "height:1000px";
        }
        else if(selectedIHFDChart.equalsIgnoreCase("IHFDDischargeCode")){
            this.chartStyle = "height:600px";
        }
        //else
        //  this.chartStyle="height:400px";
    }

    public boolean isMaleSelected() {
        return maleSelected;
    }
    public void setMaleSelected(boolean maleSelected) {
        this.maleSelected = maleSelected;
    }

    public boolean isFemaleSelected() {
        return femaleSelected;
    }
    public void setFemaleSelected(boolean femaleSelected) {
        this.femaleSelected = femaleSelected;
    }

    public int getLowerAge() {
        return lowerAge;
    }
    public void setLowerAge(int lowerAge) {
        this.lowerAge = lowerAge;
    }

    public int getUpperAge() {
        return upperAge;
    }
    public void setUpperAge(int upperAge) {
        this.upperAge = upperAge;
    }

    public java.util.Date  getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(java.util.Date  dateFrom) {
        this.dateFrom = dateFrom;
    }

    public java.util.Date getDateTo() {
        return dateTo;
    }
    public void setDateTo(java.util.Date dateTo) {
        this.dateTo = dateTo;
    }
    public BarChartModel ViewVerBarChart() {
        BarChartModel barModel = new BarChartModel();
        if (this.verBarChartSelected == true) {
            RequestContext.getCurrentInstance().execute("showSlide2();");
            CommonObjects.GetFilterParams(this.selectedTab,this.maleSelected, this.femaleSelected, this.lowerAge, this.upperAge,this.dateFrom,this.dateTo);
            barModel = (BarChartModel) ChartSelector.CreateChart(this.selectedOrganization, getSelectedChart(), ChartSelector.ChartType.VBarChart);
        } else {//Restting barchart
            //RequestContext.getCurrentInstance().execute("hidePanel('verBarChartPanel');");
            ChartSeries emptySeries = new ChartSeries();
            emptySeries.set("", 0);
            barModel.addSeries(emptySeries);
        }
        return barModel;
    }

    public BarChartModel ViewHorBarChart() {
        HorizontalBarChartModel horBarModel = new HorizontalBarChartModel();
        if (this.horBarChartSelected == true) {
            RequestContext.getCurrentInstance().execute("showSlide2();");
            CommonObjects.GetFilterParams(this.selectedTab,this.maleSelected, this.femaleSelected, this.lowerAge, this.upperAge,this.dateFrom,this.dateTo);
            horBarModel = (HorizontalBarChartModel) ChartSelector.CreateChart(this.selectedOrganization, getSelectedChart(), ChartSelector.ChartType.HBarChart);
        }
        else {//Restting barchart
            //RequestContext.getCurrentInstance().execute("hidePanel('verBarChartPanel');");
            ChartSeries emptySeries = new ChartSeries();
            emptySeries.set("", 0);
            horBarModel.addSeries(emptySeries);
        }
        return horBarModel;
    }

    public PieChartModel ViewPieChart() {
        PieChartModel pieModel = new PieChartModel();
        if (this.pieChartSelected == true) {
            //RequestContext.getCurrentInstance().execute("showPanel('verBarChartPanel');");
            RequestContext.getCurrentInstance().execute("showSlide2();");
            CommonObjects.GetFilterParams(this.selectedTab,this.maleSelected, this.femaleSelected, this.lowerAge, this.upperAge,this.dateFrom,this.dateTo);
            pieModel = (PieChartModel) ChartSelector.CreateChart(this.selectedOrganization, getSelectedChart(), ChartSelector.ChartType.PieChart);
            pieModel.setShowDataLabels(true);
            pieModel.setMouseoverHighlight(true);
            pieModel.setSliceMargin(4);
            pieModel.setExtender("SetPieHighlighter");

        } else {//Restting pie chart
            //RequestContext.getCurrentInstance().execute("hidePanel('verBarChartPanel');");
            pieModel.getData().put("", null);
        }
        return pieModel;
    }

    public BubbleChartModel ViewBubbleModel() {
        BubbleChartModel bubbleModel = new BubbleChartModel();
        if (this.bubbleChartSelected == true) {
            if (selectedNIMSChart.equalsIgnoreCase("NIMSAgeStats")) {
              //  bubbleModel = (BubbleChartModel) Temp.OldChartModels.NIMSAgeStats(this.selectedOrganization, OldChartModels.ChartType.BubbleChart);
                bubbleModel.setExtender("SetBubbleHighlighter");
                bubbleModel.setShadow(false);
                bubbleModel.setBubbleGradients(true);
                bubbleModel.setBubbleAlpha(0.8);
            }

        } else {//Restting bubble model
            bubbleModel.add(new BubbleChartSeries());
        }
        return bubbleModel;
    }
}
