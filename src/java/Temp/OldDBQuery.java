/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Temp;

import charts.ChartSelector;
import common.CommonObjects;
import entityclasses.Carehome;
import entityclasses.Communityhospital;
import entityclasses.Hospital;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import common.CommonObjects.Scope;

public class OldDBQuery {
//Retrieves NIMS counts for all CHOs
    public static Object[] NimsCounts() {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("Select(Select count(*) from stfrecord where stfrecord.choArea=1) as cho1Count,(Select count(*) from stfrecord where stfrecord.choArea=2) as cho2Count,(Select count(*) from stfrecord where stfrecord.choArea=3) as cho3Count,(Select count(*) from stfrecord where stfrecord.choArea=4) as cho4Count,(Select count(*) from stfrecord where stfrecord.choArea=5) as cho5Count,(Select count(*) from stfrecord where stfrecord.choArea=6) as cho6Count,(Select count(*) from stfrecord where stfrecord.choArea=7) as cho7Count,(Select count(*) from stfrecord where stfrecord.choArea=8) as cho8Count,(Select count(*) from stfrecord where stfrecord.choArea=9) as cho9Count").getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    //Retrieves NIMS counts for a particular CHO
    public static Object[] NimsCounts(String choID) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("Select(Select count(*) from stfrecord where choArea=? and locationC=1) as incidentsAtHospitals,(Select count(*) from stfrecord where choArea=? and locationC=2) as incidentsAtCareHome,(Select count(*) from stfrecord where choArea=? and locationC=3) as incidentsAtCommHospital")
                .setParameter(1, choID)
                .setParameter(2, choID)
                .setParameter(3, choID)
                .getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NimsCounts(Integer lowerAge) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("Select (SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (stfrecord.choArea = 1) AND (agegroup.lower >= ?)) as CHO1ElderCount,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE     (stfrecord.choArea = 2) AND (agegroup.lower >= ?)) as CHO2ElderCount,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (stfrecord.choArea = 3) AND (agegroup.lower >= ?)) as CHO3ElderCount,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (stfrecord.choArea = 4) AND (agegroup.lower >= ?)) as CHO4ElderCount,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (stfrecord.choArea = 5) AND (agegroup.lower >= ?)) as CHO5ElderCount,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (stfrecord.choArea = 6) AND (agegroup.lower >= ?)) as CHO6ElderCount,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (stfrecord.choArea = 7) AND (agegroup.lower >= ?)) as CHO7ElderCount,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (stfrecord.choArea = 8) AND (agegroup.lower >= ?)) as CHO8ElderCount,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (stfrecord.choArea = 9) AND (agegroup.lower >= ?)) as CHO9ElderCount")
                .setParameter(1, lowerAge)
                .setParameter(1, lowerAge)
                .setParameter(2, lowerAge)
                .setParameter(3, lowerAge)
                .setParameter(4, lowerAge)
                .setParameter(5, lowerAge)
                .setParameter(6, lowerAge)
                .setParameter(7, lowerAge)
                .setParameter(8, lowerAge)
                .setParameter(9, lowerAge)
                .getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NimsCounts(String[] hospitalGroups) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "select";
        for (int i = 0; i < hospitalGroups.length; i++) {
            sqlCmd += "( SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id where hospitalgroup.name='" + hospitalGroups[i] + "') as " + "count" + i;
            if ((i + 1) < hospitalGroups.length) {
                sqlCmd += ",";
            }
        }
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();

        if (hospitalGroups.length == 1) {
            counts = new Object[1];
            counts[0] = (Object) summaryCounts.get(0);

        } else if (hospitalGroups.length > 1) {
            counts = (Object[]) summaryCounts.get(0);
        }

        return counts;
    }

    public static Object[] NimsCounts(Hospital[] hospitals) {

        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "select";
        for (int i = 0; i < hospitals.length; i++) {
            sqlCmd += "( SELECT count(*) FROM stfrecord where hospitalID=" + hospitals[i].getId() + ") as " + "count" + i;
            if ((i + 1) < hospitals.length) {
                sqlCmd += ",";
            }
        }
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();
        counts = (Object[]) summaryCounts.get(0);
        return counts;
    }

    public static Object[] NimsCounts(Communityhospital[] commHospitals) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "select";
        for (int i = 0; i < commHospitals.length; i++) {
            sqlCmd += "( SELECT count(*) FROM stfrecord where communityHospitalID=" + commHospitals[i].getId() + ") as " + "count" + i;
            if ((i + 1) < commHospitals.length) {
                sqlCmd += ",";
            }
        }
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();
        counts = (Object[]) summaryCounts.get(0);
        return counts;
    }

    public static Object[] NimsCounts(Carehome[] careHomes) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "select";
        for (int i = 0; i < careHomes.length; i++) {
            sqlCmd += "( SELECT count(*) FROM stfrecord where careHomeID=" + careHomes[i].getId() + ") as " + "count" + i;
            if ((i + 1) < careHomes.length) {
                sqlCmd += ",";
            }
        }
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();
        counts = (Object[]) summaryCounts.get(0);
        return counts;
    }

    public static Object[] CHO_Populations() {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List populationCounts = em.createNativeQuery("Select(Select Population from communityhealthcareorganisation where id=1) as CHO1Population,(Select Population from communityhealthcareorganisation where id=2) as CHO2Population,(Select Population from communityhealthcareorganisation where id=3) as CHO3Population,(Select Population from communityhealthcareorganisation where id=4) as CHO4Population,(Select Population from communityhealthcareorganisation where id=5) as CHO5Population,(Select Population from communityhealthcareorganisation where id=6) as CHO6Population,(Select Population from communityhealthcareorganisation where id=7) as CHO7Population,(Select Population from communityhealthcareorganisation where id=8) as CHO8Population,(Select Population from communityhealthcareorganisation where id=9) as CHO9Population").getResultList();
        if (populationCounts.size() > 0) {
            counts = (Object[]) populationCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSAgeStats() {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("Select (SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (agegroup.lower < 40)) as ageLt40,(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (agegroup.lower >= 40) and (agegroup.upper <= 44)) as age4044,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (agegroup.lower >= 45) and (agegroup.upper <= 49)) as age4549,(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (agegroup.lower >= 50) and (agegroup.upper <= 54)) as age5054,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (agegroup.lower >= 55) and (agegroup.upper <= 59)) as age5559,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (agegroup.lower >= 60) and (agegroup.upper <= 64)) as age6064,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE (agegroup.lower >= 65)) as ageGt65").getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSAgeStats(String choID, CommonObjects.Scope scope) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "";
        if (scope == CommonObjects.Scope.CHO) {
            sqlCmd = "Select (SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And (agegroup.lower < 40)) as ageLt40,(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And (agegroup.lower >= 40) and (agegroup.upper <= 44)) as age4044,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And (agegroup.lower >= 45) and (agegroup.upper <= 49)) as age4549,(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And (agegroup.lower >= 50) and (agegroup.upper <= 54)) as age5054,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And (agegroup.lower >= 55) and (agegroup.upper <= 59)) as age5559,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE  choArea=? And (agegroup.lower >= 60) and (agegroup.upper <= 64)) as age6064,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And (agegroup.lower >= 65))as ageGt65";
        } else if (scope == CommonObjects.Scope.CommunityHospitals) {
            sqlCmd = "Select (SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=3 And (agegroup.lower < 40)) as ageLt40,(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=3 And (agegroup.lower >= 40) and (agegroup.upper <= 44)) as age4044,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=3 And (agegroup.lower >= 45) and (agegroup.upper <= 49)) as age4549,(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=3 And (agegroup.lower >= 50) and (agegroup.upper <= 54)) as age5054,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=3 And (agegroup.lower >= 55) and (agegroup.upper <= 59)) as age5559,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE  choArea=? And stfrecord.locationC=3 And (agegroup.lower >= 60) and (agegroup.upper <= 64)) as age6064,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=3 And (agegroup.lower >= 65)) as ageGt65";
        } else if (scope == CommonObjects.Scope.CareHomes) {
            sqlCmd = "Select (SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=2 And (agegroup.lower < 40)) as ageLt40,(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=2 And (agegroup.lower >= 40) and (agegroup.upper <= 44)) as age4044,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=2 And (agegroup.lower >= 45) and (agegroup.upper <= 49)) as age4549,(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=2 And (agegroup.lower >= 50) and (agegroup.upper <= 54)) as age5054,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=2 And (agegroup.lower >= 55) and (agegroup.upper <= 59)) as age5559,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE  choArea=? And stfrecord.locationC=2 And (agegroup.lower >= 60) and (agegroup.upper <= 64)) as age6064,(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=? And stfrecord.locationC=2 And (agegroup.lower >= 65)) as ageGt65";
        }
        List summaryCounts = em.createNativeQuery(sqlCmd)
                .setParameter(1, choID)
                .setParameter(2, choID)
                .setParameter(3, choID)
                .setParameter(4, choID)
                .setParameter(5, choID)
                .setParameter(6, choID)
                .setParameter(7, choID)
                .getResultList();

        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSAgeStats(String[] hospitalGroups) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();

        String condition = "(";
        for (int i = 0; i < hospitalGroups.length; i++) {
            condition += "hospitalgroup.name='" + hospitalGroups[i] + "'";
            if ((i + 1) < hospitalGroups.length) {
                condition += " or ";
            }
        }
        condition += ")";
        String sqlCmd = "select (SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE (agegroup.lower < 40) And " + condition + " ) as ageLt40,"
                + "(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE (agegroup.lower >= 40) and (agegroup.upper <= 44) And " + condition + ") as age4044,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE (agegroup.lower >= 45) and (agegroup.upper <= 49) And " + condition + ") as age4549,"
                + "(SELECT count(*)FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE (agegroup.lower >= 50) and (agegroup.upper <= 54) And " + condition + ") as age5054,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE (agegroup.lower >= 55) and (agegroup.upper <= 59) And " + condition + ") as age5559,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE (agegroup.lower >= 60) and (agegroup.upper <= 64) And " + condition + ") as age6064,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE (agegroup.lower >= 65) And " + condition + ") as ageGt65";
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();
        counts = (Object[]) summaryCounts.get(0);

        return counts;
    }

    public static Object[] NIMSGenderStats() {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("Select(SELECT count(*) FROM stfrecord where stfrecord.gender=1) as malesCount, (SELECT count(*) FROM stfrecord where stfrecord.gender=2) as femalesCount").getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSGenderStats(String choID, CommonObjects.Scope scope) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "";
        if (scope == CommonObjects.Scope.CHO) {
            sqlCmd = "Select(SELECT count(*) FROM stfrecord where stfrecord.gender=1 And choArea=?) as malesCount, (SELECT count(*) FROM stfrecord where stfrecord.gender=2 And choArea=?) as femalesCount";
        } else if (scope == CommonObjects.Scope.CommunityHospitals) {
            sqlCmd = "Select(SELECT count(*) FROM stfrecord where gender=1 And choArea=? And locationC=3) as malesCount, (SELECT count(*) FROM stfrecord where gender=2 And choArea=? And locationC=3) as femalesCount";
        } else if (scope == CommonObjects.Scope.CareHomes) {
            sqlCmd = "Select(SELECT count(*) FROM stfrecord where gender=1 And choArea=? And locationC=2) as malesCount, (SELECT count(*) FROM stfrecord where gender=2 And choArea=? And locationC=2) as femalesCount";
        }
        List summaryCounts = em.createNativeQuery(sqlCmd)
                .setParameter(1, choID)
                .setParameter(2, choID)
                .getResultList();

        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSGenderStats(String[] hospitalGroups) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();

        String condition = "(";
        for (int i = 0; i < hospitalGroups.length; i++) {
            condition += "hospitalgroup.name='" + hospitalGroups[i] + "'";
            if ((i + 1) < hospitalGroups.length) {
                condition += " or ";
            }
        }
        condition += ")";
        String sqlCmd = "select (SELECT count(*)FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE gender=1 and " + condition + ") as malesCount,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE gender=2 And " + condition + ") as femalesCount";
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();
        counts = (Object[]) summaryCounts.get(0);

        return counts;
    }

    public static Object[] NIMSRecurrStats() {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("Select(Select count(*) from stfrecord where stfrecord.recurrLikelihood=4) as rareCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=5) as unlikelyCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=3) as possibleCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=2) as likelyCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=1) as certainCount").getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSRecurrStats(String choID, CommonObjects.Scope scope) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "";
        if (scope == CommonObjects.Scope.CHO) {
            sqlCmd = "Select(Select count(*) from stfrecord where stfrecord.recurrLikelihood=4 And choArea=?) as rareCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=5 And choArea=?) as unlikelyCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=3 And choArea=?) as possibleCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=2 And choArea=?) as likelyCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=1 And choArea=?) as certainCount";
        } else if (scope == CommonObjects.Scope.CommunityHospitals) {
            sqlCmd = "Select(Select count(*) from stfrecord where stfrecord.recurrLikelihood=4 And choArea=? And locationC=3) as rareCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=5 And choArea=? And locationC=3) as unlikelyCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=3 And choArea=? And locationC=3) as possibleCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=2 And choArea=? And locationC=3) as likelyCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=1 And choArea=? And locationC=3) as certainCount";
        } else if (scope == CommonObjects.Scope.CareHomes) {
            sqlCmd = "Select(Select count(*) from stfrecord where stfrecord.recurrLikelihood=4 And choArea=? And locationC=2) as rareCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=5 And choArea=? And locationC=2) as unlikelyCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=2 And choArea=? And locationC=2) as possibleCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=2 And choArea=? And locationC=2) as likelyCount,(Select count(*) from stfrecord where stfrecord.recurrLikelihood=1 And choArea=? And locationC=2) as certainCount";
        }
        List summaryCounts = em.createNativeQuery(sqlCmd)
                .setParameter(1, choID)
                .setParameter(2, choID)
                .setParameter(3, choID)
                .setParameter(4, choID)
                .setParameter(5, choID)
                .getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSRecurrStats(String[] hospitalGroups) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();

        String condition = "(";
        for (int i = 0; i < hospitalGroups.length; i++) {
            condition += "hospitalgroup.name='" + hospitalGroups[i] + "'";
            if ((i + 1) < hospitalGroups.length) {
                condition += " or ";
            }
        }
        condition += ")";
        String sqlCmd = "select (SELECT count(*)FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE recurrLikelihood=4 and " + condition + ") as rareCount,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE recurrLikelihood=5 And " + condition + ") as unlikelyCount,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE recurrLikelihood=3 And " + condition + ") as possibleCount,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE recurrLikelihood=2 And " + condition + ") as likelyCount,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE recurrLikelihood=1 And " + condition + ") as certainCount";
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();
        counts = (Object[]) summaryCounts.get(0);

        return counts;
    }

    public static Object[] NIMSOutcomeStats() {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("Select(Select count(*) from stfrecord where stfrecord.outcome=1) count1,(Select count(*) from stfrecord where stfrecord.outcome=2) count2,(Select count(*) from stfrecord where stfrecord.outcome=3) count3,(Select count(*) from stfrecord where stfrecord.outcome=4) count4,(Select count(*) from stfrecord where stfrecord.outcome=5) count5,(Select count(*) from stfrecord where stfrecord.outcome=6) count6,(Select count(*) from stfrecord where stfrecord.outcome=7) count7,(Select count(*) from stfrecord where stfrecord.outcome=8) count8,(Select count(*) from stfrecord where stfrecord.outcome=9) count9,(Select count(*) from stfrecord where stfrecord.outcome=10) count10,(Select count(*) from stfrecord where stfrecord.outcome=11) count11,(Select count(*) from stfrecord where stfrecord.outcome=12) count12,(Select count(*) from stfrecord where stfrecord.outcome=13) count13").getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSOutcomeStats(String choID, CommonObjects.Scope scope) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "";
        if (scope == CommonObjects.Scope.CHO) {
            sqlCmd = "Select(Select count(*) from stfrecord where stfrecord.outcome=1 And choArea=?) count1,(Select count(*) from stfrecord where stfrecord.outcome=2 And choArea=?) count2,(Select count(*) from stfrecord where stfrecord.outcome=3 And choArea=?) count3,(Select count(*) from stfrecord where stfrecord.outcome=4 And choArea=?) count4,(Select count(*) from stfrecord where stfrecord.outcome=5 And choArea=?) count5,(Select count(*) from stfrecord where stfrecord.outcome=6 And choArea=?) count6,(Select count(*) from stfrecord where stfrecord.outcome=7 And choArea=?) count7,(Select count(*) from stfrecord where stfrecord.outcome=8 And choArea=?) count8,(Select count(*) from stfrecord where stfrecord.outcome=9 And choArea=?) count9,(Select count(*) from stfrecord where stfrecord.outcome=10 And choArea=?) count10,(Select count(*) from stfrecord where stfrecord.outcome=11 And choArea=?) count11,(Select count(*) from stfrecord where stfrecord.outcome=12 And choArea=?) count12,(Select count(*) from stfrecord where stfrecord.outcome=13 And choArea=?) count13";
        } else if (scope == CommonObjects.Scope.CommunityHospitals) {
            sqlCmd = "Select(Select count(*) from stfrecord where stfrecord.outcome=1 And choArea=? And locationC=3) count1,(Select count(*) from stfrecord where stfrecord.outcome=2 And choArea=? And locationC=3) count2,(Select count(*) from stfrecord where stfrecord.outcome=3 And choArea=? And locationC=3) count3,(Select count(*) from stfrecord where stfrecord.outcome=4 And choArea=? And locationC=3) count4,(Select count(*) from stfrecord where stfrecord.outcome=5 And choArea=? And locationC=3) count5,(Select count(*) from stfrecord where stfrecord.outcome=6 And choArea=? And locationC=3) count6,(Select count(*) from stfrecord where stfrecord.outcome=7 And choArea=? And locationC=3) count7,(Select count(*) from stfrecord where stfrecord.outcome=8 And choArea=? And locationC=3) count8,(Select count(*) from stfrecord where stfrecord.outcome=9 And choArea=? And locationC=3) count9,(Select count(*) from stfrecord where stfrecord.outcome=10 And choArea=? And locationC=3) count10,(Select count(*) from stfrecord where stfrecord.outcome=11 And choArea=? And locationC=3) count11,(Select count(*) from stfrecord where stfrecord.outcome=12 And choArea=? And locationC=3) count12,(Select count(*) from stfrecord where stfrecord.outcome=13 And choArea=? And locationC=3) count13";
        } else if (scope == CommonObjects.Scope.CareHomes) {
            sqlCmd = "Select(Select count(*) from stfrecord where stfrecord.outcome=1 And choArea=? And locationC=2) count1,(Select count(*) from stfrecord where stfrecord.outcome=2 And choArea=? And locationC=2) count2,(Select count(*) from stfrecord where stfrecord.outcome=3 And choArea=? And locationC=2) count3,(Select count(*) from stfrecord where stfrecord.outcome=4 And choArea=? And locationC=2) count4,(Select count(*) from stfrecord where stfrecord.outcome=5 And choArea=? And locationC=2) count5,(Select count(*) from stfrecord where stfrecord.outcome=6 And choArea=? And locationC=2) count6,(Select count(*) from stfrecord where stfrecord.outcome=7 And choArea=? And locationC=2) count7,(Select count(*) from stfrecord where stfrecord.outcome=8 And choArea=? And locationC=2) count8,(Select count(*) from stfrecord where stfrecord.outcome=9 And choArea=? And locationC=2) count9,(Select count(*) from stfrecord where stfrecord.outcome=10 And choArea=? And locationC=2) count10,(Select count(*) from stfrecord where stfrecord.outcome=11 And choArea=? And locationC=2) count11,(Select count(*) from stfrecord where stfrecord.outcome=12 And choArea=? And locationC=2) count12,(Select count(*) from stfrecord where stfrecord.outcome=13 And choArea=? And locationC=2) count13";
        }
        List summaryCounts = em.createNativeQuery(sqlCmd)
                .setParameter(1, choID)
                .setParameter(2, choID)
                .setParameter(3, choID)
                .setParameter(4, choID)
                .setParameter(5, choID)
                .setParameter(6, choID)
                .setParameter(7, choID)
                .setParameter(8, choID)
                .setParameter(9, choID)
                .setParameter(10, choID)
                .setParameter(11, choID)
                .setParameter(12, choID)
                .setParameter(13, choID)
                .getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSOutcomeStats(String[] hospitalGroups) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String condition = "(";
        for (int i = 0; i < hospitalGroups.length; i++) {
            condition += "hospitalgroup.name='" + hospitalGroups[i] + "'";
            if ((i + 1) < hospitalGroups.length) {
                condition += " or ";
            }
        }
        condition += ")";
        String sqlCmd = "select (SELECT count(*)FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=1 and " + condition + ") as count1,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=2 And " + condition + ") as count2,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=3 And " + condition + ") as count3,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=4 And " + condition + ") as count4,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=5 And " + condition + ") as count5,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=6 And " + condition + ") as count6,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=7 And " + condition + ") as count7,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=8 And " + condition + ") as count8,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=9 And " + condition + ") as count9,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=10 And " + condition + ") as count10,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=11 And " + condition + ") as count11,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=12 And " + condition + ") as count12,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE outcome=13 And " + condition + ") as count13";
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();
        counts = (Object[]) summaryCounts.get(0);

        return counts;
    }

    public static Object[] NIMSSeverityStats() {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("Select(Select count(*) from stfrecord where severity=1) count1,(Select count(*) from stfrecord where severity=2) count2,(Select count(*) from stfrecord where severity=3) count3,(Select count(*) from stfrecord where severity=4) count4,(Select count(*) from stfrecord where severity=5) count5").getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSSeverityStats(String choID, CommonObjects.Scope scope) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "";
        if (scope == CommonObjects.Scope.CHO) {
            sqlCmd = "Select(Select count(*) from stfrecord where severity=1 And choArea=?) count1,(Select count(*) from stfrecord where severity=2 And choArea=?) count2,(Select count(*) from stfrecord where severity=3 And choArea=?) count3,(Select count(*) from stfrecord where severity=4 And choArea=?) count4,(Select count(*) from stfrecord where severity=5 And choArea=?) count5";
        } else if (scope == CommonObjects.Scope.CommunityHospitals) {
            sqlCmd = "Select(Select count(*) from stfrecord where severity=1 And choArea=? And locationC=3) count1,(Select count(*) from stfrecord where severity=2 And choArea=? And locationC=3) count2,(Select count(*) from stfrecord where severity=3 And choArea=? And locationC=3) count3,(Select count(*) from stfrecord where severity=4 And choArea=? And locationC=3) count4,(Select count(*) from stfrecord where severity=5 And choArea=? And locationC=3) count5";
        } else if (scope == CommonObjects.Scope.CareHomes) {
            sqlCmd = "Select(Select count(*) from stfrecord where severity=1 And choArea=? And locationC=2) count1,(Select count(*) from stfrecord where severity=2 And choArea=? And locationC=2) count2,(Select count(*) from stfrecord where severity=3 And choArea=? And locationC=2) count3,(Select count(*) from stfrecord where severity=4 And choArea=? And locationC=2) count4,(Select count(*) from stfrecord where severity=5 And choArea=? And locationC=2) count5";
        }
        List summaryCounts = em.createNativeQuery(sqlCmd)
                .setParameter(1, choID)
                .setParameter(2, choID)
                .setParameter(3, choID)
                .setParameter(4, choID)
                .setParameter(5, choID)
                .getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSSeverityStats(String[] hospitalGroups) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String condition = "(";
        for (int i = 0; i < hospitalGroups.length; i++) {
            condition += "hospitalgroup.name='" + hospitalGroups[i] + "'";
            if ((i + 1) < hospitalGroups.length) {
                condition += " or ";
            }
        }
        condition += ")";
        String sqlCmd = "select (SELECT count(*)FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE severity=1 and " + condition + ") as count1,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE severity=2 And " + condition + ") as count2,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE severity=3 And " + condition + ") as count3,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE severity=4 And " + condition + ") as count4,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE severity=5 And " + condition + ") as count5";
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();
        counts = (Object[]) summaryCounts.get(0);
        return counts;
    }

    public static Object[] NIMSRiskStats() {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("Select(Select count(*) from stfrecord where risk=3) as count1,(Select count(*) from stfrecord where risk=2) as count2,(Select count(*) from stfrecord where risk=1) as count3").getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSRiskStats(String choID, CommonObjects.Scope scope) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String sqlCmd = "";
        if (scope == CommonObjects.Scope.CHO) {
            sqlCmd = "Select(Select count(*) from stfrecord where risk=3 And choArea=?) as count1,(Select count(*) from stfrecord where risk=2 And choArea=?) as count2,(Select count(*) from stfrecord where risk=1 And choArea=?) as count3";
        } else if (scope == CommonObjects.Scope.CommunityHospitals) {
            sqlCmd = "Select(Select count(*) from stfrecord where risk=3 And choArea=? And locationC=3) as count1,(Select count(*) from stfrecord where risk=2 And choArea=? And locationC=3) as count2,(Select count(*) from stfrecord where risk=1 And choArea=? And locationC=3) as count3";
        } else if (scope == CommonObjects.Scope.CareHomes) {
            sqlCmd = "Select(Select count(*) from stfrecord where risk=3 And choArea=? And locationC=2) as count1,(Select count(*) from stfrecord where risk=2 And choArea=? And locationC=2) as count2,(Select count(*) from stfrecord where risk=1 And choArea=? And locationC=2) as count3";
        }
        List summaryCounts = em.createNativeQuery(sqlCmd)
                .setParameter(1, choID)
                .setParameter(2, choID)
                .setParameter(3, choID)
                .getResultList();
        if (summaryCounts.size() > 0) {
            counts = (Object[]) summaryCounts.get(0);
        }
        return counts;
    }

    public static Object[] NIMSRiskStats(String[] hospitalGroups) {
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String condition = "(";
        for (int i = 0; i < hospitalGroups.length; i++) {
            condition += "hospitalgroup.name='" + hospitalGroups[i] + "'";
            if ((i + 1) < hospitalGroups.length) {
                condition += " or ";
            }
        }
        condition += ")";
        String sqlCmd = "select (SELECT count(*)FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE risk=3 and " + condition + ") as count1,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE risk=2 And " + condition + ") as count2,"
                + "(SELECT count(*) FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id WHERE risk=1 And " + condition + ") as count3";
        List summaryCounts = em.createNativeQuery(sqlCmd).getResultList();
        counts = (Object[]) summaryCounts.get(0);
        return counts;
    }
}