package com.kartag.gui.adapter;

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
import com.kartag.util.DateUtils;
import com.kartag.util.UserUtils;

import android.app.Activity;

public class TripFrequencyAdapter extends BaseAdapter {

	int resource;
	Context context;

	// Initialize adapter
	public TripFrequencyAdapter(Context context, int resource) {
		this.resource = resource;
		this.context = context;

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
			holder.title = (TextView) row.findViewById(R.id.frequencyName);
			holder.desc = (TextView) row.findViewById(R.id.frequencyDesc);
			holder.icon = (ImageView) row.findViewById(R.id.frequencyTypeImage);

			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		if (position == 0) {
			holder.title.setText(R.string.onceTitle);
			holder.desc.setText(R.string.onceDecs);
			holder.icon.setImageResource(R.drawable.once);
		} else {
			holder.title.setText(R.string.weeklyTitle);
			holder.desc.setText(R.string.weeklyDesc);
			holder.icon.setImageResource(R.drawable.weekly);
		}

		return row;
	}

	static class ViewHolder {
		TextView title;
		TextView desc;
		ImageView icon;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
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

}
