package zhiwenyan.cmccaifu.com.androidadvanced.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import zhiwenyan.cmccaifu.com.androidadvanced.R;
import zhiwenyan.cmccaifu.com.androidadvanced.recyclerview.adapter.CommonRecyclerAdapter;
import zhiwenyan.cmccaifu.com.androidadvanced.recyclerview.adapter.ViewHolder;
import zhiwenyan.cmccaifu.com.androidadvanced.selectImage.SelectImageListener;

/**
 * Description:
 * Data：12/8/2017-4:00 PM
 *
 * @author: yanzhiwen
 */
public class SelectImageListAdapter extends CommonRecyclerAdapter<String> {
    private Context mContext;
    private ArrayList<String> mResultImageList;
    private int mMaxCount;

    public SelectImageListAdapter(Context context, List<String> data, int layoutId, ArrayList<String> resultList, int maxCount) {
        super(context, data, R.layout.media_chooser_item);
        this.mContext = context;
        this.mResultImageList = resultList;
        this.mMaxCount = maxCount;
    }

    @Override
    public void convert(ViewHolder holder, final String item) {
        if (TextUtils.isEmpty(item)) {
            //显示拍照
            holder.setViewVisibility(R.id.camera_ll, View.VISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.INVISIBLE);
            holder.setViewVisibility(R.id.image, View.INVISIBLE);
        } else {
            //显示图片
            holder.setViewVisibility(R.id.camera_ll, View.INVISIBLE);
            holder.setViewVisibility(R.id.media_selected_indicator, View.VISIBLE);
            holder.setViewVisibility(R.id.image, View.VISIBLE);
            ImageView imageView = holder.getView(R.id.image);
            ImageView imageIndicator = holder.getView(R.id.media_selected_indicator);
            //显示图片利用Glide
            Glide.with(mContext).load(item).centerCrop().into(imageView);
            if (mResultImageList.contains(item)) {
                imageIndicator.setSelected(true);
            } else {
                imageIndicator.setSelected(false);

            }
            holder.setOnIntemClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mResultImageList.contains(item)) {
                        if (mResultImageList.size() >= mMaxCount) {
                            Toast.makeText(mContext, "做多选" + mMaxCount + "张", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mResultImageList.add(item);
                    } else {
                        mResultImageList.remove(item);
                    }
                    notifyDataSetChanged();
                    //通知显示布局
                    if (mSelectImageListener != null) {
                        mSelectImageListener.select();
                    }
                }
            });
        }
    }

    private SelectImageListener mSelectImageListener;

    /**
     * @param selectImageListener
     */
    public void setSelectImageListener(SelectImageListener selectImageListener) {
        mSelectImageListener = selectImageListener;
    }
}
