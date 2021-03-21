package marcos.project.webscrapping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import marcos.project.webscrapping.model.RepositoryReport;
import marcos.project.webscrapping.service.RepositoryReportService;

@RestController
@RequestMapping("/report")
public class RepositoryReportController {
	
	@Autowired
	private RepositoryReportService service;
	
	@GetMapping
	public ResponseEntity<RepositoryReport> retrieveInfomation(@RequestParam(value= "repositoryAddress", required = true) String repositoryAddress){
		
		RepositoryReport result = this.service.searchByFilters(repositoryAddress);
		
		return ResponseEntity.ok(result);
	}

}
