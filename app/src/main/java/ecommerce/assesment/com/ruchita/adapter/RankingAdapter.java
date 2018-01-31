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
import ecommerce.assesment.com.ruchita.models.Ranking;

/**
 * Created by root on 31/1/18.
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {

    private final List<Ranking> rankingList;
    private final RankingSelect catSelect;
    private int selectedPosition;

    public RankingAdapter(Context context, List<Ranking> list, RankingSelect catSelect) {
        this.rankingList = list;
        this.catSelect = catSelect;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rank_type_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.tvRank.setText(rankingList.get(position).getRanking());

//        if (selectedPosition == position)
//            holder.tvRank.setTextColor(Color.WHITE);
//        else
//            holder.tvRank.setTextColor(Color.parseColor("#BDBDBD"));

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
        return rankingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRank;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvRank = (TextView) itemView.findViewById(R.id.tvRank);
        }
    }


    public interface RankingSelect {
        void onItemClick(int selectedPosition);
    }
}
