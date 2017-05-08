package com.ifyou.nowincinema.model.dto.movieshow;

public class Source {
    private String link;
    private String name;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return
                "Source{" +
                        "link = '" + link + '\'' +
                        ",name = '" + name + '\'' +
                        "}";
    }
}
