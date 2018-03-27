package com.skiwithuge.envimove.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.skiwithuge.envimove.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by skiwi on 3/19/18.
 */

public class BusStopActivity extends AppCompatActivity {
    @BindView(R.id.webView) WebView webView;
    @BindView(R.id.swipeRefresh)SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_stop);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int[] envIds = intent.getIntArrayExtra(MainActivity.ENVID);
        final String s = "id_arret=" + Arrays.toString(envIds).replace(" ","")
                .replace("[","").replace("]","");
        reloadPage(s);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        reloadPage(s);
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }

    void reloadPage(String s){
        webView.postUrl("http://www.envibus.fr/flux.html?page=passages&dklik_boutique%5Baction%5D=select_arret",
                s.getBytes());
    }


}
