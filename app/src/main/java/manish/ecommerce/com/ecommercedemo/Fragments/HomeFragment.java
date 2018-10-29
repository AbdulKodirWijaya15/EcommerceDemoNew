package manish.ecommerce.com.ecommercedemo.Fragments;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import manish.ecommerce.com.ecommercedemo.Adapters.ImageAdapter;
import manish.ecommerce.com.ecommercedemo.Adapters.ProductCategoryAdapter;
import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.Models.ProductCategoryGet;
import manish.ecommerce.com.ecommercedemo.R;
import me.angeldevil.autoscrollviewpager.AutoScrollViewPager;

import static manish.ecommerce.com.ecommercedemo.Config.Constants.BaseUrl;

public class HomeFragment extends Fragment {

    AutoScrollViewPager autoScrollViewPager;
    List<ProductCategoryGet> product_list = new ArrayList<>();
    ProductCategoryAdapter productCategoryAdapter;
    GridView expandableHeightGridView;
    android.app.AlertDialog ad;
    RequestQueue dash_requests;
    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.activity_category_data, container, false);
        autoScrollViewPager = (AutoScrollViewPager) v.findViewById(R.id.viewPager);
        expandableHeightGridView = v.findViewById(R.id.top_brand_gridview) ;
        Constants.myFlow="dash_home_fragment";
        Banner();
        getProductData();

      return v;
    }


    @SuppressLint("ClickableViewAccessibility")
    private void Banner(){

        ImageAdapter adapterView = new ImageAdapter(getActivity());
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


    }

    private void getProductData() {

        String url = BaseUrl+"getCategory";
        /*LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        final View progressView = layoutInflater.inflate(R.layout.progress_bar_layout_anim, null);
        ImageView imageView=(ImageView)progressView.findViewById(R.id.progress);
        Glide.with(getActivity()).load(R.drawable.loader_doumbell)
                // .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        ad = new android.app.AlertDialog.Builder(getActivity())
                //.setTitle("Alert?")
                //.setMessage("Do you want to Logout to Your Account?")
                .setView(progressView)
                .create();
        ad.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ad.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                //ad.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    if(status.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("category");

                        for (int i=0; i<jsonArray.length(); i++){
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            ProductCategoryGet productCategoryGet = new ProductCategoryGet();
                            productCategoryGet.setCategoryId(jsonObject1.getString("Id"));
                            productCategoryGet.setCategoryName(jsonObject1.getString("title"));
                            productCategoryGet.setImage(jsonObject1.getString("image"));
                            productCategoryGet.setSerial_no(jsonObject1.getString("serial_no"));
                            productCategoryGet.setStatus(jsonObject1.getString("status"));
                            product_list.add(productCategoryGet);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(product_list.size() > 0){
                    bindCategoryList();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
             //   ad.dismiss();
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
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        //Adding the string request to the queue
        if(dash_requests==null){
            dash_requests= Volley.newRequestQueue(getActivity());
            dash_requests.getCache().clear();
            DiskBasedCache cache = new DiskBasedCache(getActivity().getCacheDir(), 16 * 1024 * 1024);
            dash_requests = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
            dash_requests.start();
            dash_requests.add(stringRequest);

        }else{
            dash_requests.getCache().clear();
            dash_requests.add(stringRequest);

        }

    }

    private void bindCategoryList() {
        productCategoryAdapter = new ProductCategoryAdapter(getActivity(),product_list);
        expandableHeightGridView.setAdapter(productCategoryAdapter);
        productCategoryAdapter.notifyDataSetChanged();

    }



}
