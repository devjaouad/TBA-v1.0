/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package DAO;

public class UserDao {

    private final String adm = "tba";
    private final String pwd = "000";
    private final String pwdForUpdate = "0000";

    public String getAdm() {
        return adm;
    }

    public String getPwd() {
        return pwd;
    }

    public String getpwdForUpdate() {
        return pwdForUpdate;
    }

    public boolean authenticate(String U, String P) {
        if (this.getAdm().equals(U) && this.getPwd().equals(P)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean authenticateForUpdate(String P) {
        if (this.getpwdForUpdate().equals(P)) {
            return true;
        } else {
            return false;
        }
    }

}
