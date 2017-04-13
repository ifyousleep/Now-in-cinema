package com.ifyou.nowincinema.model.film;

public class Poster{
	private String image;
	private Source source;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setSource(Source source){
		this.source = source;
	}

	public Source getSource(){
		return source;
	}

	@Override
 	public String toString(){
		return 
			"Poster{" + 
			"image = '" + image + '\'' + 
			",source = '" + source + '\'' + 
			"}";
		}
}
