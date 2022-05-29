package redcoder.chat.client.connection;

import io.netty.channel.ChannelHandlerContext;
import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
import redcoder.chat.common.model.RcMessage;

public class MessageSender {

    private ChannelHandlerContext ctx;

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void send(Message message) {
        ctx.writeAndFlush(convertTo(message));
    }

    private RcMessage convertTo(Message message) {
        User user = message.getUser();
        return new RcMessage(user.getUid(), user.getNickname(), user.getHeadImageName(), message.getMsg());
    }
}
