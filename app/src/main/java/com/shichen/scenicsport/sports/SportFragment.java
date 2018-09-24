package com.shichen.scenicsport.sports;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shichen.scenicsport.R;
import com.shichen.scenicsport.data.Sport;
import com.shichen.scenicsport.util.decoration.ItemDecorationForSport;

import java.util.ArrayList;
import java.util.List;

public class SportFragment extends Fragment implements SportConstract.View {
    SportConstract.Presenter mPresenter;
    SwipeRefreshLayout srlRefresh;

    public SportFragment() {
        // Requires empty public constructor
    }

    public static SportFragment newInstance() {
        return new SportFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new SportAdapter(new ArrayList<Sport>());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort, container, false);
        srlRefresh = view.findViewById(R.id.srl_refresh);
        srlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData();
            }
        });
        RecyclerView rvSports = view.findViewById(R.id.rv_sports);
        rvSports.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSports.addItemDecoration(new ItemDecorationForSport(1,0,0));
        rvSports.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void finishRefresh() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (srlRefresh.isRefreshing()) {
                    srlRefresh.setRefreshing(false);
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void finishLoadMore() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void setPresenter(SportConstract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        mPresenter.initData(mAdapter.getSports());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void toastMessage(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private SportAdapter mAdapter;

    private class SportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        final int ViewTypeLoadMore = 1;
        final int ViewTypeSport = 0;
        private List<Sport> sports;

        public List<Sport> getSports() {
            return sports;
        }

        public SportAdapter(@NonNull List<Sport> sports) {
            this.sports = sports;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            if (i == ViewTypeSport) {
                return new SportViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_sport, viewGroup, false));
            } else {
                return new LoadMoreViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_load_more, viewGroup, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
            if (getItemViewType(i) == ViewTypeSport) {
                Sport sport = sports.get(i);
                SportViewHolder sportViewHolder=(SportViewHolder)holder;
                sportViewHolder.tvValue.setText(sport.getParam_value());
                sportViewHolder.tvDes.setText(sport.getParam_desc());
            } else if (getItemViewType(i) == ViewTypeLoadMore) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.loadMoreData();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return sports.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == sports.size()) {
                return ViewTypeLoadMore;
            } else {
                return ViewTypeSport;
            }
        }

        class SportViewHolder extends RecyclerView.ViewHolder {
            TextView tvValue;
            TextView tvDes;

            public SportViewHolder(@NonNull View itemView) {
                super(itemView);
                tvValue = itemView.findViewById(R.id.tv_value);
                tvDes = itemView.findViewById(R.id.tv_des);
            }
        }

        class LoadMoreViewHolder extends RecyclerView.ViewHolder {
            public LoadMoreViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
