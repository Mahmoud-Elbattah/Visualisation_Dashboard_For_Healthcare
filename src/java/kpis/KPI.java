/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kpis;

import common.CommonObjects.KPIType;
import java.text.DecimalFormat;

/**
 *
 * @author 13232238
 */
public class KPI {

    public String dbQuery;
    public Integer totalCount;
    public Integer Score;
    public KPIType kpiType;

    public String getPercent() {
        if (Score!=0 && totalCount != 0) {
            Double value = (Score.doubleValue() / totalCount.doubleValue()) * 100.0;
            DecimalFormat df = new DecimalFormat("#.00");
            String percent = df.format(value) + "%";
            return percent;
        } else {
            return "0%";
        }

    }
    public KPI (KPIType kpiType){
        this.kpiType=kpiType;
    }

}
