package view.states;

import javax.swing.*;
import java.awt.*;

public abstract class LoginPanel extends JPanel {

    protected JTextField userNameTextField;
    protected JPasswordField passwordField;
    protected JButton loginButton;
    protected JButton signUpButton;
    protected JButton backButton;

    public LoginPanel() {
        setLayout(new GridLayout(3, 1, 50, 30));

        init();
    }

    private void init() {
        userNameTextField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        signUpButton = new JButton("Sign Up");
        backButton = new JButton("Back");

        loginButton.addActionListener(e -> loginAction());
        signUpButton.addActionListener(e -> signUpAction());
        backButton.addActionListener(e -> backAction());

        add(userNameTextField);
        add(passwordField);

        add(userNameTextField);
        add(passwordField);

        JPanel wrapper = new JPanel(new GridLayout(1, 3));
        wrapper.add(loginButton);
        wrapper.add(signUpButton);
        wrapper.add(backButton);

        add(wrapper);

        setBorder(
                BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(200, 450, 200, 450),
                BorderFactory.createLineBorder(Color.GRAY.darker(), 3)),
                        BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    protected abstract void signUpAction();
    protected abstract void loginAction();
    protected abstract void backAction();
}
