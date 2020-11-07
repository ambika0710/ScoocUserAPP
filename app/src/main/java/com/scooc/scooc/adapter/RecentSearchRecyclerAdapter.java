package com.scooc.scooc.adapter;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.scooc.scooc.R;
import com.scooc.scooc.model.RecentSearchBean;

public class RecentSearchRecyclerAdapter extends RecyclerView.Adapter<RecentSearchRecyclerAdapter.ViewHolder> {

    private final Activity mContext;
    private RecentSearchBean recentSearchBean;
    private boolean isLoadMore;
    private RecentSearchRecyclerAdapterListener recentSearchRecyclerAdapterListener;

    public RecentSearchRecyclerAdapter(Activity mContext, RecentSearchBean recentSearchBean) {

        this.mContext = mContext;
        this.recentSearchBean = recentSearchBean;

    }

    public RecentSearchBean getRecentSearchBean() {
        return recentSearchBean;
    }

    public void setRecentSearchBean(RecentSearchBean recentSearchBean) {
        this.recentSearchBean = recentSearchBean;
    }

    public boolean isLoadMore() {

        return isLoadMore;
    }

    public void setLoadMore(boolean loadMore) {

        isLoadMore = loadMore;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemLayout = inflater.inflate(R.layout.item_search_results, parent, false);
        return new ViewHolder(itemLayout);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        setLayoutRecentSearch(holder, position);
    }

    private void setLayoutRecentSearch(ViewHolder holder, int position) {

        holder.txtPlace.setText(recentSearchBean.getPlace());
        holder.txtAddress.setText(recentSearchBean.getAddress());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPlace;
        private TextView txtAddress;

        ViewHolder(View itemView) {
            super(itemView);

            txtPlace = (TextView) itemView.findViewById(R.id.txt_place);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address);
        }
    }

    public interface RecentSearchRecyclerAdapterListener {

        void onRequestNextPage(boolean isLoadMore, int currentPageNumber);

        void onRefresh();

        void onSwipeRefreshingChange(boolean isSwipeResfreshing);

        void onSnackBarShow(String message);
    }

    public RecentSearchRecyclerAdapterListener getRecentSearchRecyclerAdapterListener() {
        return recentSearchRecyclerAdapterListener;
    }

    public void setRecentSearchRecyclerAdapterListener(RecentSearchRecyclerAdapterListener recentSearchRecyclerAdapterListener) {
        this.recentSearchRecyclerAdapterListener = (RecentSearchRecyclerAdapterListener) recentSearchRecyclerAdapterListener;
    }
}
