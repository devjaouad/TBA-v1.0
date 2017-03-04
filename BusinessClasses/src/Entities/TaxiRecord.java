/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package Entities;

public class TaxiRecord extends EmployeeRecord {

    private String DATE;
    private int EMP_ID;
    private int OWNER_ID;
    private int WEEK;
    private String MEDALLION;
    private double CC_INCOME;
    private double CASH_INCOME;
    private int CASHTRIPS;
    private int CCTRIPS;
    private double EZPASS;
    private double ST_SURCHARGE;
    private double IMP_SURCHARGE;
    private double LEASEFEE;
    private double CHECKS;
    private double MILESTOTAL;
    private double CASHTIPS;
    private double GAS;
    private double Net_Income;
    private int TripsTotal;
    private double TaxTotal;
    private double GrandTotal;
    private double BankAccount;
    private double BillsTotal;
    private double YearTotal;

    

    public TaxiRecord(String DATE, int EMP_ID, int OWNER_ID, int WEEK, String MEDALLION,
            double CC_INCOME, double CASH_INCOME, int CASHTRIPS, int CCTRIPS, int RIPSTOTAL,
            double EZPASS, double ST_SURCHARGE, double EMP_SURCHARGE, double LEASEFEE, double CHECKS,
            double MILESTOTAL, double CASHTIPS, double GAS, double Net_Income, int TripsTotal,
            double TaxTotal, double GrandTotal, double BankAccount, double BillsTotal) {
        this.DATE = DATE;
        this.EMP_ID = EMP_ID;
        this.WEEK = WEEK;
        this.OWNER_ID = OWNER_ID;
        this.MEDALLION = MEDALLION;
        this.CC_INCOME = CC_INCOME;
        this.CASH_INCOME = CASH_INCOME;
        this.CASHTRIPS = CASHTRIPS;
        this.CCTRIPS = CCTRIPS;
        this.EZPASS = EZPASS;
        this.ST_SURCHARGE = ST_SURCHARGE;
        this.IMP_SURCHARGE = EMP_SURCHARGE;
        this.LEASEFEE = LEASEFEE;
        this.CHECKS = CHECKS;
        this.MILESTOTAL = MILESTOTAL;
        this.CASHTIPS = CASHTIPS;
        this.GAS = GAS;
        this.Net_Income = Net_Income;
        this.TripsTotal = TripsTotal;
        this.TaxTotal = TaxTotal;
        this.GrandTotal = GrandTotal;
        this.BankAccount = BankAccount;
        this.BillsTotal = BillsTotal;

    }

    public TaxiRecord() {
        super();
        this.DATE = "";
        this.EMP_ID = 0;
        this.OWNER_ID = 0;
        this.WEEK = 0;
        this.MEDALLION = "";
        this.CC_INCOME = 0;
        this.CASH_INCOME = 0;
        this.CASHTRIPS = 0;
        this.CCTRIPS = 0;
        this.EZPASS = 0;
        this.ST_SURCHARGE = 0;
        this.IMP_SURCHARGE = 0;
        this.LEASEFEE = 0;
        this.CHECKS = 0;
        this.MILESTOTAL = 0;
        this.CASHTIPS = 0;
        this.GAS = 0;
        this.Net_Income = 0;
        this.TripsTotal = 0;
        this.TaxTotal = 0;
        this.GrandTotal = 0;
        this.BankAccount = 0;

    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    @Override
    public int getEMP_ID() {
        return EMP_ID;
    }

    @Override
    public void setEMP_ID(int EMP_ID) {
        this.EMP_ID = EMP_ID;
    }

    public int getOWNER_ID() {
        return OWNER_ID;
    }

    public void setOWNER_ID(int OWNER_ID) {
        this.OWNER_ID = OWNER_ID;
    }

    public int getWEEK() {
        return WEEK;
    }

    public void setWEEK(int WEEK) {
        this.WEEK = WEEK;
    }

    public String getMEDALLION() {
        return MEDALLION;
    }

    public void setMEDALLION(String MEDALLION) {
        this.MEDALLION = MEDALLION;
    }

    public double getCC_INCOME() {
        return CC_INCOME;
    }

    public void setCC_INCOME(double CC_INCOME) {
        this.CC_INCOME = CC_INCOME;
    }

    public double getCASH_INCOME() {
        return CASH_INCOME;
    }

    public void setCASH_INCOME(double CASH_INCOME) {
        this.CASH_INCOME = CASH_INCOME;
    }

    public int getCASHTRIPS() {
        return CASHTRIPS;
    }

    public void setCASHTRIPS(int CASHTRIPS) {
        this.CASHTRIPS = CASHTRIPS;
    }

    public int getCCTRIPS() {
        return CCTRIPS;
    }

    public void setCCTRIPS(int CCTRIPS) {
        this.CCTRIPS = CCTRIPS;
    }

    public double getEZPASS() {
        return EZPASS;
    }

    public void setEZPASS(double EZPASS) {
        this.EZPASS = EZPASS;
    }

    public double getST_SURCHARGE() {
        return ST_SURCHARGE;
    }

    public void setST_SURCHARGE(double ST_SURCHARGE) {
        this.ST_SURCHARGE = ST_SURCHARGE;
    }

    public double getIMP_SURCHARGE() {
        return IMP_SURCHARGE;
    }

    public void setIMP_SURCHARGE(double IMP_SURCHARGE) {
        this.IMP_SURCHARGE = IMP_SURCHARGE;
    }

    public double getLEASEFEE() {
        return LEASEFEE;
    }

    public void setLEASEFEE(double LEASEFEE) {
        this.LEASEFEE = LEASEFEE;
    }

    public double getCHECKS() {
        return CHECKS;
    }

    public void setCHECKS(double CHECKS) {
        this.CHECKS = CHECKS;
    }

    public double getMILESTOTAL() {
        return MILESTOTAL;
    }

    public void setMILESTOTAL(double MILESTOTAL) {
        this.MILESTOTAL = MILESTOTAL;
    }

    public double getCASHTIPS() {
        return CASHTIPS;
    }

    public void setCASHTIPS(double CASHTIPS) {
        this.CASHTIPS = CASHTIPS;
    }

    public double getGAS() {
        return GAS;
    }

    public void setGAS(double GAS) {
        this.GAS = GAS;
    }

    public double getNet_Income() {
        return Net_Income;
    }

    public void setNet_Income(double Net_Income) {
        this.Net_Income = Net_Income;
    }

    public int getTripsTotal() {
        return TripsTotal;
    }

    public void setTripsTotal(int TripsTotal) {
        this.TripsTotal = TripsTotal;
    }

    public double getTaxTotal() {
        return TaxTotal;
    }

    public void setTaxTotal(double TaxTotal) {
        this.TaxTotal = TaxTotal;
    }

    public double getGrandTotal() {
        return GrandTotal;
    }

    public void setGrandTotal(double GrandTotal) {
        this.GrandTotal = GrandTotal;
    }

    public double getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(double BankAccount) {
        this.BankAccount = BankAccount;
    }

    public double getBillsTotal() {
        return BillsTotal;
    }

    public void setBillsTotal(double BillsTotal) {
        this.BillsTotal = BillsTotal;
    }
    public double getYearTotal() {
        return YearTotal;
    }

    public void setYearTotal(double YearTotal) {
        this.YearTotal = YearTotal;
    }
    

}
