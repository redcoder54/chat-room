package redcoder.chat.core.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import redcoder.chat.core.model.RcMessage;
import redcoder.chat.core.utils.MessageSerializeUtils;

public class ChatMessageEncoder extends MessageToByteEncoder<RcMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RcMessage rcMessage, ByteBuf out) throws Exception {
        byte[] bytes = MessageSerializeUtils.serialize(rcMessage);
        out.writeBytes(bytes);
    }
}
