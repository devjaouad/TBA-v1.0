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
import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.design.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JRViewer;

public class Taxi_Summary extends javax.swing.JInternalFrame {

    private static final long serialVersionUID = 1L;
     
    private PrintIsLoading PrintData;

    Validation vl = new Validation();
    NumberFormat nf = NumberFormat.getCurrencyInstance();
    DialogMessage dm = new DialogMessage();
    NumberFormat dc = new DecimalFormat("#0.00");
    PleaseWait d = new PleaseWait(new javax.swing.JFrame(), true);

    public Taxi_Summary() {
        initComponents();
        this.AllTaxiRecords();
        TaxiSummaryTable.setEnabled(false);
        this.clear();
        this.DataEntryValidation();
        this.showallbtn.setEnabled(false);
        this.setBorder(null);
        BasicInternalFrameUI bi = (BasicInternalFrameUI) this.getUI();
        bi.setNorthPane(null);
        this.TaxiSummaryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private void DataEntryValidation() {
        empidtxt.setDocument(new Validation(7));
        owneridtxt.setDocument(new Validation(3));
        medalliontxt.setDocument(new Validation(4));
        firstnametxt.setDocument(new Validation(30));
        lastnametxt.setDocument(new Validation(30));
    }

    private void AllTaxiRecords() {
        try {
            TaxiRecordDao txrecdao = new TaxiRecordDao();

            ArrayList<TaxiRecord> records = txrecdao.getAllRecords();

            Object[] tableColumnName = new Object[18];

            tableColumnName[0] = "Date";
            tableColumnName[1] = "H.License";
            tableColumnName[2] = "F.Name";
            tableColumnName[3] = "L.Name";
            tableColumnName[4] = "Owner ID";
            tableColumnName[5] = "Medallion";
            tableColumnName[6] = "CC.Income";
            tableColumnName[7] = "Ca.Income";
            tableColumnName[8] = "Cash Trips";
            tableColumnName[9] = "CC.trips";
            tableColumnName[10] = "Ez-Pass";
            tableColumnName[11] = "St.Srchg";
            tableColumnName[12] = "Imp.Srchg";
            tableColumnName[13] = "Lease Fee";
            tableColumnName[14] = "Checks";
            tableColumnName[15] = "Miles";
            tableColumnName[16] = "Gas";
            tableColumnName[17] = "Cash Tips";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.TaxiSummaryTable.setModel(tbd);

            for (int i = 0; i < records.size(); i++) {
                Object[] RowRec = new Object[18];

                RowRec[0] = records.get(i).getDATE();
                RowRec[1] = records.get(i).getEMP_ID();
                RowRec[2] = records.get(i).getFNAME().toUpperCase();
                RowRec[3] = records.get(i).getLNAME().toUpperCase();
                RowRec[4] = records.get(i).getOWNER_ID();
                RowRec[5] = records.get(i).getMEDALLION().toUpperCase();
                RowRec[6] = nf.format(records.get(i).getCC_INCOME());
                RowRec[7] = nf.format(records.get(i).getCASH_INCOME());
                RowRec[8] = records.get(i).getCASHTRIPS();
                RowRec[9] = records.get(i).getCCTRIPS();
                RowRec[10] = nf.format(records.get(i).getEZPASS());
                RowRec[11] = nf.format(records.get(i).getST_SURCHARGE());
                RowRec[12] = nf.format(records.get(i).getIMP_SURCHARGE());
                RowRec[13] = nf.format(records.get(i).getLEASEFEE());
                RowRec[14] = nf.format(records.get(i).getCHECKS());
                RowRec[15] = dc.format(records.get(i).getMILESTOTAL());
                RowRec[16] = nf.format(records.get(i).getGAS());
                RowRec[17] = nf.format(records.get(i).getCASHTIPS());

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
            String MEDALLION = this.medalliontxt.getText().trim();

            TaxiRecordDao txrecdao = new TaxiRecordDao();

            ArrayList<TaxiRecord> records = txrecdao.getAllRecordsByInput(EMP_ID, FNAME, LNAME, MEDALLION);

            Object[] tableColumnName = new Object[18];

            tableColumnName[0] = "Date";
            tableColumnName[1] = "H.License";
            tableColumnName[2] = "F.Name";
            tableColumnName[3] = "L.Name";
            tableColumnName[4] = "Owner ID";
            tableColumnName[5] = "Medallion";
            tableColumnName[6] = "CC.Income";
            tableColumnName[7] = "Ca.Income";
            tableColumnName[8] = "Cash Trips";
            tableColumnName[9] = "CC.trips";
            tableColumnName[10] = "Ez-Pass";
            tableColumnName[11] = "St.Srchg";
            tableColumnName[12] = "Imp.Srchg";
            tableColumnName[13] = "Lease Fee";
            tableColumnName[14] = "Checks";
            tableColumnName[15] = "Miles";
            tableColumnName[16] = "Gas";
            tableColumnName[17] = "Cash Tips";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.TaxiSummaryTable.setModel(tbd);

            if (records != null) {

                for (int i = 0; i < records.size(); i++) {
                    Object[] RowRec = new Object[18];

                    RowRec[0] = records.get(i).getDATE();
                    RowRec[1] = records.get(i).getEMP_ID();
                    RowRec[2] = records.get(i).getFNAME().toUpperCase();
                    RowRec[3] = records.get(i).getLNAME().toUpperCase();
                    RowRec[4] = records.get(i).getOWNER_ID();
                    RowRec[5] = records.get(i).getMEDALLION().toUpperCase();
                    RowRec[6] = nf.format(records.get(i).getCC_INCOME());
                    RowRec[7] = nf.format(records.get(i).getCASH_INCOME());
                    RowRec[8] = records.get(i).getCASHTRIPS();
                    RowRec[9] = records.get(i).getCCTRIPS();
                    RowRec[10] = nf.format(records.get(i).getEZPASS());
                    RowRec[11] = nf.format(records.get(i).getST_SURCHARGE());
                    RowRec[12] = nf.format(records.get(i).getIMP_SURCHARGE());
                    RowRec[13] = nf.format(records.get(i).getLEASEFEE());
                    RowRec[14] = nf.format(records.get(i).getCHECKS());
                    RowRec[15] = dc.format(records.get(i).getMILESTOTAL());
                    RowRec[16] = nf.format(records.get(i).getGAS());
                    RowRec[17] = nf.format(records.get(i).getCASHTIPS());

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

    private void searchByDate() {
        try {
            JTextField stxt = ((JTextField) startdatetxt.getDateEditor().getUiComponent());
            String sDATE = stxt.getText().trim();

            JTextField etxt = ((JTextField) enddatetxt.getDateEditor().getUiComponent());
            String eDATE = etxt.getText().trim();
            int OWNER_ID = vl.ParseInteger(this.owneridtxt.getText().trim());

            TaxiRecordDao txrecdao = new TaxiRecordDao();

            ArrayList<TaxiRecord> records = txrecdao.getAllRecordsByDate(sDATE, eDATE, OWNER_ID);

            Object[] tableColumnName = new Object[18];

            tableColumnName[0] = "Date";
            tableColumnName[1] = "H.License";
            tableColumnName[2] = "F.Name";
            tableColumnName[3] = "L.Name";
            tableColumnName[4] = "Owner ID";
            tableColumnName[5] = "Medallion";
            tableColumnName[6] = "CC.Income";
            tableColumnName[7] = "Ca.Income";
            tableColumnName[8] = "Cash Trips";
            tableColumnName[9] = "CC.trips";
            tableColumnName[10] = "Ez-Pass";
            tableColumnName[11] = "St.Srchg";
            tableColumnName[12] = "Imp.Srchg";
            tableColumnName[13] = "Lease Fee";
            tableColumnName[14] = "Checks";
            tableColumnName[15] = "Miles";
            tableColumnName[16] = "Gas";
            tableColumnName[17] = "Cash Tips";

            DefaultTableModel tbd = new DefaultTableModel();

            tbd.setColumnIdentifiers(tableColumnName);

            this.TaxiSummaryTable.setModel(tbd);

            if (records != null) {

                for (int i = 0; i < records.size(); i++) {
                    Object[] RowRec = new Object[18];

                    RowRec[0] = records.get(i).getDATE();
                    RowRec[1] = records.get(i).getEMP_ID();
                    RowRec[2] = records.get(i).getFNAME().toUpperCase();
                    RowRec[3] = records.get(i).getLNAME().toUpperCase();
                    RowRec[4] = records.get(i).getOWNER_ID();
                    RowRec[5] = records.get(i).getMEDALLION().toUpperCase();
                    RowRec[6] = nf.format(records.get(i).getCC_INCOME());
                    RowRec[7] = nf.format(records.get(i).getCASH_INCOME());
                    RowRec[8] = records.get(i).getCASHTRIPS();
                    RowRec[9] = records.get(i).getCCTRIPS();
                    RowRec[10] = nf.format(records.get(i).getEZPASS());
                    RowRec[11] = nf.format(records.get(i).getST_SURCHARGE());
                    RowRec[12] = nf.format(records.get(i).getIMP_SURCHARGE());
                    RowRec[13] = nf.format(records.get(i).getLEASEFEE());
                    RowRec[14] = nf.format(records.get(i).getCHECKS());
                    RowRec[15] = dc.format(records.get(i).getMILESTOTAL());
                    RowRec[16] = nf.format(records.get(i).getGAS());
                    RowRec[17] = nf.format(records.get(i).getCASHTIPS());

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
        JTextField stxt = ((JTextField) startdatetxt.getDateEditor().getUiComponent());
        JTextField etxt = ((JTextField) enddatetxt.getDateEditor().getUiComponent());

        try {

            if (!stxt.getText().trim().equals("")) {
                if (!etxt.getText().trim().equals("")) {
                    if (!owneridtxt.getText().trim().equals("")) {
                        this.searchByDate();
                        this.showallbtn.setEnabled(true);
                    }
                }
            } else if (!firstnametxt.getText().trim().equals("")) {
                this.searchByMultipleFields();
                this.showallbtn.setEnabled(true);

            } else if (!lastnametxt.getText().trim().equals("")) {
                this.searchByMultipleFields();
                this.showallbtn.setEnabled(true);

            } else if (!medalliontxt.getText().trim().equals("")) {
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

    }

    private void clear() {
        empidtxt.requestFocus();
        enddatetxt.setDate(null);
        firstnametxt.setText(null);
        lastnametxt.setText(null);
        medalliontxt.setText(null);
        owneridtxt.setText(null);
        startdatetxt.setDate(null);
        empidtxt.setText(null);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        usrtxt2 = new javax.swing.JTextField();
        HeaderPanel = new javax.swing.JPanel();
        LastnameLbl = new javax.swing.JLabel();
        FirstNameLbl = new javax.swing.JLabel();
        clearbtn = new javax.swing.JButton();
        medalliontxt = new javax.swing.JTextField();
        EdateLbl = new javax.swing.JLabel();
        startdatetxt = new com.toedter.calendar.JDateChooser();
        enddatetxt = new com.toedter.calendar.JDateChooser();
        SDateLbl = new javax.swing.JLabel();
        MedallionLbl = new javax.swing.JLabel();
        printbtn = new javax.swing.JButton();
        searchbtn = new javax.swing.JButton();
        firstnametxt = new javax.swing.JTextField();
        lastnametxt = new javax.swing.JTextField();
        HackLbl = new javax.swing.JLabel();
        empidtxt = new javax.swing.JTextField();
        OwnerIdLbl = new javax.swing.JLabel();
        owneridtxt = new javax.swing.JTextField();
        showallbtn = new javax.swing.JButton();
        BackToMenuBtn = new javax.swing.JButton();
        ButtonsPanel = new javax.swing.JPanel();
        UpdateBtn = new javax.swing.JButton();
        AddBtn = new javax.swing.JButton();
        totalbtn = new javax.swing.JButton();
        weeklybtn = new javax.swing.JButton();
        TableScrollPane = new javax.swing.JScrollPane();
        TaxiSummaryTable = new javax.swing.JTable();

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Hack License:");
        jLabel6.setAlignmentX(0.5F);
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        usrtxt2.setBackground(new java.awt.Color(153, 255, 255));
        usrtxt2.setText(" ");
        usrtxt2.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 255, 255)));

        setTitle("Taxi Summary");
        setToolTipText("");

        HeaderPanel.setBackground(new java.awt.Color(0, 0, 102));

        LastnameLbl.setBackground(new java.awt.Color(255, 255, 255));
        LastnameLbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LastnameLbl.setForeground(new java.awt.Color(255, 255, 255));
        LastnameLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        LastnameLbl.setText("Last Name :");
        LastnameLbl.setAlignmentX(0.5F);
        LastnameLbl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        FirstNameLbl.setBackground(new java.awt.Color(255, 255, 255));
        FirstNameLbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        FirstNameLbl.setForeground(new java.awt.Color(255, 255, 255));
        FirstNameLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        FirstNameLbl.setText("First Name :");
        FirstNameLbl.setAlignmentX(0.5F);
        FirstNameLbl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        medalliontxt.setBackground(new java.awt.Color(153, 255, 255));
        medalliontxt.setText(" ");
        medalliontxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 255, 255)));
        medalliontxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medalliontxtActionPerformed(evt);
            }
        });
        medalliontxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                medalliontxtKeyTyped(evt);
            }
        });

        EdateLbl.setBackground(new java.awt.Color(255, 255, 255));
        EdateLbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        EdateLbl.setForeground(new java.awt.Color(255, 255, 255));
        EdateLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        EdateLbl.setText("End Date :");
        EdateLbl.setAlignmentX(0.5F);
        EdateLbl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        startdatetxt.setBackground(new java.awt.Color(204, 255, 255));
        startdatetxt.setDateFormatString("M/d/yyyy");

        enddatetxt.setBackground(new java.awt.Color(204, 255, 255));
        enddatetxt.setDateFormatString("M/d/yyyy");

        SDateLbl.setBackground(new java.awt.Color(255, 255, 255));
        SDateLbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SDateLbl.setForeground(new java.awt.Color(255, 255, 255));
        SDateLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SDateLbl.setText("Start Date :");
        SDateLbl.setAlignmentX(0.5F);
        SDateLbl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        MedallionLbl.setBackground(new java.awt.Color(255, 255, 255));
        MedallionLbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        MedallionLbl.setForeground(new java.awt.Color(255, 255, 255));
        MedallionLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        MedallionLbl.setText("Medallion No :");
        MedallionLbl.setAlignmentX(0.5F);
        MedallionLbl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        firstnametxt.setBackground(new java.awt.Color(153, 255, 255));
        firstnametxt.setText(" ");
        firstnametxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 255, 255)));
        firstnametxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnametxtActionPerformed(evt);
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

        HackLbl.setBackground(new java.awt.Color(255, 255, 255));
        HackLbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        HackLbl.setForeground(new java.awt.Color(255, 255, 255));
        HackLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        HackLbl.setText("Hack License :");
        HackLbl.setAlignmentX(0.5F);
        HackLbl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        OwnerIdLbl.setBackground(new java.awt.Color(255, 255, 255));
        OwnerIdLbl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        OwnerIdLbl.setForeground(new java.awt.Color(255, 255, 255));
        OwnerIdLbl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        OwnerIdLbl.setText("Owner ID :");
        OwnerIdLbl.setAlignmentX(0.5F);
        OwnerIdLbl.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        owneridtxt.setBackground(new java.awt.Color(153, 255, 255));
        owneridtxt.setText(" ");
        owneridtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(255, 255, 255)));
        owneridtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                owneridtxtActionPerformed(evt);
            }
        });
        owneridtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                owneridtxtKeyTyped(evt);
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

        BackToMenuBtn.setText("<< Back to Main Menu");
        BackToMenuBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackToMenuBtnActionPerformed(evt);
            }
        });

        ButtonsPanel.setBackground(new java.awt.Color(0, 0, 102));

        UpdateBtn.setText("Update a record");
        UpdateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateBtnActionPerformed(evt);
            }
        });

        AddBtn.setText("Add new record");
        AddBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddBtnActionPerformed(evt);
            }
        });

        totalbtn.setText("Grand Total");
        totalbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalbtnActionPerformed(evt);
            }
        });
        totalbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                totalbtnKeyPressed(evt);
            }
        });

        weeklybtn.setText("Weekly Income");
        weeklybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weeklybtnActionPerformed(evt);
            }
        });
        weeklybtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                weeklybtnKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout ButtonsPanelLayout = new javax.swing.GroupLayout(ButtonsPanel);
        ButtonsPanel.setLayout(ButtonsPanelLayout);
        ButtonsPanelLayout.setHorizontalGroup(
            ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ButtonsPanelLayout.createSequentialGroup()
                .addGroup(ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AddBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(UpdateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(totalbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(weeklybtn, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                .addContainerGap())
        );
        ButtonsPanelLayout.setVerticalGroup(
            ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ButtonsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(weeklybtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AddBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(UpdateBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(totalbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout HeaderPanelLayout = new javax.swing.GroupLayout(HeaderPanel);
        HeaderPanel.setLayout(HeaderPanelLayout);
        HeaderPanelLayout.setHorizontalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HackLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(HeaderPanelLayout.createSequentialGroup()
                        .addComponent(BackToMenuBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(HeaderPanelLayout.createSequentialGroup()
                                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(firstnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lastnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(medalliontxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(6, 6, 6)
                                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(OwnerIdLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(SDateLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(EdateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(owneridtxt, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(enddatetxt, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(startdatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(HeaderPanelLayout.createSequentialGroup()
                                .addComponent(searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showallbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(FirstNameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(LastnameLbl, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(MedallionLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        HeaderPanelLayout.setVerticalGroup(
            HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(HeaderPanelLayout.createSequentialGroup()
                        .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(SDateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startdatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(EdateLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enddatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(owneridtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(OwnerIdLbl)))
                    .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(ButtonsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(HeaderPanelLayout.createSequentialGroup()
                            .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(HackLbl))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(FirstNameLbl)
                                .addComponent(firstnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(LastnameLbl)
                                .addComponent(lastnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(MedallionLbl)
                                .addComponent(medalliontxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(HeaderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(searchbtn)
                                .addComponent(printbtn)
                                .addComponent(clearbtn)
                                .addComponent(showallbtn)
                                .addComponent(BackToMenuBtn)))))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        TaxiSummaryTable.setAutoCreateRowSorter(true);
        TaxiSummaryTable.setModel(new javax.swing.table.DefaultTableModel(
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
        TaxiSummaryTable.setGridColor(new java.awt.Color(102, 102, 102));
        TableScrollPane.setViewportView(TaxiSummaryTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TableScrollPane)
                .addContainerGap())
            .addComponent(HeaderPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(HeaderPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TableScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lastnametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnametxtActionPerformed
        this.MainSearch();

    }//GEN-LAST:event_lastnametxtActionPerformed

    private void firstnametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnametxtActionPerformed
        this.MainSearch();

    }//GEN-LAST:event_firstnametxtActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_searchbtnActionPerformed

    private void printbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printbtnActionPerformed

        PrintData = new PrintIsLoading();
        PrintData.execute();
    }//GEN-LAST:event_printbtnActionPerformed

    

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
                            } else if (!owneridtxt.getText().trim().equals("")) {
                                this.printByDate();
                            }

                        } else if (!firstnametxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else if (!lastnametxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else if (!medalliontxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else if (!empidtxt.getText().trim().equals("")) {
                            this.printByInput();

                        } else {
                            dm.Message("Invalid entry, Please try again");
                        }

                    } catch (Exception e) {
                        System.out.println(e.toString());
                        dm.Message("An error has occurred, please try again");
                    }
                }

                private void printByDate() {
                    InputStream st = getClass().getResourceAsStream("/Reports/Taxi_Report.jrxml");

                    try {
                        JTextField stxt = ((JTextField) startdatetxt.getDateEditor().getUiComponent());
                        JTextField etxt = ((JTextField) enddatetxt.getDateEditor().getUiComponent());

                        DBConnection con = new DBConnection();
                        Connection connect = con.getConnection();

                        JasperDesign jd = JRXmlLoader.load(st);

                        String sql = "SELECT T.DATE,T.EMP_ID, E.FNAME, E.LNAME, T.WEEK,"
                                + " T.MEDALLION,T.CC_INCOME,T.CASH_INCOME,"
                                + " T.CASHTRIPS,T.CCTRIPS,T.EZPASS,T.LEASEFEE,T.CHECKS,"
                                + " T.MILESTOTAL, T.ST_SURCHARGE, T.IMP_SURCHARGE"
                                + " FROM EMPLOYEE E INNER JOIN TAXI_REC T ON E.Emp_ID = T.Emp_ID"
                                + " WHERE T.DATE BETWEEN '" + stxt.getText() + "' AND '" + etxt.getText() + "'"
                                + " AND T.OWNER_ID = " + vl.ParseInteger(owneridtxt.getText().trim()) + "";

                        JRDesignQuery newQuery = new JRDesignQuery();
                        newQuery.setText(sql);
                        jd.setQuery(newQuery);
                        JasperReport jr = JasperCompileManager.compileReport(jd);
                        JasperPrint jp = JasperFillManager.fillReport(jr, null, connect);
                        JRViewer viewer = new JRViewer(jp);
                        JFrame ReportFrame = new JFrame("Taxi Report");
                        ReportFrame.add(viewer);
                        ReportFrame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/notebook_edit.png")));
                        Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
                        ReportFrame.setSize(rec.width, rec.height);
                        ReportFrame.setVisible(true);

                    } catch (JRException | SecurityException e) {
                        System.out.println(e.toString());

                    }
                }

                private void printByInput() {
                    InputStream st = getClass().getResourceAsStream("/Reports/Taxi_Report.jrxml");

                    try {
                        DBConnection con = new DBConnection();
                        Connection connect = con.getConnection();

                        JasperDesign jd = JRXmlLoader.load(st);

                        String sql = "SELECT T.DATE,T.EMP_ID, E.FNAME, E.LNAME, T.WEEK,"
                                + " T.MEDALLION,T.CC_INCOME,T.CASH_INCOME,"
                                + " T.CASHTRIPS,T.CCTRIPS,T.EZPASS,T.LEASEFEE,T.CHECKS,"
                                + " T.MILESTOTAL, T.ST_SURCHARGE, T.IMP_SURCHARGE"
                                + " FROM EMPLOYEE E INNER JOIN TAXI_REC T ON E.Emp_ID = T.Emp_ID"
                                + " WHERE E.Emp_ID = " + vl.ParseInteger(empidtxt.getText().trim()) + ""
                                + " OR E.FNAME = '" + firstnametxt.getText() + "'"
                                + " OR E.LNAME = '" + lastnametxt.getText() + "'"
                                + " OR T.MEDALLION = '" + medalliontxt.getText() + "'";

                        JRDesignQuery newQuery = new JRDesignQuery();
                        newQuery.setText(sql);
                        jd.setQuery(newQuery);

                        JasperReport jr = JasperCompileManager.compileReport(jd);
                        JasperPrint jp = JasperFillManager.fillReport(jr, null, connect);

                        JRViewer viewer = new JRViewer(jp);
                        JFrame ReportFrame = new JFrame("Taxi Report");
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
    private void totalbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalbtnActionPerformed
        Grand_Total dialog = new Grand_Total(new javax.swing.JFrame(), true);
        dialog.setVisible(true);
    }//GEN-LAST:event_totalbtnActionPerformed

    private void weeklybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weeklybtnActionPerformed
        try {
            Weekly_Income wi = new Weekly_Income();
            Component source = (Component) evt.getSource();
            Menu main = (Menu) SwingUtilities.windowForComponent(source);
            main.MainDesktopPane.add(wi);
            wi.setMaximum(true);
            wi.show();
            this.dispose();
        } catch (PropertyVetoException e) {
            System.out.println(e.toString());
        }


    }//GEN-LAST:event_weeklybtnActionPerformed

    private void clearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearbtnActionPerformed
        this.clear();
    }//GEN-LAST:event_clearbtnActionPerformed

    private void medalliontxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medalliontxtActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_medalliontxtActionPerformed

    private void empidtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empidtxtActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_empidtxtActionPerformed

    private void owneridtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_owneridtxtActionPerformed
        this.MainSearch();
    }//GEN-LAST:event_owneridtxtActionPerformed

    private void medalliontxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_medalliontxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_medalliontxtKeyTyped

    private void empidtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empidtxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_empidtxtKeyTyped

    private void owneridtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_owneridtxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_owneridtxtKeyTyped

    private void showallbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showallbtnActionPerformed
        this.AllTaxiRecords();
        this.showallbtn.setEnabled(false);
        this.clear();
    }//GEN-LAST:event_showallbtnActionPerformed

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

    private void weeklybtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weeklybtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.weeklybtnActionPerformed(null);
        }
    }//GEN-LAST:event_weeklybtnKeyPressed

    private void totalbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.totalbtnActionPerformed(null);
        }
    }//GEN-LAST:event_totalbtnKeyPressed

    private void showallbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_showallbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.showallbtnActionPerformed(null);
        }
    }//GEN-LAST:event_showallbtnKeyPressed

    private void UpdateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateBtnActionPerformed

        Taxi_Update dialog = new Taxi_Update(new javax.swing.JFrame(), true);
        dialog.setVisible(true);
        this.showallbtn.setEnabled(true);
    }//GEN-LAST:event_UpdateBtnActionPerformed

    private void AddBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddBtnActionPerformed
        Add_Taxi dialog = new Add_Taxi(new javax.swing.JFrame(), true);
        dialog.setVisible(true);
        this.showallbtn.setEnabled(true);


    }//GEN-LAST:event_AddBtnActionPerformed

    private void BackToMenuBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackToMenuBtnActionPerformed
        Component source = (Component) evt.getSource();
        Menu main = (Menu) SwingUtilities.windowForComponent(source);
        this.dispose();
        main.EnableBtns();        
    }//GEN-LAST:event_BackToMenuBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddBtn;
    private javax.swing.JButton BackToMenuBtn;
    private javax.swing.JPanel ButtonsPanel;
    private javax.swing.JLabel EdateLbl;
    private javax.swing.JLabel FirstNameLbl;
    private javax.swing.JLabel HackLbl;
    private javax.swing.JPanel HeaderPanel;
    private javax.swing.JLabel LastnameLbl;
    private javax.swing.JLabel MedallionLbl;
    private javax.swing.JLabel OwnerIdLbl;
    private javax.swing.JLabel SDateLbl;
    private javax.swing.JScrollPane TableScrollPane;
    private javax.swing.JTable TaxiSummaryTable;
    private javax.swing.JButton UpdateBtn;
    private javax.swing.JButton clearbtn;
    private javax.swing.JTextField empidtxt;
    private com.toedter.calendar.JDateChooser enddatetxt;
    private javax.swing.JTextField firstnametxt;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField lastnametxt;
    private javax.swing.JTextField medalliontxt;
    private javax.swing.JTextField owneridtxt;
    private javax.swing.JButton printbtn;
    private javax.swing.JButton searchbtn;
    private javax.swing.JButton showallbtn;
    private com.toedter.calendar.JDateChooser startdatetxt;
    private javax.swing.JButton totalbtn;
    private javax.swing.JTextField usrtxt2;
    private javax.swing.JButton weeklybtn;
    // End of variables declaration//GEN-END:variables
}
