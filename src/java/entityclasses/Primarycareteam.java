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
@Table(name = "primarycareteam")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Primarycareteam.findAll", query = "SELECT p FROM Primarycareteam p"),
    @NamedQuery(name = "Primarycareteam.findById", query = "SELECT p FROM Primarycareteam p WHERE p.id = :id"),
    @NamedQuery(name = "Primarycareteam.findByName", query = "SELECT p FROM Primarycareteam p WHERE p.name = :name"),
    @NamedQuery(name = "Primarycareteam.findByPCTLogName", query = "SELECT p FROM Primarycareteam p WHERE p.pCTLogName = :pCTLogName"),
    @NamedQuery(name = "Primarycareteam.findByPopulation", query = "SELECT p FROM Primarycareteam p WHERE p.population = :population")})
public class Primarycareteam implements Serializable {
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
    @Column(name = "PCTLogName")
    private String pCTLogName;
    @Column(name = "population")
    private Integer population;
    @JoinColumn(name = "choareaid", referencedColumnName = "id")
    @ManyToOne
    private Communityhealthcareorganisation choareaid;
    @JoinColumn(name = "region", referencedColumnName = "id")
    @ManyToOne
    private Mapregion region;
    @JoinColumn(name = "PCN", referencedColumnName = "id")
    @ManyToOne
    private Primarycarenetwork pcn;

    public Primarycareteam() {
    }

    public Primarycareteam(Integer id) {
        this.id = id;
    }

    public Primarycareteam(Integer id, String name) {
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

    public String getPCTLogName() {
        return pCTLogName;
    }

    public void setPCTLogName(String pCTLogName) {
        this.pCTLogName = pCTLogName;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Communityhealthcareorganisation getChoareaid() {
        return choareaid;
    }

    public void setChoareaid(Communityhealthcareorganisation choareaid) {
        this.choareaid = choareaid;
    }

    public Mapregion getRegion() {
        return region;
    }

    public void setRegion(Mapregion region) {
        this.region = region;
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
        if (!(object instanceof Primarycareteam)) {
            return false;
        }
        Primarycareteam other = (Primarycareteam) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityclasses.Primarycareteam[ id=" + id + " ]";
    }
    
}
