package com.kartag.gui.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.kartag.gui.R;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.util.DateUtils;
import com.kartag.util.UserUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;

public class MessagesListAdapter<Message> extends ArrayAdapter<Message> {

	int resource;
	String response;
	Context context;
	List<Message> items;
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
	public MessagesListAdapter(DisplayImageOptions op, Context context, int resource, List<Message> items) {
		super(context, resource, items);
		this.resource = resource;
		this.context = context;
		this.items = items;
		this.options = op;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the current alert object
		com.kartag.persistence.model.bo.Message model = (com.kartag.persistence.model.bo.Message) getItem(position);
		final int currentPosition = position;
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resource, parent, false);

			holder = new ViewHolder();
			holder.owner = (TextView) row.findViewById(R.id.name);
			holder.message = (TextView) row.findViewById(R.id.text);
			holder.icon = (ImageView) row.findViewById(R.id.senderImage);
			holder.time = (TextView) row.findViewById(R.id.messageTime);
			holder.date = (TextView) row.findViewById(R.id.messageDate);
			holder.checked = (CheckBox) row.findViewById(R.id.messagecheck);

			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		String[] time = DateUtils.getTripTimeFromMilliseconds(model.getTime());
		holder.owner.setText(model.getFromName());
		holder.message.setText(model.getText());
		holder.time.setText(time[1]);
		holder.date.setText(time[0]);
		ImageLoader.getInstance().displayImage(UserUtils.getProfilePictureURL(model.getFromUid()), holder.icon, options,
				animateFirstListener);
		holder.checked.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		    public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		        CheckBox checkbox = (CheckBox)arg0; 
		        boolean isChecked = checkbox.isChecked();
		        // Store the boolean value somewhere durable
		        if(isChecked)
		        {
		        	getChecked().add(new Integer(currentPosition));
		        }
		    }
		      }
		);
		return row;
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
		TextView message;
		TextView time;
		TextView date;
		ImageView icon;
		ProgressBar progress;
		CheckBox checked;
		int position;
	}

}
