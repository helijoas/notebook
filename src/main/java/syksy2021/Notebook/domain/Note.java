package syksy2021.Notebook.domain;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Note {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long noteId;
	
	//@NotBlank(message = "Note name is mandatory")
	private String noteName; 
	
	//@NotBlank(message = "Creator is mandatory")
	private String creator;
	
	private String location, thoughts;
	private LocalDate date;
	private Integer evaluation;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	
	public Note() {
		super();
	}
	
	public Note(String noteName, LocalDate date, String thoughts, Integer evaluation, String creator, String location,
			Category category) {
		super();
		this.noteName = noteName;
		this.date = date;
		this.thoughts = thoughts;
		this.evaluation = evaluation;
		this.creator = creator;
		this.location = location;
		this.category = category;
	}

	public Long getNoteId() {
		return noteId;
	}

	public void setNoteId(Long noteId) {
		this.noteId = noteId;
	}

	public String getNoteName() {
		return noteName;
	}

	public void setNoteName(String noteName) {
		this.noteName = noteName;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getThoughts() {
		return thoughts;
	}

	public void setThoughts(String thoughts) {
		this.thoughts = thoughts;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", noteName=" + noteName + ", date=" + date + ", thoughts=" + thoughts
				+ ", evaluation=" + evaluation + ", creator=" + creator + ", location=" + location + ", category="
				+ category + "]";
	}
	
	
}
