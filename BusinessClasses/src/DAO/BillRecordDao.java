/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package DAO;

import Driver.DBConnection;
import Entities.BillsRecord;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BillRecordDao {

    public int insertRecordIntoBills(BillsRecord bill) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String insertTableSQL = "INSERT INTO Bills"
                + "(DATE, EMP_ID, MONEY_SENT, RENT, PHONE, GAS, ELECTRICITY, INTERNET, OTHER) VALUES"
                + "(?,?,?,?,?,?,?,?,?)";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(insertTableSQL);

            ps.setString(1, bill.getDATE());
            ps.setInt(2, bill.getEMP_ID());
            ps.setDouble(3, bill.getMONEY_SENT());
            ps.setDouble(4, bill.getRENT());
            ps.setDouble(5, bill.getPHONE());
            ps.setDouble(6, bill.getGAS());
            ps.setDouble(7, bill.getELECTRICITY());
            ps.setDouble(8, bill.getINTERNET());
            ps.setDouble(9, bill.getOTHER());

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

    public ArrayList<BillsRecord> getAllRecords() throws SQLException {

        String sql = "SELECT B.DATE, B.EMP_ID, E.FNAME, E.LNAME, MONEY_SENT,"
                + " RENT, PHONE, GAS, ELECTRICITY, INTERNET, OTHER"
                + " FROM EMPLOYEE E INNER JOIN BILLS B ON E.EMP_ID = B.EMP_ID"
                + " ORDER BY B.DATE";
        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        Statement stm = null;
        ArrayList<BillsRecord> records = new ArrayList<>();

        try {
            stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {

                BillsRecord billrec = new BillsRecord();

                billrec.setDATE(rs.getString("DATE"));
                billrec.setEMP_ID(rs.getInt("EMP_ID"));
                billrec.setFNAME(rs.getString("FNAME"));
                billrec.setLNAME(rs.getString("LNAME"));
                billrec.setMONEY_SENT(rs.getDouble("MONEY_SENT"));
                billrec.setRENT(rs.getDouble("RENT"));
                billrec.setPHONE(rs.getDouble("PHONE"));
                billrec.setGAS(rs.getDouble("GAS"));
                billrec.setELECTRICITY(rs.getDouble("ELECTRICITY"));
                billrec.setINTERNET(rs.getDouble("INTERNET"));
                billrec.setOTHER(rs.getDouble("OTHER"));

                records.add(billrec);
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

    public ArrayList<BillsRecord> getBillRecordByField(int EmpCode, String Fname, String Lname) throws SQLException {

        String sql = "SELECT B.DATE AS DT, B.EMP_ID, E.FNAME, E.LNAME, MONEY_SENT,"
                + " RENT, PHONE, GAS, ELECTRICITY, INTERNET, OTHER"
                + " FROM EMPLOYEE E INNER JOIN BILLS B ON E.EMP_ID = B.EMP_ID"
                + " WHERE B.EMP_ID = ? "
                + " OR E.FNAME = ? "
                + " OR E.LNAME = ? "
                + " ORDER BY B.DATE";
        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = null;

        ArrayList<BillsRecord> records = new ArrayList<>();

        try {

            ps = connect.prepareStatement(sql);

            ps.setInt(1, EmpCode);
            ps.setString(2, Fname);
            ps.setString(3, Lname);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BillsRecord billrec = new BillsRecord();

                billrec.setDATE(rs.getString("DT"));
                billrec.setEMP_ID(rs.getInt("EMP_ID"));
                billrec.setFNAME(rs.getString("FNAME"));
                billrec.setLNAME(rs.getString("LNAME"));
                billrec.setMONEY_SENT(rs.getDouble("MONEY_SENT"));
                billrec.setRENT(rs.getDouble("RENT"));
                billrec.setPHONE(rs.getDouble("PHONE"));
                billrec.setGAS(rs.getDouble("GAS"));
                billrec.setELECTRICITY(rs.getDouble("ELECTRICITY"));
                billrec.setINTERNET(rs.getDouble("INTERNET"));
                billrec.setOTHER(rs.getDouble("OTHER"));

                records.add(billrec);

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

    public ArrayList<BillsRecord> getBillRecordByDate(String sDate, String eDate) throws SQLException {

        String sql = "SELECT B.DATE AS DT, B.EMP_ID, E.FNAME, E.LNAME, MONEY_SENT,"
                + " RENT, PHONE, GAS, ELECTRICITY, INTERNET, OTHER"
                + " FROM EMPLOYEE E INNER JOIN BILLS B ON E.EMP_ID = B.EMP_ID"
                + " WHERE B.DATE BETWEEN ? AND ? "
                + " ORDER BY B.DATE";
        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = null;

        ArrayList<BillsRecord> records = new ArrayList<>();

        try {

            ps = connect.prepareStatement(sql);

            ps.setString(1, sDate);
            ps.setString(2, eDate);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                BillsRecord billrec = new BillsRecord();

                billrec.setDATE(rs.getString("DT"));
                billrec.setEMP_ID(rs.getInt("EMP_ID"));
                billrec.setFNAME(rs.getString("FNAME"));
                billrec.setLNAME(rs.getString("LNAME"));
                billrec.setMONEY_SENT(rs.getDouble("MONEY_SENT"));
                billrec.setRENT(rs.getDouble("RENT"));
                billrec.setPHONE(rs.getDouble("PHONE"));
                billrec.setGAS(rs.getDouble("GAS"));
                billrec.setELECTRICITY(rs.getDouble("ELECTRICITY"));
                billrec.setINTERNET(rs.getDouble("INTERNET"));
                billrec.setOTHER(rs.getDouble("OTHER"));

                records.add(billrec);

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

    public BillsRecord search(String DATE) throws Exception {

        BillsRecord bill = new BillsRecord();

        String sql = "SELECT DATE AS DT, EMP_ID, MONEY_SENT, RENT, PHONE,"
                + " GAS, ELECTRICITY, INTERNET, OTHER FROM BILLS WHERE DATE = ? ";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setString(1, DATE);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                bill.setDATE(rs.getString("DT"));
                bill.setEMP_ID(rs.getInt("EMP_ID"));
                bill.setMONEY_SENT(rs.getDouble("MONEY_SENT"));
                bill.setRENT(rs.getDouble("RENT"));
                bill.setPHONE(rs.getDouble("PHONE"));
                bill.setGAS(rs.getDouble("GAS"));
                bill.setELECTRICITY(rs.getDouble("ELECTRICITY"));
                bill.setINTERNET(rs.getDouble("INTERNET"));
                bill.setOTHER(rs.getDouble("OTHER"));

                return bill;

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

    public int updateBills(BillsRecord bill, String bdate) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String sql = "UPDATE BILLS SET DATE = ? , EMP_ID = ? , MONEY_SENT = ? ,"
                + " RENT = ? , PHONE = ?, GAS = ? ,"
                + " ELECTRICITY = ? , INTERNET = ? , OTHER = ? WHERE DATE = ? ";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(sql);

            ps.setString(1, bill.getDATE());
            ps.setInt(2, bill.getEMP_ID());
            ps.setDouble(3, bill.getMONEY_SENT());
            ps.setDouble(4, bill.getRENT());
            ps.setDouble(5, bill.getPHONE());
            ps.setDouble(6, bill.getGAS());
            ps.setDouble(7, bill.getELECTRICITY());
            ps.setDouble(8, bill.getINTERNET());
            ps.setDouble(9, bill.getOTHER());
            ps.setString(10, bdate);

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

    public BillsRecord delete(String bdate) throws Exception {

        BillsRecord bill = new BillsRecord();

        String sql = "DELETE FROM BILLS WHERE DATE = ?";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setString(1, bdate);

            ps.execute();

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                connect.close();
                ps.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        return bill;
    }
}
