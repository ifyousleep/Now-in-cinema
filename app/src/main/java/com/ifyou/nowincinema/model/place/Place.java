package com.ifyou.nowincinema.model.place;

import java.util.List;

public class Place{
	private List<ImagesItem> images;
	private String address;
	private String subway;
	private String siteUrl;
	private String phone;
	private String location;
	private int id;
	private String title;
	private String slug;
	private Coords coords;
	private boolean isStub;
	private boolean isClosed;

	public void setImages(List<ImagesItem> images){
		this.images = images;
	}

	public List<ImagesItem> getImages(){
		return images;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setSubway(String subway){
		this.subway = subway;
	}

	public String getSubway(){
		return subway;
	}

	public void setSiteUrl(String siteUrl){
		this.siteUrl = siteUrl;
	}

	public String getSiteUrl(){
		return siteUrl;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return phone;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

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

	public void setSlug(String slug){
		this.slug = slug;
	}

	public String getSlug(){
		return slug;
	}

	public void setCoords(Coords coords){
		this.coords = coords;
	}

	public Coords getCoords(){
		return coords;
	}

	public void setIsStub(boolean isStub){
		this.isStub = isStub;
	}

	public boolean isIsStub(){
		return isStub;
	}

	public void setIsClosed(boolean isClosed){
		this.isClosed = isClosed;
	}

	public boolean isIsClosed(){
		return isClosed;
	}

	@Override
 	public String toString(){
		return 
			"Place{" + 
			"images = '" + images + '\'' + 
			",address = '" + address + '\'' + 
			",subway = '" + subway + '\'' + 
			",site_url = '" + siteUrl + '\'' + 
			",phone = '" + phone + '\'' + 
			",location = '" + location + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			",slug = '" + slug + '\'' + 
			",coords = '" + coords + '\'' + 
			",is_stub = '" + isStub + '\'' + 
			",is_closed = '" + isClosed + '\'' + 
			"}";
		}
}