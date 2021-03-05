package com.Glide;

import java.io.File;
import java.util.concurrent.ExecutionException;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.PreloadTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.example.rxjava_retrofit.R;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

public class GlideTestActivity extends AppCompatActivity {

    private Button btShow;
    private ImageView ivImage;
    private ImageView ivTargetView;
    private TargetLayout targetLayout;
    String url = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x1080.jpg";
    String errorUrl = "http://cn.bing.com/az/hprichbg/rb/Dongdaemun_ZH-CN10736487148_1920x108.jpg";
    String gifUrl = "http://p1.pstatp.com/large/166200019850062839d3";
    String baiDuLogo = "https://www.baidu.com/img/bd_logo1.png";
    private ImageView ivBaiDu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        initView();
        initEvent();
        Glide.with(this).load(gifUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).preload();

    }

    private void initView() {
        btShow = findViewById(R.id.btShow);
        ivImage = findViewById(R.id.ivImage);
        ivTargetView = findViewById(R.id.ivTargetView);
        targetLayout = findViewById(R.id.targetLayout);
        ivBaiDu = findViewById(R.id.ivBaiDu);
    }

    private void initEvent() {

        btShow.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          //普通使用方法
//                                          UseGlide();
                                          //获得图片的drawable对象
                                          //UseSimpleTarget();
                                          //用自定义的view接收并设置背景图
//                                          UseViewTarget();
                                          //使用预加载
//                                          UsePreload();
                                          //下载图片
//                                          downLoadImage();
                                          //加载百度图标 宽高都是包裹自身 但宽充满了
//                                          loadBaiDu();
                                          //图像转换
                                          loadTransform();
                                      }
                                  }
        );
    }

    private void UseGlide() {
        //这是一个普通的Glide
        Glide.with(GlideTestActivity.this)
             .load(new MyGlideUrl(gifUrl))
             //    .asBitmap()                    //强制加载静态图片
             .placeholder(R.mipmap.ic_launcher)   //占位图
             .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用缓存功能
             .error(R.mipmap.ic_error)//异常占位图
             //      .override(100,100)//指定大小
             .into(ivImage);
    }

    private void UseSimpleTarget() {

        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                ivTargetView.setImageDrawable(resource);
            }

        };
        //into 传入target对象可以在onResponse中获得drawable对象并进行操作 如果确定返回的是图片可以用bitmap来接收

        Glide.with(GlideTestActivity.this)
             .load("http://cn.bing.com/az/hprichbg/rb/TOAD_ZH-CN7336795473_1920x1080.jpg")
             .into(simpleTarget);
    }

    private void UseViewTarget() {
        //自定义layout 返回的target是viewTarget对象
        Glide.with(GlideTestActivity.this)
             .load(url)
             .into(targetLayout.getTarget());
    }

    //预加载后 读取内存中的图片
    private void UsePreload() {
        Glide.with(GlideTestActivity.this).load(gifUrl).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivImage);
    }

    private void downLoadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FutureTarget<File> fileFutureTarget = Glide.with(GlideTestActivity.this)
                                                               .load(gifUrl)
                                                               .downloadOnly(Target.SIZE_ORIGINAL,
                                                                       Target.SIZE_ORIGINAL);
                    File file = fileFutureTarget.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("zyc", file.getPath());
                            Toast.makeText(GlideTestActivity.this, file.getPath(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
                catch (ExecutionException e) {
                    e.printStackTrace();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void loadBaiDu() {
        //ImgaeView 默认的scaleType 是 fit_center
        //dontTransForm 不对图片进行转换操作
        Log.d("zyc", "scaleType: " + ivBaiDu.getScaleType());
        Glide.with(this).load(baiDuLogo).dontTransform().into(ivBaiDu);
//        Glide.with(this).load(baiDuLogo).into(ivBaiDu);
        Glide.with(this)
             .load("http://cn.bing.com/az/hprichbg/rb/AvalancheCreek_ROW11173354624_1920x1080.jpg")
             .fitCenter()
             .into(ivBaiDu);
    }

    private void loadTransform() {
        Glide.with(this)
             .load(url)
             .bitmapTransform(new BlurTransformation(this),new GrayscaleTransformation(this))
             .crossFade()
             .into(ivImage);
    }

}
