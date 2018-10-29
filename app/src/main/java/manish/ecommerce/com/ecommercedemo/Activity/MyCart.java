package manish.ecommerce.com.ecommercedemo.Activity;

import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import manish.ecommerce.com.ecommercedemo.Adapters.CartAdapter;
import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.Models.CartItemGetSet;
import manish.ecommerce.com.ecommercedemo.Models.WishListGetSet;
import manish.ecommerce.com.ecommercedemo.R;

public class MyCart extends AppCompatActivity implements CartAdapter.adapter_interface{
    RecyclerView  cart_recycler;
    List<CartItemGetSet> cart_list = new ArrayList<>();
    CartItemGetSet cartItemGetSet = new CartItemGetSet();
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        TextView app = findViewById(R.id.appname);
        app.setTypeface(typeface);
        cart_recycler = findViewById(R.id.cart_recycler);
        getCartItem();
    }

    private void deleteItem(final String id) {
        String url = Constants.BaseUrl+"deleteCartItem";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject js = new JSONObject(response);
                    if(js.getString("status").equals("1")){
                        bindCartList();
                        Toasty.success(MyCart.this, "Deleted successfully !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toasty.error(MyCart.this, "Your cart is empty.", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(MyCart.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pro_Id", id);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(MyCart.this);
        requestQueue.getCache().clear();
        DiskBasedCache diskBasedCache = new DiskBasedCache(MyCart.this.getCacheDir(), 16*1024*16);
        requestQueue = new RequestQueue(diskBasedCache, new BasicNetwork(new HurlStack()));
        requestQueue.start();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);

    }

    private void getCartItem() {
        String url= Constants.BaseUrl+"getCartList";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.getString("status").equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("cartItem");

                        for (int array=0; array<jsonArray.length(); array++){
                            cartItemGetSet= new CartItemGetSet();
                            cartItemGetSet.setId(jsonArray.getJSONObject(array).getString("id"));
                            cartItemGetSet.setProduct_name(jsonArray.getJSONObject(array).getString("Title"));
                            cartItemGetSet.setPrice(jsonArray.getJSONObject(array).getString("Price"));
                            cartItemGetSet.setQuantity(jsonArray.getJSONObject(array).getString("quantity"));
                            cartItemGetSet.setImage(jsonArray.getJSONObject(array).getString("Image"));
                            cartItemGetSet.setUser_id(jsonArray.getJSONObject(array).getString("user_id"));
                            cart_list.add(cartItemGetSet);
                        }
                    } else {
                        Toasty.error(MyCart.this, "Your cart is empty.", Toast.LENGTH_SHORT).show();
                    }

                    if(cart_list.size() > 0){
                        bindCartList();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toasty.error(MyCart.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId","1");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(), 16*1024*16);
        requestQueue =new RequestQueue(cache, new BasicNetwork(new HurlStack()));
        requestQueue.start();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void bindCartList() {
        cartAdapter = new CartAdapter(this, cart_list, this);
        cart_recycler.setAdapter(cartAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cart_recycler.setLayoutManager(layoutManager);
        cartAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateValue(String id) {
        deleteItem(id);
    }
}
