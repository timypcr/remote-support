package com.justinmichaud.remotesupport.client.ui;

import com.barchart.udt.ExceptionUDT;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.justinmichaud.remotesupport.client.SimpleClient;
import org.bouncycastle.util.io.TeeOutputStream;

import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.net.InetSocketAddress;

public class ConnectForm {

    private JPanel root;
    private JTextField txtDiscoveryServer;
    private JButton connectButton;
    private JTextArea txtConsole;

    public ConnectForm(JFrame frame) {
        frame.setTitle("Remote Support");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 450));
        frame.pack();
        frame.setLocationRelativeTo(null); // Move to center of screen
        frame.setVisible(true);

        txtDiscoveryServer.setText("172.16.1.216");

        JTextAreaOutputStream txtOut = new JTextAreaOutputStream(txtConsole);
        System.setOut(new PrintStream(new TeeOutputStream(System.out, txtOut)));

        connectButton.addActionListener(e -> {
            connectButton.setEnabled(false);

            InetSocketAddress addr = new InetSocketAddress(txtDiscoveryServer.getText(), 40000);

            try {
                SimpleClient client = new SimpleClient(
                        (msg) -> JOptionPane.showInputDialog(
                                frame,
                                msg,
                                "Question",
                                JOptionPane.PLAIN_MESSAGE),
                        (c) -> {
                            frame.setContentPane(new PeerForm(frame, c, txtOut).root);
                            frame.pack();
                        }, addr);
            } catch (ExceptionUDT exceptionUDT) {
                exceptionUDT.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException
                | InstantiationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Remote Support");
        frame.setContentPane(new ConnectForm(frame).root);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        root = new JPanel();
        root.setLayout(new GridLayoutManager(3, 2, new Insets(10, 10, 10, 10), -1, -1));
        root.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(-4473925)), "Remote Support"));
        final JLabel label1 = new JLabel();
        label1.setText("Discovery Server");
        root.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtDiscoveryServer = new JTextField();
        root.add(txtDiscoveryServer, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtConsole = new JTextArea();
        txtConsole.setEditable(false);
        root.add(txtConsole, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        connectButton = new JButton();
        connectButton.setText("Connect");
        root.add(connectButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return root;
    }
}