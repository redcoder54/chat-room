package redcoder.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import redcoder.chat.common.model.ChatMessage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private final ChannelGroup channelGroup;

    public ChatServerHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.printf("来自客户端的消息: %s%n", msg);
        ChatMessage chatMessage = new ChatMessage("服务端", "Male.png", "你好，我是服务端. [" + getCurrentTime() + "]");
        channelGroup.writeAndFlush(chatMessage);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private synchronized String getCurrentTime() {
        return dateFormat.format(new Date());
    }
}
