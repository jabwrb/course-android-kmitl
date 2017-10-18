package kmitl.lab07.weerabhat58070128.lazyinstagram.model;

public class FollowRequest {
    private String user;
    private boolean isFollow;

    public FollowRequest(String user, boolean isFollow) {
        this.user = user;
        this.isFollow = isFollow;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }
}
