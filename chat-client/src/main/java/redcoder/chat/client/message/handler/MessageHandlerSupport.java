package redcoder.chat.client.message.handler;

import redcoder.chat.client.model.User;
import redcoder.chat.common.model.RcUser;

public class MessageHandlerSupport {

    protected User convertTo(RcUser rcUser) {
        return new User(rcUser.getUid(), rcUser.getNickname(), rcUser.getHeadImageName());
    }
}
