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
@Table(name = "carehome")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carehome.findAll", query = "SELECT c FROM Carehome c"),
    @NamedQuery(name = "Carehome.findById", query = "SELECT c FROM Carehome c WHERE c.id = :id"),
    @NamedQuery(name = "Carehome.findByName", query = "SELECT c FROM Carehome c WHERE c.name = :name"),
    @NamedQuery(name = "Carehome.findByLatitude", query = "SELECT c FROM Carehome c WHERE c.latitude = :latitude"),
    @NamedQuery(name = "Carehome.findByLongitude", query = "SELECT c FROM Carehome c WHERE c.longitude = :longitude")})
public class Carehome implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
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
    @JoinColumn(name = "LHO", referencedColumnName = "id")
    @ManyToOne
    private Localhealthoffice lho;
    @JoinColumn(name = "county", referencedColumnName = "id")
    @ManyToOne
    private County county;
    @JoinColumn(name = "choArea", referencedColumnName = "id")
    @ManyToOne
    private Communityhealthcareorganisation choArea;
    @JoinColumn(name = "PCN", referencedColumnName = "id")
    @ManyToOne
    private Primarycarenetwork pcn;

    public Carehome() {
    }

    public Carehome(Integer id) {
        this.id = id;
    }

    public Carehome(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public Primarycarenetwork getPcn() {
        return pcn;
    }

    public void setPcn(Primarycarenetwork pcn) {
        this.pcn = pcn;
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
        if (!(object instanceof Carehome)) {
            return false;
        }
        Carehome other = (Carehome) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Carehome[ id=" + id + " ]";
    }
    
}
