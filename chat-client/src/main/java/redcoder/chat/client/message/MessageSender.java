package redcoder.chat.client.message;

import io.netty.channel.ChannelHandlerContext;
import redcoder.chat.client.model.Message;
import redcoder.chat.client.model.User;
import redcoder.chat.core.model.RcMessage;
import redcoder.chat.core.model.RcUser;

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
        RcUser rcUser = new RcUser(user.getUid(), user.getNickname(), user.getHeadImageName());
        return new RcMessage(rcUser, message.getMsg());
    }
}
