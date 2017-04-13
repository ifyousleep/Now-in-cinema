package com.ifyou.nowincinema.model.place;

public class Movie{
	private int id;
	private String title;
	private Poster poster;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}

	public void setPoster(Poster poster){
		this.poster = poster;
	}

	public Poster getPoster(){
		return poster;
	}

	@Override
 	public String toString(){
		return 
			"Movie{" + 
			"id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",poster = '" + poster + '\'' + 
			"}";
		}
}
