package com.naqib.foodordering2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    String FOod, ADDon, ORder;
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
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("+60104220085", null, "SMS Sent Successfully, Your Order are :\n" +
                    ORder, null, null);
            Toast.makeText(MainActivity.this, "SMS Sent Successfully, Your Order are :\n" +
                    ORder, Toast.LENGTH_SHORT).show();
            MainMenu = 0.00;
            AddOn = 0.00;
            ADDon = "";
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
        });
    }
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    public  void harga(){
        FOOD = food.getCheckedRadioButtonId();
        Food = findViewById(FOOD);
        FOod = Food.getText().toString();
        MainMenu = Double.parseDouble(FOod.split(" ")[3]);
        if (mushroomsoup.isChecked()){
            AddOn += 1.00;
            ADDon += ", "+mushroomsoup.getText().toString();
        }
        if (breadsticks.isChecked()){
            AddOn += 1.00;
            ADDon += ", "+breadsticks.getText().toString();
        }
        if (croutons.isChecked()){
            AddOn += 1.00;
            ADDon += ", "+croutons.getText().toString();
        }
        Subtotal = MainMenu + AddOn;
        Tax = 11 * Subtotal / 100;
        OrderTotal = Subtotal + Tax;

        subtotal.setText("RM "+(String.format("%.2f", Subtotal)));
        tax.setText("RM "+(String.format("%.2f", Tax)));
        ordertotal.setText("RM "+(String.format("%.2f", OrderTotal)));
    }
}