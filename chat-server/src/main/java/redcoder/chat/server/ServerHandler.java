package redcoder.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import redcoder.chat.core.OnlineUserList;
import redcoder.chat.core.model.RcMessage;
import redcoder.chat.core.model.RcUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = Logger.getLogger(ServerHandler.class.getName());
    private final ChannelGroup channelGroup;
    private boolean debug = false;

    public ServerHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.add(ctx.channel());
        // 将已上线用户信息发送给客户端
        RcMessage rcMessage = new RcMessage(RcMessage.ONLINE_USER_LIST_MESSAGE);
        rcMessage.setOnlineUsers(OnlineUserList.getViews());
        ctx.writeAndFlush(rcMessage);
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

        if (rcMessage.getType() == RcMessage.ONLINE_MESSAGE) {
            OnlineUserList.add(rcMessage.getUser());
        } else if (rcMessage.getType() == RcMessage.OFFLINE_MESSAGE) {
            OnlineUserList.remove(rcMessage.getUser());
        }

        // 将消息广播给所有的客户端
        channelGroup.writeAndFlush(rcMessage);
    }

    private void replyClient() {
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        RcUser rcUser = new RcUser("chat_server", "服务端", "Male.png");
        RcMessage rcMessage = new RcMessage(rcUser, "你好，我是服务端. [" + dateTime + "]");
        channelGroup.writeAndFlush(rcMessage);
    }

}
