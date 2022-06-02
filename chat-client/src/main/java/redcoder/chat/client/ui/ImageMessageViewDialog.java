package redcoder.chat.client.ui;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.ImageMessage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

public class ImageMessageViewDialog extends JDialog {

    private static final Map<String, JDialog> DIALOGS = new HashMap<>();

    private ImageMessageViewDialog(ImageMessage imageMessage) {
        super(Framework.getActivatedRcFrame(), "图片查看", false);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new MigLayout());
        setLocationRelativeTo(null);
        add(new JLabel(new ImageIcon((imageMessage).getImageData())), "center");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DIALOGS.remove(imageMessage.getMsgId());
            }
        });
        pack();
        setVisible(true);
    }

    public static void showDialog(ImageMessage imageMessage) {
        String msgId = imageMessage.getMsgId();
        if (DIALOGS.containsKey(msgId)) {
            return;
        }
        JDialog dialog = new ImageMessageViewDialog(imageMessage);
        DIALOGS.put(msgId, dialog);
    }
}
