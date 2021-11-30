package syksy2021.Notebook.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import syksy2021.Notebook.domain.Category;
import syksy2021.Notebook.domain.CategoryRepository;
import syksy2021.Notebook.domain.Note;
import syksy2021.Notebook.domain.NoteRepository;

@RestController
public class NotebookRestController {
	
	@Autowired
	private NoteRepository noterepo;
	@Autowired
	private CategoryRepository catrepo;
	
	//GET ALL NOTES
	//@PreAuthorize("hasAuthority('ADMIN','USER')")
	@GetMapping("/api/notes")
	public ResponseEntity<List<Note>> getAllNotes() {
		List<Note> nlist = (List<Note>) noterepo.findAll(); // muodostaa listan kaikista repon muistiinpanoista
		return new ResponseEntity<>(nlist, HttpStatus.OK); // palauttaa haetun listan ja 200
	}
	
	//GET NOTE BY ID
	//@PreAuthorize("hasAuthority('ADMIN','USER')")
	@GetMapping("/api/notes/{id}")
	public ResponseEntity<?> getNoteById(@PathVariable (value="id") Long noteId) {
		Map<String, String> response = new HashMap<String, String>(); // alustetaan uusi response
		Optional<Note> note = noterepo.findById(noteId);
		if (note.isEmpty()) { //jos muistiinpanoa ei löydy
			response.put("message", "Note not found");
			return new ResponseEntity<> (response, HttpStatus.NOT_FOUND);
		} else { // jos muistiinpano löytyy
			return new ResponseEntity<> (note, HttpStatus.OK);
		}
	}
	
	//GET ALL CATEGORIES
	//@PreAuthorize("hasAuthority('ADMIN','USER')")
	@GetMapping("/api/categories")
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> clist = (List<Category>) catrepo.findAll(); // muodostaa listan kaikista repon kategorioista
		return new ResponseEntity<>(clist, HttpStatus.OK); // palauttaa haetun listan ja 200
	}
	
	//GET CATEGORY BY ID
	//@PreAuthorize("hasAuthority('ADMIN','USER')")
	@GetMapping("/api/categories/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable (value="id") Long catId) {
		Map<String, String> response = new HashMap<String, String>(); // alustetaan uusi response
		Optional<Category> cat = catrepo.findById(catId); // haetaan mahdollinen kategoria
		if (cat.isEmpty()) { // jos kategoriaa ei löydy
			response.put("message", "Category not found");
			return new ResponseEntity<> (response, HttpStatus.NOT_FOUND);
		} else { // jos kategoria löytyy
			return new ResponseEntity<> (cat, HttpStatus.OK);
		}
	}
	
	//GET NOTES BY CATEGORY
	//@PreAuthorize("hasAuthority('ADMIN','USER')")
	@GetMapping("/api/categories/{id}/notes")
	public ResponseEntity<?> getNotesByCategory(@PathVariable (value = "id") Long catId) {
		Map<String, String> response = new HashMap<String, String>(); // alustetaan uusi response
		Optional<Category> optCat = catrepo.findById(catId); // haetaan mahdollinen kategoria
		if (optCat.isEmpty()) { // jos kategoriaa ei löydy
			response.put("message", "Category not found");
			return new ResponseEntity<> (response, HttpStatus.NOT_FOUND);
		} else {
			Category cat = optCat.get(); // haetaan kategorian tiedot
			List <Note> nlist = cat.getNotes(); // haetaan kategoriaan liitetyt muistiinpanot
			if (nlist.isEmpty()) { // tarkistetaan onko muistiinpanojen lista tyhjä
				response.put("message", "No associated notes");
				return new ResponseEntity<> (response, HttpStatus.OK);
			} else { // mikäli muistiinpanoja löytyy
				return new ResponseEntity<> (nlist, HttpStatus.OK);
			}
		}
	}
	
	// POST NEW NOTE
	//@PreAuthorize("hasAuthority('ADMIN','USER')")
	@PostMapping("/api/notes")
	public ResponseEntity<?> createNote(@Valid @RequestBody Note note, BindingResult bindingresult) {
		Map<String, String> response = new HashMap<String, String>(); // alustetaan uusi response
		if (bindingresult.hasErrors()) { // jos validointi tuottaa errorin
			response.put("message", "Some of the mandatory fields (noteName, creator) are missing");
			return new ResponseEntity<> (response, HttpStatus.BAD_REQUEST);
		} else { // muuten luodaan uusi muistiinpano
			return new ResponseEntity<> (noterepo.save(note), HttpStatus.CREATED);
		}
	}
	
	// POST NEW CATEGORY
	//@PreAuthorize("hasAuthority('ADMIN','USER')")
	@PostMapping("/api/categories")
	public ResponseEntity<?> createCategory(@Valid @RequestBody Category cat, BindingResult bindingresult) {
		Map<String, String> response = new HashMap<String, String>(); // alustetaan uusi response
		if (bindingresult.hasErrors()) { // jos validointi tuottaa errorin
			response.put("message", "Mandatory field missing");
			return new ResponseEntity<> (response, HttpStatus.BAD_REQUEST);
		} else {
			if (catrepo.findByCategoryName(cat.getCategoryName()) != null) { // tarkistetaan onko ko kategoria jo olemassa
				response.put("message", "Category already exists");
				return new ResponseEntity<> (response, HttpStatus.CONFLICT);
			} else {
				return new ResponseEntity<> (catrepo.save(cat), HttpStatus.CREATED);
			}	
		}
	}
	
	// DELETE NOTE BY ID
	//@PreAuthorize("hasAuthority('ADMIN','USER')")
	@DeleteMapping("/api/notes/{id}")
	public ResponseEntity<?> deleteNoteById(@PathVariable (value = "id") Long noteId) {
		Map<String, String> response = new HashMap<String, String>(); // alustetaan uusi response
		Optional<Note> optNote = noterepo.findById(noteId); // tarkistetaan löytyykö muistiinpanoa ko id:llä
		if (optNote.isEmpty()) { // jos muistiinpanoa ei löydy
			response.put("message", "Note not found");
			return new ResponseEntity<> (response, HttpStatus.NOT_FOUND);
		} else { // jos muistiinpano löytyy
			response.put("message", "Note deleted");
			noterepo.deleteById(noteId); // poistetaan muistiinpano
			return new ResponseEntity<> (response, HttpStatus.OK);
		}
	}
	
	// DELETE CATEGORY BY ID
	//@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/api/categories/{id}")
	public ResponseEntity<?> deleteCategoryById(@PathVariable (value="id") Long catId) {
		Map<String, String> response = new HashMap<String, String>(); //alustetaan uusi response
		Optional<Category> optCat = catrepo.findById(catId); // tarkistetaan löytyykö kategoriaa ko id:llä
		if (optCat.isEmpty()) { // jos kategoriaa ei löydy
			response.put("message", "Category not found");
			return new ResponseEntity<> (response, HttpStatus.NOT_FOUND);
		} else {
			if (optCat.get().getNotes().isEmpty()) { // tarkistetaan onko kategoriaan liitetty muistiinpanoja
				response.put("message", "Category deleted");
				catrepo.deleteById(catId); // poistetaan kategoria
				return new ResponseEntity<> (response, HttpStatus.OK);
			} else { // jos kategoriaan on liitetty muistiinpanoja
				response.put("message", "Category has associated notes, cannot be deleted");
				return new ResponseEntity<> (response, HttpStatus.FORBIDDEN);
			}
		}
	}
	
	// PUT (UPDATE) NOTE BY ID
		//@PreAuthorize("hasAuthority('ADMIN','USER')")
		@PutMapping("/api/notes/{id}")
		public ResponseEntity<?> updateNoteById(@Valid @RequestBody Note newNote, BindingResult bindingresult, 
				@PathVariable (value = "id") Long noteId) {
			Map<String, String> response = new HashMap<String, String>(); //alustetaan uusi response
			if (bindingresult.hasErrors()) { // jos validointi tuottaa errorin
				response.put("message", "Some of the mandatory fields (noteName, creator) are missing");
				return new ResponseEntity<> (response, HttpStatus.BAD_REQUEST);
			} else {
				Optional<Note> target = noterepo.findById(noteId); // haetaan mahdollinen muistiinpano
				if (target.isEmpty()) { // jos muistiinpanoa ei löydy
					response.put("message", "Note not found");
					return new ResponseEntity<> (response, HttpStatus.NOT_FOUND);
				} else { // jos muistiinpano löytyy
					Note note = target.get(); // hateaan päivitettävän muistiinpanon tiedot
					note.setNoteName(newNote.getNoteName());
					note.setCreator(newNote.getCreator());
					note.setLocation(newNote.getLocation());
					note.setThoughts(newNote.getThoughts());
					note.setDate(newNote.getDate());
					note.setEvaluation(newNote.getEvaluation());
					note.setCategory(newNote.getCategory());
					noterepo.save(note); // tallennetaan muistiinpano uusin tiedoin
					return new ResponseEntity<> (note, HttpStatus.OK);
				}
			}
		}
		
		// PUT (UPDATE) CATEGORY BY ID
		//@PreAuthorize("hasAuthority('ADMIN')")
		@PutMapping ("/api/categories/{id}")
		public ResponseEntity<?> updateCategoryById(@Valid @RequestBody Category newCat, BindingResult bindingresult,
				@PathVariable (value = "id") Long catId) {
			Map<String,String> response = new HashMap<String, String>(); // alustetaan uusi response
			if (bindingresult.hasErrors()) { // jos validointi tuottaa errorin
				response.put("message", "Mandatory field is missing");
				return new ResponseEntity<> (response, HttpStatus.BAD_REQUEST);
			} else {
				Optional<Category> target = catrepo.findById(catId); // haetaan mahdollinen muistiinpano
				if (target.isEmpty()) { // jos kategoriaa ei löydy
					response.put("message", "Category not found");
					return new ResponseEntity<> (response, HttpStatus.NOT_FOUND);
				} else { // jos kategoria löytyy
					Category cat = target.get(); // haetaan päivitettävän kategorian tiedot
					cat.setCategoryName(newCat.getCategoryName());
					catrepo.save(cat); // tallennetaan kategoria uusin tiedoin
					return new ResponseEntity<> (cat, HttpStatus.OK);
				}
			}
		}
} 