package com.example.qra.data;

public class QrData {
    private String fiscalNumber;
    private String fiscalDocument;
    private String fiscalSignOfDocument;

    public String getFiscalNumber(){
        return fiscalNumber;
    }

    public String getFiscalDocument(){
        return fiscalDocument;
    }

    public String getFiscalSignOfDocument(){
        return fiscalSignOfDocument;
    }

    public void setFiscalNumber(String fiscalNum){
        fiscalNumber = fiscalNum;
    }

    public void setFiscalDocument(String fiscalDoc){
        fiscalDocument = fiscalDoc;
    }

    public void setFiscalSignOfDocument(String fiscalSignOfDoc){
        fiscalSignOfDocument = fiscalSignOfDoc;
    }
}
