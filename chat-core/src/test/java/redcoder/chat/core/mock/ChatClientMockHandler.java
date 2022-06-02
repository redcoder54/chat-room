package redcoder.chat.core.mock;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import redcoder.chat.core.model.RcMessage;
import redcoder.chat.core.model.RcUser;

import java.util.UUID;

public class ChatClientMockHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RcMessage message = new RcMessage(RcMessage.TEXT_MESSAGE,
                new RcUser("client_mock", "客户端", "Client"),
                UUID.randomUUID().toString(), "你好，我是客户端");
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RcMessage message = (RcMessage) msg;
        System.out.printf("来自服务端的消息: %s%n", message);

        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
