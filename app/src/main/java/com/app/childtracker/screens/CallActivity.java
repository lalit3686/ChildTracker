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
import com.app.childtracker.commons.ConstantCodes;
import com.app.childtracker.databases.TableIncomingCall;
import com.app.childtracker.databases.TableOutgoingCall;
import com.app.childtracker.models.IncomingCallModel;
import com.app.childtracker.models.OutgoingCallModel;

import java.util.List;

/**
 * Created by plalit on 5/31/2016.
 */
public class CallActivity extends BaseActivity {

    private ListView list_call;
    private List<?> mCallList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.call_activity);

        int callType = getIntent().getIntExtra("call", -1);
        switch (callType){
            case ConstantCodes.INCOMING:
                getIncomingCallList();
                break;
            case ConstantCodes.OUTGOING:
                getOutgoingCallList();
                break;
        }

        initComponents();
        addListeners();
    }

    @Override
    void initComponents() {
        list_call = (ListView) findViewById(R.id.list_call);
    }

    @Override
    void addListeners() {
        CallAdapter adapter = new CallAdapter(this);
        list_call.setAdapter(adapter);
    }

    private void getIncomingCallList(){
        mCallList = TableIncomingCall.getAllIncomingCalls();
    }

    private void getOutgoingCallList(){
        mCallList = TableOutgoingCall.getAllOutgoingCalls();
    }

    private class CallAdapter extends BaseAdapter{

        private Context mContext;

        public CallAdapter(Context mContext){
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mCallList.size();
        }

        @Override
        public Object getItem(int position) {
            return mCallList.get(position);
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_call, null);
                viewHolder.text_view_call = (TextView) convertView.findViewById(R.id.text_view_call);

                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Object mObject = mCallList.get(position);
            if(mObject instanceof IncomingCallModel){
                IncomingCallModel model = (IncomingCallModel) mObject;
                viewHolder.text_view_call.setText(model.getNumber() + "\n" + sdf.format(model.getMillis()));
            }
            else if(mObject instanceof OutgoingCallModel){
                OutgoingCallModel model = (OutgoingCallModel) mObject;
                viewHolder.text_view_call.setText(model.getNumber() + "\n" + sdf.format(model.getMillis()));
            }

            return convertView;
        }

        class ViewHolder{
            private TextView text_view_call;
        }
    }
}
