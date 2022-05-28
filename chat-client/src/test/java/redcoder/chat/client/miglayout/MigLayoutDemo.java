package redcoder.chat.client.miglayout;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;

public class MigLayoutDemo {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new MigLayout("debug, flowy, fillx", "", ""));
        frame.setContentPane(panel);

        JPanel p1 = new JPanel(new MigLayout("", "", ""));
        p1.add(new JButton("Button1"));
        p1.add(new JButton("Button2"));
        p1.add(new JButton("Button3"), "wrap");
        p1.add(new JTextArea("hello"),"span");

        JPanel p2 = new JPanel(new MigLayout("", "", ""));
        p2.add(new JButton("Button5"));
        p2.add(new JButton("Button6"));
        p2.add(new JButton("Button7"), "wrap");
        p2.add(new JTextArea("hello"),"span");

        panel.add(p1);
        panel.add(p2,"right");

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
