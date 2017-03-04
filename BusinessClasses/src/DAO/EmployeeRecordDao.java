/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package DAO;

import Driver.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entities.EmployeeRecord;
import java.sql.ResultSet;

public class EmployeeRecordDao {

    EmployeeRecord regdate = new EmployeeRecord();

    public int insertRecordIntoEmployee(EmployeeRecord emp) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String insertTableSQL = "INSERT INTO EMPLOYEE"
                + "(EMP_ID, FNAME, LNAME, ADDRESS, CITY, STATE, ZCODE, PHONE_N, REG_DATE, EMP_IMG) VALUES"
                + "(?,?,?,?,?,?,?,?,?,?)";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(insertTableSQL);

            ps.setInt(1, emp.getEMP_ID());
            ps.setString(2, emp.getFNAME());
            ps.setString(3, emp.getLNAME());
            ps.setString(4, emp.getADDRESS());
            ps.setString(5, emp.getCITY());
            ps.setString(6, emp.getSTATE());
            ps.setInt(7, emp.getZCODE());
            ps.setString(8, emp.getPHONE_N());
            ps.setString(9, regdate.getCurrentTimeStamp());
            //ps.setString(10, emp.getImg());
            ps.setBytes(10, emp.getEMP_IMG());

            // execute insert SQL stetement
            affected = ps.executeUpdate();

        } catch (SQLException e) {

            System.out.println(e.toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

            if (ps != null && connect != null) {
                ps.close();
                connect.close();
            }

        }
        return affected;

    }

    public int UpdateEmployee(EmployeeRecord emp, int EmpCode) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String sql = "UPDATE EMPLOYEE SET FNAME = ? , LNAME = ? , ADDRESS = ? , CITY = ? , STATE = ? "
                + ", ZCODE = ? , PHONE_N = ?, EMP_IMG = ? WHERE EMP_ID = ?";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(sql);

            ps.setString(1, emp.getFNAME());
            ps.setString(2, emp.getLNAME());
            ps.setString(3, emp.getADDRESS());
            ps.setString(4, emp.getCITY());
            ps.setString(5, emp.getSTATE());
            ps.setInt(6, emp.getZCODE());
            ps.setString(7, emp.getPHONE_N());
            //ps.setString(8, emp.getImg());
            ps.setBytes(8, emp.getEMP_IMG());
            ps.setInt(9, EmpCode);

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

    public EmployeeRecord search(int EmpCode) throws Exception {

        EmployeeRecord emp = new EmployeeRecord();

        String sql = "SELECT FNAME, LNAME, ADDRESS, CITY, STATE, ZCODE, PHONE_N, REG_DATE, EMP_IMG "
                + " FROM EMPLOYEE WHERE EMP_ID = ?";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setInt(1, EmpCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                emp.setFNAME(rs.getString("FNAME"));
                emp.setLNAME(rs.getString("LNAME"));
                emp.setADDRESS(rs.getString("ADDRESS"));
                emp.setCITY(rs.getString("CITY"));
                emp.setSTATE(rs.getString("STATE"));
                emp.setZCODE(rs.getInt("ZCODE"));
                emp.setPHONE_N(rs.getString("PHONE_N"));
                emp.setREG_DATE(rs.getString("REG_DATE"));
                //emp.setImg(rs.getString("E_IMG"));
                emp.setEMP_IMG(rs.getBytes("EMP_IMG"));

                return emp;

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

    public boolean delete(int Empcode) throws Exception {

        String sql = "DELETE FROM EMPLOYEE WHERE EMP_ID = ?";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setInt(1, Empcode);
            ps.execute();

            return true;

        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {

            connect.close();
            ps.close();
        }

        return false;
    }
}
