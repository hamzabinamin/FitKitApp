package hamza.example.fitkitapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class Add_Breakfast extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    Button save,recent,allfoods;
    ListView listview;
    SearchView searchview;
    Adaptor adaptor;
    Database db;
    Fragment f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__breakfast);

        back = (ImageView)findViewById(R.id.backFD);
        //save = (Button)findViewById(R.id.Btn_Save);
       // listview = (ListView)findViewById(R.id.listview);
        searchview = (SearchView)findViewById(R.id.searchView);
        recent = (Button)findViewById(R.id.BTN_Recent);
        allfoods = (Button)findViewById(R.id.BTN_ALLFOODS);
        db = new Database(this);

//        save.setOnClickListener(this);
        back.setOnClickListener(this);
        allfoods.setOnClickListener(this);
        recent.setOnClickListener(this);

       // showFood(getFood());

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {

                if(adaptor != null)
                Add_Breakfast.this.adaptor.getFilter().filter(text);
                return false;
            }
        });


    }
    @Override
    public void onClick(View v) {


        switch(v.getId())
        {
          /*  case R.id.Btn_Save:
                 startActivity(new Intent(Add_Breakfast.this, Food_Diary.class));
                 break;
                 */

            case R.id.BTN_Recent:
                f = new RecentFragment();
                break;

            case R.id.BTN_ALLFOODS:
                f = new AllFoodFragment();
                break;

            case R.id.backFD:
                startActivity(new Intent(Add_Breakfast.this, Food_Diary.class));
                break;

        }

        if(f != null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, f);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void showFood(List<Food> food) {
        adaptor = new Adaptor(getBaseContext(), food);
       // listview.setAdapter(adaptor);
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
