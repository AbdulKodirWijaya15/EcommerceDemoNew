package manish.ecommerce.com.ecommercedemo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.R;
import uk.co.senab.photoview.PhotoViewAttacher;

public class FullScreenImagePreview extends AppCompatActivity {
    TextView cross,downloadfile;
    ImageView article_image;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_preview);
        sharedPreferences = getSharedPreferences(Constants.MyPREFERENCES, MODE_PRIVATE);
        final String ImagURL = sharedPreferences.getString("imageUrl", null);

       /* Intent intent=getIntent();
        final String ImagURL=intent.getStringExtra("imageUrl");*/
        article_image=(ImageView)findViewById(R.id.article_image);
        cross=(TextView)findViewById(R.id.cross);
        downloadfile=(TextView)findViewById(R.id.download_img);
        Glide.with(FullScreenImagePreview.this).load(ImagURL)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(article_image);

        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FullScreenImagePreview.this,ProductDetailActivity.class);
                startActivity(intent);
                finish();
            }
        });

        PhotoViewAttacher photoAttacher;
        photoAttacher= new PhotoViewAttacher(article_image);
        photoAttacher.update();
    }
}
