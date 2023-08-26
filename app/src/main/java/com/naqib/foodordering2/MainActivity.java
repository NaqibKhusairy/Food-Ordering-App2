package com.naqib.foodordering2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup food;
    RadioButton Food;
    CheckBox mushroomsoup, breadsticks, croutons;
    Button submit, call, calculate;
    EditText subtotal, tax, ordertotal;
    double MainMenu, AddOn, Subtotal, Tax, OrderTotal;
    int FOOD;
    String FOod, ADDon, ORder, ToasT;
    private static final int PERMISSIONS_REQUEST_SMS = 2;
    private int requestCode;
    private String[] permissions;
    private int[] grantResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        food = findViewById(R.id.radioGroup);
        mushroomsoup = findViewById(R.id.cbMushroomSoup);
        breadsticks = findViewById(R.id.cbBreadSticks);
        croutons = findViewById(R.id.cbCroutons);
        submit = findViewById(R.id.button);
        call = findViewById(R.id.button2);
        calculate = findViewById(R.id.button3);
        subtotal = findViewById(R.id.editTextTextPersonName);
        tax = findViewById(R.id.editTextTextPersonName2);
        ordertotal = findViewById(R.id.editTextTextPersonName3);

        MainMenu = 0.00;
        AddOn = 0.00;
        ADDon = "";
        ORder = "";

        submit.setOnClickListener(view -> {
            harga();
            ORder = FOod+ADDon;
            if (!ToasT.equals("Please Choose The Main Menu")) {
                try {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SMS);
                    } else {
                        sendSms();
                    }
                } catch (Exception e) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, PERMISSIONS_REQUEST_SMS);
                }
            }
            MainMenu = 0.00;
            AddOn = 0.00;
            ADDon = "";
            ToasT = "";
        });

        call.setOnClickListener(view -> {
            Intent x = new Intent(Intent.ACTION_DIAL);
            x.setData(Uri.parse("tel:+60104220085"));
            startActivity(x);
        });

        calculate.setOnClickListener(view -> {
            harga();
            MainMenu = 0.00;
            AddOn = 0.00;
            ToasT = "";
        });
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public  void harga(){
        try {
            FOOD = food.getCheckedRadioButtonId();
            Food = findViewById(FOOD);
            FOod = Food.getText().toString();
            MainMenu = Double.parseDouble(FOod.split(" ")[3]);
            if (mushroomsoup.isChecked()) {
                AddOn += 1.00;
                ADDon += ", " + mushroomsoup.getText().toString();
            }
            if (breadsticks.isChecked()) {
                AddOn += 1.00;
                ADDon += ", " + breadsticks.getText().toString();
            }
            if (croutons.isChecked()) {
                AddOn += 1.00;
                ADDon += ", " + croutons.getText().toString();
            }
            Subtotal = MainMenu + AddOn;
            Tax = 11 * Subtotal / 100;
            OrderTotal = Subtotal + Tax;

            subtotal.setText("RM " + (String.format("%.2f", Subtotal)));
            tax.setText("RM " + (String.format("%.2f", Tax)));
            ordertotal.setText("RM " + (String.format("%.2f", OrderTotal)));
        }
        catch (Exception e) {
            ToasT = "Please Choose The Main Menu";
            Toast.makeText(MainActivity.this, ToasT, Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSms() {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+60104220085", null, "SMS Sent Successfully, Your Order are :\n" +
                    ORder, null, null);
            Toast.makeText(MainActivity.this, "SMS Sent Successfully, Your Order are :\n" +
                    ORder, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.SEND_SMS},PERMISSIONS_REQUEST_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        this.requestCode = requestCode;
        this.permissions = permissions;
        this.grantResults = grantResults;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            }

        }
    }

}