package hamza.example.fitkitapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by Hamza on 2/5/2016.
 */
public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyFitKitApp.db";

    // Table 1
    public static final String TABLE_NAME = "User";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "UserName";
    public static final String COL_3 = "Password";
    public static final String COL_4 = "Gender";
    public static final String COL_5 = "Email";
    public static final String COL_6 = "Country";
    public static final String COL_7 = "Current_Weight";
    public static final String COL_8 = "Goal_Weight";
    public static final String COL_9 = "Height";
    public static final String COL_10 = "Age";

    // Table 2
    public static final String TABLE_NAME_2 = "Food";
    public static final String FID = "ID";
    public static final String FNAME = "Name";
    public static final String FSIZE = "Serving_Size";
    public static final String FQUANT = "Quantity";
    public static final String FCAL = "Calories";
    public static final String FFAT = "Fat";
    public static final String FCARBS = "Carbs";
    public static final String FPRO = "Protein";

    // Table 3
    public static final String TABLE_NAME_3 = "Food_Added";
    public static final String UserID = "USER_ID";
    public static final String FoodID = "FOOD_ID";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + "," + COL_2 + " TEXT" + "," + COL_3 + " PASSWORD" + "," + COL_4 + " TEXT" + "," + COL_5 + " EMAIL" + "," + COL_6 + " TEXT" + "," + COL_7 + " FLOAT" + "," + COL_8 + " FLOAT" + "," + COL_9 + " FLOAT" + "," + COL_10 + " INTEGER" + ");");
        db.execSQL("CREATE TABLE " + TABLE_NAME_2 + " (" + FID + " INTEGER PRIMARY KEY AUTOINCREMENT" + "," + FNAME + " TEXT" + "," + FSIZE + " TEXT" + "," + FQUANT + " INTEGER" + "," + FCAL + " FLOAT" + "," + FFAT + " FLOAT" + "," + FCARBS + " FLOAT" + "," + FPRO + " FLOAT" + ");");
        db.execSQL("CREATE TABLE " + TABLE_NAME_3 + " (" + UserID + " INTEGER" + "," + FoodID + " INTEGER" + "," + " PRIMARY KEY (" + UserID + "," + FoodID + ")" + " FOREIGN KEY (" + UserID + ") REFERENCES " + TABLE_NAME + "(" + COL_1 + ")" + "," + " FOREIGN KEY (" + FoodID + ") REFERENCES " + TABLE_NAME_2 + "(" + FID + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Food");
        db.execSQL("DROP TABLE IF EXISTS Food_Added");
        onCreate(db);
    }

    public boolean insert(String UserName, String Password,String Gender, String Email, String Country, Float CWeight, Float GWeight, Float Height,Integer Age )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,UserName);
        contentValues.put(COL_3,Password);
        contentValues.put(COL_4,Gender);
        contentValues.put(COL_5,Email);
        contentValues.put(COL_6,Country);
        contentValues.put(COL_7, CWeight);
        contentValues.put(COL_8, GWeight);
        contentValues.put(COL_9, Height);
        contentValues.put(COL_10, Age);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertFood(String Name, String Size,int Quantity, Float Calories, Float Fat, Float Carbs, Float Protein)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FNAME,Name);
        contentValues.put(FSIZE,Size);
        contentValues.put(FQUANT,Quantity);
        contentValues.put(FCAL,Calories);
        contentValues.put(FFAT,Fat);
        contentValues.put(FCARBS,Carbs);
        contentValues.put(FPRO,Protein);
        long result = db.insert(TABLE_NAME_2,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertFoodAdded(Integer UserID, Integer FoodID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.UserID,UserID);
        contentValues.put(this.FoodID,FoodID);

        long result = db.insert(TABLE_NAME_3,null,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllUsers()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor getLoggedInUser(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME +" WHERE ID = " + UserID, null);
    }

    public Cursor getAllFoods()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_2, null);
    }

    public Cursor getAllFoodsCalories()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT SUM(Calories) FROM " + TABLE_NAME_2, null);
    }


    public Cursor getAllFoodsCarbs()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT SUM(Carbs) FROM " + TABLE_NAME_2, null);
    }


    public Cursor getAllFoodsFat()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT SUM(Fat) FROM " + TABLE_NAME_2, null);
    }


    public Cursor getAllFoodsProtein()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT SUM(Protein) FROM " + TABLE_NAME_2, null);
    }
    public Cursor getSelectedFoods(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT " + FoodID + " FROM " + TABLE_NAME_3 + " WHERE " + this.UserID + " = "  +  UserID , null);

    }

    public Cursor getFoodsThroughID(int FID)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE  ID = " +  + FID  , null);
    }

    public boolean deleteUser(long rowId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_1 + "=" + rowId, null) > 0;
    }

    public boolean deleteFood(long rowId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_2, FID + "=" + rowId, null) > 0;
    }

    public Cursor getFoodCount(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT COUNT(Food_ID) FROM " + TABLE_NAME_3 + " WHERE " + this.UserID + "=" + UserID, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getCaloriesSum(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT SUM(Calories) FROM " + TABLE_NAME_3 + " FA" + "," + TABLE_NAME_2 + " F" + " WHERE " + "F.ID " + "= " + "FA.FOOD_ID " + "AND " + this.UserID  + "=" + UserID , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getCarbsSum(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT SUM(Carbs) FROM " + TABLE_NAME_3 + " FA" + "," + TABLE_NAME_2 + " F" + " WHERE " + "F.ID " + "= " + "FA.FOOD_ID " + "AND " + this.UserID  + "=" + UserID , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getCarbs(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT Carbs FROM " + TABLE_NAME_3 + " FA" + "," + TABLE_NAME_2 + " F" + " WHERE " + "F.ID " + "= " + "FA.FOOD_ID " + "AND " + this.UserID  + "=" + UserID , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getFatSum(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT SUM(Fat) FROM " + TABLE_NAME_3 + " FA" + "," + TABLE_NAME_2 + " F" + " WHERE " + "F.ID " + "= " + "FA.FOOD_ID " + "AND " + this.UserID  + "=" + UserID , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getFat(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT Fat FROM " + TABLE_NAME_3 + " FA" + "," + TABLE_NAME_2 + " F" + " WHERE " + "F.ID " + "= " + "FA.FOOD_ID " + "AND " + this.UserID  + "=" + UserID , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getProteinSum(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT SUM(Protein) FROM " + TABLE_NAME_3 + " FA" + "," + TABLE_NAME_2 + " F" + " WHERE " + "F.ID " + "= " + "FA.FOOD_ID " + "AND " + this.UserID  + "=" + UserID , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getProtein(int UserID)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor mCursor = db.rawQuery("SELECT Protein FROM " + TABLE_NAME_3 + " FA" + "," + TABLE_NAME_2 + " F" + " WHERE " + "F.ID " + "= " + "FA.FOOD_ID " + "AND " + this.UserID  + "=" + UserID , null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getContactThroughID(long rowId) throws SQLException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor =
                db.query(true, TABLE_NAME, new String[]{COL_1,
                                COL_2, COL_5}, COL_1 + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getContactThroughUserName(String username) throws SQLException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor =
                db.rawQuery("SELECT ID,UserName,Password FROM " + TABLE_NAME + " WHERE UserName = " + "'" + username + "'", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getLoginDetails(String username,String password) throws SQLException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.rawQuery("SELECT ID,UserName,Password FROM " + TABLE_NAME + " WHERE UserName = " + "'" + username + "'" + " AND" + " Password = " + "'" + password +  "'", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getFood(String name) throws SQLException
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor =
                db.rawQuery("SELECT * FROM " + TABLE_NAME_2 + " WHERE Name == " + "'"+ name + "'", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
