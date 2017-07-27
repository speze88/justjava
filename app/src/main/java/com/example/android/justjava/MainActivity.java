package com.example.android.justjava;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private static final String TAG = "MainActivity";
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * This method retrieves the numeric value of the TextView that displays the quantity
     *
     * @return
     */
    private int getQuantity() {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        return Integer.valueOf(quantityTextView.getText().toString());
    }

    /**
     * Create summary of the order
     * @param price
     * @param quantity
     * @return
     */
    private String createOrderSummary(int price, int quantity, boolean addWhippedCream, boolean addChocolate, String name) {
        Resources res = getResources();
        return String.format("%s: %s\n%s? %s\n%s? %s\n%s: %d\n%s: %d €\n%s!",
                res.getString(R.string.name), name,
                res.getString(R.string.whippedcream), (addWhippedCream ? res.getString(R.string.yes) : res.getString(R.string.no)),
                res.getString(R.string.chocolate), (addChocolate ? res.getString(R.string.yes) : res.getString(R.string.no)),
                res.getString(R.string.quantity), quantity,
                res.getString(R.string.total), price,
                res.getString(R.string.thanks));
    }

    /**
     * calculate price
     * @param quantity
     * @param hasWhippedCream
     * @param hasChocolate
     * @return
     */
    private int calculatePrice(int quantity, boolean hasWhippedCream, boolean hasChocolate) {
        int priceForCoffee = 5;
        int priceForWhippedCream = 1;
        int priceForChocolate = 2;

        if(hasWhippedCream) {
            priceForCoffee += priceForWhippedCream;
        }

        if(hasChocolate) {
            priceForCoffee += priceForChocolate;
        }
        return priceForCoffee * quantity;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whippedcream_checkbox_view);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox_view);
        boolean hasChocolate = chocolate.isChecked();

        EditText nameEditTextView = (EditText) findViewById(R.id.name_edittext_view);
        String name = nameEditTextView.getText().toString();

        Log.v(TAG, "Has whipped cream: " + hasWhippedCream);
        Log.v(TAG, "Has chocolate: " + hasChocolate);
        Log.v(TAG, "Entered name: " + name);

        int priceForCoffee = calculatePrice(getQuantity(), hasWhippedCream, hasChocolate);
        Log.v(TAG, "Price for one coffee:" + priceForCoffee);

        //displayMessage(createOrderSummary(priceForCoffee, getQuantity(), hasWhippedCream, hasChocolate, name));
        sendOrderSummaryByEmail(createOrderSummary(priceForCoffee, getQuantity(), hasWhippedCream, hasChocolate, name));
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void setQuantity(int number) {
        Button increaseButton = (Button) findViewById(R.id.button_increase);
        Button decreaseButton = (Button) findViewById(R.id.button_decrease);

        if (number > 0 && number < 10) {
            increaseButton.setEnabled(true);
            decreaseButton.setEnabled(true);

            if (number == 1) {
                decreaseButton.setEnabled(false);
                Toast.makeText(this, R.string.toast_invalid_quantity, Toast.LENGTH_SHORT).show();
            } else if (number == 9) {
                Toast.makeText(this, R.string.toast_invalid_quantity, Toast.LENGTH_SHORT).show();
                increaseButton.setEnabled(false);
            }
        } else {
            return;
        }

        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
        return;
    }

    /**
     * This method displays the message on the screen
     *
     * @param message
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText("" + message);
    }

    /**
     * Pass order summary to email app
     * @param message
     */
    private void sendOrderSummaryByEmail(String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,message);
        sendIntent.setType("text/plain");

        if(sendIntent.resolveActivity(getPackageManager())!= null){
            startActivity(sendIntent);
        }
    }

    /**
     * This method increases the value of quantity by 1
     */
    public void increment(View view) {
        setQuantity(getQuantity() + 1);
    }

    /**
     * This method decreases the value of quantity by 1
     */
    public void decrement(View view) {
        setQuantity(getQuantity() - 1);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}