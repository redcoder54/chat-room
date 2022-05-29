package redcoder.chat.client.connection;

import io.netty.channel.ChannelHandlerContext;
import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
import redcoder.chat.common.model.ChatMessage;

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

    private ChatMessage convertTo(Message message) {
        User user = message.getUser();
        return new ChatMessage(user.getNickname(), user.getHeadImageName(), message.getMsg());
    }
}
