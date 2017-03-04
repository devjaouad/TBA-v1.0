/*
 *Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package JFrames;

import DAO.TaxiRecordDao;
import DAO.Validation;
import Driver.DBConnection;
import Entities.DialogMessage;
import Entities.TaxiRecord;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
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

public class Weekly_Income extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
    Validation vl = new Validation();
    NumberFormat nf = NumberFormat.getCurrencyInstance();
    NumberFormat dc = new DecimalFormat("#0.00");
    DialogMessage dm = new DialogMessage();
    PleaseWait d = new PleaseWait(new javax.swing.JFrame(), true);
    private PrintIsLoading PrintData;

    public Weekly_Income() {
        initComponents();
        Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        this.setSize(rec.width, rec.height);

        this.AllTaxiRecordsSum();
        WeeklyIncomeTable.setEnabled(false);
        this.clear();
        this.DataEntryValidation();
        this.showallbtn.setEnabled(false);
        this.setBorder(null);
        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
        bi.setNorthPane(null);
        this.WeeklyIncomeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private void clear() {
        firstnametxt.setText(null);
        lastnametxt.setText(null);
        empidtxt.setText(null);
        empidtxt.requestFocus();

    }

    private void AllTaxiRecordsSum() {
        try {
            TaxiRecordDao txrecdao = new TaxiRecordDao();

            ArrayList<TaxiRecord> records = txrecdao.getAllRecordsSum();

            Object[] tableColumnName = new Object[15];

            tableColumnName[0] = "Week";
            tableColumnName[1] = "First Name";
            tableColumnName[2] = "Last Name";
            tableColumnName[3] = "H.License";
            tableColumnName[4] = "CC.Income";
            tableColumnName[5] = "Cash.Income";
            tableColumnName[6] = "Trips Total";
            tableColumnName[7] = "Ez-Pass";
            tableColumnName[8] = "Tax Total";
            tableColumnName[9] = "Lease Fee";
            tableColumnName[10] = "Checks";
            tableColumnName[11] = "Miles";
            tableColumnName[12] = "Cash Tips";
            tableColumnName[13] = "Gas";
            tableColumnName[14] = "Net Income";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.WeeklyIncomeTable.setModel(tbd);

            for (int i = 0; i < records.size(); i++) {
                Object[] RowRec = new Object[15];

                RowRec[0] = records.get(i).getWEEK();
                RowRec[1] = records.get(i).getFNAME().toUpperCase();
                RowRec[2] = records.get(i).getLNAME().toUpperCase();
                RowRec[3] = records.get(i).getEMP_ID();
                RowRec[4] = nf.format(records.get(i).getCC_INCOME());
                RowRec[5] = nf.format(records.get(i).getCASH_INCOME());
                RowRec[6] = records.get(i).getTripsTotal();
                RowRec[7] = nf.format(records.get(i).getEZPASS());
                RowRec[8] = nf.format(records.get(i).getTaxTotal());
                RowRec[9] = nf.format(records.get(i).getLEASEFEE());
                RowRec[10] = nf.format(records.get(i).getCHECKS());
                RowRec[11] = dc.format(records.get(i).getMILESTOTAL());
                RowRec[12] = nf.format(records.get(i).getCASHTIPS());
                RowRec[13] = nf.format(records.get(i).getGAS());
                RowRec[14] = nf.format(records.get(i).getNet_Income());

                tbd.addRow(RowRec);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }
    }

    private void searchByMultipleFields() {
        try {

            int EMP_ID = vl.ParseInteger(this.empidtxt.getText().trim());
            String FNAME = this.firstnametxt.getText().trim();
            String LNAME = this.lastnametxt.getText().trim();

            TaxiRecordDao txrecdao = new TaxiRecordDao();

            ArrayList<TaxiRecord> records = txrecdao.getAllRecordsByInput(EMP_ID, FNAME, LNAME);

            Object[] tableColumnName = new Object[15];

            tableColumnName[0] = "Week";
            tableColumnName[1] = "First Name";
            tableColumnName[2] = "Last Name";
            tableColumnName[3] = "H.License";
            tableColumnName[4] = "CC.Income";
            tableColumnName[5] = "Cash.Income";
            tableColumnName[6] = "Trips Total";
            tableColumnName[7] = "Ez-Pass";
            tableColumnName[8] = "Tax Total";
            tableColumnName[9] = "Lease Fee";
            tableColumnName[10] = "Checks";
            tableColumnName[11] = "Miles";
            tableColumnName[12] = "Cash Tips";
            tableColumnName[13] = "Gas";
            tableColumnName[14] = "Net Income";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.WeeklyIncomeTable.setModel(tbd);

            if (records != null) {
                for (int i = 0; i < records.size(); i++) {
                    Object[] RowRec = new Object[15];

                    RowRec[0] = records.get(i).getWEEK();
                    RowRec[1] = records.get(i).getFNAME().toUpperCase();
                    RowRec[2] = records.get(i).getLNAME().toUpperCase();
                    RowRec[3] = records.get(i).getEMP_ID();
                    RowRec[4] = nf.format(records.get(i).getCC_INCOME());
                    RowRec[5] = nf.format(records.get(i).getCASH_INCOME());
                    RowRec[6] = records.get(i).getTripsTotal();
                    RowRec[7] = nf.format(records.get(i).getEZPASS());
                    RowRec[8] = nf.format(records.get(i).getTaxTotal());
                    RowRec[9] = nf.format(records.get(i).getLEASEFEE());
                    RowRec[10] = nf.format(records.get(i).getCHECKS());
                    RowRec[11] = dc.format(records.get(i).getMILESTOTAL());
                    RowRec[12] = nf.format(records.get(i).getCASHTIPS());
                    RowRec[13] = nf.format(records.get(i).getGAS());
                    RowRec[14] = nf.format(records.get(i).getNet_Income());

                    tbd.addRow(RowRec);

                }
            } else {
                dm.Message("No data found!");
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }
    }

    private void MainSearch() {

        try {

            if (!firstnametxt.getText().trim().equals("")) {
                this.searchByMultipleFields();
                this.showallbtn.setEnabled(true);

            } else if (!lastnametxt.getText().trim().equals("")) {
                this.searchByMultipleFields();
                this.showallbtn.setEnabled(true);

            } else if (!empidtxt.getText().trim().equals("")) {
                this.searchByMultipleFields();
                this.showallbtn.setEnabled(true);

            } else {
                dm.Message("Invalid entry, please try again");
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }
        this.showallbtn.setEnabled(true);

    }

    private void DataEntryValidation() {
        empidtxt.setDocument(new Validation(7));
        firstnametxt.setDocument(new Validation(30));
        lastnametxt.setDocument(new Validation(30));
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

                        if (!firstnametxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else if (!lastnametxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else if (!empidtxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else {
                            dm.Message("Invalid entry, please try again");
                        }

                    } catch (Exception e) {
                        System.out.println(e.toString());
                        dm.Message("An error has occurred, please try again");
                    }
                }

                private void printByInput() {
                    InputStream st = getClass().getResourceAsStream("/Reports/Taxi_Weekly_Report.jrxml");
                    try {

                        DBConnection con = new DBConnection();
                        Connection connect = con.getConnection();

                        JasperDesign jd = JRXmlLoader.load(st);

                        String sql = "SELECT T.Week, E.FNAME, E.LNAME, E.EMP_ID, Sum(CashTrips + CCTrips) AS Total_Trips,"
                                + " Sum(T.CC_INCOME) AS CC_Income, Sum(T.CASH_INCOME) AS Cash_Income, Sum(T.EZPASS) AS EzPass,"
                                + " Sum(ST_SURCHARGE + IMP_SURCHARGE) AS Tax_Total, Sum(T.LEASEFEE) AS Lease_Fee, Sum(T.CHECKS) AS Checks,"
                                + " Sum(T.MILESTOTAL) AS Total_Miles, Sum(T.GAS) AS GASE, Sum(T.CASHTIPS) AS CASHTIPS,"
                                + " Sum(CC_INCOME + CASH_INCOME - ST_SURCHARGE- IMP_SURCHARGE - EZPASS - LEASEFEE ) AS Net_Income"
                                + " FROM EMPLOYEE E INNER JOIN Taxi_REC T ON E.Emp_ID = T.Emp_ID "
                                + " WHERE E.Emp_ID = " + vl.ParseInteger(empidtxt.getText().trim()) + ""
                                + " OR E.FNAME = '" + firstnametxt.getText() + "'"
                                + " OR E.LNAME = '" + lastnametxt.getText() + "'"
                                + " GROUP BY T.Week, E.Fname, E.Lname, E.Emp_ID "
                                + " ORDER BY T.Week";

                        JRDesignQuery newQuery = new JRDesignQuery();
                        newQuery.setText(sql);
                        jd.setQuery(newQuery);
                        JasperReport jr = JasperCompileManager.compileReport(jd);                      
                        JasperPrint jp = JasperFillManager.fillReport(jr, null, connect);
                        JRViewer viewer = new JRViewer(jp);
                        JFrame ReportFrame = new JFrame("Weekly Income Report");
                        ReportFrame.add(viewer);
                        ReportFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/notebook_edit.png")));
                        Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                        ReportFrame.setSize(rec.width, rec.height);
                        ReportFrame.setVisible(true);

                    } catch (JRException | SecurityException e) {
                        System.out.println(e.toString());
                        dm.Message("An error has been occurred: " + e.toString());

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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        BackToMenuBtn = new javax.swing.JButton();
        printbtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        searchbtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        firstnametxt = new javax.swing.JTextField();
        clearbtn = new javax.swing.JButton();
        lastnametxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        empidtxt = new javax.swing.JTextField();
        showallbtn = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        WeeklyIncomeTable = new javax.swing.JTable();

        setTitle("Weekly Income");

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Lucida Calligraphy", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Weekly Income");

        BackToMenuBtn.setText("<< Back to Main Menu");
        BackToMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToMenuBtnActionPerformed(evt);
            }
        });
        BackToMenuBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BackToMenuBtnKeyPressed(evt);
            }
        });

        printbtn.setText("Print");
        printbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printbtnActionPerformed(evt);
            }
        });
        printbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                printbtnKeyPressed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Last Name :");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("First Name :");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        firstnametxt.setBackground(new java.awt.Color(153, 255, 255));
        firstnametxt.setText(" ");
        firstnametxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 255, 255)));
        firstnametxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnametxtActionPerformed(evt);
            }
        });

        clearbtn.setText("Clear");
        clearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearbtnActionPerformed(evt);
            }
        });
        clearbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                clearbtnKeyPressed(evt);
            }
        });

        lastnametxt.setBackground(new java.awt.Color(153, 255, 255));
        lastnametxt.setText(" ");
        lastnametxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 255, 255)));
        lastnametxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lastnametxtActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Hack License :");
        jLabel5.setAlignmentX(0.5F);
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        empidtxt.setBackground(new java.awt.Color(153, 255, 255));
        empidtxt.setText(" ");
        empidtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 255, 255)));
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

        showallbtn.setText("Show All / Refresh");
        showallbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showallbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BackToMenuBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(printbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(showallbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(showallbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(firstnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(lastnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchbtn)
                    .addComponent(printbtn)
                    .addComponent(clearbtn)
                    .addComponent(BackToMenuBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        WeeklyIncomeTable.setAutoCreateRowSorter(true);
        WeeklyIncomeTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        WeeklyIncomeTable.setGridColor(new java.awt.Color(102, 102, 102));
        jScrollPane2.setViewportView(WeeklyIncomeTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 776, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BackToMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToMenuBtnActionPerformed
        Component source = (Component) evt.getSource();
        Menu main = (Menu) SwingUtilities.windowForComponent(source);
        this.dispose();
        main.EnableBtns();
    }//GEN-LAST:event_BackToMenuBtnActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_searchbtnActionPerformed

    private void firstnametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnametxtActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_firstnametxtActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        this.clear();

    }//GEN-LAST:event_clearbtnActionPerformed

    private void lastnametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnametxtActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_lastnametxtActionPerformed

    private void empidtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empidtxtActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_empidtxtActionPerformed

    private void printbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtnActionPerformed
        PrintData = new PrintIsLoading();
        PrintData.execute();
    }//GEN-LAST:event_printbtnActionPerformed

    private void empidtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empidtxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_empidtxtKeyTyped

    private void searchbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.searchbtnActionPerformed(null);
        }
    }//GEN-LAST:event_searchbtnKeyPressed

    private void printbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_printbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.printbtnActionPerformed(null);
        }
    }//GEN-LAST:event_printbtnKeyPressed

    private void clearbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_clearbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.clearbtnActionPerformed(null);
        }
    }//GEN-LAST:event_clearbtnKeyPressed

    private void BackToMenuBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BackToMenuBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.BackToMenuBtnActionPerformed(null);
        }
    }//GEN-LAST:event_BackToMenuBtnKeyPressed

    private void showallbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showallbtnActionPerformed
        this.AllTaxiRecordsSum();
        this.showallbtn.setEnabled(false);
        this.clear();
    }//GEN-LAST:event_showallbtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackToMenuBtn;
    private javax.swing.JTable WeeklyIncomeTable;
    private javax.swing.JButton clearbtn;
    private javax.swing.JTextField empidtxt;
    private javax.swing.JTextField firstnametxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lastnametxt;
    private javax.swing.JButton printbtn;
    private javax.swing.JButton searchbtn;
    private javax.swing.JButton showallbtn;
    // End of variables declaration//GEN-END:variables
}
