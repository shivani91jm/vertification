package com.techindia.mgggggggg.Model.Home;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class JobDetails implements Serializable {
    @SerializedName("Total")
    public int total;
    @SerializedName("Open")
    public int open;
    @SerializedName("Close")
    public int close;
    @SerializedName("Timeout")
    public int timeout;
    public int notAssign;
}
