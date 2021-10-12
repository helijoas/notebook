package syksy2021.Notebook.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Creator {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long creatorId;
	
	private String creatorName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
	private List<Note> notes;
	
	public Creator() {
		super();
	}

	public Creator(String creatorName) {
		super();
		this.creatorName = creatorName;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	@Override
	public String toString() {
		return "Creator [creatorId=" + creatorId + ", creatorName=" + creatorName + ", notes=" + notes + "]";
	}
	
	
	
	
}
