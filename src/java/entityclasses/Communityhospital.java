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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 13232238
 */
@Entity
@Table(name = "communityhospital")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Communityhospital.findAll", query = "SELECT c FROM Communityhospital c"),
    @NamedQuery(name = "Communityhospital.findById", query = "SELECT c FROM Communityhospital c WHERE c.id = :id"),
    @NamedQuery(name = "Communityhospital.findByName", query = "SELECT c FROM Communityhospital c WHERE c.name = :name"),
    @NamedQuery(name = "Communityhospital.findByAddress", query = "SELECT c FROM Communityhospital c WHERE c.address = :address"),
    @NamedQuery(name = "Communityhospital.findByLatitude", query = "SELECT c FROM Communityhospital c WHERE c.latitude = :latitude"),
    @NamedQuery(name = "Communityhospital.findByLongitude", query = "SELECT c FROM Communityhospital c WHERE c.longitude = :longitude")})
public class Communityhospital implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "address")
    private String address;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @JoinColumn(name = "lho", referencedColumnName = "id")
    @ManyToOne
    private Localhealthoffice lho;
    @JoinColumn(name = "county", referencedColumnName = "id")
    @ManyToOne
    private County county;
    @JoinColumn(name = "choArea", referencedColumnName = "id")
    @ManyToOne
    private Communityhealthcareorganisation choArea;

    public Communityhospital() {
    }

    public Communityhospital(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Localhealthoffice getLho() {
        return lho;
    }

    public void setLho(Localhealthoffice lho) {
        this.lho = lho;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public Communityhealthcareorganisation getChoArea() {
        return choArea;
    }

    public void setChoArea(Communityhealthcareorganisation choArea) {
        this.choArea = choArea;
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
        if (!(object instanceof Communityhospital)) {
            return false;
        }
        Communityhospital other = (Communityhospital) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Communityhospital[ id=" + id + " ]";
    }
    
}
