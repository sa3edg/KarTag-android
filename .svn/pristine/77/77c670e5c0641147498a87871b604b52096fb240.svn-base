package com.kartag.gui.presentation;

import java.util.ArrayList;
import java.util.List;

import com.kartag.gui.AbstractScreen;
import com.kartag.gui.MainActivity;
import com.kartag.gui.R;
import com.kartag.gui.adapter.PoolsListAdapter;
import com.kartag.persistence.model.bo.Pool;
import com.kartag.persistence.model.bo.Trip;
import com.kartag.persistence.model.bo.User;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class PoolsListFragment extends ListFragment{
	
	private List<Pool> pools;
	private int selectedIndex = -1;
	private AbstractScreen parentfm;
	private String tripType = "";
	public PoolsListFragment(AbstractScreen main,String tripType)
	{
		this.parentfm = main;
		this.tripType = tripType;
//		OrderCoordinator.handleOrder(this,
// 				RequestFactory.createGetChildssRequest(GPSTracker.me.getId()));
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return super.onCreateView(inflater, container, savedInstanceState);
		
	}
	
	@Override
    public void onStart() {
            super.onStart();
            /** Setting the multiselect choice mode for the listview */
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            if(tripType.equals(Trip.REQUEST))
            {
              pools = new ArrayList<Pool>(User.getInstance().getRequestsPools().values());
            }
            else
            {
            	pools = new ArrayList<Pool>(User.getInstance().getOffersPools().values());
            }
            PoolsListAdapter<Pool> adapter = new PoolsListAdapter<Pool>( parentfm.getActivity(), R.layout.pools_list_row, pools);
			setListAdapter(adapter);
    }

	@Override
	public void onActivityCreated(Bundle savedState) {
	    super.onActivityCreated(savedState);

	    getListView().setOnItemClickListener(new ListView.OnItemClickListener() {
	        //@Override
	        public void onItemClick(AdapterView<?> a, View v, int i, long l) {
	        	    setSelectedIndex(i);
	                //execute click action
                    Pool pool = pools.get(i);
                    if(pool.getTrips().size() > 0)
                    {
					Intent intent = new Intent(parentfm.getActivity(), MainActivity.class);
					intent.putExtra(MainActivity.nextScreenParam, MainActivity.TRIP_LIST_ID);
					intent.putExtra("pool", pool);
					parentfm.getActivity().startActivity(intent);
                    }
                    else
                    {
    		        	Toast.makeText(parentfm.getActivity().getApplicationContext(), parentfm.getActivity().getResources().getString(R.string.noPoolTrips), Toast.LENGTH_LONG).show();
                    }
	            }
	    });
	            
	    getListView().setOnItemLongClickListener(new OnItemLongClickListener() {

	        //@Override
	        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	                int arg2, long arg3) {
	        	//edit device here
	        	
	            return true;
	        }
	    });
	}
	
	
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	
}
