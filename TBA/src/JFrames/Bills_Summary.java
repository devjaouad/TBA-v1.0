/*
 *Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package JFrames;

import DAO.BillRecordDao;
import DAO.Validation;
import Driver.DBConnection;
import Entities.BillsRecord;
import Entities.DialogMessage;
import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTextField;
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

public class Bills_Summary extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
    private PrintIsLoading PrintData;

    Validation vl = new Validation();
    NumberFormat nf = NumberFormat.getCurrencyInstance();
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    DialogMessage dm = new DialogMessage();
    PleaseWait d = new PleaseWait(new javax.swing.JFrame(), true);

    /**
     * Creates new form Bills_Summary
     */
    public Bills_Summary() {
        initComponents();
        this.AllBillsRecords();
        BillsSummaryTable.setEnabled(false);
        this.showallbtn.setEnabled(false);
        this.clear();
        this.DataEntryValidation();
        this.setBorder(null);
        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
        bi.setNorthPane(null);

    }

    private void DataEntryValidation() {
        empidtxt.setDocument(new Validation(7));
        firstnametxt.setDocument(new Validation(30));
        lastnametxt.setDocument(new Validation(30));
    }

    private void AllBillsRecords() {
        try {
            BillRecordDao billrecdao = new BillRecordDao();

            ArrayList<BillsRecord> records = billrecdao.getAllRecords();

            Object[] tableColumnName = new Object[11];

            tableColumnName[0] = "Date";
            tableColumnName[1] = "H.License";
            tableColumnName[2] = "First Name";
            tableColumnName[3] = "Last Name";
            tableColumnName[4] = "MONEY SENT";
            tableColumnName[5] = "RENT";
            tableColumnName[6] = "PHONE";
            tableColumnName[7] = "GASE";
            tableColumnName[8] = "ELECTRICITY";
            tableColumnName[9] = "INTERNET";
            tableColumnName[10] = "OTHER";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.BillsSummaryTable.setModel(tbd);

            Object[] RowRec = new Object[11];

            for (int i = 0; i < records.size(); i++) {

                //RowRec[0] = df.format(records.get(i).getDATE());
                RowRec[0] = records.get(i).getDATE();
                RowRec[1] = records.get(i).getEMP_ID();
                RowRec[2] = records.get(i).getFNAME().toUpperCase();
                RowRec[3] = records.get(i).getLNAME().toUpperCase();
                RowRec[4] = nf.format(records.get(i).getMONEY_SENT());
                RowRec[5] = nf.format(records.get(i).getRENT());
                RowRec[6] = nf.format(records.get(i).getPHONE());
                RowRec[7] = nf.format(records.get(i).getGAS());
                RowRec[8] = nf.format(records.get(i).getELECTRICITY());
                RowRec[9] = nf.format(records.get(i).getINTERNET());
                RowRec[10] = nf.format(records.get(i).getOTHER());

                tbd.addRow(RowRec);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }
    }

    private void searchByMultipleFields() {

        try {

            int EMP_ID = vl.ParseInteger(this.empidtxt.getText());
            String FNAME = this.firstnametxt.getText();
            String LNAME = this.lastnametxt.getText();

            BillRecordDao billrecdao = new BillRecordDao();

            ArrayList<BillsRecord> records = billrecdao.getBillRecordByField(EMP_ID, FNAME, LNAME);

            Object[] tableColumnName = new Object[11];

            tableColumnName[0] = "Date";
            tableColumnName[1] = "H.License";
            tableColumnName[2] = "First Name";
            tableColumnName[3] = "Last Name";
            tableColumnName[4] = "MONEY SENT";
            tableColumnName[5] = "RENT";
            tableColumnName[6] = "PHONE";
            tableColumnName[7] = "GASE";
            tableColumnName[8] = "ELECTRICITY";
            tableColumnName[9] = "INTERNET";
            tableColumnName[10] = "OTHER";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.BillsSummaryTable.setModel(tbd);

            for (int i = 0; i < records.size(); i++) {
                Object[] RowRec = new Object[11];

                RowRec[0] = records.get(i).getDATE();
                RowRec[1] = records.get(i).getEMP_ID();
                RowRec[2] = records.get(i).getFNAME().toUpperCase();
                RowRec[3] = records.get(i).getLNAME().toUpperCase();
                RowRec[4] = nf.format(records.get(i).getMONEY_SENT());
                RowRec[5] = nf.format(records.get(i).getRENT());
                RowRec[6] = nf.format(records.get(i).getPHONE());
                RowRec[7] = nf.format(records.get(i).getGAS());
                RowRec[8] = nf.format(records.get(i).getELECTRICITY());
                RowRec[9] = nf.format(records.get(i).getINTERNET());
                RowRec[10] = nf.format(records.get(i).getOTHER());

                tbd.addRow(RowRec);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }

    }

    private void searchByDate() {

        try {

            JTextField stxt = ((JTextField) startdatetxt.getDateEditor().getUiComponent());
            String sDATE = stxt.getText();

            JTextField etxt = ((JTextField) enddatetxt.getDateEditor().getUiComponent());
            String eDATE = etxt.getText();

            BillRecordDao billrecdao = new BillRecordDao();

            ArrayList<BillsRecord> records = billrecdao.getBillRecordByDate(sDATE, eDATE);

            Object[] tableColumnName = new Object[11];

            tableColumnName[0] = "Date";
            tableColumnName[1] = "H.License";
            tableColumnName[2] = "First Name";
            tableColumnName[3] = "Last Name";
            tableColumnName[4] = "MONEY SENT";
            tableColumnName[5] = "RENT";
            tableColumnName[6] = "PHONE";
            tableColumnName[7] = "GASE";
            tableColumnName[8] = "ELECTRICITY";
            tableColumnName[9] = "INTERNET";
            tableColumnName[10] = "OTHER";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.BillsSummaryTable.setModel(tbd);

            for (int i = 0; i < records.size(); i++) {
                Object[] RowRec = new Object[11];

                RowRec[0] = records.get(i).getDATE();
                RowRec[1] = records.get(i).getEMP_ID();
                RowRec[2] = records.get(i).getFNAME().toUpperCase();
                RowRec[3] = records.get(i).getLNAME().toUpperCase();
                RowRec[4] = nf.format(records.get(i).getMONEY_SENT());
                RowRec[5] = nf.format(records.get(i).getRENT());
                RowRec[6] = nf.format(records.get(i).getPHONE());
                RowRec[7] = nf.format(records.get(i).getGAS());
                RowRec[8] = nf.format(records.get(i).getELECTRICITY());
                RowRec[9] = nf.format(records.get(i).getINTERNET());
                RowRec[10] = nf.format(records.get(i).getOTHER());

                tbd.addRow(RowRec);
            }
        } catch (SQLException e) {
            dm.Message("An error has occurred, please try again" + e.toString());
        }

    }

    private void MainSearch() {
        JTextField stxt = ((JTextField) startdatetxt.getDateEditor().getUiComponent());
        JTextField etxt = ((JTextField) enddatetxt.getDateEditor().getUiComponent());

        try {

            if (!stxt.getText().trim().equals("")) {
                if (!etxt.getText().trim().equals("")) {
                    this.searchByDate();
                    this.showallbtn.setEnabled(true);

                }
            } else if (!firstnametxt.getText().trim().equals("")) {
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
            dm.Message("An error has occurred: " + e.toString());
        }

    }

    private void clear() {
        empidtxt.requestFocus();
        empidtxt.setText(null);
        enddatetxt.setDate(null);
        firstnametxt.setText(null);
        lastnametxt.setText(null);
        startdatetxt.setDate(null);
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
                    JTextField stxt = ((JTextField) startdatetxt.getDateEditor().getUiComponent());
                    JTextField etxt = ((JTextField) enddatetxt.getDateEditor().getUiComponent());

                    try {

                        if (!stxt.getText().trim().equals("")) {
                            if (!etxt.getText().trim().equals("")) {
                                this.printByDate();
                            }

                        } else if (!firstnametxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else if (!lastnametxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else if (!empidtxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else {
                            dm.Message("Invalid entry, please try again");
                        }

                    } catch (Exception e) {
                        dm.Message("An error has occurred: " + e.toString());
                    }
                }

                private void printByDate() {
                    InputStream st = getClass().getResourceAsStream("/Reports/Bills_Report.jrxml");

                    try {
                        JTextField stxt = ((JTextField) startdatetxt.getDateEditor().getUiComponent());
                        JTextField etxt = ((JTextField) enddatetxt.getDateEditor().getUiComponent());

                        DBConnection con = new DBConnection();
                        Connection connect = con.getConnection();

                        JasperDesign jd = JRXmlLoader.load(st);

                        String sql = "SELECT B.DATE AS DT, B.EMP_ID, E.FNAME, E.LNAME, MONEY_SENT, RENT, PHONE, GAS, ELECTRICITY, INTERNET, OTHER"
                                + " FROM EMPLOYEE E INNER JOIN BILLS B ON E.EMP_ID = B.EMP_ID"
                                + " WHERE B.DATE BETWEEN '" + stxt.getText() + "' AND '" + etxt.getText() + "'"
                                + " ORDER BY B.DATE";

                        JRDesignQuery newQuery = new JRDesignQuery();
                        newQuery.setText(sql);
                        jd.setQuery(newQuery);

                        JasperReport jr = JasperCompileManager.compileReport(jd);
                        JasperPrint jp = JasperFillManager.fillReport(jr, null, connect);

                        JRViewer viewer = new JRViewer(jp);
                        JFrame ReportFrame = new JFrame("Bills Report");
                        ReportFrame.add(viewer);
                        ReportFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/notebook_edit.png")));
                        Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                        ReportFrame.setSize(rec.width, rec.height);
                        ReportFrame.setVisible(true);

                    } catch (JRException | SecurityException e) {
                        dm.Message("An error has been occured: " + e.toString());

                    }
                }

                private void printByInput() {
                    InputStream st = getClass().getResourceAsStream("/Reports/Bills_Report.jrxml");

                    try {
                        DBConnection con = new DBConnection();
                        Connection connect = con.getConnection();

                        JasperDesign jd = JRXmlLoader.load(st);

                        String sql = "SELECT B.DATE AS DT, B.EMP_ID, E.FNAME, E.LNAME, MONEY_SENT, RENT, PHONE, GAS, ELECTRICITY, INTERNET, OTHER"
                                + " FROM EMPLOYEE E INNER JOIN BILLS B ON E.EMP_ID = B.EMP_ID"
                                + " WHERE E.Emp_ID = " + vl.ParseInteger(empidtxt.getText().trim()) + ""
                                + " OR E.FNAME = '" + firstnametxt.getText() + "'"
                                + " OR E.LNAME = '" + lastnametxt.getText() + "'"
                                + " ORDER BY B.DATE";

                        JRDesignQuery newQuery = new JRDesignQuery();
                        newQuery.setText(sql);
                        jd.setQuery(newQuery);

                        JasperReport jr = JasperCompileManager.compileReport(jd);
                        JasperPrint jp = JasperFillManager.fillReport(jr, null, connect);
                        JRViewer viewer = new JRViewer(jp);

                        JFrame ReportFrame = new JFrame("Bills Report");
                        ReportFrame.add(viewer);
                        ReportFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/notebook_edit.png")));
                        Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                        ReportFrame.setSize(rec.width, rec.height);
                        ReportFrame.setVisible(true);

                    } catch (JRException | SecurityException e) {
                        dm.Message("An error has been occured: " + e.toString());

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
        empidtxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        firstnametxt = new javax.swing.JTextField();
        lastnametxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        startdatetxt = new com.toedter.calendar.JDateChooser();
        enddatetxt = new com.toedter.calendar.JDateChooser();
        printbtn = new javax.swing.JButton();
        searchbtn = new javax.swing.JButton();
        clearbtn = new javax.swing.JButton();
        showallbtn = new javax.swing.JButton();
        UpdateBtn = new javax.swing.JButton();
        AddBtn = new javax.swing.JButton();
        BackToMenuBtn = new javax.swing.JButton();
        TableScrollPane = new javax.swing.JScrollPane();
        BillsSummaryTable = new javax.swing.JTable();

        setTitle("Bills Summary");

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));

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

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("End Date :");
        jLabel8.setAlignmentX(0.5F);
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Start Date :");
        jLabel7.setAlignmentX(0.5F);
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Employee ID :");
        jLabel4.setAlignmentX(0.5F);
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        firstnametxt.setBackground(new java.awt.Color(153, 255, 255));
        firstnametxt.setText(" ");
        firstnametxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 255, 255)));
        firstnametxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnametxtActionPerformed(evt);
            }
        });
        firstnametxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                firstnametxtKeyTyped(evt);
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
        lastnametxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lastnametxtKeyTyped(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Last Name :");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("First Name :");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        startdatetxt.setBackground(new java.awt.Color(204, 255, 255));
        startdatetxt.setDateFormatString("M/d/yyyy");
        startdatetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                startdatetxtKeyTyped(evt);
            }
        });

        enddatetxt.setBackground(new java.awt.Color(204, 255, 255));
        enddatetxt.setDateFormatString("M/d/yyyy");
        enddatetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                enddatetxtKeyTyped(evt);
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

        showallbtn.setText("Show all / Refresh");
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

        UpdateBtn.setText("Update");
        UpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateBtnActionPerformed(evt);
            }
        });

        AddBtn.setText("Add");
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });

        BackToMenuBtn.setText("<< Back to Main Menu");
        BackToMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToMenuBtnActionPerformed(evt);
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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startdatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enddatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BackToMenuBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(printbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(UpdateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(showallbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(AddBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(UpdateBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(showallbtn)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(startdatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(enddatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(firstnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lastnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(BackToMenuBtn)
                                    .addComponent(searchbtn)
                                    .addComponent(printbtn)
                                    .addComponent(clearbtn))))
                        .addContainerGap(29, Short.MAX_VALUE))))
        );

        BillsSummaryTable.setModel(new javax.swing.table.DefaultTableModel(
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
        TableScrollPane.setViewportView(BillsSummaryTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableScrollPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void firstnametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnametxtActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_firstnametxtActionPerformed

    private void lastnametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnametxtActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_lastnametxtActionPerformed

    private void printbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtnActionPerformed
        PrintData = new PrintIsLoading();
        PrintData.execute();

    }//GEN-LAST:event_printbtnActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        this.MainSearch();

    }//GEN-LAST:event_searchbtnActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        this.clear();

    }//GEN-LAST:event_clearbtnActionPerformed

    private void empidtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empidtxtActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_empidtxtActionPerformed

    private void showallbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showallbtnActionPerformed
        this.AllBillsRecords();
        this.showallbtn.setEnabled(false);
        this.clear();
    }//GEN-LAST:event_showallbtnActionPerformed

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

    private void showallbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_showallbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.showallbtnActionPerformed(null);
        }
    }//GEN-LAST:event_showallbtnKeyPressed

    private void firstnametxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstnametxtKeyTyped

    }//GEN-LAST:event_firstnametxtKeyTyped

    private void lastnametxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastnametxtKeyTyped

    }//GEN-LAST:event_lastnametxtKeyTyped

    private void startdatetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_startdatetxtKeyTyped

    }//GEN-LAST:event_startdatetxtKeyTyped

    private void enddatetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_enddatetxtKeyTyped

    }//GEN-LAST:event_enddatetxtKeyTyped

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed
        Bills_Update dialog = new Bills_Update(new javax.swing.JFrame(), true);
        dialog.setVisible(true);
        this.showallbtn.setEnabled(true);
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
        Add_Bills dialog = new Add_Bills(new javax.swing.JFrame(), true);
        dialog.setVisible(true);
        this.showallbtn.setEnabled(true);
    }//GEN-LAST:event_AddBtnActionPerformed

    private void BackToMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToMenuBtnActionPerformed
        Component source = (Component) evt.getSource();
        Menu main = (Menu) SwingUtilities.windowForComponent(source);
        this.dispose();
        main.EnableBtns();
    }//GEN-LAST:event_BackToMenuBtnActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton BackToMenuBtn;
    private javax.swing.JTable BillsSummaryTable;
    private javax.swing.JScrollPane TableScrollPane;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JButton clearbtn;
    private javax.swing.JTextField empidtxt;
    private com.toedter.calendar.JDateChooser enddatetxt;
    private javax.swing.JTextField firstnametxt;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField lastnametxt;
    private javax.swing.JButton printbtn;
    private javax.swing.JButton searchbtn;
    private javax.swing.JButton showallbtn;
    private com.toedter.calendar.JDateChooser startdatetxt;
    // End of variables declaration//GEN-END:variables

}
