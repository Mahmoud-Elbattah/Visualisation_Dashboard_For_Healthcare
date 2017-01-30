/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Temp;


import charts.ChartDBQuery;
import charts.ChartBuilder;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.BubbleChartModel;
import org.primefaces.model.chart.BubbleChartSeries;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean
public class test implements Serializable {

    private String country = "-1";
    
 private boolean verBarchartSelected=false;
 
 public boolean isVerBarchartSelected() {
        return this.verBarchartSelected;
    }

    public void setVerBarchartSelected(boolean verBarchartSelected) {
        this.verBarchartSelected = verBarchartSelected;
    }
    
   

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public PieChartModel getLivePieModel() {
             PieChartModel livePieModel = new PieChartModel();

        //livePieModel.clear();
        if (Integer.parseInt(this.country) == 0 && this.verBarchartSelected ==true) {
          RequestContext.getCurrentInstance().execute("document.getElementById('div1').style.visibility = 'visible';");
            refreshChart(livePieModel);
        } else if (Integer.parseInt(this.country) == 1 && this.verBarchartSelected==true ) {
            RequestContext.getCurrentInstance().execute("document.getElementById('div1').style.visibility = 'visible';"); 
            refreshChart2(livePieModel);
        } else if (Integer.parseInt(this.country) == 2 && this.verBarchartSelected==true) {
            RequestContext.getCurrentInstance().execute("document.getElementById('div1').style.visibility = 'visible';");
            refreshChart3(livePieModel);
        } else {
            //Initalise Chart here
            livePieModel.getData().put("", null);
            RequestContext.getCurrentInstance().execute("document.getElementById('div1').style.visibility = 'hidden';");


        }
        return livePieModel;
    }

    public void refreshChart(  PieChartModel livePieModel) {
        int random1 = (int) (Math.random() * 1000);
        int random2 = (int) (Math.random() * 1000);

       livePieModel.getData().put("Candidate 1", random1);
        livePieModel.getData().put("Candidate 2", random2);

    
        livePieModel.setTitle("Votes");
        livePieModel.setLegendPosition("ne");

    }

    public void refreshChart2( PieChartModel livePieModel) {
        int random1 = (int) (50);
        int random2 = (int) (50);

        livePieModel.getData().put("Candidate 1", random1);
        livePieModel.getData().put("Candidate 2", random2);

        livePieModel.setTitle("Votes");
        livePieModel.setLegendPosition("se");

    }

    public void refreshChart3( PieChartModel livePieModel) {
        int random1 = (int) (10);
        int random2 = (int) (900);

        livePieModel.getData().put("Candidate 1", random1);
        livePieModel.getData().put("Candidate 2", random2);

        livePieModel.setTitle("Votes");
        livePieModel.setLegendPosition("sw");

    }

    public BarChartModel getBar() {
         BarChartModel barModel=new BarChartModel();
        if (Integer.parseInt(this.country) == 0 && this.verBarchartSelected ==true) {
              String[] labels = {"CHO1", "CHO2", "CHO3", "CHO4","CHO5","CHO6","CHO7","CHO8","CHO9"};
     // Object []countValues=DBQuery.NimsCounts();
      //  ChartSeries chos = ChartBuilder.GnenerateBarChartSeries(labels,countValues );
      //   barModel = ChartBuilder.BuildVerBarChart("Bar Chart", "", "Community Health Organisations", "No. of NIMS Incidents", chos, "2288AA",0,0,true);
       //RequestContext.getCurrentInstance().execute("document.getElementById('div2').style.visibility = 'visible';");
   
        }
        else {
            //Initalise Chart here
            ChartSeries boys = new ChartSeries();
        boys.set("", 0);

            barModel.addSeries(boys);
            RequestContext.getCurrentInstance().execute("document.getElementById('div2').style.visibility = 'hidden';");
        }

     
        return barModel;
    }
   
private BubbleChartModel bubbleModel1;
 
    @PostConstruct
    public void init() {
        createBubbleModels();
    }
 
    public BubbleChartModel getBubbleModel1() {
        return bubbleModel1;
    }
     

    private void createBubbleModels(){
        bubbleModel1 = initBubbleModel();
        bubbleModel1.setTitle("Bubble Chart");
        bubbleModel1.getAxis(AxisType.X).setLabel("Community Health Organisations (CHOs)");
       Axis xAxis = bubbleModel1.getAxis(AxisType.X);
     //  xAxis.setMin(0);
       //xAxis.setMax(10);
      // xAxis.setTickCount(1);
       //xAxis.setTickInterval("1");
       //xAxis.setTickFormat("CHO:%d");
        Axis yAxis = bubbleModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);
       // yAxis.setMax(250);
        yAxis.setLabel("No. of NIMS Incidents");
     
    }
     
    private BubbleChartModel initBubbleModel(){
        BubbleChartModel model = new BubbleChartModel();

model.setAnimate(true);
model.setShowDatatip(true);
        model.add(new BubbleChartSeries("CHO1", 1, 183,389048));
        model.add(new BubbleChartSeries("CHO2", 2, 92, 445356));
        model.add(new BubbleChartSeries("CHO3", 3, 104, 379327));
        model.add(new BubbleChartSeries("CHO4", 4, 123, 664533));
        model.add(new BubbleChartSeries("CHO5", 5, 89, 497578));
        model.add(new BubbleChartSeries("CHO6", 6, 180, 364464));
        model.add(new BubbleChartSeries("CHO7", 7, 70, 674071));
         
        return model;
    }
public BubbleChartModel getBubb()
{
     LineChartModel multiAxisModel;
    
   return null; 
}

}
