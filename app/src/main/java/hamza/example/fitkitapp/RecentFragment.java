package hamza.example.fitkitapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class RecentFragment extends Fragment {

    ListView listview;
    SearchView searchview;
    Adaptor adaptor;
    Database db;

    public RecentFragment() {
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
        View v = inflater.inflate(R.layout.fragment_recent, container, false);
        listview = (ListView) v.findViewById(R.id.listviewRecent);
        searchview = (SearchView)getActivity().findViewById(R.id.searchView);
        db = new Database(getActivity());

        showFood(getFood());

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                RecentFragment.this.adaptor.getFilter().filter(text);
                return false;
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
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", getActivity().MODE_PRIVATE);
        int UserID=pref.getInt("UserID", 1);
        Cursor c1 = db.getSelectedFoods(UserID);

        if(c1.moveToFirst()) {
            for (int i = 0; i < c1.getCount(); i++) {
                Cursor c2 = db.getFoodsThroughID(c1.getInt(0));
                if(c2.moveToFirst()) {

                    foodArray.add(new Food(c2.getString(1), c2.getString(2), c2.getInt(3), c2.getFloat(4), c2.getFloat(5), c2.getFloat(6), c2.getFloat(7)));
                }
                c1.moveToNext();

            }
        }
        return foodArray;
    }


}
