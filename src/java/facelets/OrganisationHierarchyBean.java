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
@ManagedBean(name = "OrganisationHierarchyBean", eager = false)
@RequestScoped
public class OrganisationHierarchyBean {

    /**
     * Creates a new instance of OrganisationHierarchy
     */
    public OrganisationHierarchyBean() {
    }

    public String GenerateOrganisationsTree() {
    
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List<Communityhealthcareorganisation> choAreas = (List<Communityhealthcareorganisation>) em.createNamedQuery("Communityhealthcareorganisation.findAll").getResultList();
        StringBuilder tree = new StringBuilder("<div class='easy-tree'><ul>");
        tree.append("<li id='root' class='parent_li li_selected'>Healthcare Organizations");
        //Start CHOs container
        tree.append("<ul>");
        for (int i = 0; i < choAreas.size(); i++) {
            //Start CHOs ul
            Communityhealthcareorganisation targetCHOArea = choAreas.get(i);
            //Creating tree node for a CHO Area
            String choAreaID = targetCHOArea.getId().toString();
          Integer x=  targetCHOArea.getHospitalCollection().size();
            //start cho
            tree.append("<li id='CHO:" + targetCHOArea.getId() + "'>" + targetCHOArea.getName());
            //Loading hospital groups underlying that CHO Area
            List<Hospitalgroup> hospitalGroups = (List<Hospitalgroup>) targetCHOArea.getHospitalgroupCollection();
            //start hospital groups container
            tree.append("<ul>");
            tree.append("<li id='HospitalGroupsCHO:"+targetCHOArea.getId()+"'>HospitalGroups (n=" + hospitalGroups.size() + ")");
            if (hospitalGroups != null) {
                //Start hospital groups list
                tree.append("<ul>");
                for (int j = 0; j < hospitalGroups.size(); j++) {
                    Hospitalgroup targetHospGroup = hospitalGroups.get(j);
                    //Loading hospitals
                    List<Hospital> hospitals = (List<Hospital>) targetHospGroup.getHospitalCollection();
                    //start hospital group
                    tree.append("<li id='HospGroup:" + targetHospGroup.getId() + "'>" + targetHospGroup.getName() + " (n=" + hospitals.size() + ")");

                    if (hospitals != null) {
                        //Start hospitals list
                        tree.append("<ul>");
                        for (int k = 0; k < hospitals.size(); k++) {

                            tree.append("<li id='AcuteHospital:" + hospitals.get(k).getId() + "'>" + (k + 1) + "." + hospitals.get(k).getName() + "</li>");
                        }
                        //end hospital groups list
                        tree.append("</ul>");
                    }
                    //end hospital group
                    tree.append("</li>");
                }
                //end hospital groups list
                tree.append("</ul>");
            }
            //end hospital groups container
            tree.append("</li>");

            //Start community hospital
            List<Communityhospital> communityHospitals = (List<Communityhospital>) targetCHOArea.getCommunityhospitalCollection();
            tree.append("<li id='CommunityHospitalsCHO:"+targetCHOArea.getId()+"'>Community Hospitals (n=" + communityHospitals.size() + ")");
            tree.append("<ul>");
            for (int m = 0; m < communityHospitals.size(); m++) {
                tree.append("<li id='CommunityHospital:" + communityHospitals.get(m).getId() + "'>" + (m + 1) + "." + communityHospitals.get(m).getName() + "</li>");

            }
            tree.append("</ul>");
            tree.append("</li>");
            //End community hospital

            //Start care homes
            List<Carehome> careHomes = (List<Carehome>) targetCHOArea.getCarehomeCollection();
            tree.append("<li id='CareHomesCHO:"+targetCHOArea.getId()+"'>Care Homes (n=" + careHomes.size() + ")");
            tree.append("<ul>");
            for (int m = 0; m < careHomes.size(); m++) {
                tree.append("<li id='Carehome:" + careHomes.get(m).getId() + "'>" + (m + 1) + "." + careHomes.get(m).getName() + "</li>");
            }
            tree.append("</ul>");
            tree.append("</li>");
            //End care homes

            //Start care homes
            List<Primarycareteam> primaryTeams = (List<Primarycareteam>) targetCHOArea.getPrimarycareteamCollection();
            tree.append("<li id='PCTsCHO:"+targetCHOArea.getId()+"'>Primary Care Teams(PCTs) (n=" + primaryTeams.size() + ")");
            tree.append("<ul>");
            for (int m = 0; m < primaryTeams.size(); m++) {
                tree.append("<li id='PCT:" + primaryTeams.get(m).getId() + "'>" + (m + 1) + "." + primaryTeams.get(m).getName() + "</li>");
            }
            tree.append("</ul>");
            tree.append("</li>");
            //End care homes

            //end cho items
            tree.append("</ul>");
            //end cho
            tree.append("</li>");
        }
        //End CHOs ul       
        tree.append("</ul>");
        //end cho containers
        tree.append("</li>");
        //end tree
        tree.append("</ul></div> ");
        System.out.println(tree.toString());
        em.close();
        emf.close();
        return tree.toString();
    }
}
