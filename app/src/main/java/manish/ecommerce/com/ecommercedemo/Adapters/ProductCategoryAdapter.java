package manish.ecommerce.com.ecommercedemo.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import manish.ecommerce.com.ecommercedemo.Activity.ProductListActivity;
import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.Models.ProductCategoryGet;
import manish.ecommerce.com.ecommercedemo.R;

public class ProductCategoryAdapter extends BaseAdapter {
    Context context;
    private List<ProductCategoryGet> productCategoryGets;

    public ProductCategoryAdapter(Activity activity, List<ProductCategoryGet> product_list) {
        this.context =activity;
        this.productCategoryGets = product_list;
    }


    @Override
    public int getCount() {
        return productCategoryGets.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ProductCategoryGet productCategoryGet = productCategoryGets.get(i);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = mInflater.inflate(R.layout.category_item, null);
        TextView productNAme = view1.findViewById(R.id.product_name);
        CircleImageView catimage = view1.findViewById(R.id.catimage);
        Double price = Double.parseDouble(productCategoryGets.get(i).getCategoryName());
        productNAme.setText("Rs. "+String.valueOf(String.format("%.2f",price)));
        Glide.with(context).load("http://10.0.0.80/Regular/ultime8voucher/images/"+productCategoryGet.getImage())
                //  .thumbnail(0.5f)
                .crossFade()
              //  .error(R.drawable.add_plus)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(catimage);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductListActivity.class);
                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor  = sharedPreferences.edit();
                editor.putString("CATID", productCategoryGets.get(i).getCategoryId());
                editor.apply();
                context.startActivity(intent);
            }
        });


        return view1;
    }
}
