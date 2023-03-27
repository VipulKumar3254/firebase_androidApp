package com.example.informatics;


public class firebasemodel {
    private String title;
    private String content;
    private String docId;


    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }



    public firebasemodel(){

    }



    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public firebasemodel(String title, String content) {
        this.title = title;
        this.content = content;
    }
}