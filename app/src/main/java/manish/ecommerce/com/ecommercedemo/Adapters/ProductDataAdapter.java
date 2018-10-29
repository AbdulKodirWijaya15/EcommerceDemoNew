package manish.ecommerce.com.ecommercedemo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import manish.ecommerce.com.ecommercedemo.Activity.ProductDetailActivity;
import manish.ecommerce.com.ecommercedemo.Activity.ProductListActivity;
import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.Models.ProductItemGetSet;
import manish.ecommerce.com.ecommercedemo.R;

public class ProductDataAdapter extends RecyclerView.Adapter<ProductDataAdapter.ViewHolder> {
    Context context;
    List<ProductItemGetSet> list;

    public ProductDataAdapter(ProductListActivity productListActivity, List<ProductItemGetSet> list_product) {
        this.context = productListActivity;
        this.list = list_product;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_data_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ProductItemGetSet productItemGetSet = list.get(position);
        holder.productName.setText(productItemGetSet.getProductName());
        Double price = Double.parseDouble(productItemGetSet.getPrice());
        holder.price.setText(String.valueOf(String.format("%.2f",price)));
        Glide.with(context).load("http://10.0.0.80/Regular/ultime8voucher/products/originals/"+productItemGetSet.getImage())
                //  .thumbnail(0.5f)
                .crossFade()
              //  .error(R.drawable.add_plus)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.product_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("PROID",productItemGetSet.getId());
                editor.apply();
                intent.putExtra("what", "productData");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price, productName;
        ImageView product_image;
        public ViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            productName = itemView.findViewById(R.id.product_name);
            product_image = itemView.findViewById(R.id.product_image);
        }
    }
}

