package redcoder.chat.client.message;

import io.netty.channel.ChannelHandlerContext;
import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
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
        if (ctx == null) {
            LOGGER.log(Level.SEVERE,"消息发送失败：未设置ChannelHandlerContext，请确认是否已连接到服务器？");
            return;
        }
        ctx.writeAndFlush(convertTo(message));
    }

    private RcMessage convertTo(Message message) {
        User user = message.getUser();
        RcUser rcUser = new RcUser(user.getUid(), user.getNickname(), user.getHeadImageName());
        return new RcMessage(rcUser, message.getMsg());
    }
}
