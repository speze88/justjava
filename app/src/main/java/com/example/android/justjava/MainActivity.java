package com.example.android.justjava;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

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
    private String createOrderSummary(int price, int quantity, boolean addWhippedCream) {
        Resources res = getResources();
        return String.format("%s: %s\n%s? %s\n%s: %d\n%s: %d €\n%s!",
                res.getString(R.string.name), "Kapitain Kunal",
                res.getString(R.string.whippedcream), (addWhippedCream ? res.getString(R.string.yes) : res.getString(R.string.no)),
                res.getString(R.string.quantity), quantity,
                res.getString(R.string.total), price * quantity,
                res.getString(R.string.thanks));
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int priceForCoffee = 5;

        CheckBox whippedCream = (CheckBox) findViewById(R.id.whippedcream_checkbox_view);
        boolean hasWhippedCream = whippedCream.isChecked();

        Log.v(TAG, "Has whipped cream: " + hasWhippedCream);
        displayMessage(createOrderSummary(priceForCoffee, getQuantity(), hasWhippedCream));
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
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
     * This method increases the value of quantity by 1
     */
    public void increment(View view) {
        Button decreaseButton = (Button) findViewById(R.id.button_decrease);
        display(getQuantity() + 1);

        if (getQuantity() + 1 > 0) {
            decreaseButton.setEnabled(true);
        }
    }

    /**
     * This method decreases the value of quantity by 1
     */
    public void decrement(View view) {
        Button decreaseButton = (Button) findViewById(R.id.button_decrease);
        display(getQuantity() - 1);

        if (getQuantity() == 0) {
            decreaseButton.setEnabled(false);
        }
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