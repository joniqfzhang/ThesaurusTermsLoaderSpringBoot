package au.gov.dva.mailroom.thesaurus.loader.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import au.gov.dva.mailroom.thesaurus.loader.model.Thesaurusterm;
import au.gov.dva.mailroom.thesaurus.loader.model.ThesaurustermRecord;
@Service
public class ThesaurustermLoaderServiceImpl implements ThesaurustermLoaderService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public List<ThesaurustermRecord> createThesaurustermRecords(Thesaurusterm rootThesaurusterm) {
		List<ThesaurustermRecord> thesaurustermRecords = new ArrayList<ThesaurustermRecord>();
		if(rootThesaurusterm.getThesaurustermList().size() > 0 ) {
			rootThesaurusterm.getThesaurustermList().forEach( i1 -> {	
				if(i1.getThesaurustermList().size() > 0) {
					i1.getThesaurustermList().forEach(i2 ->{						
						if(i2.getThesaurustermList().size() > 0) {
							// with level0, level1, level2, and level 3
							i2.getThesaurustermList().forEach(i3 -> {								
								ThesaurustermRecord thesaurustermRecord = new ThesaurustermRecord();
								thesaurustermRecord.setLevel3(i3.getName());
								thesaurustermRecord.setLevel2(i2.getName());
								thesaurustermRecord.setLevel1(i1.getName());
								thesaurustermRecord.setLevel0(rootThesaurusterm.getName());
								thesaurustermRecords.add(thesaurustermRecord);
							});
						}else{
							// with level0, level1, and level2
							ThesaurustermRecord thesaurustermRecord = new ThesaurustermRecord();
							thesaurustermRecord.setLevel2(i2.getName());
							thesaurustermRecord.setLevel1(i1.getName());
							thesaurustermRecord.setLevel0(rootThesaurusterm.getName());
							thesaurustermRecords.add(thesaurustermRecord);
						}						
					});
				}else{
					// with level0 and level1
					ThesaurustermRecord thesaurustermRecord = new ThesaurustermRecord();
					thesaurustermRecord.setLevel1(i1.getName());
					thesaurustermRecord.setLevel0(rootThesaurusterm.getName());
					thesaurustermRecords.add(thesaurustermRecord);
				}					
			});			
		}else{
			// with level0
			ThesaurustermRecord thesaurustermRecord = new ThesaurustermRecord();
			thesaurustermRecord.setLevel0(rootThesaurusterm.getName());
			thesaurustermRecords.add(thesaurustermRecord);
		}
		// all records sorted by id
		List<ThesaurustermRecord> sortedThesaurustermRecords = sortThesaurustermRecords(thesaurustermRecords);
		return sortedThesaurustermRecords;
	}
	
	@Override
	public void createThesaurustermInserts(List<ThesaurustermRecord> thesaurustermRecordList, String fileToWrite) {
		Path sqlInsertFile = Paths.get(fileToWrite);
		try (BufferedWriter writer = 
                Files.newBufferedWriter(sqlInsertFile, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
			//produce delete old thesaurusterm record sql script
			writer.write(deleteThesaurustermSql());
			thesaurustermRecordList.forEach(r -> {
				try {
					//produce insert current thesaurusterm record sql script
					writer.write(insertThesaurustermSql(r));
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
			});
          
       } catch (Exception e) {
    	   logger.error(e.getMessage());
           e.printStackTrace();
       }

	}
	/**
	 * Sort ThesaurustermRecords by level0, level1, level2, and level3
	 * @param thesaurustermRecords to be sorted
	 * @return the list of sorted thesaurustermRecords
	 */
	private List<ThesaurustermRecord> sortThesaurustermRecords(List<ThesaurustermRecord> thesaurustermRecords) {
		Comparator<ThesaurustermRecord> sortByLevel0 = (t1, t2) -> t1.getLevel0().compareToIgnoreCase(t2.getLevel0());
		Comparator<ThesaurustermRecord> sortByLevel1 = (t1, t2) -> t1.getLevel1().compareToIgnoreCase(t2.getLevel1());
		Comparator<ThesaurustermRecord> sortByLevel2 = (t1, t2) -> t1.getLevel2().compareToIgnoreCase(t2.getLevel2());
		Comparator<ThesaurustermRecord> sortByLevel3 = (t1, t2) -> t1.getLevel3().compareToIgnoreCase(t2.getLevel3());

		List<ThesaurustermRecord> sortedThesaurustermRecords = thesaurustermRecords
				.stream()
				.sorted(sortByLevel0
						.thenComparing(sortByLevel1)
						.thenComparing(sortByLevel2)
						.thenComparing(sortByLevel3))
				.collect(Collectors.toList());
		return sortedThesaurustermRecords;
	}
	/**
	 * Produce thesaurusterm record sql script for a ThesaurustermRecord.
	 * @param record, a ThesaurustermRecord.
	 * @return String of sql script for inserting the record to database. 
	 */
	private String insertThesaurustermSql(ThesaurustermRecord record) {
		
		String newLine = System.getProperty("line.separator");					
		String sql = "INSERT INTO MAILROOM.THESAURUS_TERMS "
				+ "("
				+ "THESAURUS_LEVEL0, "
				+ "THESAURUS_LEVEL1, "
				+ "THESAURUS_LEVEL2, "
				+ "THESAURUS_LEVEL3) "
				+ "VALUES ("				
				+ (record.getLevel0() != null ? "'" + record.getLevel0().replace("'", "''")  + "'" : null) + ", "
				+ (record.getLevel1() != null ? "'" + record.getLevel1().replace("'", "''") + "'" : null) + ", "
				+ (record.getLevel2() != null ? "'" + record.getLevel2().replace("'", "''") + "'" : null) + ", "
				+ (record.getLevel3() != null ? "'" + record.getLevel3().replace("'", "''") + "'" : null) + ");"
				+ newLine;
		logger.info(sql);
		return sql;
	}
	/**
	 * Produce deletion sql script for database MAILROOM.THESAURUS_TERMS table and reset identity column unique sequence number.
	 * @return String of sql produced.
	 */
	private String deleteThesaurustermSql() {
		String newLine = System.getProperty("line.separator");
		//perform old THESAURUS TERMS record deletion
		String sql = "DELETE FROM MAILROOM.THESAURUS_TERMS;" + newLine;
		//perform reset THESAURUS_TERMS_ID
		sql = sql + "ALTER TABLE MAILROOM.THESAURUS_TERMS ALTER COLUMN THESAURUS_TERMS_ID RESTART WITH 1;" + newLine;
		logger.info(sql);
		return sql;
	}

	@Override
	public void generateLoadThesaurustermSqlScript(String clientElectronicDocument, String insertThesaurustermFile)
			throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(Thesaurusterm.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
        //File xml = new File("./ClientElectronicDocument.xml");
		File xml = new File(clientElectronicDocument);
        Thesaurusterm  rootThesaurusterm = (Thesaurusterm) unmarshaller.unmarshal(xml);
        if(rootThesaurusterm != null) {
        	System.out.println(rootThesaurusterm.toString());
        	List<ThesaurustermRecord> thesaurustermRecords = createThesaurustermRecords(rootThesaurusterm);
        	if(thesaurustermRecords != null) {
        		System.out.println("Records count = " +thesaurustermRecords.size());
        		thesaurustermRecords.forEach(r -> {
        			logger.info(r.toString());       			
        		});
        		createThesaurustermInserts(thesaurustermRecords, insertThesaurustermFile);        		
        	}
        }
	}

}
