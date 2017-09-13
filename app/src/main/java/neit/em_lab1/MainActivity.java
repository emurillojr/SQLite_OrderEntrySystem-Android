package neit.em_lab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import static android.R.attr.value;
import static android.R.attr.y;
import static android.os.Build.VERSION_CODES.N;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;
//import static neit.em_lab1.ResultActivity.CandyOrders;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 10; ///////
    //private static final int REQUEST_CODE1 = 11; ///////
    Order purchaseOrder = new Order();

    private EditText txtFirstName; // first name
    private EditText txtLastName; // last name
    private Spinner ddTypeOfChoc; // dropdown type of chocolate
    private EditText txtNumberOfBars; // number of bars
    private RadioButton rbNormalShipment; // normal shipment
    private RadioButton rbExpited; // expidited shipment
    private Boolean shipment; // true or false for radio button
    private TextView txtResults; // text results
    private EditText txtIDNumber;  // id number   added***
    private Button btnGetResults; // get results button
    private Button btnGetOrderByID;  // get order by id button added***
    private EditText txtPrice;  //  price   added***
    private Button btnViewAllOrders;  //  view all orders button added***

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data.hasExtra("returnkey")) {
            Integer result = data.getExtras().getInt("returnkey");
            txtResults.setText("Orders " + result.toString());
            txtFirstName.requestFocus(); // added***
            txtFirstName.setText("");
            txtLastName.setText("");
            ddTypeOfChoc.setSelection(0);
            txtNumberOfBars.setText("");
            rbNormalShipment.setChecked(true);
            txtPrice.setText("");  // added****
            txtIDNumber.setText("");  // added***
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtFirstName = (EditText) findViewById(R.id.txtFirstName); // first name
        txtLastName = (EditText) findViewById(R.id.txtLastName); // last name
        ddTypeOfChoc = (Spinner) findViewById(R.id.ddTypeOfChoc); // dropdown
        rbNormalShipment = (RadioButton) findViewById(R.id.rbNormalShipment); // normal shipment
        rbExpited = (RadioButton) findViewById(R.id.rbExpited); // expidited
        txtNumberOfBars = (EditText) findViewById(R.id.txtNumberOfBars); // number of bars
        txtResults = (TextView) findViewById(R.id.txtResults);// results
        txtIDNumber = (EditText) findViewById(R.id.txtIDNumber); // id number  // added***
        btnGetOrderByID = (Button) findViewById(R.id.btnGetOrderByID); // button get order by id
        txtPrice = (EditText) findViewById(R.id.txtPrice); // price   // added***
        //btnSave = (Button)findViewById(R.id.btnSave); // save button   - no longer in use
        btnGetResults = (Button) findViewById(R.id.btnGetResults); // NEW save button  just changed check
        btnViewAllOrders = (Button) findViewById(R.id.btnViewAllOrders); // view all orders button

        btnGetResults.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String firstName = txtFirstName.getText().toString();
                String lastName = txtLastName.getText().toString();
                String TypeOfChoc = ddTypeOfChoc.getSelectedItem().toString(); // dropdown
                String bars = txtNumberOfBars.getText().toString();
                String priceAmount = txtPrice.getText().toString(); // added***
                int NumberOfBars = parseInt(bars);
                float Price = Float.parseFloat(priceAmount); // added***
                //Order purchaseOrder = new Order();
                purchaseOrder.setFirstName(firstName);
                purchaseOrder.setLastName(lastName);
                purchaseOrder.setTypeOfChocolate(TypeOfChoc);
                purchaseOrder.setBars(NumberOfBars);

                if (rbNormalShipment.isChecked()) {
                    shipment = true;

                } else {
                    shipment = false;
                }
                purchaseOrder.setShippingType(shipment);
                purchaseOrder.setPrice(Price); // added***
                Intent i = new Intent(getApplicationContext(), neit.em_lab1.ResultActivity.class);
                i.putExtra("fNameKey", purchaseOrder);
                startActivityForResult(i, REQUEST_CODE);
            }
        });


    }

    public void GetOrderByID(View myView) {
        DatabaseHandler myDb = new DatabaseHandler(MainActivity.this);
        String idToSearch = txtIDNumber.getText().toString();
        int idNumber = parseInt(idToSearch);
        myDb.getOrdersById(idNumber);

        List<Order> myOrders = myDb.getOrdersById(idNumber);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
        // adding entries in List

        for (int counter = 0; counter < myOrders.size(); counter++) {
            adapter.add(myOrders.get(counter).get_id() + " - " +
                    myOrders.get(counter).getFirstName() +
                    myOrders.get(counter).getLastName() +
                    myOrders.get(counter).getShippingType() +
                    myOrders.get(counter).getBars() +
                    myOrders.get(counter).getPrice() +
                    myOrders.get(counter).getTypeOfChocolate()
            );
            txtFirstName.setText(myOrders.get(counter).getFirstName());
            txtLastName.setText(myOrders.get(counter).getLastName()); //+
            txtNumberOfBars.setText(String.valueOf(myOrders.get(counter).getBars()));

            txtPrice.setText(String.format("%.02f", myOrders.get(counter).getPrice()));
            boolean ship = myOrders.get(counter).getShippingType();
            if (ship == true) {
                rbNormalShipment.setChecked(true);
            } else {
                rbExpited.setChecked(true);
            }
            String choc = myOrders.get(counter).getTypeOfChocolate();
            ddTypeOfChoc.setSelection(((ArrayAdapter<String>) ddTypeOfChoc.getAdapter()).getPosition(choc));
            //txtPrice.setText(Boolean.toString(myOrders.get(counter).getShippingType())); // true false tester
        }
        // setting adapter to list
        //lsResults.setAdapter(adapter);

    }


//    public void ViewAllOrders(View view){
//        //final Context context = this;
//        Intent i = new Intent(MainActivity.this, neit.em_lab1.ResultActivity.class);
//        //i.putExtra("fNameKey1", "");
//        startActivityForResult(i, REQUEST_CODE1);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.getFirst) {
            DatabaseHandler myDb = new DatabaseHandler(MainActivity.this); ///////////
            myDb.getFirstOrder();
            List<Order> myOrders = myDb.getFirstOrder();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1);
            // adding entries in List

            for (int counter = 0; counter < myOrders.size(); counter++) {
                adapter.add(myOrders.get(counter).get_id() + " - " +
                        myOrders.get(counter).getFirstName() +
                        myOrders.get(counter).getLastName() +
                        myOrders.get(counter).getShippingType() +
                        myOrders.get(counter).getBars() +
                        myOrders.get(counter).getPrice() +
                        myOrders.get(counter).getTypeOfChocolate()
                );
                txtFirstName.setText(myOrders.get(counter).getFirstName());
                txtLastName.setText(myOrders.get(counter).getLastName()); //+
                txtNumberOfBars.setText(String.valueOf(myOrders.get(counter).getBars()));

                txtPrice.setText(String.format("%.02f", myOrders.get(counter).getPrice()));
                boolean ship = myOrders.get(counter).getShippingType();
                if (ship == true) {
                    rbNormalShipment.setChecked(true);
                } else {
                    rbExpited.setChecked(true);
                }
                String choc = myOrders.get(counter).getTypeOfChocolate();
                ddTypeOfChoc.setSelection(((ArrayAdapter<String>) ddTypeOfChoc.getAdapter()).getPosition(choc));
            }


        }
        if (id == R.id.doubleOrder) {
            String bars = txtNumberOfBars.getText().toString();
            if (bars.equals("")) {
                bars = "0";
            }

            int NumberOfBars = parseInt(bars);
            int newNum = NumberOfBars * 2;
            txtNumberOfBars.setText("" + newNum);
        }
        if (id == R.id.newPlus) {  // add button
            txtFirstName.setText("");
            txtLastName.setText("");
            rbNormalShipment.setChecked(true);
            txtNumberOfBars.setText("");
            ddTypeOfChoc.setSelection(0);
            txtPrice.setText("");  // added *****
        }

        return super.onOptionsItemSelected(item);
    }

}
