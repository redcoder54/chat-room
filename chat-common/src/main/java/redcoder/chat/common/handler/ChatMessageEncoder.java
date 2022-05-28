package redcoder.chat.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.MessageToByteEncoder;
import redcoder.chat.common.model.ChatMessage;

import java.nio.charset.StandardCharsets;

public class ChatMessageEncoder extends MessageToByteEncoder<ChatMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ChatMessage chatMessage, ByteBuf out) throws Exception {
        String nickname = chatMessage.getNickname();
        String headImageName = chatMessage.getHeadImageName();
        String msg = chatMessage.getMsg();

        out.writeBytes(nickname.getBytes(StandardCharsets.UTF_8));
        out.writeByte((byte) -1);

        out.writeBytes(headImageName.getBytes(StandardCharsets.UTF_8));
        out.writeByte((byte) -1);

        out.writeBytes(msg.getBytes(StandardCharsets.UTF_8));
        out.writeByte((byte) -1);

        for (ByteBuf buf : Delimiters.nulDelimiter()) {
            out.writeBytes(buf);
        }
    }
}
