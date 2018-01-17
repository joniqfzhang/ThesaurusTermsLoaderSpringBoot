package au.gov.dva.mailroom.thesaurus.loader.model;

/**
 * Entity model that represents a database record for "MAILROOM"."THESAURUS_TERMS" table.
 * @author zzhanq
 *
 */
public class ThesaurustermRecord {	
	private int id;
	private String level0;
	private String level1;
	private String level2;
	private String level3;
		
	public int getId() {
		return id;
	}	
	public void setId(int id) {
		this.id = id;
	}	
	public String getLevel0() {
		return level0;
	}
	public void setLevel0(String level0) {
		this.level0 = level0;
	}
	public String getLevel1() {
		return level1;
	}
	public void setLevel1(String level1) {
		this.level1 = level1;
	}
	public String getLevel2() {
		return level2;
	}
	public void setLevel2(String level2) {
		this.level2 = level2;
	}
	public String getLevel3() {
		return level3;
	}
	public void setLevel3(String level3) {
		this.level3 = level3;
	}
	@Override
	public String toString() {
		return "ThesaurustermRecord [id=" + id + ", level0=" + level0 + ", level1=" + level1 + ", level2=" + level2
				+ ", level3=" + level3 + "]";
	}
	
}
