package kmitl.lab07.weerabhat58070128.lazyinstagram.controller;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kmitl.lab07.weerabhat58070128.lazyinstagram.R;
import kmitl.lab07.weerabhat58070128.lazyinstagram.model.UserProfile;
import kmitl.lab07.weerabhat58070128.lazyinstagram.adapter.PostAdapter;
import kmitl.lab07.weerabhat58070128.lazyinstagram.api.LazyInstagramApi;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageProfile;
    private Spinner spinnerUser;
    private TextView textPost;
    private TextView textFollowing;
    private TextView textFollower;
    private TextView textBio;
    private ImageButton imageButtonGrid;
    private ImageButton imageButtonList;
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bind();

        imageButtonGrid.setOnClickListener(this);
        imageButtonList.setOnClickListener(this);

        final String[] userArray = getResources().getStringArray(R.array.user);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, userArray);
        spinnerUser.setAdapter(arrayAdapter);
        spinnerUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getUserProfile(userArray[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void bind() {
        imageProfile = (ImageView) findViewById(R.id.imageProfile);
        spinnerUser = (Spinner) findViewById(R.id.spinnerUser);
        textPost = (TextView) findViewById(R.id.textPost);
        textFollowing = (TextView) findViewById(R.id.textFollowing);
        textFollower = (TextView) findViewById(R.id.textFollower);
        textBio = (TextView) findViewById(R.id.textBio);
        imageButtonGrid = (ImageButton) findViewById(R.id.imageButtonGrid);
        imageButtonList = (ImageButton) findViewById(R.id.imageButtonList);
        recyclerView = (RecyclerView) findViewById(R.id.list);
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
                    UserProfile userProfile = response.body();

                    showProfile(userProfile);
                    showImages(userProfile);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {

            }
        });
    }

    private void showProfile(UserProfile userProfile) {
        Glide.with(MainActivity.this)
                .load(userProfile.getUrlProfile())
                .into(imageProfile);
        textPost.setText("Post\n"+userProfile.getPost());
        textFollowing.setText("Following\n"+userProfile.getFollowing());
        textFollower.setText("Follower\n"+userProfile.getFollower());
        textBio.setText(userProfile.getBio());
    }

    private void showImages(UserProfile userProfile) {
        postAdapter = new PostAdapter(this, userProfile.getPosts());
        recyclerView.setAdapter(postAdapter);
        onBtnGrid();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonGrid:
                onBtnGrid();
                break;
            case R.id.imageButtonList:
                onBtnList();
                break;
        }
    }

    private void onBtnGrid() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        postAdapter.setItemLayout(PostAdapter.LAYOUT_TYPE_GRID);
        imageButtonGrid.setColorFilter(Color.parseColor("#3F51B5"));
        imageButtonList.setColorFilter(Color.parseColor("#808080"));
    }

    private void onBtnList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter.setItemLayout(PostAdapter.LAYOUT_TYPE_LIST);
        imageButtonList.setColorFilter(Color.parseColor("#3F51B5"));
        imageButtonGrid.setColorFilter(Color.parseColor("#808080"));
    }
}
