package redcoder.chat.client.message.handler;

import redcoder.chat.client.ui.ChatFrame;
import redcoder.chat.core.model.RcMessage;

public interface MessageHandler extends Ordered{

    boolean handle(ChatFrame chatFrame, RcMessage rcMessage);
}
