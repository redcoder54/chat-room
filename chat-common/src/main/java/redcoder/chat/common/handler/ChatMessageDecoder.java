package redcoder.chat.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import redcoder.chat.common.model.ChatMessage;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte[] buffer = new byte[in.readableBytes()];
        in.readBytes(buffer);

        String[] strs = new String[3];
        int i = 0;
        byte[] bytes = new byte[buffer.length];
        int len = 0;
        for (byte b : buffer) {
            if (b != -1) {
                bytes[len++] = b;
            } else {
                strs[i++] = new String(bytes, 0, len, StandardCharsets.UTF_8);
                len = 0;
            }
        }

        out.add(new ChatMessage(strs[0], strs[1], strs[2]));
    }
}
