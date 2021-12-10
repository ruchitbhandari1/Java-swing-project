import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class Withdraw implements ActionListener {
    int id;

    Font fontHeading = new Font("", Font.BOLD, 40);
    Font fontInput = new Font("", Font.TYPE1_FONT, 25);
    Font fontBigLabel = new Font("", Font.TRUETYPE_FONT, 24);
    Font fontButton = new Font("", Font.CENTER_BASELINE, 24);
    Font fontLabel = new Font("", Font.TRUETYPE_FONT, 16);
    Font fontSmallLabel = new Font("", Font.TYPE1_FONT, 15);
    Color mainColor = new Color(162, 89, 255);
    Color darkGreyColor = new Color(115, 115, 125);
    Color lightGreyColor = new Color(206, 206, 212);

    JFrame withdraw_Frame, success_Frame;

    JLabel withdraw_Label, amount_Label, pin_Label, success_Label;

    JTextField amount_TextField, symbol_TextField; 
    JPasswordField pin_TextField;

    JButton back_Button, confirm_Button, ok_Button;

    Withdraw(int id) {
        this.id = id;

        withdraw_Frame = new JFrame("Withdraw");

        withdraw_Label = new JLabel("WITHDRAW");
        withdraw_Label.setFont(fontHeading);
        withdraw_Label.setForeground(Color.black);
        withdraw_Label.setBounds(280, 70, 240, 40);
        withdraw_Frame.add(withdraw_Label);

        amount_Label = new JLabel("Enter Amount To Withdraw: -");
        amount_Label.setFont(fontBigLabel);
        amount_Label.setBounds(80, 240, 320, 30);
        withdraw_Frame.add(amount_Label);

        symbol_TextField = new JTextField(20);
        symbol_TextField.setFont(fontHeading);
        symbol_TextField.setText("\u20B9");
        symbol_TextField.setEnabled(false);
        symbol_TextField.setBounds(400, 240, 25, 45);
        withdraw_Frame.add(symbol_TextField);

        amount_TextField = new JTextField(20);
        amount_TextField.setFont(fontHeading);
        amount_TextField.setBounds(425, 240, 200, 45);
        withdraw_Frame.add(amount_TextField);

        pin_Label = new JLabel("Enter Pin : -");
        pin_Label.setFont(fontBigLabel);
        pin_Label.setBounds(270, 325, 130, 45);
        withdraw_Frame.add(pin_Label);

        pin_TextField = new JPasswordField(20);
        pin_TextField.setFont(fontHeading);
        pin_TextField.setBounds(400, 325, 200, 45);
        withdraw_Frame.add(pin_TextField);

        back_Button = new JButton("BACK");
        back_Button.setFont(fontButton);
        back_Button.setBackground(lightGreyColor);
        back_Button.setForeground(darkGreyColor);
        back_Button.setBounds(235, 500, 150, 43);
        withdraw_Frame.add(back_Button);
        back_Button.addActionListener(this);

        confirm_Button = new JButton("CONFIRM");
        confirm_Button.setFont(fontButton);
        confirm_Button.setBackground(mainColor);
        confirm_Button.setForeground(Color.WHITE);
        confirm_Button.setBounds(415, 500, 150, 43);
        withdraw_Frame.add(confirm_Button);
        confirm_Button.addActionListener(this);

        withdraw_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                withdraw_Frame.dispose();
            }
        });

        withdraw_Frame.setSize(800, 600);
        withdraw_Frame.setLayout(null);
        withdraw_Frame.setVisible(true);
        withdraw_Frame.setLocation(360, 140);

    }

    public void actionPerformed(ActionEvent ae) {

        String amount = amount_TextField.getText();
        String enteredPin = new String(pin_TextField.getPassword());

        if (ae.getSource() == back_Button) {
            withdraw_Frame.dispose();
            new Dashboard(id);
        } else if (ae.getSource() == confirm_Button) {

            if (!validatePin(enteredPin) || !validateAmount(amount) || (Integer.parseInt(amount) <= 0)) {
                // --------POPUP SUCCESS ---------

                // Frame for balance
                success_Frame = new JFrame("Error");
                if (!validatePin(enteredPin)) {
                    success_Label = new JLabel("Enter valid 4-digit pin");
                } else if (!validateAmount(amount)) {
                    success_Label = new JLabel("Enter amount less than 99,99,999");
                } else {
                    success_Label = new JLabel("Please enter all fields properly.");
                }
                ok_Button = new JButton("OK");

                // Label for balance
                success_Label.setBounds(30, 60, 240, 21);
                success_Label.setFont(fontLabel);
                success_Frame.add(success_Label);

                // Ok button for balance
                ok_Button.setBounds(120, 123, 60, 35);
                ok_Button.setFont(fontLabel);
                success_Frame.add(ok_Button);

                // working of close button for alance
                success_Frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        success_Frame.dispose();
                        amount_TextField.setText("");
                    }
                });

                // working of ok button to close popup
                ok_Button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        success_Frame.dispose();
                        amount_TextField.setText("");
                    }
                });

                success_Frame.setSize(300, 200);
                success_Frame.setLocation(610, 340);
                success_Frame.setLayout(null);
                success_Frame.setVisible(true);
            } else {
                try {
                    Mysqlconnect c1 = new Mysqlconnect();
                    ResultSet rs = c1.s.executeQuery("select balance from bank where id = '" + id + "';");
                    rs.next();
                    int balance = rs.getInt("balance");

                    ResultSet rs2 = c1.s.executeQuery("select pin from login where id = '" + id + "';");
                    rs2.next();
                    int pin = rs2.getInt("pin");

                    if (pin != Integer.parseInt(enteredPin)) {
                        // Frame for balance
                        success_Frame = new JFrame("Error");
                        success_Label = new JLabel("Incorrect Pin.");
                        ok_Button = new JButton("OK");

                        // Label for balance
                        success_Label.setBounds(30, 60, 240, 21);
                        success_Label.setFont(fontLabel);
                        success_Frame.add(success_Label);

                        // Ok button for balance
                        ok_Button.setBounds(120, 123, 60, 35);
                        ok_Button.setFont(fontLabel);
                        success_Frame.add(ok_Button);

                        // working of close button for alance
                        success_Frame.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                success_Frame.dispose();
                                amount_TextField.setText("");
                                pin_TextField.setText("");
                            }
                        });

                        // working of ok button to close popup
                        ok_Button.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                success_Frame.dispose();
                                amount_TextField.setText("");
                                pin_TextField.setText("");

                            }
                        });

                        success_Frame.setSize(300, 200);
                        success_Frame.setLocation(610, 340);
                        success_Frame.setLayout(null);
                        success_Frame.setVisible(true);

                    } else if (balance < Integer.parseInt(amount)) {
                        // Frame for balance
                        success_Frame = new JFrame("Error");
                        success_Label = new JLabel("Insufficient balance.");
                        ok_Button = new JButton("OK");

                        // Label for balance
                        success_Label.setBounds(30, 60, 240, 21);
                        success_Label.setFont(fontLabel);
                        success_Frame.add(success_Label);

                        // Ok button for balance
                        ok_Button.setBounds(120, 123, 60, 35);
                        ok_Button.setFont(fontLabel);
                        success_Frame.add(ok_Button);

                        // working of close button for alance
                        success_Frame.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                success_Frame.dispose();
                                amount_TextField.setText("");
                                pin_TextField.setText("");
                            }
                        });

                        // working of ok button to close popup
                        ok_Button.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                success_Frame.dispose();
                                amount_TextField.setText("");
                                pin_TextField.setText("");
                            }
                        });

                        success_Frame.setSize(300, 200);
                        success_Frame.setLocation(610, 340);
                        success_Frame.setLayout(null);
                        success_Frame.setVisible(true);
                    } else {
                        int newBalance = balance - Integer.parseInt(amount);
                        String query = "update bank set balance = " + newBalance + " where id = " + id + ";";
                        c1.s.executeUpdate(query);

                        // Frame for balance
                        success_Frame = new JFrame("Success");
                        success_Label = new JLabel("Rs." + amount + " withdrawn successfully.");
                        ok_Button = new JButton("OK");

                        // Label for balance
                        success_Label.setBounds(30, 60, 240, 21);
                        success_Label.setFont(fontLabel);
                        success_Frame.add(success_Label);

                        // Ok button for balance
                        ok_Button.setBounds(120, 123, 60, 35);
                        ok_Button.setFont(fontLabel);
                        success_Frame.add(ok_Button);

                        // working of close button for alance
                        success_Frame.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                withdraw_Frame.dispose();
                                success_Frame.dispose();
                                new Dashboard(id);
                            }
                        });

                        // working of ok button to close popup
                        ok_Button.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                withdraw_Frame.dispose();
                                success_Frame.dispose();
                                new Dashboard(id);
                            }
                        });

                        success_Frame.setSize(300, 200);
                        success_Frame.setLocation(610, 340);
                        success_Frame.setLayout(null);
                        success_Frame.setVisible(true);
                    }
                } catch (Exception e) {

                }
            }

        }

    }

    public static void main(String[] args) {
        // new Withdraw(5);
    }

    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateMobile(String mobile) {
        if (mobile == null || mobile.isEmpty()) {
            return false;
        }
        if (mobile.matches("[6-9]\\d{9}")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatePin(String pin) {
        if (pin == null || pin.isEmpty()) {
            return false;
        }
        if (pin.matches("[0-9]\\d{3}")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateAmount(String amount) {
        if (amount == null || amount.isEmpty()) {
            return false;
        }
        if (amount.matches("[0-9]{0,7}")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateAccountNo(String ac_no) {
        if (ac_no == null || ac_no.isEmpty()) {
            return false;
        }
        if (ac_no.matches("d{15}")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (name.matches("[a-zA-Z]{3,20}")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateUserName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        if (name.matches("[a-zA-Z0-9_]{3,20}")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (password.matches("[a-zA-Z0-9@]{3,20}")) {
            return true;
        } else {
            return false;
        }
    }

}
