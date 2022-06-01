package redcoder.chat.client.model;

public class OnlineMessage extends Message {

    public OnlineMessage(User user, String msg) {
        super(user);
        this.msg = msg;
    }
}
