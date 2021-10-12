package syksy2021.Notebook.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import syksy2021.Notebook.domain.CategoryRepository;
import syksy2021.Notebook.domain.CreatorRepository;
import syksy2021.Notebook.domain.LocationRepository;
import syksy2021.Notebook.domain.NoteRepository;

@Controller
public class NotebookController {
	
	@Autowired
	private CategoryRepository catRepo;
	@Autowired
	private CreatorRepository creaRepo;
	@Autowired
	private LocationRepository locRepo;
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
}
