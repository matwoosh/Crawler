package pl.edu.agh.frazeusz.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wojtek on 2017-01-03.
 */

public class CCPanel {
    private int nrOfDefaultThreads = Runtime.getRuntime().availableProcessors();
    private int nrOfDefaultDepth = 2;
    private int nrOfChosenThreads;
    private int nrOfChosenDepth;
    private List<String> urls;

    private JFrame f;

    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel labelLinks;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JLabel labelThreads1;
    private JSpinner spinnerThreads;
    private JLabel labelDepth1;
    private JSpinner spinnerDepth;
    private JLabel labelThreads2;
    private JLabel labelDepth2;
    private JPanel buttonBar;
    private JButton buttonStart;
    private JButton buttonReset;

    public CCPanel(JFrame f) {
        this.f = f;
        nrOfChosenThreads = nrOfDefaultThreads;
        nrOfChosenDepth = nrOfDefaultDepth;
        urls = new ArrayList<>();

        initComponents();
    }

    private void initComponents() {
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        labelLinks = new JLabel();
        scrollPane = new JScrollPane();
        textArea = new JTextArea();
        labelThreads1 = new JLabel();
        spinnerThreads = new JSpinner();
        labelDepth1 = new JLabel();
        spinnerDepth = new JSpinner();
        labelThreads2 = new JLabel();
        labelDepth2 = new JLabel();
        buttonBar = new JPanel();
        buttonStart = new JButton();
        buttonReset = new JButton();

        //======== this ========
        Container contentPane = f.getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                //---- labelLinks ----
                labelLinks.setText("Input links:");

                //---- scrollPane ----
                scrollPane.setViewportView(textArea);

                //---- labelThreads1 ----
                labelThreads1.setText("Threads:");

                //---- spinnerThreads ----
                final SpinnerModel valueOfThreads =
                        new SpinnerNumberModel(nrOfDefaultThreads, //initial value
                                1,  //min
                                10000,  //max
                                1);  //step

                spinnerThreads.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        nrOfChosenThreads = (int) ((JSpinner) e.getSource()).getValue();
                    }
                });


                //---- labelDepth1 ----
                labelDepth1.setText("Depth:");

                //---- spinnerDepth ----
                SpinnerModel valueOfDepth =
                        new SpinnerNumberModel(nrOfDefaultDepth, //initial value
                                1,  //min
                                10,  //max
                                1);  //step
                spinnerDepth.setModel(valueOfDepth);

                spinnerDepth.addChangeListener(new ChangeListener() {
                    public void stateChanged(ChangeEvent e) {
                        nrOfChosenDepth = (int) ((JSpinner) e.getSource()).getValue();
                    }
                });

                //---- labelThreads2 ----
                labelThreads2.setText("1 - 10 000 (default: " + nrOfDefaultThreads + ")");

                //---- labelDepth2 ----
                labelDepth2.setText("1 - 10 (default: " + nrOfDefaultDepth + ")");

                //---- size of spinners ----
                spinnerThreads.setModel(valueOfThreads);
                JComponent editor = spinnerThreads.getEditor();
                JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
                tf.setColumns(4);

                // ===== buttonBar =====
                {
                    buttonBar.setBorder(new EmptyBorder(5, 0, 0, 0));
                    buttonBar.setLayout(new GridBagLayout());
                    ((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[]{0, 0, 0, 0, 40};
                    ((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0};

                    //---- buttonStart ----
                    buttonStart.setText("Start");
                    buttonStart.addActionListener(new startListener());

                    buttonBar.add(buttonStart, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 5), 0, 0));

                    //---- buttonReset ----
                    buttonReset.setText("Reset");
                    buttonBar.add(buttonReset, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    buttonReset.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            textArea.setText("");
                            spinnerThreads.setValue(nrOfDefaultThreads);
                            spinnerDepth.setValue(nrOfDefaultDepth);
                        }
                    });
                }

                // ===== LAYOUT =====
                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addComponent(scrollPane)
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                        .addGroup(contentPanelLayout.createParallelGroup()
                                                .addComponent(labelLinks)
                                                .addGroup(contentPanelLayout.createSequentialGroup()
                                                        .addGroup(contentPanelLayout.createParallelGroup()
                                                                .addComponent(labelThreads1)
                                                                .addComponent(labelDepth1))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(contentPanelLayout.createParallelGroup()
                                                                .addComponent(spinnerThreads, GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                                                .addComponent(spinnerDepth))))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(contentPanelLayout.createParallelGroup()
                                                .addComponent(labelThreads2)
                                                .addComponent(labelDepth2))
                                        .addGap(158, 158, 158))
                                .addComponent(buttonBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                contentPanelLayout.setVerticalGroup(
                        contentPanelLayout.createParallelGroup()
                                .addGroup(contentPanelLayout.createSequentialGroup()
                                        .addComponent(labelLinks)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(labelThreads1)
                                                .addComponent(spinnerThreads)
                                                .addComponent(labelThreads2))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(labelDepth1)
                                                .addComponent(spinnerDepth)
                                                .addComponent(labelDepth2))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(buttonBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                );
            }
            dialogPane.add(contentPanel, BorderLayout.NORTH);
        }

        contentPane.add(dialogPane, BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(f.getOwner());
    }

    public class startListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            buttonReset.setEnabled(false);
            textArea.setEnabled(false);
            spinnerThreads.setEnabled(false);
            spinnerDepth.setEnabled(false);

            for (String line : textArea.getText().split("\\n"))
                if (!line.isEmpty())
                    urls.add(line);

            System.out.println("before");

            // TODO - run Crawler here
            // TODO - support Concurrency
            int countdown = 1;
            while (countdown < 5) {
                System.out.println(countdown);
                ++countdown;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }

//            Thread thread = new Thread();
//            thread.start();
//            try {
//                thread.sleep(2000);
//                thread.join();
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }

            System.out.println("after");

            urls.clear();
//            buttonReset.setEnabled(true);
//            textArea.setEnabled(true);
//            spinnerThreads.setEnabled(true);
//            spinnerDepth.setEnabled(true);
        }
    }
}
