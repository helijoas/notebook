package syksy2021.Notebook.web;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import syksy2021.Notebook.domain.CategoryRepository;
import syksy2021.Notebook.domain.Note;
import syksy2021.Notebook.domain.NoteRepository;

@Controller
public class NotebookController {
	
	@Autowired
	private CategoryRepository catRepo;
	@Autowired
	private NoteRepository noteRepo;
	
	// index-sivu
	@GetMapping ("/index")
	public String index() {
		return "welcome";
	}
	
	// muistiinpanojen listaus
	@GetMapping ("/notelist")
	public String notelist(Model model) {
		model.addAttribute("notes", noteRepo.findAll());
		return "notelist";
	}
	
	// muistiinpanojen lisäys
	@GetMapping ("/addnote")
	public String addNote (Model model) {
		model.addAttribute("note", new Note());
		model.addAttribute("categories", catRepo.findAll());
		return "addnote";
	}
	
	// tallenna uusi muistiinpano
	@PostMapping ("/savenote")
	public String saveNote(Note note) {
		//LocalDate date = LocalDate.now();
		//note.setDate(date);
		noteRepo.save(note);
		return "redirect:/notelist";
	}
	
	// hae kannasta muistiinpanojen editiointia varten
	@GetMapping ("/editnote/{id}")
	public String editNote(@PathVariable ("id") Long noteId, Model model) {
		model.addAttribute("note", noteRepo.findById(noteId));
		model.addAttribute("categories", catRepo.findAll());
		return "editnote";
	}
}
