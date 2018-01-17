package au.gov.dva.mailroom.thesaurus.loader.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
/**
 * Represent Thesaurusterm corresponding xml file, contains only the information need to store in database.
 * @author zzhanq
 *
 */
@XmlRootElement(name = "THESAURUSTERM")
@XmlType(propOrder = {"name", "thesaurustermList"})
public class Thesaurusterm {	
	private String name;
	private List<Thesaurusterm> thesaurustermList;
			
	@XmlElement(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	// XmlElementWrapper generates a wrapper element around XML representation
	@XmlElementWrapper(name="NARROWTERMS")
	// XmlElement sets the name of the entities
	@XmlElement(name="THESAURUSTERM")
	public List<Thesaurusterm> getThesaurustermList() {
		return thesaurustermList;
	}
	public void setThesaurustermList(List<Thesaurusterm> thesaurusterms) {
		this.thesaurustermList = thesaurusterms;
	}
	@Override
	public String toString() {
		return "Thesaurusterm [name=" + name + ", thesaurustermList=" + thesaurustermList + "]";
	}
		
}
