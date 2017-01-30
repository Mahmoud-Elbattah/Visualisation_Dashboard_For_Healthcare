/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charts;

/**
 *
 * @author dxdiag
 */
public class ChartParams {

    public String ChartTitle;
    public String XAxisLabel;
    public String YAxisLabel;
    public String[] XValues;
    public Object[] YValues;
    public String PieColors="";
    public String LegendPosition="e";
    public boolean IsAnimate = true;
    public int XTickAngle = 0;
    public int YTickAngle = 0;
    public String VerSeriesColors= ThemeColors.DarkBlue;
    public String HorSeriesColors= ThemeColors.LightGreen;
}
