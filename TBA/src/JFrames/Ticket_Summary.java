/*
 *Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package JFrames;

import DAO.TicketRecordDao;
import DAO.Validation;
import Driver.DBConnection;
import Entities.DialogMessage;
import Entities.TicketRecord;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;

public final class Ticket_Summary extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
    DialogMessage dm = new DialogMessage();
    Validation vl = new Validation();
    NumberFormat nf = NumberFormat.getCurrencyInstance();
    PleaseWait d = new PleaseWait(new javax.swing.JFrame(), true);
    private PrintIsLoading PrintData;

    public Ticket_Summary() {
        initComponents();
        this.AllTicketRecords();
        empidtxt.setText(null);
        showallbtn.setEnabled(false);
        empidtxt.setDocument(new Validation(20));
        TicketsSummaryTable.setEnabled(false);
        empidtxt.setDocument(new Validation(20));
        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
        bi.setNorthPane(null);
        this.setBorder(null);

    }

    public void AllTicketRecords() {
        try {
            TicketRecordDao ticketrecdao = new TicketRecordDao();

            ArrayList<TicketRecord> records = ticketrecdao.getAllTicketsRecords();

            Object[] tableColumnName = new Object[7];

            tableColumnName[0] = "Date";
            tableColumnName[1] = "H.License";
            tableColumnName[2] = "First Name";
            tableColumnName[3] = "Last Name";
            tableColumnName[4] = "Ticket ID";
            tableColumnName[5] = "Violation";
            tableColumnName[6] = "Cost";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.TicketsSummaryTable.setModel(tbd);

            for (int i = 0; i < records.size(); i++) {
                Object[] RowRec = new Object[7];

                RowRec[0] = records.get(i).getDATE();
                RowRec[1] = records.get(i).getEMP_ID();
                RowRec[2] = records.get(i).getFNAME().toUpperCase();
                RowRec[3] = records.get(i).getLNAME().toUpperCase();
                RowRec[4] = records.get(i).getTICKET_ID();
                RowRec[5] = records.get(i).getVIOLATION();
                RowRec[6] = nf.format(records.get(i).getCOST());

                tbd.addRow(RowRec);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        } catch (Exception e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }

    }

    private void search() {

        try {

            int EMP_ID = vl.ParseInteger(this.empidtxt.getText());
            String TICKET_ID = this.empidtxt.getText();

            TicketRecordDao ticketrecdao = new TicketRecordDao();

            ArrayList<TicketRecord> records = ticketrecdao.getTicketByID(EMP_ID, TICKET_ID);

            Object[] tableColumnName = new Object[7];

            tableColumnName[0] = "Date";
            tableColumnName[1] = "H.License";
            tableColumnName[2] = "First Name";
            tableColumnName[3] = "Last Name";
            tableColumnName[4] = "Ticket ID";
            tableColumnName[5] = "Violation";
            tableColumnName[6] = "Cost";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.TicketsSummaryTable.setModel(tbd);

            for (int i = 0; i < records.size(); i++) {
                Object[] RowRec = new Object[7];

                RowRec[0] = records.get(i).getDATE();
                RowRec[1] = records.get(i).getEMP_ID();
                RowRec[2] = records.get(i).getFNAME().toUpperCase();
                RowRec[3] = records.get(i).getLNAME().toUpperCase();
                RowRec[4] = records.get(i).getTICKET_ID();
                RowRec[5] = records.get(i).getVIOLATION();
                RowRec[6] = nf.format(records.get(i).getCOST());

                tbd.addRow(RowRec);

            }
        } catch (SQLException | NumberFormatException e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }

    }

    private class PrintIsLoading extends SwingWorker<Void, Void> {

        @Override
        @SuppressWarnings("Convert2Lambda")
        public Void doInBackground() throws InterruptedException {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    d.setVisible(true);
                    this.MainPrint();
                }

                private void MainPrint() {
                    try {

                        if (!empidtxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else {
                            dm.Message("Invalid entry, Please try again");
                        }

                    } catch (Exception e) {
                        System.out.println(e.toString());
                        dm.Message("An error has occurred, please try again");
                    }
                }

                private void printByInput() {
                    InputStream st = getClass().getResourceAsStream("/Reports/Ticket_Report.jrxml");

                    try {
                        DBConnection con = new DBConnection();
                        Connection connect = con.getConnection();

                        JasperDesign jd = JRXmlLoader.load(st);

                        String sql = "SELECT DATE, T.EMP_ID ,E.FNAME, E.LNAME ,TICKET_ID, VIOLATION, COST"
                                + " FROM EMPLOYEE E INNER JOIN TICKET T ON E.EMP_ID = T.EMP_ID"
                                + " WHERE T.EMP_ID = " + vl.ParseInteger(empidtxt.getText().trim()) + ""
                                + " ORDER BY DATE";

                        JRDesignQuery newQuery = new JRDesignQuery();
                        newQuery.setText(sql);
                        jd.setQuery(newQuery);
                        JasperReport jr = JasperCompileManager.compileReport(jd);
                        JasperPrint jp = JasperFillManager.fillReport(jr, null, connect);
                        JRViewer viewer = new JRViewer(jp);
                        JFrame ReportFrame = new JFrame("Tickets Report");
                        ReportFrame.add(viewer);
                        ReportFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/notebook_edit.png")));
                        Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                        ReportFrame.setSize(rec.width, rec.height);
                        ReportFrame.setVisible(true);

                    } catch (JRException | SecurityException e) {
                        System.out.println(e.toString());

                    }
                }
            });

            Thread.sleep(4000);//1000 millis = 1 sec

            return null;
        }

        @Override
        public void done() {
            d.dispose();

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        empidtxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        searchBtn = new javax.swing.JButton();
        showallbtn = new javax.swing.JButton();
        printbtn = new javax.swing.JButton();
        AddBtn = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        BackToMenuBtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        TicketsSummaryTable = new javax.swing.JTable();

        setTitle("Tickets");

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Lucida Calligraphy", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tickets");

        empidtxt.setBackground(new java.awt.Color(153, 255, 255));
        empidtxt.setText(" ");
        empidtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 255, 255)));
        empidtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empidtxtActionPerformed(evt);
            }
        });
        empidtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                empidtxtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                empidtxtKeyTyped(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Employee ID/Ticket ID :");
        jLabel5.setAlignmentX(0.5F);
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        searchBtn.setText("Search");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });
        searchBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchBtnKeyPressed(evt);
            }
        });

        showallbtn.setText("Show All / Refresh");
        showallbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showallbtnActionPerformed(evt);
            }
        });
        showallbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                showallbtnKeyPressed(evt);
            }
        });

        printbtn.setText("Print");
        printbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbtnActionPerformed(evt);
            }
        });

        AddBtn.setText("Add");
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });
        AddBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddBtnKeyPressed(evt);
            }
        });

        UpdateBtn.setText("Update");
        UpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateBtnActionPerformed(evt);
            }
        });
        UpdateBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UpdateBtnKeyPressed(evt);
            }
        });

        BackToMenuBtn.setText("<< Back to Main Menu");
        BackToMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToMenuBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BackToMenuBtn)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(empidtxt)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(searchBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(printbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(AddBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(showallbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddBtn)
                            .addComponent(UpdateBtn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(BackToMenuBtn)
                            .addComponent(searchBtn)
                            .addComponent(printbtn)
                            .addComponent(showallbtn))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TicketsSummaryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(TicketsSummaryTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        if (empidtxt.getText().trim().equals("")) {
            dm.Message("Invalid entry, please try again!");
            empidtxt.requestFocus();
        } else {
            try {
                this.search();
                showallbtn.setEnabled(true);
            } catch (Exception e) {
                System.out.println(e.toString());
                dm.Message("An error has occurred, please try again");
            }

        }

    }//GEN-LAST:event_searchBtnActionPerformed

    private void showallbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showallbtnActionPerformed
        this.AllTicketRecords();
        this.showallbtn.setEnabled(false);
        this.empidtxt.setText(null);
    }//GEN-LAST:event_showallbtnActionPerformed

    private void empidtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empidtxtKeyTyped


    }//GEN-LAST:event_empidtxtKeyTyped

    private void searchBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.searchBtnActionPerformed(null);
        }
    }//GEN-LAST:event_searchBtnKeyPressed

    private void showallbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_showallbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.showallbtnActionPerformed(null);
        }
    }//GEN-LAST:event_showallbtnKeyPressed

    private void empidtxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empidtxtKeyPressed

    }//GEN-LAST:event_empidtxtKeyPressed

    private void empidtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empidtxtActionPerformed
        this.searchBtnActionPerformed(evt);
    }//GEN-LAST:event_empidtxtActionPerformed

    private void printbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtnActionPerformed
        PrintData = new PrintIsLoading();
        PrintData.execute();
    }//GEN-LAST:event_printbtnActionPerformed

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
        Add_Ticket dialog = new Add_Ticket(new javax.swing.JFrame(), true);
        dialog.setVisible(true);
        this.showallbtn.setEnabled(true);

    }//GEN-LAST:event_AddBtnActionPerformed

    private void AddBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddBtnKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddBtnKeyPressed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
        Ticket_Info dialog = new Ticket_Info(new javax.swing.JFrame(), true);
        dialog.setVisible(true);
        this.showallbtn.setEnabled(true);
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void UpdateBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UpdateBtnKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_UpdateBtnKeyPressed

    private void BackToMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToMenuBtnActionPerformed
        Component source = (Component) evt.getSource();
        Menu main = (Menu) SwingUtilities.windowForComponent(source);
        this.dispose();
        
        main.EnableBtns();
    }//GEN-LAST:event_BackToMenuBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton BackToMenuBtn;
    private javax.swing.JTable TicketsSummaryTable;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JTextField empidtxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton printbtn;
    private javax.swing.JButton searchBtn;
    private javax.swing.JButton showallbtn;
    // End of variables declaration//GEN-END:variables
}
