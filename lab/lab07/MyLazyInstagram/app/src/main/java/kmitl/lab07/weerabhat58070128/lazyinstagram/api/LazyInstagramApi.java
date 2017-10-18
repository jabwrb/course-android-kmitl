package kmitl.lab07.weerabhat58070128.lazyinstagram.api;

import kmitl.lab07.weerabhat58070128.lazyinstagram.model.FollowRequest;
import kmitl.lab07.weerabhat58070128.lazyinstagram.model.FollowResponse;
import kmitl.lab07.weerabhat58070128.lazyinstagram.model.UserProfile;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LazyInstagramApi {

    String BASE_URL = "https://us-central1-retrofit-course.cloudfunctions.net";

    @GET("/getProfile")
    Call<UserProfile> getProfile(@Query("user") String user);

    @POST("/follow")
    Call<FollowResponse> follow(@Body FollowRequest request);
}
