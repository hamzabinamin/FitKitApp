package hamza.example.fitkitapp;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AllFoodFragment extends Fragment {

    ListView listview;
    Adaptor adaptor;
    Database db;
    SearchView searchview;
    Bundle b;
    Intent n;

    public AllFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_food, null);
        listview = (ListView) v.findViewById(R.id.listviewAllFoods);
        db = new Database(getActivity());
        searchview = (SearchView)getActivity().findViewById(R.id.searchView);
        b = new Bundle();
        n = new Intent(getActivity(),Food_Details.class);
        showFood(getFood());

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                AllFoodFragment.this.adaptor.getFilter().filter(text);
                return false;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int itemPosition = position;

                String itemValue = (String) listview.getItemAtPosition(position).toString();

                Food object = new Food();
                object.setName(itemValue);

                try {
                    String FoodName = db.getFood(object.getName()).getString(1);
                    String FoodSize = db.getFood(object.getName()).getString(2);
                    int FoodQuantity = db.getFood(object.getName()).getInt(3);
                    float FoodCalories = db.getFood(object.getName()).getFloat(4);
                    float FoodFat = db.getFood(object.getName()).getInt(5);
                    float FoodCarbs = db.getFood(object.getName()).getInt(6);
                    float FoodProtein = db.getFood(object.getName()).getInt(7);

                    b.putString("FoodName",FoodName);
                    b.putString("FoodSize",FoodSize);
                    b.putInt("FoodQuantity", FoodQuantity);
                    b.putFloat("FoodCalories", FoodCalories);
                    b.putFloat("FoodFat", FoodFat);
                    b.putFloat("FoodCarbs", FoodCarbs);
                    b.putFloat("FoodProtein", FoodProtein);
                    n.putExtras(b);
                    startActivity(n);
                    //db.insertFoodAdded(UserID, FoodID);
                } catch (SQLException e) {
             //       e.printStackTrace();
                      }

                //favoriteList.add(itemValue);
             //   Toast.makeText(getActivity(), "Food Added", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    public void showFood(List<Food> food) {
        adaptor = new Adaptor(getActivity(), food);
        listview.setAdapter(adaptor);
    }

    private List<Food> getFood() {
        List<Food> foodArray = new ArrayList<>();

        Cursor c = db.getAllFoods();

        if(c.moveToFirst()) {
            for (int i = 0; i < c.getCount(); i++) {
                foodArray.add(new Food(c.getString(1), c.getString(2), c.getInt(3), c.getFloat(4), c.getFloat(5), c.getFloat(6), c.getFloat(7)));
                c.moveToNext();
            }
        }
        return foodArray;
    }



}
