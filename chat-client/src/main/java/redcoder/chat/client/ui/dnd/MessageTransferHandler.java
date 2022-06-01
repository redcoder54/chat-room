package redcoder.chat.client.ui.dnd;

import redcoder.chat.client.model.ImageMessage;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.client.ui.Framework;
import redcoder.chat.client.ui.RcFrame;
import redcoder.chat.client.utils.FileUtils;
import redcoder.chat.client.utils.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class MessageTransferHandler extends TransferHandler {

    private static final Logger LOGGER = Logger.getLogger(MessageTransferHandler.class.getName());
    private static final String[] options = {"发送", "取消"};

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }

        try {
            if (support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                return importStringData(support);
            } else if (support.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                return importImageData(support);
            } else {
                return importFileData(support);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to export data.", e);
            return false;
        }
    }

    private boolean importStringData(TransferSupport support) throws Exception {
        JTextComponent textComponent = (JTextComponent) support.getComponent();
        Transferable transferable = support.getTransferable();
        String str = (String) transferable.getTransferData(DataFlavor.stringFlavor);

        Document document = textComponent.getDocument();
        if (support.isDrop()) {
            Point point = support.getDropLocation().getDropPoint();
            int offset = textComponent.viewToModel(point);
            document.insertString(offset, str, null);
        } else {
            // paste
            document.insertString(textComponent.getCaretPosition(), str, null);
        }

        return true;
    }

    private boolean importImageData(TransferSupport support) throws Exception {
        RcFrame rcFrame = Framework.getActivatedRcFrame();
        if (!(rcFrame instanceof ChatFrame)) {
            return false;
        }
        ChatFrame chatFrame = (ChatFrame) rcFrame;

        Transferable transferable = support.getTransferable();
        Image image = (Image) transferable.getTransferData(DataFlavor.imageFlavor);
        if (image instanceof BufferedImage) {
            BufferedImage bufferedImage = (BufferedImage) image;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream(1024)) {
                ImageIO.write(bufferedImage, "png", baos);
                sendImageMessage(chatFrame, baos.toByteArray());
            }
        }
        return true;
    }

    private boolean importFileData(TransferSupport support) throws Exception {
        RcFrame rcFrame = Framework.getActivatedRcFrame();
        if (!(rcFrame instanceof ChatFrame)) {
            return false;
        }
        ChatFrame chatFrame = (ChatFrame) rcFrame;

        Transferable transferable = support.getTransferable();
        @SuppressWarnings("unchecked")
        List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

        // 1. 弹出对话框，让用户确认是否发送图文件
        // 2. 如果用户确认，则发送文件消息
        List<String> names = files.stream().map(File::getName).collect(Collectors.toList());
        String message = StringUtils.join(names, "\n");
        int state = JOptionPane.showOptionDialog(chatFrame, message, "发送文件", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (state == JOptionPane.YES_OPTION) {
            for (File file : files) {
                if (ImageIO.read(file) != null) {
                    byte[] bytes = FileUtils.read(file);
                    sendImageMessage(chatFrame, bytes);
                } else {
                    LOGGER.log(Level.WARNING, "Only send image file.");
                }
            }
        }
        return true;
    }

    private void sendImageMessage(ChatFrame chatFrame, byte[] bytes) {
        ImageMessage imageMessage = new ImageMessage(chatFrame.getLoggedUser(), bytes);
        chatFrame.getMessageDisplayPanel().addMessage(imageMessage, true);
        chatFrame.getSender().send(imageMessage);
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.stringFlavor)
                || support.isDataFlavorSupported(DataFlavor.imageFlavor)
                || support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    @Override
    public int getSourceActions(JComponent c) {
        return NONE;
    }
}
