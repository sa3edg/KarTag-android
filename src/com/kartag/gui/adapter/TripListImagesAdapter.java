package com.kartag.gui.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kartag.gui.R;
import com.kartag.util.DateUtils;
import com.kartag.util.UserUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;

public class TripListImagesAdapter<Trip> extends ArrayAdapter<Trip> {

	int resource;
	String response;
	Context context;
	List<Trip> items;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	// Initialize adapter
	public TripListImagesAdapter(DisplayImageOptions op, Context context, int resource, List<Trip> items) {
		super(context, resource, items);
		this.resource = resource;
		this.context = context;
		this.items = items;
		this.options = op;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the current alert object
		com.kartag.persistence.model.bo.Trip model = (com.kartag.persistence.model.bo.Trip) getItem(position);

		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);

			holder = new ViewHolder();
			holder.owner = (TextView) row.findViewById(R.id.name);
			holder.startPool = (TextView) row.findViewById(R.id.startPool);
			holder.icon = (ImageView) row.findViewById(R.id.userImage);
			holder.time = (TextView) row.findViewById(R.id.timeText);
			//holder.icon.set

			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		String[] time = DateUtils.getTripTimeFromMilliseconds(model.getTime());
		holder.owner.setText(model.getUser().getName());
		holder.startPool.setText(model.getStartPool().getPoolName() + " To " + model.getEndPool().getPoolName());
		holder.time.setText(time[0] + " " + time[1]);
		ImageLoader.getInstance().displayImage(UserUtils.getProfilePictureURL(model.getUser().getUid()), holder.icon, options,
				animateFirstListener);

		return row;
	}

	
	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				} else {
					imageView.setImageBitmap(loadedImage);
				}
			}
		}
	}

	static class ViewHolder {
		TextView owner;
		TextView startPool;
		TextView time;
		ImageView icon;
		ProgressBar progress;
		int position;
	}

}
