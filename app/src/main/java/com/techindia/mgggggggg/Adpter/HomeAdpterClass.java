package com.techindia.mgggggggg.Adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techindia.mgggggggg.Model.Home.Datasss;
import com.techindia.mgggggggg.Model.JobList.Datum;
import com.techindia.mgggggggg.R;

import java.util.List;

public class HomeAdpterClass extends RecyclerView.Adapter<HomeAdpterClass.CustomViewHolder> {
    Context context;
    List<Datum> datasssesList;

    public HomeAdpterClass(Context context, List<Datum> datassses) {
        this.context = context;
        this.datasssesList = datassses;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.joblist_home, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
             Datum data = datasssesList.get(position);
           holder.textView.setText(data.job_details.name.toString());

        if(data.job_details.email!=null) {
            String[] parts = data.job_details.email.split("@");
            String maskedEmail = "XXXXX" + "@" + parts[1];
            holder.tv_email.setText("" + maskedEmail);
        }
        if(data.job_details.mobile.toString()!=null || data.job_details.mobile.toString()!="")
        {
            String maskedNumber = "XXXXXXXX" + data.job_details.mobile.toString().substring(data.job_details.mobile.length() - 2);
            holder.tv_value.setText(""+maskedNumber);
        }
        if(data.job_details.status.equals("1"))
        {
            holder.tv_status.setText("Open");
        }
        else if(data.job_details.status.equals("2"))
        {
            holder.tv_status.setText("Close");
        }
        if(data.job_details.status.equals("3"))
        {
            holder.tv_status.setText("Open");
        }
        holder.tv_add.setText(data.job_details.address.toString());
        holder.tv_time.setText(data.job_details.time.toString());
        holder.tv_remining.setText(data.job_details.remaining_time.toString());
    }

    @Override
    public int getItemCount() {
        return datasssesList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView textView,tv_value,tv_email,tv_add,tv_time,tv_remining,tv_status;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_name_job);
            tv_value = itemView.findViewById(R.id.tv_mob_job);
            tv_add = itemView.findViewById(R.id.tv_add_job);
            tv_email = itemView.findViewById(R.id.tv_email_job);
            tv_time = itemView.findViewById(R.id.tv_time_job);
            tv_remining = itemView.findViewById(R.id.tv_remingtime_job);
            tv_status = itemView.findViewById(R.id.status);

        }


    }
}
