/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import entityclasses.Communityhealthcareorganisation;
import entityclasses.Hospitalgroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author 13232238
 */
public class CommonObjects {
    public  static String filterString="";
     static   public enum Scope {
        Global, CHO, CHOHospitalGroups,
        HospitalGroup,
        CommunityHospitals,
        CareHomes,
        PrimaryCareTeams,
        AcuteHospital,
        CommunityHospital,
        CareHome,
        PrimaryCareTeam
    };
        public  static enum KPIType{
        NIMS,
        IHFD
    };
         public static Object[] SetSelectedScope(String selectedOrganisation) {
        Object[] scope = new Object[2];
        if (selectedOrganisation.equals("root")) {
            scope[0] = CommonObjects.Scope.Global;
            scope[1] = selectedOrganisation;
        } else if (selectedOrganisation.startsWith("CHO", 0)) {
            scope[0] = CommonObjects.Scope.CHO;
            scope[1] = selectedOrganisation.replaceAll("CHO:", "");
        } else if (selectedOrganisation.startsWith("HospitalGroupsCHO", 0)) {
            scope[0] = CommonObjects.Scope.CHOHospitalGroups;
            scope[1] = selectedOrganisation.replaceAll("HospitalGroupsCHO:", "");
        } else if (selectedOrganisation.startsWith("HospGroup", 0)) {
            scope[0] = CommonObjects.Scope.HospitalGroup;
            scope[1] = selectedOrganisation.replaceAll("HospGroup:", "");
        } else if (selectedOrganisation.startsWith("CommunityHospitalsCHO", 0)) {
            scope[0] = CommonObjects.Scope.CommunityHospitals;
            scope[1] = selectedOrganisation.replaceAll("CommunityHospitalsCHO:", "");
        } else if (selectedOrganisation.startsWith("CareHomesCHO", 0)) {
            scope[0] = CommonObjects.Scope.CareHomes;
            scope[1] = selectedOrganisation.replaceAll("CareHomesCHO:", "");
        } 
        else if (selectedOrganisation.startsWith("AcuteHospital", 0)) {
            scope[0] = CommonObjects.Scope.AcuteHospital;
            scope[1] = selectedOrganisation.replaceAll("AcuteHospital:", "");
        }
   else if (selectedOrganisation.startsWith("CommunityHospital", 0)) {
            scope[0] = CommonObjects.Scope.CommunityHospital;
            scope[1] = selectedOrganisation.replaceAll("CommunityHospital:", "");
        }
      else if (selectedOrganisation.startsWith("Carehome", 0)) {
            scope[0] = CommonObjects.Scope.CareHome;
            scope[1] = selectedOrganisation.replaceAll("Carehome:", "");
        }
           else if (selectedOrganisation.startsWith("PCTsCHO", 0)) {
            scope[0] = CommonObjects.Scope.PrimaryCareTeams;
            scope[1] = selectedOrganisation.replaceAll("PCTsCHO:", "");
        }
       else if (selectedOrganisation.startsWith("PCT", 0)) {
            scope[0] = CommonObjects.Scope.PrimaryCareTeams;
            scope[1] = selectedOrganisation.replaceAll("PCT:", "");
        }
        else {
            return null;
        }
        return scope;
    }
             public static  String[] SelectedHospitalGroups(String selectedScopeID) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        Communityhealthcareorganisation selectedCHO = (Communityhealthcareorganisation) em.createNamedQuery("Communityhealthcareorganisation.findById").setParameter("id", Integer.parseInt(selectedScopeID)).getSingleResult();
        List<Hospitalgroup> hospitalGroups = (List<Hospitalgroup>) selectedCHO.getHospitalgroupCollection();
        String[] hospitalGroupNames = new String[hospitalGroups.size()];
        for (int i = 0; i < hospitalGroups.size(); i++) {
            hospitalGroupNames[i] = hospitalGroups.get(i).getName();
        }
        return hospitalGroupNames;
    }
        public static Hospitalgroup SelectedHospitalGroup(String selectedScopeID) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        Hospitalgroup selectedHospGroup = (Hospitalgroup) em.createNamedQuery("Hospitalgroup.findById").setParameter("id", Integer.parseInt(selectedScopeID)).getSingleResult();
        return selectedHospGroup;
    }
       private static java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }
    public static void GetFilterParams(String dataSource,boolean maleSelected, boolean femaleSelected,int lowerAge,int upperAge, Date dateFrom, Date dateTo) {
     ArrayList fitersParams = new ArrayList();
     filterString  = "";
        if (maleSelected == true && femaleSelected == false) {
            fitersParams.add(",gender=1");
        } else if (femaleSelected == true && maleSelected == false) {
            fitersParams.add(",gender=2");
        }
        if(lowerAge!=0){
            fitersParams.add(",age>="+lowerAge);
        }
        if(upperAge!=112){
          fitersParams.add(",age<="+upperAge);
        }
        if (dateFrom != null && dateTo != null) {
                String dateFromFormatted=sqlDate(dateFrom).toString();
                String dateToFormatted=sqlDate(dateTo).toString();
            if (dataSource.equals("NIMS")) {
                fitersParams.add(",dateOccurred between '" + dateFromFormatted + "' And '" + dateToFormatted + "'");
            } else if (dataSource.equals("IHFD")) {
                fitersParams.add(",admissionDate between '" + dateFromFormatted + "' And '" + dateToFormatted + "'");
            }
        }

        if (fitersParams.size() > 0) {
            filterString = Arrays.toString(fitersParams.toArray());
            filterString = filterString.replaceFirst(",", "");
            filterString = filterString.replaceAll("\\[", "");
            filterString = filterString.replaceAll("\\]", "");
            filterString = filterString.replaceAll(", ,", " And ");
        }

    }
public static String AddQueryFilter(String queryCmd)
{
    if(!filterString.equals("")){
        if(queryCmd.toLowerCase().contains("where")==true){
            queryCmd+=" And "+filterString;
        }
        else{
         queryCmd+=" where "+filterString;
        }
    }
        return queryCmd;
}
}
