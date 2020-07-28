package studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Chamath Wanigasooriya
 */
public final class Master extends javax.swing.JFrame {

    Connection con = ConnectionManager.getConnection();
    ResultSet rs = null;
    PreparedStatement pst = null;
    DefaultTableModel tm;
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form Master
     */
    public Master() {
        initComponents();
        populateStudentTable();
        populateAttendenceTable();
        populateResultTable();
        populatePaymentTable();
        addComboItems();
    }

    public static boolean isNumeric(final String str) {

        // null or empty
        if (str == null || str.length() == 0) {
            return false;
        }

        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;

    }

    private boolean validateStudentFeild() {
        String value = idTxt.getText();
        if (idTxt.getText().isEmpty() || fNameTxt.getText().isEmpty() || lNameTxt.getText().isEmpty() || nicTxt.getText().isEmpty() || emailTxt.getText().isEmpty() || phoneTxt.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill in all Required Feilds");
            return false;
        } else if (new ConnectionManager().validateMail(emailTxt.getText()) == false) {
            JOptionPane.showMessageDialog(this, "Please Enter a Valid Email");
            return false;
        }
        try {
            String adm = "Select * from student where id='" + value + "'";
            pst = con.prepareStatement(adm);
            pst.execute();
            rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Student ID already Exsists, Please Check Again");
                return false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return true;
    }

    private boolean validatePaymentField() {
        String value = paymentNoTxt.getText();
        if (paymentNoTxt.getText().isEmpty() || paymentAmount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Fill in all Required Feilds");
            return false;
        }
        try {
            String adm = "Select * from payment where paymentNo='" + value + "'";
            pst = con.prepareStatement(adm);
            pst.execute();
            rs = pst.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Student Payment number already Exsists, Please Check Again");
                return false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return true;
    }

    private void filter(String query) {
        DefaultTableModel dm = (DefaultTableModel) attendenceTable.getModel();
        DefaultTableModel rm = (DefaultTableModel) studentTable.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(dm);
        TableRowSorter<DefaultTableModel> rt = new TableRowSorter<DefaultTableModel>(rm);
        attendenceTable.setRowSorter(tr);
        studentTable.setRowSorter(rt);
        tr.setRowFilter(RowFilter.regexFilter(query));
        rt.setRowFilter(RowFilter.regexFilter(query));
    }

    private void addComboItems() {
        //String[] sub={"Software Engineering","Computer Engineering","Computer Science"};
        year.removeAllItems();
        for (int i = 1990; i <= 2020; i++) {
            year.addItem(String.valueOf(i));
        }
        month.removeAllItems();
        for (int i = 1; i <= 12; i++) {
            month.addItem(String.valueOf(i));
        }
        date.removeAllItems();
        for (int i = 1; i <= 31; i++) {
            date.addItem(String.valueOf(i));
        }
        paymentSem.removeAllItems();
        for (int i = 1; i <= 8; i++) {
            paymentSem.addItem(String.valueOf(i));
        }
        try {
            pst = con.prepareStatement("select * from student");
            rs = pst.executeQuery();
            idAttendence.removeAllItems();
            paymentCombo.removeAllItems();
            paymentName.removeAllItems();
            resultID.removeAllItems();
            resultName.removeAllItems();
            while (rs.next()) {
                idAttendence.addItem(rs.getString(1));
                paymentName.addItem(rs.getString(2) + " " + rs.getString(3));
                paymentCombo.addItem(rs.getString(1));
                resultID.addItem(rs.getString(1));
                resultName.addItem(rs.getString(2) + " " + rs.getString(3));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        try {
            pst = con.prepareStatement("select * from course");
            rs = pst.executeQuery();
            cidTxt.removeAllItems();
            crsCombo.removeAllItems();
            resultCourse.removeAllItems();
            while (rs.next()) {
                cidTxt.addItem(rs.getString(1));
                crsCombo.addItem(rs.getString(1));
                resultCourse.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        //DefaultComboBoxModel dm=new DefaultComboBoxModel(sub);
        //crsCombo.setModel(dm);
    }

    private void populateStudentTable() {
        try {
            String adm = "Select * from student";
            pst = con.prepareStatement(adm);
            rs = pst.executeQuery();
            tm = (DefaultTableModel) studentTable.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object o[] = {rs.getString(1), rs.getString(2) + " " + rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)};
                tm.addRow(o);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private void populateResultTable() {
        try {
            String adm = "Select * from result";
            pst = con.prepareStatement(adm);
            rs = pst.executeQuery();
            tm = (DefaultTableModel) resultTable.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object o[] = {rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)};
                tm.addRow(o);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private void populatePaymentTable() {
        try {
            String adm = "Select * from payment";
            pst = con.prepareStatement(adm);
            rs = pst.executeQuery();
            tm = (DefaultTableModel) paymentTable.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object o[] = {rs.getString(1), rs.getString(2), rs.getString(3) + " " + rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)};
                tm.addRow(o);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private void populateAttendenceTable() {
        try {
            String adt = "Select * from attendence";
            pst = con.prepareStatement(adt);
            rs = pst.executeQuery();
            tm = (DefaultTableModel) attendenceTable.getModel();
            tm.setRowCount(0);
            while (rs.next()) {
                Object a[] = {rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)};
                tm.addRow(a);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialise the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        welcome = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        addBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lNameTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        fNameTxt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        idTxt = new javax.swing.JTextField();
        nicTxt = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        emailTxt = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        phoneTxt = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        year = new javax.swing.JComboBox<>();
        month = new javax.swing.JComboBox<>();
        date = new javax.swing.JComboBox<>();
        crsCombo = new javax.swing.JComboBox<>();
        jButton7 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        attendenceTable = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        slot2 = new javax.swing.JCheckBox();
        slot1 = new javax.swing.JCheckBox();
        dateChooser = new com.toedter.calendar.JDateChooser();
        idAttendence = new javax.swing.JComboBox<>();
        cidTxt = new javax.swing.JComboBox<>();
        jPanel13 = new javax.swing.JPanel();
        addBtnAttend = new javax.swing.JButton();
        removeBtn4 = new javax.swing.JButton();
        updateBtn4 = new javax.swing.JButton();
        search = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        paymentTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        paymentAmount = new javax.swing.JTextField();
        paymentDate = new com.toedter.calendar.JDateChooser();
        jLabel62 = new javax.swing.JLabel();
        paymentSem = new javax.swing.JComboBox<>();
        paymentNoTxt = new javax.swing.JTextField();
        paymentCombo = new javax.swing.JComboBox<>();
        paymentName = new javax.swing.JComboBox<>();
        addBtn3 = new javax.swing.JButton();
        removeBtn3 = new javax.swing.JButton();
        updateBtn3 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        resultID = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        resultName = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        resultGrade = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        resultCourse = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        addBtn2 = new javax.swing.JButton();
        removeBtn2 = new javax.swing.JButton();
        updateBtn2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1366, 720));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        welcome.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        welcome.setForeground(new java.awt.Color(255, 255, 255));
        welcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(welcome, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 20, 250, 50));

        jLabel4.setFont(new java.awt.Font("Corbel Light", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ADMIN PANEL");
        jLabel4.setToolTipText("");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, -1, -1));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/arrow.png"))); // NOI18N
        jButton1.setToolTipText("Back");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 60, 40));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/studentTab.png"))); // NOI18N
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 230, 60));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/attendenceTab.png"))); // NOI18N
        jButton3.setContentAreaFilled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 230, 60));

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/resultsTab.png"))); // NOI18N
        jButton4.setContentAreaFilled(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 230, 60));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/paymentsTab.png"))); // NOI18N
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 230, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/adminHeader.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1600, 90));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/adminBackground.png"))); // NOI18N
        jLabel3.setText("jLabel3");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 250, 720));

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        studentTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Name", "NIC", "DOB", "Email", "Phone", "Course"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        studentTable.setRowHeight(20);
        studentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                studentTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(studentTable);
        if (studentTable.getColumnModel().getColumnCount() > 0) {
            studentTable.getColumnModel().getColumn(0).setHeaderValue("ID");
            studentTable.getColumnModel().getColumn(1).setHeaderValue("Name");
            studentTable.getColumnModel().getColumn(2).setHeaderValue("NIC");
            studentTable.getColumnModel().getColumn(3).setHeaderValue("DOB");
            studentTable.getColumnModel().getColumn(4).setHeaderValue("Email");
            studentTable.getColumnModel().getColumn(5).setHeaderValue("Phone");
            studentTable.getColumnModel().getColumn(6).setHeaderValue("Course");
        }

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(554, 60, 840, 550));

        addBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/addBtn.png"))); // NOI18N
        addBtn.setContentAreaFilled(false);
        addBtn.setFocusable(false);
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });
        jPanel2.add(addBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 620, 190, 75));

        removeBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/removeBtn.png"))); // NOI18N
        removeBtn.setContentAreaFilled(false);
        removeBtn.setFocusable(false);
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });
        jPanel2.add(removeBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 620, 190, 75));

        updateBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/updateBtn.png"))); // NOI18N
        updateBtn.setContentAreaFilled(false);
        updateBtn.setFocusable(false);
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });
        jPanel2.add(updateBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 620, 200, 75));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24))); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Last Name");
        jPanel7.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));
        jPanel7.add(lNameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, 370, 40));

        jLabel5.setText("First Name");
        jPanel7.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));
        jPanel7.add(fNameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 370, 40));

        jLabel10.setText("ID");
        jPanel7.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));
        jPanel7.add(idTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 370, 40));
        jPanel7.add(nicTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 200, 370, 40));

        jLabel11.setText("NIC");
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));

        jLabel12.setText("DOB");
        jPanel7.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));
        jPanel7.add(emailTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 300, 370, 40));

        jLabel13.setText("Email");
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));

        phoneTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                phoneTxtKeyTyped(evt);
            }
        });
        jPanel7.add(phoneTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 370, 40));

        jLabel14.setText("Phone");
        jPanel7.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));

        jLabel15.setText("Course");
        jPanel7.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, -1, -1));

        jLabel16.setText("Year");
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, -1, -1));

        jLabel17.setText("Date");
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, -1, -1));

        jLabel18.setText("Month");
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, -1, -1));

        jPanel7.add(year, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, -1, -1));

        jPanel7.add(month, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 260, -1, -1));

        jPanel7.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, -1, -1));

        jPanel7.add(crsCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 402, 370, 40));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 480, 600));

        jButton7.setText("View Registered Students");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 640, 260, 40));

        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        jPanel2.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, 780, 30));

        jLabel6.setText("Search");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, -1, -1));

        jTabbedPane1.addTab("", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        attendenceTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        attendenceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "ID", "Course ID", "Date", "Morning", "Afternoon"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        attendenceTable.setRowHeight(20);
        attendenceTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                attendenceTableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(attendenceTable);

        jPanel3.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(554, 60, 840, 550));

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Attendence", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24))); // NOI18N
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setText("Course ID");
        jPanel9.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel20.setText("ID");
        jPanel9.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        jLabel22.setText("Date");
        jPanel9.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Session Attendence", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        slot2.setText("Afternoon");
        jPanel12.add(slot2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, -1, -1));

        slot1.setText("Morning");
        slot1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slot1ActionPerformed(evt);
            }
        });
        jPanel12.add(slot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jPanel9.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 460, 90));

        dateChooser.setDateFormatString("yyyy-MM-dd");
        jPanel9.add(dateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, 370, 40));

        jPanel9.add(idAttendence, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 360, 40));

        jPanel9.add(cidTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 360, 40));

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Overall Attendence", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        jPanel9.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 320, 460, 270));

        jPanel3.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 480, 600));

        addBtnAttend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/addBtn.png"))); // NOI18N
        addBtnAttend.setContentAreaFilled(false);
        addBtnAttend.setFocusable(false);
        addBtnAttend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnAttendActionPerformed(evt);
            }
        });
        jPanel3.add(addBtnAttend, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 620, 190, 75));

        removeBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/removeBtn.png"))); // NOI18N
        removeBtn4.setContentAreaFilled(false);
        removeBtn4.setFocusable(false);
        removeBtn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtn4ActionPerformed(evt);
            }
        });
        jPanel3.add(removeBtn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 620, 190, 75));

        updateBtn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/updateBtn.png"))); // NOI18N
        updateBtn4.setContentAreaFilled(false);
        updateBtn4.setFocusable(false);
        updateBtn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtn4ActionPerformed(evt);
            }
        });
        jPanel3.add(updateBtn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 620, 200, 75));

        search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchFocusLost(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });
        jPanel3.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, 780, 30));

        jLabel7.setText("Search");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, -1, -1));

        jTabbedPane1.addTab("", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        paymentTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        paymentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "SID", "Name", "Semester", "Amount", "Payment Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        paymentTable.setRowHeight(20);
        paymentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paymentTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(paymentTable);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(554, 20, 840, 590));

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Payments", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel34.setText("No");
        jPanel8.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        jLabel35.setText("SID");
        jPanel8.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jLabel36.setText("Name");
        jPanel8.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        jLabel38.setText("Semester");
        jPanel8.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        jLabel39.setText("Payment Date");
        jPanel8.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));

        paymentAmount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                paymentAmountKeyTyped(evt);
            }
        });
        jPanel8.add(paymentAmount, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 330, 40));

        paymentDate.setDateFormatString("yyyy-MM-dd");
        jPanel8.add(paymentDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 370, 320, 40));

        jLabel62.setText("Amount");
        jPanel8.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));

        jPanel8.add(paymentSem, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 330, 40));
        jPanel8.add(paymentNoTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 330, 40));

        paymentCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentComboActionPerformed(evt);
            }
        });
        jPanel8.add(paymentCombo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 330, 40));

        jPanel8.add(paymentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 330, 40));

        jPanel4.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 480, 600));

        addBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/addBtn.png"))); // NOI18N
        addBtn3.setContentAreaFilled(false);
        addBtn3.setFocusable(false);
        addBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtn3ActionPerformed(evt);
            }
        });
        jPanel4.add(addBtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 620, 190, 75));

        removeBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/removeBtn.png"))); // NOI18N
        removeBtn3.setContentAreaFilled(false);
        removeBtn3.setFocusable(false);
        removeBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtn3ActionPerformed(evt);
            }
        });
        jPanel4.add(removeBtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 620, 190, 75));

        updateBtn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/updateBtn.png"))); // NOI18N
        updateBtn3.setContentAreaFilled(false);
        updateBtn3.setFocusable(false);
        updateBtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtn3ActionPerformed(evt);
            }
        });
        jPanel4.add(updateBtn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 620, 200, 75));

        jTabbedPane1.addTab("", jPanel4);

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        resultTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "SID", "Course", "Name", "Grade"
            }
        ));
        resultTable.setRowHeight(20);
        resultTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(resultTable);

        jPanel5.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(554, 20, 840, 590));

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Result", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 24))); // NOI18N
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.add(resultID, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 330, 40));

        jLabel37.setText("SID");
        jPanel10.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        jPanel10.add(resultName, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 330, 40));

        jLabel63.setText("Name");
        jPanel10.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        resultGrade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "A", "A-", "B+", "B", "B-", "C+", "C", "D" }));
        jPanel10.add(resultGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 330, 40));

        jLabel64.setText("Grade");
        jPanel10.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));

        jPanel10.add(resultCourse, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 330, 40));

        jLabel65.setText("Course");
        jPanel10.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, -1));

        jPanel5.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 480, 600));

        addBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/addBtn.png"))); // NOI18N
        addBtn2.setContentAreaFilled(false);
        addBtn2.setFocusable(false);
        addBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtn2ActionPerformed(evt);
            }
        });
        jPanel5.add(addBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 620, 190, 75));

        removeBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/removeBtn.png"))); // NOI18N
        removeBtn2.setContentAreaFilled(false);
        removeBtn2.setFocusable(false);
        removeBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtn2ActionPerformed(evt);
            }
        });
        jPanel5.add(removeBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 620, 190, 75));

        updateBtn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/studentmanagement/updateBtn.png"))); // NOI18N
        updateBtn2.setContentAreaFilled(false);
        updateBtn2.setFocusable(false);
        updateBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtn2ActionPerformed(evt);
            }
        });
        jPanel5.add(updateBtn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 620, 200, 75));

        jTabbedPane1.addTab("", jPanel5);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 1430, 710));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1600, 800));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        addComboItems();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jTabbedPane1.setSelectedIndex(1);
        addComboItems();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        addComboItems();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void updateBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtn2ActionPerformed
        int i = resultTable.getSelectedRow();
        if (i >= 0) {
            int column = 0;
            int row = resultTable.getSelectedRow();
            String value = (resultTable.getModel().getValueAt(row, column).toString());
            new ConnectionManager().updateResult(value, resultID.getSelectedItem().toString(), resultCourse.getSelectedItem().toString(), resultName.getSelectedItem().toString(), resultGrade.getSelectedItem().toString());
            populateResultTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please Select a Record");
        }
    }//GEN-LAST:event_updateBtn2ActionPerformed

    private void removeBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtn2ActionPerformed
        tm = (DefaultTableModel) resultTable.getModel();
        int i = resultTable.getSelectedRow();
        if (i >= 0) {
            int column = 0;
            int row = resultTable.getSelectedRow();
            String value = (resultTable.getModel().getValueAt(row, column).toString());
            tm.removeRow(i);
            try {
                String sql = "delete from result where sid='" + value + "'";
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(this, "Successfully Removed a Record");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Select a Record");
        }
    }//GEN-LAST:event_removeBtn2ActionPerformed

    private void addBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtn2ActionPerformed
        tm = (DefaultTableModel) resultTable.getModel();
        Object[] line = new Object[5];
        line[0] = resultID.getSelectedItem().toString();
        line[1] = resultName.getSelectedItem().toString();
        line[2] = resultCourse.getSelectedItem().toString();
        line[3] = resultGrade.getSelectedItem().toString();
        new ConnectionManager().insertResult(line);
        Object[] row = new Object[6];
        row[0] = resultID.getSelectedItem().toString();
        row[1] = resultName.getSelectedItem().toString();
        row[2] = resultCourse.getSelectedItem().toString();
        row[3] = resultGrade.getSelectedItem().toString();
        tm.addRow(row);
        populatePaymentTable();
        JOptionPane.showMessageDialog(this, "Successfully Added");
    }//GEN-LAST:event_addBtn2ActionPerformed

    private void resultTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultTableMouseClicked

    }//GEN-LAST:event_resultTableMouseClicked

    private void updateBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtn3ActionPerformed
        String name = String.valueOf(paymentName.getSelectedItem());
        Object[] line = new Object[8];
        int i = paymentTable.getSelectedRow();
        if (i >= 0) {
            int column = 0;
            int row = paymentTable.getSelectedRow();
            String value = (paymentTable.getModel().getValueAt(row, column).toString());
            if (name.split("\\w+").length > 1) {
                line[3] = name.substring(name.lastIndexOf(" ") + 1);
                line[2] = name.substring(0, name.lastIndexOf(' '));
            }
            line[0] = paymentNoTxt.getText();
            line[1] = paymentCombo.getSelectedItem().toString();
            line[4] = String.valueOf(paymentSem.getSelectedItem());
            line[5] = paymentAmount.getText();
            line[6] = dt.format(paymentDate.getDate());
            new ConnectionManager().updatePayment(value, line);
            populatePaymentTable();
            JOptionPane.showMessageDialog(this, "Update Successful");
        } else {
            JOptionPane.showMessageDialog(this, "Please Select a Record");
        }
    }//GEN-LAST:event_updateBtn3ActionPerformed

    private void removeBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtn3ActionPerformed
        tm = (DefaultTableModel) paymentTable.getModel();
        int i = paymentTable.getSelectedRow();
        if (i >= 0) {
            int column = 0;
            int row = paymentTable.getSelectedRow();
            String value = (paymentTable.getModel().getValueAt(row, column).toString());
            tm.removeRow(i);
            try {
                String sql = "delete from payment where paymentNo='" + value + "'";
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(this, "Successfully Removed a Record");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Select a Record");
        }
    }//GEN-LAST:event_removeBtn3ActionPerformed

    private void addBtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtn3ActionPerformed
        if (validatePaymentField()) {
            if (paymentDate.getDate() != null) {
                if (isNumeric(paymentAmount.getText()) == true) {
                    tm = (DefaultTableModel) paymentTable.getModel();
                    Object[] line = new Object[8];
                    String name = String.valueOf(paymentName.getSelectedItem());
                    if (name.split("\\w+").length > 1) {
                        line[3] = name.substring(name.lastIndexOf(" ") + 1);
                        line[2] = name.substring(0, name.lastIndexOf(' '));
                    }
                    line[0] = paymentNoTxt.getText();
                    line[1] = paymentCombo.getSelectedItem().toString();
                    line[4] = String.valueOf(paymentSem.getSelectedItem());
                    line[5] = paymentAmount.getText();
                    line[6] = dt.format(paymentDate.getDate());
                    new ConnectionManager().insertPayment(line);
                    Object[] row = new Object[6];
                    row[0] = paymentNoTxt.getText();
                    row[1] = paymentCombo.getSelectedItem().toString();
                    row[2] = name;
                    row[3] = String.valueOf(paymentSem.getSelectedItem());
                    row[4] = paymentAmount.getText();
                    row[5] = dt.format(paymentDate.getDate());
                    tm.addRow(row);
                    populatePaymentTable();
                    JOptionPane.showMessageDialog(this, "Successfully Added");
                } else {
                    JOptionPane.showMessageDialog(this, "Please provide a valid amount");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please provide a valid date");
            }
        }
    }//GEN-LAST:event_addBtn3ActionPerformed

    private void paymentComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_paymentComboActionPerformed

    private void paymentAmountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_paymentAmountKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_paymentAmountKeyTyped

    private void paymentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paymentTableMouseClicked
        try {
            int column = 0;
            int row = paymentTable.getSelectedRow();
            String value = (paymentTable.getModel().getValueAt(row, column).toString());
            String ad = "Select * from payment where paymentNo='" + value + "'";
            pst = con.prepareStatement(ad);
            rs = pst.executeQuery();
            if (rs.next()) {
                paymentNoTxt.setText(rs.getString(1));
                paymentAmount.setText(rs.getString(6));
                paymentName.setSelectedItem(rs.getString(3) + " " + rs.getString(4));
                paymentSem.setSelectedItem(rs.getString(5));
                ((JTextField) paymentDate.getDateEditor().getUiComponent()).setText(rs.getString(7));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_paymentTableMouseClicked

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        String query = search.getText();
        filter(query);
    }//GEN-LAST:event_searchKeyReleased

    private void searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusLost
        filter("");
        search.setText("");
    }//GEN-LAST:event_searchFocusLost

    private void updateBtn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtn4ActionPerformed
        int i = attendenceTable.getSelectedRow();
        if (i >= 0) {
            int column = 0;
            int row = attendenceTable.getSelectedRow();
            String value = (attendenceTable.getModel().getValueAt(row, column).toString());
            if (slot1.isSelected() && slot2.isSelected()) {
                new ConnectionManager().updateAttendence(value, idAttendence.getSelectedItem().toString(), cidTxt.getSelectedItem().toString(), dt.format(dateChooser.getDate()), "1", "1");
            } else if (slot1.isSelected() && slot2.isSelected() == false) {
                new ConnectionManager().updateAttendence(value, idAttendence.getSelectedItem().toString(), cidTxt.getSelectedItem().toString(), dt.format(dateChooser.getDate()), "1", "0");
            } else if (slot1.isSelected() == false && slot2.isSelected()) {
                new ConnectionManager().updateAttendence(value, idAttendence.getSelectedItem().toString(), cidTxt.getSelectedItem().toString(), dt.format(dateChooser.getDate()), "0", "1");
            } else {
                new ConnectionManager().updateAttendence(value, idAttendence.getSelectedItem().toString(), cidTxt.getSelectedItem().toString(), dt.format(dateChooser.getDate()), "0", "0");
            }
            populateAttendenceTable();
            JOptionPane.showMessageDialog(this, "Update Successful");
        } else {
            JOptionPane.showMessageDialog(this, "Please Select a Record");
        }
    }//GEN-LAST:event_updateBtn4ActionPerformed

    private void removeBtn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtn4ActionPerformed
        tm = (DefaultTableModel) attendenceTable.getModel();
        int i = attendenceTable.getSelectedRow();
        if (i >= 0) {
            int column = 0;
            int row = attendenceTable.getSelectedRow();
            String value = (attendenceTable.getModel().getValueAt(row, column).toString());
            tm.removeRow(i);
            try {
                String sql = "delete from attendence where no='" + value + "'";
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(this, "Successfully Removed a Record");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Select a Record");
        }
    }//GEN-LAST:event_removeBtn4ActionPerformed

    private void addBtnAttendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnAttendActionPerformed
        Object[] line = new Object[5];
        tm = (DefaultTableModel) attendenceTable.getModel();
        if (dateChooser.getDate() != null) {
            line[0] = idAttendence.getSelectedItem().toString();
            line[1] = cidTxt.getSelectedItem().toString();
            line[2] = dt.format(dateChooser.getDate());
            if (slot1.isSelected() && slot2.isSelected()) {
                line[3] = "1";
                line[4] = "1";
            } else if (slot1.isSelected() && slot2.isSelected() == false) {
                line[3] = "1";
                line[4] = "0";
            } else if (slot1.isSelected() == false && slot2.isSelected()) {
                line[3] = "0";
                line[4] = "1";
            } else if (slot1.isSelected() == false && slot2.isSelected() == false) {
                line[3] = "0";
                line[4] = "0";
            }
            new ConnectionManager().insertAttend(line);
            tm.addRow(line);
            populateAttendenceTable();
            JOptionPane.showMessageDialog(this, "Successfully Added");
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in the required Fields with Vaild Details");
        }
    }//GEN-LAST:event_addBtnAttendActionPerformed

    private void slot1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slot1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_slot1ActionPerformed

    private void attendenceTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_attendenceTableMouseClicked
        try {
            int column = 0;
            int row = attendenceTable.getSelectedRow();
            String value = (attendenceTable.getModel().getValueAt(row, column).toString());
            String ad = "Select * from attendence where no='" + value + "'";
            pst = con.prepareStatement(ad);
            rs = pst.executeQuery();
            if (rs.next()) {
                idAttendence.setSelectedItem(rs.getString(2));
                cidTxt.setSelectedItem(rs.getString(3));
                ((JTextField) dateChooser.getDateEditor().getUiComponent()).setText(rs.getString(4));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }//GEN-LAST:event_attendenceTableMouseClicked

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        String query = jTextField1.getText();
        filter(query);
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        filter("");
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1FocusLost

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            new ConnectionManager().printReport();
        } catch (JRException ex) {
            Logger.getLogger(Master.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void phoneTxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_phoneTxtKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_phoneTxtKeyTyped

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        String dob = (String) year.getSelectedItem() + "-" + month.getSelectedItem() + "-" + date.getSelectedItem();
        int i = studentTable.getSelectedRow();
        if (i >= 0) {
            int column = 0;
            int row = studentTable.getSelectedRow();
            String value = (studentTable.getModel().getValueAt(row, column).toString());
            new ConnectionManager().updateStudent(value, idTxt.getText(), fNameTxt.getText(), lNameTxt.getText(), nicTxt.getText(), dob, emailTxt.getText(), phoneTxt.getText(), crsCombo.getSelectedItem().toString());
            populateStudentTable();
            JOptionPane.showMessageDialog(this, "Update Successful");
        } else {
            JOptionPane.showMessageDialog(this, "Please Select a Record");
        }
    }//GEN-LAST:event_updateBtnActionPerformed

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
        tm = (DefaultTableModel) studentTable.getModel();
        int i = studentTable.getSelectedRow();
        if (i >= 0) {
            int column = 0;
            int row = studentTable.getSelectedRow();
            String value = (studentTable.getModel().getValueAt(row, column).toString());
            tm.removeRow(i);
            try {
                String sql = "delete from student where id='" + value + "'";
                pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(this, "Successfully Removed a Record");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please Select a Record");
        }
    }//GEN-LAST:event_removeBtnActionPerformed

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
        if (validateStudentFeild()) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to send an email to notify the user? Press No to add record only.", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                Mail email = new Mail(emailTxt.getText(), "CNC INSTITUTE", "Hello," + fNameTxt.getText() + " you jave been selected to the " + crsCombo.getSelectedItem() + " course at the CNC Institute. Use your email(" + emailTxt.getText() + ") as the Username and Password as qwerty123 to login.");
                email.doTest();
            }
            String dob = (String) year.getSelectedItem() + "-" + month.getSelectedItem() + "-" + date.getSelectedItem();
            tm = (DefaultTableModel) studentTable.getModel();
            Object[] line = new Object[7];
            new ConnectionManager().insertStudent(idTxt.getText(), fNameTxt.getText(), lNameTxt.getText(), nicTxt.getText(), dob, emailTxt.getText(), phoneTxt.getText(), (String) crsCombo.getSelectedItem());
            line[0] = idTxt.getText();
            line[1] = fNameTxt.getText() + " " + lNameTxt.getText();
            line[2] = nicTxt.getText();
            line[3] = dob;
            line[4] = emailTxt.getText();
            line[5] = phoneTxt.getText();
            line[6] = crsCombo.getSelectedItem();
            tm.addRow(line);
            populateStudentTable();
        }
    }//GEN-LAST:event_addBtnActionPerformed

    private void studentTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_studentTableMouseClicked
        try {
            int column = 0;
            int row = studentTable.getSelectedRow();
            String value = (studentTable.getModel().getValueAt(row, column).toString());
            String adm = "Select * from student where id='" + value + "'";
            pst = con.prepareStatement(adm);
            rs = pst.executeQuery();
            if (rs.next()) {
                idTxt.setText(rs.getString("id"));
                fNameTxt.setText(rs.getString("fname"));
                lNameTxt.setText(rs.getString("lname"));
                nicTxt.setText(rs.getString("nic"));
                emailTxt.setText(rs.getString("email"));
                phoneTxt.setText(rs.getString("tele"));
                crsCombo.setSelectedItem(rs.getString("cid"));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_studentTableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Master.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Master.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Master.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Master.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Master().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JButton addBtn2;
    private javax.swing.JButton addBtn3;
    private javax.swing.JButton addBtnAttend;
    private javax.swing.JTable attendenceTable;
    private javax.swing.JComboBox<String> cidTxt;
    private javax.swing.JComboBox<String> crsCombo;
    private javax.swing.JComboBox<String> date;
    private com.toedter.calendar.JDateChooser dateChooser;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JTextField fNameTxt;
    private javax.swing.JComboBox<String> idAttendence;
    private javax.swing.JTextField idTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField lNameTxt;
    private javax.swing.JComboBox<String> month;
    private javax.swing.JTextField nicTxt;
    private javax.swing.JTextField paymentAmount;
    private javax.swing.JComboBox<String> paymentCombo;
    private com.toedter.calendar.JDateChooser paymentDate;
    private javax.swing.JComboBox<String> paymentName;
    private javax.swing.JTextField paymentNoTxt;
    private javax.swing.JComboBox<String> paymentSem;
    private javax.swing.JTable paymentTable;
    private javax.swing.JTextField phoneTxt;
    private javax.swing.JButton removeBtn;
    private javax.swing.JButton removeBtn2;
    private javax.swing.JButton removeBtn3;
    private javax.swing.JButton removeBtn4;
    private javax.swing.JComboBox<String> resultCourse;
    private javax.swing.JComboBox<String> resultGrade;
    private javax.swing.JComboBox<String> resultID;
    private javax.swing.JComboBox<String> resultName;
    private javax.swing.JTable resultTable;
    private javax.swing.JTextField search;
    private javax.swing.JCheckBox slot1;
    private javax.swing.JCheckBox slot2;
    private javax.swing.JTable studentTable;
    private javax.swing.JButton updateBtn;
    private javax.swing.JButton updateBtn2;
    private javax.swing.JButton updateBtn3;
    private javax.swing.JButton updateBtn4;
    public static javax.swing.JLabel welcome;
    private javax.swing.JComboBox<String> year;
    // End of variables declaration//GEN-END:variables
}
