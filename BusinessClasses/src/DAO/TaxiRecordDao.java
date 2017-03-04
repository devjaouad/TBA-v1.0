/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package DAO;

import Driver.DBConnection;
import Entities.EmployeeRecord;
import Entities.TaxiRecord;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TaxiRecordDao extends EmployeeRecord {

    public int insertRecordIntoTaxi(TaxiRecord taxi) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String insertTableSQL = "INSERT INTO TAXI_REC"
                + "(DATE, EMP_ID, OWNER_ID, WEEK, MEDALLION, CC_INCOME, CASH_INCOME,"
                + " CASHTRIPS, CCTRIPS, EZPASS, LEASEFEE, CHECKS, MILESTOTAL, CASHTIPS,"
                + " GAS, ST_SURCHARGE, IMP_SURCHARGE) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(insertTableSQL);

            ps.setString(1, taxi.getDATE());
            ps.setInt(2, taxi.getEMP_ID());
            ps.setInt(3, taxi.getOWNER_ID());
            ps.setInt(4, taxi.getWEEK());
            ps.setString(5, taxi.getMEDALLION());
            ps.setDouble(6, taxi.getCC_INCOME());
            ps.setDouble(7, taxi.getCASH_INCOME());
            ps.setInt(8, taxi.getCASHTRIPS());
            ps.setInt(9, taxi.getCCTRIPS());
            ps.setDouble(10, taxi.getEZPASS());
            ps.setDouble(11, taxi.getLEASEFEE());
            ps.setDouble(12, taxi.getCHECKS());
            ps.setDouble(13, taxi.getMILESTOTAL());
            ps.setDouble(14, taxi.getCASHTIPS());
            ps.setDouble(15, taxi.getGAS());
            ps.setDouble(16, taxi.getST_SURCHARGE());
            ps.setDouble(17, taxi.getIMP_SURCHARGE());

            // execute insert SQL stetement
            affected = ps.executeUpdate();

        } catch (SQLException e) {

            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

            if (ps != null) {
                ps.close();
            }

            if (connect != null) {
                connect.close();

            }
        }
        return affected;

    }

    public int updateTaxi(TaxiRecord taxi, String tdate) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String insertTableSQL = "UPDATE TAXI_REC SET DATE = ?, EMP_ID = ? , OWNER_ID = ?, WEEK = ?,"
                + " MEDALLION = ?, CC_INCOME = ?, CASH_INCOME = ?, CASHTRIPS = ?, CCTRIPS = ?, EZPASS = ?,"
                + " LEASEFEE = ?, CHECKS = ?, MILESTOTAL = ?, CASHTIPS = ?, GAS = ?, ST_SURCHARGE = ?, IMP_SURCHARGE = ?"
                + " WHERE DATE = ? ";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(insertTableSQL);

            ps.setString(1, taxi.getDATE());
            ps.setInt(2, taxi.getEMP_ID());
            ps.setInt(3, taxi.getOWNER_ID());
            ps.setInt(4, taxi.getWEEK());
            ps.setString(5, taxi.getMEDALLION());
            ps.setDouble(6, taxi.getCC_INCOME());
            ps.setDouble(7, taxi.getCASH_INCOME());
            ps.setInt(8, taxi.getCASHTRIPS());
            ps.setInt(9, taxi.getCCTRIPS());
            ps.setDouble(10, taxi.getEZPASS());
            ps.setDouble(11, taxi.getLEASEFEE());
            ps.setDouble(12, taxi.getCHECKS());
            ps.setDouble(13, taxi.getMILESTOTAL());
            ps.setDouble(14, taxi.getCASHTIPS());
            ps.setDouble(15, taxi.getGAS());
            ps.setDouble(16, taxi.getST_SURCHARGE());
            ps.setDouble(17, taxi.getIMP_SURCHARGE());
            ps.setString(18, tdate);

            // execute update SQL stetement
            affected = ps.executeUpdate();

        } catch (SQLException e) {

            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

            if (ps != null) {
                ps.close();
            }

            if (connect != null) {
                connect.close();

            }
        }
        return affected;

    }

    public ArrayList<TaxiRecord> getAllRecords() throws SQLException {

        String sql = "SELECT T.DATE,T.EMP_ID, E.FNAME, E.LNAME, T.OWNER_ID, T.MEDALLION,"
                + " T.CC_INCOME, T.CASH_INCOME, T.CASHTRIPS, T.CCTRIPS, T.EZPASS,"
                + " T.LEASEFEE, T.CHECKS, T.MILESTOTAL, T.CASHTIPS, T.GAS, T.ST_SURCHARGE,"
                + " T.IMP_SURCHARGE FROM EMPLOYEE E INNER JOIN TAXI_REC T ON E.Emp_ID = T.Emp_ID"
                + " ORDER BY T.DATE";
        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        Statement stm = null;
        ArrayList<TaxiRecord> records = new ArrayList<>();

        try {
            stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {

                TaxiRecord taxirec = new TaxiRecord();

                taxirec.setDATE(rs.getString("DATE"));
                taxirec.setEMP_ID(rs.getInt("EMP_ID"));
                taxirec.setOWNER_ID(rs.getInt("OWNER_ID"));
                taxirec.setFNAME(rs.getString("FNAME"));
                taxirec.setLNAME(rs.getString("LNAME"));
                taxirec.setMEDALLION(rs.getString("MEDALLION"));
                taxirec.setCC_INCOME(rs.getDouble("CC_INCOME"));
                taxirec.setCASH_INCOME(rs.getDouble("CASH_INCOME"));
                taxirec.setCASHTRIPS(rs.getInt("CASHTRIPS"));
                taxirec.setCCTRIPS(rs.getInt("CCTRIPS"));
                taxirec.setEZPASS(rs.getDouble("EZPASS"));
                taxirec.setLEASEFEE(rs.getDouble("LEASEFEE"));
                taxirec.setCHECKS(rs.getDouble("CHECKS"));
                taxirec.setMILESTOTAL(rs.getDouble("MILESTOTAL"));
                taxirec.setCASHTIPS(rs.getDouble("CASHTIPS"));
                taxirec.setGAS(rs.getDouble("GAS"));
                taxirec.setST_SURCHARGE(rs.getDouble("ST_SURCHARGE"));
                taxirec.setIMP_SURCHARGE(rs.getDouble("IMP_SURCHARGE"));

                records.add(taxirec);

            }

        } catch (SQLException e) {
            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

            if (stm != null) {
                stm.close();
            }

            if (connect != null) {
                connect.close();
            }

        }

        return records;

    }

    public ArrayList<TaxiRecord> getAllRecordsByInput(int EmpCode, String Fname, String Lname, String Medallion) throws SQLException {

        String sql = "SELECT T.DATE,T.EMP_ID, E.FNAME, E.LNAME, T.OWNER_ID, T.MEDALLION,"
                + " T.CC_INCOME, T.CASH_INCOME, T.CASHTRIPS, T.CCTRIPS, T.EZPASS,"
                + " T.LEASEFEE, T.CHECKS, T.MILESTOTAL, T.CASHTIPS, T.GAS, T.ST_SURCHARGE, T.IMP_SURCHARGE"
                + " FROM Employee E INNER JOIN Taxi_REC T ON E.Emp_ID = T.Emp_ID"
                + " WHERE T.EMP_ID = ? "
                + " OR E.FNAME = ? "
                + " OR E.LNAME = ? "
                + " OR T.MEDALLION = ? "
                + " ORDER BY T.DATE";
        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = null;
        ArrayList<TaxiRecord> records = new ArrayList<>();

        try {
            ps = connect.prepareStatement(sql);
            ps.setInt(1, EmpCode);
            ps.setString(2, Fname);
            ps.setString(3, Lname);
            ps.setString(4, Medallion);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TaxiRecord taxirec = new TaxiRecord();

                taxirec.setDATE(rs.getString("DATE"));
                taxirec.setEMP_ID(rs.getInt("EMP_ID"));
                taxirec.setOWNER_ID(rs.getInt("OWNER_ID"));
                taxirec.setFNAME(rs.getString("FNAME"));
                taxirec.setLNAME(rs.getString("LNAME"));
                taxirec.setMEDALLION(rs.getString("MEDALLION"));
                taxirec.setCC_INCOME(rs.getDouble("CC_INCOME"));
                taxirec.setCASH_INCOME(rs.getDouble("CASH_INCOME"));
                taxirec.setCASHTRIPS(rs.getInt("CASHTRIPS"));
                taxirec.setCCTRIPS(rs.getInt("CCTRIPS"));
                taxirec.setEZPASS(rs.getDouble("EZPASS"));
                taxirec.setLEASEFEE(rs.getDouble("LEASEFEE"));
                taxirec.setCHECKS(rs.getDouble("CHECKS"));
                taxirec.setMILESTOTAL(rs.getDouble("MILESTOTAL"));
                taxirec.setCASHTIPS(rs.getDouble("CASHTIPS"));
                taxirec.setGAS(rs.getDouble("GAS"));
                taxirec.setST_SURCHARGE(rs.getDouble("ST_SURCHARGE"));
                taxirec.setIMP_SURCHARGE(rs.getDouble("IMP_SURCHARGE"));

                records.add(taxirec);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (connect != null) {
                connect.close();
            }
        }

        return records;

    }

    //overloaded method
    public ArrayList<TaxiRecord> getAllRecordsByInput(int EmpCode, String Fname, String Lname) throws SQLException {

        String sql = "SELECT T.Week, E.FNAME, E.LNAME, E.EMP_ID, Sum(CashTrips + CCTrips) AS Total_Trips,"
                + " Sum(T.CC_INCOME) AS CC_Income, Sum(T.CASH_INCOME) AS Cash_Income, Sum(T.EZPASS) AS EzPass,"
                + " Sum(ST_SURCHARGE + IMP_SURCHARGE) AS Tax_Total, Sum(T.LEASEFEE) AS Lease_Fee, Sum(T.CHECKS) AS Checks,"
                + " Sum(T.MILESTOTAL) AS Total_Miles, Sum(T.GAS) AS GASE, Sum(T.CASHTIPS) AS CASHTIPS,"
                + " Sum(CC_INCOME + CASH_INCOME - ST_SURCHARGE - IMP_SURCHARGE - EZPASS - LEASEFEE - T.GAS + T.CASHTIPS) AS Net_Income"
                + " FROM EMPLOYEE E INNER JOIN Taxi_REC T ON E.Emp_ID = T.Emp_ID "
                + " WHERE E.EMP_ID = ? "
                + " OR E.FNAME = ? "
                + " OR E.LNAME = ? "
                + " GROUP BY T.Week, E.Fname, E.Lname, E.Emp_ID "
                + " ORDER BY T.Week";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = null;
        ArrayList<TaxiRecord> records = new ArrayList<>();

        try {
            ps = connect.prepareStatement(sql);
            ps.setInt(1, EmpCode);
            ps.setString(2, Fname);
            ps.setString(3, Lname);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TaxiRecord taxirec = new TaxiRecord();

                taxirec.setWEEK(rs.getInt("WEEK"));
                taxirec.setFNAME(rs.getString("FNAME"));
                taxirec.setLNAME(rs.getString("LNAME"));
                taxirec.setEMP_ID(rs.getInt("EMP_ID"));
                taxirec.setCC_INCOME(rs.getDouble("CC_Income"));
                taxirec.setCASH_INCOME(rs.getDouble("Cash_Income"));
                taxirec.setTripsTotal(rs.getInt("Total_Trips"));
                taxirec.setEZPASS(rs.getDouble("EzPass"));
                taxirec.setLEASEFEE(rs.getDouble("Lease_Fee"));
                taxirec.setCHECKS(rs.getDouble("Checks"));
                taxirec.setMILESTOTAL(rs.getDouble("Total_Miles"));
                taxirec.setCASHTIPS(rs.getDouble("CASHTIPS"));
                taxirec.setGAS(rs.getDouble("GASE"));
                taxirec.setTaxTotal(rs.getDouble("Tax_Total"));
                taxirec.setNet_Income(rs.getDouble("Net_Income"));

                records.add(taxirec);

            }

        } catch (SQLException e) {
            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (connect != null) {
                connect.close();
            }

            if (ps != null) {
                ps.close();
            }

        }

        return records;

    }

    public ArrayList<TaxiRecord> getAllRecordsByDate(String sDate, String eDate, int OwnerID) throws SQLException {

        String sql = "SELECT T.DATE,T.EMP_ID, E.FNAME, E.LNAME, T.OWNER_ID, T.MEDALLION,"
                + " T.CC_INCOME, T.CASH_INCOME, T.CASHTRIPS, T.CCTRIPS, T.EZPASS,"
                + " T.LEASEFEE, T.CHECKS, T.MILESTOTAL, T.CASHTIPS, T.GAS, T.ST_SURCHARGE, T.IMP_SURCHARGE"
                + " FROM Employee E INNER JOIN Taxi_REC T ON E.Emp_ID = T.Emp_ID"
                + " WHERE T.DATE BETWEEN ? AND ?"
                + " AND T.OWNER_ID = ? "
                + " ORDER BY T.DATE";
        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = null;
        ArrayList<TaxiRecord> records = new ArrayList<>();

        try {
            ps = connect.prepareStatement(sql);

            ps.setString(1, sDate);
            ps.setString(2, eDate);
            ps.setInt(3, OwnerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TaxiRecord taxirec = new TaxiRecord();

                taxirec.setDATE(rs.getString("DATE"));
                taxirec.setEMP_ID(rs.getInt("EMP_ID"));
                taxirec.setOWNER_ID(rs.getInt("OWNER_ID"));
                taxirec.setFNAME(rs.getString("FNAME"));
                taxirec.setLNAME(rs.getString("LNAME"));
                taxirec.setMEDALLION(rs.getString("MEDALLION"));
                taxirec.setCC_INCOME(rs.getDouble("CC_INCOME"));
                taxirec.setCASH_INCOME(rs.getDouble("CASH_INCOME"));
                taxirec.setCASHTRIPS(rs.getInt("CASHTRIPS"));
                taxirec.setCCTRIPS(rs.getInt("CCTRIPS"));
                taxirec.setEZPASS(rs.getDouble("EZPASS"));
                taxirec.setLEASEFEE(rs.getDouble("LEASEFEE"));
                taxirec.setCHECKS(rs.getDouble("CHECKS"));
                taxirec.setMILESTOTAL(rs.getDouble("MILESTOTAL"));
                taxirec.setCASHTIPS(rs.getDouble("CASHTIPS"));
                taxirec.setGAS(rs.getDouble("GAS"));
                taxirec.setST_SURCHARGE(rs.getDouble("ST_SURCHARGE"));
                taxirec.setIMP_SURCHARGE(rs.getDouble("IMP_SURCHARGE"));

                records.add(taxirec);

            }

        } catch (SQLException e) {
            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (ps != null) {
                ps.close();
            }

            if (connect != null) {
                connect.close();
            }
        }

        return records;

    }

    public ArrayList<TaxiRecord> getAllRecordsSum() throws SQLException {

        String sql = "SELECT T.Week, E.Fname, E.Lname, E.Emp_ID, Sum(CashTrips + CCTrips) AS Total_Trips,"
                + " Sum(T.CC_INCOME) AS CC_Income, Sum(T.CASH_INCOME) AS Cash_Income, Sum(T.EZPASS) AS EzPass,"
                + " Sum(ST_SURCHARGE + IMP_SURCHARGE) AS Tax_Total, Sum(T.LEASEFEE) AS Lease_Fee, Sum(T.CHECKS) AS Checks,"
                + " Sum(T.MILESTOTAL) AS Total_Miles, Sum(T.GAS) AS GASE, Sum(T.CASHTIPS) AS CASHTIPS,"
                + "Sum(CC_INCOME + CASH_INCOME - ST_SURCHARGE- IMP_SURCHARGE - EZPASS - LEASEFEE - T.GAS + T.CASHTIPS) AS Net_Income"
                + " FROM EMPLOYEE E INNER JOIN Taxi_REC T ON E.Emp_ID = T.Emp_ID "
                + " GROUP BY T.Week, E.Fname, E.Lname, E.Emp_ID "
                + " ORDER BY T.Week";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        Statement stm = null;
        ArrayList<TaxiRecord> records = new ArrayList<>();

        try {
            stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {

                TaxiRecord taxirec = new TaxiRecord();

                taxirec.setWEEK(rs.getInt("WEEK"));
                taxirec.setFNAME(rs.getString("FNAME"));
                taxirec.setLNAME(rs.getString("LNAME"));
                taxirec.setEMP_ID(rs.getInt("EMP_ID"));
                taxirec.setCC_INCOME(rs.getDouble("CC_Income"));
                taxirec.setCASH_INCOME(rs.getDouble("Cash_Income"));
                taxirec.setTripsTotal(rs.getInt("Total_Trips"));
                taxirec.setEZPASS(rs.getDouble("EzPass"));
                taxirec.setLEASEFEE(rs.getDouble("Lease_Fee"));
                taxirec.setCHECKS(rs.getDouble("Checks"));
                taxirec.setMILESTOTAL(rs.getDouble("Total_Miles"));
                taxirec.setCASHTIPS(rs.getDouble("CASHTIPS"));
                taxirec.setGAS(rs.getDouble("GASE"));
                taxirec.setTaxTotal(rs.getDouble("Tax_Total"));
                taxirec.setNet_Income(rs.getDouble("Net_Income"));

                records.add(taxirec);

            }

        } catch (SQLException e) {
            System.out.println("" + e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            if (connect != null) {
                connect.close();
            }

            if (stm != null) {
                stm.close();
            }

        }

        return records;

    }

    public TaxiRecord search(String DATE) throws Exception {

        TaxiRecord taxi = new TaxiRecord();

        String sql = "SELECT DATE AS DT, EMP_ID, OWNER_ID, WEEK, MEDALLION, CC_INCOME, CASH_INCOME,"
                + " CASHTRIPS, CCTRIPS, EZPASS, LEASEFEE, CHECKS, MILESTOTAL, CASHTIPS, GAS,"
                + " ST_SURCHARGE, IMP_SURCHARGE FROM TAXI_REC WHERE DATE = ? ";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setString(1, DATE);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                taxi.setDATE(rs.getString("DT"));
                taxi.setEMP_ID(rs.getInt("EMP_ID"));
                taxi.setOWNER_ID(rs.getInt("OWNER_ID"));
                taxi.setWEEK(rs.getInt("WEEK"));
                taxi.setMEDALLION(rs.getString("MEDALLION"));
                taxi.setCC_INCOME(rs.getDouble("CC_INCOME"));
                taxi.setCASH_INCOME(rs.getDouble("CASH_INCOME"));
                taxi.setCASHTRIPS(rs.getInt("CASHTRIPS"));
                taxi.setCCTRIPS(rs.getInt("CCTRIPS"));
                taxi.setEZPASS(rs.getDouble("EZPASS"));
                taxi.setLEASEFEE(rs.getDouble("LEASEFEE"));
                taxi.setCHECKS(rs.getDouble("CHECKS"));
                taxi.setMILESTOTAL(rs.getDouble("MILESTOTAL"));
                taxi.setCASHTIPS(rs.getDouble("CASHTIPS"));
                taxi.setGAS(rs.getDouble("GAS"));
                taxi.setST_SURCHARGE(rs.getDouble("ST_SURCHARGE"));
                taxi.setIMP_SURCHARGE(rs.getDouble("IMP_SURCHARGE"));

                return taxi;

            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            connect.close();
            ps.close();
        }

        return null;
    }

    public TaxiRecord delete(String tdate) throws Exception {

        TaxiRecord taxi = new TaxiRecord();

        String sql = "DELETE FROM TAXI_REC WHERE DATE = ?";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setString(1, tdate);

            ps.execute();

        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

            connect.close();
            ps.close();
        }

        return taxi;
    }

    public TaxiRecord GrandTotal(int EmpCode, String Year) throws Exception {

        TaxiRecord rec = new TaxiRecord();

        String sql = "SELECT E.Fname, E.Lname, T.Emp_ID,"
                + " SUM(T.CC_INCOME + T.CASH_INCOME + T.CASHTIPS - T.ST_SURCHARGE- T.IMP_SURCHARGE - T.EZPASS - T.LEASEFEE - T.GAS) AS YEAR_NET_INCOME, TT.T_TOTAL"
                + " FROM TAXI_TOTAL TT INNER JOIN  TAXI_REC T"
                + " ON T.EMP_ID = TT.EMP_ID INNER JOIN EMPLOYEE E"
                + " ON E.EMP_ID = T.EMP_ID"
                + " WHERE E.EMP_ID = ? AND YEAR(T.DATE) = ?"
                + " GROUP BY E.Fname, E.Lname, T.Emp_ID, TT.T_TOTAL";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setInt(1, EmpCode);
            ps.setString(2, Year);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                rec.setFNAME(rs.getString("Fname"));
                rec.setLNAME(rs.getString("Lname"));
                rec.setYearTotal(rs.getDouble("YEAR_NET_INCOME"));
                rec.setGrandTotal(rs.getDouble("T_TOTAL"));

                return rec;

            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

            connect.close();
            ps.close();

        }

        return null;
    }

    public TaxiRecord BankAccount(int EmpCode) throws Exception {

        TaxiRecord rec = new TaxiRecord();

        String sql = "SELECT T.EMP_ID,E.FNAME, E.LNAME, T.T_TOTAL, B.B_TOTAL "
                + " FROM TAXI_TOTAL T INNER JOIN  BILLS_TOTAL B"
                + " ON T.EMP_ID = B.EMP_ID INNER JOIN EMPLOYEE E"
                + " ON E.EMP_ID = T.EMP_ID "
                + " WHERE T.EMP_ID = ? "
                + " GROUP BY T.EMP_ID,E.FNAME, E.LNAME,T.T_TOTAL, B.B_TOTAL ";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setInt(1, EmpCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                rec.setFNAME(rs.getString("FNAME"));
                rec.setLNAME(rs.getString("LNAME"));
                rec.setNet_Income(rs.getDouble("T_TOTAL"));
                rec.setBillsTotal(rs.getDouble("B_TOTAL"));

                return rec;

            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

            connect.close();
            ps.close();

        }

        return null;
    }

}
