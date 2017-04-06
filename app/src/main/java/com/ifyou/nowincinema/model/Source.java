package com.ifyou.nowincinema.model;

import java.io.Serializable;

public class Source implements Serializable {
	private String name;
	private String link;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setLink(String link){
		this.link = link;
	}

	public String getLink(){
		return link;
	}

	@Override
 	public String toString(){
		return 
			"Source{" + 
			"name = '" + name + '\'' + 
			",link = '" + link + '\'' + 
			"}";
		}
}
