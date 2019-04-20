package com.comic.Forms;

public class ExploreForm {
    String category;

    String sortBy;

    public ExploreForm(){
        this.category = "All";
        this.sortBy = "None";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
