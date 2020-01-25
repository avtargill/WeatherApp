package com.example.weatherapp;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RecycleViewAdapter  extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private ArrayList<ConsolidatedWeather> weatherarray;
    private Context c;

    public RecycleViewAdapter(ArrayList<ConsolidatedWeather> weatherarray, Context c) {
        this.weatherarray = weatherarray;
        this.c = c;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dashboard, parent, false);//to be changed

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull   RecycleViewAdapter.ViewHolder holder, int position) {

        setImage(holder.img, getWeImage(weatherarray.get(position).getWeatherStateAbbr()));
        setText(holder.txt_temp, weatherarray.get(position).getWeatherStateName());
        try {
            holder.txt_day.setText(getDay(weatherarray.get(position).getApplicableDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return weatherarray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt_temp, txt_day;

        public ViewHolder(View view) {
            super(view);

         //   img = view.findViewById(R.id.temp_icon);

           // txt_temp = view.findViewById(R.id.weather_temp);
          //  txt_day = view.findViewById(R.id.day);

          //  itemView.setTag(this);

        }
    }


    public String getWeImage(String code) {
        return "https://www.metaweather.com/static/img/weather/png/" + code + ".png";
    }

    public void setImage(ImageView img, String link) {
        Picasso.get().load(link).into(img);
    }

    public void setText(TextView txtv, String msg) {
        txtv.setText(msg);
    }

    public String getDay(String d) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(d);
        String dayOfTheWeek = (String) DateFormat.format("EEEE", date);

        return dayOfTheWeek;
    }

}
