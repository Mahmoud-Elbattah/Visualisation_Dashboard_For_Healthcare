/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kpis;

import common.CommonObjects.KPIType;

/**
 *
 * @author 13232238
 */
public class NIMSKPIs {
     public KPI nimsIncidentsCount;
     public KPI malePatients;
     public KPI femalePatients;
     public KPI incidentsAtAcute;
     public KPI incidentsAtCommunity;
     public KPI incidentsAtNursing;
     public KPI aged65;
     public KPI extremeSeverity;
     public KPI certainRecurr;
    public NIMSKPIs() {
        //1st KPI: Count of NIMS
        this.nimsIncidentsCount = new KPI(KPIType.NIMS);
        this.nimsIncidentsCount.dbQuery = "select (select count(*) from stfrecord),count(*) from stfrecord";
        //2nd KPI:Count of male patients
        this.malePatients = new KPI(KPIType.NIMS);
        this.malePatients.dbQuery = "Select count(*),IFNULL(sum(case when gender=1 then 1 else 0 end),0) from stfrecord";
        //3rd KPI:Count of female patients
        this.femalePatients = new KPI(KPIType.NIMS);
        this.femalePatients.dbQuery = "Select count(*),IFNULL(sum(case when gender=2 then 1 else 0 end),0) from stfrecord";
        //4th KPI:Count of incidents that happened at acute hospitals
        this.incidentsAtAcute = new KPI(KPIType.NIMS);
        this.incidentsAtAcute.dbQuery = "Select count(*),IFNULL(sum(case when locationC=1 then 1 else 0 end),0) from stfrecord";
        //5th KPI:Count of incidents that happened at community hospitals
        this.incidentsAtCommunity = new KPI(KPIType.NIMS);
        this.incidentsAtCommunity.dbQuery = "Select count(*),IFNULL(sum(case when locationC=2 then 1 else 0 end),0) from stfrecord";
        //6th KPI:Count of incidents that happened at nursing homes
        this.incidentsAtNursing = new KPI(KPIType.NIMS);
        this.incidentsAtNursing.dbQuery = "Select count(*),IFNULL(sum(case when locationC=3 then 1 else 0 end),0) from stfrecord";
        //7th KPI: Count of Patients aged >=65
         this.aged65 = new KPI(KPIType.NIMS);
        this.aged65.dbQuery = "Select count(*),IFNULL(sum(case when age>=65 then 1 else 0 end),0) from stfrecord";
        //8th KPI: Count of incidents with  extreme severity
        this.extremeSeverity = new KPI(KPIType.NIMS);
        this.extremeSeverity.dbQuery = "Select count(*),IFNULL(sum(case when severity=4 then 1 else 0 end),0) from stfrecord"; 
        //9th KPI: Count of patients with almost certain recurrence
        this.certainRecurr = new KPI(KPIType.NIMS);
        this.certainRecurr.dbQuery = "Select count(*),IFNULL(sum(case when recurrLikelihood=1 then 1 else 0 end),0) from stfrecord"; 
    }

     
     
}
