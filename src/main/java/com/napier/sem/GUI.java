package com.napier.sem;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Component;

public class GUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /** CONSTRUCTOR
     * Create the frame.
     */
    public GUI() {
        setLNF();

        setTitle("Population Reports 1.0");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));


        JPanel PopulationReportPanel = new JPanel();
        JPanel CityReportPanel = new JPanel();
        JPanel CapitalReportPanel = new JPanel();
        JPanel CountryReportPanel = new JPanel();
        JPanel LanguageReportPanel = new JPanel();


        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.addTab("Population Reports", PopulationReportPanel);
        PopulationReportPanel.setLayout(new BorderLayout(0, 0));

        JSplitPane PRTitleSplit = new JSplitPane();
        PRTitleSplit.setEnabled(false);
        PRTitleSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        PRTitleSplit.setDividerLocation(100);
        PopulationReportPanel.add(PRTitleSplit);

        JPanel PRContentPanel = new JPanel();
        PRTitleSplit.setRightComponent(PRContentPanel);
        PRContentPanel.setLayout(new BoxLayout(PRContentPanel, BoxLayout.Y_AXIS));

        JSplitPane PRContentSplit = new JSplitPane();
        PRContentSplit.setEnabled(false);
        PRContentSplit.setAlignmentX(Component.CENTER_ALIGNMENT);
        PRContentSplit.setAlignmentY(Component.CENTER_ALIGNMENT);
        PRContentSplit.setOrientation(JSplitPane.VERTICAL_SPLIT);
        PRContentPanel.add(PRContentSplit);

        JPanel PRControlsPanel = new JPanel();
        PRContentSplit.setLeftComponent(PRControlsPanel);
        PRControlsPanel.setLayout(new BoxLayout(PRControlsPanel, BoxLayout.X_AXIS));

        JPanel PRControlsLabelPanel = new JPanel();
        PRControlsPanel.add(PRControlsLabelPanel);
        PRControlsLabelPanel.setLayout(new GridLayout(0, 1, 0, 0));

        JLabel PRContinentLabel = new JLabel("Continent:");
        PRContinentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        PRControlsLabelPanel.add(PRContinentLabel);

        JLabel PRRegionLabel = new JLabel("Region:");
        PRRegionLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        PRContinentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        PRControlsLabelPanel.add(PRRegionLabel);

        JLabel PRCountryLabel = new JLabel("Country:");
        PRCountryLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        PRContinentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        PRControlsLabelPanel.add(PRCountryLabel);

        JLabel PRDistrictLabel = new JLabel("District:");
        PRDistrictLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        PRContinentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        PRControlsLabelPanel.add(PRDistrictLabel);

        JLabel PRCityLabel = new JLabel("City:");
        PRCityLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        PRCityLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        PRContinentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        PRControlsLabelPanel.add(PRCityLabel);

        JPanel PRControlsComboPanel = new JPanel();
        PRControlsPanel.add(PRControlsComboPanel);
        PRControlsComboPanel.setLayout(new BoxLayout(PRControlsComboPanel, BoxLayout.Y_AXIS));

        JComboBox<String> PRContinentCombo = new JComboBox<String>();
        PRControlsComboPanel.add(PRContinentCombo);

        JComboBox<String> PRRegionCombo = new JComboBox<String>();
        PRControlsComboPanel.add(PRRegionCombo);

        JComboBox<String> PRCountryCombo = new JComboBox<String>();
        PRControlsComboPanel.add(PRCountryCombo);

        JComboBox<String> PRDistrictCombo = new JComboBox<String>();
        PRControlsComboPanel.add(PRDistrictCombo);

        JComboBox<String> PRCityCombo = new JComboBox<String>();
        PRControlsComboPanel.add(PRCityCombo);

        JPanel PRControlsButtonPanel = new JPanel();
        PRControlsPanel.add(PRControlsButtonPanel);
        PRControlsButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton PRGenerateButton = new JButton("Generate Report");
        PRControlsButtonPanel.add(PRGenerateButton);

        JPanel PROutputPanel = new JPanel();
        PRContentSplit.setRightComponent(PROutputPanel);
        PROutputPanel.setLayout(new BoxLayout(PROutputPanel, BoxLayout.X_AXIS));

        JTextArea textArea = new JTextArea();
        PROutputPanel.add(textArea);

        JPanel PRTitlePanel = new JPanel();
        PRTitleSplit.setLeftComponent(PRTitlePanel);
        PRTitlePanel.setLayout(new BoxLayout(PRTitlePanel, BoxLayout.Y_AXIS));

        JPanel PRTitleLabelPanel = new JPanel();
        PRTitlePanel.add(PRTitleLabelPanel);

        JLabel PopulationReportsPanelLabel = new JLabel("Population Reports");
        PRTitleLabelPanel.add(PopulationReportsPanelLabel);
        PopulationReportsPanelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        PopulationReportsPanelLabel.setHorizontalAlignment(SwingConstants.CENTER);
        PopulationReportsPanelLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));

        JPanel PRTitleWorldPopPanel = new JPanel();
        PRTitlePanel.add(PRTitleWorldPopPanel);

        JLabel WorldPopLabel = new JLabel("[set to display world pop on UI instantiation]");
        PRTitleWorldPopPanel.add(WorldPopLabel);
        tabbedPane.addTab("Cities", CityReportPanel);
        tabbedPane.addTab("Capitals", CapitalReportPanel);
        tabbedPane.addTab("Countries", CountryReportPanel);
        tabbedPane.addTab("Languages", LanguageReportPanel);
        tabbedPane.setTabPlacement(JTabbedPane.LEFT);

        contentPane.add(tabbedPane);
        setContentPane(contentPane);
    }

    public void setLNF() {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {  // <-- iterates through all installed look and feels...
            if ("Nimbus".equals(info.getName())) {							 // <-- if the current one has the name "Nimbus" (which is one of the default installed ones)...
                try {
                    UIManager.setLookAndFeel(info.getClassName());			 // <-- tries to set look and feel....
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) { // <-- or catch these exception types...
                    System.out.println("Error loading window LNF skin!");
                }													   // It did not cause an exception when testing invalid LNF names, and the window is still created with the default LNF. Being unalterable by the
                break;												   // user, and with "Nimbus" being a default LNF it is safe to assume that this won't cause any problems/exceptions.
            }
        }
    }

}
