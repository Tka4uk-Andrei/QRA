package com.example.qra.model.qrCode;

public class QrData {

    private final static String FISCAL_NUMBER_SEQENCE = "fn=";
    private final static String FISCAL_DOCUMENT_SEQENCE = "i=";
    private final static String FISCAL_SIGN_SEQENCE = "fp=";

    private String fiscalNumber;
    private String fiscalDocument;
    private String fiscalSignOfDocument;
    private String totalCheckSum;
    private String typeOfFiscalDocument;

    /**
     * QrData constructor
     * @param rawData String that recognized from QR code. Example "t=20190124T1744&s=410.90&fn=9251440300003811&i=8947&fp=3163913062&n=1"
     */
    public QrData(String rawData){
        fiscalNumber = getSubString(rawData, rawData.indexOf(FISCAL_NUMBER_SEQENCE) + FISCAL_NUMBER_SEQENCE.length());
        fiscalDocument = getSubString(rawData, rawData.indexOf(FISCAL_DOCUMENT_SEQENCE) + FISCAL_DOCUMENT_SEQENCE.length());
        fiscalSignOfDocument = getSubString(rawData, rawData.indexOf(FISCAL_SIGN_SEQENCE) + FISCAL_SIGN_SEQENCE.length());
    }

    /**
     * Method that returns substring from "charPos" to first after it symbol '&'
     * @param str string for getting substring
     * @param charPos starting position
     * @return substring from "charPos" to first after it symbol '&'
     */
    private String getSubString(String str, int charPos){
        StringBuilder ans = new StringBuilder();

        while (str.length() > charPos && str.charAt(charPos) != '&'){
            ans.append(str.charAt(charPos));
            charPos++;
        }

        return ans.toString();
    }

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

    public String getTotalCheckSum() {
        return totalCheckSum;
    }

    public void setTotalCheckSum(String totalCheckSum) {
        this.totalCheckSum = totalCheckSum;
    }

    public String getTypeOfFiscalDocument() {
        return typeOfFiscalDocument;
    }

    public void setTypeOfFiscalDocument(String typeOfFiscalDocument) {
        this.typeOfFiscalDocument = typeOfFiscalDocument;
    }
}
