/**
 * 
 */
package au.gov.dva.mailroom.thesaurus.loader.service;

import java.util.List;

import javax.xml.bind.JAXBException;

import au.gov.dva.mailroom.thesaurus.loader.model.Thesaurusterm;
import au.gov.dva.mailroom.thesaurus.loader.model.ThesaurustermRecord;

/**
 * Services to facilitate Thesaurusterm info extracted from xml document and transfer to database records.
 * @author zzhanq
 *
 */
public interface ThesaurustermLoaderService {
	/**
	 * Processes a root Thesaurusterm object and generates a list of ThesaurustermRecord object, which represent corresponding database records.
	 * @param rootThesaurusterm type of Thesaurusterm.
	 * @return list of ThesaurustermRecord objects.
	 */
	public List<ThesaurustermRecord> createThesaurustermRecords(Thesaurusterm rootThesaurusterm);
	/**
	 * Produce insert database script for Thesaurusterm Records to insert into database. 
	 * @param thesaurustermRecordList, the ThesaurustermRecord list of Thesaurusterm Records need to insert into database.
	 * @param fileName, the file name (including path) that contains the insert database scripts.
	 */
	public void createThesaurustermInserts(List<ThesaurustermRecord> thesaurustermRecordList, String fileName);
	/**
	 * Parses clientElectronicDocument xml document and produces insertThesaurustermFile (contains database loading sql scripts).
	 * @param clientElectronicDocument, String of Thesaurusterm xml file.
	 * @param insertThesaurustermFile, sql script produced from info extracted in clientElectronicDocument
	 * @throws JAXBException
	 */
	public void generateLoadThesaurustermSqlScript(String clientElectronicDocument, String insertThesaurustermFile) throws JAXBException;
}
