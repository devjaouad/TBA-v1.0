/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package Entities;

public class OwnerRecord extends EmployeeRecord{

    private int OWNER_ID;
    private String COMPANY;
    

    public OwnerRecord(int OWNER_ID, String COMPANY) {
        this.OWNER_ID = OWNER_ID;
        this.COMPANY = COMPANY;
        
    }

    public OwnerRecord() {
        super();
        this.OWNER_ID = 0;
        this.COMPANY = "";
        
    }

    public int getOWNER_ID() {
        return OWNER_ID;
    }

    public void setOWNER_ID(int OWNER_ID) {
        this.OWNER_ID = OWNER_ID;
    }

    public String getCOMPANY() {
        return COMPANY;
    }

    public void setCOMPANY(String COMPANY) {
        this.COMPANY = COMPANY;
    }

}