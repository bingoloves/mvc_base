package com.learn.gallery;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * ViewPager适配器
 */
public class GalleryPagerAdapter extends PagerAdapter {
    private Activity aty;
    private String[] imageUrls;
    private PhotoViewAttacher mAttacher;
    private ImageLoader imageLoader;
    public static int TYPE_GIF = 0;
    public static int TYPE_JPG = 1;
    public static int TYPE_PNG = 2;

    public GalleryPagerAdapter(Activity aty, String[] imageUrls) {
        this.aty = aty;
        this.imageUrls = imageUrls;
        this.imageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View root = View.inflate(aty, R.layout.layout_gallery_item, null);
        final PhotoView photoView = (PhotoView) root.findViewById(R.id.photoView);
        String imageUrl = imageUrls[position];
        if (imageUrl.endsWith(".gif")){
            GalleryHelper.get().getImageLoader().displayGif(imageUrl,photoView);
            //Glide.with(aty).load(imageUrl).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(photoView);
        } else {
            GalleryHelper.get().getImageLoader().displayImage(imageUrl,photoView);
            //Glide.with(aty).load(imageUrl).into(photoView);
        }
        container.addView(root, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        return root;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
