/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Temp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import charts.ChartBuilder;
import charts.ChartParams;
import charts.ChartDBQuery;
import charts.ThemeColors;
import entityclasses.Carehome;
import entityclasses.Communityhealthcareorganisation;
import entityclasses.Communityhospital;
import entityclasses.Hospital;
import entityclasses.Hospitalgroup;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.model.chart.ChartSeries;

public class OldChartModels2 {
    public enum Scope {
        Global, CHO, CHOHospitalGroups,
        HospitalGroup,
        CommunityHospitals,
        CareHomes,
        PrimaryCareTeams
    };
    public enum ChartType {
        VBarChart, HBarChart, PieChart, BubbleChart
    };
/*
    public static Object CreateChart(String selectedOrganisation, String targetChart, ChartType chartType) {
        ChartParams chartParams = SetChartParams(selectedOrganisation, targetChart);
        if (chartType == ChartType.VBarChart) {
            ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(chartParams.XValues, chartParams.YValues);
            return ChartBuilder.BuildVerBarChart(chartParams.ChartTitle, "", chartParams.XAxisLabel, chartParams.YAxisLabel, chartSeries, chartParams.VerSeriesColors, chartParams.XTickAngle, chartParams.YTickAngle, chartParams.IsAnimate);
        } else if (chartType == ChartType.HBarChart) {
            ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(chartParams.XValues, chartParams.YValues);
            return ChartBuilder.BuildHorBarChart(chartParams.ChartTitle, "", chartParams.XAxisLabel, chartParams.YAxisLabel, chartSeries, chartParams.HorSeriesColors, chartParams.IsAnimate);
        } else if (chartType == ChartType.PieChart) {
            return ChartBuilder.BuildPieChart(chartParams.ChartTitle, chartParams.LegendPosition, chartParams.XValues, chartParams.PieColors, chartParams.YValues);

        } else {
            return null;
        }
    }
    private static ChartParams SetChartParams(String selectedOrganisation, String targetChart) {
        Object[] scope = SetSelectedScope(selectedOrganisation);
        Scope selecetedScope = (Scope) scope[0];
        String selectedScopeID = scope[1].toString();
        ChartParams chartParams = new ChartParams();

        switch (targetChart) {
            case "NIMSSummary":
                //Global parameters applied to all charts regardless of selected scope
                chartParams.ChartTitle = "NIMS Incidents Statisitcs";
                chartParams.YAxisLabel = "No. of NIMS Incidents";
                if (selecetedScope == Scope.Global) {//All CHOs
                    chartParams.XAxisLabel = "Community Health Organisations";
                    chartParams.XValues = new String[]{"CHO1", "CHO2", "CHO3", "CHO4", "CHO5", "CHO6", "CHO7", "CHO8", "CHO9"};
                    // chartParams.YValues = DBQuery.NimsCounts();
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHO) {//All CHOs
                    chartParams.ChartTitle += "-CHO" + selectedScopeID;
                    chartParams.XAxisLabel = "Health Services Providers";
                    chartParams.XValues = new String[]{"Acute Hospitals", "Community Hospitals", "Care Homes"};
                    // chartParams.YValues = DBQuery.NimsCounts(selectedScopeID);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);

                } else if (selecetedScope == Scope.CHOHospitalGroups) {
                    chartParams.ChartTitle += "-Hospital Groups (CHO" + scope[1] + ")";
                    chartParams.XAxisLabel = "Hospital Groups";
                    chartParams.XValues = SelectedHospitalGroups(selectedScopeID);
                    //chartParams.YValues = DBQuery.NimsCounts(chartParams.XValues);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, chartParams.XValues);

                } else if (selecetedScope == Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
                    chartParams.ChartTitle += "-Acute Hospitals (" + selectedHospGroup.getName() + ")";
                    List<Hospital> hospitals = (List<Hospital>) selectedHospGroup.getHospitalCollection();
                    String[] hospitalNames = new String[hospitals.size()];
                    for (int i = 0; i < hospitals.size(); i++) {
                        hospitalNames[i] = Shorten(hospitals.get(i).getName());
                    }
                    chartParams.XAxisLabel = "Acute Hospitals";
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                    chartParams.XValues = hospitalNames;
                    //  chartParams.YValues = DBQuery.NimsCounts(hospitals.toArray(new Hospital[hospitals.size()]));
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, hospitals.toArray(new Hospital[hospitals.size()]));
                } else if (selecetedScope == Scope.CommunityHospitals) {
                    chartParams.ChartTitle += "-Community Hospitals(CHO" + selectedScopeID + ")";
                    Communityhealthcareorganisation selectedCHO = GetSelectedCHO(selectedScopeID);
                    List<Communityhospital> commHospitals = (List<Communityhospital>) selectedCHO.getCommunityhospitalCollection();
                    String[] commHospitalNames = new String[commHospitals.size()];
                    for (int i = 0; i < commHospitals.size(); i++) {
                        commHospitalNames[i] = Shorten(commHospitals.get(i).getName());
                    }
                    chartParams.XAxisLabel = "Community Hospitals";
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                    chartParams.XValues = commHospitalNames;
                    //chartParams.YValues = DBQuery.NimsCounts(commHospitals.toArray(new Communityhospital[commHospitals.size()]));
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, commHospitals.toArray(new Communityhospital[commHospitals.size()]));

                } else if (selecetedScope == Scope.CareHomes) {
                    chartParams.ChartTitle += "-Care Homes(CHO" + selectedScopeID + ")";
                    Communityhealthcareorganisation selectedCHO = GetSelectedCHO(selectedScopeID);
                    List<Carehome> careHomes = (List<Carehome>) selectedCHO.getCarehomeCollection();
                    String[] careHomeNames = new String[careHomes.size()];
                    for (int i = 0; i < careHomes.size(); i++) {
                        careHomeNames[i] = Shorten(careHomes.get(i).getName());
                    }
                    chartParams.XAxisLabel = "Care Homes";
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                    chartParams.XValues = careHomeNames;
                    //  chartParams.YValues = DBQuery.NimsCounts(careHomes.toArray(new Carehome[careHomes.size()]));
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, careHomes.toArray(new Carehome[careHomes.size()]));
                }
                break;
            case "NIMSAgeStats":
                //Global parameters applied to all charts regardless of selected scope
                chartParams.ChartTitle = "NIMS Age Groups Statistics";
                chartParams.XAxisLabel = "Age Groups";
                chartParams.YAxisLabel = "No. of NIMS Incidents";
                chartParams.XValues = new String[]{"<40", "40-44", "45-49", "50-54", "55-59", "60-64", ">=65"};
                if (selecetedScope == Scope.Global) {
                    //   chartParams.YValues = DBQuery.NIMSAgeStats();
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);

                } else if (selecetedScope == Scope.CHO) {
                    chartParams.ChartTitle += "(CHO" + selectedScopeID + ")";
                    // chartParams.YValues = DBQuery.NIMSAgeStats(selectedScopeID, Scope.CHO);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);

                } else if (selecetedScope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
                    chartParams.ChartTitle += "-Hospital Groups" + "(CHO" + selectedScopeID + ")";
                    String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
                    // chartParams.YValues = DBQuery.NIMSAgeStats(hospitalGroupNames);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, hospitalGroupNames);
                } else if (selecetedScope == Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
                    chartParams.ChartTitle += "(" + selectedHospGroup.getName() + " Hospital Group)";
                    // chartParams.YValues = DBQuery.NIMSAgeStats(new String[]{selectedHospGroup.getName()});
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, new String[]{selectedHospGroup.getName()});

                } else if (selecetedScope == Scope.CommunityHospitals) {
                    chartParams.ChartTitle += "-Community Hospitals" + "(CHO" + selectedScopeID + ")";
                    // chartParams.YValues = DBQuery.NIMSAgeStats(selectedScopeID, ChartModels.Scope.CommunityHospitals);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CareHomes) {
                    chartParams.ChartTitle += "-Care Homes" + "(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSAgeStats(selectedScopeID, ChartModels.Scope.CareHomes);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);

                }
                break;
            case "NIMSGenderStats":
                chartParams.ChartTitle = "NIMS Gender Statistics";
                chartParams.XAxisLabel = "Gender";
                chartParams.YAxisLabel = "No. of NIMS Incidents";
                chartParams.VerSeriesColors = "3498db,D2527F";
                chartParams.HorSeriesColors = "3498db,D2527F";
                chartParams.PieColors = "3498db,D2527F";
                chartParams.XValues = new String[]{"Males", "Females"};
                if (selecetedScope == Scope.Global) {
                    //chartParams.YValues = DBQuery.NIMSGenderStats();
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHO) {
                    chartParams.ChartTitle += "(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSGenderStats(selectedScopeID, Scope.CHO);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
                    chartParams.ChartTitle += "-Hospital Groups (CHO" + selectedScopeID + ")";
                    String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
                    // chartParams.YValues = DBQuery.NIMSGenderStats(hospitalGroupNames);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, hospitalGroupNames);
                } else if (selecetedScope == Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
                    chartParams.ChartTitle += "(" + selectedHospGroup.getName() + " Hospital Group)";
                    // chartParams.YValues = DBQuery.NIMSGenderStats(new String[]{selectedHospGroup.getName()});
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, new String[]{selectedHospGroup.getName()});
                } else if (selecetedScope == Scope.CommunityHospitals) {
                    chartParams.ChartTitle += "-Community Hospitals(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSGenderStats(selectedScopeID, Scope.CommunityHospitals);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CareHomes) {
                    chartParams.ChartTitle += "-Care Homes(CHO" + selectedScopeID + ")";
                    // chartParams.YValues = DBQuery.NIMSGenderStats(selectedScopeID, ChartModels.Scope.CareHomes);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                }
                break;
            case "NIMSRecurStats":
                chartParams.ChartTitle = "Recurrence Likelihood Statistics of NIMS Incidents";
                chartParams.XAxisLabel = "Recurrence Likelihood";
                chartParams.YAxisLabel = "No. of NIMS Incidents";
                chartParams.XValues = new String[]{"Rare", "Unlikely", "Possible", "Likely", "Almost Certain"};
                if (selecetedScope == Scope.Global) {
                    //chartParams.YValues = DBQuery.NIMSRecurrStats();
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHO) {
                    chartParams.ChartTitle += "(CHO" + selectedScopeID + ")";
                    //  chartParams.YValues = DBQuery.NIMSRecurrStats(selectedScopeID, Scope.CHO);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
                    chartParams.ChartTitle += "-Hospital Groups (CHO" + selectedScopeID + ")";
                    String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
                    //chartParams.YValues = DBQuery.NIMSRecurrStats(hospitalGroupNames);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, hospitalGroupNames);
                } else if (selecetedScope == Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
                    chartParams.ChartTitle += "(" + selectedHospGroup.getName() + " Hospital Group)";
                    //chartParams.YValues = DBQuery.NIMSRecurrStats(new String[]{selectedHospGroup.getName()});
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, new String[]{selectedHospGroup.getName()});
                } else if (selecetedScope == Scope.CommunityHospitals) {
                    chartParams.ChartTitle += "-Community Hospitals(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSRecurrStats(selectedScopeID, Scope.CommunityHospitals);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CareHomes) {
                    chartParams.ChartTitle += "-Care Homes(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSRecurrStats(selectedScopeID, Scope.CareHomes);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);

                }
                break;
            case "NIMSSeverity":
                chartParams.ChartTitle = "Severity Statistics of NIMS Incidents";
                chartParams.XAxisLabel = "Incident Severity";
                chartParams.YAxisLabel = "No. of NIMS Incidents";
                chartParams.VerSeriesColors = ThemeColors.GreenToRed;
                chartParams.HorSeriesColors = ThemeColors.GreenToRed;
                chartParams.XValues = new String[]{"Low", "Minor", "Moderate", "Major", "Catastrophic"};
                if (selecetedScope == Scope.Global) {
                    // chartParams.YValues = DBQuery.NIMSSeverityStats();
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHO) {
                    chartParams.ChartTitle += "(CHO" + selectedScopeID + ")";
                    //  chartParams.YValues = DBQuery.NIMSSeverityStats(selectedScopeID, Scope.CHO);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
                    chartParams.ChartTitle += "-Hospital Groups (CHO" + selectedScopeID + ")";
                    String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
                    //  chartParams.YValues = DBQuery.NIMSSeverityStats(hospitalGroupNames);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, hospitalGroupNames);
                } else if (selecetedScope == Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
                    chartParams.ChartTitle += "(" + selectedHospGroup.getName() + " Hospital Group)";
                    chartParams.YValues = DBQuery.NIMSSeverityStats(new String[]{selectedHospGroup.getName()});
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, new String[]{selectedHospGroup.getName()});
                } else if (selecetedScope == Scope.CommunityHospitals) {
                    chartParams.ChartTitle += "-Community Hospitals(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSSeverityStats(selectedScopeID, ChartModels.Scope.CommunityHospitals);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CareHomes) {
                    chartParams.ChartTitle += "-Care Homes(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSSeverityStats(selectedScopeID, ChartModels.Scope.CareHomes);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                }
                break;
            case "NIMSOutcomes":
                chartParams.ChartTitle = "Statistics of NIMS Incidents Outcomes";
                chartParams.XAxisLabel = "Outcome";
                chartParams.YAxisLabel = "No. of NIMS Incidents";
                chartParams.XValues = new String[]{"Bruising", "Burn/Scald", "Dislocation", "Fatality", "Fracture", "Graze", "Haemorrhage", "Laceration", "Multi-Injuries", "No Apparent Injury", "Pain & Suffering", "Serious Deterioration", "Other"};
                chartParams.XTickAngle = -50;
                chartParams.YTickAngle = 50;
                if (selecetedScope == Scope.Global) {
                    //chartParams.YValues = DBQuery.NIMSOutcomeStats();
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHO) {
                    chartParams.ChartTitle += "(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSOutcomeStats(selectedScopeID, Scope.CHO);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
                    chartParams.ChartTitle += "-Hospital Groups (CHO" + selectedScopeID + ")";
                    String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
                    //chartParams.YValues = DBQuery.NIMSOutcomeStats(hospitalGroupNames);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, hospitalGroupNames);
                } else if (selecetedScope == Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
                    chartParams.ChartTitle += "(" + selectedHospGroup.getName() + " Hospital Group)";
                    //chartParams.YValues = DBQuery.NIMSOutcomeStats(new String[]{selectedHospGroup.getName()});
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, new String[]{selectedHospGroup.getName()});
                } else if (selecetedScope == Scope.CommunityHospitals) {
                    chartParams.ChartTitle += "-Community Hospitals(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSOutcomeStats(selectedScopeID, Scope.CommunityHospitals);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CareHomes) {
                    chartParams.ChartTitle += "-Care Homes(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSOutcomeStats(selectedScopeID, Scope.CareHomes);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                }
                break;
            case "NIMSRisk":
                chartParams.ChartTitle = "Risk Statistics of NIMS Incidents";
                chartParams.XAxisLabel = "Risk Rating";
                chartParams.YAxisLabel = "No. of NIMS Incidents";
                chartParams.VerSeriesColors = ThemeColors.GreenToRed;
                chartParams.HorSeriesColors = ThemeColors.GreenToRed;
                chartParams.PieColors = ThemeColors.GreenToRed;
                chartParams.XValues = new String[]{"Very Low", "Low", "High"};
                if (selecetedScope == Scope.Global) {
                    //chartParams.YValues = DBQuery.NIMSRiskStats();
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHO) {
                    chartParams.ChartTitle += "(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSRiskStats(selectedScopeID, Scope.CHO);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
                    chartParams.ChartTitle += "-Hospital Groups (CHO" + selectedScopeID + ")";
                    String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
                    //chartParams.YValues = DBQuery.NIMSRiskStats(hospitalGroupNames);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, hospitalGroupNames);
                } else if (selecetedScope == Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
                    chartParams.ChartTitle += "(" + selectedHospGroup.getName() + " Hospital Group)";
                    //chartParams.YValues = DBQuery.NIMSRiskStats(new String[]{selectedHospGroup.getName()});
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope, new String[]{selectedHospGroup.getName()});
                } else if (selecetedScope == Scope.CommunityHospitals) {
                    chartParams.ChartTitle += "-Community Hospitals(CHO" + selectedScopeID + ")";
                    // chartParams.YValues = DBQuery.NIMSRiskStats(selectedScopeID, ChartModels.Scope.CommunityHospitals);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                } else if (selecetedScope == Scope.CareHomes) {
                    chartParams.ChartTitle += "-Care Homes(CHO" + selectedScopeID + ")";
                    //chartParams.YValues = DBQuery.NIMSRiskStats(selectedScopeID, ChartModels.Scope.CareHomes);
                    chartParams.YValues = DBQuery.ChartQuery(targetChart, scope);
                }
                break;
        }
        return chartParams;
    }

    //Miscellaneous functions
    private static Object[] SetSelectedScope(String selectedOrganisation) {
        Object[] scope = new Object[2];
        if (selectedOrganisation.equals("root")) {
            scope[0] = Scope.Global;
            scope[1] = selectedOrganisation;
        } else if (selectedOrganisation.startsWith("CHO", 0)) {
            scope[0] = Scope.CHO;
            scope[1] = selectedOrganisation.replaceAll("CHO:", "");
        } else if (selectedOrganisation.startsWith("HospitalGroupsCHO", 0)) {
            scope[0] = Scope.CHOHospitalGroups;
            scope[1] = selectedOrganisation.replaceAll("HospitalGroupsCHO:", "");
        } else if (selectedOrganisation.startsWith("HospGroup", 0)) {
            scope[0] = Scope.HospitalGroup;
            scope[1] = selectedOrganisation.replaceAll("HospGroup:", "");
        } else if (selectedOrganisation.startsWith("CommunityHospitalsCHO", 0)) {
            scope[0] = Scope.CommunityHospitals;
            scope[1] = selectedOrganisation.replaceAll("CommunityHospitalsCHO:", "");
        } else if (selectedOrganisation.startsWith("CareHomesCHO", 0)) {
            scope[0] = Scope.CareHomes;
            scope[1] = selectedOrganisation.replaceAll("CareHomesCHO:", "");
        } else {
            return null;
        }
        return scope;
    }

    private static Communityhealthcareorganisation GetSelectedCHO(String selectedScopeID) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        Communityhealthcareorganisation selectedCHO = (Communityhealthcareorganisation) em.createNamedQuery("Communityhealthcareorganisation.findById").setParameter("id", Integer.parseInt(selectedScopeID)).getSingleResult();
        return selectedCHO;
    }

    private static String[] SelectedHospitalGroups(String selectedScopeID) {
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

    private static Hospitalgroup SelectedHospitalGroup(String selectedScopeID) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        Hospitalgroup selectedHospGroup = (Hospitalgroup) em.createNamedQuery("Hospitalgroup.findById").setParameter("id", Integer.parseInt(selectedScopeID)).getSingleResult();
        return selectedHospGroup;
    }

    private static String Shorten(String hospitalName) {
        hospitalName = hospitalName.replaceAll("Hospital", "");
        hospitalName = hospitalName.replaceAll("Hospital", "");
        hospitalName = hospitalName.replaceAll("Mid-Western", "MidWest");
        hospitalName = hospitalName.replaceAll("Regional", "");
        hospitalName = hospitalName.replaceAll("'s", "");
        hospitalName = hospitalName.replaceAll("Radiation Oncology Network", "");
        hospitalName = hospitalName.replaceAll("Community", "");
        hospitalName = hospitalName.replaceAll("Nursing", "");
        hospitalName = hospitalName.replaceAll("Home", "");
        hospitalName = hospitalName.replaceAll(", ", " ");
        return hospitalName;
    }*/
}
