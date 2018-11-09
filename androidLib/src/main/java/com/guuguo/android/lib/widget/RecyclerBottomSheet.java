package com.guuguo.android.lib.widget;

import android.content.Context;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.guuguo.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guodeqing on 6/23/16.
 */
public class RecyclerBottomSheet<T> extends BottomSheetDialog {
    @IntRange(from = 1)
    int colume = 1;
    @LayoutRes
    int itemLayoutRes = R.layout.dialog_recycle_item_bottom_sheet;

    private List<T> items = new ArrayList();
    private BindItemViewListener bindItemViewListener = new BindItemViewListener<T>() {
        @Override
        public void bindViewData(View itemView, T data) {
            TextView view = itemView.findViewById(R.id.tv_content);
            view.setText(data.toString());
        }
    };
    private OnItemClickListener onItemClickListener;

    public T getItem(int i) {
        return items.get(i);
    }

    public void setItems(List<T> items) {
        this.items = items;
        adapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setBindItemViewListener(BindItemViewListener bindItemViewListener) {
        this.bindItemViewListener = bindItemViewListener;
    }

    public RecyclerBottomSheet(@NonNull Context context) {
        super(context);
        init();
    }

    public RecyclerBottomSheet(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
        init();
    }

    public void setPeekHeight(int height) {
        BottomSheetBehavior.from(getWindow().getDecorView().findViewById(R.id.design_bottom_sheet))
                .setPeekHeight(height);
    }

    protected RecyclerBottomSheet(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }


    public interface BindItemViewListener<T> {
        void bindViewData(View itemView, T data);
    }

    public interface OnItemClickListener {
        void itemClick(int point);
    }

    DefaultAdapter adapter = new DefaultAdapter();

    private void init() {
        View bottomView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_recycler_bottom_sheet, null);
        RecyclerView recyclerView = bottomView.findViewById(R.id.recycler);
        if (colume == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), colume));
        }
        recyclerView.setAdapter(adapter);
        setContentView(bottomView);
    }

    class DefaultAdapter extends RecyclerView.Adapter<DefaultAdapter.MyViewHolder> {
        class MyViewHolder extends RecyclerView.ViewHolder {
            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getContext()).inflate(itemLayoutRes, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, final int i) {
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.itemClick(i);
                    }
                }
            });
            bindItemViewListener.bindViewData(myViewHolder.itemView, items.get(i));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}


