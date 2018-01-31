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

/**
 * Created by root on 30/1/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private final List<Category> catList;
    private final CategorySelect catSelect;
    private int selectedPosition;

    public CategoryAdapter(Context context, List<Category> list, CategorySelect catSelect) {
        this.catList = list;
        this.catSelect = catSelect;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cate_type_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.catName.setText(catList.get(position).getName());

        if (selectedPosition == position)
            holder.catName.setTextColor(Color.WHITE);
        else
            holder.catName.setTextColor(Color.parseColor("#BDBDBD"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition = holder.getAdapterPosition();
                catSelect.onItemClick(selectedPosition);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView catName;

        public MyViewHolder(View itemView) {
            super(itemView);
            catName = (TextView) itemView.findViewById(R.id.catName);
        }
    }


    public interface CategorySelect {
        void onItemClick(int selectedPosition);
    }
}
