package com.skiwithuge.envimove.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.skiwithuge.envimove.R;

/**
 * Created by skiwi on 2/7/18.
 */

public class LineFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_line, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        WebView webView = (WebView) getView().findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.envibus.fr/flux.html?page=passages");
    }
}
