package com.revature.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Trainer implements Serializable {
	private static final long serialVersionUID = -2546407792912483570L;
	
	/*	Fields that have been consolidated between Caliber and AssignForce
	 * 	TrainerId:
	 * 		Caliber used trainerId as an int, while AssignForce used a string for the Id
	 * 	Name:
	 * 		Caliber used a single String for name, while AssignForce used two Strings: firstName, lastName
	 *  Tier:
	 *  	Caliber used a TrainerRole class to differentiate between an active trainer and an inactive trainer
	 *  	AssignForce used a Boolean value.
	 *  
	 */
	private Integer trainerId;
	private String name;
	private String title;
	private String email;
	private TrainerRole tier;
	private Set<Batch> batches;
	
	// AssignForce specific fields
	private String resume;
	private List<SimpleCategory> skills;
	private List<Certification> certifications;

	public Trainer() {
		super();
	}

	public Trainer(String name, String title, String email, TrainerRole tier) {
		this();
		this.name = name;
		this.title = title;
		this.email = email;
		this.tier = tier;
	}

	public Trainer(Integer trainerId, String name, String title, String email, TrainerRole tier, Set<Batch> batches,
			String resume, List<SimpleCategory> skills, List<Certification> certifications) {
		super();
		this.trainerId = trainerId;
		this.name = name;
		this.title = title;
		this.email = email;
		this.tier = tier;
		this.batches = batches;
		this.resume = resume;
		this.skills = skills;
		this.certifications = certifications;
	}

	public Trainer(SimpleTrainer simpleTrainer){
		this();
		this.trainerId = simpleTrainer.getTrainerId();
		this.name = simpleTrainer.getName();
		this.title = simpleTrainer.getTitle();
		this.email = simpleTrainer.getEmail();
		this.tier = simpleTrainer.getTier();
		this.resume = simpleTrainer.getResume();
		this.certifications = simpleTrainer.getCertifications();
	}

	public Integer getTrainerId() {
		return trainerId;
	}

	public void setTrainerId(Integer trainerId) {
		this.trainerId = trainerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TrainerRole getTier() {
		return tier;
	}

	public void setTier(TrainerRole tier) {
		this.tier = tier;
	}

	public Set<Batch> getBatches() {
		return batches;
	}

	public void setBatches(Set<Batch> batches) {
		this.batches = batches;
	}
	
	

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public List<SimpleCategory> getSkills() {
		return skills;
	}

	public void setSkills(List<SimpleCategory> skills) {
		this.skills = skills;
	}

	public List<Certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batches == null) ? 0 : batches.hashCode());
		result = prime * result + ((certifications == null) ? 0 : certifications.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((resume == null) ? 0 : resume.hashCode());
		result = prime * result + ((skills == null) ? 0 : skills.hashCode());
		result = prime * result + ((tier == null) ? 0 : tier.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((trainerId == null) ? 0 : trainerId.hashCode());
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
		Trainer other = (Trainer) obj;
		if (batches == null) {
			if (other.batches != null)
				return false;
		} else if (!batches.equals(other.batches))
			return false;
		if (certifications == null) {
			if (other.certifications != null)
				return false;
		} else if (!certifications.equals(other.certifications))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (resume == null) {
			if (other.resume != null)
				return false;
		} else if (!resume.equals(other.resume))
			return false;
		if (skills == null) {
			if (other.skills != null)
				return false;
		} else if (!skills.equals(other.skills))
			return false;
		if (tier != other.tier)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (trainerId == null) {
			if (other.trainerId != null)
				return false;
		} else if (!trainerId.equals(other.trainerId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trainer [trainerId=" + trainerId + ", name=" + name + ", title=" + title + ", email=" + email
				+ ", tier=" + tier + ", batches=" + batches + ", resume=" + resume + ", skills=" + skills
				+ ", certifications=" + certifications + "]";
	}

	
}
