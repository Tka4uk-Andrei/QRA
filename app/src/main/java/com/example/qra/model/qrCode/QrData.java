package com.example.qra.model.qrCode;

public class QrData {

    private final static String TIME_SEQUENCE = "t=";
    private final static String SUM_SEQUENCE = "s=";
    private final static String FISCAL_NUMBER_SEQUENCE = "fn=";
    private final static String FISCAL_DOCUMENT_SEQUENCE = "i=";
    private final static String FISCAL_SIGN_SEQUENCE = "fp=";
    private final static String FISCAL_TYPE_SEQUENCE = "&n=";

    private String fiscalNumber;
    private String fiscalDocument;
    private String fiscalSignOfDocument;
    private String totalCheckSum;
    private String typeOfFiscalDocument;
    private String buyTime;

    /**
     * Set time to required format
     * @param date time in some format
     * @return formatted date
     */
    private String changeFormatBuyTime(String date) {
        StringBuilder sb = new StringBuilder();
        sb.append(date.substring(0, 4)).append("-").append(date.substring(4, 6));
        sb.append("-").append(date.substring(6, 11)).append(":").append(date.substring(11, 13));
        if (date.length() == 13) {
            return sb.toString();
        } else {
            sb.append(":").append(date.substring(13));
        }
        return sb.toString();
    }

    /**
     * Set sum to required format
     * @param sum sum in rubbles
     * @return sum in kopeck
     */
    private String changeFormatTotalCheckSum(String sum) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (sum.charAt(i) != '.') {
            i++;
        }
        sb.append(sum.substring(0, i)).append(sum.substring(i + 1));
        return sb.toString();
    }

    /**
     * Method that returns substring from "charPos" to first after it symbol '&'
     *
     * @param str     string for getting substring
     * @param charPos starting position
     * @return substring from "charPos" to first after it symbol '&'
     */
    private String getSubString(String str, int charPos) {
        StringBuilder ans = new StringBuilder();

        while (str.length() > charPos && str.charAt(charPos) != '&' &&  str.charAt(charPos) != '\0') {
            ans.append(str.charAt(charPos));
            charPos++;
        }

        return ans.toString();
    }

    /**
     * QrData constructor
     *
     * @param rawData String that recognized from QR code.
     *                Example "t=20190124T1744&s=410.90&fn=9251440300003811&i=8947&fp=3163913062&n=1"
     */
    public QrData(String rawData) {
        fiscalNumber = getSubString(rawData, rawData.indexOf(FISCAL_NUMBER_SEQUENCE) + FISCAL_NUMBER_SEQUENCE.length());
        fiscalDocument = getSubString(rawData, rawData.indexOf(FISCAL_DOCUMENT_SEQUENCE) + FISCAL_DOCUMENT_SEQUENCE.length());
        fiscalSignOfDocument = getSubString(rawData, rawData.indexOf(FISCAL_SIGN_SEQUENCE) + FISCAL_SIGN_SEQUENCE.length());

        totalCheckSum = changeFormatTotalCheckSum(getSubString(rawData, rawData.indexOf(SUM_SEQUENCE) + SUM_SEQUENCE.length()));
        typeOfFiscalDocument = getSubString(rawData, rawData.indexOf(FISCAL_TYPE_SEQUENCE) + FISCAL_TYPE_SEQUENCE.length());
        buyTime = changeFormatBuyTime(getSubString(rawData, rawData.indexOf(TIME_SEQUENCE) + TIME_SEQUENCE.length()));
    }

    public QrData(String fiscalNum, String fiscalDoc, String fiscalSignOfDoc) {
        this.fiscalNumber = fiscalNum;
        this.fiscalDocument = fiscalDoc;
        this.fiscalSignOfDocument = fiscalSignOfDoc;

        this.typeOfFiscalDocument = null;
        this.totalCheckSum = null;
        this.buyTime = null;
    }

    public QrData(String fiscalNum, String fiscalDoc, String fiscalSignOfDoc,
                  String totalCheckSum, String typeOfFiscalDocument, String buyTime) {
        this.fiscalNumber = fiscalNum;
        this.fiscalDocument = fiscalDoc;
        this.fiscalSignOfDocument = fiscalSignOfDoc;
        this.typeOfFiscalDocument = typeOfFiscalDocument;
        this.totalCheckSum = totalCheckSum;
        this.buyTime = buyTime;
    }

    public String getFiscalNumber() {
        return fiscalNumber;
    }

    public String getFiscalDocument() {
        return fiscalDocument;
    }

    public String getFiscalSignOfDocument() {
        return fiscalSignOfDocument;
    }

    /**
     * Method returns total sum from check in format "ruble.kopeck"
     *
     * @return check sum if fails throw NullPointerException
     */
    public String getTotalCheckSum() {
        if (totalCheckSum == null)
            throw new NullPointerException("field totalCheckSum is null");
        return totalCheckSum;
    }

    /**
     * Method returns type of fiscal document.
     *
     * @return check sum if fails throw NullPointerException
     */
    public String getTypeOfFiscalDocument() {
        if (typeOfFiscalDocument == null)
            throw new NullPointerException("field typeOfFiscalDocument is null");
        return typeOfFiscalDocument;
    }

    /**
     * Method returns time then check get.
     *
     * @return check sum if fails throw NullPointerException
     */
    public String getBuyTime() {
        if (buyTime == null)
            throw new NullPointerException("field buyTime is null");
        return buyTime;
    }
}
