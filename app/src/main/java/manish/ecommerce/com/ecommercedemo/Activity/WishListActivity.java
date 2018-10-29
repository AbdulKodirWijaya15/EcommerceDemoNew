package manish.ecommerce.com.ecommercedemo.Activity;

import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import manish.ecommerce.com.ecommercedemo.Adapters.WishListAdapter;
import manish.ecommerce.com.ecommercedemo.Models.WishListGetSet;
import manish.ecommerce.com.ecommercedemo.R;

import static manish.ecommerce.com.ecommercedemo.Config.Constants.BaseUrl;

public class WishListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<WishListGetSet> wishListGetSetList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        TextView app = findViewById(R.id.appname);
        app.setTypeface(typeface);
        recyclerView = findViewById(R.id.product_recycler);
        getWishListData();
    }

    private void getWishListData() {
        String url = BaseUrl+"getWishList";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("wishListData");
                        for (int array=0; array<jsonArray.length(); array++){
                            WishListGetSet wishListGetSet = new WishListGetSet();
                            wishListGetSet.setId(jsonArray.getJSONObject(array).getString("Id"));
                            wishListGetSet.setName(jsonArray.getJSONObject(array).getString("Title"));
                            wishListGetSet.setPrice(jsonArray.getJSONObject(array).getString("Price"));
                            wishListGetSet.setImage(jsonArray.getJSONObject(array).getString("Image"));
                            wishListGetSetList.add(wishListGetSet);
                        }


                    } else {
                        Toasty.error(WishListActivity.this, "Empty !", Toast.LENGTH_SHORT).show();
                    }

                    if(wishListGetSetList.size() > 0){
                        bindWishList();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WishListActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        DiskBasedCache cache = new DiskBasedCache(this.getCacheDir(),16*1024*16);
        requestQueue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
        requestQueue.start();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void bindWishList() {
        WishListAdapter wishListAdapter = new WishListAdapter(this,wishListGetSetList);
        recyclerView.setAdapter(wishListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        wishListAdapter.notifyDataSetChanged();
    }

}
