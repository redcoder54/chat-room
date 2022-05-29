package redcoder.chat.common.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import redcoder.chat.common.model.RcMessage;
import redcoder.chat.common.utils.MessageSerializeUtils;

public class ChatMessageEncoder extends MessageToByteEncoder<RcMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RcMessage rcMessage, ByteBuf out) throws Exception {
        byte[] bytes = MessageSerializeUtils.serialize(rcMessage);
        out.writeBytes(bytes);
    }
}
