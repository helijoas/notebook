package syksy2021.Notebook.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotebookController {
	
	@GetMapping ("/index")
	public String index() {
		return "welcome";
	}

}
