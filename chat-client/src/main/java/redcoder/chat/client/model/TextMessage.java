package redcoder.chat.client.model;

public class TextMessage extends Message {

    public TextMessage(User user, String msg) {
        super(user);
        this.msg = msg;
    }
}
