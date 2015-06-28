/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.Entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author abel
 */
@Entity
@Table(name = "auction", catalog = "guessbid", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Auction.findAll", query = "SELECT a FROM Auction a"),
    @NamedQuery(name = "Auction.findById", query = "SELECT a FROM Auction a WHERE a.id = :id"),
    @NamedQuery(name = "Auction.findByAuctionName", query = "SELECT a FROM Auction a WHERE a.auctionName = :auctionName"),
    @NamedQuery(name = "Auction.findByInitialPrice", query = "SELECT a FROM Auction a WHERE a.initialPrice = :initialPrice"),
    @NamedQuery(name = "Auction.findByStartDate", query = "SELECT a FROM Auction a WHERE a.startDate = :startDate"),
    @NamedQuery(name = "Auction.findByEndDate", query = "SELECT a FROM Auction a WHERE a.endDate = :endDate"),
    @NamedQuery(name = "Auction.findByStatus", query = "SELECT a FROM Auction a WHERE a.status = :status")})
public class Auction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Size(max = 100)
    @Column(name = "auction_name", length = 100)
    private String auctionName;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "initial_price", precision = 17, scale = 17)
    private Double initialPrice;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "status")
    private Boolean status;
    @OneToMany(mappedBy = "auctionId")
    private Collection<Winer> winerCollection;
    @OneToMany(mappedBy = "auctionId")
    private Collection<Payment> paymentCollection;
    @OneToMany(mappedBy = "auctionId")
    private Collection<Bid> bidCollection;
    @JoinColumn(name = "goods_id", referencedColumnName = "id")
    @ManyToOne
    private Goods goodsId;
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @ManyToOne
    private Seller sellerId;

    public Auction() {
    }

    public Auction(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    @XmlTransient
    public Collection<Winer> getWinerCollection() {
        return winerCollection;
    }

    public void setWinerCollection(Collection<Winer> winerCollection) {
        this.winerCollection = winerCollection;
    }

    @XmlTransient
    public Collection<Payment> getPaymentCollection() {
        return paymentCollection;
    }

    public void setPaymentCollection(Collection<Payment> paymentCollection) {
        this.paymentCollection = paymentCollection;
    }

    @XmlTransient
    public Collection<Bid> getBidCollection() {
        return bidCollection;
    }

    public void setBidCollection(Collection<Bid> bidCollection) {
        this.bidCollection = bidCollection;
    }

    public Goods getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Goods goodsId) {
        this.goodsId = goodsId;
    }

    public Seller getSellerId() {
        return sellerId;
    }

    public void setSellerId(Seller sellerId) {
        this.sellerId = sellerId;
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
        if (!(object instanceof Auction)) {
            return false;
        }
        Auction other = (Auction) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.polimi.guessbid.Entity.Auction[ id=" + id + " ]";
    }
    
}
