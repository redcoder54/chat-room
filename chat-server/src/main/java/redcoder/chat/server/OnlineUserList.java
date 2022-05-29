package redcoder.chat.server;

import redcoder.chat.core.model.RcUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OnlineUserList {

    private static final Set<RcUser> USERS = new HashSet<>();

    public static synchronized void add(RcUser user) {
        USERS.add(user);
    }

    public static synchronized void remove(RcUser user) {
        USERS.remove(user);
    }

    public static ArrayList<RcUser> getViews() {
        return new ArrayList<>(USERS);
    }
}
