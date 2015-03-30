package org.shujito.sandbox;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ActivitiesAdapter extends BaseAdapter
{
    private static class ViewHolder extends FrameLayout
    {
        public final TextView text1;
        public final TextView text2;
        
        public ViewHolder(Context context)
        {
            super(context);
            LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, this);
            this.text1 = (TextView) this.findViewById(android.R.id.text1);
            this.text2 = (TextView) this.findViewById(android.R.id.text2);
        }
    }
    
    private final List<ActivityInfo> mActivityInfos;
    
    public ActivitiesAdapter(Context context) throws NameNotFoundException
    {
        ActivityInfo[] vInfos = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES).activities;
        this.mActivityInfos = new ArrayList<>();
        for (ActivityInfo ainfo : vInfos)
        {
            if (TextUtils.equals(ainfo.name, MainActivity.class.getName()))
                continue;
            this.mActivityInfos.add(ainfo);
        }
    }
    
    @Override
    public int getCount()
    {
        return this.mActivityInfos.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        return this.mActivityInfos.get(position);
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null)
            holder = new ViewHolder(parent.getContext());
        else
            holder = ViewHolder.class.cast(convertView);
        ActivityInfo info = this.mActivityInfos.get(position);
        holder.text1.setText(info.labelRes != 0 ? info.labelRes : R.string.app_name);
        holder.text2.setText(info.name);
        return holder;
    }
}