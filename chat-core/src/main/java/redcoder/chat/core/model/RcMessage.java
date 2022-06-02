package redcoder.chat.core.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RcMessage implements Serializable {

    public static final byte ONLINE_MESSAGE = (byte) 1;
    public static final byte OFFLINE_MESSAGE = (byte) 2;
    public static final byte TEXT_MESSAGE = (byte) 3;
    public static final byte ONLINE_USER_LIST_MESSAGE = (byte) 4;
    public static final byte IMAGE_MESSAGE = (byte) 5;

    /**
     * 消息类型，1-上线通知、2-下线通知、3-文本消息、4-在线用户、5-图片消息
     */
    private byte type;
    private RcUser user;
    private String msgId;
    private String msg;
    private ArrayList<RcUser> onlineUsers;
    private byte[] imageData;

    public RcMessage(byte type) {
        this.type = type;
    }

    public RcMessage(byte type, RcUser user, String msgId, String msg) {
        this.type = type;
        this.user = user;
        this.msgId = msgId;
        this.msg = msg;
    }

    public RcMessage(byte type, RcUser user, String msgId, byte[] imageData) {
        this.type = type;
        this.user = user;
        this.msgId = msgId;
        this.imageData = imageData;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public RcUser getUser() {
        return user;
    }

    public void setUser(RcUser user) {
        this.user = user;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<RcUser> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(ArrayList<RcUser> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    @Override
    public String toString() {
        return "RcMessage{" +
                "type=" + type +
                ", user=" + user +
                ", msg='" + msg + '\'' +
                '}';
    }
}
