package com.example.labourlaw;

public class LawDataSample {
    private String id;
    private String lawData;
    private String note;
    private boolean fav;

    public LawDataSample(String id, String lawData, String note, boolean fav) {
        this.id = id;
        this.lawData = lawData;
        this.note = note;
        this.fav = fav;
    }

    public String getId() {
        return id;
    }

    public String getLawData() {
        return lawData;
    }

    public String getNote() {
        return note;
    }

    public boolean isFav() {
        return fav;
    }
}
