package com.kartag.gui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kartag.gui.R;

import android.app.Activity;

public class PoolsListAdapter<Pool> extends ArrayAdapter<Pool> {

	int resource;
	String response;
	Context context;

	// Initialize adapter
	public PoolsListAdapter(Context context, int resource, List<Pool> items) {
		super(context, resource, items);
		this.resource = resource;
		this.context = context;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the current alert object
		com.kartag.persistence.model.bo.Pool model = (com.kartag.persistence.model.bo.Pool)getItem(position);

		View row = convertView;
		ViewHolder holder = null;
       
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
           
            holder = new ViewHolder();
            holder.name = (TextView) row.findViewById(R.id.poolsName);
			
            row.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)row.getTag();
        }
        holder.name.setText(model.getPoolName().trim());
   
        
        return row;
	}
	
	
	
	static class ViewHolder {
		  TextView name;
		}

}
