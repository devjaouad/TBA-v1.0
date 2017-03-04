/*
 *Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package JFrames;

import DAO.TicketRecordDao;
import DAO.Validation;
import Entities.DialogMessage;
import Entities.TicketRecord;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JOptionPane;

public class Ticket_Info extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;

    Validation vl = new Validation();
    DialogMessage dm = new DialogMessage();
    NumberFormat nf = NumberFormat.getCurrencyInstance();
    DecimalFormat df = new DecimalFormat("#0.00");

    public Ticket_Info(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.clear();
        this.ticketidtxt.requestFocus();
        this.DataEntryValidation();
        this.NotEditable();
    }

    private void SearchTicket() {

        try {

            String TICKET_ID = this.ticketidtxt.getText();

            TicketRecordDao tiktdao = new TicketRecordDao();
            TicketRecord tikt = tiktdao.search(TICKET_ID);

            if (tikt != null) {

                datetxt.setText(tikt.getDATE());
                ticketidtxt.setText(tikt.getTICKET_ID());
                empidtxt.setText(Integer.toString(tikt.getEMP_ID()));
                violationtxt.setText(tikt.getVIOLATION());
                double cost = tikt.getCOST();

                //format number with double sign and 2 decimal ponit
                costtxt.setText(df.format(cost));
                //Enable edit button
                this.editbtn.setEnabled(true);

            } else {
                this.clear();
                dm.Message("Ticket not found!");
                this.editbtn.setEnabled(false);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }

    }

    private void DeleteTicket() {
        try {
            String TICKET_ID = this.ticketidtxt.getText();
            TicketRecordDao ticketdao = new TicketRecordDao();

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Would you like to delete this Ticket?", "Warning", dialogButton);
            if (dialogResult == 0) {

                ticketdao.delete(TICKET_ID);
                dm.Message("The TICKET ID: " + TICKET_ID + " has been deleted");
                this.clear();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }
    }

    private void UpdateTicket() {
        try {

            String DATE = this.datetxt.getText();
            String TICKET_ID = this.ticketidtxt.getText();
            int EMP_ID = Integer.parseInt(this.empidtxt.getText());
            String VIOLATION = this.violationtxt.getText();
            double COST = Double.parseDouble(this.costtxt.getText());

            TicketRecord ticket = new TicketRecord();

            ticket.setDATE(DATE);
            ticket.setTICKET_ID(TICKET_ID);
            ticket.setEMP_ID(EMP_ID);
            ticket.setVIOLATION(VIOLATION);
            ticket.setCOST(COST);

            TicketRecordDao ticketdao = new TicketRecordDao();

            int result = ticketdao.updateTicket(ticket, TICKET_ID);

            if (result != 0) {
                dm.Message("Your data has been updated!");
            } else {
                dm.Message("Your data has been rejected!");
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }
        datetxt.requestFocus();

    }

    private void DataEntryValidation() {
        empidtxt.setDocument(new Validation(7));
        ticketidtxt.setDocument(new Validation(20));
        datetxt.setDocument(new Validation(10));
    }

    private void Editable() {
        costtxt.setEditable(true);
        datetxt.setEditable(true);
        empidtxt.setEditable(true);
        violationtxt.setEditable(true);
        this.savebtn.setEnabled(true);
        this.deletebtn.setEnabled(true);
        datetxt.requestFocus();
    }

    private void NotEditable() {
        costtxt.setEditable(false);
        datetxt.setEditable(false);
        empidtxt.setEditable(false);
        violationtxt.setEditable(false);
        this.savebtn.setEnabled(false);
        this.deletebtn.setEnabled(false);
        this.editbtn.setEnabled(false);
    }

    private void clear() {
        costtxt.setText(null);
        datetxt.setText(null);
        empidtxt.setText(null);
        violationtxt.setText(null);
        ticketidtxt.setText(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        costtxt = new javax.swing.JTextField();
        deletebtn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        editbtn = new javax.swing.JButton();
        empidtxt = new javax.swing.JTextField();
        savebtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        datetxt = new javax.swing.JTextField();
        exitbtn = new javax.swing.JButton();
        violationtxt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ticketidtxt = new javax.swing.JTextField();
        searchbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add Ticket");
        setAlwaysOnTop(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Date              ");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Violation");
        jLabel5.setAlignmentX(0.5F);
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        costtxt.setEditable(false);
        costtxt.setBackground(new java.awt.Color(153, 255, 255));
        costtxt.setText(" ");
        costtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        costtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                costtxtActionPerformed(evt);
            }
        });
        costtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                costtxtKeyTyped(evt);
            }
        });

        deletebtn.setText("Delete");
        deletebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletebtnActionPerformed(evt);
            }
        });
        deletebtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                deletebtnKeyPressed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Cost");
        jLabel6.setAlignmentX(0.5F);
        jLabel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        editbtn.setText("Edit");
        editbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editbtnActionPerformed(evt);
            }
        });
        editbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                editbtnKeyPressed(evt);
            }
        });

        empidtxt.setEditable(false);
        empidtxt.setBackground(new java.awt.Color(153, 255, 255));
        empidtxt.setText(" ");
        empidtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        empidtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empidtxtActionPerformed(evt);
            }
        });
        empidtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                empidtxtKeyTyped(evt);
            }
        });

        savebtn.setText("Save");
        savebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savebtnActionPerformed(evt);
            }
        });
        savebtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                savebtnKeyPressed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Emp ID");
        jLabel4.setAlignmentX(0.5F);
        jLabel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        datetxt.setEditable(false);
        datetxt.setBackground(new java.awt.Color(153, 255, 255));
        datetxt.setText(" ");
        datetxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        datetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                datetxtActionPerformed(evt);
            }
        });
        datetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                datetxtKeyTyped(evt);
            }
        });

        exitbtn.setText("Exit");
        exitbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitbtnActionPerformed(evt);
            }
        });
        exitbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                exitbtnKeyPressed(evt);
            }
        });

        violationtxt.setEditable(false);
        violationtxt.setBackground(new java.awt.Color(153, 255, 255));
        violationtxt.setText(" ");
        violationtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        violationtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                violationtxtActionPerformed(evt);
            }
        });
        violationtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                violationtxtKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(costtxt, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                            .addComponent(empidtxt)
                            .addComponent(violationtxt)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datetxt)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(savebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(datetxt)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(violationtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(costtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deletebtn)
                    .addComponent(editbtn)
                    .addComponent(savebtn)
                    .addComponent(exitbtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Lucida Calligraphy", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tickets");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Ticket ID");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        ticketidtxt.setBackground(new java.awt.Color(153, 255, 255));
        ticketidtxt.setText(" ");
        ticketidtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, java.awt.Color.white));
        ticketidtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ticketidtxtActionPerformed(evt);
            }
        });
        ticketidtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ticketidtxtKeyTyped(evt);
            }
        });

        searchbtn.setText("Search");
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });
        searchbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchbtnKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(123, 123, 123)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(92, 92, 92))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ticketidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchbtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchbtn)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ticketidtxt))
                .addGap(8, 8, 8))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ticketidtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ticketidtxtActionPerformed
        if (ticketidtxt.getText().trim().equals("")) {
            dm.Message("Invalid Entry, please try again!");
            ticketidtxt.requestFocus();

        } else {
            this.SearchTicket();
        }

    }//GEN-LAST:event_ticketidtxtActionPerformed

    private void editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbtnActionPerformed
        this.Editable();
        datetxt.selectAll();
    }//GEN-LAST:event_editbtnActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        this.DeleteTicket();
    }//GEN-LAST:event_deletebtnActionPerformed

    private void empidtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empidtxtActionPerformed
        empidtxt.transferFocus();
        violationtxt.selectAll();
    }//GEN-LAST:event_empidtxtActionPerformed

    private void costtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_costtxtActionPerformed
        costtxt.transferFocus();
    }//GEN-LAST:event_costtxtActionPerformed

    private void savebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebtnActionPerformed

        if (datetxt.getText().trim().equals("") || empidtxt.getText().trim().equals("")
                || ticketidtxt.getText().trim().equals("")
                || costtxt.getText().trim().equals("")) {
            dm.Message("Invalid Entry, please try again!");
            datetxt.requestFocusInWindow();

        } else {
            this.UpdateTicket();
            this.NotEditable();
            this.clear();
            ticketidtxt.requestFocus();
        }


    }//GEN-LAST:event_savebtnActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        if (ticketidtxt.getText().trim().equals("")) {
            dm.Message("Invalid Entry, please try again!");
            ticketidtxt.requestFocus();

        } else {
            this.SearchTicket();
        }
    }//GEN-LAST:event_searchbtnActionPerformed

    private void datetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datetxtActionPerformed
        datetxt.transferFocus();
        empidtxt.selectAll();
    }//GEN-LAST:event_datetxtActionPerformed

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitbtnActionPerformed

    private void violationtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_violationtxtActionPerformed
        violationtxt.transferFocus();
        costtxt.selectAll();
    }//GEN-LAST:event_violationtxtActionPerformed

    private void ticketidtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ticketidtxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_ticketidtxtKeyTyped

    private void datetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_datetxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_datetxtKeyTyped

    private void empidtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empidtxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_empidtxtKeyTyped

    private void violationtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_violationtxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_violationtxtKeyTyped

    private void costtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_costtxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_costtxtKeyTyped

    private void searchbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.searchbtnActionPerformed(null);
        }
    }//GEN-LAST:event_searchbtnKeyPressed

    private void savebtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_savebtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.savebtnActionPerformed(null);
        }
    }//GEN-LAST:event_savebtnKeyPressed

    private void editbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_editbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.editbtnActionPerformed(null);
        }
    }//GEN-LAST:event_editbtnKeyPressed

    private void deletebtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_deletebtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.deletebtnActionPerformed(null);
        }
    }//GEN-LAST:event_deletebtnKeyPressed

    private void exitbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_exitbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.exitbtnActionPerformed(null);
        }
    }//GEN-LAST:event_exitbtnKeyPressed

    /**
     * @param args the command line arguments
     */
    @SuppressWarnings("Convert2Lambda")
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Ticket_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ticket_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ticket_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ticket_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Ticket_Info dialog = new Ticket_Info(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField costtxt;
    private javax.swing.JTextField datetxt;
    private javax.swing.JButton deletebtn;
    private javax.swing.JButton editbtn;
    private javax.swing.JTextField empidtxt;
    private javax.swing.JButton exitbtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton savebtn;
    private javax.swing.JButton searchbtn;
    private javax.swing.JTextField ticketidtxt;
    private javax.swing.JTextField violationtxt;
    // End of variables declaration//GEN-END:variables
}
