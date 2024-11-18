/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.ipcalculator;

/**
 *
 * @author pirad
 */
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class IPCalculatorGUI extends JFrame {
    private JTextField ipAddressField;
    private JTextField netmaskField;
    private JButton calculateButton;
    private JPanel resultsPanel;
    
    public IPCalculatorGUI() {
        setTitle("IP Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        // Main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        //menambahkan gambar
        ImageIcon iconKalkulator = new ImageIcon(getClass().getResource("/math.png"));
         
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel iconLabel = new JLabel(iconKalkulator);
        JLabel ipLabel = new JLabel("IP");
        ipLabel.setForeground(Color.WHITE);
        ipLabel.setBackground(new Color(219, 39, 119)); // Pink color
        ipLabel.setOpaque(true);
        ipLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
        ipLabel.setFont(new Font("Arial", Font.BOLD, 24));        
        JLabel titleLabel = new JLabel("Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(219, 39, 119));
        titlePanel.add(iconLabel);
        titlePanel.add(ipLabel);
        titlePanel.add(titleLabel);
        titlePanel.setBackground(new Color(255, 181, 213));
        
        //author label in main panel
        JLabel authorLabel = new JLabel("by: oddiv");
        authorLabel.setFont(new Font("Arial", Font.BOLD, 24));
        authorLabel.setForeground(new Color(219, 39, 119));
        titlePanel.add(authorLabel);
        
        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(20, 0, 20, 0),
            BorderFactory.createEtchedBorder()
        ));
        inputPanel.setBackground(new Color(255, 181, 213));
        
        // IP Address input
        JPanel ipPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JLabel ipAddressLabel = new JLabel("IP address");
        ipAddressLabel.setFont(new Font("Arial", Font.PLAIN, 12));           
        ipAddressField = new JTextField(30);
        ipAddressField.setToolTipText("Example: 172.217.16.142");
        ipAddressField.setBackground(new Color(255, 181, 213));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        ipPanel.add(ipAddressLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ipPanel.add(ipAddressField, gbc);
        
        // Netmask input
        JLabel netmaskLabel = new JLabel("Netmask");
        netmaskLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        netmaskField = new JTextField(30);
        netmaskField.setToolTipText("Example: 255.255.255.0 or 24");
        netmaskField.setBackground(new Color(255, 181, 213));
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        ipPanel.add(netmaskLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ipPanel.add(netmaskField, gbc);
        
        inputPanel.add(ipPanel);
        
        // Calculate Button
        calculateButton = new JButton("Calculate ↓");
        calculateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calculateButton.setBackground(new Color(219, 39, 119));
        calculateButton.setForeground(new Color(219, 39, 119));
        calculateButton.setFocusPainted(false);
        calculateButton.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        // Results Panel
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(20, 0, 20, 0),
            BorderFactory.createEtchedBorder()
        ));
        resultsPanel.setBackground(new Color(255, 181, 213));
        resultsPanel.setVisible(false);

        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.CENTER);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(calculateButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(resultsPanel);
        
        // Add listener for calculate button
        calculateButton.addActionListener(e -> calculateIP());
        
        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        setContentPane(scrollPane);
    }
    
    private void calculateIP() {
        try {
            resultsPanel.removeAll();
            
            // Parse IP address
            String[] ipParts = ipAddressField.getText().split("\\.");
            if (ipParts.length != 4) {
                throw new Exception("Invalid IP address format");
            }
            
            IPAddress ipAddress = new IPAddress(ipParts[0], ipParts[1], ipParts[2], ipParts[3]);
            
            // Parse netmask
            String netmaskText = netmaskField.getText();
            if (netmaskText.contains(".")) {
                ipAddress.setNetMask(netmaskText);
            } else {
                ipAddress.setPrefiks(Integer.parseInt(netmaskText));
                ipAddress.setNetMask(Integer.parseInt(netmaskText));
            }
            
            // Calculate results
            IPAddress networkId = ipAddress.getNetworkID();
            IPAddress broadcast = ipAddress.getBroadcast();
            long IPEfektif = ipAddress.getIPEfektif();
            
            // Add results to panel
            addResultRow("Address", ipAddress.toString(), ipAddress.toStringBiner());
            addResultRow("Netmask", ipAddress.getNetmask(),ipAddress.getNetmaskBiner());
            addResultRow("Network", networkId.toString(), networkId.toStringBiner());
            addResultRow("Broadcast", broadcast.toString(), broadcast.toStringBiner());
            addResultRow("IP Efektif",String.valueOf(IPEfektif), "");
            
            resultsPanel.setVisible(true);
            resultsPanel.revalidate();
            resultsPanel.repaint();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Error: " + ex.getMessage(),
                "Calculation Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addResultRow(String label, String value, String value2) {
        JPanel rowPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        rowPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        
        JLabel labelComponent = new JLabel(label);
        labelComponent.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel valueComponent = new JLabel(value);
        valueComponent.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JLabel value2Component = new JLabel(value2);
        valueComponent.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        rowPanel.add(labelComponent);
        rowPanel.add(valueComponent);
        rowPanel.add(value2Component);
        
        resultsPanel.add(rowPanel);
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new IPCalculatorGUI().setVisible(true);
        });
    }
}

