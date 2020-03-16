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
@Table(name = "report")
@NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
public class Report implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final Long serializable = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "problem")
    private String problem;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "opinion")
    private String opinion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "reply")
    private String repply;

    public Report() {
    }

    public Report(String name, String email, String problem, String mobile, String opinion, Date createdDate, String repply) {
        this.name = name;
        this.email = email;
        this.problem = problem;
        this.mobile = mobile;
        this.opinion = opinion;
        this.createdDate = createdDate;
        this.repply = repply;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getRepply() {
        return repply;
    }

    public void setRepply(String repply) {
        this.repply = repply;
    }

    @PrePersist
    public void pre() throws ParseException{
        if(this.createdDate == null){
        	Date create = new Date();
        	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.createdDate = sdf.parse(sdf.format(create));
        }
    }
}
