package manish.ecommerce.com.ecommercedemo.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import manish.ecommerce.com.ecommercedemo.R;

public class SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        TextView app = findViewById(R.id.appname);
        app.setTypeface(typeface);


        YoYo.with(Techniques.Bounce)
                .duration(7000)
                .playOn(findViewById(R.id.logo));

        YoYo.with(Techniques.FadeInUp)
                .duration(5000)
                .playOn(findViewById(R.id.appname));


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                startActivity(new Intent(SplashScreen.this,WelcomeActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
