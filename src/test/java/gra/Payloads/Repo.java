package gra.Payloads;

public class Repo {
	
	//select all keys (name, description , homepage and is_template) and click generate getters and setters under source option
	// to generate get and set methods for each keys of reqst body.
	
	String name;
	String description;
	String homepage;
	Boolean is_template;
	Boolean has_issues;
	Boolean has_projects;
	Boolean has_wiki;
	
	public Boolean getHas_issues() {
		return has_issues;
	}
	public void setHas_issues(Boolean has_issues) {
		this.has_issues = has_issues;
	}
	public Boolean getHas_projects() {
		return has_projects;
	}
	public void setHas_projects(Boolean has_projects) {
		this.has_projects = has_projects;
	}
	public Boolean getHas_wiki() {
		return has_wiki;
	}
	public void setHas_wiki(Boolean has_wiki) {
		this.has_wiki = has_wiki;
	}
	
   public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public Boolean getIs_template() {
		return is_template;
	}
	public void setIs_template(Boolean is_template) {
		this.is_template = is_template;
	}
	
}
