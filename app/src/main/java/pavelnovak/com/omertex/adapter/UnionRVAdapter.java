package pavelnovak.com.omertex.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pavelnovak.com.omertex.R;
import pavelnovak.com.omertex.model.entity.Union;

public class UnionRVAdapter extends RecyclerView.Adapter<UnionRVAdapter.UnionViewHolder>{

    private List<Union> unionList;

    public UnionRVAdapter(List<Union> unionList) {
        this.unionList = unionList;
    }

    static class UnionViewHolder extends RecyclerView.ViewHolder{

        TextView idInCircle;
        TextView titleTextView;
        TextView linkTextView;

        UnionViewHolder(View itemView) {
            super(itemView);
            idInCircle = itemView.findViewById(R.id.id_in_circle);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            linkTextView = itemView.findViewById(R.id.link_text_view);
        }
    }

    @NonNull
    @Override
    public UnionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.union_item, parent, false);
        return new UnionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UnionViewHolder holder, int position) {
        holder.idInCircle.setText(String.valueOf(position));
        holder.titleTextView.setText(unionList.get(position).getTitle());
        holder.linkTextView.setText(unionList.get(position).getUrl());

    }

    @Override
    public int getItemCount() {
        return unionList.size();
    }

}
