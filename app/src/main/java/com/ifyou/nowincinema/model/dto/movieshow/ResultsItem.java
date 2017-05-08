package com.ifyou.nowincinema.model.dto.movieshow;

public class ResultsItem {
    private int datetime;
    private boolean originalLanguage;
    private boolean threeD;
    private boolean fourDx;
    private String price;
    private boolean imax;
    private int id;
    private Place place;

    public int getDatetime() {
        return datetime;
    }

    public void setDatetime(int datetime) {
        this.datetime = datetime;
    }

    public boolean isOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(boolean originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public boolean isThreeD() {
        return threeD;
    }

    public void setThreeD(boolean threeD) {
        this.threeD = threeD;
    }

    public boolean isFourDx() {
        return fourDx;
    }

    public void setFourDx(boolean fourDx) {
        this.fourDx = fourDx;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isImax() {
        return imax;
    }

    public void setImax(boolean imax) {
        this.imax = imax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return
                "ResultsItem{" +
                        "datetime = '" + datetime + '\'' +
                        ",original_language = '" + originalLanguage + '\'' +
                        ",three_d = '" + threeD + '\'' +
                        ",four_dx = '" + fourDx + '\'' +
                        ",price = '" + price + '\'' +
                        ",imax = '" + imax + '\'' +
                        ",id = '" + id + '\'' +
                        ",place = '" + place + '\'' +
                        "}";
    }
}
