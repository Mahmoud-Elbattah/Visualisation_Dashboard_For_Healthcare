/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityclasses;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 13232238
 */
@Entity
@Table(name = "summarycounts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Summarycounts.findAll", query = "SELECT s FROM Summarycounts s"),
    @NamedQuery(name = "Summarycounts.findByChoCount", query = "SELECT s FROM Summarycounts s WHERE s.choCount = :choCount"),
    @NamedQuery(name = "Summarycounts.findByHospitalCount", query = "SELECT s FROM Summarycounts s WHERE s.hospitalCount = :hospitalCount"),
    @NamedQuery(name = "Summarycounts.findByCommHospitalCount", query = "SELECT s FROM Summarycounts s WHERE s.commHospitalCount = :commHospitalCount"),
    @NamedQuery(name = "Summarycounts.findByCarehomeCount", query = "SELECT s FROM Summarycounts s WHERE s.carehomeCount = :carehomeCount"),
    @NamedQuery(name = "Summarycounts.findByPctCount", query = "SELECT s FROM Summarycounts s WHERE s.pctCount = :pctCount"),
    @NamedQuery(name = "Summarycounts.findByTotalPopulation", query = "SELECT s FROM Summarycounts s WHERE s.totalPopulation = :totalPopulation")})
public class Summarycounts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "choCount")
    private BigInteger choCount;
    @Column(name = "hospitalCount")
    private BigInteger hospitalCount;
    @Column(name = "commHospitalCount")
    private BigInteger commHospitalCount;
    @Column(name = "carehomeCount")
    private BigInteger carehomeCount;
    @Column(name = "pctCount")
    private BigInteger pctCount;
    @Column(name = "totalPopulation")
    private BigInteger totalPopulation;
    @Id
    private Long id;

    public Summarycounts() {
    }

    public BigInteger getChoCount() {
        return choCount;
    }

    public void setChoCount(BigInteger choCount) {
        this.choCount = choCount;
    }

    public BigInteger getHospitalCount() {
        return hospitalCount;
    }

    public void setHospitalCount(BigInteger hospitalCount) {
        this.hospitalCount = hospitalCount;
    }

    public BigInteger getCommHospitalCount() {
        return commHospitalCount;
    }

    public void setCommHospitalCount(BigInteger commHospitalCount) {
        this.commHospitalCount = commHospitalCount;
    }

    public BigInteger getCarehomeCount() {
        return carehomeCount;
    }

    public void setCarehomeCount(BigInteger carehomeCount) {
        this.carehomeCount = carehomeCount;
    }

    public BigInteger getPctCount() {
        return pctCount;
    }

    public void setPctCount(BigInteger pctCount) {
        this.pctCount = pctCount;
    }

    public BigInteger getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(BigInteger totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
