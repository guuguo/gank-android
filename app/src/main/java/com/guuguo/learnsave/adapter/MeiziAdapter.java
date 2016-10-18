package com.guuguo.learnsave.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guuguo.learnsave.R;
import com.guuguo.learnsave.bean.entity.GankBean;
import com.guuguo.learnsave.config.CustomImageSizeModelFutureStudio;

import java.text.SimpleDateFormat;

public class MeiziAdapter extends BaseQuickAdapter<GankBean> {
    public MeiziAdapter() {
        super(R.layout.item_category_image, null);
    }

    //    HashMap<GankBean, Integer> map=new HashMap<>();
//    WindowManager wm = (WindowManager) MyApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);

    @Override
    protected void convert(BaseViewHolder holder, GankBean gankBean) {
        holder.setText(R.id.who, gankBean.getWho())
                .setText(R.id.date, new SimpleDateFormat("yyyy年MM月dd日")
                        .format(gankBean.getPublishedAt()));
//        if (map.containsKey(gankBean)) {
//            holder.getView(R.id.image).getLayoutParams().height = map.get(gankBean);
//            holder.getView(R.id.image).requestLayout();
//        }
        Glide.with(mContext).load(new CustomImageSizeModelFutureStudio(gankBean.getUrl())).into((ImageView) holder.getView(R.id.image));
//                .getSize((width, height) -> {
//            if (!map.containsKey(gankBean)) {
//            int margin = ((ViewGroup.MarginLayoutParams) holder.getConvertView().getLayoutParams()).leftMargin;
//            int myHeight = (wm.getDefaultDisplay().getWidth() - margin * 4) / 2 / width * height;
//                map.put(gankBean, myHeight);
//            holder.getView(R.id.image).getLayoutParams().height = myHeight;
//            }
//                });
    }

    private static final String TAG = "MeiziAdapter";
}
