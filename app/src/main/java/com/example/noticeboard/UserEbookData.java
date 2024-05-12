package com.example.noticeboard;

public class UserEbookData {
    private String pdfTitle,PdfUrl;

    public UserEbookData(String pdfTitle, String pdfUrl) {
        this.pdfTitle = pdfTitle;
        this.PdfUrl = pdfUrl;
    }

    public UserEbookData() {

    }

    public String getPdfTitle() {
        return pdfTitle;
    }

    public void setPdfTitle(String pdfTitle) {
        this.pdfTitle = pdfTitle;
    }

    public String getPdfUrl() {
        return PdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        PdfUrl = pdfUrl;
    }


    /* public UserEbookData(String name, String PdfUrl) {
        this.name = name;
        this.PdfUrl = PdfUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdfName() {
        return PdfName;
    }

    public void setPdfName(String pdfName) {
        PdfName = pdfName;
    }*/
}
