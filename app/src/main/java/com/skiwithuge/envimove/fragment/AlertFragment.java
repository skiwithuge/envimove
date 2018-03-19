package com.skiwithuge.envimove.fragment;

/**
 * Created by skiwi on 2/7/18.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


import com.skiwithuge.envimove.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertFragment extends Fragment {
    @BindView(R.id.webView) WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alert, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.envibus.fr/alertes-trafic.html");
    }
}