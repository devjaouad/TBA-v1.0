/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package DAO;

import Driver.DBConnection;
import Entities.OwnerRecord;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerRecordDao {

    OwnerRecord regdate = new OwnerRecord();

    public int insertRecordIntoOwner(OwnerRecord own) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String insertTableSQL = "INSERT INTO OWNER"
                + "(OWNER_ID, COMPANY, ADDRESS, CITY, STATE, ZCODE, PHONE_N, REG_DATE) VALUES"
                + "(?,?,?,?,?,?,?,?)";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(insertTableSQL);

            ps.setInt(1, own.getOWNER_ID());
            ps.setString(2, own.getCOMPANY());
            ps.setString(3, own.getADDRESS());
            ps.setString(4, own.getCITY());
            ps.setString(5, own.getSTATE());
            ps.setInt(6, own.getZCODE());
            ps.setString(7, own.getPHONE_N());
            ps.setString(8, regdate.getCurrentTimeStamp());

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

    public OwnerRecord search(int OwnerCode) throws Exception {

        OwnerRecord own = new OwnerRecord();

        String sql = "SELECT COMPANY, ADDRESS, CITY, STATE, ZCODE, PHONE_N,"
                + " REG_DATE FROM OWNER WHERE OWNER_ID = ?";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setInt(1, OwnerCode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                own.setCOMPANY(rs.getString("COMPANY"));
                own.setADDRESS(rs.getString("ADDRESS"));
                own.setCITY(rs.getString("CITY"));
                own.setSTATE(rs.getString("STATE"));
                own.setZCODE(rs.getInt("ZCODE"));
                own.setPHONE_N(rs.getString("PHONE_N"));
                own.setREG_DATE(rs.getString("REG_DATE"));

                return own;

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

    public int updateOwner(OwnerRecord own, int OwnerCode) throws SQLException {
        int affected = 0;

        Connection connect = null;
        PreparedStatement ps = null;

        String sql = "UPDATE OWNER SET COMPANY = ? , ADDRESS = ? , CITY = ? , STATE = ? "
                + ", ZCODE = ? , PHONE_N = ? WHERE OWNER_ID = ?";

        try {
            DBConnection con = new DBConnection();
            connect = con.getConnection();
            ps = connect.prepareStatement(sql);

            ps.setString(1, own.getCOMPANY());
            ps.setString(2, own.getADDRESS());
            ps.setString(3, own.getCITY());
            ps.setString(4, own.getSTATE());
            ps.setInt(5, own.getZCODE());
            ps.setString(6, own.getPHONE_N());
            ps.setInt(7, OwnerCode);

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

    public boolean delete(int OwnerCode) throws Exception {

        String sql = "DELETE FROM OWNER WHERE OWNER_ID = ?";

        DBConnection con = new DBConnection();
        Connection connect = con.getConnection();
        PreparedStatement ps = connect.prepareStatement(sql);

        try {
            ps.setInt(1, OwnerCode);
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
