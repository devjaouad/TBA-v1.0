/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package DAO;

import Driver.DBConnection;
import Entities.TicketRecord;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TicketRecordDao {

    @SuppressWarnings("null")
    public int insertRecordIntoTicket(TicketRecord ticket) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String insertTableSQL = "INSERT INTO TICKET"
                + "(TICKET_ID, EMP_ID, DATE, VIOLATION, COST) VALUES"
                + "(?,?,?,?,?)";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(insertTableSQL);

            ps.setString(1, ticket.getTICKET_ID());
            ps.setInt(2, ticket.getEMP_ID());
            ps.setString(3, ticket.getDATE());
            ps.setString(4, ticket.getVIOLATION());
            ps.setDouble(5, ticket.getCOST());

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

    public ArrayList<TicketRecord> getAllTicketsRecords() throws SQLException {

        String sql = "SELECT DATE, T.EMP_ID ,E.FNAME, E.LNAME ,TICKET_ID, VIOLATION, COST"
                + " FROM EMPLOYEE E INNER JOIN TICKET T ON T.EMP_ID = E.EMP_ID"
                + " ORDER BY DATE";
        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        Statement stm = null;
        ArrayList<TicketRecord> records = new ArrayList<>();

        try {
            stm = connect.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            while (rs.next()) {

                TicketRecord tickterec = new TicketRecord();

                tickterec.setDATE(rs.getString("DATE"));
                tickterec.setEMP_ID(rs.getInt("EMP_ID"));
                tickterec.setFNAME(rs.getString("FNAME"));
                tickterec.setLNAME(rs.getString("LNAME"));
                tickterec.setTICKET_ID(rs.getString("TICKET_ID"));
                tickterec.setVIOLATION(rs.getString("VIOLATION"));
                tickterec.setCOST(rs.getDouble("COST"));

                records.add(tickterec);

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

    public ArrayList<TicketRecord> getTicketByID(int EmpCode, String TicketCode) throws SQLException {

        String sql = "SELECT DATE, T.EMP_ID ,E.FNAME, E.LNAME ,TICKET_ID, VIOLATION, COST"
                + " FROM EMPLOYEE E INNER JOIN TICKET T ON E.EMP_ID = T.EMP_ID"
                + " WHERE T.EMP_ID = ? "
                + " OR T.TICKET_ID = ? "
                + " ORDER BY DATE";
        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();

        PreparedStatement ps = null;
        ArrayList<TicketRecord> records = new ArrayList<>();

        try {
            ps = connect.prepareStatement(sql);
            ps.setInt(1, EmpCode);
            ps.setString(2, TicketCode);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                TicketRecord tickterec = new TicketRecord();

                tickterec.setDATE(rs.getString("DATE"));
                tickterec.setEMP_ID(rs.getInt("EMP_ID"));
                tickterec.setFNAME(rs.getString("FNAME"));
                tickterec.setLNAME(rs.getString("LNAME"));
                tickterec.setTICKET_ID(rs.getString("TICKET_ID"));
                tickterec.setVIOLATION(rs.getString("VIOLATION"));
                tickterec.setCOST(rs.getDouble("COST"));

                records.add(tickterec);

            }

        } catch (SQLException e) {
            System.out.println("" + e.toString());

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

    public TicketRecord search(String TicketCode) throws Exception {

        TicketRecord tikt = new TicketRecord();

        String sql = "SELECT TICKET_ID, EMP_ID, DATE, VIOLATION, COST FROM TICKET WHERE  TICKET_ID = ? ";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setString(1, TicketCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tikt.setTICKET_ID(rs.getString("TICKET_ID"));
                tikt.setEMP_ID(rs.getInt("EMP_ID"));
                tikt.setDATE(rs.getString("DATE"));
                tikt.setVIOLATION(rs.getString("VIOLATION"));
                tikt.setCOST(rs.getDouble("COST"));

                return tikt;

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

    public int updateTicket(TicketRecord tikt, String TicketCode) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String sql = "UPDATE TICKET SET TICKET_ID = ? , EMP_ID = ? , DATE = ? , VIOLATION = ? , COST = ? WHERE TICKET_ID = ?";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(sql);

            ps.setString(1, tikt.getTICKET_ID());
            ps.setInt(2, tikt.getEMP_ID());
            ps.setString(3, tikt.getDATE());
            ps.setString(4, tikt.getVIOLATION());
            ps.setDouble(5, tikt.getCOST());
            ps.setString(6, TicketCode);

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

    public TicketRecord delete(String TicketCode) throws Exception {

        TicketRecord tikt = new TicketRecord();

        String sql = "DELETE FROM TICKET WHERE TICKET_ID = ?";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setString(1, TicketCode);

            ps.execute();

        } catch (SQLException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            connect.close();
            ps.close();

        }

        return tikt;
    }
}
