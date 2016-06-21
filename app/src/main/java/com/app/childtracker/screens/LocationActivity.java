package com.app.childtracker.screens;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.childtracker.R;
import com.app.childtracker.databases.TableLocation;
import com.app.childtracker.models.LocationModel;

import java.util.List;

/**
 * Created by plalit on 6/1/2016.
 */
public class LocationActivity extends BaseActivity{

    private ListView list_location;
    private List<?> mLocationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_activity);

        getIncomingCallList();

        initComponents();
        addListeners();
    }

    @Override
    void initComponents() {
        list_location = (ListView) findViewById(R.id.list_location);
    }

    @Override
    void addListeners() {
        LocationAdapter adapter = new LocationAdapter(this);
        list_location.setAdapter(adapter);
    }

    private void getIncomingCallList(){
        mLocationList = TableLocation.getAllLocations();
    }

    private class LocationAdapter extends BaseAdapter {

        private Context mContext;

        public LocationAdapter(Context mContext){
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mLocationList.size();
        }

        @Override
        public Object getItem(int position) {
            return mLocationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if(convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_location, null);

                viewHolder.text_view_location = (TextView) convertView.findViewById(R.id.text_view_location);

                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Object mObject = mLocationList.get(position);

            if(mObject instanceof LocationModel){
                LocationModel model = (LocationModel) mObject;
                viewHolder.text_view_location.setText(sdf.format(model.getMillis()));
            }

            return convertView;
        }

        class ViewHolder{
            private TextView text_view_location;
        }
    }
}
