package charts;
import entityclasses.Carehome;
import entityclasses.Communityhospital;
import entityclasses.Hospital;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import common.CommonObjects;
public class ChartDBQuery {
    public static Object[] ExecuteQuery(String targetChart, Object[] scope, Object... xValues) {
        CommonObjects.Scope selecetedScope = (CommonObjects.Scope) scope[0];
        String selectedScopeID = scope[1].toString();
        Object[] counts = null;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        String cmdQuery = "";
        List queryResult = null;
        switch (targetChart) {
            case "NIMSSummary":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when choArea = 1 then 1 else 0 end ),0) as count1,IFNULL(sum(case when choArea = 2 then 1 else 0 end ),0) as count2,IFNULL(sum(case when choArea = 3 then 1 else 0 end ),0) as count3,IFNULL(sum(case when choArea = 4 then 1 else 0 end ),0) as count4,IFNULL(sum(case when choArea = 5 then 1 else 0 end ),0) as count5,IFNULL(sum(case when choArea = 6 then 1 else 0 end ),0) as count6,IFNULL(sum(case when choArea = 7 then 1 else 0 end ),0) as count7,IFNULL(sum(case when choArea = 8 then 1 else 0 end ),0) as count8,IFNULL(sum(case when choArea = 9 then 1 else 0 end ),0) as count9 from stfrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when locationC =1 then 1 else 0 end ),0) as count1,IFNULL(sum(case when locationC =2 then 1 else 0 end ),0) as count2,IFNULL(sum(case when locationC =3 then 1 else 0 end ),0) as count3 from stfrecord where choArea=?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)//Setting CHO ID in where condition
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        cmdQuery += "IFNULL(sum(case when hospitalgroup.name='" + xValues[i].toString() + "' then 1 else 0 end ),0) count" + i;
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    cmdQuery += " FROM stfrecord INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        Hospital hospital = (Hospital) xValues[i];
                        cmdQuery += "IFNULL(sum(case when hospitalID=" + hospital.getId() + " then 1 else 0 end ),0)" + " count" + i;
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    cmdQuery += " FROM stfrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        Communityhospital commHospital = (Communityhospital) xValues[i];
                        cmdQuery += "IFNULL(sum(case when communityHospitalID=" + commHospital.getId() + " then 1 else 0 end ),0)" + " count" + i;
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    cmdQuery += " FROM stfrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        Carehome careHome = (Carehome) xValues[i];
                        cmdQuery += "IFNULL(sum(case when careHomeID=" + careHome.getId() + " then 1 else 0 end ),0)" + " count" + i;
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    cmdQuery += " FROM stfrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                }
                break;
            case "NIMSAgeStats":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end ),0) count1,IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end ),0)  count2,IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end ),0) count3,IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end ),0)  count4,IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end ),0) count5,IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end ),0) count6,IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end ),0) count7 from stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end),0) count1,IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end),0) count2,IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end),0) count3,IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end),0) count4,IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end),0) count5,IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end),0) count6,IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end ),0) count6 from stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id WHERE choArea=?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end ),0) count1,IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end ),0) count2,IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end ),0)  count3,IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end ),0)  count4,IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end ),0) count5,IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end ),0) count6,IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end ),0) count7 From stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end ),0) count1,IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end ),0) count2,IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end ),0) count3,IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end ),0) count4,IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end ),0) count5,IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end ),0) count6,IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end ),0) count7 from stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id";
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=3";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end),0) count1,IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end),0) count2,IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end),0) count3,IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end),0) count4,IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end),0) count5,IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end),0) count6,IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end ),0) count7 from stfrecord INNER JOIN agegroup ON stfrecord.ageGroup = agegroup.id";
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=2";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
            break;
            case "NIMSGenderStats":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when gender=1 then 1 else 0 end ),0) as count1,IFNULL(sum(case when gender=2 then 1 else 0 end ),0) as count2 from stfrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when gender=1 then 1 else 0 end ),0) as count1,IFNULL(sum(case when gender=2 then 1 else 0 end ),0) as count2 from stfrecord WHERE choArea=?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when gender=1 then 1 else 0 end),0) count1,IFNULL(sum(case when gender=2 then 1 else 0 end ),0) count2 FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when gender=1 then 1 else 0 end),0) as count1,IFNULL(sum(case when gender=2 then 1 else 0 end),0) as count2 from stfrecord";
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=3";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select IFNULL(sum(case when gender=1 then 1 else 0 end),0) as count1,IFNULL(sum(case when gender=2 then 1 else 0 end),0) as count2 from stfrecord";
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=2";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                break;
                case "NIMSRecurStats":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when recurrLikelihood = 4 then 1 else 0 end),0) as count1,IFNULL(sum(case when recurrLikelihood = 5 then 1 else 0 end),0) as count2,IFNULL(sum(case when recurrLikelihood = 3 then 1 else 0 end),0) as count3,IFNULL(sum(case when recurrLikelihood = 2 then 1 else 0 end),0) as count4,IFNULL(sum(case when recurrLikelihood = 1 then 1 else 0 end),0) as count5 from stfrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when recurrLikelihood = 4 then 1 else 0 end),0) as count1,IFNULL(sum(case when recurrLikelihood = 5 then 1 else 0 end),0) as count2,IFNULL(sum(case when recurrLikelihood = 3 then 1 else 0 end),0) as count3,IFNULL(sum(case when recurrLikelihood = 2 then 1 else 0 end),0) as count4,IFNULL(sum(case when recurrLikelihood = 1 then 1 else 0 end),0) as count5 from stfrecord";
                    cmdQuery += " WHERE choArea=?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "Select IFNULL(sum(case when recurrLikelihood = 4 then 1 else 0 end ),0),IFNULL(sum(case when recurrLikelihood = 5 then 1 else 0 end ),0),IFNULL(sum(case when recurrLikelihood = 3 then 1 else 0 end ),0),IFNULL(sum(case when recurrLikelihood = 2 then 1 else 0 end ),0),IFNULL(sum(case when recurrLikelihood = 1 then 1 else 0 end ),0) FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when recurrLikelihood = 4 then 1 else 0 end),0) as count1,IFNULL(sum(case when recurrLikelihood = 5 then 1 else 0 end),0) as count2,IFNULL(sum(case when recurrLikelihood = 3 then 1 else 0 end),0) as count3,IFNULL(sum(case when recurrLikelihood = 2 then 1 else 0 end),0) as count4,IFNULL(sum(case when recurrLikelihood = 1 then 1 else 0 end),0) as count5 from stfrecord";
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=3";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select IFNULL(sum(case when recurrLikelihood = 4 then 1 else 0 end),0) as count1,IFNULL(sum(case when recurrLikelihood = 5 then 1 else 0 end),0) as count2,IFNULL(sum(case when recurrLikelihood = 3 then 1 else 0 end),0) as count3,IFNULL(sum(case when recurrLikelihood = 2 then 1 else 0 end),0) as count4,IFNULL(sum(case when recurrLikelihood = 1 then 1 else 0 end),0) as count5 from stfrecord";
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=2";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                break;
            case "NIMSSeverity":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "Select IFNULL(sum(case when severity =1 then 1 else 0 end),0) as count1,IFNULL(sum(case when severity =2 then 1 else 0 end),0) as count2,IFNULL(sum(case when severity =3 then 1 else 0 end),0) as count3,IFNULL(sum(case when severity =4 then 1 else 0 end),0) as count4,IFNULL(sum(case when severity =5 then 1 else 0 end),0) as count5 from stfrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "Select IFNULL(sum(case when severity =1 then 1 else 0 end),0) as count1,IFNULL(sum(case when severity =2 then 1 else 0 end),0) as count2,IFNULL(sum(case when severity =3 then 1 else 0 end),0) as count3,IFNULL(sum(case when severity =4 then 1 else 0 end),0) as count4,IFNULL(sum(case when severity =5 then 1 else 0 end),0) as count5 from stfrecord WHERE choArea=?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "Select IFNULL(sum(case when severity =1 then 1 else 0 end ),0),IFNULL(sum(case when severity =2 then 1 else 0 end ),0),IFNULL(sum(case when severity =3 then 1 else 0 end ),0),IFNULL(sum(case when severity =4 then 1 else 0 end ),0),IFNULL(sum(case when severity =5 then 1 else 0 end ),0) FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "Select IFNULL(sum(case when severity =1 then 1 else 0 end),0) as count1,IFNULL(sum(case when severity =2 then 1 else 0 end),0) as count2,IFNULL(sum(case when severity =3 then 1 else 0 end),0) as count3,IFNULL(sum(case when severity =4 then 1 else 0 end),0) as count4,IFNULL(sum(case when severity =5 then 1 else 0 end),0) as count5 from stfrecord";
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=3";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "Select IFNULL(sum(case when severity =1 then 1 else 0 end),0) as count1,IFNULL(sum(case when severity =2 then 1 else 0 end),0) as count2,IFNULL(sum(case when severity =3 then 1 else 0 end),0) as count3,IFNULL(sum(case when severity =4 then 1 else 0 end),0) as count4,IFNULL(sum(case when severity =5 then 1 else 0 end),0) as count5 from stfrecord";
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=2";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                break;
                  case "NIMSOutcomes":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "Select IFNULL(sum(case when outcome = 1 then 1 else 0 end),0) as count1,IFNULL(sum(case when outcome = 2 then 1 else 0 end),0) as count2,IFNULL(sum(case when outcome = 3 then 1 else 0 end),0) as count3,IFNULL(sum(case when outcome = 4 then 1 else 0 end),0) as count4,IFNULL(sum(case when outcome = 5 then 1 else 0 end),0) as count5,IFNULL(sum(case when outcome = 6 then 1 else 0 end),0) as count6,IFNULL(sum(case when outcome = 7 then 1 else 0 end),0) as count7,IFNULL(sum(case when outcome = 8 then 1 else 0 end),0) as count8,IFNULL(sum(case when outcome = 9 then 1 else 0 end),0) as count9,IFNULL(sum(case when outcome = 10 then 1 else 0 end),0) as count10,IFNULL(sum(case when outcome = 11 then 1 else 0 end),0) as count11,IFNULL(sum(case when outcome = 12 then 1 else 0 end),0) as count12,IFNULL(sum(case when outcome = 13 then 1 else 0 end),0) as count13 from stfrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "Select IFNULL(sum(case when outcome = 1 then 1 else 0 end),0) as count1,IFNULL(sum(case when outcome = 2 then 1 else 0 end),0) as count2,IFNULL(sum(case when outcome = 3 then 1 else 0 end),0) as count3,IFNULL(sum(case when outcome = 4 then 1 else 0 end),0) as count4,IFNULL(sum(case when outcome = 5 then 1 else 0 end),0) as count5,IFNULL(sum(case when outcome = 6 then 1 else 0 end),0) as count6,IFNULL(sum(case when outcome = 7 then 1 else 0 end),0) as count7,IFNULL(sum(case when outcome = 8 then 1 else 0 end),0) as count8,IFNULL(sum(case when outcome = 9 then 1 else 0 end),0) as count9,IFNULL(sum(case when outcome = 10 then 1 else 0 end),0) as count10,IFNULL(sum(case when outcome = 11 then 1 else 0 end),0) as count11,IFNULL(sum(case when outcome = 12 then 1 else 0 end),0) as count12,IFNULL(sum(case when outcome = 13 then 1 else 0 end),0) as count13 from stfrecord WHERE choArea=?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "Select IFNULL(sum(case when outcome = 1 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 2 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 3 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 4 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 5 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 6 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 7 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 8 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 9 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 10 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 11 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 12 then 1 else 0 end ),0),IFNULL(sum(case when outcome = 13 then 1 else 0 end ),0) FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                      } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                          cmdQuery = "Select IFNULL(sum(case when outcome = 1 then 1 else 0 end),0) as count1,IFNULL(sum(case when outcome = 2 then 1 else 0 end),0) as count2,IFNULL(sum(case when outcome = 3 then 1 else 0 end),0) as count3,IFNULL(sum(case when outcome = 4 then 1 else 0 end),0) as count4,IFNULL(sum(case when outcome = 5 then 1 else 0 end),0) as count5,IFNULL(sum(case when outcome = 6 then 1 else 0 end),0) as count6,IFNULL(sum(case when outcome = 7 then 1 else 0 end),0) as count7,IFNULL(sum(case when outcome = 8 then 1 else 0 end),0) as count8,IFNULL(sum(case when outcome = 9 then 1 else 0 end),0) as count9,IFNULL(sum(case when outcome = 10 then 1 else 0 end),0) as count10,IFNULL(sum(case when outcome = 11 then 1 else 0 end),0) as count11,IFNULL(sum(case when outcome = 12 then 1 else 0 end),0) as count12,IFNULL(sum(case when outcome = 13 then 1 else 0 end),0) as count13 from stfrecord";
                          cmdQuery += " WHERE choArea=? And stfrecord.locationC=3";
                          cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                          queryResult = em.createNativeQuery(cmdQuery)
                                  .setParameter(1, selectedScopeID)
                                  .getResultList();
                      } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                          cmdQuery = "Select IFNULL(sum(case when outcome = 1 then 1 else 0 end),0) as count1,IFNULL(sum(case when outcome = 2 then 1 else 0 end),0) as count2,IFNULL(sum(case when outcome = 3 then 1 else 0 end),0) as count3,IFNULL(sum(case when outcome = 4 then 1 else 0 end),0) as count4,IFNULL(sum(case when outcome = 5 then 1 else 0 end),0) as count5,IFNULL(sum(case when outcome = 6 then 1 else 0 end),0) as count6,IFNULL(sum(case when outcome = 7 then 1 else 0 end),0) as count7,IFNULL(sum(case when outcome = 8 then 1 else 0 end),0) as count8,IFNULL(sum(case when outcome = 9 then 1 else 0 end),0) as count9,IFNULL(sum(case when outcome = 10 then 1 else 0 end),0) as count10,IFNULL(sum(case when outcome = 11 then 1 else 0 end),0) as count11,IFNULL(sum(case when outcome = 12 then 1 else 0 end),0) as count12,IFNULL(sum(case when outcome = 13 then 1 else 0 end),0) as count13 from stfrecord";
                          cmdQuery += " WHERE choArea=? And stfrecord.locationC=2";
                          cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                          queryResult = em.createNativeQuery(cmdQuery)
                                  .setParameter(1, selectedScopeID)
                                  .getResultList();
                      }
                break;
              case "NIMSRisk":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when risk = 3 then 1 else 0 end),0) as count1,IFNULL(sum(case when risk = 2 then 1 else 0 end),0) as count2,IFNULL(sum(case when risk = 1 then 1 else 0 end),0) as count3 from stfrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when risk = 3 then 1 else 0 end),0) as count1,IFNULL(sum(case when risk = 2 then 1 else 0 end),0) as count2,IFNULL(sum(case when risk = 1 then 1 else 0 end),0) as count3 from stfrecord WHERE choArea=?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "Select IFNULL(sum(case when risk = 3 then 1 else 0 end ),0),IFNULL(sum(case when risk = 2 then 1 else 0 end ),0),IFNULL(sum(case when risk = 1 then 1 else 0 end ),0) FROM stfrecord  INNER JOIN hospital ON stfrecord.hospitalID = hospital.id INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when risk = 3 then 1 else 0 end),0) as count1,IFNULL(sum(case when risk = 2 then 1 else 0 end),0) as count2,IFNULL(sum(case when risk = 1 then 1 else 0 end),0) as count3 from stfrecord";      
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=3";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select IFNULL(sum(case when risk = 3 then 1 else 0 end),0) as count1,IFNULL(sum(case when risk = 2 then 1 else 0 end),0) as count2,IFNULL(sum(case when risk = 1 then 1 else 0 end),0) as count3 from stfrecord";
                    cmdQuery += " WHERE choArea=? And stfrecord.locationC=2";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                break;
                //Start IHFD queries
                case "IHFDAdmissionSource":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when admissionSource = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 6 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 7 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 8 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 9 then 1 else 0 end),0) from hfdrecord";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when admissionSource = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 6 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 7 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 8 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 9 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery += " WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when admissionSource = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 6 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 7 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 8 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 9 then 1 else 0 end),0)from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when admissionSource = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 6 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 7 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 8 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 9 then 1 else 0 end),0) from hfdrecord where hfdrecord.hospitalCode=-1";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();

                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select IFNULL(sum(case when admissionSource = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 6 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 7 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 8 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 9 then 1 else 0 end),0) from hfdrecord where hfdrecord.hospitalCode=-1";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                else if(selecetedScope == CommonObjects.Scope.AcuteHospital){
                    cmdQuery = "select IFNULL(sum(case when admissionSource = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 6 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 7 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 8 then 1 else 0 end),0),IFNULL(sum(case when admissionSource = 9 then 1 else 0 end),0)"
                                +" from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                                +" where hospital.id="+selectedScopeID;
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();  
                }
                break;
              case "IHFDDischargeCode":
                    if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when dischargeCode = 0 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 1 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 3 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 4 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 5 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 6 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 7 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 8 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 9 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 10 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 11 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 12 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 13 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 15 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 14 then 1 else 0 end),0) FROM hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when dischargeCode = 0 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 1 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 3 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 4 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 5 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 6 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 7 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 8 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 9 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 10 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 11 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 12 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 13 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 15 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 14 then 1 else 0 end),0) FROM hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when dischargeCode = 0 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 1 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 3 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 4 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 5 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 6 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 7 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 8 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 9 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 10 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 11 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 12 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 13 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 15 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 14 then 1 else 0 end),0) from hfdrecord hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when dischargeCode = 0 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 1 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 3 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 4 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 5 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 6 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 7 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 8 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 9 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 10 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 11 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 12 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 13 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 15 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 14 then 1 else 0 end),0) from hfdrecord where hfdrecord.hospitalCode=-1";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();

                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select IFNULL(sum(case when dischargeCode = 0 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 1 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 3 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 4 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 5 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 6 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 7 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 8 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 9 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 10 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 11 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 12 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 13 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 15 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 14 then 1 else 0 end),0) from hfdrecord where hfdrecord.hospitalCode=-1";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                    else if(selecetedScope == CommonObjects.Scope.AcuteHospital){
                    cmdQuery = "select IFNULL(sum(case when dischargeCode = 0 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 1 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 3 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 4 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 5 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 6 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 7 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 8 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 9 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 10 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 11 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 12 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 13 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 15 then 1 else 0 end),0),IFNULL(sum(case when dischargeCode = 14 then 1 else 0 end),0)"
                                +" from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                                +" where hospital.id="+selectedScopeID;
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();  
                }
                  break;
            case "IHFDDischargeStatus":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when dischargeStatus = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeStatus = 1 then 1 else 0 end),0) From hfdrecord";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when dischargeStatus = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeStatus = 1 then 1 else 0 end),0) FROM hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery += " WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when dischargeStatus = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeStatus = 1 then 1 else 0 end),0) from hfdrecord hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when dischargeStatus = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeStatus = 1 then 1 else 0 end),0) from hfdrecord where hfdrecord.hospitalCode=-1";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();

                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select IFNULL(sum(case when dischargeStatus = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeStatus = 1 then 1 else 0 end),0) from hfdrecord where hfdrecord.hospitalCode=-1";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                    cmdQuery = "select IFNULL(sum(case when dischargeStatus = 2 then 1 else 0 end),0),IFNULL(sum(case when dischargeStatus = 1 then 1 else 0 end),0)"
                            + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                            + " where hospital.id=" + selectedScopeID;
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                break;
            case "IHFDAdmDischarge":
     if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when admissionsource=1 and dischargeCode=1 then 1 else 0 end ),0)as HomeToHome,IFNULL(sum(case when admissionsource=1 and dischargeCode=2 then 1 else 0 end ),0)as HomeToNursing,IFNULL(sum(case when admissionsource=1 and (dischargeCode=3 or dischargeCode=4 or dischargeCode=5 or dischargeCode=8 or dischargeCode=9) then 1 else 0 end ),0)as HomeToHospital from hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when admissionsource=1 and dischargeCode=1 then 1 else 0 end ),0)as HomeToHome,IFNULL(sum(case when admissionsource=1 and dischargeCode=2 then 1 else 0 end ),0)as HomeToNursing,IFNULL(sum(case when admissionsource=1 and (dischargeCode=3 or dischargeCode=4 or dischargeCode=5 or dischargeCode=8 or dischargeCode=9) then 1 else 0 end ),0)as HomeToHospital from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when admissionsource=1 and dischargeCode=1 then 1 else 0 end ),0)as HomeToHome,IFNULL(sum(case when admissionsource=1 and dischargeCode=2 then 1 else 0 end ),0)as HomeToNursing,IFNULL(sum(case when admissionsource=1 and (dischargeCode=3 or dischargeCode=4 or dischargeCode=5 or dischargeCode=8 or dischargeCode=9) then 1 else 0 end ),0)as HomeToHospital from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select 0,0,0";
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                       } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                           cmdQuery = "select 0,0,0";
                           queryResult = em.createNativeQuery(cmdQuery)
                                   .setParameter(1, selectedScopeID)
                                   .getResultList();
                       } else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                           cmdQuery = "select IFNULL(sum(case when admissionsource=1 and dischargeCode=1 then 1 else 0 end ),0)as HomeToHome,IFNULL(sum(case when admissionsource=1 and dischargeCode=2 then 1 else 0 end ),0)as HomeToNursing,IFNULL(sum(case when admissionsource=1 and (dischargeCode=3 or dischargeCode=4 or dischargeCode=5 or dischargeCode=8 or dischargeCode=9) then 1 else 0 end ),0)as HomeToHospital"
                                   + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                                   + " where hospital.id=" + selectedScopeID;
                           cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                           queryResult = em.createNativeQuery(cmdQuery)
                                   .setParameter(1, selectedScopeID)
                                   .getResultList();
                       }
                break;
                   case "IHFDAdmissionType":
                    if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when admissionType = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 6 then 1 else 0 end),0) from hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when admissionType = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 6 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when admissionType = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 6 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when admissionType = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 6 then 1 else 0 end),0) from hfdrecord where hospitalCode=-1";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();

                       } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                           cmdQuery = "select IFNULL(sum(case when admissionType = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 6 then 1 else 0 end),0) from hfdrecord where hospitalCode=-1";
                           cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                           queryResult = em.createNativeQuery(cmdQuery)
                                   .setParameter(1, selectedScopeID)
                                   .getResultList();
                       } else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                           cmdQuery = "select IFNULL(sum(case when admissionType = 1 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 2 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 3 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 4 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 5 then 1 else 0 end),0),IFNULL(sum(case when admissionType = 6 then 1 else 0 end),0)"
                                   + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                                   + " where hospital.id=" + selectedScopeID;
                           cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                           queryResult = em.createNativeQuery(cmdQuery)
                                   .setParameter(1, selectedScopeID)
                                   .getResultList();
                       }
                       break;
                   case "IHFDAgeGroups":
                  if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end),0) from hfdrecord INNER JOIN agegroup ON hfdrecord.ageGroup = agegroup.id";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end),0) from hfdrecord INNER JOIN agegroup ON hfdrecord.ageGroup = agegroup.id INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end),0) from hfdrecord INNER JOIN agegroup ON hfdrecord.ageGroup = agegroup.id INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end),0) from hfdrecord INNER JOIN agegroup ON hfdrecord.ageGroup = agegroup.id where hospitalCode=-1";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end),0) from hfdrecord INNER JOIN agegroup ON hfdrecord.ageGroup = agegroup.id where hospitalCode=-1";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                       else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                           cmdQuery = "select IFNULL(sum(case when agegroup.lower < 40 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 40 And agegroup.upper <= 44 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 45 And agegroup.upper <= 49 then 1 else 0 end ),0),IFNULL(sum(case when agegroup.lower >= 50 And agegroup.upper <= 54 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 55 And agegroup.upper <= 59 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 60 And agegroup.upper <= 64 then 1 else 0 end),0),IFNULL(sum(case when agegroup.lower >= 65 then 1 else 0 end),0) "
                                   + "from hfdrecord INNER JOIN agegroup ON hfdrecord.ageGroup = agegroup.id INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                                   + " where hospital.id=" + selectedScopeID;
                           cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                           queryResult = em.createNativeQuery(cmdQuery)
                                   .setParameter(1, selectedScopeID)
                                   .getResultList();
                       }
                  break;   
              case "IHFDGender":
                    if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when gender = 1 then 1 else 0 end),0), IFNULL(sum(case when gender = 2 then 1 else 0 end),0) from hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when gender = 1 then 1 else 0 end),0), IFNULL(sum(case when gender = 2 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when gender = 1 then 1 else 0 end),0), IFNULL(sum(case when gender = 2 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select IFNULL(sum(case when gender = 1 then 1 else 0 end),0),IFNULL(sum(case when gender = 2 then 1 else 0 end),0) from hfdrecord where hospitalCode=-1";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select IFNULL(sum(case when gender = 1 then 1 else 0 end),0),IFNULL(sum(case when gender = 2 then 1 else 0 end),0) from hfdrecord where hospitalCode=-1";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                  else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                      cmdQuery = "select IFNULL(sum(case when gender = 1 then 1 else 0 end),0), IFNULL(sum(case when gender = 2 then 1 else 0 end),0)"
                              + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                              + " where hospital.id=" + selectedScopeID;
                      cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                      queryResult = em.createNativeQuery(cmdQuery)
                              .setParameter(1, selectedScopeID)
                              .getResultList();
                  }
                  break;
                  case "IHFDLengthofStay":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(round(sum(case when communityhealthcareorganisation.id = 1 then lengthOfStay else 0 end )/sum(case when communityhealthcareorganisation.id = 1 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 2 then lengthOfStay else 0 end )/sum(case when communityhealthcareorganisation.id = 2 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 3 then lengthOfStay else 0 end )/sum(case when communityhealthcareorganisation.id = 3 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 4 then lengthOfStay else 0 end )/sum(case when communityhealthcareorganisation.id = 4 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 5 then lengthOfStay else 0 end )/sum(case when communityhealthcareorganisation.id = 5 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 6 then lengthOfStay else 0 end )/sum(case when communityhealthcareorganisation.id = 6 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 7 then lengthOfStay else 0 end )/sum(case when communityhealthcareorganisation.id = 7 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 8 then lengthOfStay else 0 end )/sum(case when communityhealthcareorganisation.id = 8 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 9 then lengthOfStay else 0 end )/sum(case when communityhealthcareorganisation.id = 9 then 1 else 0 end )),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO||selecetedScope == CommonObjects.Scope.CHOHospitalGroups) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                       cmdQuery+="IFNULL(round(sum(case when hospitalgroup.name='"+xValues[i].toString()+"' then lengthOfStay else 0 end )/sum(case when hospitalgroup.name='"+xValues[i].toString()+"' then 1 else 0 end )),0)";
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    cmdQuery += " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        Hospital hospital = (Hospital) xValues[i];
                        cmdQuery += "IFNULL(round(sum(case when hospitalCode = "+hospital.getDoHCode()+" then lengthOfStay else 0 end )/sum(case when hospitalCode ="+hospital.getDoHCode()+" then 1 else 0 end )),0)";
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    cmdQuery += " FROM hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                }  else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        cmdQuery += "0";
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        cmdQuery += "0";
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                      } else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                          cmdQuery = "select IFNULL(round(sum(case when hospital.id =" + selectedScopeID + " then lengthOfStay else 0 end )/sum(case when hospital.id =" + selectedScopeID + " then 1 else 0 end )),0)"
                                  + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode";
                          cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                          queryResult = em.createNativeQuery(cmdQuery)
                                  .setParameter(1, selectedScopeID)
                                  .getResultList();
                      }
                break; 
               case "IHFDAgeLOS":
                  if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(round(sum(case when age < 40 then lengthOfStay else 0 end )/sum(case when age < 40 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 40 And age <= 44 then lengthOfStay else 0 end )/sum(case when age >= 40 And age <= 44 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 45 And age <= 49 then lengthOfStay else 0 end )/sum(case when age >= 45 And age <= 49 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 50 And age <= 54 then lengthOfStay else 0 end )/sum(case when age >= 50 And age <= 54 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 55 And age <= 59 then lengthOfStay else 0 end )/sum(case when age >= 55 And age <= 59 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 60 And age <= 64 then lengthOfStay else 0 end )/sum(case when age >= 60 And age <= 64 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 65 then lengthOfStay else 0 end )/sum(case when age then 1 else 0 end )),0) from hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(round(sum(case when age < 40 then lengthOfStay else 0 end )/sum(case when age < 40 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 40 And age <= 44 then lengthOfStay else 0 end )/sum(case when age >= 40 And age <= 44 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 45 And age <= 49 then lengthOfStay else 0 end )/sum(case when age >= 45 And age <= 49 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 50 And age <= 54 then lengthOfStay else 0 end )/sum(case when age >= 50 And age <= 54 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 55 And age <= 59 then lengthOfStay else 0 end )/sum(case when age >= 55 And age <= 59 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 60 And age <= 64 then lengthOfStay else 0 end )/sum(case when age >= 60 And age <= 64 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 65 then lengthOfStay else 0 end )/sum(case when age then 1 else 0 end )),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(round(sum(case when age < 40 then lengthOfStay else 0 end )/sum(case when age < 40 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 40 And age <= 44 then lengthOfStay else 0 end )/sum(case when age >= 40 And age <= 44 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 45 And age <= 49 then lengthOfStay else 0 end )/sum(case when age >= 45 And age <= 49 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 50 And age <= 54 then lengthOfStay else 0 end )/sum(case when age >= 50 And age <= 54 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 55 And age <= 59 then lengthOfStay else 0 end )/sum(case when age >= 55 And age <= 59 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 60 And age <= 64 then lengthOfStay else 0 end )/sum(case when age >= 60 And age <= 64 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 65 then lengthOfStay else 0 end )/sum(case when age then 1 else 0 end )),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "Select 0,0,0,0,0,0,0";
                    //cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "Select 0,0,0,0,0,0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                       else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                           cmdQuery = "select IFNULL(round(sum(case when age < 40 then lengthOfStay else 0 end )/sum(case when age < 40 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 40 And age <= 44 then lengthOfStay else 0 end )/sum(case when age >= 40 And age <= 44 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 45 And age <= 49 then lengthOfStay else 0 end )/sum(case when age >= 45 And age <= 49 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 50 And age <= 54 then lengthOfStay else 0 end )/sum(case when age >= 50 And age <= 54 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 55 And age <= 59 then lengthOfStay else 0 end )/sum(case when age >= 55 And age <= 59 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 60 And age <= 64 then lengthOfStay else 0 end )/sum(case when age >= 60 And age <= 64 then 1 else 0 end )),0),IFNULL(round(sum(case when age >= 65 then lengthOfStay else 0 end )/sum(case when age then 1 else 0 end )),0)"
                                   + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                                   + " where hospital.id=" + selectedScopeID;
                           cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                           queryResult = em.createNativeQuery(cmdQuery)
                                   .setParameter(1, selectedScopeID)
                                   .getResultList();
                       }
                  break;   
                  case "IHFDTraumaType":
                   if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when traumaType = 1 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 2 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 8 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 9 then 1 else 0 end),0) from hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when traumaType = 1 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 2 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 8 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 9 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when traumaType = 1 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 2 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 8 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 9 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select 0,0,0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select 0,0,0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                  else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                      cmdQuery = "select IFNULL(sum(case when traumaType = 1 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 2 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 8 then 1 else 0 end),0),IFNULL(sum(case when traumaType = 9 then 1 else 0 end),0)"
                              + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                              + " where hospital.id=" + selectedScopeID;
                      cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                      queryResult = em.createNativeQuery(cmdQuery)
                              .setParameter(1, selectedScopeID)
                              .getResultList();
                  }
                  break;   
            case "IHFDFractureType":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when fractureType = 1 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 2 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 3 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 4 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 8 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 9 then 1 else 0 end),0) from hfdrecord";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when fractureType = 1 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 2 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 3 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 4 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 8 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 9 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery += " WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when fractureType = 1 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 2 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 3 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 4 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 8 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 9 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select 0,0,0,0";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select 0,0,0,0,0,0";
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                    cmdQuery = "select IFNULL(sum(case when fractureType = 1 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 2 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 3 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 4 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 8 then 1 else 0 end),0),IFNULL(sum(case when fractureType = 9 then 1 else 0 end),0)"
                            + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                            + " where hospital.id=" + selectedScopeID;
                    cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                break;  
            case "IHFDFallAssessment":
                    if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when specailistFallsAssess = 1 or specailistFallsAssess =2  then 1 else 0 end),0),IFNULL(sum(case when specailistFallsAssess = 0 then 1 else 0 end),0) from hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when specailistFallsAssess = 1 or specailistFallsAssess =2  then 1 else 0 end),0),IFNULL(sum(case when specailistFallsAssess = 0 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when specailistFallsAssess = 1 or specailistFallsAssess =2  then 1 else 0 end),0),IFNULL(sum(case when specailistFallsAssess = 0 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "Select 0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "Select 0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                  else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                      cmdQuery = "select IFNULL(sum(case when specailistFallsAssess = 1 or specailistFallsAssess =2  then 1 else 0 end),0),IFNULL(sum(case when specailistFallsAssess = 0 then 1 else 0 end),0)"
                              + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                              + " where hospital.id=" + selectedScopeID;
                      cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                      queryResult = em.createNativeQuery(cmdQuery)
                              .setParameter(1, selectedScopeID)
                              .getResultList();
                  }
                break;
              case "IHFDTimeToSurgery":
                if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(round(sum(case when communityhealthcareorganisation.id = 1 then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when communityhealthcareorganisation.id = 1 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 2 then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when communityhealthcareorganisation.id = 2 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 3 then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when communityhealthcareorganisation.id = 3 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 4 then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when communityhealthcareorganisation.id = 4 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 5 then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when communityhealthcareorganisation.id = 5 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 6 then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when communityhealthcareorganisation.id = 6 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 7 then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when communityhealthcareorganisation.id = 7 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 8 then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when communityhealthcareorganisation.id = 8 then 1 else 0 end )),0),IFNULL(round(sum(case when communityhealthcareorganisation.id = 9 then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when communityhealthcareorganisation.id = 9 then 1 else 0 end )),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO||selecetedScope == CommonObjects.Scope.CHOHospitalGroups) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                       cmdQuery+="IFNULL(round(sum(case when hospitalgroup.name='"+xValues[i].toString()+"' then DATEDIFF(primarySurgeryDate,admissionDate)  else 0 end )/sum(case when hospitalgroup.name='"+xValues[i].toString()+"' then 1 else 0 end )),0)";
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    cmdQuery += " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        Hospital hospital = (Hospital) xValues[i];
                        cmdQuery += "IFNULL(round(sum(case when hospitalCode = "+hospital.getDoHCode()+" then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when hospitalCode ="+hospital.getDoHCode()+" then 1 else 0 end )),0)";
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    cmdQuery += " FROM hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                }  else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        cmdQuery += "0";
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select ";
                    for (int i = 0; i < xValues.length; i++) {
                        cmdQuery += "0";
                        if ((i + 1) < xValues.length) {
                            cmdQuery += ",";
                        }
                    }
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                      } else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                          cmdQuery = "select IFNULL(round(sum(case when hospital.id =" + selectedScopeID + " then DATEDIFF(primarySurgeryDate,admissionDate) else 0 end )/sum(case when hospital.id =" + selectedScopeID + " then 1 else 0 end )),0)"
                                  + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode";
                          cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                          queryResult = em.createNativeQuery(cmdQuery)
                                  .setParameter(1, selectedScopeID)
                                  .getResultList();
                      }
                break;
              case "IHFDFragilityHistory":
               if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when fragilityHistory = 1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory = 2 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory = 9 then 1 else 0 end),0) from hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when fragilityHistory = 1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory = 2 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory = 9 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when fragilityHistory = 1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory = 2 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory = 9 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "Select 0,0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select 0,0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                  else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                      cmdQuery = "select IFNULL(sum(case when fragilityHistory = 1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory = 2 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory = 9 then 1 else 0 end),0)"
                              + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                              + " where hospital.id=" + selectedScopeID;
                      cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                      queryResult = em.createNativeQuery(cmdQuery)
                              .setParameter(1, selectedScopeID)
                              .getResultList();
                  }
                  break;            
               case "IHFDMultiAssessment":
               if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when fragilityHistory=1 And multiRehabAssess=1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory=1 And multiRehabAssess=2 then 1 else 0 end),0) from hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when fragilityHistory=1 And multiRehabAssess=1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory=1 And multiRehabAssess=2 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when fragilityHistory=1 And multiRehabAssess=1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory=1 And multiRehabAssess=2 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "Select 0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select 0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                  else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                      cmdQuery = "select IFNULL(sum(case when fragilityHistory=1 And multiRehabAssess=1 then 1 else 0 end),0),IFNULL(sum(case when fragilityHistory=1 And multiRehabAssess=2 then 1 else 0 end),0)"
                              + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                              + " where hospital.id=" + selectedScopeID;
                      cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                      queryResult = em.createNativeQuery(cmdQuery)
                              .setParameter(1, selectedScopeID)
                              .getResultList();
                  }
                  break;
            case "IHFDTimeToSurgery48hrs":
               if (selecetedScope == CommonObjects.Scope.Global) {
                    cmdQuery = "select IFNULL(sum(case when DATEDIFF(primarySurgeryDate,admissionDate)<=2 then 1 else 0 end),0),IFNULL(sum(case when DATEDIFF(primarySurgeryDate,admissionDate)>2 then 1 else 0 end),0) from hfdrecord";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    cmdQuery = "select IFNULL(sum(case when DATEDIFF(primarySurgeryDate,admissionDate)<=2 then 1 else 0 end),0),IFNULL(sum(case when DATEDIFF(primarySurgeryDate,admissionDate)>2 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN communityhealthcareorganisation ON hospital.areaid = communityhealthcareorganisation.id";
                    cmdQuery +=" WHERE communityhealthcareorganisation.id = ?";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups || selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    String condition = "(";
                    for (int i = 0; i < xValues.length; i++) {
                        condition += "hospitalgroup.name='" + xValues[i].toString() + "'";
                        if ((i + 1) < xValues.length) {
                            condition += " or ";
                        }
                    }
                    condition += ")";
                    cmdQuery = "select IFNULL(sum(case when DATEDIFF(primarySurgeryDate,admissionDate)<=2 then 1 else 0 end),0),IFNULL(sum(case when DATEDIFF(primarySurgeryDate,admissionDate)>2 then 1 else 0 end),0) from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode INNER JOIN hospitalgroup ON hospital.hospitalGroup = hospitalgroup.id";
                    cmdQuery += " WHERE " + condition;
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery).getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    cmdQuery = "Select 0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    cmdQuery = "select 0,0";
                    cmdQuery=CommonObjects.AddQueryFilter(cmdQuery);
                    queryResult = em.createNativeQuery(cmdQuery)
                            .setParameter(1, selectedScopeID)
                            .getResultList();
                }
                  else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                      cmdQuery = "select IFNULL(sum(case when DATEDIFF(primarySurgeryDate,admissionDate)<=2 then 1 else 0 end),0),IFNULL(sum(case when DATEDIFF(primarySurgeryDate,admissionDate)>2 then 1 else 0 end),0)"
                              + " from hfdrecord INNER JOIN hospital ON hfdrecord.hospitalCode = hospital.DoHCode"
                              + " where hospital.id=" + selectedScopeID;
                      cmdQuery = CommonObjects.AddQueryFilter(cmdQuery);
                      queryResult = em.createNativeQuery(cmdQuery)
                              .setParameter(1, selectedScopeID)
                              .getResultList();
                  }
                  break;   
                   
        }
        try {
            counts = (Object[]) queryResult.get(0);
        } catch (Exception e) {
            counts = new Object[1];
            counts[0] = (Object) queryResult.get(0);
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
 /*   public static void GetFilterParams(boolean maleSelected, boolean femaleSelected) {
        fitersParams = new ArrayList();
        filterString = "";
        if (maleSelected == true && femaleSelected == false) {
            fitersParams.add(",gender=1");
        } else if (femaleSelected == true && maleSelected == false) {
            fitersParams.add(",gender=2");
        }
        if (fitersParams.size() > 0) {
            filterString = Arrays.toString(fitersParams.toArray());
            filterString = filterString.replaceFirst(",", "");
            filterString = filterString.replaceAll("\\[", "");
            filterString = filterString.replaceAll("\\]", "");
            filterString = filterString.replaceAll(",", " And ");
        }

    }*/
    private static java.sql.Date sqlDate(java.util.Date calendarDate) {
        return new java.sql.Date(calendarDate.getTime());
    }

}
