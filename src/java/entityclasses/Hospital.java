/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityclasses;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 13232238
 */
@Entity
@Table(name = "hospital")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hospital.findAll", query = "SELECT h FROM Hospital h"),
    @NamedQuery(name = "Hospital.findById", query = "SELECT h FROM Hospital h WHERE h.id = :id"),
    @NamedQuery(name = "Hospital.findByHIPECode", query = "SELECT h FROM Hospital h WHERE h.hIPECode = :hIPECode"),
    @NamedQuery(name = "Hospital.findByDoHCode", query = "SELECT h FROM Hospital h WHERE h.doHCode = :doHCode"),
    @NamedQuery(name = "Hospital.findByName", query = "SELECT h FROM Hospital h WHERE h.name = :name"),
    @NamedQuery(name = "Hospital.findByLatitude", query = "SELECT h FROM Hospital h WHERE h.latitude = :latitude"),
    @NamedQuery(name = "Hospital.findByLongitude", query = "SELECT h FROM Hospital h WHERE h.longitude = :longitude"),
    @NamedQuery(name = "Hospital.findByNho", query = "SELECT h FROM Hospital h WHERE h.nho = :nho")})
public class Hospital implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "HIPECode")
    private Integer hIPECode;
    @Column(name = "DoHCode")
    private Integer doHCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "NHO")
    private Boolean nho;
    @JoinColumn(name = "countyid", referencedColumnName = "id")
    @ManyToOne
    private County countyid;
    @JoinColumn(name = "areaid", referencedColumnName = "id")
    @ManyToOne
    private Communityhealthcareorganisation areaid;
    @JoinColumn(name = "hospitalGroup", referencedColumnName = "id")
    @ManyToOne
    private Hospitalgroup hospitalGroup;

    public Hospital() {
    }

    public Hospital(Integer id) {
        this.id = id;
    }

    public Hospital(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHIPECode() {
        return hIPECode;
    }

    public void setHIPECode(Integer hIPECode) {
        this.hIPECode = hIPECode;
    }

    public Integer getDoHCode() {
        return doHCode;
    }

    public void setDoHCode(Integer doHCode) {
        this.doHCode = doHCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getNho() {
        return nho;
    }

    public void setNho(Boolean nho) {
        this.nho = nho;
    }

    public County getCountyid() {
        return countyid;
    }

    public void setCountyid(County countyid) {
        this.countyid = countyid;
    }

    public Communityhealthcareorganisation getAreaid() {
        return areaid;
    }

    public void setAreaid(Communityhealthcareorganisation areaid) {
        this.areaid = areaid;
    }

    public Hospitalgroup getHospitalGroup() {
        return hospitalGroup;
    }

    public void setHospitalGroup(Hospitalgroup hospitalGroup) {
        this.hospitalGroup = hospitalGroup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hospital)) {
            return false;
        }
        Hospital other = (Hospital) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Hospital[ id=" + id + " ]";
    }
    
}
