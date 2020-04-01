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
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "reject")
@NamedQuery(name = "Reject.findAll",query = "SELECT r FROM Reject r")
public class Reject implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "reason")
    private String reason;

    @Column(name = "code_bill")
    private String code;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "address")
    private String address;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_date")
    private Date createdDate;
    

    @Column(name = "status", columnDefinition = "TINYINT")
    private int status;

    public Reject() {
    }

    public Reject(String name, String reason, String code, String email, String mobile, String address, Date createdDate) {
        this.name = name;
        this.reason = reason;
        this.code = code;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.createdDate = createdDate;
    }
    
    public Reject(Long id, String name, String reason, String code, String email, String mobile, String address,
			Date createdDate, int status) {
		super();
		this.id = id;
		this.name = name;
		this.reason = reason;
		this.code = code;
		this.email = email;
		this.mobile = mobile;
		this.address = address;
		this.createdDate = createdDate;
		this.status = status;
	}

	public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    

    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@PrePersist
    public void pre() throws ParseException{
        if(this.createdDate == null){
            this.createdDate = new Date();
        }
    }
}
