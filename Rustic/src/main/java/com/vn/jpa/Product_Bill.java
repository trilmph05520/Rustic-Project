package com.vn.jpa;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "product_bill")
@NamedQuery(name = "Product_Bill.findAll", query = "SELECT pb FROM Product_Bill pb")
public class Product_Bill implements Serializable {

    public static final Long serializable = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="id_product")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "id_bill")
    private Bill bill;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "isdelete", columnDefinition = "CHAR")
    private String isdelete;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;

    public Product_Bill() {
    }

    public Product_Bill(Product product, Bill bill, int quantity, String isdelete) {
        this.product = product;
        this.bill = bill;
        this.quantity = quantity;
        this.isdelete = isdelete;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(String isdelete) {
        this.isdelete = isdelete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @PrePersist
    public void pre() throws ParseException{
        if(this.createdDate == null){
            this.createdDate = new Date();
        }
    }
}
