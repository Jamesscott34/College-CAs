package com.example.moobiledevelopmentca1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBHandler class for managing SQLite database operations.
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "restaurant.db";

    // Table names
    private static final String TABLE_MENU = "menu";
    private static final String TABLE_SPECIALS = "specials";
    private static final String TABLE_FEEDBACK = "feedback";

    // Menu Table Columns
    private static final String COLUMN_MENU_ID = "id";
    private static final String COLUMN_MENU_FOODTYPE = "foodtype";
    private static final String COLUMN_MENU_DESCRIPTION = "description";
    private static final String COLUMN_MENU_ALLERGIES = "allergies";
    private static final String COLUMN_MENU_PRICING = "pricing";

    // Specials Table Columns
    private static final String COLUMN_SPECIALS_ID = "id";
    private static final String COLUMN_SPECIALS_DAY = "day";
    private static final String COLUMN_SPECIALS_FOODTYPE = "foodtype";
    private static final String COLUMN_SPECIALS_ALLERGIES = "allergies";
    private static final String COLUMN_SPECIALS_PRICE = "price";

    // Feedback Table Columns
    private static final String COLUMN_FEEDBACK_ID = "id";
    private static final String COLUMN_FEEDBACK_RATING = "rating";
    private static final String COLUMN_FEEDBACK_NAVIGATION = "navigation";
    private static final String COLUMN_FEEDBACK_CHANGE = "change";
    private static final String COLUMN_FEEDBACK_LAYOUT = "layout";
    private static final String COLUMN_FEEDBACK_IMPROVEMENT = "improvement";

    /**
     * Constructor for DBHandler.
     *
     * @param context The context of the application.
     */
    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called when the database is created for the first time.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Menu Table
        String CREATE_MENU_TABLE = "CREATE TABLE " + TABLE_MENU + "("
                + COLUMN_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MENU_FOODTYPE + " TEXT,"
                + COLUMN_MENU_DESCRIPTION + " TEXT,"
                + COLUMN_MENU_ALLERGIES + " TEXT,"
                + COLUMN_MENU_PRICING + " REAL" + ")";
        db.execSQL(CREATE_MENU_TABLE);

        // Create Specials Table
        String CREATE_SPECIALS_TABLE = "CREATE TABLE " + TABLE_SPECIALS + "("
                + COLUMN_SPECIALS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SPECIALS_DAY + " TEXT,"
                + COLUMN_SPECIALS_FOODTYPE + " TEXT,"
                + COLUMN_SPECIALS_ALLERGIES + " TEXT,"
                + COLUMN_SPECIALS_PRICE + " REAL" + ")";
        db.execSQL(CREATE_SPECIALS_TABLE);

        // Create Feedback Table
        String CREATE_FEEDBACK_TABLE = "CREATE TABLE " + TABLE_FEEDBACK + "("
                + COLUMN_FEEDBACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FEEDBACK_RATING + " INTEGER,"
                + COLUMN_FEEDBACK_NAVIGATION + " TEXT,"
                + COLUMN_FEEDBACK_CHANGE + " TEXT,"
                + COLUMN_FEEDBACK_LAYOUT + " TEXT,"
                + COLUMN_FEEDBACK_IMPROVEMENT + " TEXT" + ")";
        db.execSQL(CREATE_FEEDBACK_TABLE);

        // Insert initial data into tables
        insertInitialMenuData(db);
        insertInitialSpecialsData(db);
    }

    /**
     * Called when the database needs to be upgraded.
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDBACK);

        // Create tables again
        onCreate(db);
    }

    /**
     * Inserts initial data into the Menu table.
     *
     * @param db The database.
     */
    private void insertInitialMenuData(SQLiteDatabase db) {
        String[] menuInserts = {
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Spring Rolls', 'Gluten', 5.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Garlic Bread', 'Gluten, Dairy', 4.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Stuffed Mushrooms', 'Dairy', 6.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Bruschetta', 'Gluten', 5.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Chicken Wings', 'None', 8.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Mozzarella Sticks', 'Dairy, Gluten', 7.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Nachos', 'Dairy', 9.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Onion Rings', 'Gluten', 6.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Calamari', 'Seafood, Gluten', 10.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Appetizer', 'Potato Skins', 'Dairy', 7.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'Grilled Chicken', 'None', 12.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'Beef Steak', 'None', 19.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'Spaghetti Bolognese', 'Gluten', 14.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'Vegetable Stir Fry', 'Soy', 11.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'Fish and Chips', 'Seafood, Gluten', 13.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'Chicken Alfredo', 'Dairy, Gluten', 15.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'Beef Tacos', 'Gluten', 12.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'Vegetarian Pizza', 'Dairy, Gluten', 11.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'BBQ Ribs', 'None', 18.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Main Course', 'Lamb Chops', 'None', 21.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Chocolate Cake', 'Gluten, Dairy', 6.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Ice Cream', 'Dairy', 4.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Cheesecake', 'Gluten, Dairy', 7.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Apple Pie', 'Gluten', 5.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Brownie', 'Gluten, Dairy', 6.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Fruit Salad', 'None', 4.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Panna Cotta', 'Dairy', 6.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Tiramisu', 'Gluten, Dairy', 7.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Lemon Tart', 'Gluten, Dairy', 6.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Dessert', 'Creme Brulee', 'Dairy', 7.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Coffee', 'None', 2.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Tea', 'None', 2.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Orange Juice', 'None', 3.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Lemonade', 'None', 3.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Soda', 'None', 2.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Milkshake', 'Dairy', 4.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Smoothie', 'None', 5.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Water', 'None', 1.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Iced Tea', 'None', 3.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Beverage', 'Hot Chocolate', 'Dairy', 3.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Salad', 'Caesar Salad', 'Dairy, Gluten', 7.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Salad', 'Greek Salad', 'Dairy', 8.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Salad', 'Garden Salad', 'None', 6.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Salad', 'Cobb Salad', 'Dairy', 9.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Salad', 'Caprese Salad', 'Dairy', 8.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Salad', 'Pasta Salad', 'Gluten', 7.49);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Salad', 'Quinoa Salad', 'None', 8.99);",
                "INSERT INTO menu (foodtype, description, allergies, pricing) VALUES ('Salad', 'Fruit Salad', 'None', 6.49);"
        };

        // Execute each insert statement
        for (String insert : menuInserts) {
            db.execSQL(insert);
        }
    }

    /**
     * Inserts initial data into the Specials table.
     *
     * @param db The database.
     */
    private void insertInitialSpecialsData(SQLiteDatabase db) {
        String[] specialsInserts = {
                "INSERT INTO specials (day, foodtype, allergies, price) VALUES ('Monday', 'Grilled Salmon', 'Seafood', 15.99);",
                "INSERT INTO specials (day, foodtype, allergies, price) VALUES ('Tuesday', 'Taco Tuesday', 'Gluten', 9.99);",
                "INSERT INTO specials (day, foodtype, allergies, price) VALUES ('Wednesday', 'Pasta Night', 'Gluten, Dairy', 12.99);",
                "INSERT INTO specials (day, foodtype, allergies, price) VALUES ('Thursday', 'BBQ Ribs', 'None', 14.99);",
                "INSERT INTO specials (day, foodtype, allergies, price) VALUES ('Friday', 'Fish Fry', 'Seafood, Gluten', 13.99);",
                "INSERT INTO specials (day, foodtype, allergies, price) VALUES ('Saturday', 'Steak Night', 'None', 19.99);",
                "INSERT INTO specials (day, foodtype, allergies, price) VALUES ('Sunday', 'Roast Dinner', 'None', 16.99);"
        };

        // Execute each insert statement
        for (String insert : specialsInserts) {
            db.execSQL(insert);
        }
    }
}