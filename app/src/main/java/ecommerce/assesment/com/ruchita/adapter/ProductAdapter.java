package ecommerce.assesment.com.ruchita.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ecommerce.assesment.com.ruchita.R;
import ecommerce.assesment.com.ruchita.models.Category;
import ecommerce.assesment.com.ruchita.models.Product;

/**
 * Created by root on 30/1/18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private final List<Product> prodList;
    private int selectedPosition;

    public ProductAdapter(Context context, List<Product> list) {
        this.prodList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.prodName.setText(prodList.get(position).getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return prodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView prodName;

        public MyViewHolder(View itemView) {
            super(itemView);
            prodName = (TextView) itemView.findViewById(R.id.prodName);
        }
    }
}
