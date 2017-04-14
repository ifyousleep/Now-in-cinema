package com.ifyou.nowincinema.model.dto.details;

public class Source{
	private String link;
	private String name;

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"Source{" + 
			"link = '" + link + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}
