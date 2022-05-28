package redcoder.chat.common.mock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import redcoder.chat.common.model.ChatMessage;

public class ChatServerMockHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ChatMessage message = (ChatMessage) msg;
        System.out.printf("来自客户端的消息: %s%n", message);
        ctx.writeAndFlush(new ChatMessage("服务端","Server","你好，我是服务端"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
