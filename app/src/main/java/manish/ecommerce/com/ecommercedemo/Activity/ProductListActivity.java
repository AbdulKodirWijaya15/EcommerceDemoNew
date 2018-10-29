package manish.ecommerce.com.ecommercedemo.Activity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import manish.ecommerce.com.ecommercedemo.Adapters.ProductDataAdapter;
import manish.ecommerce.com.ecommercedemo.Adapters.SubCategoryAdapter;
import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.Models.ProductItemGetSet;
import manish.ecommerce.com.ecommercedemo.Models.SubCategoryGetSet;
import manish.ecommerce.com.ecommercedemo.R;

import static manish.ecommerce.com.ecommercedemo.Config.Constants.BaseUrl;

public class ProductListActivity extends AppCompatActivity {
    List<ProductItemGetSet> list_product = new ArrayList<>();
    List<SubCategoryGetSet> sub_cat_list = new ArrayList<>();
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView, sub_cat_recycler;
    String categoryId;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        toolbar = findViewById(R.id.toolbar);
        TextView textView = findViewById(R.id.appname);
        Typeface typeface = ResourcesCompat.getFont(this, R.font.blacklist);
        textView.setTypeface(typeface);
        recyclerView = findViewById(R.id.product_recycler);
        sub_cat_recycler = findViewById(R.id.sub_category);
        sharedPreferences = getSharedPreferences(Constants.MyPREFERENCES, MODE_PRIVATE);
        categoryId = sharedPreferences.getString("CATID", null);
        /*Intent intent = getIntent();
        categoryId = intent.getStringExtra("CATID");*/
        getSubCategory();
        getProductDetail();
    }

    private void getSubCategory() {

        SubCategoryGetSet subCategoryGetSet = new SubCategoryGetSet("1", "All");
        sub_cat_list.add(subCategoryGetSet);

        SubCategoryGetSet subCategoryGetSet1 = new SubCategoryGetSet("2", "Men's");
        sub_cat_list.add(subCategoryGetSet1);

        SubCategoryGetSet subCategoryGetSet2 = new SubCategoryGetSet("3", "Women's");
        sub_cat_list.add(subCategoryGetSet2);
        SubCategoryGetSet subCategoryGetSet3= new SubCategoryGetSet("4", "Kid's");
        sub_cat_list.add(subCategoryGetSet3);

        SubCategoryGetSet subCategoryGetSet4 = new SubCategoryGetSet("5", "Teenager");
        sub_cat_list.add(subCategoryGetSet4);

        SubCategoryGetSet subCategoryGetSet5 = new SubCategoryGetSet("6", "Younger");
        sub_cat_list.add(subCategoryGetSet5);

        SubCategoryGetSet subCategoryGetSet6 = new SubCategoryGetSet("7", "All");
        sub_cat_list.add(subCategoryGetSet6);

        SubCategoryGetSet subCategoryGetSet7 = new SubCategoryGetSet("8", "All");
        sub_cat_list.add(subCategoryGetSet7);

        SubCategoryGetSet subCategoryGetSet8 = new SubCategoryGetSet("9", "All");
        sub_cat_list.add(subCategoryGetSet8);

        if(sub_cat_list.size() > 0){
            bindSubCatList();
        }



    }

    private void bindSubCatList() {
        SubCategoryAdapter subCategoryAdapter = new SubCategoryAdapter(this, sub_cat_list);
        sub_cat_recycler.setAdapter(subCategoryAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        sub_cat_recycler.setLayoutManager(layoutManager);
        subCategoryAdapter.notifyDataSetChanged();
    }

    private void getProductDetail() {

        String url = BaseUrl+"getProductById";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String  status = jsonObject.getString("status");
                    if(status.equals("1")){
                        JSONArray jsonArray = jsonObject.getJSONArray("productData");
                        for ( int i= 0; i < jsonArray.length(); i++){
                            ProductItemGetSet productItemGetSet = new ProductItemGetSet();
                            productItemGetSet.setId(jsonArray.getJSONObject(i).getString("Id"));
                            productItemGetSet.setProductName(jsonArray.getJSONObject(i).getString("Title"));
                            productItemGetSet.setPrice(jsonArray.getJSONObject(i).getString("Price"));
                            productItemGetSet.setImage(jsonArray.getJSONObject(i).getString("Image"));
                            productItemGetSet.setDiscription(jsonArray.getJSONObject(i).getString("Description"));
                            list_product.add(productItemGetSet);
                        }

                    }else {
                        Toast.makeText(ProductListActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }

                    if(list_product.size() > 0){
                        bindProductList();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductListActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("categoryId", categoryId);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.getCache().clear();
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(),16*1024*1024);
        requestQueue = new RequestQueue(cache, new BasicNetwork(new HurlStack()));
        requestQueue.start();
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);


        /*ProductItemGetSet productItemGetSet = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet);

        ProductItemGetSet productItemGetSet1 = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet1);

        ProductItemGetSet productItemGetSet2 = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet2);

        ProductItemGetSet productItemGetSet3 = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet3);

        ProductItemGetSet productItemGetSet4 = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet4);

        ProductItemGetSet productItemGetSet5 = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet5);

        ProductItemGetSet productItemGetSet6 = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet6);

        ProductItemGetSet productItemGetSet7 = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet7);

        ProductItemGetSet productItemGetSet8 = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet8);

        ProductItemGetSet productItemGetSet9 = new ProductItemGetSet("1","1","bottle","active"
                ,"2","Bodyfit Total Gym Kit Combo 20Kg Home Gym,Set","1000.00","45","3","gs","2","1","1","3");
        list_product.add(productItemGetSet9);

        if(list_product.size() > 0){
            bindProductList();
        }*/
    }

    private void bindProductList() {
        ProductDataAdapter productDataAdapter = new ProductDataAdapter(this, list_product);
        recyclerView.setAdapter(productDataAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        productDataAdapter.notifyDataSetChanged();
    }
}
