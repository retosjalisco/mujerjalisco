package com.dotstudio.mujerjalisco;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapterVentas extends BaseAdapter {

    private ArrayList<ItemGet> listData;
    Context context;
    public ImageLoader imageLoader;

    public CustomListAdapterVentas(Context context, ArrayList<ItemGet> listData) {
        this.listData = listData;
        this.context = context;
        imageLoader=new ImageLoader(context.getApplicationContext());
    }

    @Override
    public int getCount() { return listData.size(); }

    @Override
    public Object getItem(int position) { return listData.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.ventasrow, null);

            holder = new ViewHolder();
            holder.titulotxt = (TextView) convertView.findViewById(R.id.textView13);
            holder.descripciontxt = (TextView) convertView.findViewById(R.id.textView14);
            holder.fechatxt = (TextView) convertView.findViewById(R.id.textView15);
            holder.foto = (ImageView) convertView.findViewById(R.id.imageView3);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.titulotxt.setText(listData.get(position).getTitulo());
        holder.descripciontxt.setText(listData.get(position).getSubtitulo());
        holder.fechatxt.setText(listData.get(position).getFecha());

        String path = listData.get(position).getImagen();
        imageLoader.DisplayImage(path, holder.foto);

        return convertView;
    }

    static class ViewHolder {
        TextView titulotxt;
        TextView descripciontxt;
        TextView fechatxt;
        TextView nocomentariostxt;
        ImageView foto;
    }
}
