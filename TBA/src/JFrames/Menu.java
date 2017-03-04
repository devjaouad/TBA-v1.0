/*
 *Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package JFrames;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.SwingWorker;

public class Menu extends javax.swing.JFrame implements ActionListener, KeyListener, WindowListener {

    private static final long serialVersionUID = 1L;

    PleaseWait d = new PleaseWait(new javax.swing.JFrame(), true);
    private LoadTaxiData taxi;
    private LoadBillsData bills;
    private LoadTicketsData ticket;

    public Menu() {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            initComponents();
            this.PanelForPane.setEnabled(false); 
            this.MainMenuLogo();            
            this.setIcon();                       
            Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
            this.setSize(rec.width, rec.height);
            setLocationRelativeTo(null);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);

        }
        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        if (defaults.get("Table.alternateRowColor") == null) {
            defaults.put("Table.alternateRowColor", new Color(206, 250, 249));
        }

    }

    public void MainMenuLogo() {
        try {
            Welcome wel = new Welcome();
            MainDesktopPane.add(wel);
            wel.setMaximum(true);            
            wel.show();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    private void setIcon() {
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/notebook_edit.png")));
    }

    private class LoadTaxiData extends SwingWorker<Void, Void> {

        @Override
        @SuppressWarnings("Convert2Lambda")
        public Void doInBackground() throws InterruptedException {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {

                        d.setVisible(true);

                        Taxi_Summary ts = new Taxi_Summary();                        
                        MainDesktopPane.add(ts);
                        ts.setMaximum(true);
                        
                        ts.show();
                         
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
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

    private class LoadBillsData extends SwingWorker<Void, Void> {

        @Override
        @SuppressWarnings("Convert2Lambda")
        public Void doInBackground() throws InterruptedException {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        d.setVisible(true);
                        //new Bills_Summary().setVisible(true);
                        Bills_Summary bs = new Bills_Summary();                        
                        MainDesktopPane.add(bs);
                        bs.setMaximum(true);
                        
                        bs.show();
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
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

    private class LoadTicketsData extends SwingWorker<Void, Void> {

        @Override
        @SuppressWarnings("Convert2Lambda")
        public Void doInBackground() throws InterruptedException {

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        d.setVisible(true);
                        Ticket_Summary ts = new Ticket_Summary();                        
                        MainDesktopPane.add(ts);
                        ts.setMaximum(true);
                        ts.show();
                    } catch (PropertyVetoException ex) {
                        Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
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
    public void DisableBtns(){
        this.taxirecordBtn.setEnabled(false);
        this.billsrecordBtn.setEnabled(false);
        this.violationBtn.setEnabled(false);
    }
     public void EnableBtns(){
        this.taxirecordBtn.setEnabled(true);
        this.billsrecordBtn.setEnabled(true);
        this.violationBtn.setEnabled(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainPanel = new javax.swing.JPanel();
        CopyRightLbl = new javax.swing.JLabel();
        TaxiPanel = new javax.swing.JPanel();
        addtaxiBtn = new javax.swing.JButton();
        updatetaxiBtn = new javax.swing.JButton();
        taxirecordBtn = new javax.swing.JButton();
        BillsPanel = new javax.swing.JPanel();
        addBillsBtn = new javax.swing.JButton();
        updatebillsBtn = new javax.swing.JButton();
        billsrecordBtn = new javax.swing.JButton();
        RegPanel = new javax.swing.JPanel();
        registrationBtn = new javax.swing.JButton();
        employeeBtn = new javax.swing.JButton();
        ownerBtn = new javax.swing.JButton();
        ViolationPanel = new javax.swing.JPanel();
        violationBtn = new javax.swing.JButton();
        bankBtn = new javax.swing.JButton();
        AboutBtn = new javax.swing.JButton();
        exit = new javax.swing.JButton();
        PanelForPane = new javax.swing.JPanel();
        MainDesktopPane = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TBA 1.0");
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(153, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setLocationByPlatform(true);
        setName("MFrame"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1240, 600));
        setResizable(false);
        setState(2);
        addWindowListener(this);

        MainPanel.setBackground(new java.awt.Color(255, 204, 0));
        MainPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 204), 1, true));
        MainPanel.setForeground(new java.awt.Color(255, 255, 255));
        MainPanel.setAlignmentX(0.0F);
        MainPanel.setAlignmentY(0.0F);

        CopyRightLbl.setText("<html><u>Copyright © 2015 M.Jaouad</u></html>");

        TaxiPanel.setBackground(new java.awt.Color(255, 204, 0));
        TaxiPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Taxi"));

        addtaxiBtn.setText("Add Taxi Income");
        addtaxiBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        addtaxiBtn.addActionListener(this);
        addtaxiBtn.addKeyListener(this);

        updatetaxiBtn.setText("Update Taxi Income");
        updatetaxiBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        updatetaxiBtn.addActionListener(this);
        updatetaxiBtn.addKeyListener(this);

        taxirecordBtn.setText("Taxi Details");
        taxirecordBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        taxirecordBtn.addActionListener(this);
        taxirecordBtn.addKeyListener(this);

        javax.swing.GroupLayout TaxiPanelLayout = new javax.swing.GroupLayout(TaxiPanel);
        TaxiPanel.setLayout(TaxiPanelLayout);
        TaxiPanelLayout.setHorizontalGroup(
            TaxiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TaxiPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TaxiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addtaxiBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updatetaxiBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                    .addComponent(taxirecordBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        TaxiPanelLayout.setVerticalGroup(
            TaxiPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TaxiPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addtaxiBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updatetaxiBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(taxirecordBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        BillsPanel.setBackground(new java.awt.Color(255, 204, 0));
        BillsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Bills"));

        addBillsBtn.setText("Add Bills");
        addBillsBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        addBillsBtn.addActionListener(this);
        addBillsBtn.addKeyListener(this);

        updatebillsBtn.setText("Update Bills");
        updatebillsBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        updatebillsBtn.addActionListener(this);
        updatebillsBtn.addKeyListener(this);

        billsrecordBtn.setText("Bills Details");
        billsrecordBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        billsrecordBtn.addActionListener(this);
        billsrecordBtn.addKeyListener(this);

        javax.swing.GroupLayout BillsPanelLayout = new javax.swing.GroupLayout(BillsPanel);
        BillsPanel.setLayout(BillsPanelLayout);
        BillsPanelLayout.setHorizontalGroup(
            BillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BillsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(BillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addBillsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updatebillsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(billsrecordBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        BillsPanelLayout.setVerticalGroup(
            BillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BillsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addBillsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updatebillsBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(billsrecordBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        RegPanel.setBackground(new java.awt.Color(255, 204, 0));
        RegPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Registration"));

        registrationBtn.setText("Registration");
        registrationBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        registrationBtn.addActionListener(this);
        registrationBtn.addKeyListener(this);

        employeeBtn.setText("Employees");
        employeeBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        employeeBtn.addActionListener(this);
        employeeBtn.addKeyListener(this);

        ownerBtn.setText("Owners");
        ownerBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        ownerBtn.addActionListener(this);
        ownerBtn.addKeyListener(this);

        javax.swing.GroupLayout RegPanelLayout = new javax.swing.GroupLayout(RegPanel);
        RegPanel.setLayout(RegPanelLayout);
        RegPanelLayout.setHorizontalGroup(
            RegPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(RegPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registrationBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(employeeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ownerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        RegPanelLayout.setVerticalGroup(
            RegPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RegPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registrationBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employeeBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ownerBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        ViolationPanel.setBackground(new java.awt.Color(255, 204, 0));
        ViolationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Other's"));

        violationBtn.setText("Violations");
        violationBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        violationBtn.addActionListener(this);
        violationBtn.addKeyListener(this);

        bankBtn.setText("Bank Account");
        bankBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        bankBtn.addActionListener(this);
        bankBtn.addKeyListener(this);

        AboutBtn.setText("About");
        AboutBtn.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        AboutBtn.addActionListener(this);
        AboutBtn.addKeyListener(this);

        exit.setForeground(new java.awt.Color(204, 0, 0));
        exit.setText("Log out");
        exit.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        exit.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        exit.addActionListener(this);
        exit.addKeyListener(this);

        javax.swing.GroupLayout ViolationPanelLayout = new javax.swing.GroupLayout(ViolationPanel);
        ViolationPanel.setLayout(ViolationPanelLayout);
        ViolationPanelLayout.setHorizontalGroup(
            ViolationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViolationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ViolationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(violationBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bankBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AboutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ViolationPanelLayout.setVerticalGroup(
            ViolationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ViolationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(violationBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bankBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AboutBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exit, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout MainPanelLayout = new javax.swing.GroupLayout(MainPanel);
        MainPanel.setLayout(MainPanelLayout);
        MainPanelLayout.setHorizontalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(RegPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BillsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TaxiPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ViolationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(CopyRightLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MainPanelLayout.setVerticalGroup(
            MainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(MainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(RegPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TaxiPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BillsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ViolationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(32, 32, 32)
                .addComponent(CopyRightLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );

        PanelForPane.setBackground(new java.awt.Color(255, 204, 0));
        PanelForPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 204)));

        MainDesktopPane.setBackground(new java.awt.Color(255, 204, 0));

        javax.swing.GroupLayout MainDesktopPaneLayout = new javax.swing.GroupLayout(MainDesktopPane);
        MainDesktopPane.setLayout(MainDesktopPaneLayout);
        MainDesktopPaneLayout.setHorizontalGroup(
            MainDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1037, Short.MAX_VALUE)
        );
        MainDesktopPaneLayout.setVerticalGroup(
            MainDesktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout PanelForPaneLayout = new javax.swing.GroupLayout(PanelForPane);
        PanelForPane.setLayout(PanelForPaneLayout);
        PanelForPaneLayout.setHorizontalGroup(
            PanelForPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainDesktopPane)
        );
        PanelForPaneLayout.setVerticalGroup(
            PanelForPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(MainDesktopPane)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(MainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelForPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelForPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == addtaxiBtn) {
            Menu.this.addtaxiBtnActionPerformed(evt);
        }
        else if (evt.getSource() == updatetaxiBtn) {
            Menu.this.updatetaxiBtnActionPerformed(evt);
        }
        else if (evt.getSource() == taxirecordBtn) {
            Menu.this.taxirecordBtnActionPerformed(evt);
        }
        else if (evt.getSource() == addBillsBtn) {
            Menu.this.addBillsBtnActionPerformed(evt);
        }
        else if (evt.getSource() == updatebillsBtn) {
            Menu.this.updatebillsBtnActionPerformed(evt);
        }
        else if (evt.getSource() == billsrecordBtn) {
            Menu.this.billsrecordBtnActionPerformed(evt);
        }
        else if (evt.getSource() == registrationBtn) {
            Menu.this.registrationBtnActionPerformed(evt);
        }
        else if (evt.getSource() == employeeBtn) {
            Menu.this.employeeBtnActionPerformed(evt);
        }
        else if (evt.getSource() == ownerBtn) {
            Menu.this.ownerBtnActionPerformed(evt);
        }
        else if (evt.getSource() == violationBtn) {
            Menu.this.violationBtnActionPerformed(evt);
        }
        else if (evt.getSource() == bankBtn) {
            Menu.this.bankBtnActionPerformed(evt);
        }
        else if (evt.getSource() == AboutBtn) {
            Menu.this.AboutBtnActionPerformed(evt);
        }
        else if (evt.getSource() == exit) {
            Menu.this.exitActionPerformed(evt);
        }
    }

    public void keyPressed(java.awt.event.KeyEvent evt) {
        if (evt.getSource() == addtaxiBtn) {
            Menu.this.addtaxiBtnKeyPressed(evt);
        }
        else if (evt.getSource() == updatetaxiBtn) {
            Menu.this.updatetaxiBtnKeyPressed(evt);
        }
        else if (evt.getSource() == taxirecordBtn) {
            Menu.this.taxirecordBtnKeyPressed(evt);
        }
        else if (evt.getSource() == addBillsBtn) {
            Menu.this.addBillsBtnKeyPressed(evt);
        }
        else if (evt.getSource() == updatebillsBtn) {
            Menu.this.updatebillsBtnKeyPressed(evt);
        }
        else if (evt.getSource() == billsrecordBtn) {
            Menu.this.billsrecordBtnKeyPressed(evt);
        }
        else if (evt.getSource() == registrationBtn) {
            Menu.this.registrationBtnKeyPressed(evt);
        }
        else if (evt.getSource() == employeeBtn) {
            Menu.this.employeeBtnKeyPressed(evt);
        }
        else if (evt.getSource() == ownerBtn) {
            Menu.this.ownerBtnKeyPressed(evt);
        }
        else if (evt.getSource() == violationBtn) {
            Menu.this.violationBtnKeyPressed(evt);
        }
        else if (evt.getSource() == bankBtn) {
            Menu.this.bankBtnKeyPressed(evt);
        }
        else if (evt.getSource() == AboutBtn) {
            Menu.this.AboutBtnKeyPressed(evt);
        }
        else if (evt.getSource() == exit) {
            Menu.this.exitKeyPressed(evt);
        }
    }

    public void keyReleased(java.awt.event.KeyEvent evt) {
    }

    public void keyTyped(java.awt.event.KeyEvent evt) {
    }

    public void windowActivated(java.awt.event.WindowEvent evt) {
    }

    public void windowClosed(java.awt.event.WindowEvent evt) {
    }

    public void windowClosing(java.awt.event.WindowEvent evt) {
    }

    public void windowDeactivated(java.awt.event.WindowEvent evt) {
    }

    public void windowDeiconified(java.awt.event.WindowEvent evt) {
    }

    public void windowIconified(java.awt.event.WindowEvent evt) {
    }

    public void windowOpened(java.awt.event.WindowEvent evt) {
        if (evt.getSource() == Menu.this) {
            Menu.this.MAX(evt);
        }
    }// </editor-fold>//GEN-END:initComponents

    private void addtaxiBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addtaxiBtnActionPerformed
        Add_Taxi dialog = new Add_Taxi(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_addtaxiBtnActionPerformed

    private void MAX(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_MAX

    }//GEN-LAST:event_MAX

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        this.dispose();
        Login dialog = new Login(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_exitActionPerformed

    private void registrationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrationBtnActionPerformed
        Registration dialog = new Registration(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_registrationBtnActionPerformed

    private void updatetaxiBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatetaxiBtnActionPerformed
        Taxi_Update_Login dialog = new Taxi_Update_Login(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_updatetaxiBtnActionPerformed

    private void addBillsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBillsBtnActionPerformed
        Add_Bills dialog = new Add_Bills(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_addBillsBtnActionPerformed

    private void updatebillsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatebillsBtnActionPerformed
        Bills_Update_Login dialog = new Bills_Update_Login(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_updatebillsBtnActionPerformed

    private void taxirecordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_taxirecordBtnActionPerformed
        taxi = new LoadTaxiData();
        taxi.execute();
        this.DisableBtns();
        
        
    }//GEN-LAST:event_taxirecordBtnActionPerformed

    private void billsrecordBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billsrecordBtnActionPerformed
        bills = new LoadBillsData();
        bills.execute();
        this.DisableBtns();

    }//GEN-LAST:event_billsrecordBtnActionPerformed

    private void employeeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employeeBtnActionPerformed
        Employee_Info dialog = new Employee_Info(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_employeeBtnActionPerformed

    private void ownerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ownerBtnActionPerformed
        Owner_Info dialog = new Owner_Info(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_ownerBtnActionPerformed

    private void violationBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_violationBtnActionPerformed
        ticket = new LoadTicketsData();
        ticket.execute();
        this.DisableBtns();
    }//GEN-LAST:event_violationBtnActionPerformed

    private void bankBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bankBtnActionPerformed
        Bank_Account dialog = new Bank_Account(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_bankBtnActionPerformed

    private void registrationBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_registrationBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.registrationBtnActionPerformed(null);
        }
    }//GEN-LAST:event_registrationBtnKeyPressed

    private void addtaxiBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addtaxiBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.addtaxiBtnActionPerformed(null);
        }
    }//GEN-LAST:event_addtaxiBtnKeyPressed

    private void updatetaxiBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_updatetaxiBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.updatetaxiBtnActionPerformed(null);
        }
    }//GEN-LAST:event_updatetaxiBtnKeyPressed

    private void addBillsBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addBillsBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.addBillsBtnActionPerformed(null);
        }
    }//GEN-LAST:event_addBillsBtnKeyPressed

    private void updatebillsBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_updatebillsBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.updatebillsBtnActionPerformed(null);
        }
    }//GEN-LAST:event_updatebillsBtnKeyPressed

    private void taxirecordBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_taxirecordBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.taxirecordBtnActionPerformed(null);
        }
    }//GEN-LAST:event_taxirecordBtnKeyPressed

    private void billsrecordBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_billsrecordBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.billsrecordBtnActionPerformed(null);
        }
    }//GEN-LAST:event_billsrecordBtnKeyPressed

    private void employeeBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_employeeBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.employeeBtnActionPerformed(null);
        }
    }//GEN-LAST:event_employeeBtnKeyPressed

    private void ownerBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ownerBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.ownerBtnActionPerformed(null);
        }
    }//GEN-LAST:event_ownerBtnKeyPressed

    private void violationBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_violationBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.violationBtnActionPerformed(null);
        }
    }//GEN-LAST:event_violationBtnKeyPressed

    private void bankBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_bankBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.bankBtnActionPerformed(null);
        }
    }//GEN-LAST:event_bankBtnKeyPressed

    private void exitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_exitKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.exitActionPerformed(null);
        }
    }//GEN-LAST:event_exitKeyPressed

    private void AboutBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutBtnActionPerformed
        About dialog = new About(this, true);
        dialog.setVisible(true);
    }//GEN-LAST:event_AboutBtnActionPerformed

    private void AboutBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AboutBtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.AboutBtnActionPerformed(null);
        }
    }//GEN-LAST:event_AboutBtnKeyPressed
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
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Menu().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AboutBtn;
    private javax.swing.JPanel BillsPanel;
    private javax.swing.JLabel CopyRightLbl;
    public javax.swing.JDesktopPane MainDesktopPane;
    private javax.swing.JPanel MainPanel;
    public javax.swing.JPanel PanelForPane;
    private javax.swing.JPanel RegPanel;
    private javax.swing.JPanel TaxiPanel;
    private javax.swing.JPanel ViolationPanel;
    private javax.swing.JButton addBillsBtn;
    private javax.swing.JButton addtaxiBtn;
    private javax.swing.JButton bankBtn;
    private javax.swing.JButton billsrecordBtn;
    private javax.swing.JButton employeeBtn;
    private javax.swing.JButton exit;
    private javax.swing.JButton ownerBtn;
    private javax.swing.JButton registrationBtn;
    private javax.swing.JButton taxirecordBtn;
    private javax.swing.JButton updatebillsBtn;
    private javax.swing.JButton updatetaxiBtn;
    private javax.swing.JButton violationBtn;
    // End of variables declaration//GEN-END:variables
}
