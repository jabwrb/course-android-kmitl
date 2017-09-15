package com.jabwrb.day05.lab05_workshop;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jabwrb.day05.lab05_workshop.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,
                        new MainFragment().newInstance(""))
                .commit();

        Button accessFragment = (Button) findViewById(R.id.accessFragment);
        accessFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .add(R.id.fragmentContainer,
                                new MainFragment().newInstance("Access from MainActivity"))
                        .commit();
            }
        });
    }
}
