package neit.em_lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;
import static java.lang.Integer.parseInt;

// boolean needs to be int   true  1  or false  0

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 2;

	// Database Name
	private static final String DATABASE_NAME = "orderManager";

	// Contacts table name
	private static final String TABLE_ORDERS = "orders";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_FNAME = "firstName";
	private static final String KEY_LNAME = "lastName";
	private static final String KEY_TYPEOFCHOC = "typeOfChoc"; // spinner text
	private static final String KEY_NUMOFBARS = "numberOfBars";
	private static final String KEY_SHIPTYPE = "shipmentType";  // 1 for NormalShipment or 0 for Expited
	private static final String KEY_PRICE = "price";
	//private int shipmentType = 0;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY,"
				+ KEY_FNAME + " TEXT,"
				+ KEY_LNAME + " TEXT,"
				+ KEY_TYPEOFCHOC + " TEXT,"
				+ KEY_NUMOFBARS + " INTEGER,"
				+ KEY_SHIPTYPE + " INTEGER,"
				+ KEY_PRICE + " REAL" + ")";

		db.execSQL(CREATE_ORDERS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new order
	void addOrder(Order order) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(KEY_FNAME, order.getFirstName());
		values.put(KEY_LNAME, order.getLastName());
		values.put(KEY_TYPEOFCHOC, order.getTypeOfChocolate());
		values.put(KEY_NUMOFBARS, order.getBars());
		if (order.getShippingType().equals(true)){
			values.put(KEY_SHIPTYPE, 1);
		}
		else{
			values.put(KEY_SHIPTYPE, 0);
		}
		values.put(KEY_PRICE, order.getPrice());

		// Inserting Row
		db.insert(TABLE_ORDERS, null, values);
		db.close(); // Closing database connection
	}

	// Getting All Orders
	public List<Order> getAllOrders() {
		List<Order> orderList = new ArrayList<Order>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_ORDERS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Order order = new Order();
				order.set_id(parseInt(cursor.getString(0)));
				order.setFirstName(cursor.getString(1));
		        order.setLastName(cursor.getString(2));
				order.setTypeOfChocolate(cursor.getString(3));
				order.setBars(parseInt(cursor.getString(4)));
				order.setShippingType(Boolean.parseBoolean(cursor.getString(5))); //////////
				order.setPrice(Float.parseFloat(cursor.getString(6))); ////////////////

				// Adding contact to list
				orderList.add(order);
			} while (cursor.moveToNext());
		}

		// return account list
		return orderList;
	}

	// Updating single account
//	public int updateAccount(BankAccount bankAccount) {
//		SQLiteDatabase db = this.getWritableDatabase();
//
//		ContentValues values = new ContentValues();
//		values.put(KEY_NAME, bankAccount.getName());
//		values.put(KEY_TYPE, bankAccount.getType());
//
//		// updating row
//		return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//				new String[] { String.valueOf(bankAccount.getID()) });
//	}
//
//	// Deleting single account
//	public void deleteAccount(BankAccount bankAccount) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//				new String[] { String.valueOf(bankAccount.getID()) });
//		db.close();
//	}


	// Getting Orders Count
	public int getOrdersCount() {
		String countQuery = "SELECT  * FROM " + TABLE_ORDERS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

    // Getting All Orders By Id
    public List<Order> getOrdersById(int idNumber) {
        List<Order> orderList1 = new ArrayList<Order>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDERS + " WHERE " + KEY_ID + " = " + "'" + idNumber + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

		Boolean shipment;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
				Order order = new Order();
				order.set_id(Integer.parseInt(cursor.getString(0)));
				order.setFirstName(cursor.getString(1));
				order.setLastName(cursor.getString(2));
				order.setTypeOfChocolate(cursor.getString(3));
				order.setBars(parseInt(cursor.getString(4)));
				if (cursor.getInt(5) == 1 ) {
					shipment = true;
					order.setShippingType(shipment); //////////
				}
				else {
					shipment = false;
					order.setShippingType(shipment); //////////
				}
					order.setPrice(Float.parseFloat(cursor.getString(6))); ////////////////
                // Adding orderbyId to list
				orderList1.add(order);
            } while (cursor.moveToNext());
        }

        // return account list
        return orderList1;
    }

	// Getting All AMOUNTS GREATER THAN
	public List<Order> getAllAmountsGreaterThan(float priceAmount) {
		List<Order> orderList = new ArrayList<Order>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_ORDERS + " WHERE " + KEY_PRICE + " > " + "'" + priceAmount + "'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Order order = new Order();
				order.set_id(parseInt(cursor.getString(0)));
				order.setFirstName(cursor.getString(1));
				order.setLastName(cursor.getString(2));
				order.setTypeOfChocolate(cursor.getString(3));
				order.setBars(parseInt(cursor.getString(4)));
				order.setShippingType(Boolean.parseBoolean(cursor.getString(5))); //////////
				order.setPrice(Float.parseFloat(cursor.getString(6))); ////////////////

				// Adding contact to list
				orderList.add(order);
			} while (cursor.moveToNext());
		}

		// return account list
		return orderList;
	}

	// Getting first Order
	public List<Order> getFirstOrder() {
		List<Order> orderList1 = new ArrayList<Order>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_ORDERS + " WHERE " + KEY_ID + " = " + "'" + 1 + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		Boolean shipment;

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Order order = new Order();
				order.set_id(Integer.parseInt(cursor.getString(0)));
				order.setFirstName(cursor.getString(1));
				order.setLastName(cursor.getString(2));
				order.setTypeOfChocolate(cursor.getString(3));
				order.setBars(parseInt(cursor.getString(4)));
				if (cursor.getInt(5) == 1 ) {
					shipment = true;
					order.setShippingType(shipment); //////////
				}
				else {
					shipment = false;
					order.setShippingType(shipment); //////////
				}
				order.setPrice(Float.parseFloat(cursor.getString(6))); ////////////////
				// Adding orderbyId to list
				orderList1.add(order);
			} while (cursor.moveToNext());
		}

		// return account list
		return orderList1;
	}


}
