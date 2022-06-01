package redcoder.chat.client.model;

public class ImageMessage extends Message {

    private byte[] imageData;

    public ImageMessage(User user, byte[] imageData) {
        super(user);
        this.imageData = imageData;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
