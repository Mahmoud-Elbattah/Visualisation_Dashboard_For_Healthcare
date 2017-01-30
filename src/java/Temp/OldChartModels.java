/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Temp;

import entityclasses.Carehome;
import entityclasses.Communityhealthcareorganisation;
import entityclasses.Communityhospital;
import entityclasses.Hospital;
import entityclasses.Hospitalgroup;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import charts.ChartBuilder;
import charts.ChartDBQuery;
import charts.ChartSelector;
import charts.ThemeColors;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author dxdiag
 */
public class OldChartModels {

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
    public static Object NIMSSummary(String selectedOrganisation, ChartType chartType) {
        if (selectedOrganisation.equals("root")) {
            // return NIMSSummary_AllCHOs(chartType);
            return NIMSSummaryChart(Scope.Global, selectedOrganisation, chartType);
        } else if (selectedOrganisation.startsWith("CHO", 0)) {
            return NIMSSummaryChart(Scope.CHO, selectedOrganisation.replaceAll("CHO:", ""), chartType);
            //  return NIMSSummary_SelectedCHO(selectedOrganisation.replaceAll("CHO:", ""),chartType);
        } else if (selectedOrganisation.startsWith("HospitalGroupsCHO", 0)) {
            return NIMSSummaryChart(Scope.CHOHospitalGroups, selectedOrganisation.replaceAll("HospitalGroupsCHO:", ""), chartType);

            //  return NIMSSummary_CHOHospitalGroups(selectedOrganisation.replaceAll("HospitalGroupsCHO:", ""),chartType);
        } else if (selectedOrganisation.startsWith("HospGroup", 0)) {
            return NIMSSummaryChart(Scope.HospitalGroup, selectedOrganisation.replaceAll("HospGroup:", ""), chartType);

            //  return NIMSSummary_SelectedHospitalGroup(selectedOrganisation.replaceAll("HospGroup:", ""), chartType);
        } else if (selectedOrganisation.startsWith("CommunityHospitalsCHO", 0)) {
            return NIMSSummaryChart(Scope.CommunityHospitals, selectedOrganisation.replaceAll("CommunityHospitalsCHO:", ""), chartType);
        } else if (selectedOrganisation.startsWith("CareHomesCHO", 0)) {
            return NIMSSummaryChart(Scope.CareHomes, selectedOrganisation.replaceAll("CareHomesCHO:", ""), chartType);
        } else {
            return null;
        }
    }
    private static Object NIMSSummaryChart(Scope scope, String selectedScopeID, ChartType chartType) {
        if (scope == Scope.Global) {//All CHOs
            String[] labels = {"CHO1", "CHO2", "CHO3", "CHO4", "CHO5", "CHO6", "CHO7", "CHO8", "CHO9"};
            Object[] countValues = DBQuery.NimsCounts();
            if (chartType == ChartType.VBarChart) {
                ChartSeries chos = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                return ChartBuilder.BuildVerBarChart("Bar Chart", "", "Community Health Organisations", "No. of NIMS Incidents", chos, ThemeColors.DarkBlue, 0, 0, true);
            } else if (chartType == ChartType.HBarChart) {
                ChartSeries chos = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                return ChartBuilder.BuildHorBarChart("Horizonal Bar Chart", "", "No. of NIMS Incidents", "Community Health Organisations", chos, ThemeColors.LightGreen, true);
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart("Pie Chart", "e", labels,"", countValues);
            }
        } else if (scope == Scope.CHO) {//A single CHO is selected
            String[] labels = {"Acute Hospitals", "Community Hospitals", "Care Homes"};
            Object[] countValues = DBQuery.NimsCounts(selectedScopeID);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries serviceProviders = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart("NIMS Summary-CHO" + selectedScopeID, "", "Health Services Providers", "No. of NIMS Incidents", serviceProviders, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart("NIMS Summary-CHO" + selectedScopeID, "", "No. of NIMS Incidents", "Health Services Providers", serviceProviders, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart("NIMS Summary-CHO" + selectedScopeID, "e", labels,"",countValues);
            }
        } else if (scope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
            String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
            Object[] countValues = DBQuery.NimsCounts(hospitalGroupNames);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(hospitalGroupNames, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart("NIMS Summary-Hospital Groups (CHO" + selectedScopeID + ")", "", "Hospital Groups", "No. of NIMS Incidents", chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart("NIMS Summary-Hospital Groups (CHO" + selectedScopeID + ")", "", "No. of NIMS Incidents", "Hospital Groups", chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart("NIMS Summary-Hospital Groups (CHO" + selectedScopeID + ")", "e", hospitalGroupNames,"", countValues);
            }
        } else if (scope == Scope.HospitalGroup) {
            Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
            List<Hospital> hospitals = (List<Hospital>) selectedHospGroup.getHospitalCollection();
            String[] hospitalNames = new String[hospitals.size()];
            for (int i = 0; i < hospitals.size(); i++) {
                hospitalNames[i] = Shorten(hospitals.get(i).getName());
            }
            Object[] countValues = DBQuery.NimsCounts(hospitals.toArray(new Hospital[hospitals.size()]));
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(hospitalNames, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart("NIMS Summary-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", "Acute Hospitals", "No. of NIMS Incidents", chartSeries, ThemeColors.DarkBlue, -50, 50, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart("NIMS Summary-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", "No. of NIMS Incidents", "Acute Hospitals", chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart("NIMS Summary-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "e", hospitalNames,"", countValues);

            }
        } else if (scope == Scope.CommunityHospitals) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
            EntityManager em = emf.createEntityManager();
            Communityhealthcareorganisation selectedCHO = (Communityhealthcareorganisation) em.createNamedQuery("Communityhealthcareorganisation.findById").setParameter("id", Integer.parseInt(selectedScopeID)).getSingleResult();
            List<Communityhospital> commHospitals = (List<Communityhospital>) selectedCHO.getCommunityhospitalCollection();
            String[] commHospitalNames = new String[commHospitals.size()];
            for (int i = 0; i < commHospitals.size(); i++) {
                commHospitalNames[i] = Shorten(commHospitals.get(i).getName());
            }

            Object[] countValues = DBQuery.NimsCounts(commHospitals.toArray(new Communityhospital[commHospitals.size()]));
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(commHospitalNames, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart("NIMS Summary-Community Hospitals(CHO" + selectedScopeID + ")", "", "Community Hospitals", "No. of NIMS Incidents", chartSeries, ThemeColors.DarkBlue, -50, 50, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart("NIMS Summary-Community Hospitals(CHO" + selectedScopeID + ")", "", "No. of NIMS Incidents", "Community Hospitals", chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart("NIMS Summary-Community Hospitals(CHO" + selectedScopeID + ")", "e", commHospitalNames,"", countValues);

            }
        } else if (scope == Scope.CareHomes) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
            EntityManager em = emf.createEntityManager();
            Communityhealthcareorganisation selectedCHO = (Communityhealthcareorganisation) em.createNamedQuery("Communityhealthcareorganisation.findById").setParameter("id", Integer.parseInt(selectedScopeID)).getSingleResult();
            List<Carehome> careHomes = (List<Carehome>) selectedCHO.getCarehomeCollection();
            String[] careHomeNames = new String[careHomes.size()];
            for (int i = 0; i < careHomes.size(); i++) {
                careHomeNames[i] = Shorten(careHomes.get(i).getName());
            }

            Object[] countValues = DBQuery.NimsCounts(careHomes.toArray(new Carehome[careHomes.size()]));
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(careHomeNames, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart("NIMS Summary-Care Homes(CHO" + selectedScopeID + ")", "", "Care Homes", "No. of NIMS Incidents", chartSeries, ThemeColors.DarkBlue, -50, 50, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart("NIMS Summary-Care Homes(CHO" + selectedScopeID + ")", "", "No. of NIMS Incidents", "Care Homes", chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart("NIMS Summary-Care Homes(CHO" + selectedScopeID + ")", "e", careHomeNames,"", countValues);

            }
        }

        return null;
    }

    public static Object NIMSAgeStats(String selectedOrganisation, ChartType chartType) {
        if (selectedOrganisation.equals("root")) {
            return NIMSAgeStatsChart(Scope.Global, selectedOrganisation, chartType);
        } else if (selectedOrganisation.startsWith("CHO", 0)) {
            return NIMSAgeStatsChart(Scope.CHO, selectedOrganisation.replaceAll("CHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospitalGroupsCHO", 0)) {
            return NIMSAgeStatsChart(Scope.CHOHospitalGroups, selectedOrganisation.replaceAll("HospitalGroupsCHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospGroup", 0)) {
            return NIMSAgeStatsChart(Scope.HospitalGroup, selectedOrganisation.replaceAll("HospGroup:", ""), chartType);

        } else if (selectedOrganisation.startsWith("CommunityHospitalsCHO", 0)) {
            return NIMSAgeStatsChart(Scope.CommunityHospitals, selectedOrganisation.replaceAll("CommunityHospitalsCHO:", ""), chartType);
        } else if (selectedOrganisation.startsWith("CareHomesCHO", 0)) {
            return NIMSAgeStatsChart(Scope.CareHomes, selectedOrganisation.replaceAll("CareHomesCHO:", ""), chartType);
        } else {
            return null;
        }
    }
    private static Object NIMSAgeStatsChart(Scope scope, String selectedScopeID, ChartType chartType) {
        String[] labels = {"<40", "40-44", "45-49", "50-54", "55-59", "60-64", ">=65"};
        String chartTitle = "NIMS Age Groups Statistics";
        String xAxisLabel = "Age Groups";
        String yAxisLabel = "No. of NIMS Incidents";
        if (scope == Scope.Global) {//All CHOs
            Object[] countValues = DBQuery.NIMSAgeStats();
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle, "e", labels,"", countValues);
            } else if (chartType == ChartType.BubbleChart) {
                countValues = DBQuery.NimsCounts();
                Object[] populationCounts = DBQuery.CHO_Populations();
                Object[] elderNIMSCounts = DBQuery.NimsCounts(65);
                String[] bubbleLabels = {"CHO1", "CHO2", "CHO3", "CHO4", "CHO5", "CHO6", "CHO7", "CHO8", "CHO9"};
                return ChartBuilder.BuildBubbleChart("NIMS Incidents and CHOs Populations<br/>(The size of the bubbles represents no. of NIMS Incidents for age >= 65)", "Total Populations per CHOs", yAxisLabel, bubbleLabels, populationCounts, countValues, elderNIMSCounts);
            }
        } else if (scope == Scope.CHO) {//A single CHO is selected
            Object[] countValues = DBQuery.NIMSAgeStats(selectedScopeID, ChartModels.Scope.CHO);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-CHO" + selectedScopeID, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-CHO" + selectedScopeID, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-CHO" + selectedScopeID, "e", labels,"", countValues);
            }
        } else if (scope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
            String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
            Object[] countValues = DBQuery.NIMSAgeStats(hospitalGroupNames);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }
        } else if (scope == Scope.HospitalGroup) {
            Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);

            Object[] countValues = DBQuery.NIMSAgeStats(new String[]{selectedHospGroup.getName()});
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, -50, 50, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "e", labels,"", countValues);

            }
        } else if (scope == Scope.CommunityHospitals) {
            Object[] countValues = DBQuery.NIMSAgeStats(selectedScopeID, ChartModels.Scope.CommunityHospitals);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Community Hospitals(CHO" + ")" + selectedScopeID, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Community HospitalsCHO" + ")" + selectedScopeID, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Community Hospitals(CHO" + ")" + selectedScopeID, "e", labels,"", countValues);
            }

        } else if (scope == Scope.CareHomes) {
            Object[] countValues = DBQuery.NIMSAgeStats(selectedScopeID, ChartModels.Scope.CareHomes);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Care Homes(CHO" + ")" + selectedScopeID, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Care Homes(CHO" + ")" + selectedScopeID, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Care Homes(CHO" + ")" + selectedScopeID, "e", labels,"", countValues);
            }
        }

        return null;
    }

    public static Object NIMSGenderStats(String selectedOrganisation, ChartType chartType) {
        if (selectedOrganisation.equals("root")) {
            return NIMSGenderStatsChart(Scope.Global, selectedOrganisation, chartType);
        } else if (selectedOrganisation.startsWith("CHO", 0)) {
            return NIMSGenderStatsChart(Scope.CHO, selectedOrganisation.replaceAll("CHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospitalGroupsCHO", 0)) {
            return NIMSGenderStatsChart(Scope.CHOHospitalGroups, selectedOrganisation.replaceAll("HospitalGroupsCHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospGroup", 0)) {
            return NIMSGenderStatsChart(Scope.HospitalGroup, selectedOrganisation.replaceAll("HospGroup:", ""), chartType);

        } else if (selectedOrganisation.startsWith("CommunityHospitalsCHO", 0)) {
            return NIMSGenderStatsChart(Scope.CommunityHospitals, selectedOrganisation.replaceAll("CommunityHospitalsCHO:", ""), chartType);
        } else if (selectedOrganisation.startsWith("CareHomesCHO", 0)) {
            return NIMSGenderStatsChart(Scope.CareHomes, selectedOrganisation.replaceAll("CareHomesCHO:", ""), chartType);
        } else {
            return null;
        }
    }
    private static Object NIMSGenderStatsChart(Scope scope, String selectedScopeID, ChartType chartType) {
        String[] labels = {"Males", "Females"};
        String chartTitle = "NIMS Gender Statistics";
        String xAxisLabel = "Gender";
        String yAxisLabel = "No. of NIMS Incidents";
        if (scope == Scope.Global) {//All CHOs
            Object[] countValues = DBQuery.NIMSGenderStats();
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle, "e", labels,"", countValues);
            }
        } else if (scope == Scope.CHO) {//A single CHO is selected
            Object[] countValues = DBQuery.NIMSGenderStats(selectedScopeID, ChartModels.Scope.CHO);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-CHO" + selectedScopeID, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-CHO" + selectedScopeID, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-CHO" + selectedScopeID, "e", labels,"", countValues);
            }
        } else if (scope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
            String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
            Object[] countValues = DBQuery.NIMSGenderStats(hospitalGroupNames);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }
        } else if (scope == Scope.HospitalGroup) {
            Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
            Object[] countValues = DBQuery.NIMSGenderStats(new String[]{selectedHospGroup.getName()});
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, -50, 50, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "e", labels,"", countValues);

            }
        } else if (scope == Scope.CommunityHospitals) {
            Object[] countValues = DBQuery.NIMSGenderStats(selectedScopeID, ChartModels.Scope.CommunityHospitals);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Community HospitalsCHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }

        } else if (scope == Scope.CareHomes) {
            Object[] countValues = DBQuery.NIMSGenderStats(selectedScopeID, ChartModels.Scope.CareHomes);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Care Homes(CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }
        }

        return null;
    }

    public static Object NIMSRecurrStats(String selectedOrganisation, ChartType chartType) {
        if (selectedOrganisation.equals("root")) {
            return NIMSRecurrStatsChart(Scope.Global, selectedOrganisation, chartType);
        } else if (selectedOrganisation.startsWith("CHO", 0)) {
            return NIMSRecurrStatsChart(Scope.CHO, selectedOrganisation.replaceAll("CHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospitalGroupsCHO", 0)) {
            return NIMSRecurrStatsChart(Scope.CHOHospitalGroups, selectedOrganisation.replaceAll("HospitalGroupsCHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospGroup", 0)) {
            return NIMSRecurrStatsChart(Scope.HospitalGroup, selectedOrganisation.replaceAll("HospGroup:", ""), chartType);

        } else if (selectedOrganisation.startsWith("CommunityHospitalsCHO", 0)) {
            return NIMSRecurrStatsChart(Scope.CommunityHospitals, selectedOrganisation.replaceAll("CommunityHospitalsCHO:", ""), chartType);
        } else if (selectedOrganisation.startsWith("CareHomesCHO", 0)) {
            return NIMSRecurrStatsChart(Scope.CareHomes, selectedOrganisation.replaceAll("CareHomesCHO:", ""), chartType);
        } else {
            return null;
        }
    }
    private static Object NIMSRecurrStatsChart(Scope scope, String selectedScopeID, ChartType chartType) {
        String[] labels = {"Rare", "Unlikely", "Possible", "Likely", "Almost Certain"};
        String chartTitle = "Recurrence Likelihood Statistics of NIMS Incidents";
        String xAxisLabel = "Recurrence Likelihood";
        String yAxisLabel = "No. of NIMS Incidents";
        if (scope == Scope.Global) {//All CHOs
            Object[] countValues = DBQuery.NIMSRecurrStats();
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle, "e", labels,"", countValues);
            }
        } else if (scope == Scope.CHO) {//A single CHO is selected
            Object[] countValues = DBQuery.NIMSRecurrStats(selectedScopeID, ChartModels.Scope.CHO);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-CHO" + selectedScopeID, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-CHO" + selectedScopeID, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-CHO" + selectedScopeID, "e", labels,"", countValues);
            }
        } else if (scope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
            String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
            Object[] countValues = DBQuery.NIMSRecurrStats(hospitalGroupNames);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }
        } else if (scope == Scope.HospitalGroup) {
            Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
            Object[] countValues = DBQuery.NIMSRecurrStats(new String[]{selectedHospGroup.getName()});
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, -50, 50, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "e", labels,"", countValues);

            }
        } else if (scope == Scope.CommunityHospitals) {
            Object[] countValues = DBQuery.NIMSRecurrStats(selectedScopeID, ChartModels.Scope.CommunityHospitals);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Community HospitalsCHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }

        } else if (scope == Scope.CareHomes) {
            Object[] countValues = DBQuery.NIMSRecurrStats(selectedScopeID, ChartModels.Scope.CareHomes);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, 0, 0, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Care Homes(CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }
        }

        return null;
    }
   
   public static Object NIMSOutcomeStats(String selectedOrganisation, ChartType chartType) {
        if (selectedOrganisation.equals("root")) {
            return NIMSOutcomeStatsChart(Scope.Global, selectedOrganisation, chartType);
        } else if (selectedOrganisation.startsWith("CHO", 0)) {
           return NIMSOutcomeStatsChart(Scope.CHO, selectedOrganisation.replaceAll("CHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospitalGroupsCHO", 0)) {
           return NIMSOutcomeStatsChart(Scope.CHOHospitalGroups, selectedOrganisation.replaceAll("HospitalGroupsCHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospGroup", 0)) {
           return NIMSOutcomeStatsChart(Scope.HospitalGroup, selectedOrganisation.replaceAll("HospGroup:", ""), chartType);

        } else if (selectedOrganisation.startsWith("CommunityHospitalsCHO", 0)) {
            return NIMSOutcomeStatsChart(Scope.CommunityHospitals, selectedOrganisation.replaceAll("CommunityHospitalsCHO:", ""), chartType);
        } else if (selectedOrganisation.startsWith("CareHomesCHO", 0)) {
            return NIMSOutcomeStatsChart(Scope.CareHomes, selectedOrganisation.replaceAll("CareHomesCHO:", ""), chartType);
        } else {
            return null;
        }
    }
   private static Object NIMSOutcomeStatsChart(Scope scope, String selectedScopeID, ChartType chartType) {
        String[] labels = {"Bruising", "Burn/Scald", "Dislocation", "Fatality", "Fracture","Graze","Haemorrhage","Laceration","Multi-Injuries","No Apparent Injury","Pain & Suffering","Serious Deterioration","Other"};
        String chartTitle = "Outcomes Statistics of NIMS Incidents";
        String xAxisLabel = "Outcome";
        String yAxisLabel = "No. of NIMS Incidents";
        int xTickAngle=-50,yTickAngle=50;

        if (scope == Scope.Global) {//All CHOs
            Object[] countValues = DBQuery.NIMSOutcomeStats();
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle, "e", labels,"", countValues);
            }
        } else if (scope == Scope.CHO) {//A single CHO is selected
            Object[] countValues = DBQuery.NIMSOutcomeStats(selectedScopeID, ChartModels.Scope.CHO);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-CHO" + selectedScopeID, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-CHO" + selectedScopeID, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-CHO" + selectedScopeID, "e", labels,"", countValues);
            }
        } else if (scope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
            String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
            Object[] countValues = DBQuery.NIMSOutcomeStats(hospitalGroupNames);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }
        } else if (scope == Scope.HospitalGroup) {
            Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
            Object[] countValues = DBQuery.NIMSOutcomeStats(new String[]{selectedHospGroup.getName()});
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, xTickAngle,yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "e", labels,"", countValues);

            }
        } else if (scope == Scope.CommunityHospitals) {
            Object[] countValues = DBQuery.NIMSOutcomeStats(selectedScopeID, ChartModels.Scope.CommunityHospitals);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Community HospitalsCHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }
        } else if (scope == Scope.CareHomes) {
            Object[] countValues = DBQuery.NIMSOutcomeStats(selectedScopeID, ChartModels.Scope.CareHomes);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.DarkBlue, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Care Homes(CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.LightGreen, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "e", labels,"" ,countValues);
            }
        }
        return null;
    }

   public static Object NIMSSeverityStats(String selectedOrganisation, ChartType chartType) {
        if (selectedOrganisation.equals("root")) {
            return NIMSSeverityStatsChart(Scope.Global, selectedOrganisation, chartType);
        } else if (selectedOrganisation.startsWith("CHO", 0)) {
           return NIMSSeverityStatsChart(Scope.CHO, selectedOrganisation.replaceAll("CHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospitalGroupsCHO", 0)) {
           return NIMSSeverityStatsChart(Scope.CHOHospitalGroups, selectedOrganisation.replaceAll("HospitalGroupsCHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospGroup", 0)) {
           return NIMSSeverityStatsChart(Scope.HospitalGroup, selectedOrganisation.replaceAll("HospGroup:", ""), chartType);

        } else if (selectedOrganisation.startsWith("CommunityHospitalsCHO", 0)) {
            return NIMSSeverityStatsChart(Scope.CommunityHospitals, selectedOrganisation.replaceAll("CommunityHospitalsCHO:", ""), chartType);
        } else if (selectedOrganisation.startsWith("CareHomesCHO", 0)) {
            return NIMSSeverityStatsChart(Scope.CareHomes, selectedOrganisation.replaceAll("CareHomesCHO:", ""), chartType);
        } else {
            return null;
        }
    }
   private static Object NIMSSeverityStatsChart(Scope scope, String selectedScopeID, ChartType chartType) {
        String[] labels = {"Low", "Minor", "Moderate", "Major", "Catastrophic"};
        String chartTitle = "Severity Statistics of NIMS Incidents";
        String xAxisLabel = "Severity";
        String yAxisLabel = "No. of NIMS Incidents";
        int xTickAngle=0,yTickAngle=0;
        if (scope == Scope.Global) {//All CHOs
            Object[] countValues = DBQuery.NIMSSeverityStats();
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle, "e", labels,"", countValues);
            }
        } else if (scope == Scope.CHO) {//A single CHO is selected
            Object[] countValues = DBQuery.NIMSSeverityStats(selectedScopeID, ChartModels.Scope.CHO);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-CHO" + selectedScopeID, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-CHO" + selectedScopeID, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-CHO" + selectedScopeID, "e", labels,"" ,countValues);
            }
        } else if (scope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
            String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
            Object[] countValues = DBQuery.NIMSSeverityStats(hospitalGroupNames);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "e", labels,"",countValues);
            }
        } else if (scope == Scope.HospitalGroup) {
            Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
            Object[] countValues = DBQuery.NIMSSeverityStats(new String[]{selectedHospGroup.getName()});
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle,yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "e", labels,"", countValues);

            }
        } else if (scope == Scope.CommunityHospitals) {
            Object[] countValues = DBQuery.NIMSSeverityStats(selectedScopeID, ChartModels.Scope.CommunityHospitals);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Community HospitalsCHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "e", labels,"",countValues);
            }
        } else if (scope == Scope.CareHomes) {
            Object[] countValues = DBQuery.NIMSSeverityStats(selectedScopeID, ChartModels.Scope.CareHomes);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Care Homes(CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "e", labels,"", countValues);
            }
        }
        return null;
    }
  
    
    public static Object NIMSRiskStats(String selectedOrganisation, ChartType chartType) {
        if (selectedOrganisation.equals("root")) {
            return NIMSRiskStatsChart(Scope.Global, selectedOrganisation, chartType);
        } else if (selectedOrganisation.startsWith("CHO", 0)) {
           return NIMSRiskStatsChart(Scope.CHO, selectedOrganisation.replaceAll("CHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospitalGroupsCHO", 0)) {
           return NIMSRiskStatsChart(Scope.CHOHospitalGroups, selectedOrganisation.replaceAll("HospitalGroupsCHO:", ""), chartType);

        } else if (selectedOrganisation.startsWith("HospGroup", 0)) {
           return NIMSRiskStatsChart(Scope.HospitalGroup, selectedOrganisation.replaceAll("HospGroup:", ""), chartType);

        } else if (selectedOrganisation.startsWith("CommunityHospitalsCHO", 0)) {
            return NIMSRiskStatsChart(Scope.CommunityHospitals, selectedOrganisation.replaceAll("CommunityHospitalsCHO:", ""), chartType);
        } else if (selectedOrganisation.startsWith("CareHomesCHO", 0)) {
            return NIMSRiskStatsChart(Scope.CareHomes, selectedOrganisation.replaceAll("CareHomesCHO:", ""), chartType);
        } else {
            return null;
        }
    }
    private static Object NIMSRiskStatsChart(Scope scope, String selectedScopeID, ChartType chartType) {
        String[] labels = {"Very Low", "Low", "High"};
        String chartTitle = "Risk Statistics of NIMS Incidents";
        String xAxisLabel = "Risk Rating";
        String yAxisLabel = "No. of NIMS Incidents";
        int xTickAngle=0,yTickAngle=0;
        if (scope == Scope.Global) {//All CHOs
            Object[] countValues = DBQuery.NIMSRiskStats();
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle, "e", labels,ThemeColors.GreenToRed ,countValues);
            }
        } else if (scope == Scope.CHO) {//A single CHO is selected
            Object[] countValues = DBQuery.NIMSRiskStats(selectedScopeID, ChartModels.Scope.CHO);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-CHO" + selectedScopeID, "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-CHO" + selectedScopeID, "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-CHO" + selectedScopeID, "e", labels,ThemeColors.GreenToRed ,countValues);
            }
        } else if (scope == Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
            String[] hospitalGroupNames = SelectedHospitalGroups(selectedScopeID);
            Object[] countValues = DBQuery.NIMSRiskStats(hospitalGroupNames);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Hospital Groups (CHO" + selectedScopeID + ")", "e", labels, ThemeColors.GreenToRed,countValues);
            }
        } else if (scope == Scope.HospitalGroup) {
            Hospitalgroup selectedHospGroup = SelectedHospitalGroup(selectedScopeID);
            Object[] countValues = DBQuery.NIMSRiskStats(new String[]{selectedHospGroup.getName()});
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle,yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Acute Hospitals(" + selectedHospGroup.getName() + " Hospital Group)", "e", labels, ThemeColors.GreenToRed,countValues);

            }
        } else if (scope == Scope.CommunityHospitals) {
            Object[] countValues = DBQuery.NIMSRiskStats(selectedScopeID, ChartModels.Scope.CommunityHospitals);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Community HospitalsCHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Community Hospitals(CHO" + selectedScopeID + ")", "e", labels,ThemeColors.GreenToRed ,countValues);
            }
        } else if (scope == Scope.CareHomes) {
            Object[] countValues = DBQuery.NIMSRiskStats(selectedScopeID, ChartModels.Scope.CareHomes);
            if (chartType == ChartType.VBarChart || chartType == ChartType.HBarChart) {
                ChartSeries chartSeries = ChartBuilder.GnenerateBarChartSeries(labels, countValues);
                if (chartType == ChartType.VBarChart) {
                    return ChartBuilder.BuildVerBarChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "", xAxisLabel, yAxisLabel, chartSeries, ThemeColors.GreenToRed, xTickAngle, yTickAngle, true);
                } else if (chartType == ChartType.HBarChart) {
                    return ChartBuilder.BuildHorBarChart(chartTitle + "-(Care Homes(CHO" + selectedScopeID + ")", "", yAxisLabel, xAxisLabel, chartSeries, ThemeColors.GreenToRed, true);
                }
            } else if (chartType == ChartType.PieChart) {
                return ChartBuilder.BuildPieChart(chartTitle + "-Care Homes(CHO" + selectedScopeID + ")", "e", labels,ThemeColors.GreenToRed ,countValues);
            }
        }
        return null;
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
    }
*/
}
