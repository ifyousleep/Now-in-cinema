package com.ifyou.nowincinema.model.dto.details;

import java.util.List;

public class DetailsMovie {
    private String country;
    private String body_text;
    private int year;
    private int favoritesCount;
    private String description;
    private String language;
    private String imdbUrl;
    private String title;
    private String locale;
    private String trailer;
    private List<GenresItem> genres;
    private boolean disableComments;
    private int publicationDate;
    private int runningTime;
    private int id;
    private double imdb_rating;
    private boolean isEditorsChoice;
    private String slug;
    private String mpaaRating;
    private double budget;
    private List<ImagesItem> images;
    private String originalTitle;
    private String director;
    private String stars;
    private String url;
    private String siteUrl;
    private int commentsCount;
    private String awards;
    private String writer;
    private String budgetCurrency;
    private Object ageRestriction;
    private Poster poster;

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setBody_text(String body_text) {
        this.body_text = body_text;
    }

    public String getBody_text() {
        return body_text;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setFavoritesCount(int favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public int getFavoritesCount() {
        return favoritesCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setImdbUrl(String imdbUrl) {
        this.imdbUrl = imdbUrl;
    }

    public String getImdbUrl() {
        return imdbUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getLocale() {
        return locale;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setGenres(List<GenresItem> genres) {
        this.genres = genres;
    }

    public List<GenresItem> getGenres() {
        return genres;
    }

    public void setDisableComments(boolean disableComments) {
        this.disableComments = disableComments;
    }

    public boolean isDisableComments() {
        return disableComments;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setRunningTime(int runningTime) {
        this.runningTime = runningTime;
    }

    public int getRunningTime() {
        return runningTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setImdb_rating(double imdb_rating) {
        this.imdb_rating = imdb_rating;
    }

    public double getImdb_rating() {
        return imdb_rating;
    }

    public void setIsEditorsChoice(boolean isEditorsChoice) {
        this.isEditorsChoice = isEditorsChoice;
    }

    public boolean isIsEditorsChoice() {
        return isEditorsChoice;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    public String getMpaaRating() {
        return mpaaRating;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

    public void setImages(List<ImagesItem> images) {
        this.images = images;
    }

    public List<ImagesItem> getImages() {
        return images;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirector() {
        return director;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getStars() {
        return stars;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setAwards(String awards) {
        this.awards = awards;
    }

    public String getAwards() {
        return awards;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public void setBudgetCurrency(String budgetCurrency) {
        this.budgetCurrency = budgetCurrency;
    }

    public String getBudgetCurrency() {
        return budgetCurrency;
    }

    public void setAgeRestriction(Object ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    public Object getAgeRestriction() {
        return ageRestriction;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }

    public Poster getPoster() {
        return poster;
    }

    @Override
    public String toString() {
        return
                "DetailsMovie{" +
                        "country = '" + country + '\'' +
                        ",body_text = '" + body_text + '\'' +
                        ",year = '" + year + '\'' +
                        ",favorites_count = '" + favoritesCount + '\'' +
                        ",description = '" + description + '\'' +
                        ",language = '" + language + '\'' +
                        ",imdb_url = '" + imdbUrl + '\'' +
                        ",title = '" + title + '\'' +
                        ",locale = '" + locale + '\'' +
                        ",trailer = '" + trailer + '\'' +
                        ",genres = '" + genres + '\'' +
                        ",disable_comments = '" + disableComments + '\'' +
                        ",publication_date = '" + publicationDate + '\'' +
                        ",running_time = '" + runningTime + '\'' +
                        ",id = '" + id + '\'' +
                        ",imdb_rating = '" + imdb_rating + '\'' +
                        ",is_editors_choice = '" + isEditorsChoice + '\'' +
                        ",slug = '" + slug + '\'' +
                        ",mpaa_rating = '" + mpaaRating + '\'' +
                        ",budget = '" + budget + '\'' +
                        ",images = '" + images + '\'' +
                        ",original_title = '" + originalTitle + '\'' +
                        ",director = '" + director + '\'' +
                        ",stars = '" + stars + '\'' +
                        ",url = '" + url + '\'' +
                        ",site_url = '" + siteUrl + '\'' +
                        ",comments_count = '" + commentsCount + '\'' +
                        ",awards = '" + awards + '\'' +
                        ",writer = '" + writer + '\'' +
                        ",budget_currency = '" + budgetCurrency + '\'' +
                        ",age_restriction = '" + ageRestriction + '\'' +
                        ",poster = '" + poster + '\'' +
                        "}";
    }
}