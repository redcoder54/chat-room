package redcoder.chat.client.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import redcoder.chat.client.model.User;
import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.common.model.RcMessage;

public class ChatHandler extends ChannelInboundHandlerAdapter {

    private final MessageSender messageSender;
    private final MessageReceiver messageReceiver;
    private final ChatFrame chatFrame;
    private ChannelHandlerContext ctx;

    public ChatHandler(MessageSender messageSender, MessageReceiver messageReceiver, ChatFrame chatFrame) {
        this.messageSender = messageSender;
        this.messageReceiver = messageReceiver;
        this.chatFrame = chatFrame;
    }

    public void closeChannel() {
        // 向服务端发送下线通知消息
        User user = chatFrame.getLoggedUser();
        try {
            ctx.writeAndFlush(new RcMessage(RcMessage.OFFLINE_MESSAGE, user.getUid(), user.getNickname(), user.getHeadImageName(), "我下线了")).sync();
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
        User user = chatFrame.getLoggedUser();
        ctx.writeAndFlush(new RcMessage(RcMessage.ONLINE_MESSAGE, user.getUid(), user.getNickname(), user.getHeadImageName(), "我上线了"));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RcMessage message = (RcMessage) msg;
        System.out.printf("来自服务端的消息: %s%n", message);
        messageReceiver.onReceive(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
