package Temp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import entityclasses.Carehome;
import entityclasses.Communityhealthcareorganisation;
import entityclasses.Communityhospital;
import entityclasses.Hospital;
import entityclasses.Hospitalgroup;
import entityclasses.Primarycarenetwork;
import entityclasses.Primarycareteam;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author 13232238
 */
@ManagedBean(name = "TreeviewBean")
@RequestScoped
public class TreeviewBean {

    /**
     * Creates a new instance of TreeviewBean
     */
    public TreeviewBean() {
       
    }


    public String getOrganisationHierarchy() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List<Communityhealthcareorganisation> choAreas = (List<Communityhealthcareorganisation>) em.createNamedQuery("Communityhealthcareorganisation.findAll").getResultList();
        StringBuilder tree = new StringBuilder("[");
        tree.append("{id:0,pId:0, name:'Healthcare Organisations Hierarchy', open:true},");
        for (int i = 0; i < choAreas.size(); i++) {
            Communityhealthcareorganisation targetCHOArea = choAreas.get(i);
            //Creating tree node for a CHO Area
            String choAreaID = targetCHOArea.getId().toString();
            tree.append("{id:" + choAreaID + ", pId:0, name:'CHO-" + targetCHOArea.getName() + "',type:'CHO" + choAreaID + "'},");

            //Loading hospital groups underlying that CHO Area
            List<Hospitalgroup> hospitalGroups = (List<Hospitalgroup>) targetCHOArea.getHospitalgroupCollection();
            //Creating a grouping node for Hospital Groups
            String groupingNodeID = "1";
            tree.append("{id:" + choAreaID + groupingNodeID + ", pId:" + choAreaID + ",name:'Hospital Groups (n=" + hospitalGroups.size() + ")',type:'text'},");

            if (hospitalGroups != null) {
                for (int j = 0; j < hospitalGroups.size(); j++) {
                    Hospitalgroup targetHospGroup = hospitalGroups.get(j);
                    List<Hospital> hospitals = (List<Hospital>) targetHospGroup.getHospitalCollection();
                    //Creating tree node for a hospital group
                    String hospGroupID = targetHospGroup.getId().toString();
                    tree.append("{id:" + choAreaID + groupingNodeID + hospGroupID + ", pId:" + choAreaID + groupingNodeID + ", name:'" + targetHospGroup.getName() + " (n=" + hospitals.size() + ")',type:'HospitalGroup" + hospGroupID + "'},");
                    if (hospitals != null) {
                        for (int k = 0; k < hospitals.size(); k++) {
                            Hospital targetHospital = hospitals.get(k);
                            //Creating tree node for an acute hospital
                            String hospitalID = targetHospital.getId().toString();
                            tree.append("{id:" + choAreaID + groupingNodeID + hospGroupID + hospitalID + ", pId:" + choAreaID + groupingNodeID + hospGroupID + ", name:\"" + Integer.toString(k + 1) + "." + targetHospital.getName() + "\",type:'AcuteHospital" + hospitalID + "'},");
                        }
                    }
                }
            }

            //Loading community hospitals underlying that CHO Area
            List<Communityhospital> communityHospitals = (List<Communityhospital>) targetCHOArea.getCommunityhospitalCollection();
            //Creating a grouping node of all community hospitals
            String groupingNodeID2 = "2";
            tree.append("{id:" + choAreaID + groupingNodeID2 + ", pId:" + choAreaID + ",name:'Community Hospitals (n=" + communityHospitals.size() + ")',type:'text'},");
            if (communityHospitals != null) {
                for (int m = 0; m < communityHospitals.size(); m++) {
                    Communityhospital targetCommHospital = communityHospitals.get(m);
                    //Creating tree node for a community hospital
                    String commHospitalID = targetCommHospital.getId().toString();
                    tree.append("{id:" + choAreaID + groupingNodeID2 + commHospitalID + ", pId:" + choAreaID + groupingNodeID2 + ", name:\"" + Integer.toString(m + 1) + "." + targetCommHospital.getName() + "\",type:'CommunityHospital" + commHospitalID + "'},");
                }
            }

            //Loading community hospitals underlying that CHO Area
            List<Carehome> careHomes = (List<Carehome>) targetCHOArea.getCarehomeCollection();
            //Creating a grouping node of all care homes
            String groupingNodeID3 = "3";
            tree.append("{id:" + choAreaID + groupingNodeID3 + ", pId:" + choAreaID + ",name:'Care Homes (n=" + careHomes.size() + ")',type:'text'},");
            if (careHomes != null) {
                for (int n = 0; n < careHomes.size(); n++) {
                    Carehome targetCareHome = careHomes.get(n);
                    //Creating tree node for a care home
                    String careHomeID = targetCareHome.getId().toString();
                    tree.append("{id:" + choAreaID + groupingNodeID3 + careHomeID + ", pId:" + choAreaID + groupingNodeID3 + ", name:\"" + Integer.toString(n + 1) + "." + targetCareHome.getName() + "\",type:'CareHome" + careHomeID + "'},");
                }
            }

            //Loading Primary Care Teams(PCTs) underlying that CHO Area
            List<Primarycareteam> PCTs = (List<Primarycareteam>) targetCHOArea.getPrimarycareteamCollection();
            //Creating a grouping node of all PCTS
            String groupingNodeID4 = "4";
            tree.append("{id:" + choAreaID + groupingNodeID4 + ", pId:" + choAreaID + ",name:'Primary Care Teams(PCTs) (n=" + PCTs.size() + ")',type:'text'},");
            if (PCTs != null) {
                for (int t = 0; t < PCTs.size(); t++) {
                    Primarycareteam targetPriamryTeam = PCTs.get(t);
                    //Creating tree node for a care home
                    String priamryTeamID = targetPriamryTeam.getId().toString();
                    tree.append("{id:" + choAreaID + groupingNodeID4 + priamryTeamID + ", pId:" + choAreaID + groupingNodeID4 + ", name:\"" + Integer.toString(t + 1) + "." + targetPriamryTeam.getName() + "\",type:'PCT" + priamryTeamID + "'},");
                }
            }
        }
        tree.append("]");
        em.close();
        emf.close();
        return tree.toString();
    }

    
        public String getOrganisationTree() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List<Communityhealthcareorganisation> choAreas = (List<Communityhealthcareorganisation>) em.createNamedQuery("Communityhealthcareorganisation.findAll").getResultList();
        StringBuilder tree = new StringBuilder("<ul><li id='root'>Healthcare Organizations");
    
        for (int i = 0; i < choAreas.size(); i++) {
            //Start CHOs ul
            if(i==0)tree.append("<ul>"); 
            Communityhealthcareorganisation targetCHOArea = choAreas.get(i);
            //Creating tree node for a CHO Area
            String choAreaID = targetCHOArea.getId().toString();
         tree.append("<li>"+targetCHOArea.getName()+"</li>");
         
         //End CHOs ul       
         tree.append("</ul>");

         // tree.append("{id:" + choAreaID + ", pId:0, name:'CHO-" + targetCHOArea.getName() + "',type:'CHO" + choAreaID + "'},");

            //Loading hospital groups underlying that CHO Area
            List<Hospitalgroup> hospitalGroups = (List<Hospitalgroup>) targetCHOArea.getHospitalgroupCollection();
            //Creating a grouping node for Hospital Groups
            String groupingNodeID = "1";
            tree.append("{id:" + choAreaID + groupingNodeID + ", pId:" + choAreaID + ",name:'Hospital Groups (n=" + hospitalGroups.size() + ")',type:'text'},");

            if (hospitalGroups != null) {
                for (int j = 0; j < hospitalGroups.size(); j++) {
                    Hospitalgroup targetHospGroup = hospitalGroups.get(j);
                    List<Hospital> hospitals = (List<Hospital>) targetHospGroup.getHospitalCollection();
                    //Creating tree node for a hospital group
                    String hospGroupID = targetHospGroup.getId().toString();
                    tree.append("{id:" + choAreaID + groupingNodeID + hospGroupID + ", pId:" + choAreaID + groupingNodeID + ", name:'" + targetHospGroup.getName() + " (n=" + hospitals.size() + ")',type:'HospitalGroup" + hospGroupID + "'},");
                    if (hospitals != null) {
                        for (int k = 0; k < hospitals.size(); k++) {
                            Hospital targetHospital = hospitals.get(k);
                            //Creating tree node for an acute hospital
                            String hospitalID = targetHospital.getId().toString();
                            tree.append("{id:" + choAreaID + groupingNodeID + hospGroupID + hospitalID + ", pId:" + choAreaID + groupingNodeID + hospGroupID + ", name:\"" + Integer.toString(k + 1) + "." + targetHospital.getName() + "\",type:'AcuteHospital" + hospitalID + "'},");
                        }
                    }
                }
            }

            //Loading community hospitals underlying that CHO Area
            List<Communityhospital> communityHospitals = (List<Communityhospital>) targetCHOArea.getCommunityhospitalCollection();
            //Creating a grouping node of all community hospitals
            String groupingNodeID2 = "2";
            tree.append("{id:" + choAreaID + groupingNodeID2 + ", pId:" + choAreaID + ",name:'Community Hospitals (n=" + communityHospitals.size() + ")',type:'text'},");
            if (communityHospitals != null) {
                for (int m = 0; m < communityHospitals.size(); m++) {
                    Communityhospital targetCommHospital = communityHospitals.get(m);
                    //Creating tree node for a community hospital
                    String commHospitalID = targetCommHospital.getId().toString();
                    tree.append("{id:" + choAreaID + groupingNodeID2 + commHospitalID + ", pId:" + choAreaID + groupingNodeID2 + ", name:\"" + Integer.toString(m + 1) + "." + targetCommHospital.getName() + "\",type:'CommunityHospital" + commHospitalID + "'},");
                }
            }

            //Loading community hospitals underlying that CHO Area
            List<Carehome> careHomes = (List<Carehome>) targetCHOArea.getCarehomeCollection();
            //Creating a grouping node of all care homes
            String groupingNodeID3 = "3";
            tree.append("{id:" + choAreaID + groupingNodeID3 + ", pId:" + choAreaID + ",name:'Care Homes (n=" + careHomes.size() + ")',type:'text'},");
            if (careHomes != null) {
                for (int n = 0; n < careHomes.size(); n++) {
                    Carehome targetCareHome = careHomes.get(n);
                    //Creating tree node for a care home
                    String careHomeID = targetCareHome.getId().toString();
                    tree.append("{id:" + choAreaID + groupingNodeID3 + careHomeID + ", pId:" + choAreaID + groupingNodeID3 + ", name:\"" + Integer.toString(n + 1) + "." + targetCareHome.getName() + "\",type:'CareHome" + careHomeID + "'},");
                }
            }

            //Loading Primary Care Teams(PCTs) underlying that CHO Area
            List<Primarycareteam> PCTs = (List<Primarycareteam>) targetCHOArea.getPrimarycareteamCollection();
            //Creating a grouping node of all PCTS
            String groupingNodeID4 = "4";
            tree.append("{id:" + choAreaID + groupingNodeID4 + ", pId:" + choAreaID + ",name:'Primary Care Teams(PCTs) (n=" + PCTs.size() + ")',type:'text'},");
            if (PCTs != null) {
                for (int t = 0; t < PCTs.size(); t++) {
                    Primarycareteam targetPriamryTeam = PCTs.get(t);
                    //Creating tree node for a care home
                    String priamryTeamID = targetPriamryTeam.getId().toString();
                    tree.append("{id:" + choAreaID + groupingNodeID4 + priamryTeamID + ", pId:" + choAreaID + groupingNodeID4 + ", name:\"" + Integer.toString(t + 1) + "." + targetPriamryTeam.getName() + "\",type:'PCT" + priamryTeamID + "'},");
                }
            }
        }
        tree.append("]");
        em.close();
        emf.close();
        return tree.toString();
    }

}


//In case of the presence of PCNs
/*
//Creating a grouping node of all PCNs
            String groupingNodeID4 = "4";
            tree.append("{id:" + choAreaID + groupingNodeID4 + ", pId:" + choAreaID + ",name:'Primary Care Network(PCNs) (n=" + PCNs.size() + ")',type:'text'},");
            if (PCNs != null) {
                for (int t = 0; t < PCNs.size(); t++) {
                    Primarycarenetwork targetPriamryNetwork = PCNs.get(t);
                    //Creating tree node for a care home
                    String priamryNetworkID = targetPriamryNetwork.getId().toString();
                    tree.append("{id:" + choAreaID + groupingNodeID4 + priamryNetworkID + ", pId:" + choAreaID + groupingNodeID4 + ", name:\"" + Integer.toString(t + 1) + "." + targetPriamryNetwork.getName() + "\",type:'PCN" + priamryNetworkID + "'},");

                    //Loading Primary Care Teams(PCTs) underlying that PCN
                    List<Primarycareteam> PCTs = (List<Primarycareteam>) targetPriamryNetwork.getPrimarycareteamCollection();
                    //Creating a grouping node of all care homes
                    String groupingNodeID5 = "5";
                    tree.append("{id:" + choAreaID + groupingNodeID4+ priamryNetworkID + groupingNodeID5 + ", pId:" + choAreaID + groupingNodeID4+priamryNetworkID + ",name:'Primary Care Teams(PCTs) (n=" + PCTs.size() + ")',type:'text'},");
                    if (PCTs != null) {
                        for (int x = 0; x < PCTs.size(); x++) {
                            Primarycareteam targetPriamryTeam = PCTs.get(x);
                            //Creating tree node for a primary care team
                            String priamryTeamID = targetPriamryTeam.getId().toString();
                            tree.append("{id:" + choAreaID + groupingNodeID4+ priamryNetworkID + groupingNodeID5  + priamryTeamID + ", pId:" + choAreaID + groupingNodeID4+ priamryNetworkID + groupingNodeID5 + ", name:\"" + Integer.toString(x + 1) + "." + targetPriamryTeam.getName() + "\",type:'PCT" + priamryTeamID + "'},");

                        }

                    }

                }
            }

*/
