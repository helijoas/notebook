package syksy2021.Notebook.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import syksy2021.Notebook.domain.Category;
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
	
	// kategorian lisäys
	@GetMapping ("/addcategory")
	public String addCategory (Model model) {
		model.addAttribute("category", new Category());
		return "addcategory";
	}
	
	// tallenna uusi kategoria
	@PostMapping ("/savecategory")
	public String saveCategory(Category category) {
		List<Category> existingCategory = catRepo.findByCategoryName(category.getCategoryName()); //tarkistetaan löytyykö kannasta jo samannimistä kategoriaa
		if (existingCategory.isEmpty()) { 
			catRepo.save(category); // tallennetaan kantaan kun samannimistä ei löydy
			return "redirect:/addnote"; // ohjataan lisäämään muistiinpanoa
		} else {
			return "redirect:/addnote"; //palautetaan lisäämään muistiinpano tallentamatta kategoriaa uudestaan kantaan
		}	
	}
	
	// hae kannasta muistiinpanojen editiointia varten
	@GetMapping ("/editnote/{id}")
	public String editNote(@PathVariable ("id") Long noteId, Model model) {
		model.addAttribute("note", noteRepo.findById(noteId));
		model.addAttribute("categories", catRepo.findAll());
		return "editnote";
	}
	
	// poista muistiinpano
	@GetMapping ("/deletenote/{id}")
	public String deleteNote(@PathVariable ("id") Long noteId) {
		noteRepo.deleteById(noteId);
		// model.addAttribute("note", noteRepo.findById(noteId));
		return "redirect:/notelist";
	}
}

