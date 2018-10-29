package manish.ecommerce.com.ecommercedemo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.R;
import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

import static manish.ecommerce.com.ecommercedemo.Config.Constants.BaseUrl;

public class ProductDetailActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView increase, decrease;
    TextView count_item,add_cart, buy_now,product_name, productPrice;
    ImageView imageView,wishlist;
    int itemCount;
    public static int cartItemCount;
    LinearLayout add_cart_panel,simillar_layout, wishist_layout, share_layout;
    AutoScrollViewPager autoScrollViewPager;
    String productId, imageUrl, isWishlist;
    SharedPreferences sharedPreferences;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.appname);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        textView.setTypeface(typeface);
        increase = findViewById(R.id.increase);
        decrease = findViewById(R.id.decrease);
        count_item = findViewById(R.id.count_item) ;
        add_cart = findViewById(R.id.add_cart);
        add_cart_panel  = findViewById(R.id.add_cart_panel);
        simillar_layout = findViewById(R.id.simmillar_layout);
        wishist_layout = findViewById(R.id.wish_list_layout);
        share_layout = findViewById(R.id.share_layot);
        product_name = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.price);
        imageView = findViewById(R.id.action_image);
        wishlist = findViewById(R.id.wishlist);
        buy_now = findViewById(R.id.buy_now);
        sharedPreferences = getSharedPreferences(Constants.MyPREFERENCES, MODE_PRIVATE);
        Intent intent = getIntent();
        if(intent.hasExtra("what")){
            if(intent.getStringExtra("what").equalsIgnoreCase("productData")){
                productId = sharedPreferences.getString("PROID", null);
            } else if(intent.getStringExtra("what").equalsIgnoreCase("wishList")){
                productId = sharedPreferences.getString("wishId", null);
            }
        }

        /*Intent intent = getIntent();
        productId = intent.getStringExtra("PROID");*/

        // mViewPager = (AutoScrollViewPager) findViewById(R.id.viewPager);
        getProductData();
        //Banner();
        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);
        wishist_layout.setOnClickListener(this);
        simillar_layout.setOnClickListener(this);
        add_cart_panel.setOnClickListener(this);
        buy_now.setOnClickListener(this);
        imageView.setOnClickListener(this);
        share_layout.setOnClickListener(this);

    }

    private void getProductData() {
        String url = BaseUrl+"getProductDetail";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if (status.equals("1")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("productData");
                        product_name.setText(jsonObject1.getString("Title"));
                        Double dou = Double.parseDouble(jsonObject1.getString("Price"));
                        productPrice.setText(String.valueOf(String.format("%.2f",dou)));
                        imageUrl = "http://10.0.0.80/Regular/ultime8voucher/products/originals/"+jsonObject1.getString("Image");
                        Glide.with(ProductDetailActivity.this).load("http://10.0.0.80/Regular/ultime8voucher/products/originals/"+jsonObject1.getString("Image"))
                                //  .thumbnail(0.5f)
                                .crossFade()
                               // .error(R.drawable.add_plus)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imageView);
                        isWishlist = jsonObject1.getString("wish_id");
                        if(jsonObject1.getString("wish_id").equals("1")){
                            wishlist.setImageResource(R.drawable.favorite_heart_button);
                        } else if(jsonObject1.getString("wish_id").equals("0")){
                            wishlist.setImageResource(R.drawable.favorite);
                        } else {
                            wishlist.setImageResource(R.drawable.favorite);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetailActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("productId",productId);
                return params;
            }
        } ;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(),16*1024*16);
        requestQueue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
        requestQueue.start();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

   /* private void Banner() {
        ImageAdapter adapterView = new ImageAdapter(this);
        autoScrollViewPager.setAdapter(adapterView);
        // optional start auto scroll
        autoScrollViewPager.startAutoScroll();

        autoScrollViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(false);
                autoScrollViewPager.startAutoScroll();
                return false;
            }
        });

        autoScrollViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                autoScrollViewPager.getParent().requestDisallowInterceptTouchEvent(false);
                autoScrollViewPager.startAutoScroll();
            }
        });
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.increase:
                itemCount = Integer.parseInt(count_item.getText().toString());
                if(itemCount < 0){

                } else {
                    itemCount++;
                    count_item.setText(String.valueOf(itemCount));
                }
                break;
            case R.id.decrease:
                itemCount = Integer.parseInt(count_item.getText().toString());
                if(itemCount < 1){

                } else {
                    itemCount--;
                    count_item.setText(String.valueOf(itemCount));
                }
                break;
            case R.id.add_cart_panel:
                if(count_item.getText().toString().equals("0")) {
                    Toasty.error(ProductDetailActivity.this, "Invalid Quantity", Toast.LENGTH_SHORT).show();

                } else {
                    addToCart();
                }

                break;
            case R.id.simmillar_layout:
                Toast.makeText(this, "similar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.wish_list_layout:
                if(isWishlist.equals("1")){
                    Toasty.warning(ProductDetailActivity.this, "Item is already in your wishlist", Toast.LENGTH_SHORT, true).show();

                } else if(isWishlist.equals("0")){
                    addWishList();
                }

                break;
            case R.id.buy_now:
                Intent intent = new Intent(ProductDetailActivity.this, ConfirmOrder.class);
                startActivity(intent);
                break;
            case R.id.share_layot:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Found amazing " + product_name.getText().toString() + " on Ecommerce Demo App";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            break;
            case R.id.action_image:
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.MyPREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("imageUrl", imageUrl);
                editor.apply();
                Intent in = new Intent(ProductDetailActivity.this, FullScreenImagePreview.class);
               /* in.putExtra("imageUrl", imageUrl);*/
                startActivity(in);
                break;
        }
    }

    private void addToCart() {

        String url = BaseUrl+"addToCart";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    switch (jsonObject.getString("status")) {
                        case "2":
                            Toasty.success(ProductDetailActivity.this, String.valueOf(itemCount) + " item added in your cart.", Toast.LENGTH_SHORT).show();
                            cartItemCount = cartItemCount + itemCount;
                            add_cart.setText("(" + String.valueOf(cartItemCount) + ")");
                            break;
                        case "1":
                            Toasty.success(ProductDetailActivity.this, String.valueOf(itemCount) + " item added in your cart.", Toast.LENGTH_SHORT).show();
                            cartItemCount = cartItemCount + itemCount;
                            add_cart.setText("(" + String.valueOf(cartItemCount) + ")");
                            break;
                        default:
                            Toasty.error(ProductDetailActivity.this, "Somthing Went Wrong..!", Toast.LENGTH_LONG).show();
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                assert message != null;
                Toasty.error(ProductDetailActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String, String> params = new HashMap<>();
                 params.put("productId", productId);
                 params.put("quantity", String.valueOf(itemCount));
                 params.put("userId", "1");
                 return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 16*1024*16);
        requestQueue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
        requestQueue.start();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);


    }

    private void addWishList() {
        String url = BaseUrl+"addWishlist";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        Toasty.success(ProductDetailActivity.this, "Item added in your wishlist.", Toast.LENGTH_SHORT).show();
                        wishlist.setImageResource(R.drawable.favorite_heart_button);
                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductDetailActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("productId",productId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 16*1024*1024);
        requestQueue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
        requestQueue.start();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
}
