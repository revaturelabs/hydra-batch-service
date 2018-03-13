package com.revature.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BATCH_STATUS_LOOKUP")
public class BatchStatusLookup {

	@Id
	@Column(name = "BATCH_STATUS_ID")
	@SequenceGenerator(allocationSize = 1, name = "batch_status_seq", sequenceName = "batch_status_seq")
	@GeneratedValue(generator = "batch_status_seq", strategy = GenerationType.SEQUENCE)
	private int batchStatusID;
	
	@Column(name = "BS_NAME", unique=true, nullable=false)
	private String batchStatusName;

	public BatchStatusLookup(){
		super();
	}
	
	public BatchStatusLookup(int batchStatusID, String batchStatusName) {
		super();
		this.batchStatusID = batchStatusID;
		this.batchStatusName = batchStatusName;
	}
	
	public int getBatchStatusID() {
		return batchStatusID;
	}

	public void setBatchStatusID(int batchStatusID) {
		this.batchStatusID = batchStatusID;
	}

	public String getBatchStatusName() {
		return batchStatusName;
	}

	public void setBatchStatusName(String batchStatusName) {
		this.batchStatusName = batchStatusName;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + batchStatusID;
		result = prime * result + ((batchStatusName == null) ? 0 : batchStatusName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BatchStatusLookup other = (BatchStatusLookup) obj;
		if (batchStatusID != other.batchStatusID)
			return false;
		if (batchStatusName == null) {
			if (other.batchStatusName != null)
				return false;
		} else if (!batchStatusName.equals(other.batchStatusName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatchStatusLookup [batchStatusID=" + batchStatusID + ", batchStatusName=" + batchStatusName + "]";
	}	
	
}
