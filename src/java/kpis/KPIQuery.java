/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kpis;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import common.CommonObjects;
import common.CommonObjects.KPIType;
import entityclasses.Hospitalgroup;
/**
 *
 * @author 13232238
 */
public class KPIQuery {
    private static KPI ComuputeKPI(String selectedOrganisation, KPI kpi) {
        Object[] scope = CommonObjects.SetSelectedScope(selectedOrganisation);
        CommonObjects.Scope selecetedScope = (CommonObjects.Scope) scope[0];
        String selectedScopeID = scope[1].toString();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        switch (selecetedScope) {
            case Global:
                break;
            case CHO:
                //choArea
                if (kpi.kpiType == KPIType.IHFD) {
                    kpi.dbQuery += " INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id"
                            + " WHERE communityhealthcareorganisation.id = " + selectedScopeID;
                } else if (kpi.kpiType == KPIType.NIMS) {
                    kpi.dbQuery += " WHERE choArea = " + selectedScopeID;
                }
                break;
            case CHOHospitalGroups:
                String[] hospitalGroupNames = CommonObjects.SelectedHospitalGroups(selectedScopeID);
                String condition = "(";
                for (int i = 0; i < hospitalGroupNames.length; i++) {
                    condition += "hospitalgroup.name='" + hospitalGroupNames[i].toString() + "'";
                    if ((i + 1) < hospitalGroupNames.length) {
                        condition += " or ";
                    }
                }
                condition += ")";
                if (kpi.kpiType == KPIType.IHFD) {
                    kpi.dbQuery += " INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id"
                            + " WHERE " + condition;
                } else if (kpi.kpiType == KPIType.NIMS) {
                    kpi.dbQuery += " INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id"
                            + " WHERE " + condition;
                }
                break;
            case HospitalGroup:
                Hospitalgroup selectedHospGroup = CommonObjects.SelectedHospitalGroup(selectedScopeID);
                if (kpi.kpiType == KPIType.IHFD) {
                    kpi.dbQuery += " INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id"
                            + " WHERE  hospitalgroup.name='" + selectedHospGroup.getName() + "'";
                }
                else if (kpi.kpiType == KPIType.NIMS) {
               kpi.dbQuery += " INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id"
                            + " WHERE  hospitalgroup.name='" + selectedHospGroup.getName() + "'";    
                }

                break;
            case AcuteHospital:
                if (kpi.kpiType == KPIType.IHFD) {
                    kpi.dbQuery += " INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                            + " where hospital.id=" + selectedScopeID;
                } else if (kpi.kpiType == KPIType.NIMS) {
                    kpi.dbQuery += " where stfrecord.hospitalID=" + selectedScopeID;

                }
                break;
            case CommunityHospitals:
            case CareHomes:
            case CommunityHospital:
            case CareHome:
            case PrimaryCareTeams:
            case PrimaryCareTeam:
                kpi.totalCount = 0;
                kpi.Score = 0;
                return kpi;
        }
        kpi.dbQuery = CommonObjects.AddQueryFilter(kpi.dbQuery);
        //Executing 1st KPI query
        List queryResult = em.createNativeQuery(kpi.dbQuery).getResultList();
        Object[] counts = (Object[]) queryResult.get(0);
        //counts
        kpi.totalCount = Integer.parseInt(counts[0].toString());
        kpi.Score = Integer.parseInt(counts[1].toString());
        return kpi;
    }

    public static IHFDKPIs ComputeIHFDKPIS(String selectedOrganisation) {
        IHFDKPIs ihfdKPIs = new IHFDKPIs();
        ihfdKPIs.ihfdCasesCount = ComuputeKPI(selectedOrganisation, ihfdKPIs.ihfdCasesCount);
        ihfdKPIs.timetoAdmission = ComuputeKPI(selectedOrganisation, ihfdKPIs.timetoAdmission);
        ihfdKPIs.timetoPrimarySurgery = ComuputeKPI(selectedOrganisation, ihfdKPIs.timetoPrimarySurgery);
        ihfdKPIs.geriatricAssesed = ComuputeKPI(selectedOrganisation, ihfdKPIs.geriatricAssesed);
        ihfdKPIs.fallSpecialistAssessed = ComuputeKPI(selectedOrganisation, ihfdKPIs.fallSpecialistAssessed);
        ihfdKPIs.multiAssessed = ComuputeKPI(selectedOrganisation, ihfdKPIs.multiAssessed);
        ihfdKPIs.aged65 = ComuputeKPI(selectedOrganisation, ihfdKPIs.aged65);
        ihfdKPIs.malePatients = ComuputeKPI(selectedOrganisation, ihfdKPIs.malePatients);
        ihfdKPIs.femalePatients = ComuputeKPI(selectedOrganisation, ihfdKPIs.femalePatients);
        ihfdKPIs.transferredPatients = ComuputeKPI(selectedOrganisation, ihfdKPIs.transferredPatients);
        ihfdKPIs.averageLOS=ComuputeKPI(selectedOrganisation, ihfdKPIs.averageLOS);
        return ihfdKPIs;
    }

    public static NIMSKPIs ComputeNIMSKPIS(String selectedOrganisation) {
        NIMSKPIs nimsKPIs = new NIMSKPIs();
        nimsKPIs.nimsIncidentsCount = ComuputeKPI(selectedOrganisation, nimsKPIs.nimsIncidentsCount);
        nimsKPIs.malePatients = ComuputeKPI(selectedOrganisation, nimsKPIs.malePatients);
        nimsKPIs.femalePatients = ComuputeKPI(selectedOrganisation, nimsKPIs.femalePatients);
        nimsKPIs.incidentsAtAcute = ComuputeKPI(selectedOrganisation, nimsKPIs.incidentsAtAcute);
        nimsKPIs.incidentsAtCommunity = ComuputeKPI(selectedOrganisation, nimsKPIs.incidentsAtCommunity);
        nimsKPIs.incidentsAtNursing = ComuputeKPI(selectedOrganisation, nimsKPIs.incidentsAtNursing);
        nimsKPIs.aged65 = ComuputeKPI(selectedOrganisation, nimsKPIs.aged65);
        nimsKPIs.extremeSeverity = ComuputeKPI(selectedOrganisation, nimsKPIs.extremeSeverity);
        nimsKPIs.certainRecurr = ComuputeKPI(selectedOrganisation, nimsKPIs.certainRecurr);
        return nimsKPIs;
    }

}
