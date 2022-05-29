package redcoder.chat.client.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import redcoder.chat.common.model.ChatMessage;

public class ChatHandler extends ChannelInboundHandlerAdapter {

    private final MessageSender messageSender;
    private final MessageReceiver messageReceiver;
    private ChannelHandlerContext ctx;

    public ChatHandler(MessageSender messageSender, MessageReceiver messageReceiver) {
        this.messageSender = messageSender;
        this.messageReceiver = messageReceiver;
    }

    public void closeChannel() {
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        messageSender.setCtx(ctx);
        ctx.writeAndFlush(new ChatMessage("客户端", "Client", "你好，我是客户端"));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChatMessage message = (ChatMessage) msg;
        System.out.printf("来自服务端的消息: %s%n", message);
        messageReceiver.onReceive(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
