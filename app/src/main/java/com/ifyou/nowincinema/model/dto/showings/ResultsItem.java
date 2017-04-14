package com.ifyou.nowincinema.model.dto.showings;

public class ResultsItem{
	private int datetime;
	private boolean originalLanguage;
	private boolean threeD;
	private Movie movie;
	private boolean fourDx;
	private String price;
	private boolean imax;
	private int id;
	private Place place;

	public void setDatetime(int datetime){
		this.datetime = datetime;
	}

	public int getDatetime(){
		return datetime;
	}

	public void setOriginalLanguage(boolean originalLanguage){
		this.originalLanguage = originalLanguage;
	}

	public boolean isOriginalLanguage(){
		return originalLanguage;
	}

	public void setThreeD(boolean threeD){
		this.threeD = threeD;
	}

	public boolean isThreeD(){
		return threeD;
	}

	public void setMovie(Movie movie){
		this.movie = movie;
	}

	public Movie getMovie(){
		return movie;
	}

	public void setFourDx(boolean fourDx){
		this.fourDx = fourDx;
	}

	public boolean isFourDx(){
		return fourDx;
	}

	public void setPrice(String price){
		this.price = price;
	}

	public String getPrice(){
		return price;
	}

	public void setImax(boolean imax){
		this.imax = imax;
	}

	public boolean isImax(){
		return imax;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPlace(Place place){
		this.place = place;
	}

	public Place getPlace(){
		return place;
	}

	@Override
 	public String toString(){
		return 
			"ResultsItem{" + 
			"datetime = '" + datetime + '\'' + 
			",original_language = '" + originalLanguage + '\'' + 
			",three_d = '" + threeD + '\'' + 
			",movie = '" + movie + '\'' + 
			",four_dx = '" + fourDx + '\'' + 
			",price = '" + price + '\'' + 
			",imax = '" + imax + '\'' + 
			",id = '" + id + '\'' + 
			",place = '" + place + '\'' + 
			"}";
		}
}
