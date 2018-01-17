package au.gov.dva.mailroom.thesaurus.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import au.gov.dva.mailroom.thesaurus.loader.service.ThesaurustermLoaderService;


@SpringBootApplication
public class ThesaurusTermsLoaderApplication implements CommandLineRunner  {
	@Autowired
	private ThesaurustermLoaderService thesaurustermService;
	@Value("${client.electronic.document}")
	private String clientElectronicDocument;
	@Value("${thesaurusterm.insert.filename}")
	private String insertThesaurustermFile;

	public static void main(String[] args) {
		SpringApplication.run(ThesaurusTermsLoaderApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		thesaurustermService.generateLoadThesaurustermSqlScript(this.clientElectronicDocument, this.insertThesaurustermFile);
		
	}
	
}
