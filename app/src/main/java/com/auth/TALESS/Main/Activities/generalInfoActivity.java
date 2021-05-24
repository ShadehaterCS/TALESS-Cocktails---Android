package com.auth.TALESS.Main.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.webkit.WebView;
import android.widget.ImageView;

import com.auth.TALESS.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.io.File;
import java.util.Objects;

public class generalInfoActivity extends AppCompatActivity {

    /**
     * All layouts are static so there is no need for anything more other than
     * settings the correct layout based on the Intent's extra
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String type = getIntent().getStringExtra("type");

        if (type.equals("FAQ"))
            setContentView(R.layout.settings_faq);
        else if (type.equals("APP"))
            setContentView(R.layout.settings_app);
        else if (type.equals("TAC")) {
            setContentView(R.layout.terms_and_conditions);
            WebView webView = findViewById(R.id.settingsWebView);
            File lFile = new File(Environment.getExternalStorageDirectory() + "/assets/terms.html");
            webView.loadUrl("file:///android_asset/terms.html");
        } else {
            setContentView(R.layout.settings_team);
            ImageView elias = findViewById(R.id.liakosImage);
            ImageView alket = findViewById(R.id.alketImage);
            ImageView antonis = findViewById(R.id.antonisImage);
            glide(elias, R.drawable.img_elias_kordoulas);
            glide(alket, R.drawable.img_alket_saliaj);
            glide(antonis, R.drawable.img_antonis_papavasiliou);
        }

        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    private void glide(ImageView view, int id) {
        Glide.with(this).load(id)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).transform(new CircleCrop()).into(view);
    }
}