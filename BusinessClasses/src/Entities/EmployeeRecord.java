/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package Entities;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class EmployeeRecord {

    private int EMP_ID;
    private String FNAME;
    private String LNAME;
    private String ADDRESS;
    private String CITY;
    private String STATE;
    private int ZCODE;
    private String PHONE_N;
    private String REG_DATE;
    private byte[] EMP_IMG;
    private String img;

    public EmployeeRecord(int EMP_ID, String FNAME, String LNAME, String ADDRESS, String CITY,
            String STATE, int ZCODE, String PHONE_N, String REG_DATE, byte[] EMP_IMG) {
        this.EMP_ID = EMP_ID;
        this.FNAME = FNAME;
        this.LNAME = LNAME;
        this.ADDRESS = ADDRESS;
        this.CITY = CITY;
        this.STATE = STATE;
        this.ZCODE = ZCODE;
        this.PHONE_N = PHONE_N;
        this.REG_DATE = REG_DATE;
        this.EMP_IMG = EMP_IMG;
    }

    public EmployeeRecord() {
        this.EMP_ID = 0;
        this.FNAME = "";
        this.LNAME = "";
        this.ADDRESS = "";
        this.CITY = "";
        this.STATE = "";
        this.ZCODE = 0;
        this.PHONE_N = "";
        this.REG_DATE = "";
        this.EMP_IMG = null;
        this.img = "";

    }

    public int getEMP_ID() {
        return EMP_ID;
    }

    public void setEMP_ID(int EMP_ID) {
        this.EMP_ID = EMP_ID;
    }

    public String getFNAME() {
        return FNAME;
    }

    public void setFNAME(String FNAME) {
        this.FNAME = FNAME;
    }

    public String getLNAME() {
        return LNAME;
    }

    public void setLNAME(String LNAME) {
        this.LNAME = LNAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public int getZCODE() {
        return ZCODE;
    }

    public void setZCODE(int ZCODE) {
        this.ZCODE = ZCODE;
    }

    public String getPHONE_N() {
        return PHONE_N;
    }

    public void setPHONE_N(String PHONE_N) {
        this.PHONE_N = PHONE_N;
    }

    public String getREG_DATE() {
        return REG_DATE;
    }

    public void setREG_DATE(String REG_DATE) {
        this.REG_DATE = REG_DATE;
    }

    @SuppressWarnings("ReturnOfCollectionOrArrayField")
    public byte[] getEMP_IMG() {
        return EMP_IMG;
    }

    @SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
    public void setEMP_IMG(byte[] EMP_IMG) {
        this.EMP_IMG = EMP_IMG;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCurrentTimeStamp() {

        java.util.Date today = new java.util.Date();
        Date date = new java.sql.Date(today.getTime());
        String S = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return S;

    }

    public Integer sum(Integer num1, Integer num2) {
        Integer sum = num1 + num2;
        return sum;
    }

}
