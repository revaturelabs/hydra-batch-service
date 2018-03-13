package com.revature.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Batch implements Serializable {
	private static final long serialVersionUID = -5755409643112884001L;

	private Integer batchId;
	private String resourceId;
	private String trainingName;
	private String name;
	private Trainer trainer;
	private Trainer coTrainer;
	private SkillType skillType;                                             
	private List<Integer> skills;
	private TrainingType trainingType;
	private String location;
	private Address address;
	private Short goodGradeThreshold;
	private Short borderlineGradeThreshold;
	private Set<Trainee> trainees;
	private Integer weeks;
	private Integer gradedWeeks;
	private Date startDate;
	private Date endDate;
	private Integer curriculum;
	private Integer focus; 
	private BatchStatusLookup batchStatus;
	private BatchLocation batchLocation;
	private Set<Note> notes;

	public Batch() {
		super();
		this.weeks = 1;
		this.gradedWeeks = 7;
		this.goodGradeThreshold = 80;
		this.borderlineGradeThreshold = 70;
		this.trainingType = TrainingType.Revature;
		this.trainees = new HashSet<>();
		this.notes = new HashSet<>();
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
	public Batch(String trainingName, Trainer trainer, Date startDate, Date endDate, String location) {
		this();
		this.trainingName = trainingName;
		this.trainer = trainer;
		this.skillType = SkillType.J2EE;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
	}

	public Batch(SimpleBatch simpleBatch) {
		this();
		this.batchId = simpleBatch.getBatchId();
		this.resourceId = simpleBatch.getResourceId();
		this.trainingName = simpleBatch.getTrainingName();
		this.skillType = simpleBatch.getSkillType();
		this.startDate = simpleBatch.getStartDate();
		this.endDate = simpleBatch.getEndDate();
		this.location = simpleBatch.getLocation();
		this.goodGradeThreshold = simpleBatch.getGoodGradeThreshold();
		this.borderlineGradeThreshold = simpleBatch.getBorderlineGradeThreshold();
		this.weeks = simpleBatch.getWeeks();
		this.gradedWeeks = simpleBatch.getGradedWeeks();
		this.skills = simpleBatch.getSkills();
		this.trainingType = simpleBatch.getTrainingType();
		this.focus = simpleBatch.getFocus();
		this.batchStatus = simpleBatch.getBatchStatus();
		this.batchLocation = simpleBatch.getBatchLocation();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public Trainer getCoTrainer() {
		return coTrainer;
	}

	public void setCoTrainer(Trainer coTrainer) {
		this.coTrainer = coTrainer;
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
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

	public Set<Trainee> getTrainees() {
		return trainees;
	}

	public void setTrainees(Set<Trainee> trainees) {
		this.trainees = trainees;
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

	public Set<Note> getNotes() {
		return notes;
	}

	public void setNotes(Set<Note> notes) {
		this.notes = notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((batchId == null) ? 0 : batchId.hashCode());
		result = prime * result + ((batchLocation == null) ? 0 : batchLocation.hashCode());
		result = prime * result + ((batchStatus == null) ? 0 : batchStatus.hashCode());
		result = prime * result + ((borderlineGradeThreshold == null) ? 0 : borderlineGradeThreshold.hashCode());
		result = prime * result + ((coTrainer == null) ? 0 : coTrainer.hashCode());
		result = prime * result + ((curriculum == null) ? 0 : curriculum.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((focus == null) ? 0 : focus.hashCode());
		result = prime * result + ((goodGradeThreshold == null) ? 0 : goodGradeThreshold.hashCode());
		result = prime * result + ((gradedWeeks == null) ? 0 : gradedWeeks.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
		result = prime * result + ((skillType == null) ? 0 : skillType.hashCode());
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((trainees == null) ? 0 : trainees.hashCode());
		result = prime * result + ((trainer == null) ? 0 : trainer.hashCode());
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
		Batch other = (Batch) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
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
		if (coTrainer == null) {
			if (other.coTrainer != null)
				return false;
		} else if (!coTrainer.equals(other.coTrainer))
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
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
		if (trainees == null) {
			if (other.trainees != null)
				return false;
		} else if (!trainees.equals(other.trainees))
			return false;
		if (trainer == null) {
			if (other.trainer != null)
				return false;
		} else if (!trainer.equals(other.trainer))
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
		return "Batch [batchId=" + batchId + ", resourceId=" + resourceId + ", trainingName=" + trainingName + ", name="
				+ name + ", trainer=" + trainer + ", coTrainer=" + coTrainer + ", skillType=" + skillType + ", skills="
				+ skills + ", trainingType=" + trainingType + ", location=" + location + ", address=" + address
				+ ", goodGradeThreshold=" + goodGradeThreshold + ", borderlineGradeThreshold="
				+ borderlineGradeThreshold + ", trainees=" + trainees + ", weeks=" + weeks + ", gradedWeeks="
				+ gradedWeeks + ", startDate=" + startDate + ", endDate=" + endDate + ", curriculum=" + curriculum
				+ ", focus=" + focus + ", batchStatus=" + batchStatus + ", batchLocation=" + batchLocation + ", notes="
				+ notes + "]";
	}

	
}
