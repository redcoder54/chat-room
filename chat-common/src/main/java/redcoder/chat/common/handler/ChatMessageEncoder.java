package redcoder.chat.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import redcoder.chat.common.model.ChatMessage;
import redcoder.chat.common.utils.MessageSerializeUtils;

public class ChatMessageEncoder extends MessageToByteEncoder<ChatMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ChatMessage chatMessage, ByteBuf out) throws Exception {
        byte[] bytes = MessageSerializeUtils.serialize(chatMessage);
        out.writeBytes(bytes);
    }
}
