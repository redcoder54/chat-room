package redcoder.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import redcoder.chat.common.model.RcMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(ChatServerHandler.class.getName());
    private final ChannelGroup channelGroup;
    private boolean debug = true;

    public ChatServerHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.log(Level.INFO, "来自客户端的消息: {0}", msg.toString());
        handleMessage((RcMessage) msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handleMessage(RcMessage rcMessage) {
        if (debug) {
            replyClient();
        }
        // 将消息广播给所有的客户端
        channelGroup.writeAndFlush(rcMessage);
    }

    private void replyClient() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        RcMessage rcMessage = new RcMessage("chat_server", "服务端", "Male.png", "你好，我是服务端. [" + dateTime + "]");
        channelGroup.writeAndFlush(rcMessage);
    }

}
