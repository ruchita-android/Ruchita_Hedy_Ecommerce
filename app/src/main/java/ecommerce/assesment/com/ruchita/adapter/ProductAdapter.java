package ecommerce.assesment.com.ruchita.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import ecommerce.assesment.com.ruchita.R;
import ecommerce.assesment.com.ruchita.activity.ProductDescription;
import ecommerce.assesment.com.ruchita.models.Category;
import ecommerce.assesment.com.ruchita.models.Product;
import ecommerce.assesment.com.ruchita.models.Product_;

/**
 * Created by root on 30/1/18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private final List<Product> prodList;
    private final Context mContext;
    private final boolean isFilter;

    public ProductAdapter(Context context, List<Product> list, boolean isFilter) {
        this.prodList = list;
        this.mContext = context;
        this.isFilter = isFilter;
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
        holder.tvprice.setText("Price : Rs. " + prodList.get(position).getVariants().get(0).getPrice());

        if (isFilter) {
            holder.tvRank.setVisibility(View.VISIBLE);
            Product_ rankProd = prodList.get(position).getRankProduct();
            if (rankProd.getViewCount() != null)
                holder.tvRank.setText("View Count : " + rankProd.getViewCount());
            if (rankProd.getOrderCount() != null)
                holder.tvRank.setText("Order Count : " + rankProd.getOrderCount());
            if (rankProd.getShares() != null)
                holder.tvRank.setText("Shares : " + rankProd.getShares());
        } else {
            holder.tvRank.setVisibility(View.GONE);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, ProductDescription.class);
                i.putExtra("product", (Serializable) prodList.get(holder.getAdapterPosition()));
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return prodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView prodName;
        private final TextView tvRank;
        private final TextView tvprice;

        public MyViewHolder(View itemView) {
            super(itemView);
            prodName = (TextView) itemView.findViewById(R.id.prodName);
            tvRank = (TextView) itemView.findViewById(R.id.tvRank);
            tvprice = (TextView) itemView.findViewById(R.id.tvprice);
        }
    }

}
