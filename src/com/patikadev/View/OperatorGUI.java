package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Operator;
import com.patikadev.Model.User;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_username;
    private JPasswordField fld_user_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_username;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    private final Operator operator;


    public OperatorGUI(Operator operator) {
        this.operator = operator;


        add(wrapper);
        setSize(800, 600);
        setLocation(Helper.screenCenterPoint("x", getSize()), Helper.screenCenterPoint("y", getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        lbl_welcome.setText("Hoşgeldin\t" + operator.getName());

        // ModelUserList
        mdl_user_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 4) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };


        Object[] col_user_list = {"ID", "Ad Soyad", "Kullanıcı Adı", "Şifre", "Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);

        row_user_list = new Object[col_user_list.length];

        loadUserModel();

        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                fld_user_id.setText(select_user_id);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        tbl_user_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1).toString();
                String user_username = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2).toString();
                String user_password = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4).toString();

                if (User.update(user_id,user_name,user_username,user_password,user_type)){
                    Helper.showMsg("done");
                }
                loadUserModel();
            }
        });

        btn_user_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_username) || Helper.isFieldEmpty(fld_user_password)) {
                Helper.showMsg("fill");
            } else {
                String name = fld_user_name.getText();
                String username = fld_user_username.getText();
                String password = fld_user_password.getText();
                String type = cmb_user_type.getSelectedItem().toString();
                if (User.add(name, username, password, type)) {
                    Helper.showMsg("done");
                    loadUserModel();
                    fld_user_name.setText(null);
                    fld_user_username.setText(null);
                    fld_user_password.setText(null);
                }
            }
        });
        btn_user_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_user_id)) {
                Helper.showMsg("fill");
            } else {
                int user_id = Integer.parseInt(fld_user_id.getText());
                if (User.delete(user_id)) {
                    Helper.showMsg("done");
                    loadUserModel();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        btn_user_sh.addActionListener(e -> {
            String name = fld_sh_user_name.getText();
            String username = fld_sh_user_username.getText();
            String type = cmb_sh_user_type.getSelectedItem().toString();

            String sqlQuery = User.searchQuery(name,username,type).toString();
            ArrayList<User> searchingUser = User.searchUserList(sqlQuery);
            loadUserModel(searchingUser);
        });
        btn_logout.addActionListener(e -> {
            dispose(); 
        });
    }

    public void loadUserModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : User.getList()) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPassword();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);

        }
    }

    public void loadUserModel(ArrayList<User> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);

        for (User obj : list) {
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUsername();
            row_user_list[i++] = obj.getPassword();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);

        }
    }

    public static void main(String[] args) {
        Helper.setLayout();

        Operator op = new Operator();
        op.setId(1);
        op.setName("furkan");
        op.setUsername("furkan");
        op.setPassword("1234");
        op.setType("operator");

        DBConnector.getInstance();

        OperatorGUI opGUI = new OperatorGUI(op);
    }
}
