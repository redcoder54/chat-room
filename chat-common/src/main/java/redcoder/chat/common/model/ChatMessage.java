package redcoder.chat.common.model;

public class ChatMessage {

    private String nickname;
    private String headImageName;
    private String msg;

    public ChatMessage(String nickname, String headImageName, String msg) {
        this.nickname = nickname;
        this.headImageName = headImageName;
        this.msg = msg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadImageName() {
        return headImageName;
    }

    public void setHeadImageName(String headImageName) {
        this.headImageName = headImageName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "nickname='" + nickname + '\'' +
                ", headImageName='" + headImageName + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
