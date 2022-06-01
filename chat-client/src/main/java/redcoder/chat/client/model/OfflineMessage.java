package redcoder.chat.client.model;

public class OfflineMessage extends Message {

    public OfflineMessage(User user, String msg) {
        super(user);
        this.msg = msg;
    }
}
