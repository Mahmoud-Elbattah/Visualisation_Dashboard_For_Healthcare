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
public class IHFDKPIs {
 public KPI ihfdCasesCount;
 public KPI timetoAdmission;
 public KPI timetoPrimarySurgery;
 public KPI geriatricAssesed;
 public KPI fallSpecialistAssessed;
 public KPI multiAssessed;
 public KPI aged65;
 public KPI malePatients;
 public KPI femalePatients;
 public KPI transferredPatients;
 public KPI averageLOS;
    public IHFDKPIs() {
        //1st KPI: Count of hip fracture cases
        this.ihfdCasesCount = new KPI(KPIType.IHFD);
        this.ihfdCasesCount.dbQuery = "select (select count(*) from hfdrecord),count(*) from hfdrecord";
        //2nd KPI: All patients with hip fracture should be admitted to an acute orthopedic ward within 4 hours of presentation
        this.timetoAdmission = new KPI(KPIType.IHFD);
        this.timetoAdmission.dbQuery = "select count(*),IFNULL(sum(case when (EDDischargeTime-firstPresHospAdmTime) <=400 And  DATEDIFF(EDDischargeDate,firstPresHospAdmDate) =0 then 1 else 0 end),0) from hfdrecord";
        //3rd KPI: All patients with hip fracture who are medically fit should have surgery within 48 hours of admission, and during normal working hours
        this.timetoPrimarySurgery = new KPI(KPIType.IHFD);
        this.timetoPrimarySurgery.dbQuery = "select count(*),IFNULL(sum(case when  DATEDIFF(primarySurgeryDate,admissionDate)<=2 then 1 else 0 end),0) from hfdrecord";
        //4th KPI:All patients presenting with a fragility fracture should be managed on an orthopaedic ward with routine access to Geriatrics medical support from the time of admission
        this.geriatricAssesed = new KPI(KPIType.IHFD);
        this.geriatricAssesed.dbQuery = "select IFNULL(sum(case when fragilityHistory=1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory=1 And  geriatricAssessDate Is Not NULL then 1 else 0 end),0) from hfdrecord";
        //5th KPI:All patients presenting with fragility fractures should be assessed to determine their need for therapies to prevent future osteoporotic fractures
        this.fallSpecialistAssessed = new KPI(KPIType.IHFD);
        this.fallSpecialistAssessed.dbQuery = "select IFNULL(sum(case when fragilityHistory=1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory=1 And (specailistFallsAssess=1 or specailistFallsAssess=2) then 1 else 0 end),0) from hfdrecord";
        //6th KPI:All patients presenting with a fragility fracture following a fall should be offered multidisciplinary assessment and intervention to prevent future falls
        this.multiAssessed = new KPI(KPIType.IHFD);
        this.multiAssessed.dbQuery = "select IFNULL(sum(case when fragilityHistory=1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory=1 And multiRehabAssess=1 then 1 else 0 end),0) from hfdrecord";
        //7th KPI:All patients over 65 yrs presenting at ED
        this.aged65 = new KPI(KPIType.IHFD);
        this.aged65.dbQuery = "select count(*),IFNULL(sum(case when age>=65 then 1 else 0 end),0) from hfdrecord";
        //8th KPI:Male patients
        this.malePatients = new KPI(KPIType.IHFD);
        this.malePatients.dbQuery = "select count(*),IFNULL(sum(case when gender=1 then 1 else 0 end),0) from hfdrecord";
        //9th KPI:Female patients
        this.femalePatients = new KPI(KPIType.IHFD);
        this.femalePatients.dbQuery = "select count(*),IFNULL(sum(case when gender=2 then 1 else 0 end),0) from hfdrecord";
        //10th KPI:Transferred patients
        this.transferredPatients = new KPI(KPIType.IHFD);
        this.transferredPatients.dbQuery = "Select count(*),IFNULL(sum(case when (transferInHospital Is Not NULL and transferOutHospital Is  NULL) or (hfdrecord.transferOutHospital Is  Not NULL and hfdrecord.transferInHospital Is  NULL) then 1 else 0 end ),0) from hfdrecord";
        //11th KPI:Average Length of Stay
        this.averageLOS = new KPI(KPIType.IHFD);
        this.averageLOS.dbQuery = "select count(*),IFNULL(round(sum(lengthOfStay)/count(*)),0) from hfdrecord";
    }
}
