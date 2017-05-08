package com.ifyou.nowincinema.model.dto.movieshow;

public class ImagesItem {
    private String image;
    private Source source;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return
                "ImagesItem{" +
                        "image = '" + image + '\'' +
                        ",source = '" + source + '\'' +
                        "}";
    }
}
