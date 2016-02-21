package org.kaleta.lolstats.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Stanislav Kaleta on 27.07.2015.
 *
 * Dialog window which shows error message (and details) for specified exception.
 */
public class ErrorDialog extends JDialog {
    private Exception exception;
    private JScrollPane exceptionScrollPane;

    public ErrorDialog(Exception exception) {
        this.exception = exception;
        initComponents();
        this.setTitle("Service Failure!");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = this.getSize();
        int centerPosX = (screenSize.width - dialogSize.width) / 2;
        int centerPosY = (screenSize.height - dialogSize.height) / 2;
        this.setLocation(centerPosX, centerPosY);
    }

    private void initComponents() {
        //final BufferedImage errorImage = IconLoader.getIcon(IconLoader.ERROR_ICON); // TODO icon loader + updated via TODO file hint + adds error icons
        JPanel errorIcon = new JPanel(){
            @Override
            protected void paintComponent(Graphics gr) {
                super.paintComponent(gr);
                //if (errorImage != null) {
                  //  gr.drawImage(errorImage, 0, 0, 50, 50, this);
                //}
            }
        };

        JLabel labelError = new JLabel();
        labelError.setText(" A runtime error has occurred! Please send a bug report to the developer. ");
        labelError.setFont(new Font(labelError.getFont().getName(), Font.BOLD, 15));
        labelError.setOpaque(true);
        labelError.setBackground(Color.WHITE);

        JToggleButton toggleButton = new JToggleButton("▿ More Details");
        toggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JToggleButton source = (JToggleButton) e.getSource();
                if (source.isSelected()) {
                    exceptionScrollPane.setVisible(true);
                    //U+25B5 (White up-pointing small triangle ▵)
                    source.setText("▵ Hide Details");
                    ErrorDialog.this.pack();
                } else {
                    exceptionScrollPane.setVisible(false);
                    //U+25BF (White down-pointing small triangle ▿)
                    source.setText("▿ More Details");
                    ErrorDialog.this.pack();
                }
            }
        });

        JButton buttonClose = new JButton("Close");
        buttonClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ErrorDialog.this.dispose();
            }
        });


        JTextArea exceptionTextArea = new JTextArea();
        exceptionTextArea.setEditable(false);

        exceptionTextArea.setText(getExceptionStackTrace(exception));

        exceptionScrollPane = new JScrollPane(exceptionTextArea);
        exceptionScrollPane.setVisible(false);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(errorIcon, 50, 50, 50)
                        .addComponent(labelError)
                        .addGap(10))
                .addGroup(layout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(toggleButton))
                .addComponent(exceptionScrollPane,0,GroupLayout.PREFERRED_SIZE,590)
                .addComponent(buttonClose, GroupLayout.Alignment.CENTER));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(errorIcon, 50, 50, 50)
                        .addComponent(labelError, GroupLayout.Alignment.CENTER, 50, 50, 50))
                .addComponent(toggleButton, 20, 20, 20)
                .addComponent(exceptionScrollPane, 0, GroupLayout.PREFERRED_SIZE, 300)
                .addGap(20)
                .addComponent(buttonClose)
                .addGap(10));
    }

    public String getExceptionStackTrace(){
        return getExceptionStackTrace(exception);
    }

    /**
     * Returns stack trace for specified exception as String.
     */
    public static String getExceptionStackTrace(Exception exception){
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        exception.printStackTrace(printWriter);
        return writer.toString();
    }
}
