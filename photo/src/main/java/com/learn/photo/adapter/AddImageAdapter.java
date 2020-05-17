package com.learn.photo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.learn.photo.PhotoHelper;
import com.learn.photo.R;
import com.learn.photo.utils.ImageUtil;
import com.learn.photo.utils.UriUtils;
import com.learn.photo.utils.VersionUtils;
import java.util.ArrayList;
import java.util.List;

public class AddImageAdapter extends RecyclerView.Adapter<AddImageAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mImages;
    private LayoutInflater mInflater;
    private boolean addPhoto = false;
    private static final String ADD_PHOTO = "addPhoto";
    private boolean isAndroidQ = VersionUtils.isAndroidQ();
    private OnItemClickListener onItemClickListener;
    public AddImageAdapter(Context context) {
        this(context,false);
    }
    public AddImageAdapter(Context context, boolean addPhoto) {
        mContext = context;
        this.addPhoto = addPhoto;
        this.mInflater = LayoutInflater.from(mContext);
        mImages = new ArrayList<>();
        if (addPhoto){
            mImages.add(ADD_PHOTO);
        }
    }

    public List<String> getImages() {
        List<String> rs = new ArrayList<>();
        for (String image : mImages) {
            if (!ADD_PHOTO.endsWith(image)){
                rs.add(image);
            }
        }
        return rs;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_add_image_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final String image = mImages.get(position);
        final boolean isAddPhoto = ADD_PHOTO.equals(image);
        if (isAddPhoto){
            holder.ivImage.setVisibility(View.GONE);
            holder.addPhotoRl.setVisibility(View.VISIBLE);
            //Glide.with(mContext).load(R.mipmap.ic_add_photo).into(holder.ivImage);
        } else {
            holder.ivImage.setVisibility(View.VISIBLE);
            holder.addPhotoRl.setVisibility(View.GONE);
            // 是否是剪切返回的图片
            boolean isCutImage = ImageUtil.isCutImage(mContext, image);
            if (isAndroidQ && !isCutImage) {
                PhotoHelper.get().getImageLoader().displayImage(UriUtils.getPathForUri(mContext,UriUtils.getImageContentUri(mContext, image)),holder.ivImage);
                //Glide.with(mContext).load(UriUtils.getImageContentUri(mContext, image)).into(holder.ivImage);
            } else {
                PhotoHelper.get().getImageLoader().displayImage(image,holder.ivImage);
                //Glide.with(mContext).load(image).into(holder.ivImage);
            }
        }
        if (onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(position,isAddPhoto);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    public void refresh(List<String> images) {
        mImages.addAll(0,images);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        RelativeLayout addPhotoRl;
        public ViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            addPhotoRl = itemView.findViewById(R.id.add_photo_rl);
        }
    }

    public interface OnItemClickListener{
        void onClick(int postion ,boolean isAdd);
    }
}
