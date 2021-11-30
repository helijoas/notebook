package syksy2021.Notebook;

import java.time.LocalDate;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import syksy2021.Notebook.domain.User;
import syksy2021.Notebook.domain.UserRepository;
import syksy2021.Notebook.domain.Category;
import syksy2021.Notebook.domain.CategoryRepository;
import syksy2021.Notebook.domain.Note;
import syksy2021.Notebook.domain.NoteRepository;

@SpringBootApplication
public class NotebookApplication {
	
	private static final Logger log = LoggerFactory.getLogger(NotebookApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(NotebookApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner notebook(CategoryRepository catRepo, NoteRepository noteRepo, UserRepository userRepo) {
		return (args) -> {
			log.info("Creating categories");
			catRepo.save(new Category ("Exhibitions"));
			catRepo.save(new Category ("Books"));
			catRepo.save(new Category ("Movies"));
			
			LocalDate exh1 = LocalDate.of(2021,10,10);
			LocalDate exh2 = LocalDate.of(2021,10,10);
			LocalDate book1 = LocalDate.of(2021,9,30);
			LocalDate movie1 = LocalDate.of(2021,10,9);
			
			log.info("Creating notes");
			noteRepo.save(new Note ("Blazing World", exh1, "Interesting new drawings and paintings.", 4, "Hannaleena Heiska",
					"Helsinki Contemporary", catRepo.findByCategoryName("Exhibitions").get(0)));
			noteRepo.save(new Note ("Rakkaus", exh2, "Amazing photography retrospective.", 4, "Susanna Majuri",
					"Valokuvataiteen museo K1", catRepo.findByCategoryName("Exhibitions").get(0)));
			noteRepo.save(new Note ("Where the Crawdads Sing", book1, "Touching survival story.", 5, "Delia Owens",
					"Home", catRepo.findByCategoryName("Books").get(0)));
			noteRepo.save(new Note ("Stargate", movie1, "Entertaining science fiction classic.", 3, "Roland Emmerich",
					"Home", catRepo.findByCategoryName("Movies").get(0)));
			
			log.info("Creating users"); // user:user, admin:admin
			userRepo.save(new User("user", "$2a$10$JuBTfoMcXQ3fKAlCbhH8euIWuxJ0gmE8W0AcnT.lJTEag8ouAw/2q", "USER"));
			userRepo.save(new User("admin", "$2a$10$u/j4faaPHc95K4lNWE4j6.wWvnsuYpltLHEvbRp4i/44FDhLPwrxK", "ADMIN" ));
		};
	}

}
