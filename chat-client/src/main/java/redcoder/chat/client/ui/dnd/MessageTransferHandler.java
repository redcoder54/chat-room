package redcoder.chat.client.ui.dnd;

import redcoder.chat.client.model.ImageMessage;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.client.ui.Framework;
import redcoder.chat.client.ui.RcFrame;
import redcoder.chat.client.utils.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
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

    private Position startPos;
    private Position endPos;

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
            Caret caret = textComponent.getCaret();
            int dot = caret.getDot();
            int mark = caret.getMark();
            if (dot != mark) {
                // remove selected text
                document.remove(Math.min(dot, mark), Math.abs(dot - mark));
            }
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
            sendImageMessage(chatFrame, bufferedImage);
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
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    sendImageMessage(chatFrame, image);
                } else {
                    LOGGER.log(Level.WARNING, "Only send image file.");
                }
            }
        }
        return true;
    }

    private void sendImageMessage(ChatFrame chatFrame, BufferedImage bufferedImage) throws Exception {
        byte[] bytes;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(1024)) {
            ImageIO.write(bufferedImage, "png", baos);
            bytes = baos.toByteArray();
        }
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
        return ((JTextComponent) c).isEditable() ? COPY_OR_MOVE : COPY;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        if (!(c instanceof JTextComponent)) {
            return null;
        }
        JTextComponent textComponent = (JTextComponent) c;
        int start = textComponent.getSelectionStart();
        int end = textComponent.getSelectionEnd();
        if (start == end) {
            return null;
        }
        Document doc = textComponent.getDocument();
        try {
            startPos = doc.createPosition(start);
            endPos = doc.createPosition(end);
        } catch (BadLocationException e) {
            LOGGER.log(Level.WARNING, "Can't create position.");
        }
        String selectedText = textComponent.getSelectedText();
        return new StringSelection(selectedText);
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if (!(source instanceof JTextComponent)) {
            return;
        }
        if (action != MOVE) {
            return;
        }
        if (startPos != null && endPos != null && startPos.getOffset() != endPos.getOffset()) {
            try {
                JTextComponent textComponent = (JTextComponent) source;
                textComponent.getDocument().remove(startPos.getOffset(), endPos.getOffset() - startPos.getOffset());
            } catch (BadLocationException e) {
                LOGGER.log(Level.WARNING, "Failed to remove exported content.", e);
            }
        }
    }
}
