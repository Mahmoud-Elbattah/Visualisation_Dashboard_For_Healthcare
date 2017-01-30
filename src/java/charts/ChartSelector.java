/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charts;
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
import common.CommonObjects;

public class ChartSelector {

    public enum ChartType {
        VBarChart, HBarChart, PieChart, BubbleChart
    };
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
        Object[] scope = CommonObjects.SetSelectedScope(selectedOrganisation);
        CommonObjects.Scope selecetedScope = (CommonObjects.Scope) scope[0];
        String selectedScopeID = scope[1].toString();
        ChartParams chartParams = new ChartParams();

        switch (targetChart) {
            case "NIMSSummary":
                //Global parameters applied to all charts regardless of selected scope
                chartParams.ChartTitle = "NIMS Incidents Statisitcs";
                chartParams.YAxisLabel = "No. of NIMS Incidents";
                if (selecetedScope == CommonObjects.Scope.Global) {//All CHOs
                    chartParams.XAxisLabel = "Community Health Organisations";
                    chartParams.XValues = new String[]{"CHO1", "CHO2", "CHO3", "CHO4", "CHO5", "CHO6", "CHO7", "CHO8", "CHO9"};
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                } else if (selecetedScope == CommonObjects.Scope.CHO) {//All CHOs
                    chartParams.ChartTitle += "-CHO" + selectedScopeID;
                    chartParams.XAxisLabel = "Health Services Providers";
                    chartParams.XValues = new String[]{"Acute Hospitals", "Community Hospitals", "Care Homes"};
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups) {
                    chartParams.ChartTitle += "-Hospital Groups (CHO" + scope[1] + ")";
                    chartParams.XAxisLabel = "Hospital Groups";
                    chartParams.XValues = CommonObjects.SelectedHospitalGroups(selectedScopeID);
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, chartParams.XValues);
                } else if (selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = CommonObjects.SelectedHospitalGroup(selectedScopeID);
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
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, hospitals.toArray(new Hospital[hospitals.size()]));
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
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
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, commHospitals.toArray(new Communityhospital[commHospitals.size()]));
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
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
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, careHomes.toArray(new Carehome[careHomes.size()]));
                }
                break;
            case "NIMSAgeStats":
            case "NIMSGenderStats":
            case "NIMSRecurStats":
            case "NIMSSeverity":
            case "NIMSOutcomes":
            case "NIMSRisk":
            case "IHFDAdmissionSource":
            case "IHFDDischargeCode":
            case "IHFDAdmDischarge":
            case "IHFDDischargeStatus":
            case "IHFDAdmissionType":
            case "IHFDAgeGroups":
            case "IHFDGender":
            case "IHFDTraumaType":
            case "IHFDFractureType":
            case "IHFDFallAssessment":
            case "IHFDFragilityHistory":
            case "IHFDMultiAssessment":
            case "IHFDTimeToSurgery48hrs":
            case "IHFDAgeLOS":
                //Global parameters applied to all charts regardless of selected scope
                if (targetChart.equals("NIMSAgeStats")) {
                    chartParams.ChartTitle = "NIMS Age Profile";
                    chartParams.XAxisLabel = "Age Groups";
                    chartParams.YAxisLabel = "No. of NIMS Incidents";
                    chartParams.XValues = new String[]{"<40", "40-44", "45-49", "50-54", "55-59", "60-64", ">=65"};
                } else if (targetChart.equals("NIMSGenderStats")) {
                    chartParams.ChartTitle = "NIMS Gender Profile";
                    chartParams.XAxisLabel = "Gender";
                    chartParams.YAxisLabel = "No. of NIMS Incidents";
                    chartParams.VerSeriesColors = "3498db,D2527F";
                    chartParams.HorSeriesColors = "3498db,D2527F";
                    chartParams.PieColors = "3498db,D2527F";
                    chartParams.XValues = new String[]{"Males", "Females"};
                } else if (targetChart.equals("NIMSRecurStats")) {
                    chartParams.ChartTitle = "Recurrence Likelihood Statistics of NIMS Incidents";
                    chartParams.XAxisLabel = "Recurrence Likelihood";
                    chartParams.YAxisLabel = "No. of NIMS Incidents";
                    chartParams.XValues = new String[]{"Rare", "Unlikely", "Possible", "Likely", "Almost Certain"};
                } else if (targetChart.equals("NIMSSeverity")) {
                    chartParams.ChartTitle = "Severity Statistics of NIMS Incidents";
                    chartParams.XAxisLabel = "Incident Severity";
                    chartParams.YAxisLabel = "No. of NIMS Incidents";
                    chartParams.VerSeriesColors = ThemeColors.GreenToRed;
                    chartParams.HorSeriesColors = ThemeColors.GreenToRed;
                    chartParams.XValues = new String[]{"Low", "Minor", "Moderate", "Major", "Extreme"};
                } else if (targetChart.equals("NIMSOutcomes")) {
                    chartParams.ChartTitle = "Statistics of NIMS Incidents Outcomes";
                    chartParams.XAxisLabel = "Outcome";
                    chartParams.YAxisLabel = "No. of NIMS Incidents";
                    chartParams.XValues = new String[]{"Bruising", "Burn/Scald", "Dislocation", "Fatality", "Fracture", "Graze", "Haemorrhage", "Laceration", "Multi-Injuries", "No Apparent Injury", "Pain & Suffering", "Serious Deterioration", "Other"};
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                } else if (targetChart.equals("NIMSRisk")) {
                    chartParams.ChartTitle = "Risk Statistics of NIMS Incidents";
                    chartParams.XAxisLabel = "Risk Rating";
                    chartParams.YAxisLabel = "No. of NIMS Incidents";
                    chartParams.VerSeriesColors = ThemeColors.GreenToRed;
                    chartParams.HorSeriesColors = ThemeColors.GreenToRed;
                    chartParams.PieColors = ThemeColors.GreenToRed;
                    chartParams.XValues = new String[]{"Very Low", "Low", "High"};
                } 
                //Start IHFD Charts
                else if (targetChart.equals("IHFDAdmissionSource")) {
                    chartParams.ChartTitle = "IHFD Admission Source Statistics";
                    chartParams.XAxisLabel = "Admission Source";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"Home", "Nursing Home", "Acute Hospital", "Non-Acute Hospital", "Hospice", "Psychiatric Unit", "New Born", "Temporary Residence","Prison"};
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                }
                else if (targetChart.equals("IHFDDischargeCode")) {
                    chartParams.ChartTitle = "IHFD Discharge Code Statistics";
                    chartParams.XAxisLabel = "Discharge Code";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"Self-Discharge", "Home", "Nursing Home", "Transfer to Hospital-Emergency", "Transfer to Hospital-Non Emergency", "Transfer to Psychiatric Unit", "Died with Post Mortem", "Died No Post Mortem", "Transfer to Non-Acute Hospital-Emergency", "Transfer to Non-Acute Hospital-Non Emergency", "Transfer to Rehabilitation Facility", "Hospice", "Prison", "Absconded", "Temporary Place of Residence", "Other"};
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                }
                else if (targetChart.equals("IHFDDischargeStatus")) {
                    chartParams.ChartTitle = "IHFD Discharge Status Statistics";
                    chartParams.XAxisLabel = "Discharge Status";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"Private", "Public"};
                }
               else if (targetChart.equals("IHFDAdmDischarge")) {
                    chartParams.ChartTitle = "IHFD Admission Source against Discharge Destination Statistics";
                    chartParams.XAxisLabel = "Admission-Discharge";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"Home To Home", "Home To Nursing Home","Home To Hospital"};
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
               }
                else if (targetChart.equals("IHFDAdmissionType")) {
                    chartParams.ChartTitle = "IHFD Admission Type Statistics";
                    chartParams.XAxisLabel = "Admission Type";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"Elective", "Elective Readmission", "Emergency", "Emergency Readmission", "Maternity", "New Born"};
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                } else if (targetChart.equals("IHFDAgeGroups")) {
                    chartParams.ChartTitle = "IHFD Age Profile";
                    chartParams.XAxisLabel = "Age Groups";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"<40", "40-44", "45-49", "50-54", "55-59", "60-64", ">=65"};
                }
                  else if (targetChart.equals("IHFDGender")) {
                    chartParams.ChartTitle = "IHFD Gender Profile";
                    chartParams.XAxisLabel = "Gender";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.VerSeriesColors = "3498db,D2527F";
                    chartParams.HorSeriesColors = "3498db,D2527F";
                    chartParams.PieColors = "3498db,D2527F";
                    chartParams.XValues = new String[]{"Males", "Females"};
                }
                else if (targetChart.equals("IHFDTraumaType")) {
                    chartParams.ChartTitle = "IHFD Trauma Type Statistics";
                    chartParams.XAxisLabel = "Trauma Type";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                    chartParams.XValues = new String[]{"High Energy Trauma", "Low Energy Trauma","Unknown","Not Documented"};
                }
                else if (targetChart.equals("IHFDFractureType")) {
                    chartParams.ChartTitle = "IHFD Fracture Type Statistics";
                    chartParams.XAxisLabel = "Fracture Type";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                    chartParams.XValues = new String[]{"Intracapsular-Displaced", "Intracapsular-Undisplaced", "Intertrochanteric", "Subtrochanteric","Other","Not Documented"};
                }
                 else  if (targetChart.equals("IHFDFallAssessment")) {
                    chartParams.ChartTitle = "Falls Assessment Statistics";
                    chartParams.XAxisLabel = "Specialist Falls Assessment";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"Yes-Performed on Admission", "No"};
                }
                 else if (targetChart.equals("IHFDFragilityHistory")) {
                    chartParams.ChartTitle = "Fragility History of Patients Statistics";
                    chartParams.XAxisLabel = "Fragility History";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XTickAngle = -50;
                    chartParams.YTickAngle = 50;
                    chartParams.XValues = new String[]{"Yes-Previous Low Trauma Fractures", "No Fractures","Not Documented"};
                }  
               else if (targetChart.equals("IHFDMultiAssessment")) {
                    chartParams.ChartTitle = "Multidisciplinary Assessment of Patients with Fragility History";
                    chartParams.XAxisLabel = "Multidisciplinary Assessment";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"Yes", "No"};
                }
               else if (targetChart.equals("IHFDTimeToSurgery48hrs")) {
                    chartParams.ChartTitle = "IHFD Time To Surgery within 48hrs of Admission";
                    chartParams.XAxisLabel = "Time To Surgery within 48hrs?";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"Yes", "No"};
                }
               else if (targetChart.equals("IHFDAgeLOS")) {
                    chartParams.ChartTitle = "IHFD LOS against Age Groups";
                    chartParams.XAxisLabel = "Age Groups";
                    chartParams.YAxisLabel = "No. of Patients";
                    chartParams.XValues = new String[]{"<40", "40-44", "45-49", "50-54", "55-59", "60-64", ">=65"};
                }
                if (selecetedScope == CommonObjects.Scope.Global) {
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                } else if (selecetedScope == CommonObjects.Scope.CHO) {
                    chartParams.ChartTitle += "(CHO" + selectedScopeID + ")";
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups) {//All hospital groups for a particular CHO
                    chartParams.ChartTitle += "-Hospital Groups" + "(CHO" + selectedScopeID + ")";
                    String[] hospitalGroupNames = CommonObjects.SelectedHospitalGroups(selectedScopeID);
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, hospitalGroupNames);
                } else if (selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = CommonObjects.SelectedHospitalGroup(selectedScopeID);
                    chartParams.ChartTitle += "(" + selectedHospGroup.getName() + " Hospital Group)";
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, new String[]{selectedHospGroup.getName()});

                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
                    chartParams.ChartTitle += "-Community Hospitals" + "(CHO" + selectedScopeID + ")";
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
                    chartParams.ChartTitle += "-Care Homes" + "(CHO" + selectedScopeID + ")";
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                }
                else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                    chartParams.ChartTitle += "-" + GetHospitalName(selectedScopeID) ;
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                }
                break;
             case "IHFDLengthofStay":
                //Global parameters applied to all charts regardless of selected scope
                chartParams.ChartTitle = "IHFD Average Length of Stay Statisitcs";
                chartParams.YAxisLabel = "Average LOS (Days)";
                if (selecetedScope == CommonObjects.Scope.Global) {//All CHOs
                    chartParams.XAxisLabel = "Community Health Organisations";
                    chartParams.XValues = new String[]{"CHO1", "CHO2", "CHO3", "CHO4", "CHO5", "CHO6", "CHO7", "CHO8", "CHO9"};
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                } else if (selecetedScope == CommonObjects.Scope.CHO||selecetedScope == CommonObjects.Scope.CHOHospitalGroups) {//All CHOs
                  chartParams.ChartTitle += "-Hospital Groups (CHO" + scope[1] + ")";
                    chartParams.XAxisLabel = "Hospital Groups";
                    chartParams.XValues = CommonObjects.SelectedHospitalGroups(selectedScopeID);
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, chartParams.XValues);
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups) {
                    chartParams.ChartTitle += "-Hospital Groups (CHO" + scope[1] + ")";
                    chartParams.XAxisLabel = "Hospital Groups";
                    chartParams.XValues = CommonObjects.SelectedHospitalGroups(selectedScopeID);
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, chartParams.XValues);
                } else if (selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = CommonObjects.SelectedHospitalGroup(selectedScopeID);
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
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, hospitals.toArray(new Hospital[hospitals.size()]));
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
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
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, commHospitals.toArray(new Communityhospital[commHospitals.size()]));
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
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
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, careHomes.toArray(new Carehome[careHomes.size()]));
                }
                 else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                     chartParams.XAxisLabel = "Acute Hospital";
                     String hospitalName = GetHospitalName(selectedScopeID);
                     chartParams.ChartTitle += "-" + hospitalName;
                     chartParams.XValues = new String[]{hospitalName};
                     chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                 }
                break;
            case "IHFDTimeToSurgery":
                //Global parameters applied to all charts regardless of selected scope
                chartParams.ChartTitle = "IHFD Average Elapsed Time to Primary Surgery Statisitcs";
                chartParams.YAxisLabel = "Average Elapsed Time (Days)";
                if (selecetedScope == CommonObjects.Scope.Global) {//All CHOs
                    chartParams.XAxisLabel = "Community Health Organisations";
                    chartParams.XValues = new String[]{"CHO1", "CHO2", "CHO3", "CHO4", "CHO5", "CHO6", "CHO7", "CHO8", "CHO9"};
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                } else if (selecetedScope == CommonObjects.Scope.CHO||selecetedScope == CommonObjects.Scope.CHOHospitalGroups) {//All CHOs
                  chartParams.ChartTitle += "-Hospital Groups (CHO" + scope[1] + ")";
                    chartParams.XAxisLabel = "Hospital Groups";
                    chartParams.XValues = CommonObjects.SelectedHospitalGroups(selectedScopeID);
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, chartParams.XValues);
                } else if (selecetedScope == CommonObjects.Scope.CHOHospitalGroups) {
                    chartParams.ChartTitle += "-Hospital Groups (CHO" + scope[1] + ")";
                    chartParams.XAxisLabel = "Hospital Groups";
                    chartParams.XValues = CommonObjects.SelectedHospitalGroups(selectedScopeID);
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, chartParams.XValues);
                } else if (selecetedScope == CommonObjects.Scope.HospitalGroup) {
                    Hospitalgroup selectedHospGroup = CommonObjects.SelectedHospitalGroup(selectedScopeID);
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
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, hospitals.toArray(new Hospital[hospitals.size()]));
                } else if (selecetedScope == CommonObjects.Scope.CommunityHospitals) {
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
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, commHospitals.toArray(new Communityhospital[commHospitals.size()]));
                } else if (selecetedScope == CommonObjects.Scope.CareHomes) {
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
                    chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope, careHomes.toArray(new Carehome[careHomes.size()]));
                }
                 else if (selecetedScope == CommonObjects.Scope.AcuteHospital) {
                     chartParams.XAxisLabel = "Acute Hospital";
                     String hospitalName = GetHospitalName(selectedScopeID);
                     chartParams.ChartTitle += "-" + hospitalName;
                     chartParams.XValues = new String[]{hospitalName};
                     chartParams.YValues = ChartDBQuery.ExecuteQuery(targetChart, scope);
                 }
                break;
        }
        return chartParams;
    }
//Miscellaneous functions

    private static String GetHospitalName(String selectedScopeID) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        Hospital selectedHospital = (Hospital) em.createNamedQuery("Hospital.findById").setParameter("id", Integer.parseInt(selectedScopeID)).getSingleResult();
        return selectedHospital.getName();
    }
    private static Communityhealthcareorganisation GetSelectedCHO(String selectedScopeID) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        Communityhealthcareorganisation selectedCHO = (Communityhealthcareorganisation) em.createNamedQuery("Communityhealthcareorganisation.findById").setParameter("id", Integer.parseInt(selectedScopeID)).getSingleResult();
        return selectedCHO;
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
}
