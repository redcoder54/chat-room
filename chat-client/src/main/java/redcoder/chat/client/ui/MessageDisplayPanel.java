package redcoder.chat.client.ui;

import net.miginfocom.swing.MigLayout;
import redcoder.chat.client.model.ImageMessage;
import redcoder.chat.client.model.Message;
import redcoder.chat.client.resource.IconResource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageDisplayPanel extends JScrollPane {

    private static final Logger LOGGER = Logger.getLogger(MessageDisplayPanel.class.getName());
    private static final Font FONT = new Font("null", Font.BOLD, 16);
    private final JPanel contentPane;

    public MessageDisplayPanel() {
        super();
        contentPane = new JPanel();
        contentPane.setLayout(new MigLayout("flowy, fillx"));

        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        setViewportView(contentPane);
        setAutoscrolls(true);
    }

    public void addMessage(Message message, boolean isMe) {
        JLabel avatarLabel = new JLabel(message.getUser().getAvatarIcon());

        JLabel nicknameLabel = new JLabel(message.getUser().getNickname());
        nicknameLabel.setFont(new Font(null, Font.PLAIN, 12));

        JComponent msgComp = createMessageComponent(message);

        JPanel p = new JPanel(new MigLayout());
        p.add(avatarLabel, "span 1 2, top");
        p.add(nicknameLabel, "span, wrap");
        p.add(msgComp, "span");

        if (isMe) {
            contentPane.add(p, "right");
        } else {
            contentPane.add(p, "left");
        }
        contentPane.validate();
        SwingUtilities.invokeLater(() -> getViewport().scrollRectToVisible(new Rectangle(p.getX(), p.getY(), p.getWidth(), p.getHeight())));
    }

    private JComponent createMessageComponent(Message message) {
        JTextPane textPane = new JTextPane();
        // textPane.setContentType("text/html");
        textPane.setMaximumSize(new Dimension(contentPane.getWidth() - 150, Short.MAX_VALUE));
        textPane.setEditable(false);
        textPane.setFont(FONT);

        if (message instanceof ImageMessage) {
            ImageMessage imageMessage = (ImageMessage) message;
            Image thumbnail = createThumbnail(imageMessage.getImageData());
            if (thumbnail == null) {
                textPane.insertIcon(IconResource.getImageIcon("expire_pic.png"));
            } else {
                textPane.insertIcon(new ImageIcon(thumbnail));
                textPane.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        JDialog dialog = new JDialog(Framework.getActivatedRcFrame(), "图片查看", false);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        dialog.setLayout(new MigLayout());
                        dialog.setLocationRelativeTo(null);
                        dialog.add(new JLabel(new ImageIcon(((ImageMessage) message).getImageData())), "center");
                        dialog.pack();
                        dialog.setVisible(true);
                    }
                });
            }
        } else {
            textPane.setText(message.getMsg());
        }

        return textPane;
    }

    private Image createThumbnail(byte[] imageData) {
        try {
            ByteArrayInputStream bain = new ByteArrayInputStream(imageData);
            BufferedImage image = ImageIO.read(bain);
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();
            int tThumbWidth = Math.min(100, originWidth);
            int tThumbHeight = Math.min(100, originHeight);

            BufferedImage tThumbImage = new BufferedImage(tThumbWidth, tThumbHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = tThumbImage.createGraphics(); // create a graphics object to paint to
            graphics2D.setBackground(Color.WHITE);
            graphics2D.setPaint(Color.WHITE);
            graphics2D.fillRect(0, 0, tThumbWidth, tThumbHeight);
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            graphics2D.drawImage(image, 0, 0, tThumbWidth, tThumbHeight, null); // draw the image scaled

            // ImageIO.write(tThumbImage, "png", tThumbnailTarget);
            return tThumbImage;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to thumbnail", e);
            return null;
        }
    }
}
