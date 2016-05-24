package hamza.example.fitkitapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hamza on 2/10/2016.
 */
public class Adaptor extends  BaseAdapter implements Filterable {

    private final LayoutInflater mInflater;
    Context context;
    List<Food> list = null;
    List<Food> filtered_list = null;
    ItemFilter mFilter = new ItemFilter();
    Database db;
    Bundle b = new Bundle();


    public Adaptor(Context context, List<Food> l) {
        this.context = context;
        this.list = l;
        this.filtered_list = l;
        mInflater = LayoutInflater.from(context);
        db = new Database(context);
    }

    public void add(Food food) {
        filtered_list.add(food);
        notifyDataSetChanged();
    }


    public void remove(Food food) {
        filtered_list.remove(food);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filtered_list.size();
    }

    @Override
    public Object getItem(int position) {
        return filtered_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final ViewHolder holder;
        if (convertView == null) {
            convertView =  mInflater.inflate(R.layout.list_view, null);
            holder = new ViewHolder();
            holder.Food_name = (TextView) convertView.findViewById(R.id.FoodName);
            holder.Food_size = (TextView)convertView.findViewById(R.id.FoodDetailsSize);
            holder.Food_calories = (TextView)convertView.findViewById(R.id.FoodDetailsCalories);
            convertView.setTag(holder);

        }else{
            holder  = (ViewHolder) convertView.getTag();
        }


        Food food = (Food) getItem(position);
        holder.Food_name.setText(filtered_list.get(position).getName());
        holder.Food_size.setText(filtered_list.get(position).getSize());
        holder.Food_calories.setText(String.valueOf(filtered_list.get(position).getCalories()));


        return convertView;
    }


    @Override
    public Filter getFilter() {
        return mFilter;
    }

    
    private class ViewHolder{
        TextView Food_name;
        TextView Food_size;
        TextView Food_calories;
    }

    
    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final List<Food> List = list;

            int count = List.size();
            final ArrayList<Food> nlist = new ArrayList<Food>(count);

            Food filterableString ;

            for (int i = 0; i < count; i++) {
                filterableString = List.get(i);
                if (filterableString.toString().toLowerCase().contains(filterString)) {
                    nlist.add(filterableString);
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            filtered_list = (ArrayList<Food>) results.values;
            notifyDataSetChanged();
        }
    }



}
