package manish.ecommerce.com.ecommercedemo.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.geniusforapp.fancydialog.FancyAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import manish.ecommerce.com.ecommercedemo.Activity.MyCart;
import manish.ecommerce.com.ecommercedemo.Config.Constants;
import manish.ecommerce.com.ecommercedemo.Models.CartItemGetSet;
import manish.ecommerce.com.ecommercedemo.R;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{
    Context context;
    List<CartItemGetSet> cartItemGetSets;
    public adapter_interface minterface;



    public CartAdapter(MyCart myCart, List<CartItemGetSet> cart_list, adapter_interface adapter_interface) {
        this.context = myCart;
        this.cartItemGetSets = cart_list;
        this.minterface = adapter_interface;

    }



    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder viewHolder, final int i) {
        final CartItemGetSet cartItemGetSet = cartItemGetSets.get(i);
        viewHolder.productName.setText(cartItemGetSet.getProduct_name());
        viewHolder.quantity.setText(cartItemGetSet.getQuantity());
        viewHolder.price.setText(cartItemGetSet.getPrice());

        Glide.with(context).load("http://10.0.0.80/Regular/ultime8voucher/products/originals/"+cartItemGetSet.getImage())
                //  .thumbnail(0.5f)
                .crossFade()
               // .error(R.drawable.add_plus)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.product_image);



        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FancyAlertDialog.Builder alert = new FancyAlertDialog.Builder(context);
                alert.setBackgroundColor(R.color.colorAccent);

                alert.setTextTitle("Alert !");
                alert.setBody("Are you sure to remove this item from your cart ? ");
                alert.setPositiveButtonText("Remove");
                alert.setPositiveColor(R.color.colorPrimary);
                alert.setNegativeColor(R.color.black_overlay);
                alert.setNegativeButtonText("Cancel");
                alert.setOnPositiveClicked(new FancyAlertDialog.OnPositiveClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                       String id =  cartItemGetSet.getId();
                       minterface.updateValue(id);
                    }
                });
                alert.setOnNegativeClicked(new FancyAlertDialog.OnNegativeClicked() {
                    @Override
                    public void OnClick(View view, Dialog dialog) {
                        alert.dismiss();
                    }
                });
                alert.setBodyGravity(FancyAlertDialog.TextGravity.CENTER);
                alert.setTitleGravity(FancyAlertDialog.TextGravity.CENTER);
                alert.setSubtitleGravity(FancyAlertDialog.TextGravity.CENTER);
                alert.setCancelable(false);
                alert.build();
                alert.show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return cartItemGetSets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price, productName, quantity, remove;
        ImageView product_image;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.price);
            productName = itemView.findViewById(R.id.product_name);
            product_image = itemView.findViewById(R.id.product_image);
            quantity = itemView.findViewById(R.id.quantity_);
            remove = itemView.findViewById(R.id.remove);
        }
    }

    public interface adapter_interface{
        void updateValue(String id);
    }


}
