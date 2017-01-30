/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facelets;

import com.sun.org.apache.xerces.internal.parsers.IntegratedParserConfiguration;
import entityclasses.County;
import entityclasses.Hospital;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author dxdiag
 */
@ManagedBean
@SessionScoped
public class MapViewer {

    /**
     * Creates a new instance of MapViewer
     */
    public MapViewer() {
    }
    public String ViewHospitalMarkers()
    {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
         List<Hospital> hospitals  =  em.createNamedQuery("Hospital.findAll").getResultList();
        
         StringBuilder json=new StringBuilder("[");
        for (int i = 0; i < hospitals.size(); i++) {
            if (hospitals.get(i).getLatitude() != 0 && hospitals.get(i).getLongitude() != 0) {
                json.append("{");
                json.append("'title':'" + hospitals.get(i).getName() + "',");
                json.append("'county':'" + hospitals.get(i).getCountyid().getName() + "',");
                json.append("'lat':" + hospitals.get(i).getLatitude() + ",");
                json.append("'lng':" + hospitals.get(i).getLongitude());
                json.append("},");
            }
        }
 json.deleteCharAt(json.lastIndexOf(","));
 json.append("]");
   return json.toString();
    }
}
