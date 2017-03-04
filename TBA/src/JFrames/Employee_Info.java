/*
 *Developer: Jaouad Mouaou
 *Date: 12/09/2015
 *Application: TBA v1.0
 */
package JFrames;

import DAO.EmployeeRecordDao;
import DAO.Validation;
import Entities.DialogMessage;
import Entities.EmployeeRecord;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Employee_Info extends javax.swing.JDialog {

    Validation vl = new Validation();
    DialogMessage dm = new DialogMessage();
    String filename = null;
    byte[] EMP_IMG_UPDATED;
    byte[] EMP_IMG_NOT_UPDATED;
    boolean success;
    String encodedImage;
    BufferedImage bufferedImage;

    public Employee_Info(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.clear();
        this.DataEntryValidation();
        this.NotEditable();

    }

    private void SearchEmployee() {
        try {
            int EMP_ID = Integer.parseInt(this.empidtxt.getText());
            EmployeeRecordDao empdao = new EmployeeRecordDao();
            EmployeeRecord emp = empdao.search(EMP_ID);

            if (emp != null) {
                firstnametxt.setText(emp.getFNAME().toUpperCase());
                lastnametxt.setText(emp.getLNAME().toUpperCase());
                addresstxt.setText(emp.getADDRESS().toUpperCase());
                citytxt.setText(emp.getCITY().toUpperCase());
                statetxt.setText(emp.getSTATE().toUpperCase());
                zipcodetxt.setText(Integer.toString(emp.getZCODE()));
                phonetxt.setText(emp.getPHONE_N());
                regdatetxt.setText(emp.getREG_DATE());

                Image img;
                img = getToolkit().createImage((emp.getEMP_IMG()));
                //This will get the image from database and convert it to BufferedImage
                //Then convert it again to Byte[] to be able to store it again in DB
                //if the image not updated;
                bufferedImage = ImageIO.read(new ByteArrayInputStream(emp.getEMP_IMG()));
                try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                    ImageIO.write(bufferedImage, "jpg", bos);
                    bos.flush();
                    EMP_IMG_NOT_UPDATED = bos.toByteArray();
                    bos.close();
                }
                //Get the image and display it in the label
                ImageIcon ii = new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                AttImglbl.setIcon(ii);

                //Enable edit Button after an emplyee has been found...
                this.editbtn.setEnabled(true);

            } else {
                dm.Message("Employee not found!");
                this.NotEditable();
                this.clear();
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }

        empidtxt.requestFocus(true);

    }

    private void DeleteEmployee() {
        try {
            int EMP_ID = Integer.parseInt(this.empidtxt.getText());
            EmployeeRecordDao empdao = new EmployeeRecordDao();

            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this Employee?", "Warning", dialogButton);

            if (dialogResult == 0) {//0 = Yes
                success = empdao.delete(EMP_ID);
                if (success) {
                    this.clear();
                    dm.Message("The Employee ID: " + EMP_ID + " record has been deleted");
                } else {
                    dm.Message("Cannot delete this Employee, make sure he doesn't have any related records!");
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }
        empidtxt.requestFocus(true);
    }

    private void UpdateEmployee() throws Exception {
        try {

            int EMP_ID = Integer.parseInt(this.empidtxt.getText());

            String FNAME = this.firstnametxt.getText().toUpperCase();
            String LNAME = this.lastnametxt.getText().toUpperCase();
            String ADDRESS = this.addresstxt.getText().toUpperCase();
            String CITY = this.citytxt.getText().toUpperCase();
            String STATE = this.statetxt.getText().toUpperCase();
            int ZCODE = Integer.parseInt(this.zipcodetxt.getText());
            String PHONE_N = this.phonetxt.getText();

            EmployeeRecord emp = new EmployeeRecord();

            emp.setFNAME(FNAME);
            emp.setLNAME(LNAME);
            emp.setADDRESS(ADDRESS);
            emp.setADDRESS(ADDRESS);
            emp.setCITY(CITY);
            emp.setSTATE(STATE);
            emp.setZCODE(ZCODE);
            emp.setPHONE_N(PHONE_N);
            if (filename != null) {
                emp.setEMP_IMG(EMP_IMG_UPDATED);
                filename = null;
            } else {
                emp.setEMP_IMG(EMP_IMG_NOT_UPDATED);
            }

            EmployeeRecordDao empdao = new EmployeeRecordDao();

            int result = empdao.UpdateEmployee(emp, EMP_ID);

            if (result != 0) {
                dm.Message("Your data has been saved!");
            } else {
                dm.Message("Your data has been rejected!");
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
            dm.Message("An error has occurred, please try again");
        }

        empidtxt.requestFocus();

    }

    @SuppressWarnings("NestedAssignment")
    private void AttachImage() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(attachmentbtn.getParent());
            File f = chooser.getSelectedFile();

            if (f != null) {
                filename = f.getAbsolutePath();

                Image img;
                img = getToolkit().createImage(filename);
                ImageIcon ii = new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                AttImglbl.setIcon(ii);

                File image = new File(filename);
                FileInputStream fis = new FileInputStream(image);
                try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                    byte[] buf = new byte[1024];
                    for (int readNum; (readNum = fis.read(buf)) != -1;) {
                        bos.write(buf, 0, readNum);
                        bos.flush();
                    }

                    EMP_IMG_UPDATED = bos.toByteArray();
                    bos.close();

                }
            }

        } catch (HeadlessException | IOException e) {
            System.out.println(e.toString());
        }

    }

    private void DataEntryValidation() {
        zipcodetxt.setDocument(new Validation(5));//only 5 numbers are allowed
        empidtxt.setDocument(new Validation(7));
        statetxt.setDocument(new Validation(2));
        phonetxt.setDocument(new Validation(10));
        citytxt.setDocument(new Validation(20));

    }

    private void Editable() {
        firstnametxt.setEditable(true);
        lastnametxt.setEditable(true);
        addresstxt.setEditable(true);
        citytxt.setEditable(true);
        statetxt.setEditable(true);
        zipcodetxt.setEditable(true);
        phonetxt.setEditable(true);
        firstnametxt.requestFocus(true);
        attachmentbtn.setEnabled(true);
        savebtn.setEnabled(true);
        deletebtn.setEnabled(true);
        firstnametxt.requestFocus();
    }

    private void NotEditable() {
        firstnametxt.setEditable(false);
        lastnametxt.setEditable(false);
        addresstxt.setEditable(false);
        citytxt.setEditable(false);
        statetxt.setEditable(false);
        zipcodetxt.setEditable(false);
        phonetxt.setEditable(false);
        firstnametxt.requestFocus(false);
        attachmentbtn.setEnabled(false);
        savebtn.setEnabled(false);
        deletebtn.setEnabled(false);
        editbtn.setEnabled(false);
        empidtxt.requestFocus();
    }

    private void clear() {
        empidtxt.setText(null);
        firstnametxt.setText(null);
        lastnametxt.setText(null);
        addresstxt.setText(null);
        citytxt.setText(null);
        statetxt.setText(null);
        zipcodetxt.setText(null);
        phonetxt.setText(null);
        regdatetxt.setText(null);
        firstnametxt.setText(null);
        AttImglbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shadow-person.jpg")));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lastnametxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        deletebtn = new javax.swing.JButton();
        zipcodetxt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        regdatetxt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        addresstxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        citytxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        phonetxt = new javax.swing.JTextField();
        savebtn = new javax.swing.JButton();
        firstnametxt = new javax.swing.JTextField();
        statetxt = new javax.swing.JTextField();
        editbtn = new javax.swing.JButton();
        exitbtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        searchbtn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        empidtxt = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        AttImglbl = new javax.swing.JLabel();
        attachmentbtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Employee Record");
        setAlwaysOnTop(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        lastnametxt.setEditable(false);
        lastnametxt.setBackground(new java.awt.Color(153, 255, 255));
        lastnametxt.setText(" ");
        lastnametxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
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

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Last Name");
        jLabel4.setAlignmentX(0.5F);
        jLabel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

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

        zipcodetxt.setEditable(false);
        zipcodetxt.setBackground(new java.awt.Color(153, 255, 255));
        zipcodetxt.setText(" ");
        zipcodetxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        zipcodetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zipcodetxtActionPerformed(evt);
            }
        });
        zipcodetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                zipcodetxtKeyTyped(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel7.setText("State");
        jLabel7.setAlignmentX(0.5F);
        jLabel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Address");
        jLabel5.setAlignmentX(0.5F);
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        regdatetxt.setEditable(false);
        regdatetxt.setBackground(new java.awt.Color(153, 255, 255));
        regdatetxt.setText(" ");
        regdatetxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        regdatetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regdatetxtActionPerformed(evt);
            }
        });
        regdatetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                regdatetxtKeyTyped(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Phone Number");
        jLabel9.setAlignmentX(0.5F);
        jLabel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        addresstxt.setEditable(false);
        addresstxt.setBackground(new java.awt.Color(153, 255, 255));
        addresstxt.setText(" ");
        addresstxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        addresstxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addresstxtActionPerformed(evt);
            }
        });
        addresstxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                addresstxtKeyTyped(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("First Name");
        jLabel3.setAlignmentX(0.5F);
        jLabel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("City");
        jLabel6.setAlignmentX(0.5F);
        jLabel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        citytxt.setEditable(false);
        citytxt.setBackground(new java.awt.Color(153, 255, 255));
        citytxt.setText(" ");
        citytxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        citytxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                citytxtActionPerformed(evt);
            }
        });
        citytxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                citytxtKeyTyped(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Registration date");
        jLabel10.setAlignmentX(0.5F);
        jLabel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel10.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Zipe Code");
        jLabel8.setAlignmentX(0.5F);
        jLabel8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel8.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        phonetxt.setEditable(false);
        phonetxt.setBackground(new java.awt.Color(153, 255, 255));
        phonetxt.setText(" ");
        phonetxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        phonetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                phonetxtActionPerformed(evt);
            }
        });
        phonetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                phonetxtKeyTyped(evt);
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

        firstnametxt.setEditable(false);
        firstnametxt.setBackground(new java.awt.Color(153, 255, 255));
        firstnametxt.setText(" ");
        firstnametxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
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

        statetxt.setEditable(false);
        statetxt.setBackground(new java.awt.Color(153, 255, 255));
        statetxt.setText(" ");
        statetxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(0, 51, 102)));
        statetxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statetxtActionPerformed(evt);
            }
        });
        statetxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                statetxtKeyTyped(evt);
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(regdatetxt, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(phonetxt)
                    .addComponent(zipcodetxt)
                    .addComponent(citytxt)
                    .addComponent(addresstxt)
                    .addComponent(lastnametxt)
                    .addComponent(firstnametxt)
                    .addComponent(statetxt))
                .addContainerGap(48, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(savebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deletebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastnametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addresstxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(citytxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zipcodetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phonetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(regdatetxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deletebtn)
                    .addComponent(savebtn)
                    .addComponent(editbtn)
                    .addComponent(exitbtn))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 102));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Lucida Calligraphy", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Employee");

        searchbtn.setText("Search");
        searchbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchbtnActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Hack License :");
        jLabel2.setAlignmentX(0.5F);
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        empidtxt.setText(" ");
        empidtxt.setBorder(javax.swing.BorderFactory.createEtchedBorder(null, new java.awt.Color(204, 255, 255)));
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchbtn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(122, 122, 122)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(empidtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(searchbtn)))
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));

        AttImglbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shadow-person.jpg"))); // NOI18N
        AttImglbl.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        attachmentbtn.setText("Upload New Image");
        attachmentbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachmentbtnActionPerformed(evt);
            }
        });
        attachmentbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                attachmentbtnKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(AttImglbl, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(attachmentbtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AttImglbl, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(attachmentbtn)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void empidtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empidtxtActionPerformed
        if (empidtxt.getText().trim().equals("")) {
            dm.Message("Invalid Entry, please try again!");
            empidtxt.requestFocus();

        } else {
            this.SearchEmployee();
        }
    }//GEN-LAST:event_empidtxtActionPerformed

    private void firstnametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstnametxtActionPerformed
        firstnametxt.transferFocus();
        lastnametxt.selectAll();
    }//GEN-LAST:event_firstnametxtActionPerformed

    private void exitbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitbtnActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitbtnActionPerformed

    private void searchbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchbtnActionPerformed
        if (empidtxt.getText().trim().equals("")) {
            dm.Message("Invalid Entry, please try again!");
            empidtxt.requestFocus();

        } else {
            this.SearchEmployee();
        }

    }//GEN-LAST:event_searchbtnActionPerformed


    private void editbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editbtnActionPerformed
        this.Editable();
        firstnametxt.selectAll();
    }//GEN-LAST:event_editbtnActionPerformed

    private void lastnametxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastnametxtActionPerformed
        addresstxt.selectAll();
        lastnametxt.transferFocus();

    }//GEN-LAST:event_lastnametxtActionPerformed

    private void addresstxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addresstxtActionPerformed
        citytxt.selectAll();
        addresstxt.transferFocus();
    }//GEN-LAST:event_addresstxtActionPerformed

    private void citytxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_citytxtActionPerformed
        citytxt.transferFocus();
        statetxt.selectAll();
    }//GEN-LAST:event_citytxtActionPerformed

    private void statetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statetxtActionPerformed
        statetxt.transferFocus();
        zipcodetxt.selectAll();
    }//GEN-LAST:event_statetxtActionPerformed

    private void zipcodetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zipcodetxtActionPerformed
        zipcodetxt.transferFocus();
        phonetxt.selectAll();
    }//GEN-LAST:event_zipcodetxtActionPerformed

    private void phonetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_phonetxtActionPerformed
        phonetxt.transferFocus();
        savebtn.requestFocus();
    }//GEN-LAST:event_phonetxtActionPerformed

    private void empidtxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empidtxtKeyPressed

    }//GEN-LAST:event_empidtxtKeyPressed

    private void attachmentbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachmentbtnActionPerformed

        this.AttachImage();
    }//GEN-LAST:event_attachmentbtnActionPerformed

    private void savebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_savebtnActionPerformed
        if (firstnametxt.getText().trim().equals("") || lastnametxt.getText().trim().equals("")
                || addresstxt.getText().trim().equals("") || citytxt.getText().trim().equals("")
                || statetxt.getText().trim().equals("") || zipcodetxt.getText().trim().equals("")
                || phonetxt.getText().trim().equals("") || AttImglbl.getIcon() == null) {
            dm.Message("Invalid Entry, please try again!");
            empidtxt.requestFocus();
        } else {
            try {
                this.UpdateEmployee();
                this.NotEditable();
            } catch (Exception e) {
                System.out.println(e.toString());
                dm.Message("An error has occurred, please try again");
            }
        }

    }//GEN-LAST:event_savebtnActionPerformed

    private void regdatetxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regdatetxtActionPerformed
        regdatetxt.transferFocus();
    }//GEN-LAST:event_regdatetxtActionPerformed

    private void deletebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletebtnActionPerformed
        this.DeleteEmployee();

    }//GEN-LAST:event_deletebtnActionPerformed

    private void empidtxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_empidtxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_empidtxtKeyTyped

    private void firstnametxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_firstnametxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_firstnametxtKeyTyped

    private void lastnametxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lastnametxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_lastnametxtKeyTyped

    private void addresstxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_addresstxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_addresstxtKeyTyped

    private void citytxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_citytxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_citytxtKeyTyped

    private void statetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_statetxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_statetxtKeyTyped

    private void zipcodetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_zipcodetxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_zipcodetxtKeyTyped

    private void phonetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phonetxtKeyTyped
        vl.digitOnly(evt);
    }//GEN-LAST:event_phonetxtKeyTyped

    private void regdatetxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_regdatetxtKeyTyped
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.regdatetxtActionPerformed(null);
        }
    }//GEN-LAST:event_regdatetxtKeyTyped

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

    private void attachmentbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_attachmentbtnKeyPressed
        char c = evt.getKeyChar();
        if (c == KeyEvent.VK_ENTER) {
            this.attachmentbtnActionPerformed(null);
        }
    }//GEN-LAST:event_attachmentbtnKeyPressed

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
            java.util.logging.Logger.getLogger(Employee_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee_Info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                Employee_Info dialog = new Employee_Info(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel AttImglbl;
    private javax.swing.JTextField addresstxt;
    private javax.swing.JButton attachmentbtn;
    private javax.swing.JTextField citytxt;
    private javax.swing.JButton deletebtn;
    private javax.swing.JButton editbtn;
    private javax.swing.JTextField empidtxt;
    private javax.swing.JButton exitbtn;
    private javax.swing.JTextField firstnametxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField lastnametxt;
    private javax.swing.JTextField phonetxt;
    private javax.swing.JTextField regdatetxt;
    private javax.swing.JButton savebtn;
    private javax.swing.JButton searchbtn;
    private javax.swing.JTextField statetxt;
    private javax.swing.JTextField zipcodetxt;
    // End of variables declaration//GEN-END:variables
}
