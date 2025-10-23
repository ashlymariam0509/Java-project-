package com.parking.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Room {
    private int roomNumber;
    private String roomType;
    private String status;
    private double price;
    private String guestName;
    private String guestPhone;
    
    public Room(int roomNumber, String roomType, double price) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.status = "Available";
        this.guestName = "";
        this.guestPhone = "";
    }
    
    public int getRoomNumber() { return roomNumber; }
    public String getRoomType() { return roomType; }
    public String getStatus() { return status; }
    public double getPrice() { return price; }
    public String getGuestName() { return guestName; }
    public String getGuestPhone() { return guestPhone; }
    
    public void setStatus(String status) { this.status = status; }
    public void setGuestName(String guestName) { this.guestName = guestName; }
    public void setGuestPhone(String guestPhone) { this.guestPhone = guestPhone; }
}

class RoomPanel extends JPanel {
    private Room room;
    private JLabel roomLabel;
    private JLabel statusLabel;
    private JButton actionButton;
    private JButton maintenanceButton;
    
    public RoomPanel(Room room, ActionListener bookListener, ActionListener maintenanceListener) {
        this.room = room;
        setLayout(new BorderLayout(5, 5));
        setPreferredSize(new Dimension(150, 140));
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        
        roomLabel = new JLabel("Room " + room.getRoomNumber());
        roomLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel typeLabel = new JLabel(room.getRoomType());
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        JLabel priceLabel = new JLabel("₹" + room.getPrice() + "/night");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        
        statusLabel = new JLabel(room.getStatus());
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        infoPanel.add(roomLabel);
        infoPanel.add(typeLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(statusLabel);
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        actionButton = new JButton();
        actionButton.addActionListener(bookListener);
        maintenanceButton = new JButton("Maintenance");
        maintenanceButton.setFont(new Font("Arial", Font.PLAIN, 10));
        maintenanceButton.addActionListener(maintenanceListener);
        
        buttonPanel.add(actionButton);
        buttonPanel.add(maintenanceButton);
        
        add(infoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        updateDisplay();
    }
    
    public void updateDisplay() {
        switch(room.getStatus()) {
            case "Available":
                setBackground(new Color(144, 238, 144));
                statusLabel.setForeground(new Color(0, 128, 0));
                actionButton.setText("Book Room");
                actionButton.setEnabled(true);
                maintenanceButton.setEnabled(true);
                break;
            case "Occupied":
                setBackground(new Color(255, 182, 193));
                statusLabel.setForeground(new Color(178, 34, 34));
                actionButton.setText("Check Out");
                actionButton.setEnabled(true);
                maintenanceButton.setEnabled(false);
                break;
            case "Maintenance":
                setBackground(new Color(255, 255, 153));
                statusLabel.setForeground(new Color(184, 134, 11));
                actionButton.setText("Unavailable");
                actionButton.setEnabled(false);
                maintenanceButton.setText("End Maintenance");
                maintenanceButton.setEnabled(true);
                break;
        }
        statusLabel.setText(room.getStatus());
    }
    
    public Room getRoom() {
        return room;
    }
}
public class HotelRoomManagementUI extends JFrame {
    private ArrayList<Room> rooms;
    private ArrayList<RoomPanel> roomPanels;
    private JPanel roomsDisplayPanel;
    private JLabel statsLabel;
    
    public HotelRoomManagementUI() {
        setTitle("Hotel Room Management System");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        initializeRooms();
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        roomsDisplayPanel = new JPanel();
        roomsDisplayPanel.setLayout(new GridLayout(0, 4, 10, 10));
        roomsDisplayPanel.setBackground(Color.WHITE);
        
        displayRooms();
        
        JScrollPane scrollPane = new JScrollPane(roomsDisplayPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void initializeRooms() {
        rooms = new ArrayList<>();
        roomPanels = new ArrayList<>();
        for (int i = 101; i <= 106; i++) {
            rooms.add(new Room(i, "Single", 1500));
        }
        for (int i = 201; i <= 208; i++) {
            rooms.add(new Room(i, "Double", 2500));
        }
        for (int i = 301; i <= 304; i++) {
            rooms.add(new Room(i, "Suite", 5000));
        }
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(41, 128, 185));
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel titleLabel = new JLabel("Hotel Room Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateStats());
        
        panel.add(titleLabel, BorderLayout.WEST);
        panel.add(refreshButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private void displayRooms() {
        roomsDisplayPanel.removeAll();
        roomPanels.clear();
        
        for (Room room : rooms) {
            RoomPanel roomPanel = new RoomPanel(room, 
                new RoomActionListener(room),
                new MaintenanceActionListener(room));
            roomPanels.add(roomPanel);
            roomsDisplayPanel.add(roomPanel);
        }
        
        roomsDisplayPanel.revalidate();
        roomsDisplayPanel.repaint();
        this.statsLabel = new javax.swing.JLabel(); 
        updateStats();
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        statsLabel = new JLabel();
        statsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        updateStats();
        
        panel.add(statsLabel);
        return panel;
    }
    
    private void updateStats() {
        int available = 0, occupied = 0, maintenance = 0;
        double totalRevenue = 0;
        
        for (Room room : rooms) {
            switch(room.getStatus()) {
                case "Available": available++; break;
                case "Occupied": 
                    occupied++; 
                    totalRevenue += room.getPrice();
                    break;
                case "Maintenance": maintenance++; break;
            }
        }
        
        int total = rooms.size();
        double occupancy = (occupied * 100.0) / total;
        
        statsLabel.setText(String.format(
            "Total Rooms: %d  |  Available: %d  |  Occupied: %d  |  Maintenance: %d  |  Occupancy: %.1f%%  |  Daily Revenue: ₹%.2f",
            total, available, occupied, maintenance, occupancy, totalRevenue
        ));
    }
    
   
    class RoomActionListener implements ActionListener {
        private Room room;
        
        public RoomActionListener(Room room) {
            this.room = room;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (room.getStatus().equals("Available")) {
                bookRoom(room);
            } else if (room.getStatus().equals("Occupied")) {
                checkoutRoom(room);
            }
        }
    }
    
  
    class MaintenanceActionListener implements ActionListener {
        private Room room;
        
        public MaintenanceActionListener(Room room) {
            this.room = room;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (room.getStatus().equals("Maintenance")) {
                endMaintenance(room);
            } else if (room.getStatus().equals("Available")) {
                startMaintenance(room);
            }
        }
    }
    
    private void bookRoom(Room room) {
        JTextField guestNameField = new JTextField(20);
        JTextField guestPhoneField = new JTextField(20);
        
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel("Guest Name:"));
        panel.add(guestNameField);
        panel.add(new JLabel("Phone Number:"));
        panel.add(guestPhoneField);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Book Room " + room.getRoomNumber(), JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String guestName = guestNameField.getText().trim();
            String guestPhone = guestPhoneField.getText().trim();
            
            if (!guestName.isEmpty() && !guestPhone.isEmpty()) {
                room.setStatus("Occupied");
                room.setGuestName(guestName);
                room.setGuestPhone(guestPhone);
                
                updateRoomDisplay(room);
                updateStats();
                
                JOptionPane.showMessageDialog(this, 
                    "Room " + room.getRoomNumber() + " booked successfully!\n" +
                    "Guest: " + guestName + "\nPhone: " + guestPhone + "\n" +
                    "Rate: ₹" + room.getPrice() + "/night",
                    "Booking Confirmed", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Please enter all guest details!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void checkoutRoom(Room room) {
        int result = JOptionPane.showConfirmDialog(this, 
            "Checkout room " + room.getRoomNumber() + "?\n\n" +
            "Guest: " + room.getGuestName() + "\n" +
            "Phone: " + room.getGuestPhone() + "\n" +
            "Room Rate: ₹" + room.getPrice() + "/night",
            "Confirm Checkout", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            room.setStatus("Available");
            room.setGuestName("");
            room.setGuestPhone("");
            
            updateRoomDisplay(room);
            updateStats();
            
            JOptionPane.showMessageDialog(this, 
                "Room " + room.getRoomNumber() + " checked out successfully!",
                "Checkout Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void startMaintenance(Room room) {
        int result = JOptionPane.showConfirmDialog(this,
            "Set room " + room.getRoomNumber() + " to maintenance mode?",
            "Start Maintenance", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            room.setStatus("Maintenance");
            updateRoomDisplay(room);
            updateStats();
        }
    }
    
    private void endMaintenance(Room room) {
        int result = JOptionPane.showConfirmDialog(this,
            "End maintenance for room " + room.getRoomNumber() + "?",
            "End Maintenance", JOptionPane.YES_NO_OPTION);
        
        if (result == JOptionPane.YES_OPTION) {
            room.setStatus("Available");
            updateRoomDisplay(room);
            updateStats();
        }
    }
    
    private void updateRoomDisplay(Room room) {
        for (RoomPanel panel : roomPanels) {
            if (panel.getRoom() == room) {
                panel.updateDisplay();
                break;
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelRoomManagementUI());
    }
}
