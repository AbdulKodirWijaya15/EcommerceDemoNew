package manish.ecommerce.com.ecommercedemo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import manish.ecommerce.com.ecommercedemo.Activity.ProductListActivity;
import manish.ecommerce.com.ecommercedemo.Models.SubCategoryGetSet;
import manish.ecommerce.com.ecommercedemo.R;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    Context context;
    private List<SubCategoryGetSet> list_sub_cat;



    public SubCategoryAdapter(ProductListActivity productListActivity, List<SubCategoryGetSet> sub_cat_list) {
        this.context  = productListActivity;
        this.list_sub_cat = sub_cat_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sub_cat_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SubCategoryGetSet subCategoryGetSet = list_sub_cat.get(position);
        holder.sub_item_name.setText(subCategoryGetSet.getSub_name());

    }

    @Override
    public int getItemCount() {
        return list_sub_cat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sub_item_name;
        public ViewHolder(View itemView) {
            super(itemView);
            sub_item_name = itemView.findViewById(R.id.sub_item_name);
        }
    }
}
