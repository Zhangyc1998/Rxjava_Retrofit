package com.Glide;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rxjava_retrofit.R;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GlideTestActivity extends AppCompatActivity {

    private Button btShow;
    private ImageView ivImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        initView();
        initEvent();
    }

    private void initView() {
        btShow = findViewById(R.id.btShow);
        ivImage = findViewById(R.id.ivImage);

    }
    private void initEvent() {
        String url="http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
        String errorUrl="http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x108.jpg";
        String gifUrl="http://p1.pstatp.com/large/166200019850062839d3";

        btShow.setOnClickListener(v -> Glide.with(GlideTestActivity.this)
                                            .load(new MyGlideUrl(gifUrl))
                                      //    .asBitmap()//强制加载静态图片
                                            .placeholder(R.mipmap.ic_launcher)//未加载出来时的占位图
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用缓存功能
                                            .error(R.mipmap.ic_error)//异常占位图
                                      //      .override(100,100)//指定大小
                                            .into(ivImage)
        );
    }
}
