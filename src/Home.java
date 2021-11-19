
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Home implements ActionListener {

    Font fontHeading = new Font("", Font.BOLD, 40);
    Font fontInput = new Font("", Font.TRUETYPE_FONT, 30);
    Font fontButton = new Font("", Font.CENTER_BASELINE, 30);
    Font fontLabel = new Font("", Font.TRUETYPE_FONT, 16);
    Font fontSmallLabel = new Font("", Font.TYPE1_FONT, 15);
    Color mainColor = new Color(162, 89, 255);

    // home_Frame is main frame and error_Frame is popup frame
    JFrame home_Frame, error_Frame;

    //text_label is text of incorrect username and password
    JLabel welcome_Label, username_Label, password_Label, notAMember_Label, text_Label;

    // ok_Button is ok button of popup
    JButton login_Button, createAccount_Button, ok_Button;

    // 
    JTextField username_TextField;
    JPasswordField password_TextField;

    Home() {

        home_Frame = new JFrame("JAVA Bank");

        welcome_Label = new JLabel("WELCOME");
        welcome_Label.setFont(fontHeading);
        welcome_Label.setForeground(mainColor);
        welcome_Label.setBounds(300, 116, 215, 40);
        home_Frame.add(welcome_Label);

        username_Label = new JLabel("Username");
        username_Label.setFont(fontLabel);
        username_Label.setBounds(250, 196, 97, 16);
        home_Frame.add(username_Label);

        password_Label = new JLabel("Password");
        password_Label.setFont(fontLabel);
        password_Label.setBounds(250, 287, 97, 16);
        home_Frame.add(password_Label);

        username_TextField = new JTextField(20);
        username_TextField.setFont(fontInput);
        username_TextField.setBounds(250, 222, 300, 40);
        home_Frame.add(username_TextField);

        password_TextField = new JPasswordField(15);
        password_TextField.setFont(fontInput);
        password_TextField.setBounds(250, 312, 300, 40);
        home_Frame.add(password_TextField);

        login_Button = new JButton("LOGIN");
        login_Button.setFont(fontButton);
        login_Button.setBackground(mainColor);
        login_Button.setForeground(Color.WHITE);
        login_Button.setBounds(275, 382, 240, 46);
        home_Frame.add(login_Button);
        login_Button.addActionListener(this);

        notAMember_Label = new JLabel("Not a member?");
        notAMember_Label.setFont(fontSmallLabel);
        notAMember_Label.setBounds(270, 444, 115, 15);
        home_Frame.add(notAMember_Label);

        createAccount_Button = new JButton("Create Account");
        createAccount_Button.setFont(fontSmallLabel);
        createAccount_Button.setBounds(388, 444, 150, 17);
        createAccount_Button.setForeground(mainColor);
        home_Frame.add(createAccount_Button);
        createAccount_Button.addActionListener(this);

        // to make close button work
        home_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                home_Frame.dispose();
                error_Frame.dispose();
            }
        });

        home_Frame.setSize(800, 600);
        home_Frame.setLayout(null);
        home_Frame.setVisible(true);
        home_Frame.setLocation(360, 140);

        //--------------------------POPUP FOR INCORRECT USERNAME AND PASSWORD------------------------------

        // Frame for incorrect username or password
        error_Frame = new JFrame("Try Again");
        text_Label = new JLabel("Incorrect username or password");
        ok_Button = new JButton("OK");

        // Label for incorrect username or password
        text_Label.setBounds(30, 60, 240, 21);
        text_Label.setFont(fontLabel);
        error_Frame.add(text_Label);

        // Ok button for incorrect username or password
        ok_Button.setBounds(120, 123, 60, 35);
        ok_Button.setFont(fontLabel);
        error_Frame.add(ok_Button);

        //working of close button for incorrect username or password
        error_Frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                error_Frame.dispose();
            }
        });

        // working of ok button to clear username and password fields
        ok_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                error_Frame.dispose();
                username_TextField.setText("");
                password_TextField.setText("");
            }
        });

        error_Frame.setSize(300, 200);
        error_Frame.setLocation(610, 340);
        error_Frame.setLayout(null);
        error_Frame.setVisible(false);

    }

    public void actionPerformed(ActionEvent ae) {

        try {

            if (ae.getSource() == login_Button) {

                Mysqlconnect c1 = new Mysqlconnect();

                String username = new String(username_TextField.getText());
                String password = new String(password_TextField.getPassword());

                String query = "select * from login where username = '" + username + "' and pass = '" + password + "'";
                
                ResultSet rs = c1.s.executeQuery(query);
                
                if (!rs.isBeforeFirst()) {
                    error_Frame.setVisible(true);
                    
                } else {
                    rs.next();
                    int id = rs.getInt("id");

                    new Dashboard(id);
                    home_Frame.setVisible(false);

                }
            }else if(ae.getSource() == createAccount_Button){

                new SignUp();
                home_Frame.setVisible(false);

            }

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    
    public static void main(String[] args) {
        new Home();
    }

}


