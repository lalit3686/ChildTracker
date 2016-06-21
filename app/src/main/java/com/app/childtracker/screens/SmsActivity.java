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
import com.app.childtracker.databases.TableIncomingSms;
import com.app.childtracker.models.IncomingSmsModel;
import com.app.childtracker.models.OutgoingSmsModel;

import java.util.List;

/**
 * Created by plalit on 5/31/2016.
 */
public class SmsActivity extends BaseActivity {

    private ListView list_sms;
    private List<?> mSmsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_activity);

        int smsType = getIntent().getIntExtra("sms", -1);
        switch (smsType){
            case ConstantCodes.INCOMING:
                getIncomingSmsList();
                break;
            case ConstantCodes.OUTGOING:
                getOutgoingSmsList();
                break;
        }

        initComponents();
        addListeners();
    }

    @Override
    void initComponents() {
        list_sms = (ListView) findViewById(R.id.list_sms);
    }

    @Override
    void addListeners() {
        SmsAdapter adapter = new SmsAdapter(this);
        list_sms.setAdapter(adapter);
    }

    private void getIncomingSmsList(){
        mSmsList = TableIncomingSms.getAllIncomingSms();
    }

    private void getOutgoingSmsList(){
        mSmsList = TableIncomingSms.getAllIncomingSms();
    }

    private class SmsAdapter extends BaseAdapter{

        private Context mContext;

        public SmsAdapter(Context mContext){
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mSmsList.size();
        }

        @Override
        public Object getItem(int position) {
            return mSmsList.get(position);
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.row_sms, null);
                viewHolder.text_view_sms = (TextView) convertView.findViewById(R.id.text_view_sms);

                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Object mObject = mSmsList.get(position);
            if(mObject instanceof IncomingSmsModel){
                IncomingSmsModel model = (IncomingSmsModel) mObject;
                viewHolder.text_view_sms.setText(model.getNumber() + " \n " + model.getMessage() + "\n" + sdf.format(model.getMillis()));
            }
            else if(mObject instanceof OutgoingSmsModel){
                OutgoingSmsModel model = (OutgoingSmsModel) mObject;
                viewHolder.text_view_sms.setText(model.getNumber() + " \n " + model.getMessage() + "\n" + sdf.format(model.getMillis()));
            }

            return convertView;
        }

        class ViewHolder{
            private TextView text_view_sms;
        }
    }
}
