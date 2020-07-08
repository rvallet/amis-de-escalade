package com.ocesclade.amisdeescalade.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ocesclade.amisdeescalade.enumerated.TopoLoanStatusEnum;

@Entity
@Table(name="topo_loan")
public class TopoLoan implements Serializable {

	private static final long serialVersionUID = -5440269504807490412L;
	
	@Id
	@Column(name="id_topo_loan")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String lender;
	private String borrower;
	private Date creationDate;
	private String status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_topo")
	private Topo topo;

	public TopoLoan() {
		super();
		this.creationDate= Calendar.getInstance().getTime();
		this.status = TopoLoanStatusEnum.IN_PROGRESS.toString();
	}

	public TopoLoan(String lender, String borrower, Topo topo) {
		super();
		this.lender = lender;
		this.borrower = borrower;
		this.topo = topo;
		this.status = TopoLoanStatusEnum.IN_PROGRESS.toString();
		this.creationDate= Calendar.getInstance().getTime();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLender() {
		return lender;
	}

	public void setLender(String lender) {
		this.lender = lender;
	}

	public String getBorrower() {
		return borrower;
	}

	public void setBorrower(String borrower) {
		this.borrower = borrower;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Topo getTopo() {
		return topo;
	}

	public void setTopo(Topo topo) {
		this.topo = topo;
	}
	
	public String getStatus() {
		return status;
	}

	public void setTopoLoanStatus(TopoLoanStatusEnum statusEnum) {
		this.status = statusEnum.toString();
	}

	@Override
	public String toString() {
		return "TopoLoan [id=" + id + ", lender=" + lender + ", borrower=" + borrower + ", creationDate=" + creationDate
				+ ", status=" + status + ", topo=" + topo + "]";
	}
	
	
}
