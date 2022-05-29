package redcoder.chat.client;

import redcoder.chat.client.ui.LoginFrame;

public class ChatClient {

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            LoginFrame frame = new LoginFrame();
            frame.createAndShowGUI();
        }
    }
}
