package gui;

import console.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JButton signupButton;
    private JLabel headerLabel;

    private User user;

    public LoginFrame() {
        setTitle("User Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        headerLabel = new JLabel("Login");
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);

        usernameField = new JTextField(15);
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        formPanel.add(usernameField, gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(15);
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridy++;
        formPanel.add(passwordField, gbc);

        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");
        signupButton = new JButton("Sign Up");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login logic
                // For example, validate credentials and open main application window
                // Replace this with your actual login logic
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all the fields");
                    return;
                }
                if (validateUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successfully");
                    usernameField.setText("");
                    passwordField.setText("");
                    Dashboard dashboard = new Dashboard(user);
                    dashboard.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Login unsuccessful");
                    usernameField.setText("");
                    passwordField.setText("");

                }

            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmed = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to exit the application?", "Exit Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmed == JOptionPane.YES_OPTION) {
                    dispose(); // Close the JFrame
                }
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SignUp signUp = new SignUp();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        buttonPanel.add(exitButton);

        panel.add(headerLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(",");
                if (parts.length == 2) { // Assuming each line has username, password
                    String username = parts[0];
                    String password = parts[1];
                    User user = new User(username, password);
                    users.add(user);
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found: " + e.getMessage());

        }
        return users;
    }

    private boolean validateUser(String username, String password) {
        List<User> users = readUsersFromFile();
        if (!users.isEmpty()) {
            for (User user : users) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    this.user = user;
                    return true; // User found
                }
            }
        }
        return false; // User not found or password incorrect
    }

}
