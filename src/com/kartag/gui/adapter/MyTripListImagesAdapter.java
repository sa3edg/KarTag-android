package com.kartag.gui.adapter;

import java.util.ArrayList;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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

public class MyTripListImagesAdapter<Trip> extends ArrayAdapter<Trip> {

	int resource;
	String response;
	Context context;
	List<Trip> items;
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private ArrayList<Integer> checked = new ArrayList<Integer>();

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	// Initialize adapter
	public MyTripListImagesAdapter(DisplayImageOptions op, Context context, int resource, List<Trip> items) {
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

		final int currentPosition = position;
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);

			holder = new ViewHolder();
			holder.owner = (TextView) row.findViewById(R.id.name);
			holder.startPool = (TextView) row.findViewById(R.id.startPool);
			holder.time = (TextView) row.findViewById(R.id.timeText);
			holder.icon = (ImageView) row.findViewById(R.id.userImage);
			holder.status = (ImageView) row.findViewById(R.id.statusImg);
			holder.checked = (CheckBox) row.findViewById(R.id.tripcheck);

			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		String[] time = DateUtils.getTripTimeFromMilliseconds(model.getTime());
		holder.owner.setText(model.getUser().getName());
		holder.startPool.setText(model.getStartPool().getPoolName() +" To " + model.getEndPool().getPoolName());
		holder.time.setText(time[0] + " " + time[1]);
		if(com.kartag.persistence.model.bo.Trip.TRIP_OWNER.equals(model.getStatus())){
			holder.status.setBackgroundResource(R.drawable.owner);
		}else if(com.kartag.persistence.model.bo.Trip.ACCEPTED.equals(model.getStatus())){
			holder.status.setBackgroundResource(R.drawable.accepted);
		}else if(com.kartag.persistence.model.bo.Trip.REJECTED.equals(model.getStatus())){
			holder.status.setBackgroundResource(R.drawable.rejected);
		}else if(com.kartag.persistence.model.bo.Trip.REQUEST_SENT.equals(model.getStatus())){
			holder.status.setBackgroundResource(R.drawable.requested);
		}

		ImageLoader.getInstance().displayImage(UserUtils.getProfilePictureURL(model.getUser().getUid()), holder.icon, options,
				animateFirstListener);

		holder.checked.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		        CheckBox checkbox = (CheckBox)arg0; 
		        boolean isChecked = checkbox.isChecked();
		        // Store the boolean value somewhere durable
		        if(isChecked)
		        {
		        	checked.add(new Integer(currentPosition));
		        }
		    }
		      }
		);
		return row;
	}

	private String getStatus(String sattus)
	{
		if(com.kartag.persistence.model.bo.Trip.REQUEST_SENT.equals(sattus))
		{
			return "Request Sent";
		}
		else if(com.kartag.persistence.model.bo.Trip.ACCEPTED.equals(sattus))
		{
			return "Accepted";
		}
		else if(com.kartag.persistence.model.bo.Trip.REJECTED.equals(sattus))
		{
			return "Rejected";
		}
		else if(com.kartag.persistence.model.bo.Trip.TRIP_OWNER.equals(sattus))
		{
			return "Owner";
		}
		else if(com.kartag.persistence.model.bo.Trip.CANCELED.equals(sattus))
		{
			return "Canceled";
		}
		return sattus;
	}
	
	public ArrayList<Integer> getChecked() {
		return checked;
	}

	public void setChecked(ArrayList<Integer> checked) {
		this.checked = checked;
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
		ImageView status;
		CheckBox checked;
		ImageView icon;
		ProgressBar progress;
		int position;
	}

}
