/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package Entities;

public class BillsRecord extends EmployeeRecord {

    private String DATE;
    private double MONEY_SENT;
    private double RENT;
    private double PHONE;
    private double GAS;
    private double ELECTRICITY;
    private double INTERNET;
    private double OTHER;

    public BillsRecord(String DATE, double MONEY_SENT, double RENT, double PHONE,
            double GAS, double ELECTRICITY, double INTERNET, double OTHER) {
        this.DATE = DATE;
        this.MONEY_SENT = MONEY_SENT;
        this.RENT = RENT;
        this.PHONE = PHONE;
        this.GAS = GAS;
        this.ELECTRICITY = ELECTRICITY;
        this.INTERNET = INTERNET;
        this.OTHER = OTHER;
    }

    public BillsRecord() {
        super();
        this.DATE = "";
        this.MONEY_SENT = 0;
        this.RENT = 0;
        this.PHONE = 0;
        this.GAS = 0;
        this.ELECTRICITY = 0;
        this.INTERNET = 0;
        this.OTHER = 0;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public double getMONEY_SENT() {
        return MONEY_SENT;
    }

    public void setMONEY_SENT(double MONEY_SENT) {
        this.MONEY_SENT = MONEY_SENT;
    }

    public double getRENT() {
        return RENT;
    }

    public void setRENT(double RENT) {
        this.RENT = RENT;
    }

    public double getPHONE() {
        return PHONE;
    }

    public void setPHONE(double PHONE) {
        this.PHONE = PHONE;
    }

    public double getGAS() {
        return GAS;

    }

    public void setGAS(double GAS) {
        this.GAS = GAS;

    }

    public double getELECTRICITY() {
        return ELECTRICITY;
    }

    public void setELECTRICITY(double ELECTRICITY) {
        this.ELECTRICITY = ELECTRICITY;
    }

    public double getINTERNET() {
        return INTERNET;
    }

    public void setINTERNET(double INTERNET) {
        this.INTERNET = INTERNET;
    }

    public double getOTHER() {
        return OTHER;
    }

    public void setOTHER(double OTHER) {
        this.OTHER = OTHER;
    }

}
