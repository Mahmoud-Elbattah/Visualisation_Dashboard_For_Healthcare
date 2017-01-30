/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityclasses;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "hospitalgroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hospitalgroup.findAll", query = "SELECT h FROM Hospitalgroup h"),
    @NamedQuery(name = "Hospitalgroup.findById", query = "SELECT h FROM Hospitalgroup h WHERE h.id = :id"),
    @NamedQuery(name = "Hospitalgroup.findByName", query = "SELECT h FROM Hospitalgroup h WHERE h.name = :name"),
    @NamedQuery(name = "Hospitalgroup.findByDescription", query = "SELECT h FROM Hospitalgroup h WHERE h.description = :description")})
public class Hospitalgroup implements Serializable {
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
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "hospitalgroupCollection")
    private Collection<Communityhealthcareorganisation> communityhealthcareorganisationCollection;
    @JoinColumn(name = "region", referencedColumnName = "id")
    @ManyToOne
    private Mapregion region;
    @OneToMany(mappedBy = "hospitalGroup")
    private Collection<Hospital> hospitalCollection;

    public Hospitalgroup() {
    }

    public Hospitalgroup(Integer id) {
        this.id = id;
    }

    public Hospitalgroup(Integer id, String name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Communityhealthcareorganisation> getCommunityhealthcareorganisationCollection() {
        return communityhealthcareorganisationCollection;
    }

    public void setCommunityhealthcareorganisationCollection(Collection<Communityhealthcareorganisation> communityhealthcareorganisationCollection) {
        this.communityhealthcareorganisationCollection = communityhealthcareorganisationCollection;
    }

    public Mapregion getRegion() {
        return region;
    }

    public void setRegion(Mapregion region) {
        this.region = region;
    }

    @XmlTransient
    public Collection<Hospital> getHospitalCollection() {
        return hospitalCollection;
    }

    public void setHospitalCollection(Collection<Hospital> hospitalCollection) {
        this.hospitalCollection = hospitalCollection;
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
        if (!(object instanceof Hospitalgroup)) {
            return false;
        }
        Hospitalgroup other = (Hospitalgroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Hospitalgroup[ id=" + id + " ]";
    }
    
}
