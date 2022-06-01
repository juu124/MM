package com.example.mm3;


public class ListViewItem
{
    private String time_hour;
    private String time_min;
    private String time_ampm;
    public void set_time(String hour)
    {
        time_hour = hour;
    }
    public void set_min(String min)
    {
        time_min = min;
    }
    public void set_ampm(String ampm)
    {
        time_ampm = ampm;
    }

    public String getHour()
    {
        return this.time_hour;
    }
    public String getMin()
    {
        return this.time_min;
    }
    public String getAmpm()
    {
        return this.time_ampm;
    }

}
