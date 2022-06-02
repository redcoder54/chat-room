package redcoder.chat.client.message;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import redcoder.chat.client.model.*;
import redcoder.chat.core.model.RcMessage;
import redcoder.chat.core.model.RcUser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageSender {

    private final static Logger LOGGER = Logger.getLogger(MessageSender.class.getName());

    private ChannelHandlerContext ctx;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void send(Message message) {
        send(message, false);
    }

    public void send(Message message, boolean sync) {
        if (ctx == null) {
            LOGGER.log(Level.SEVERE, "消息发送失败：未设置ChannelHandlerContext，请确认是否已连接到服务器？");
            return;
        }
        ChannelFuture future = ctx.writeAndFlush(convertTo(message));
        if (sync) {
            try {
                future.sync();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    private RcMessage convertTo(Message message) {
        User user = message.getUser();
        RcUser rcUser = new RcUser(user.getUid(), user.getNickname(), user.getAvatarName());
        if (message instanceof OnlineMessage) {
            OnlineMessage onlineMessage = (OnlineMessage) message;
            return new RcMessage(RcMessage.ONLINE_MESSAGE, rcUser, onlineMessage.getMsgId(), onlineMessage.getMsg());
        }
        if (message instanceof OfflineMessage) {
            OfflineMessage offlineMessage = (OfflineMessage) message;
            return new RcMessage(RcMessage.OFFLINE_MESSAGE, rcUser, offlineMessage.getMsgId(), offlineMessage.getMsg());
        }
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            return new RcMessage(RcMessage.TEXT_MESSAGE, rcUser, textMessage.getMsgId(), textMessage.getMsg());
        }
        if (message instanceof ImageMessage) {
            ImageMessage imageMessage = (ImageMessage) message;
            return new RcMessage(RcMessage.IMAGE_MESSAGE, rcUser, imageMessage.getMsgId(), imageMessage.getImageData());
        }
        throw new IllegalStateException("无法处理的消息: " + message);
    }
}
