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
import ecommerce.assesment.com.ruchita.models.Variant;

/**
 * Created by root on 30/1/18.
 */

public class VariantsAdapter extends RecyclerView.Adapter<VariantsAdapter.MyViewHolder> {

    private final List<Variant> variantList;
    private final ItemSelected catSelect;
    private int selectedPosition = 0;

    public VariantsAdapter( List<Variant> list, ItemSelected itemSelect) {
        this.variantList = list;
        this.catSelect = itemSelect;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.variant_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvColor.setText(variantList.get(position).getColor());
        
        if (selectedPosition == position)
            holder.tvColor.setBackgroundResource(R.drawable.rectangle_box_selected);
     else
            holder.tvColor.setBackgroundResource(R.drawable.rectangle_box);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                catSelect.onItemClick(variantList.get(holder.getAdapterPosition()));
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return variantList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvColor;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvColor = (TextView) itemView.findViewById(R.id.tvColorV);
        }
    }


    public interface ItemSelected {
        void onItemClick(Variant variant);
    }
}
