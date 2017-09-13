package neit.em_lab1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.order;
import static java.lang.Float.parseFloat;

public class ResultActivity extends AppCompatActivity {
    DatabaseHandler myDb = new DatabaseHandler(ResultActivity.this); ///////////
    ListView results;

    private EditText txtPriceToSearchAmount;
    private Button btnSearchByPrice;

    //static ArrayList<Order> CandyOrders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtPriceToSearchAmount = (EditText) findViewById(R.id.txtPriceToSearchAmount);
        btnSearchByPrice = (Button) findViewById(R.id.btnSearchByPrice);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();

        //Order newOrder = extras.getParcelable("fNameKey");
        Order newOrder = extras.getParcelable("fNameKey");

        //Order newOrders = new Order(newOrder);
        //DecimalFormat df = new DecimalFormat("#.00");
        //System.out.println(df.format(f));
        // CandyOrders.add(newOrder);
        myDb.addOrder(newOrder);
        results = (ListView)findViewById(R.id.lstViewOrders);
//      int intBooltype;

        List<Order> myOrders = myDb.getAllOrders();


//        if (newOrder.getShippingType().equals(true)){
//            intBooltype = 1;
//        }
//        else {
//            intBooltype = 0;
//        }


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
// adding entries in List


        for (int counter = 0 ; counter < myOrders.size();counter++) {
            adapter.add(
                    //myOrders.get(counter).get_id() + " - " +
                    myOrders.get(counter).getFirstName() + " " +
                            myOrders.get(counter).getLastName() + " " +
                            "id = " + myOrders.get(counter).get_id() + " - " +
                            myOrders.get(counter).getTypeOfChocolate() + " " +
                            "$" + String.format("%.02f", myOrders.get(counter).getPrice()) //+ " " +
                    //myOrders.get(counter).getBars() + " " +
                    //intBooltype



                    // myOrders.get(counter).getShippingType() + " " +     // false all the time

            );
        }
        results.setAdapter(adapter);
    }

    public void finish() {
        Intent i = new Intent();
        Integer ResultNum = myDb.getOrdersCount();
        i.putExtra("returnkey",ResultNum);
        setResult(RESULT_OK, i);
        super.finish();
    }

    public void GoBack(View view){  ///////////////
        finish();
    }


    public void SearchByPrice(View view){

        String amount = txtPriceToSearchAmount.getText().toString();
        float searchAmount = parseFloat(amount);
        myDb.getAllAmountsGreaterThan(searchAmount);
        List<Order> myOrders = myDb.getAllAmountsGreaterThan(searchAmount);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1);
// adding entries in List
        for (int counter = 0 ; counter < myOrders.size();counter++) {
            adapter.add(
                    //myOrders.get(counter).get_id() + " - " +
                    myOrders.get(counter).getFirstName() + " " +
                            myOrders.get(counter).getLastName() + " " +
                            "id = " + myOrders.get(counter).get_id() + " - " +
                            myOrders.get(counter).getTypeOfChocolate() + " " +
                            "$" + String.format("%.02f", myOrders.get(counter).getPrice()) //+ " " +
            );
        }
        results.setAdapter(adapter);
    }




}
