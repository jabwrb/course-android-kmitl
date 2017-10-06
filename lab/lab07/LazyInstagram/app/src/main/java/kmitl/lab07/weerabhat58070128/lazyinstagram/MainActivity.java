package kmitl.lab07.weerabhat58070128.lazyinstagram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kmitl.lab07.weerabhat58070128.lazyinstagram.adapter.PostAdapter;
import kmitl.lab07.weerabhat58070128.lazyinstagram.api.LazyInstagramApi;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getUserProfile("android");
        PostAdapter postAdapter = new PostAdapter(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(postAdapter);
    }

    private void getUserProfile(String name) {
        OkHttpClient client = new OkHttpClient
                                    .Builder()
                                    .build();

        Retrofit retrofit = new Retrofit
                                .Builder()
                                .baseUrl(LazyInstagramApi.BASE_URL)
                                .client(client)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        LazyInstagramApi lazyInstagramApi = retrofit.create(LazyInstagramApi.class);

        Call<UserProfile> call = lazyInstagramApi.getProfile(name);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if(response.isSuccessful()) {
                    UserProfile result = response.body();

                    ImageView imageProfile = (ImageView) findViewById(R.id.imageProfile);
                    TextView textUser = (TextView) findViewById(R.id.textUser);
                    TextView textPost = (TextView) findViewById(R.id.textPost);
                    TextView textFollowing = (TextView) findViewById(R.id.textFollowing);
                    TextView textFollower = (TextView) findViewById(R.id.textFollower);
                    TextView textBio = (TextView) findViewById(R.id.textBio);

                    Glide.with(MainActivity.this)
                            .load(result.getUrlProfile())
                            .into(imageProfile);
                    textUser.setText("@"+result.getUser());
                    textPost.setText("Post\n"+result.getPost());
                    textFollowing.setText("Following\n"+result.getFollowing());
                    textFollower.setText("Follower\n"+result.getFollower());
                    textBio.setText(result.getBio());
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }
}
