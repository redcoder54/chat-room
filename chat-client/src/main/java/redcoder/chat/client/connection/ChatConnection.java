package redcoder.chat.client.connection;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import redcoder.chat.client.message.MessageReceiver;
import redcoder.chat.client.message.MessageSender;
import redcoder.chat.client.model.User;
import redcoder.chat.core.handler.ChatMessageDecoder;
import redcoder.chat.core.handler.ChatMessageEncoder;

import java.util.logging.Logger;

public class ChatConnection {

    private static final Logger LOGGER = Logger.getLogger(ChatConnection.class.getName());
    private final ClientHandler clientHandler;
    private EventLoopGroup workerGroup;
    private boolean opened = false;

    public ChatConnection(MessageSender sender, MessageReceiver receiver, User loggedUser) {
        this.clientHandler = new ClientHandler(sender, receiver, loggedUser);
    }

    public void open() {
        workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
                            ch.pipeline().addLast(new LengthFieldPrepender(2));
                            ch.pipeline().addLast(new ChatMessageDecoder());
                            ch.pipeline().addLast(new ChatMessageEncoder());
                            ch.pipeline().addLast(clientHandler);
                        }
                    })
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.connect("localhost", 8080).sync();
            opened = true;
        } catch (Exception e) {
            throw new RuntimeException("无法连接到服务器", e);
        } finally {
            if (!opened) {
                workerGroup.shutdownGracefully();
            }
        }
    }

    public boolean isOpen() {
        return opened;
    }

    public void close() {
        if (opened) {
            clientHandler.closeChannel();
            workerGroup.shutdownGracefully();
        }
    }
}
