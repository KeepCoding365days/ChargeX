package com.example.chargex;

        import static android.content.ContentValues.TAG;

        import static com.google.android.gms.common.util.CollectionUtils.listOf;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;

        import com.github.kittinunf.fuel.core.Body;
        import com.stripe.android.PaymentConfiguration;
        import com.stripe.android.paymentsheet.PaymentSheet;
        import com.stripe.android.paymentsheet.PaymentSheetResult;

        import org.json.JSONException;
        import org.json.JSONObject;
        import com.stripe.android.paymentsheet.*;

// Add the following lines to build.gradle to use this example's networking library:
//   implementation 'com.github.kittinunf.fuel:fuel:2.3.1'
        import com.github.kittinunf.fuel.Fuel;
        import com.github.kittinunf.fuel.core.FuelError;
        import com.github.kittinunf.fuel.core.Handler;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

        import kotlin.Pair;

/*public class Checkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    }
}*/


public class Checkout extends AppCompatActivity {
    private static final String TAG = "CheckoutActivity";
    PaymentSheet paymentSheet;
    String paymentClientSecret;

    PaymentSheet.CustomerConfiguration customerConfig;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);
        //Map<String,String> params=new HashMap<>();
        //params.put("Name","Affan");
        JSONObject json = new JSONObject();
        try {
            json.put("name", "affan");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        try {
            json.put("amount",2000);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        Fuel.INSTANCE.post("http://192.168.0.101:8080/create-payment-intent",null).header("Content-Type", "application/json")
                .body( ByteArrayBody.Companion.createFrom(json.toString().getBytes())).responseString(new Handler<String>() {
            @Override
            public void success(String s) {
                try {
                    final JSONObject result = new JSONObject(s);
                    customerConfig = new PaymentSheet.CustomerConfiguration(
                            result.getString("customer"),
                            result.getString("ephemeralKey")
                    );
                    paymentClientSecret = result.getString("clientSecret");
                    PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));
                    Log.d(TAG,"checkout is fine");
                } catch (JSONException e) {
                    Log.d(TAG,"an error occured!"+e);
                }
            }

            @Override
            public void failure(@NonNull FuelError fuelError) {
                Log.d(TAG,"error is:"+fuelError);
                /* handle error */ }
        });
    }

    public void listenr(View a){
        presentPaymentSheet();
    }
    public void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {

        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Log.d(TAG, "Canceled");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Log.e(TAG, "Got error: ", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            // Display for example, an order confirmation screen
            Log.d(TAG, "Completed");
        }
    }


    private void presentPaymentSheet() {
        final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("ChargeX.")
                .customer(customerConfig)
                // Set `allowsDelayedPaymentMethods` to true if your business handles payment methods
                // delayed notification payment methods like US bank accounts.
                .allowsDelayedPaymentMethods(true)
                .build();
        paymentSheet.presentWithPaymentIntent(
                paymentClientSecret,
                configuration
        );
    }
}