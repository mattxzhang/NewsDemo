package net.arvin.imagescan.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import net.arvin.imagescan.R;
import net.arvin.imagescan.entitys.ImageBean;

import java.util.ArrayList;

public class ReviewPagerAdapter extends PagerAdapter {
	private Context context;
	private ArrayList<ImageBean> currentImages;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public ReviewPagerAdapter(Context context,
			ArrayList<ImageBean> currentImages) {
		this.context = context;
		this.currentImages = currentImages;

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.is_loading)
				.showImageForEmptyUri(R.drawable.is_loading)
				.showImageOnFail(R.drawable.is_loading).cacheInMemory(true)
				.cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	@Override
	public int getCount() {
		return currentImages.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView img = new ImageView(context);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		img.setScaleType(ScaleType.CENTER_CROP);
		container.addView(img, params);
		imageLoader.displayImage("file://"
				+ currentImages.get(position).getImagePath(), img, options);
		return img;
	}

}
