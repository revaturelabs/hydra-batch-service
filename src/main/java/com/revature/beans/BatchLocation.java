package com.revature.beans;

import javax.persistence.*;

/**
 * Created by August Duet on 4/7/2017.
 */
@Entity
@Table(name="BATCH_LOCATION")
public class BatchLocation {
    @Id
    @Column(name = "BATCH_LOCATION_ID")
    @SequenceGenerator(allocationSize = 1, name = "batchLocSeq", sequenceName = "BATCH_LOC_SEQ")
    @GeneratedValue(generator = "batchLocSeq", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name="LOCATION_ID")
    private Integer locationId = null;

    @Column(name="BUILDING_ID")
    private Integer buildingId = null;

    @Column(name="ROOM_ID")
    private Integer roomId = null;
    
    public BatchLocation(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildingId == null) ? 0 : buildingId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
		result = prime * result + ((roomId == null) ? 0 : roomId.hashCode());
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
		BatchLocation other = (BatchLocation) obj;
		if (buildingId == null) {
			if (other.buildingId != null)
				return false;
		} else if (!buildingId.equals(other.buildingId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (locationId == null) {
			if (other.locationId != null)
				return false;
		} else if (!locationId.equals(other.locationId))
			return false;
		if (roomId == null) {
			if (other.roomId != null)
				return false;
		} else if (!roomId.equals(other.roomId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatchLocation [id=" + id + ", locationId=" + locationId + ", buildingId=" + buildingId + ", roomId="
				+ roomId + "]";
	}
}
