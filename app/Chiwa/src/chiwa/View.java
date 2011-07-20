/*
 * View.java
 */
package chiwa;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class View extends FrameView {

    private Chart updateableChart;

    public View(SingleFrameApplication app) {
        super(app);

        initComponents();
        getFrame().pack();
        getFrame().setResizable(false);
        Chart quicksort = new Chart("quicksort");        
        mainPanel.add(quicksort.getChartComponent(), new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));
        updateableChart = quicksort;
        
        Chart bubblesort = new Chart("bubblesort");
        mainPanel.add(bubblesort.getChartComponent(), new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 70, -1, -1));

        Chart mergeort = new Chart("mergesort");
        mainPanel.add(mergeort.getChartComponent(), new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, -1, -1));

        Chart heapsort = new Chart("heapsort");
        mainPanel.add(heapsort.getChartComponent(), new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));

        Chart insertionsort = new Chart("insertionsort");
        mainPanel.add(insertionsort.getChartComponent(), new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 240, -1, -1));

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();

        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (updateableChart.isRunning()) {
                    statusMessageLabel.setText("Running");
                } else {
                    statusMessageLabel.setText("Halted");
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon); 
                    runButton.setEnabled(true);
                }
            }
        }).start();

    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = App.getApplication().getMainFrame();
            aboutBox = new AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        App.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        runButton = new javax.swing.JButton();
        stepButton = new javax.swing.JButton();
        pauseButton = new javax.swing.JToggleButton();
        jComboBox1 = new javax.swing.JComboBox();
        problemSizeSlider = new javax.swing.JSlider();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        problemSetSizeIndicator = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        updateProblemSetButton = new javax.swing.JToggleButton();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setMaximumSize(mainPanel.getPreferredSize());
        mainPanel.setMinimumSize(mainPanel.getPreferredSize());
        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(990, 500));
        mainPanel.setSize(mainPanel.getPreferredSize());
        mainPanel.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                mainPanelComponentAdded(evt);
            }
        });
        mainPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(chiwa.App.class).getContext().getResourceMap(View.class);
        runButton.setText(resourceMap.getString("runButton.text")); // NOI18N
        runButton.setName("runButton"); // NOI18N
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });
        mainPanel.add(runButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 470, -1, -1));

        stepButton.setText(resourceMap.getString("stepButton.text")); // NOI18N
        stepButton.setName("stepButton"); // NOI18N
        mainPanel.add(stepButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 470, -1, -1));

        pauseButton.setText(resourceMap.getString("pauseButton.text")); // NOI18N
        pauseButton.setName("pauseButton"); // NOI18N
        pauseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseButtonActionPerformed(evt);
            }
        });
        mainPanel.add(pauseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, -1));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Random", "Reverse", "Flat" }));
        jComboBox1.setName("jComboBox1"); // NOI18N
        mainPanel.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 25, -1, -1));

        problemSizeSlider.setMajorTickSpacing(20);
        problemSizeSlider.setMaximum(50);
        problemSizeSlider.setMinimum(10);
        problemSizeSlider.setMinorTickSpacing(10);
        problemSizeSlider.setSnapToTicks(true);
        problemSizeSlider.setValue(20);
        problemSizeSlider.setName("problemSetSize"); // NOI18N
        problemSizeSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                problemSetSizeChangeHandler(evt);
            }
        });
        mainPanel.add(problemSizeSlider, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 24, -1, -1));
        problemSetSizeIndicator.setText("("+problemSizeSlider.getValue()+")");

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel1.setName("jLabel1"); // NOI18N
        mainPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N
        mainPanel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        problemSetSizeIndicator.setText(resourceMap.getString("problemSetSizeIndicator.text")); // NOI18N
        problemSetSizeIndicator.setName("problemSetSizeIndicator"); // NOI18N
        mainPanel.add(problemSetSizeIndicator, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 30, -1, -1));
        problemSetSizeIndicator.setText("("+problemSizeSlider.getValue()+")");
        problemSetSizeIndicator.getAccessibleContext().setAccessibleName(resourceMap.getString("problemSetSizeIndicator.AccessibleContext.accessibleName")); // NOI18N

        jSeparator1.setName("jSeparator1"); // NOI18N
        mainPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 930, -1));

        updateProblemSetButton.setText("Update");
        updateProblemSetButton.setName("updateProblemSetButton"); // NOI18N
        updateProblemSetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateProblemSetButtonActionPerformed(evt);
            }
        });
        mainPanel.add(updateProblemSetButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 25, -1, -1));

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(chiwa.App.class).getContext().getActionMap(View.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
            .add(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(statusMessageLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 794, Short.MAX_VALUE)
                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(statusMessageLabel)
                    .add(statusAnimationLabel)
                    .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    /*
     * 
     */
    private void problemSetSizeChangeHandler(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_problemSetSizeChangeHandler
        problemSetSizeIndicator.setText("(" + problemSizeSlider.getValue() + ")");
    }//GEN-LAST:event_problemSetSizeChangeHandler

    private void mainPanelComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_mainPanelComponentAdded
        // debugging
        System.out.println(evt.getChild());
    }//GEN-LAST:event_mainPanelComponentAdded

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        stepButton.setEnabled(false);
        progressBar.setVisible(true);
        runButton.setEnabled(false);
        if (!busyIconTimer.isRunning()) {
            statusAnimationLabel.setIcon(busyIcons[0]);
            busyIconIndex = 0;
            busyIconTimer.start();
        }
        statusMessageLabel.setText("Running");
        progressBar.setVisible(false);
        stepButton.setEnabled(true);
        updateableChart.exampleRunLoop();
    }//GEN-LAST:event_runButtonActionPerformed

    private void pauseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseButtonActionPerformed
        stepButton.setEnabled(true);
    }//GEN-LAST:event_pauseButtonActionPerformed

    public void clearStatusMessageLabel() {
        statusAnimationLabel.setText("");
    }

    private void updateProblemSetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateProblemSetButtonActionPerformed
        updateableChart.updatePoints(problemSizeSlider.getValue());
        updateProblemSetButton.setSelected(false);
    }//GEN-LAST:event_updateProblemSetButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSeparator jSeparator1;
    public javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JToggleButton pauseButton;
    private javax.swing.JLabel problemSetSizeIndicator;
    private javax.swing.JSlider problemSizeSlider;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JButton runButton;
    private javax.swing.JLabel statusAnimationLabel;
    javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JButton stepButton;
    private javax.swing.JToggleButton updateProblemSetButton;
    // End of variables declaration//GEN-END:variables
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
}