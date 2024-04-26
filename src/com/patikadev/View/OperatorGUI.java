package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.Operator;

import javax.swing.*;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;

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


    }

    public static void main(String[] args) {
        Helper.setLayout();

        Operator op = new Operator();
        op.setId(1);
        op.setName("furkan");
        op.setUsername("furkan");
        op.setPassword("1234");
        op.setType("operator");

        OperatorGUI opGUI = new OperatorGUI(op);
    }
}
