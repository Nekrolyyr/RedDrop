package ch.versusvirus.reddrop.ui;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ch.versusvirus.reddrop.R;


public class ProfileActivity extends AppCompatActivity {

    private String gender = "";
    public static final String MyPREFERENCES = "MyPrefs";
    private String db = "";
    private String bt = "";
    private String zc = "";
    private String donornumber = "";
    private EditText Zipcode;
    private EditText Donornumber;
    CheckBox terms_checkbox;

    void loadData() { // load from shared preferences
        SharedPreferences sp = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        gender = sp.getString("Gender", ""); //Change this
        db = sp.getString("Birthday", ""); //Change this
        bt = sp.getString("BloodType", "Blood type (optional)"); //Change this
        zc = sp.getString("ZipCode", ""); //Change this
        donornumber = sp.getString("DonorNumber", ""); //Change this
        //Log.d("Editable", "Loaded data: Hints = " + String.valueOf(mHints));
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.other:
                if (checked) {
                    gender = "O";
                }
                break;
            case R.id.male:
                if (checked) {
                    gender = "M";
                }
                break;
            case R.id.female:
                if (checked) {
                    gender = "F";
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.findViewById(R.id.btn_toolbar_home).setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));

        Calendar myCalendar = Calendar.getInstance();

        loadData();

        terms_checkbox = findViewById(R.id.terms);// Terms and Conditions Check box

        // ZIP code
        Zipcode = findViewById(R.id.zipCode);
        Donornumber = findViewById(R.id.donornumber);
        Donornumber.setText(donornumber);

        EditText edittext = findViewById(R.id.dateOfBirth);
        if (!TextUtils.isEmpty(db)) {
            edittext.setText(db);
        }
        if (gender.equals("M")) {
            RadioGroup radioGroup = findViewById(R.id.gender);
            radioGroup.check(R.id.male);
        }
        if (gender.equals("F")) {
            RadioGroup radioGroup = findViewById(R.id.gender);
            radioGroup.check(R.id.female);
        }
        if (gender.equals("O")) {
            RadioGroup radioGroup = findViewById(R.id.gender);
            radioGroup.check(R.id.other);
        }

        Log.d("Editable", "Value zc " + zc);
        if (!TextUtils.isEmpty(zc)) {
            Log.d("Editable", "Value zc inside " + zc);
            int zc_temp = Integer.parseInt(zc);
            Zipcode.setText(zc);
        }

        // Blood type
        String[] items = new String[]{"Blood type (optional)", "0+", "0-", "A+", "A-", "AB+", "AB-", "B+", "B-"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        Spinner dynamicSpinner = findViewById(R.id.bloodType);

        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.select_state, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dynamicSpinner.setAdapter(adapter);
        Log.d("Editable", "Value before" + bt);
        if (bt != null) {
            Log.d("Editable", "Value " + bt);
            int spinnerPosition = adapter.getPosition(bt);
            Log.d("Editable", "Value " + spinnerPosition);
            dynamicSpinner.setSelection(spinnerPosition);
        }
        //set the spinners adapter to the previously created one.
        //dynamicSpinner.setAdapter(adapter);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittext.setText(sdf.format(myCalendar.getTime()));
                //updateLabel();
            }
        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

                DatePickerDialog dialog = new DatePickerDialog(v.getContext(), date,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });


        RadioGroup radioGroup = findViewById(R.id.gender);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            View radioButton = radioGroup.findViewById(checkedId);
            Log.d("Editable", "Radio " + checkedId);
            int index = radioGroup.indexOfChild(radioButton);
            Log.d("Editable", "Radio " + index);
            switch (index) {
                case 0:
                    gender = "M";
                    break;
                case 1:
                    gender = "F";
                    break;
                case 2:
                    gender = "O";
                    break;
            }
        });

        findViewById(R.id.btn_saveLife).setOnClickListener(v -> {
            //check if madatory fields are filled
            if(!terms_checkbox.isChecked()){
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Please accept terms and conditions");
                AlertDialog alert = builder.create();
                alert.show();
                alert.getWindow().setLayout(1000, 250);
            } else if (!TextUtils.isEmpty(edittext.getText().toString()) && (gender.equals("M") || gender.equals("F") || gender.equals("O"))) {
                SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("Gender", gender);
                editor.putString("Birthday", edittext.getText().toString());

                Spinner mySpinner = findViewById(R.id.bloodType);
                String blood_type = mySpinner.getSelectedItem().toString();
                editor.putString("BloodType", blood_type);

                String zip = (Zipcode.getText().toString());
                editor.putString("ZipCode", zip);

                if (Donornumber.getText().toString().length() > 0) {
                    editor.putString("DonorNumber", Donornumber.getText().toString());
                }

                editor.apply();

                startActivity(new Intent(this, HomeActivity.class));
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Please insert the mandatory fields");
                AlertDialog alert = builder.create();
                alert.show();
                alert.getWindow().setLayout(1000, 250);

            }

        });
    }

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
        switch (id) {
            case R.id.action_donor_card:
                startActivity(new Intent(this, MyCardActivity.class));
                break;
            case R.id.action_notifications:
                startActivity(new Intent(this, NotificationsActivity.class));
                break;
            case R.id.action_share:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}