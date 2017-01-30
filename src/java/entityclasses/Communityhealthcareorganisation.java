/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityclasses;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 13232238
 */
@Entity
@Table(name = "communityhealthcareorganisation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Communityhealthcareorganisation.findAll", query = "SELECT c FROM Communityhealthcareorganisation c"),
    @NamedQuery(name = "Communityhealthcareorganisation.findById", query = "SELECT c FROM Communityhealthcareorganisation c WHERE c.id = :id"),
    @NamedQuery(name = "Communityhealthcareorganisation.findByName", query = "SELECT c FROM Communityhealthcareorganisation c WHERE c.name = :name"),
    @NamedQuery(name = "Communityhealthcareorganisation.findByPopulation", query = "SELECT c FROM Communityhealthcareorganisation c WHERE c.population = :population")})
public class Communityhealthcareorganisation implements Serializable {
    @OneToMany(mappedBy = "choareaid")
    private Collection<Primarycareteam> primarycareteamCollection;
    @OneToMany(mappedBy = "choArea")
    private Collection<Carehome> carehomeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cho")
    private Collection<Primarycarenetwork> primarycarenetworkCollection;
    @OneToMany(mappedBy = "choarea")
    private Collection<Localhealthoffice> localhealthofficeCollection;
    @OneToMany(mappedBy = "choArea")
    private Collection<Communityhospital> communityhospitalCollection;
    @JoinTable(name = "chohospitalgroups", joinColumns = {
        @JoinColumn(name = "areaid", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "hospitalgroupid", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Hospitalgroup> hospitalgroupCollection;
    @OneToMany(mappedBy = "areaid")
    private Collection<Hospital> hospitalCollection;
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
    @Column(name = "Population")
    private Integer population;
    @JoinColumn(name = "region", referencedColumnName = "id")
    @ManyToOne
    private Mapregion region;

    public Communityhealthcareorganisation() {
    }

    public Communityhealthcareorganisation(Integer id) {
        this.id = id;
    }

    public Communityhealthcareorganisation(Integer id, String name) {
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

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Mapregion getRegion() {
        return region;
    }

    public void setRegion(Mapregion region) {
        this.region = region;
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
        if (!(object instanceof Communityhealthcareorganisation)) {
            return false;
        }
        Communityhealthcareorganisation other = (Communityhealthcareorganisation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Communityhealthcareorganisation[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Hospitalgroup> getHospitalgroupCollection() {
        return hospitalgroupCollection;
    }

    public void setHospitalgroupCollection(Collection<Hospitalgroup> hospitalgroupCollection) {
        this.hospitalgroupCollection = hospitalgroupCollection;
    }

    @XmlTransient
    public Collection<Hospital> getHospitalCollection() {
        return hospitalCollection;
    }

    public void setHospitalCollection(Collection<Hospital> hospitalCollection) {
        this.hospitalCollection = hospitalCollection;
    }

    @XmlTransient
    public Collection<Localhealthoffice> getLocalhealthofficeCollection() {
        return localhealthofficeCollection;
    }

    public void setLocalhealthofficeCollection(Collection<Localhealthoffice> localhealthofficeCollection) {
        this.localhealthofficeCollection = localhealthofficeCollection;
    }

    @XmlTransient
    public Collection<Communityhospital> getCommunityhospitalCollection() {
        return communityhospitalCollection;
    }

    public void setCommunityhospitalCollection(Collection<Communityhospital> communityhospitalCollection) {
        this.communityhospitalCollection = communityhospitalCollection;
    }

    @XmlTransient
    public Collection<Carehome> getCarehomeCollection() {
        return carehomeCollection;
    }

    public void setCarehomeCollection(Collection<Carehome> carehomeCollection) {
        this.carehomeCollection = carehomeCollection;
    }

    @XmlTransient
    public Collection<Primarycarenetwork> getPrimarycarenetworkCollection() {
        return primarycarenetworkCollection;
    }

    public void setPrimarycarenetworkCollection(Collection<Primarycarenetwork> primarycarenetworkCollection) {
        this.primarycarenetworkCollection = primarycarenetworkCollection;
    }

    @XmlTransient
    public Collection<Primarycareteam> getPrimarycareteamCollection() {
        return primarycareteamCollection;
    }

    public void setPrimarycareteamCollection(Collection<Primarycareteam> primarycareteamCollection) {
        this.primarycareteamCollection = primarycareteamCollection;
    }
    
}
