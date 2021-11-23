package syksy2021.Notebook.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	@GetMapping ("/login")
	public String index() {
		return "welcome";
	}
	
	// muistiinpanojen listaus
	@GetMapping ("/notelist")
	public String listNotes(Model model) {
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
	
	//kategorioiden listaus
	@GetMapping ("/admin/categorylist")
	public String listCategories(Model model) {
		model.addAttribute("categories", catRepo.findAll());
		return "categorylist";
	}
	
	// kategorian lisäys
	@GetMapping ("/addcategory")
	public String addCategory (Model model) {
		model.addAttribute("category", new Category());
		return "addcategory";
	}
	
	// tallenna uusi kategoria
	@PostMapping ("/savecategory")
	public String saveCategory(@Valid Category category, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { // tarkistetaan heittääkö validointi errorin
			return "addcategory";
		} else {
			List<Category> existingCategory = catRepo.findByCategoryName(category.getCategoryName()); //tarkistetaan löytyykö kannasta jo samannimistä kategoriaa
			if (existingCategory.isEmpty()) { 
				catRepo.save(category); // tallennetaan kantaan kun samannimistä ei löydy
				return "redirect:/addnote"; // ohjataan lisäämään muistiinpanoa
			} else {
				return "redirect:/addnote"; //ohjataan lisäämään muistiinpano tallentamatta kategoriaa uudestaan kantaan
			}	
		}
	}
	
	// kategorian lisäys adminin puolelta
	@GetMapping ("/admin/addcategory")
	public String addCategoryAdmin (Model model) {
		model.addAttribute("category", new Category());
		return "adminaddcategory";
	}
	
	// tallenna uusi kategoria adminin puolelta
	@PostMapping ("/admin/savecategory")
	public String saveCategoryAdmin(@Valid Category category, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) { // tarkistetaan heittääkö validointi errorin
			return "adminaddcategory";
		} else {
			List<Category> existingCategory = catRepo.findByCategoryName(category.getCategoryName()); //tarkistetaan löytyykö kannasta jo samannimistä kategoriaa
			if (existingCategory.isEmpty()) { 
				catRepo.save(category); // tallennetaan kantaan kun samannimistä ei löydy
				return "redirect:/admin/categorylist"; // ohjataan lisäämään muistiinpanoa
			} else {
				return "redirect:/admin/categorylist"; //ohjataan lisäämään muistiinpano tallentamatta kategoriaa uudestaan kantaan
			}
		}
	}
	
	// hae kannasta muistiinpano editiointia varten
	@GetMapping ("/editnote/{id}")
	public String editNote(@PathVariable ("id") Long noteId, Model model) {
		model.addAttribute("note", noteRepo.findById(noteId));
		model.addAttribute("categories", catRepo.findAll());
		return "editnote";
	}
	
	// hae kannasta kategoria editointia varten
	@GetMapping ("/admin/editcategory/{id}")
	public String editCategory(@PathVariable ("id") Long categoryId, Model model) {
		model.addAttribute("category", catRepo.findById(categoryId));
		return "editcategory";
	}
	
	// tallenna editoitu kategoria
	
	
	// poista muistiinpano
	@GetMapping ("/deletenote/{id}")
	public String deleteNote(@PathVariable ("id") Long noteId) {
		noteRepo.deleteById(noteId);
		return "redirect:/notelist";
	}
	
	// TARVITSEE TARKISTUSVIESTIT/HERJAT
	// poista kategoria
	@GetMapping ("/admin/deletecategory/{id}")
	public String deleteCategory(@PathVariable ("id") Long categoryId) {	
		Optional<Category> category = catRepo.findById(categoryId); // haetaan mahdollinen kategoria tietokannasta
		if (category.isPresent()) { // mikäli id:llä löytyy kategoria
			List<Note> notes = category.get().getNotes(); // haetaan mahdolliset kategoriaan liitetyt muistiinpanot
			if (notes.isEmpty()) { // mikäli kategoriaan ei ole liitetty muistiinpanoja
				catRepo.deleteById(categoryId); // poistetaan kategoria
				return "redirect:/admin/categorylist"; // palautetaan kategorialista
			} else {
				return "redirect:/admin/categorylist"; // palautetaan kategorialista poistamatta kategoriaa
			}
		} else {
			return "redirect:/categorylist";
		}
	}
}

