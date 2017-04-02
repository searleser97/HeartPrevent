package com.wakeupinc.hpandroid;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class AddMedica extends AppCompatActivity{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private bajaMedicamentos bajamedi=null;
    // UI references.
    static final int DATE_DIALOG_ID = 0;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private Button mDateDisplay;
    private Button mDateDisplay2;
    int mYear;
    int mMonth;
    int mDay;

    int mYear1;
    int mMonth1;
    int mDay1;

    private int hour;
    private int minute;

    private int hour2;
    private int minute2;

    private Spinner spinner;
    private Button horaini;
    private Button horafin;

    private String fechainifull;
    private String fechafinfull;

    public String imgurl;
    public String correo;
    public String contra;
    public String nombre;
    public String nombreusr;
    public int longitudname=0;
    public String imgusr;

    String medicamento;
    private String periodo;
    private EditText periodview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medica);

        Bundle recibe = new Bundle();
        recibe = this.getIntent().getExtras();
        correo=recibe.getString("mail");
        contra=recibe.getString("contra");
        imgusr=recibe.getString("imgusr");
        nombreusr=recibe.getString("nombreusr");



       bajamedi=new bajaMedicamentos();
        bajamedi.execute((Void)null);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuthTask = new UserLoginTask();
                mAuthTask.execute((Void) null);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        spinner = (Spinner) findViewById(R.id.spinner);
        mDateDisplay = (Button) findViewById(R.id.fechini);
        mDateDisplay2 = (Button) findViewById(R.id.fechfin);
        horaini = (Button) findViewById(R.id.horaini);
        horafin = (Button) findViewById(R.id.horafin);
        final Calendar c = Calendar.getInstance();
        mYear1 = mYear = c.get(Calendar.YEAR);
        mMonth1 = mMonth = c.get(Calendar.MONTH);
        mDay1 = mDay = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        periodview=(EditText) findViewById(R.id.password);

    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }



    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {


            UserLoginTask(){
                periodo=periodview.getText().toString();
            }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                WebService.altamedicamentos(correo, contra, medicamento, fechainifull, fechafinfull,periodo, "inmedicamentos");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            finish();
        }

    }



    public class bajaMedicamentos extends AsyncTask<Void, Void, String[]> {


        bajaMedicamentos() {

        }

        @Override
        protected String[] doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String [] medicaArray=null;
            try {
                medicaArray=WebService.bajalistamedica();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            // TODO: register the new account here.
            return medicaArray;
        }

        @Override
        protected void onPostExecute(final String[] medica) {


// Application of the Array to the Spinner
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_item, medica);
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item); // The drop down view
            spinner.setAdapter(spinnerArrayAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                   medicamento = spinner.getSelectedItem().toString();
                    Toast.makeText(getApplicationContext(), medicamento,
                            Toast.LENGTH_LONG).show();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }






    ///////////////////////////////////
    ///////////////
    //////////////////////////

    public void fechini(View view){
        showDialog(DATE_DIALOG_ID);
    }

    public void fechfin(View view){
        showDialog(1);
    }
    public void horaini(View view){
        showDialog(2);
    }
    public void horafin(View view){
        showDialog(3);
    }



    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;

                    fechainifull=mYear+"-"+mMonth+"-"+mDay;
                    updateDisplay();
                }
            };

    private DatePickerDialog.OnDateSetListener mDateSetListener2 =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mYear1 = year;
                    mMonth1 = monthOfYear;
                    mDay1 = dayOfMonth;
                    fechafinfull=mYear1+"-"+mMonth1+"-"+mDay1;
                    updateDisplay2();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);
            case 1:
                return new DatePickerDialog(this,
                        mDateSetListener2,
                        mYear1, mMonth1, mDay1);
            case 2:
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,false);
            case 3:
                return new TimePickerDialog(this,
                        timePickerListener2, hour2, minute2,false);
        }
        return null;
    }

    private void updateDisplay() {
        mDateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
    }

    private void updateDisplay2() {
        mDateDisplay2.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(mMonth1 + 1).append("-")
                        .append(mDay1).append("-")
                        .append(mYear1).append(" "));
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;
                    fechainifull+=" "+hour+":"+minute+":00";
                    // set current time into textview
                    horaini.setText(new StringBuilder().append(pad(hour))
                            .append(":").append(pad(minute)));

                }
            };
    private TimePickerDialog.OnTimeSetListener timePickerListener2 =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour2 = selectedHour;
                    minute2 = selectedMinute;
                    fechafinfull+=" "+hour2+":"+minute2+":00";
                    // set current time into textview
                    horafin.setText(new StringBuilder().append(pad(hour2))
                            .append(":").append(pad(minute2)));

                }
            };
}

