package com.example.qra.data;

public class QrData {
    private String fiscalNumber;
    private String fiscalDocument;
    private String fiscalSignOfDocument;

    public QrData(String fiscalNum, String fiscalDoc, String fiscalSignOfDoc){
        fiscalNumber = fiscalNum;
        fiscalDocument = fiscalDoc;
        fiscalSignOfDocument = fiscalSignOfDoc;
    }
    public String getFiscalNumber(){
        return fiscalNumber;
    }

    public String getFiscalDocument(){
        return fiscalDocument;
    }

    public String getFiscalSignOfDocument(){
        return fiscalSignOfDocument;
    }
}
