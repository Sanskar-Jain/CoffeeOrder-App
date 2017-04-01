package com.example.sanskarjain.coffeeorder;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.id.inputExtractEditText;
import static android.R.id.message;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int Quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox cb1 = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=cb1.isChecked();
        CheckBox cb2 = (CheckBox)findViewById(R.id.chocolate_topping_checkbox);
        boolean hasChocolateTopping=cb2.isChecked();
        EditText ed1 = (EditText)findViewById(R.id.name_field);
        String name = ed1.getText().toString();
        String pricemessage=createOrderSummary(Quantity , hasWhippedCream , hasChocolateTopping, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Coffee Order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT,pricemessage);
        intent.putExtra(Intent.EXTRA_EMAIL,"sjdangersj@gmail.com");
        if(intent.resolveActivity(getPackageManager()) !=null) {
            startActivity(intent);
        }

        //displayMessage(pricemessage);
    }

    public void increment(View view)  {
        if(Quantity==100) {
            Toast.makeText(this, "Maximum number of Coffees ordered at a time cannot exceed 100.", Toast.LENGTH_SHORT);
            return;
        }
        Quantity++;
        display(Quantity);
    }

    public void decrement(View view)  {
        if(Quantity==1) {
            Toast.makeText(this, "Minimum number of Coffees ordered at a time cannot be zero or negative.", Toast.LENGTH_SHORT);
            return;
        }
        Quantity--;
        display(Quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity,boolean whip,boolean choco) {

        int baseprice = 5;

        if(whip)
            baseprice+=1;
        if(choco)
            baseprice+=2;

        return baseprice*quantity;
    }

    private String createOrderSummary(int number , boolean addWhippedCream , boolean addChocolateTopping , String nam) {

        String pricemessage = "Name : " + nam;
        pricemessage = pricemessage + "\nQuantity : " + Quantity;
        pricemessage = pricemessage + "\nAdd Whipped Cream? " + addWhippedCream;
        pricemessage = pricemessage + "\nAdd Chocolate? " + addChocolateTopping;
        int price = calculatePrice(Quantity, addWhippedCream, addChocolateTopping);
        pricemessage = pricemessage + "\nTotal: $" + price;
        pricemessage = pricemessage + "\nThank You!";
        return pricemessage;
    }

}

