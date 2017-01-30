/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facelets;

import common.CommonObjects;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import kpis.IHFDKPIs;
import kpis.KPIQuery;
import kpis.NIMSKPIs;

/**
 *
 * @author 13232238
 */
@ManagedBean
@SessionScoped
public class KPIsViewer {
//IHFD KPIs-----------------------------------------------------
private Integer ihfdCasesTotal = 0;
private Integer ihfdCasesHits = 0;
private String  ihfdCasesPercent= "0";

private Integer timetoAdmissionTotal = 0;
private Integer timetoAdmissionHits = 0;
private String timetoAdmissionPercent= "0";

private Integer timetoSurgeryTotal=0;
private Integer timetoSurgeryHits = 0;
private String timetoSurgeryPercent= "0";

private Integer geriatricAssesedTotal=0;
private Integer geriatricAssesedHits = 0;
private String geriatricAssesedPercent= "0";

private Integer fallSpecialistTotal=0;
private Integer fallSpecialistHits = 0;
private String fallSpecialistPercent= "0";

private Integer multiAssessedTotal=0;
private Integer multiAssessedHits = 0;
private String multiAssessedPercent= "0";

private Integer aged65Total=0;
private Integer aged65Hits = 0;
private String aged65Percent= "0";

private Integer malePatientsTotal=0;
private Integer malePatientsHits = 0;
private String malePatientsPercent= "0";

private Integer femalePatientsTotal=0;
private Integer femalePatientsHits = 0;
private String femalePatientsPercent= "0";

private Integer transferredPatientsTotal=0;
private Integer transferredPatientsHits = 0;
private String  transferredPatientsPercent= "0";

private Integer averageLOS=0;

//NIMS KPIs-----------------------------------------------------
private Integer nimsCountTotal=0;
private Integer nimsCountHits=0;
private String nimsCountPercent="0";

private Integer nimsMaleTotal = 0;
private Integer nimsMaleHits = 0;
private String  nimsMalePercent= "0";

private Integer nimsFemaleTotal = 0;
private Integer nimsFemaleHits = 0;
private String  nimsFemalePercent= "0";

private Integer nimsAcuteTotal = 0;
private Integer nimsAcuteHits = 0;
private String  nimsAcutePercent= "0";

private Integer nimsCommunityTotal = 0;
private Integer nimsCommunityHits = 0;
private String  nimsCommunityPercent= "0";

private Integer nimsNursingTotal = 0;
private Integer nimsNursingHits = 0;
private String  nimsNursingPercent= "0";

private Integer nimsAged65Total= 0;
private Integer nimsAged65Hits = 0;
private String  nimsAged65Percent= "0";

private Integer nimsExtremeSeverityTotal= 0;
private Integer nimsExtremeSeverityHits = 0;
private String  nimsExtremeSeverityPercent= "0";

private Integer nimsCertainRecurrTotal= 0;
private Integer nimsCertainRecurrHits = 0;
private String  nimsCertainRecurrPercent= "0";

    public Integer getIhfdCasesTotal() {
        return ihfdCasesTotal;
    }

    public Integer getIhfdCasesHits() {
        return ihfdCasesHits;
    }

    public String getIhfdCasesPercent() {
        return ihfdCasesPercent;
    }

    public Integer getTimetoAdmissionHits() {
        return timetoAdmissionHits;
    }

    public String getTimetoAdmissionPercent() {
        return timetoAdmissionPercent;
    }

    public Integer getTimetoSurgeryHits() {
        return timetoSurgeryHits;
    }

    public String getTimetoSurgeryPercent() {
        return timetoSurgeryPercent ;
    }

    public Integer getGeriatricAssesedHits() {
        return geriatricAssesedHits;
    }

    public String getGeriatricAssesedPercent() {
        return geriatricAssesedPercent;
    }

    public Integer getTimetoAdmissionTotal() {
        return timetoAdmissionTotal;
    }

    public Integer getTimetoSurgeryTotal() {
        return timetoSurgeryTotal;
    }

    public Integer getGeriatricAssesedTotal() {
        return geriatricAssesedTotal;
    }

    public Integer getFallSpecialistTotal() {
        return fallSpecialistTotal;
    }

    public Integer getFallSpecialistHits() {
        return fallSpecialistHits;
    }

    public String getFallSpecialistPercent() {
        return fallSpecialistPercent;
    }

    public Integer getMultiAssessedTotal() {
        return multiAssessedTotal;
    }

    public Integer getMultiAssessedHits() {
        return multiAssessedHits;
    }

    public String getMultiAssessedPercent() {
        return multiAssessedPercent;
    }

    public Integer getAged65Total() {
        return aged65Total;
    }

    public Integer getAged65Hits() {
        return aged65Hits;
    }

    public String getAged65Percent() {
        return aged65Percent;
    }

    public Integer getMalePatientsTotal() {
        return malePatientsTotal;
    }

    public Integer getMalePatientsHits() {
        return malePatientsHits;
    }

    public String getMalePatientsPercent() {
        return malePatientsPercent;
    }

    public Integer getFemalePatientsTotal() {
        return femalePatientsTotal;
    }

    public Integer getFemalePatientsHits() {
        return femalePatientsHits;
    }

    public String getFemalePatientsPercent() {
        return femalePatientsPercent;
    }

    public Integer getTransferredPatientsTotal() {
        return transferredPatientsTotal;
    }

    public Integer getTransferredPatientsHits() {
        return transferredPatientsHits;
    }

    public String getTransferredPatientsPercent() {
        return transferredPatientsPercent;
    }

    public Integer getAverageLOS() {
        return averageLOS;
    }

    public Integer getNimsCountTotal() {
        return nimsCountTotal;
    }

    public Integer getNimsCountHits() {
        return nimsCountHits;
    }

    public String getNimsCountPercent() {
        return nimsCountPercent;
    }

    public Integer getNimsMaleTotal() {
        return nimsMaleTotal;
    }

    public Integer getNimsMaleHits() {
        return nimsMaleHits;
    }

    public String getNimsMalePercent() {
        return nimsMalePercent;
    }

    public Integer getNimsFemaleTotal() {
        return nimsFemaleTotal;
    }

    public Integer getNimsFemaleHits() {
        return nimsFemaleHits;
    }

    public String getNimsFemalePercent() {
        return nimsFemalePercent;
    }

    public Integer getNimsAcuteTotal() {
        return nimsAcuteTotal;
    }

    public Integer getNimsAcuteHits() {
        return nimsAcuteHits;
    }

    public String getNimsAcutePercent() {
        return nimsAcutePercent;
    }

    public Integer getNimsCommunityTotal() {
        return nimsCommunityTotal;
    }

    public Integer getNimsCommunityHits() {
        return nimsCommunityHits;
    }

    public String getNimsCommunityPercent() {
        return nimsCommunityPercent;
    }

    public Integer getNimsNursingTotal() {
        return nimsNursingTotal;
    }

    public Integer getNimsNursingHits() {
        return nimsNursingHits;
    }

    public String getNimsNursingPercent() {
        return nimsNursingPercent;
    }

    public Integer getNimsAged65Total() {
        return nimsAged65Total;
    }

    public Integer getNimsAged65Hits() {
        return nimsAged65Hits;
    }

    public String getNimsAged65Percent() {
        return nimsAged65Percent;
    }

    public Integer getNimsExtremeSeverityTotal() {
        return nimsExtremeSeverityTotal;
    }

    public Integer getNimsExtremeSeverityHits() {
        return nimsExtremeSeverityHits;
    }

    public String getNimsExtremeSeverityPercent() {
        return nimsExtremeSeverityPercent;
    }

    public Integer getNimsCertainRecurrTotal() {
        return nimsCertainRecurrTotal;
    }

    public Integer getNimsCertainRecurrHits() {
        return nimsCertainRecurrHits;
    }

    public String getNimsCertainRecurrPercent() {
        return nimsCertainRecurrPercent;
    }


    public KPIsViewer() {
    }
    public void ViewKPI() {
       
        //Getting the current selected health organisation
        FacesContext context = FacesContext.getCurrentInstance();
        String selectedOrganization = context.getApplication().evaluateExpressionGet(context, "#{chartViewer.selectedOrganization}", String.class);

        //Getting the selected filter options
        boolean maleSelected = context.getApplication().evaluateExpressionGet(context, "#{chartViewer.maleSelected}", boolean.class);
        boolean femaleSelected = context.getApplication().evaluateExpressionGet(context, "#{chartViewer.femaleSelected}", boolean.class);
        int lowerAge = context.getApplication().evaluateExpressionGet(context, "#{chartViewer.lowerAge}", int.class);
        int upperAge = context.getApplication().evaluateExpressionGet(context, "#{chartViewer.upperAge}", int.class);
        java.util.Date dateFrom = context.getApplication().evaluateExpressionGet(context, "#{chartViewer.dateFrom}", java.util.Date.class);
        java.util.Date dateTo = context.getApplication().evaluateExpressionGet(context, "#{chartViewer.dateTo}", java.util.Date.class);
        //Computing IHFD KPIs
        CommonObjects.GetFilterParams("IHFD", maleSelected, femaleSelected, lowerAge, upperAge, dateFrom, dateTo);

        IHFDKPIs ihfdKPIs = KPIQuery.ComputeIHFDKPIS(selectedOrganization);
        //Computing NIMs KPIs
        CommonObjects.GetFilterParams("NIMS", maleSelected, femaleSelected, lowerAge, upperAge, dateFrom, dateTo);
        NIMSKPIs nimsKPIs =KPIQuery.ComputeNIMSKPIS(selectedOrganization);

        //Setting IHFD KPIs values   
        this.ihfdCasesTotal = ihfdKPIs.ihfdCasesCount.totalCount;
        this.ihfdCasesHits = ihfdKPIs.ihfdCasesCount.Score;
        this.ihfdCasesPercent = ihfdKPIs.ihfdCasesCount.getPercent();     
        //1st KPI
        this.timetoAdmissionTotal = ihfdKPIs.timetoAdmission.totalCount;
        this.timetoAdmissionHits = ihfdKPIs.timetoAdmission.Score;
        this.timetoAdmissionPercent = ihfdKPIs.timetoAdmission.getPercent();
        //2nd KPI
        this.timetoSurgeryTotal = ihfdKPIs.timetoPrimarySurgery.totalCount;
        this.timetoSurgeryHits = ihfdKPIs.timetoPrimarySurgery.Score;
        this.timetoSurgeryPercent = ihfdKPIs.timetoPrimarySurgery.getPercent();
        //3rd KPI
        this.geriatricAssesedTotal = ihfdKPIs.geriatricAssesed.totalCount;
        this.geriatricAssesedHits = ihfdKPIs.geriatricAssesed.Score;
        this.geriatricAssesedPercent = ihfdKPIs.geriatricAssesed.getPercent();
        //4th KPI
        this.fallSpecialistTotal = ihfdKPIs.fallSpecialistAssessed.totalCount;
        this.fallSpecialistHits = ihfdKPIs.fallSpecialistAssessed.Score;
        this.fallSpecialistPercent = ihfdKPIs.fallSpecialistAssessed.getPercent();
        //5th KPI
        this.multiAssessedTotal = ihfdKPIs.multiAssessed.totalCount;
        this.multiAssessedHits = ihfdKPIs.multiAssessed.Score;
        this.multiAssessedPercent = ihfdKPIs.multiAssessed.getPercent();
        //6th KPI
        this.aged65Total = ihfdKPIs.aged65.totalCount;
        this.aged65Hits = ihfdKPIs.aged65.Score;
        this.aged65Percent = ihfdKPIs.aged65.getPercent();
        //7th KPI
        this.malePatientsTotal = ihfdKPIs.malePatients.totalCount;
        this.malePatientsHits = ihfdKPIs.malePatients.Score;
        this.malePatientsPercent = ihfdKPIs.malePatients.getPercent();
        //8th KPI
        this.femalePatientsTotal = ihfdKPIs.femalePatients.totalCount;
        this.femalePatientsHits = ihfdKPIs.femalePatients.Score;
        this.femalePatientsPercent = ihfdKPIs.femalePatients.getPercent();
        //9th KPI
        this.transferredPatientsTotal = ihfdKPIs.transferredPatients.totalCount;
        this.transferredPatientsHits = ihfdKPIs.transferredPatients.Score;
        this.transferredPatientsPercent = ihfdKPIs.transferredPatients.getPercent();
        //10th KPI
       this.averageLOS=ihfdKPIs.averageLOS.Score;
       
        //Setting NIMS KPIs values
        //1st NIMS KPI
        this.nimsCountTotal = nimsKPIs.nimsIncidentsCount.totalCount;
        this.nimsCountHits = nimsKPIs.nimsIncidentsCount.Score;
        this.nimsCountPercent = nimsKPIs.nimsIncidentsCount.getPercent();
        //2nd NIMS KPI
        this.nimsMaleTotal = nimsKPIs.malePatients.totalCount;
        this.nimsMaleHits = nimsKPIs.malePatients.Score;
        this.nimsMalePercent = nimsKPIs.malePatients.getPercent();
        //3nd NIMS KPI
        this.nimsFemaleTotal = nimsKPIs.femalePatients.totalCount;
        this.nimsFemaleHits = nimsKPIs.femalePatients.Score;
        this.nimsFemalePercent = nimsKPIs.femalePatients.getPercent();
        //4th NIMS KPI
        this.nimsAcuteTotal = nimsKPIs.incidentsAtAcute.totalCount;
        this.nimsAcuteHits = nimsKPIs.incidentsAtAcute.Score;
        this.nimsAcutePercent = nimsKPIs.incidentsAtAcute.getPercent();
        //5th NIMS KPI
        this.nimsCommunityTotal = nimsKPIs.incidentsAtCommunity.totalCount;
        this.nimsCommunityHits = nimsKPIs.incidentsAtCommunity.Score;
        this.nimsCommunityPercent = nimsKPIs.incidentsAtCommunity.getPercent();
        //6th NIMS KPI
        this.nimsNursingTotal = nimsKPIs.incidentsAtNursing.totalCount;
        this.nimsNursingHits = nimsKPIs.incidentsAtNursing.Score;
        this.nimsNursingPercent = nimsKPIs.incidentsAtNursing.getPercent();
        //7th NIMS KPI
        this.nimsAged65Total = nimsKPIs.aged65.totalCount;
        this.nimsAged65Hits = nimsKPIs.aged65.Score;
        this.nimsAged65Percent = nimsKPIs.aged65.getPercent();
        //8th NIMS KPI
        this.nimsExtremeSeverityTotal = nimsKPIs.extremeSeverity.totalCount;
        this.nimsExtremeSeverityHits = nimsKPIs.extremeSeverity.Score;
        this.nimsExtremeSeverityPercent = nimsKPIs.extremeSeverity.getPercent();
        //9th NIMS KPI
        this.nimsCertainRecurrTotal = nimsKPIs.certainRecurr.totalCount;
        this.nimsCertainRecurrHits = nimsKPIs.certainRecurr.Score;
        this.nimsCertainRecurrPercent = nimsKPIs.certainRecurr.getPercent();
    }
    
}
