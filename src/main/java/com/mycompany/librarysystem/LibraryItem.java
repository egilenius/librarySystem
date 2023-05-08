package com.mycompany.librarysystem;

public class LibraryItem {
    private String authorDirector;
    private String isbn;
    private String classification;
    private int ageRestriction;
    private String productionCountry;
    private String actors;

    public LibraryItem(String authorDirector, String isbn, String classification, int ageRestriction,
                       String productionCountry, String actors) {
        this.authorDirector = authorDirector;
        this.isbn = isbn;
        this.classification = classification;
        this.ageRestriction = ageRestriction;
        this.productionCountry = productionCountry;
        this.actors = actors;
    }

    public String getAuthorDirector() {
        return authorDirector;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getClassification() {
        return classification;
    }

    public int getAgeRestriction() {
        return ageRestriction;
    }

    public String getProductionCountry() {
        return productionCountry;
    }

    public String getActors() {
        return actors;
    }
}
