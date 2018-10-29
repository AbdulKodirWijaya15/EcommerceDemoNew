package manish.ecommerce.com.ecommercedemo.Adapters;

import android.app.Activity;
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
import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.Models.WishListGetSet;
import manish.ecommerce.com.ecommercedemo.R;


public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
    Context context;
    List<WishListGetSet> wishListGetSetList;

    public WishListAdapter(Activity activity, List<WishListGetSet> wishListGetSetList) {
        this.context = activity;
        this.wishListGetSetList = wishListGetSetList;
    }

    @NonNull
    @Override
    public WishListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wishlist_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.ViewHolder holder, int position) {
        final WishListGetSet wishListGetSet = wishListGetSetList.get(position);
        holder.productName.setText(wishListGetSet.getName());
        Double price = Double.parseDouble(wishListGetSet.getPrice());
        holder.price.setText(String.valueOf(String.format("%.2f",price)));
        Glide.with(context).load("http://10.0.0.80/Regular/ultime8voucher/products/originals/"+wishListGetSet.getImage())
                //  .thumbnail(0.5f)
                .crossFade()
                .error(R.drawable.add_plus)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.product_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("wishId",wishListGetSet.getId());
                editor.apply();
                intent.putExtra("what", "wishList");
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return wishListGetSetList.size();
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
