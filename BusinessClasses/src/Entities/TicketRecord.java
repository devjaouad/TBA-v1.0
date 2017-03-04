/*Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package Entities;

public class TicketRecord extends EmployeeRecord{
    String TICKET_ID;
    int EMP_ID;
    String DATE;
    String VIOLATION;
    Double COST;

    public TicketRecord(String TICKET_ID, int EMP_ID, String DATE, String VIOLATION, Double COST) {
        this.TICKET_ID = TICKET_ID;
        this.EMP_ID = EMP_ID;
        this.DATE = DATE;
        this.VIOLATION = VIOLATION;
        this.COST = COST;
    }
    public TicketRecord() {
        super();
        this.TICKET_ID = "";
        this.EMP_ID = 0;
        this.DATE = "";
        this.VIOLATION = "";
        this.COST = 0.0;
    }

    public String getTICKET_ID() {
        return TICKET_ID;
    }

    public void setTICKET_ID(String TICKET_ID) {
        this.TICKET_ID = TICKET_ID;
    }

    @Override
    public int getEMP_ID() {
        return EMP_ID;
    }

    @Override
    public void setEMP_ID(int EMP_ID) {
        this.EMP_ID = EMP_ID;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getVIOLATION() {
        return VIOLATION;
    }

    public void setVIOLATION(String VIOLATION) {
        this.VIOLATION = VIOLATION;
    }

    public Double getCOST() {
        return COST;
    }

    public void setCOST(Double COST) {
        this.COST = COST;
    }
    
    
}
