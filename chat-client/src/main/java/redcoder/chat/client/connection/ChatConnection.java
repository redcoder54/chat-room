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
import redcoder.chat.common.handler.ChatMessageDecoder;
import redcoder.chat.common.handler.ChatMessageEncoder;

public class ChatConnection {

    private final EventLoopGroup workerGroup;
    private final ClientHandler clientHandler;

    public ChatConnection(MessageSender sender, MessageReceiver receiver, User loggedUser) {
        this.workerGroup = new NioEventLoopGroup();
        this.clientHandler = new ClientHandler(sender, receiver, loggedUser);
    }

    public void open() {
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
                    .option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.connect("localhost", 8080).sync();
        } catch (Exception e) {
            throw new RuntimeException("无法连接到服务器", e);
        }
    }

    public void close() {
        clientHandler.closeChannel();
        workerGroup.shutdownGracefully();
    }
}
