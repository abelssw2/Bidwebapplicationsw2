/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.Entity;

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
 * @author abel
 */
@Entity
@Table(name = "goods", catalog = "guessbid", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Goods.findAll", query = "SELECT g FROM Goods g"),
    @NamedQuery(name = "Goods.findById", query = "SELECT g FROM Goods g WHERE g.id = :id"),
    @NamedQuery(name = "Goods.findByGoodsName", query = "SELECT g FROM Goods g WHERE g.goodsName = :goodsName"),
    @NamedQuery(name = "Goods.findByGoodsType", query = "SELECT g FROM Goods g WHERE g.goodsType = :goodsType"),
    @NamedQuery(name = "Goods.findByDescribution", query = "SELECT g FROM Goods g WHERE g.describution = :describution"),
    @NamedQuery(name = "Goods.findByUnitPrice", query = "SELECT g FROM Goods g WHERE g.unitPrice = :unitPrice"),
    @NamedQuery(name = "Goods.findByQuantity", query = "SELECT g FROM Goods g WHERE g.quantity = :quantity")})
public class Goods implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 100)
    @Column(name = "goods_name", length = 100)
    private String goodsName;
    @Size(max = 100)
    @Column(name = "goods_type", length = 100)
    private String goodsType;
    @Size(max = 100)
    @Column(name = "describution", length = 100)
    private String describution;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unit_price", precision = 17, scale = 17)
    private Double unitPrice;
    @Column(name = "quantity", precision = 17, scale = 17)
    private Double quantity;
    @OneToMany(mappedBy = "goodsId")
    private Collection<Auction> auctionCollection;

    public Goods() {
    }

    public Goods(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getDescribution() {
        return describution;
    }

    public void setDescribution(String describution) {
        this.describution = describution;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    @XmlTransient
    public Collection<Auction> getAuctionCollection() {
        return auctionCollection;
    }

    public void setAuctionCollection(Collection<Auction> auctionCollection) {
        this.auctionCollection = auctionCollection;
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
        if (!(object instanceof Goods)) {
            return false;
        }
        Goods other = (Goods) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.guessbid.Entity.Goods[ id=" + id + " ]";
    }
    
}
