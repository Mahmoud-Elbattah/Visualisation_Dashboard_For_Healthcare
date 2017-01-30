/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facelets;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.primefaces.context.RequestContext;

/**
 *
 * @author 13232238
 */
@ManagedBean(name = "SummaryStats", eager = false)
@RequestScoped
public class SummaryStats {

    public String chosNo;
    public String hospitalGroupsNo;
    public String hospitalsNo;
    public String commHospitalsNo;
    public String careHomesNo;
    public String pctsNo;
    public String totalPopulation;
    public String targetCHO;



    public String getChosNo() {
        return chosNo;
    }

    public String getHospitalGroupsNo() {
        return hospitalGroupsNo;
    }

    public String getHospitalsNo() {
        return hospitalsNo;
    }

    public String getCommHospitalsNo() {
        return commHospitalsNo;
    }

    public String getTotalPopulation() {
        return totalPopulation;
    }

    public String getCareHomesNo() {
        return careHomesNo;
    }

    public String getPctsNo() {
        return pctsNo;
    }

    public void setPctsNo(String pctsNo) {
        this.pctsNo = pctsNo;
    }
    public String getTargetCHO() {
        return targetCHO;
    }

    public void setTargetCHO(String targetCHO) {
        this.targetCHO = targetCHO;
    }
//Adds comma separators to numbers
    private String FormatNumber(Integer number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

    public SummaryStats() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
        EntityManager em = emf.createEntityManager();
        List summaryCounts = em.createNativeQuery("SELECT (SELECT count(*) FROM communityhealthcareorganisation) AS choCount,(Select count(*) from hospitalgroup) as hospitalgroupCount,(SELECT count(*) FROM   hospital) AS hospitalCount,(SELECT count(*) FROM   communityhospital) AS commHospitalCount, (SELECT count(*) FROM   carehome) AS carehomeCount,( SELECT count(*) FROM   primarycareteam ) AS pctCount, ( select sum(Population) from communityhealthcareorganisation) as totalPopulation").getResultList();
        if (summaryCounts.size() > 0) {
            Object[] counts = (Object[]) summaryCounts.get(0);
            this.chosNo = FormatNumber(Integer.parseInt(counts[0].toString()));
            this.hospitalGroupsNo = FormatNumber(Integer.parseInt(counts[1].toString()));
            this.hospitalsNo = FormatNumber(Integer.parseInt(counts[2].toString()));
            this.commHospitalsNo = FormatNumber(Integer.parseInt(counts[3].toString()));
            this.careHomesNo = FormatNumber(Integer.parseInt(counts[4].toString()));
            this.pctsNo = FormatNumber(Integer.parseInt(counts[5].toString()));
            this.totalPopulation = FormatNumber(Integer.parseInt(counts[6].toString()));
        }
        em.close();
        emf.close();
    }

    private String Percentage(Object sub, Object total) {
        if (Float.parseFloat(total.toString()) == 0) {
            return "0";
        } else {
            DecimalFormat df = new DecimalFormat("#,###,##0.00");
            return df.format((Float.parseFloat(sub.toString()) / Float.parseFloat(total.toString())) * 100F);
        }

    }

    public String GenerateNimsSummary() {
   String generatedHTML="";
        if (targetCHO!=null) {
            if(targetCHO.startsWith("CHO"))
            {
               //RequestContext.getCurrentInstance().execute("alert('This onload script is added from backing bean.')");
            String choareid = targetCHO.substring(4, targetCHO.length());
            RequestContext.getCurrentInstance().execute("setTitle('NIMS Incidents Summary - CHO " + choareid+"')");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("WebApplication2PU");
            EntityManager em = emf.createEntityManager();
            List summaryCounts = em.createNativeQuery("Select (select count(*) from stfrecord) as totalNims,(select count(*) from stfrecord where stfrecord.choArea=?) as choNims,(select count(*) from stfrecord where stfrecord.choArea=? and agegroup=1) as agegroup85, (select count(*) from stfrecord where stfrecord.choArea=? and agegroup=2) as agegroup80,(select count(*) from stfrecord where stfrecord.choArea=? and agegroup=3) as agegroup75,(select count(*) from stfrecord where stfrecord.choArea=? and agegroup=4) as agegroup70,(select count(*) from stfrecord where stfrecord.choArea=? and agegroup=5) as agegroup65,(select count(*) from stfrecord where stfrecord.choArea=? and agegroup=6) as agegroup60,(select count(*) from stfrecord where stfrecord.choArea=? and agegroup>6) as agegrouplt60,(select count(*) from stfrecord where stfrecord.choArea=? and agegroup is NULL) as ageundefined,(select count(*) from stfrecord where stfrecord.choArea=? and gender=1) as totalMales,( select count(*) from stfrecord where stfrecord.choArea=? and gender=2) as totalFemales,(select count(*) from stfrecord where stfrecord.choArea=? and gender is NULL) as sexUndefined")
                    .setParameter(1, choareid)
                    .setParameter(2, choareid)
                    .setParameter(3, choareid)
                    .setParameter(4, choareid)
                    .setParameter(5, choareid)
                    .setParameter(6, choareid)
                    .setParameter(7, choareid)
                    .setParameter(8, choareid)
                    .setParameter(9, choareid)
                    .setParameter(10, choareid)
                    .setParameter(11, choareid)
                    .setParameter(12, choareid)
                    .getResultList();
                Object[] counts = (Object[]) summaryCounts.get(0);
                generatedHTML = "<table class='table table-striped table-bordered table-hover'>"
                        + "<thead>"
                        + "<tr>"
                        + "<th>Metric</th>"
                        + "<th>Count</th>"
                        + "<th>Percent %</th>"
                        + "</tr>"
                        + "</thead>"
                        + "<tbody>"
                        + "<tr>"
                        + "<td>No. of NIMS Incidents</td>"
                        + "<td>" + counts[1].toString() + "</td>"
                        + "<td>" + Percentage(counts[1], counts[0]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Age &gt; 85</td>"
                        + "<td>" + counts[2].toString() + "</td>"
                        + "<td>" + Percentage(counts[2], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Age (80 — 84)</td>"
                        + "<td>" + counts[3].toString() + "</td>"
                        + "<td>" + Percentage(counts[3], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Age (75 — 79)</td>"
                        + "<td>" + counts[4].toString() + "</td>"
                        + "<td>" + Percentage(counts[4], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Age (70 — 74)</td>"
                        + "<td>" + counts[5].toString() + "</td>"
                        + "<td>" + Percentage(counts[5], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Age (65 — 69)</td>"
                        + "<td>" + counts[6].toString() + "</td>"
                        + "<td>" + Percentage(counts[6], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Age (60 — 64)</td>"
                        + "<td>" + counts[7].toString() + "</td>"
                        + "<td>" + Percentage(counts[7], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Age &lt; 60</td>"
                        + "<td>" + counts[8].toString() + "</td>"
                        + "<td>" + Percentage(counts[8], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Age Undefined</td>"
                        + "<td>" + counts[9].toString() + "</td>"
                        + "<td>" + Percentage(counts[9], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Males</td>"
                        + "<td>" + counts[10].toString() + "</td>"
                        + "<td>" + Percentage(counts[10], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Females</td>"
                        + "<td>" + counts[11].toString() + "</td>"
                        + "<td>" + Percentage(counts[11], counts[1]) + "%</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td>Gender Undefined</td>"
                        + "<td>" + counts[12].toString() + "</td>"
                        + "<td>" + Percentage(counts[12], counts[1]) + "%</td>"
                        + "</tr>"
                        + "</tbody>"
                        + "</table>";

            }
            else
            RequestContext.getCurrentInstance().execute("hideNIMSPanel()");
        
        }
        

        return generatedHTML;

    }


}
