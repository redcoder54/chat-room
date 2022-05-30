package redcoder.chat.client.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import redcoder.chat.client.message.MessageReceiver;
import redcoder.chat.client.message.MessageSender;
import redcoder.chat.client.model.User;
import redcoder.chat.core.model.RcMessage;
import redcoder.chat.core.model.RcUser;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(ClientHandler.class.getName());
    private final MessageSender messageSender;
    private final MessageReceiver messageReceiver;
    private final User loggedUser;
    private ChannelHandlerContext ctx;

    public ClientHandler(MessageSender messageSender, MessageReceiver messageReceiver, User loggedUser) {
        this.messageSender = messageSender;
        this.messageReceiver = messageReceiver;
        this.loggedUser = loggedUser;
    }

    public void closeChannel() {
        try {
            // 向服务端发送下线通知消息
            RcUser rcUser = new RcUser(loggedUser.getUid(), loggedUser.getNickname(), loggedUser.getHeadImageName());
            ctx.writeAndFlush(new RcMessage(RcMessage.OFFLINE_MESSAGE, rcUser, "我下线了")).sync();
        } catch (InterruptedException e) {
            // ignore
        }
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        messageSender.setCtx(ctx);

        // 向服务端发送上线通知消息
        RcUser rcUser = new RcUser(loggedUser.getUid(), loggedUser.getNickname(), loggedUser.getHeadImageName());
        ctx.writeAndFlush(new RcMessage(RcMessage.ONLINE_MESSAGE, rcUser, "我上线了"));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RcMessage message = (RcMessage) msg;
        LOGGER.log(Level.INFO, "来自服务端的消息: {0}", msg.toString());
        messageReceiver.onReceive(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.log(Level.SEVERE, "", cause);
        ctx.close();
    }
}
