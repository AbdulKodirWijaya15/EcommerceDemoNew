package manish.ecommerce.com.ecommercedemo.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import manish.ecommerce.com.ecommercedemo.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView appName = findViewById(R.id.appname);
        Typeface typeface = ResourcesCompat.getFont(this,R.font.blacklist);
        appName.setTypeface(typeface);


        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });
    }
}
