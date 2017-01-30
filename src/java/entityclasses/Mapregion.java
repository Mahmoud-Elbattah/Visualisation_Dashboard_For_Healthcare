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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 13232238
 */
@Entity
@Table(name = "mapregion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mapregion.findAll", query = "SELECT m FROM Mapregion m"),
    @NamedQuery(name = "Mapregion.findById", query = "SELECT m FROM Mapregion m WHERE m.id = :id"),
    @NamedQuery(name = "Mapregion.findByName", query = "SELECT m FROM Mapregion m WHERE m.name = :name")})
public class Mapregion implements Serializable {
    @OneToMany(mappedBy = "region")
    private Collection<Primarycareteam> primarycareteamCollection;
    @OneToMany(mappedBy = "region")
    private Collection<Primarycarenetwork> primarycarenetworkCollection;
    @OneToMany(mappedBy = "region")
    private Collection<Hospitalgroup> hospitalgroupCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "region")
    private Collection<Communityhealthcareorganisation> communityhealthcareorganisationCollection;

    public Mapregion() {
    }

    public Mapregion(Integer id) {
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

    @XmlTransient
    public Collection<Communityhealthcareorganisation> getCommunityhealthcareorganisationCollection() {
        return communityhealthcareorganisationCollection;
    }

    public void setCommunityhealthcareorganisationCollection(Collection<Communityhealthcareorganisation> communityhealthcareorganisationCollection) {
        this.communityhealthcareorganisationCollection = communityhealthcareorganisationCollection;
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
        if (!(object instanceof Mapregion)) {
            return false;
        }
        Mapregion other = (Mapregion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Mapregion[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Hospitalgroup> getHospitalgroupCollection() {
        return hospitalgroupCollection;
    }

    public void setHospitalgroupCollection(Collection<Hospitalgroup> hospitalgroupCollection) {
        this.hospitalgroupCollection = hospitalgroupCollection;
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
