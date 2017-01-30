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
@Table(name = "primarycarenetwork")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Primarycarenetwork.findAll", query = "SELECT p FROM Primarycarenetwork p"),
    @NamedQuery(name = "Primarycarenetwork.findById", query = "SELECT p FROM Primarycarenetwork p WHERE p.id = :id"),
    @NamedQuery(name = "Primarycarenetwork.findByName", query = "SELECT p FROM Primarycarenetwork p WHERE p.name = :name")})
public class Primarycarenetwork implements Serializable {
    @OneToMany(mappedBy = "pcn")
    private Collection<Primarycareteam> primarycareteamCollection;
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
    @OneToMany(mappedBy = "pcn")
    private Collection<Carehome> carehomeCollection;
    @JoinColumn(name = "region", referencedColumnName = "id")
    @ManyToOne
    private Mapregion region;
    @JoinColumn(name = "CHO", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Communityhealthcareorganisation cho;

    public Primarycarenetwork() {
    }

    public Primarycarenetwork(Integer id) {
        this.id = id;
    }

    public Primarycarenetwork(Integer id, String name) {
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

    @XmlTransient
    public Collection<Carehome> getCarehomeCollection() {
        return carehomeCollection;
    }

    public void setCarehomeCollection(Collection<Carehome> carehomeCollection) {
        this.carehomeCollection = carehomeCollection;
    }

    public Mapregion getRegion() {
        return region;
    }

    public void setRegion(Mapregion region) {
        this.region = region;
    }

    public Communityhealthcareorganisation getCho() {
        return cho;
    }

    public void setCho(Communityhealthcareorganisation cho) {
        this.cho = cho;
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
        if (!(object instanceof Primarycarenetwork)) {
            return false;
        }
        Primarycarenetwork other = (Primarycarenetwork) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Primarycarenetwork[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Primarycareteam> getPrimarycareteamCollection() {
        return primarycareteamCollection;
    }

    public void setPrimarycareteamCollection(Collection<Primarycareteam> primarycareteamCollection) {
        this.primarycareteamCollection = primarycareteamCollection;
    }
    
}
