package syksy2021.Notebook.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import syksy2021.Notebook.domain.Note;
import syksy2021.Notebook.domain.NoteRepository;

@RestController
public class NotebookRestController {
	
	@Autowired
	private NoteRepository noterepo;
	
	//GET ALL NOTES
	@GetMapping("/api/notes")
	public ResponseEntity<List<Note>> getAllNotes() {
		List<Note> nlist = (List<Note>) noterepo.findAll(); // muodostaa listan kaikista repon muistiinpanoista
		return new ResponseEntity<>(nlist, HttpStatus.OK); //palauttaa haetun listan ja 200
	}
	
} 