/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.Entity;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abel
 */
@Entity
@Table(name = "winer", catalog = "guessbid", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Winer.findAll", query = "SELECT w FROM Winer w"),
    @NamedQuery(name = "Winer.findById", query = "SELECT w FROM Winer w WHERE w.id = :id"),
    @NamedQuery(name = "Winer.findByWinPrice", query = "SELECT w FROM Winer w WHERE w.winPrice = :winPrice")})
public class Winer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "win_price", precision = 17, scale = 17)
    private Double winPrice;
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    @ManyToOne
    private Auction auctionId;
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    @ManyToOne
    private Buyer buyerId;

    public Winer() {
    }

    public Winer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getWinPrice() {
        return winPrice;
    }

    public void setWinPrice(Double winPrice) {
        this.winPrice = winPrice;
    }

    public Auction getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Auction auctionId) {
        this.auctionId = auctionId;
    }

    public Buyer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Buyer buyerId) {
        this.buyerId = buyerId;
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
        if (!(object instanceof Winer)) {
            return false;
        }
        Winer other = (Winer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.guessbid.Entity.Winer[ id=" + id + " ]";
    }
    
}
