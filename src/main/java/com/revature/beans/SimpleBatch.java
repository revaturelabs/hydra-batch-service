package com.revature.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "BATCH")
@Cacheable
public class SimpleBatch implements Serializable {
	private static final long serialVersionUID = -7000300062384597615L;

	@Id
	@Column(name = "BATCH_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATCH_ID_SEQUENCE")
	@SequenceGenerator(name = "BATCH_ID_SEQUENCE", sequenceName = "BATCH_ID_SEQUENCE")
	private Integer batchId;

	@Column(name = "RESOURCE_ID")
	private String resourceId;

	@NotNull
	@Column(name = "TRAINING_NAME")
	private String trainingName;
	
	@NotNull
	@Column(name = "TRAINER_ID", nullable = false)
	private Integer trainerId;

	@Column(name = "CO_TRAINER_ID")
	private Integer coTrainerId;

	//TODO
	//Consolidate skillType and skills
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "SKILL_TYPE")
	private SkillType skillType;
	
	@ElementCollection(fetch=FetchType.EAGER)                                                         
	@CollectionTable(name = "BATCH_SKILL_JT", joinColumns = @JoinColumn(name = "BATCH_ID"))
	@Column(name = "SKILL_ID")                                                    
	private List<Integer> skills;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TRAINING_TYPE")
	private TrainingType trainingType;

	//TODO
	//Consolidate Location and BatchLocation
	@NotEmpty
	@Column(name = "LOCATION", nullable = false)
	private String location;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "BATCH_LOCATION")
	BatchLocation batchLocation;

	@Column(name = "ADDRESS_ID")
	private Integer addressId;

	/**
	 * Anything above this grade is GREEN
	 */
	@Min(value = 1)
	@Column(name = "GOOD_GRADE_THRESHOLD")
	private Short goodGradeThreshold;

	/**
	 * Anything above this grade but below goodGradeThreshold is YELLOW Anything
	 * below this grade is RED
	 */
	@Min(value = 1)
	@Column(name = "BORDERLINE_GRADE_THRESHOLD")
	private Short borderlineGradeThreshold;

	@Column(name = "NUMBER_OF_WEEKS", nullable = false)
	private Integer weeks;

	@Column(name = "GRADED_WEEKS")
	private Integer gradedWeeks;
	
	@Column(name = "START_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "CURRICULUM")
	private Integer curriculum;
	
	@Column(name = "FOCUS")
	private Integer focus;

	@ManyToOne
	@JoinColumn(name = "STATUS")
	@Fetch(FetchMode.JOIN)
	private BatchStatusLookup batchStatus;
	
	
	public SimpleBatch() {
		super();
		this.weeks = 1;
		this.gradedWeeks = 7;
		this.goodGradeThreshold = 80;
		this.borderlineGradeThreshold = 70;
		this.trainingType = TrainingType.Revature;
	}

	/**
	 * Constructor mostly used for testing. Defaults TrainingType - Revature,
	 * SkillType - J2EE, Good grade - 80, and Borderline grade - 70
	 *
	 * @param trainingName
	 * @param trainer
	 * @param startDate
	 * @param endDate
	 * @param location
	 */
	public SimpleBatch(String trainingName, Integer trainerId, Date startDate, Date endDate, String location) {
		super();
		this.trainingName = trainingName;
		this.trainerId = trainerId;
		this.skillType = SkillType.J2EE;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
	}

	public SimpleBatch(Batch batch) {
		super();
		this.batchId = batch.getBatchId();
		this.resourceId = batch.getResourceId();
		this.trainingName = batch.getTrainingName();
		this.trainerId = batch.getTrainer() != null ? batch.getTrainer().getTrainerId() : null;
		this.coTrainerId = batch.getCoTrainer() != null ? batch.getCoTrainer().getTrainerId() : null;
		this.skillType = batch.getSkillType();
		this.skills = batch.getSkills();
		this.trainingType = batch.getTrainingType();
		this.startDate = batch.getStartDate();
		this.endDate = batch.getEndDate();
		this.location = batch.getLocation();
		this.addressId = batch.getAddress() != null ? batch.getAddress().getAddressId() : null;
		this.goodGradeThreshold = batch.getGoodGradeThreshold();
		this.borderlineGradeThreshold = batch.getBorderlineGradeThreshold();
		this.trainingType = batch.getTrainingType();
		this.weeks = batch.getWeeks();
		this.gradedWeeks = batch.getGradedWeeks();
		this.curriculum = batch.getCurriculum();
		this.focus = batch.getFocus();
		this.batchStatus = batch.getBatchStatus();
		this.batchLocation = batch.getBatchLocation();
	}
	

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public Integer getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Integer trainerId) {
		this.trainerId = trainerId;
	}

	public Integer getCoTrainerId() {
		return coTrainerId;
	}

	public void setCoTrainerId(Integer coTrainerId) {
		this.coTrainerId = coTrainerId;
	}

	public SkillType getSkillType() {
		return skillType;
	}

	public void setSkillType(SkillType skillType) {
		this.skillType = skillType;
	}

	public List<Integer> getSkills() {
		return skills;
	}

	public void setSkills(List<Integer> skills) {
		this.skills = skills;
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public void setTrainingType(TrainingType trainingType) {
		this.trainingType = trainingType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Short getGoodGradeThreshold() {
		return goodGradeThreshold;
	}

	public void setGoodGradeThreshold(Short goodGradeThreshold) {
		this.goodGradeThreshold = goodGradeThreshold;
	}

	public Short getBorderlineGradeThreshold() {
		return borderlineGradeThreshold;
	}

	public void setBorderlineGradeThreshold(Short borderlineGradeThreshold) {
		this.borderlineGradeThreshold = borderlineGradeThreshold;
	}

	public Integer getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer weeks) {
		this.weeks = weeks;
	}

	public Integer getGradedWeeks() {
		return gradedWeeks;
	}

	public void setGradedWeeks(Integer gradedWeeks) {
		this.gradedWeeks = gradedWeeks;
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

	public Integer getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Integer curriculum) {
		this.curriculum = curriculum;
	}

	public Integer getFocus() {
		return focus;
	}

	public void setFocus(Integer focus) {
		this.focus = focus;
	}

	public BatchStatusLookup getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(BatchStatusLookup batchStatus) {
		this.batchStatus = batchStatus;
	}

	public BatchLocation getBatchLocation() {
		return batchLocation;
	}

	public void setBatchLocation(BatchLocation batchLocation) {
		this.batchLocation = batchLocation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addressId == null) ? 0 : addressId.hashCode());
		result = prime * result + ((batchId == null) ? 0 : batchId.hashCode());
		result = prime * result + ((batchLocation == null) ? 0 : batchLocation.hashCode());
		result = prime * result + ((batchStatus == null) ? 0 : batchStatus.hashCode());
		result = prime * result + ((borderlineGradeThreshold == null) ? 0 : borderlineGradeThreshold.hashCode());
		result = prime * result + ((coTrainerId == null) ? 0 : coTrainerId.hashCode());
		result = prime * result + ((curriculum == null) ? 0 : curriculum.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((focus == null) ? 0 : focus.hashCode());
		result = prime * result + ((goodGradeThreshold == null) ? 0 : goodGradeThreshold.hashCode());
		result = prime * result + ((gradedWeeks == null) ? 0 : gradedWeeks.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
		result = prime * result + ((skillType == null) ? 0 : skillType.hashCode());
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((trainerId == null) ? 0 : trainerId.hashCode());
		result = prime * result + ((trainingName == null) ? 0 : trainingName.hashCode());
		result = prime * result + ((trainingType == null) ? 0 : trainingType.hashCode());
		result = prime * result + ((weeks == null) ? 0 : weeks.hashCode());
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
		SimpleBatch other = (SimpleBatch) obj;
		if (addressId == null) {
			if (other.addressId != null)
				return false;
		} else if (!addressId.equals(other.addressId))
			return false;
		if (batchId == null) {
			if (other.batchId != null)
				return false;
		} else if (!batchId.equals(other.batchId))
			return false;
		if (batchLocation == null) {
			if (other.batchLocation != null)
				return false;
		} else if (!batchLocation.equals(other.batchLocation))
			return false;
		if (batchStatus == null) {
			if (other.batchStatus != null)
				return false;
		} else if (!batchStatus.equals(other.batchStatus))
			return false;
		if (borderlineGradeThreshold == null) {
			if (other.borderlineGradeThreshold != null)
				return false;
		} else if (!borderlineGradeThreshold.equals(other.borderlineGradeThreshold))
			return false;
		if (coTrainerId == null) {
			if (other.coTrainerId != null)
				return false;
		} else if (!coTrainerId.equals(other.coTrainerId))
			return false;
		if (curriculum == null) {
			if (other.curriculum != null)
				return false;
		} else if (!curriculum.equals(other.curriculum))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (focus == null) {
			if (other.focus != null)
				return false;
		} else if (!focus.equals(other.focus))
			return false;
		if (goodGradeThreshold == null) {
			if (other.goodGradeThreshold != null)
				return false;
		} else if (!goodGradeThreshold.equals(other.goodGradeThreshold))
			return false;
		if (gradedWeeks == null) {
			if (other.gradedWeeks != null)
				return false;
		} else if (!gradedWeeks.equals(other.gradedWeeks))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (resourceId == null) {
			if (other.resourceId != null)
				return false;
		} else if (!resourceId.equals(other.resourceId))
			return false;
		if (skillType != other.skillType)
			return false;
		if (skills == null) {
			if (other.skills != null)
				return false;
		} else if (!skills.equals(other.skills))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (trainerId == null) {
			if (other.trainerId != null)
				return false;
		} else if (!trainerId.equals(other.trainerId))
			return false;
		if (trainingName == null) {
			if (other.trainingName != null)
				return false;
		} else if (!trainingName.equals(other.trainingName))
			return false;
		if (trainingType != other.trainingType)
			return false;
		if (weeks == null) {
			if (other.weeks != null)
				return false;
		} else if (!weeks.equals(other.weeks))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SimpleBatch [batchId=" + batchId + ", resourceId=" + resourceId + ", trainingName=" + trainingName
				+ ", trainerId=" + trainerId + ", coTrainerId=" + coTrainerId + ", skillType=" 
				+ skillType +", skills=" + skills
				+ ", trainingType=" + trainingType + ", location=" + location + ", addressId=" + addressId
				+ ", goodGradeThreshold=" + goodGradeThreshold + ", borderlineGradeThreshold="
				+ borderlineGradeThreshold + ", weeks=" + weeks + ", gradedWeeks=" + gradedWeeks + ", startDate="
				+ startDate + ", endDate=" + endDate + ", curriculum=" + curriculum + ", focus=" + focus
				+ ", batchStatus=" + batchStatus + ", batchLocation=" + batchLocation + "]";
	}
}

