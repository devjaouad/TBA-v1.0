/*
 *Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package JFrames;

import DAO.TaxiRecordDao;
import DAO.Validation;
import Entities.DialogMessage;
import Entities.TaxiRecord;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Taxi_Update extends javax.swing.JDialog {

    private static final long serialVersionUID = 1L;

    Validation vl = new Validation();
    DialogMessage dm = new DialogMessage();
    NumberFormat nf = NumberFormat.getCurrencyInstance();
    DecimalFormat df = new DecimalFormat("#0.00");

    public Taxi_Update(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.clear();
        this.DataEntryValidation();
        this.NotEditable();
    }

    private void UpdateTaxi() {
        try {
            JTextField txt = ((JTextField) datechooser.getDateEditor().getUiComponent());
            String DATE = txt.getText();

            String DATE2 = this.datetxt.getText();
            int EMP_ID = vl.ParseInteger(this.empidtxt.getText());
            int OWNER_ID = vl.ParseInteger(this.owneridtxt.getText());
            int WEEK = vl.ParseInteger(this.weektxt.getText());
            String MEDALLION = this.medalliontxt.getText();
            double CC_INCOME = vl.ParseDouble(this.ccincometxt.getText());
            double CASH_INCOME = vl.ParseDouble(this.cashincometxt.getText());
            int CASHTRIPS = vl.ParseInteger(this.cashtripstxt.getText());
            int CCTRIPS = vl.ParseInteger(this.cctripstxt.getText());
            double EZPASS = vl.ParseDouble(this.ezpasstxt.getText());
            double ST_SURCHARGE = vl.ParseDouble(this.sttaxtxt.getText());
            double IMP_SURCHARGE = vl.ParseDouble(this.imptaxtxt.getText());
            double LEASEFEE = vl.ParseDouble(this.leasefeetxt.getText());
            double CHECKS = vl.ParseDouble(this.checkstxt.getText());
            double MILESTOTAL = vl.ParseDouble(this.milestxt.getText());
            double CASHTIPS = vl.ParseDouble(this.cashtipstxt.getText());
            double GAS = vl.ParseDouble(this.gaztxt.getText());

            TaxiRecord taxi = new TaxiRecord();

            taxi.setDATE(DATE2);
            taxi.setEMP_ID(EMP_ID);
            taxi.setOWNER_ID(OWNER_ID);
            taxi.setWEEK(WEEK);
            taxi.setMEDALLION(MEDALLION);
            taxi.setCC_INCOME(CC_INCOME);
            taxi.setCASH_INCOME(CASH_INCOME);
            taxi.setCASHTRIPS(CASHTRIPS);
            taxi.setCCTRIPS(CCTRIPS);
            taxi.setEZPASS(EZPASS);
            taxi.setST_SURCHARGE(ST_SURCHARGE);
            taxi.setIMP_SURCHARGE(IMP_SURCHARGE);
            taxi.setLEASEFEE(LEASEFEE);
            taxi.setCHECKS(CHECKS);
            taxi.setMILESTOTAL(MILESTOTAL);
            taxi.setGAS(GAS);
            taxi.setCASHTIPS(CASHTIPS);

            TaxiRecordDao TaxiDao = new TaxiRecordDao();

            int result = TaxiDao.updateTaxi(taxi, DATE);

            if (result != 0) {
                dm.Message("Your data has been saved!");
            } else {
                dm.Message("Your data has been rejected!");
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }

    }

    private void SearchTaxi() {

        try {

            JTextField txt = ((JTextField) datechooser.getDateEditor().getUiComponent());
            String DATE = txt.getText();

            TaxiRecordDao taxidao = new TaxiRecordDao();
            TaxiRecord taxi = taxidao.search(DATE);

            if (taxi != null) {
                datetxt.setText(taxi.getDATE());
                weektxt.setText(Integer.toString(taxi.getWEEK()));
                medalliontxt.setText(taxi.getMEDALLION());
                empidtxt.setText(Integer.toString(taxi.getEMP_ID()));
                owneridtxt.setText(Integer.toString(taxi.getOWNER_ID()));
                double cashincome = taxi.getCASH_INCOME();
                double cashtips = taxi.getCASHTIPS(); 
                double ccincome = taxi.getCC_INCOME();                
                double checks = taxi.getCHECKS();
                double ezpass = taxi.getEZPASS();
                double gas = taxi.getGAS();
                double leasefee = taxi.getLEASEFEE();
                double miles = taxi.getMILESTOTAL();
                double sttax = taxi.getST_SURCHARGE();
                double imptax = taxi.getIMP_SURCHARGE();

                //Format number with dollar sign and 2 decimal points
                cashincometxt.setText(df.format(cashincome));
                cashtipstxt.setText(df.format(cashtips));
                cashtripstxt.setText(Integer.toString(taxi.getCASHTRIPS()));
                ccincometxt.setText(df.format(ccincome));
                cctripstxt.setText(Integer.toString(taxi.getCCTRIPS()));
                checkstxt.setText(df.format(checks));
                ezpasstxt.setText(df.format(ezpass));
                gaztxt.setText(df.format(gas));
                leasefeetxt.setText(df.format(leasefee));
                milestxt.setText(df.format(miles));
                sttaxtxt.setText(df.format(sttax));
                imptaxtxt.setText(df.format(imptax));
                
                //Enable edit button
                this.editbtn.setEnabled(true);

            } else {
                this.clear();
                dm.Message("Date not found!");
                datechooser.requestFocusInWindow();
                this.NotEditable();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }

    }

    private void DeleteTaxi() {
        try {
            String DATE = this.datetxt.getText();
            TaxiRecordDao taxidao = new TaxiRecordDao();
            
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Would you like to delete this Record?", "Warning", dialogButton);
            if (dialogResult == 0) {

            taxidao.delete(DATE);
            dm.Message("The date " + DATE + " record has been deleted");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }
    }

    private void Editable() {
        cashincometxt.setEditable(true);
        cashtipstxt.setEditable(true);
        cashtripstxt.setEditable(true);
        ccincometxt.setEditable(true);
        cctripstxt.setEditable(true);
        checkstxt.setEditable(true);
        datetxt.setEditable(true);
        ezpasstxt.setEditable(true);
        gaztxt.setEditable(true);
        empidtxt.setEditable(true);
        leasefeetxt.setEditable(true);
        medalliontxt.setEditable(true);
        milestxt.setEditable(true);
        sttaxtxt.setEditable(true);
        imptaxtxt.setEditable(true);
        owneridtxt.setEditable(true);
        weektxt.setEditable(true);
        this.savebtn.setEnabled(true);
        this.deletebtn.setEnabled(true);        
        datetxt.requestFocus();
    }

    private void NotEditable() {
        cashincometxt.setEditable(false);
        cashtipstxt.setEditable(false);
        cashtripstxt.setEditable(false);
        ccincometxt.setEditable(false);
        cctripstxt.setEditable(false);
        checkstxt.setEditable(false);
        datetxt.setEditable(false);
        ezpasstxt.setEditable(false);
        gaztxt.setEditable(false);
        leasefeetxt.setEditable(false);
        medalliontxt.setEditable(false);
        milestxt.setEditable(false);
        sttaxtxt.setEditable(false);
        imptaxtxt.setEditable(false);
        owneridtxt.setEditable(false);
        weektxt.setEditable(false);
        this.savebtn.setEnabled(false);
        this.deletebtn.setEnabled(false);
        this.editbtn.setEnabled(false);

    }

    private void clear() {
        cashincometxt.setText(null);
        cashtipstxt.setText(null);
        cashtripstxt.setText(null);
        ccincometxt.setText(null);
        cctripstxt.setText(null);
        checkstxt.setText(null);
        datetxt.setText(null);
        ezpasstxt.setText(null);
        gaztxt.setText(null);
        empidtxt.setText(null);
        leasefeetxt.setText(null);
        medalliontxt.setText(null);
        milestxt.setText(null);
        sttaxtxt.setText(null);
        owneridtxt.setText(null);
        weektxt.setText(null);
        imptaxtxt.setText(null);
        datechooser.setDate(null);
    }

    private void DataEntryValidation() {
        empidtxt.setDocument(new Validation(7));
        medalliontxt.setDocument(new Validation(4));
        owneridtxt.setDocument(new Validation(3));
        weektxt.setDocument(new Validation(5));
        datetxt.setDocument(new Validation(10));
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        sttaxtxt = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        checkstxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        leasefeetxt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        milestxt = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        exitbtn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        deletebtn = new javax.swing.JButton();
        editbtn = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        gaztxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        cashtipstxt = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        weektxt = new javax.swing.JTextField();
        medalliontxt = new javax.swing.JTextField();
        empidtxt = new javax.swing.JTextField();
        owneridtxt = new javax.swing.JTextField();
        cashtripstxt = new javax.swing.JTextField();
        cashincometxt = new javax.swing.JTextField();
        cctripstxt = new javax.swing.JTextField();
        ccincometxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ezpasstxt = new javax.swing.JTextField();
        savebtn = new javax.swing.JButton();
        datetxt = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        imptaxtxt = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        searchbtn = new javax.swing.JButton();
        datechooser = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Taxi Record");
        setAlwaysOnTop(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Medallion        ");
        jLabel4.setAlignmentX(0.5F);
        jLabel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Hack License  ");
        jLabel5.setAlignmentX(0.5F);
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        sttaxtxt.setEditable(false);
        sttaxtxt.setBackground(new java.awt.Color(153, 255, 255));
        sttaxtxt.setText(" ");
        sttaxtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        sttaxtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sttaxtxtActionPerformed(evt);
            }
        });
        sttaxtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sttaxtxtKeyTyped(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Owner_ID     ");
        jLabel6.setAlignmentX(0.5F);
        jLabel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        checkstxt.setEditable(false);
        checkstxt.setBackground(new java.awt.Color(153, 255, 255));
        checkstxt.setText(" ");
        checkstxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        checkstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkstxtActionPerformed(evt);
            }
        });
        checkstxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                checkstxtKeyTyped(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("Cash Trips    ");
        jLabel7.setAlignmentX(0.5F);
        jLabel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        leasefeetxt.setEditable(false);
        leasefeetxt.setBackground(new java.awt.Color(153, 255, 255));
        leasefeetxt.setText(" ");
        leasefeetxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        leasefeetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                leasefeetxtActionPerformed(evt);
            }
        });
        leasefeetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                leasefeetxtKeyTyped(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Cash Income ");
        jLabel8.setAlignmentX(0.5F);
        jLabel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("C.C Trips      ");
        jLabel9.setAlignmentX(0.5F);
        jLabel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        milestxt.setEditable(false);
        milestxt.setBackground(new java.awt.Color(153, 255, 255));
        milestxt.setText(" ");
        milestxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        milestxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                milestxtActionPerformed(evt);
            }
        });
        milestxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                milestxtKeyTyped(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Miles");
        jLabel16.setAlignmentX(0.5F);
        jLabel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel16.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("C.C Income  ");
        jLabel10.setAlignmentX(0.5F);
        jLabel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("EZ-Pass         ");
        jLabel11.setAlignmentX(0.5F);
        jLabel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel11.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("St.Surcharge");
        jLabel13.setAlignmentX(0.5F);
        jLabel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel13.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        gaztxt.setEditable(false);
        gaztxt.setBackground(new java.awt.Color(153, 255, 255));
        gaztxt.setText(" ");
        gaztxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        gaztxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gaztxtActionPerformed(evt);
            }
        });
        gaztxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                gaztxtKeyTyped(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Lease Fee");
        jLabel14.setAlignmentX(0.5F);
        jLabel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel14.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("Gas");
        jLabel17.setAlignmentX(0.5F);
        jLabel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel17.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("Checks");
        jLabel15.setAlignmentX(0.5F);
        jLabel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel15.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        cashtipstxt.setEditable(false);
        cashtipstxt.setBackground(new java.awt.Color(153, 255, 255));
        cashtipstxt.setText(" ");
        cashtipstxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        cashtipstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashtipstxtActionPerformed(evt);
            }
        });
        cashtipstxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cashtipstxtKeyTyped(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Cash Tips");
        jLabel18.setAlignmentX(0.5F);
        jLabel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel18.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        weektxt.setEditable(false);
        weektxt.setBackground(new java.awt.Color(153, 255, 255));
        weektxt.setText(" ");
        weektxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        weektxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weektxtActionPerformed(evt);
            }
        });
        weektxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                weektxtKeyTyped(evt);
            }
        });

        medalliontxt.setEditable(false);
        medalliontxt.setBackground(new java.awt.Color(153, 255, 255));
        medalliontxt.setText(" ");
        medalliontxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
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

        owneridtxt.setEditable(false);
        owneridtxt.setBackground(new java.awt.Color(153, 255, 255));
        owneridtxt.setText(" ");
        owneridtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
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

        cashtripstxt.setEditable(false);
        cashtripstxt.setBackground(new java.awt.Color(153, 255, 255));
        cashtripstxt.setText(" ");
        cashtripstxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        cashtripstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashtripstxtActionPerformed(evt);
            }
        });
        cashtripstxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cashtripstxtKeyTyped(evt);
            }
        });

        cashincometxt.setEditable(false);
        cashincometxt.setBackground(new java.awt.Color(153, 255, 255));
        cashincometxt.setText(" ");
        cashincometxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        cashincometxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashincometxtActionPerformed(evt);
            }
        });
        cashincometxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cashincometxtKeyTyped(evt);
            }
        });

        cctripstxt.setEditable(false);
        cctripstxt.setBackground(new java.awt.Color(153, 255, 255));
        cctripstxt.setText(" ");
        cctripstxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        cctripstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cctripstxtActionPerformed(evt);
            }
        });
        cctripstxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cctripstxtKeyTyped(evt);
            }
        });

        ccincometxt.setEditable(false);
        ccincometxt.setBackground(new java.awt.Color(153, 255, 255));
        ccincometxt.setText(" ");
        ccincometxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        ccincometxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ccincometxtActionPerformed(evt);
            }
        });
        ccincometxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ccincometxtKeyTyped(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Week            ");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        ezpasstxt.setEditable(false);
        ezpasstxt.setBackground(new java.awt.Color(153, 255, 255));
        ezpasstxt.setText(" ");
        ezpasstxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        ezpasstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ezpasstxtActionPerformed(evt);
            }
        });
        ezpasstxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ezpasstxtKeyTyped(evt);
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

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("Date              ");
        jLabel19.setAlignmentX(0.5F);
        jLabel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel19.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel20.setBackground(new java.awt.Color(255, 255, 255));
        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Imp.Surcharge");
        jLabel20.setAlignmentX(0.5F);
        jLabel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel20.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        imptaxtxt.setEditable(false);
        imptaxtxt.setBackground(new java.awt.Color(153, 255, 255));
        imptaxtxt.setText(" ");
        imptaxtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        imptaxtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imptaxtxtActionPerformed(evt);
            }
        });
        imptaxtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                imptaxtxtKeyTyped(evt);
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
                        .addComponent(savebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ezpasstxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(ccincometxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cctripstxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cashincometxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(cashtripstxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(owneridtxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(empidtxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(medalliontxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(weektxt, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(datetxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cashtipstxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(checkstxt)
                                .addComponent(leasefeetxt)
                                .addComponent(milestxt)
                                .addComponent(gaztxt)
                                .addComponent(sttaxtxt)
                                .addComponent(imptaxtxt)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weektxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(medalliontxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(owneridtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cashtripstxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cashincometxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cctripstxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ccincometxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ezpasstxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sttaxtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imptaxtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(leasefeetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkstxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(milestxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gaztxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cashtipstxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exitbtn)
                    .addComponent(deletebtn)
                    .addComponent(editbtn)
                    .addComponent(savebtn))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Lucida Calligraphy", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Taxi Income");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Search by date :");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        searchbtn.setText("Search");
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });

        datechooser.setDateFormatString("yyyy-MM-dd");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(88, 88, 88))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(datechooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(datechooser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchbtn)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void weektxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weektxtActionPerformed
        weektxt.transferFocus();
        medalliontxt.selectAll();
    }//GEN-LAST:event_weektxtActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        this.DeleteTaxi();
        this.clear();
    }//GEN-LAST:event_deletebtnActionPerformed

    private void savebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebtnActionPerformed

        if (datetxt.getText().trim().equals("") || empidtxt.getText().trim().equals("")
                || owneridtxt.getText().trim().equals("") || weektxt.getText().trim().equals("")
                || medalliontxt.getText().trim().equals("")) {
            dm.Message("Invalid Entry, please try again!");
            datechooser.requestFocusInWindow();

        } else {
            this.UpdateTaxi();
            this.NotEditable();
            this.clear();
            datechooser.requestFocusInWindow();
        }


    }//GEN-LAST:event_savebtnActionPerformed

    private void editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbtnActionPerformed
        this.Editable();
        datetxt.selectAll();
    }//GEN-LAST:event_editbtnActionPerformed

    private void datetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_datetxtActionPerformed
        datetxt.transferFocus();
        weektxt.selectAll();
    }//GEN-LAST:event_datetxtActionPerformed

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitbtnActionPerformed

    private void medalliontxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medalliontxtActionPerformed
        medalliontxt.transferFocus();
        empidtxt.selectAll();
    }//GEN-LAST:event_medalliontxtActionPerformed

    private void empidtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empidtxtActionPerformed
        empidtxt.transferFocus();
        owneridtxt.selectAll();
    }//GEN-LAST:event_empidtxtActionPerformed

    private void owneridtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_owneridtxtActionPerformed
        owneridtxt.transferFocus();
        cashtripstxt.selectAll();
    }//GEN-LAST:event_owneridtxtActionPerformed

    private void cashtripstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashtripstxtActionPerformed
        cashtripstxt.transferFocus();
        cashincometxt.selectAll();
    }//GEN-LAST:event_cashtripstxtActionPerformed

    private void cashincometxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashincometxtActionPerformed
        cashincometxt.transferFocus();
        cctripstxt.selectAll();
    }//GEN-LAST:event_cashincometxtActionPerformed

    private void cctripstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cctripstxtActionPerformed
        cctripstxt.transferFocus();
        ccincometxt.selectAll();
    }//GEN-LAST:event_cctripstxtActionPerformed

    private void ccincometxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ccincometxtActionPerformed
        ccincometxt.transferFocus();
        ezpasstxt.selectAll();
    }//GEN-LAST:event_ccincometxtActionPerformed

    private void ezpasstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ezpasstxtActionPerformed
        ezpasstxt.transferFocus();
        sttaxtxt.selectAll();
    }//GEN-LAST:event_ezpasstxtActionPerformed

    private void sttaxtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sttaxtxtActionPerformed
        sttaxtxt.transferFocus();
        imptaxtxt.selectAll();
    }//GEN-LAST:event_sttaxtxtActionPerformed

    private void leasefeetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_leasefeetxtActionPerformed
        leasefeetxt.transferFocus();
        checkstxt.selectAll();
    }//GEN-LAST:event_leasefeetxtActionPerformed

    private void checkstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkstxtActionPerformed
        checkstxt.transferFocus();
        milestxt.selectAll();
    }//GEN-LAST:event_checkstxtActionPerformed

    private void milestxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_milestxtActionPerformed
        milestxt.transferFocus();
        gaztxt.selectAll();

    }//GEN-LAST:event_milestxtActionPerformed

    private void gaztxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gaztxtActionPerformed
        gaztxt.transferFocus();
        cashtipstxt.selectAll();

    }//GEN-LAST:event_gaztxtActionPerformed

    private void cashtipstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashtipstxtActionPerformed
        cashtipstxt.transferFocus();
        imptaxtxt.selectAll();

    }//GEN-LAST:event_cashtipstxtActionPerformed

    private void imptaxtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imptaxtxtActionPerformed
        imptaxtxt.transferFocus();
        leasefeetxt.selectAll();
    }//GEN-LAST:event_imptaxtxtActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        JTextField txt = ((JTextField) datechooser.getDateEditor().getUiComponent());
        String DATE = txt.getText();
        if (DATE.trim().equals("")) {
            dm.Message("Invalid Entry, please try again!");
            datechooser.requestFocusInWindow();

        } else {
            this.SearchTaxi();
        }
    }//GEN-LAST:event_searchbtnActionPerformed

    private void datetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_datetxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_datetxtKeyTyped

    private void weektxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_weektxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_weektxtKeyTyped

    private void medalliontxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_medalliontxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_medalliontxtKeyTyped

    private void empidtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empidtxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_empidtxtKeyTyped

    private void owneridtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_owneridtxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_owneridtxtKeyTyped

    private void cashtripstxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashtripstxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_cashtripstxtKeyTyped

    private void cashincometxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashincometxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_cashincometxtKeyTyped

    private void cctripstxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cctripstxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_cctripstxtKeyTyped

    private void ccincometxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ccincometxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_ccincometxtKeyTyped

    private void ezpasstxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ezpasstxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_ezpasstxtKeyTyped

    private void sttaxtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sttaxtxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_sttaxtxtKeyTyped

    private void imptaxtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imptaxtxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_imptaxtxtKeyTyped

    private void leasefeetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_leasefeetxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_leasefeetxtKeyTyped

    private void checkstxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_checkstxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_checkstxtKeyTyped

    private void milestxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_milestxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_milestxtKeyTyped

    private void gaztxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gaztxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_gaztxtKeyTyped

    private void cashtipstxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cashtipstxtKeyTyped
        vl.doubleOnly(evt);
    }//GEN-LAST:event_cashtipstxtKeyTyped

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
            java.util.logging.Logger.getLogger(Taxi_Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Taxi_Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Taxi_Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Taxi_Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Taxi_Update dialog = new Taxi_Update(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField cashincometxt;
    private javax.swing.JTextField cashtipstxt;
    private javax.swing.JTextField cashtripstxt;
    private javax.swing.JTextField ccincometxt;
    private javax.swing.JTextField cctripstxt;
    private javax.swing.JTextField checkstxt;
    private com.toedter.calendar.JDateChooser datechooser;
    private javax.swing.JTextField datetxt;
    private javax.swing.JButton deletebtn;
    private javax.swing.JButton editbtn;
    private javax.swing.JTextField empidtxt;
    private javax.swing.JButton exitbtn;
    private javax.swing.JTextField ezpasstxt;
    private javax.swing.JTextField gaztxt;
    private javax.swing.JTextField imptaxtxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField leasefeetxt;
    private javax.swing.JTextField medalliontxt;
    private javax.swing.JTextField milestxt;
    private javax.swing.JTextField owneridtxt;
    private javax.swing.JButton savebtn;
    private javax.swing.JButton searchbtn;
    private javax.swing.JTextField sttaxtxt;
    private javax.swing.JTextField weektxt;
    // End of variables declaration//GEN-END:variables
}
