package redcoder.chat.client.connection;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import redcoder.chat.common.model.ChatMessage;

public class ChatHandler extends ChannelInboundHandlerAdapter {

    private ChatMessage chatMessage;
    private MessageReceiver messageReceiver;

    public ChatHandler(ChatMessage chatMessage, MessageReceiver messageReceiver) {
        this.chatMessage = chatMessage;
        this.messageReceiver = messageReceiver;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // ChatMessage message = new ChatMessage("客户端","Client","你好，我是客户端");
        ctx.writeAndFlush(chatMessage);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChatMessage message = (ChatMessage) msg;
        System.out.printf("来自服务端的消息: %s%n", message);
        messageReceiver.onReceive(message);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
