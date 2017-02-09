package com.kartag.gui.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kartag.gui.R;
import com.kartag.gui.adapter.TripListImagesAdapter.ViewHolder;
import com.kartag.persistence.model.bo.Message;
import com.kartag.util.DateUtils;
import com.kartag.util.UserUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;

public class MessageDetailsAdapter extends BaseAdapter {

	private int resource;
	private Context context;
	private Message message;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	// Initialize adapter
	public MessageDetailsAdapter(DisplayImageOptions op, Context context, int resource, Message message) {
		this.resource = resource;
		this.context = context;
		this.message = message;
		this.options = op;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the current alert object
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);

			holder = new ViewHolder();
			holder.owner = (TextView) row.findViewById(R.id.senderName);
			holder.message = (TextView) row.findViewById(R.id.messageText);
			holder.icon = (ImageView) row.findViewById(R.id.userIm);
			holder.time = (TextView) row.findViewById(R.id.messageTime);
			holder.date = (TextView) row.findViewById(R.id.messageDate);

			row.setTag(holder);
			} else {
			holder = (ViewHolder) row.getTag();
		}
		if (position == 0) {

			holder.owner.setText(message.getFromName());
			holder.message.setText(message.getText());
			holder.icon.setImageResource(R.drawable.once);
			String[] time = DateUtils.getTripTimeFromMilliseconds(message.getTime());
			holder.time.setText(time[1]);
			holder.date.setText(time[0]);
			ImageLoader.getInstance().displayImage(UserUtils.getProfilePictureURL(message.getFromUid()), holder.icon, options,
					animateFirstListener);
		} else {
			com.kartag.persistence.model.bo.Reply model = (com.kartag.persistence.model.bo.Reply) message.getReplies().get(position-1);
			holder.owner.setText(model.getFromName());
			holder.message.setText(model.getText());
			String[] time = DateUtils.getTripTimeFromMilliseconds(model.getTime());
			holder.time.setText(time[1]);
			holder.date.setText(time[0]);
			ImageLoader.getInstance().displayImage(UserUtils.getProfilePictureURL(model.getFromUid()), holder.icon, options,
					animateFirstListener);
		}

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
		TextView message;
		TextView time;
		TextView date;
		ImageView icon;
		ProgressBar progress;
		int position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return message.getReplies().size() + 1;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override 
	public boolean isEnabled(int position) 
	{ 
		return false;
	}

}
