package project;
import javax.swing.*;

import company.AboutPage;

import java.awt.*;
import java.awt.event.*;

public class about extends JFrame {

    public about() {
        setTitle("About - Hotel Room Management System");
        setSize(500, 400);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Header
        JLabel header = new JLabel("Hotel Room Management System", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setForeground(new Color(41, 128, 185));
        add(header, BorderLayout.NORTH);

        // Description text
        JTextArea aboutText = new JTextArea();
        aboutText.setEditable(false);
        aboutText.setFont(new Font("Arial", Font.PLAIN, 14));
        aboutText.setLineWrap(true);
        aboutText.setWrapStyleWord(true);
        aboutText.setBackground(new Color(245, 245, 245));
        aboutText.setText(
            "The Hotel Room Management System is a desktop application "
          + "designed to simplify and automate hotel operations. It helps staff "
          + "manage room bookings, customer check-ins and check-outs, and monitor "
          + "room availability efficiently.\n\n"
          + "This system improves workflow by reducing manual errors and saving time. "
          + "Users can easily track which rooms are available, occupied, or under maintenance. "
          + "It also provides occupancy statistics and revenue summaries.\n\n"
          + "Developed using Java Swing and event handling, the system offers an intuitive, "
          + "user-friendly interface suitable for small and medium-sized hotels."
        );

        add(new JScrollPane(aboutText), BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(41, 128, 185));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(closeButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AboutPage().setVisible(true));
    }
}



