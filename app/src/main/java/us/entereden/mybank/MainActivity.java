package us.entereden.mybank;


import android.app.DialogFragment;
import android.content.Context;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;


public class MainActivity extends ActionBarActivity {
    public interface OnMyDialogClick {
        public abstract void onPositiveButtonClick();

        public abstract void onNegativeButtonClick();
    }

    private static final String TAG = "MainActivity";

    private EditText mAmountInput;
    private Button mWithdrawButton;
    private Button mDepositButton;
    private TextView mAmountDisplay;
    private BankAccount mCurrentAccount;



    private static final String PROPERTY_ID = "UA-58450054-1";
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
    synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {

            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.ecommerce_tracker);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Overdraft fee is " + BankAccount.OVERDRAFT_FEE);

        mCurrentAccount = new BankAccount();
        mAmountDisplay = (TextView) findViewById(R.id.balance_display);
        mAmountDisplay.setText("Welcome to our Bank! Please deposit or withdraw.");
        mWithdrawButton = (Button) findViewById(R.id.withdraw_button);
        mDepositButton = (Button) findViewById(R.id.deposit_button);
        mAmountInput = (EditText) findViewById(R.id.amount_input);



        HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

        mDepositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = mAmountInput.getText().toString();
                if (amount.equals("")){
                    amount = "0";
                } mCurrentAccount.deposit(Double.parseDouble(amount));
                mAmountDisplay.setText("Balance is " + mCurrentAccount.getBalance());
                mAmountInput.setText("");
            }
        });

        mWithdrawButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String amount2=mAmountInput.getText().toString();
    if (amount2.equals("")){
    amount2 = "0";
    }
                int result = Integer.parseInt(amount2);

                if (mCurrentAccount.getBalance() < result) {

                    DialogFragment Warning = new AlertBox(new OnMyDialogClick() {
                        public void onPositiveButtonClick() {
                            String amount = mAmountInput.getText().toString();
                            if (amount.equals("")){
                                amount = "0";
                            }
                            mCurrentAccount.withdraw(Double.parseDouble(amount));
                            mCurrentAccount.withdraw(BankAccount.OVERDRAFT_FEE);
                            mAmountDisplay.setText("Balance is " + mCurrentAccount.getBalance() + ", heathen.");
                        }

                        public void onNegativeButtonClick() {
                            mAmountInput.setText("");
                        }
                    });
                    Warning.show(getFragmentManager(), "Warning");


                } else {
                    String amount = mAmountInput.getText().toString();
                    if (amount.equals("")){
                        amount = "0";
                    }
                    mCurrentAccount.withdraw(Double.parseDouble(amount));
                    mAmountDisplay.setText("Balance is " + mCurrentAccount.getBalance());

                }
                mAmountInput.setText("");

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
        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mAmountInput.getWindowToken(),0);
        setContentView(R.layout.about);
        Button myKansasReturn = (Button) findViewById(R.id.kansasreturn);
        myKansasReturn.setOnClickListener(new View.OnClickListener() {
            //      @Override
            public void onClick(View v) {

                setContentView(R.layout.activity_main);
                Log.d(TAG, "Overdraft fee is " + BankAccount.OVERDRAFT_FEE);

                mCurrentAccount = new BankAccount();
                mAmountDisplay = (TextView) findViewById(R.id.balance_display);
                mAmountDisplay.setText("Welcome to our Bank! Please deposit or withdraw.");
                mWithdrawButton = (Button) findViewById(R.id.withdraw_button);
                mDepositButton = (Button) findViewById(R.id.deposit_button);
                mAmountInput = (EditText) findViewById(R.id.amount_input);


                mDepositButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String amount = mAmountInput.getText().toString();
                        if (amount.equals("")){
                            amount = "0";
                        }
                        mCurrentAccount.deposit(Double.parseDouble(amount));
                        mAmountDisplay.setText("Balance is " + mCurrentAccount.getBalance());
                        mAmountInput.setText("");
                    }
                });

                mWithdrawButton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        String amount2=mAmountInput.getText().toString();
                        if (amount2.equals("")){
                            amount2 = "0";
                        }
                        int result = Integer.parseInt(amount2);

                        if (mCurrentAccount.getBalance() < result) {

                            DialogFragment Warning = new AlertBox(new OnMyDialogClick() {
                                public void onPositiveButtonClick() {
                                    String amount = mAmountInput.getText().toString();
                                    if (amount.equals("")){
                                        amount = "0";
                                    }
                                    mCurrentAccount.withdraw(Double.parseDouble(amount));
                                    mCurrentAccount.withdraw(BankAccount.OVERDRAFT_FEE);
                                    mAmountDisplay.setText("Balance is " + mCurrentAccount.getBalance() + ", heathen.");
                                }

                                public void onNegativeButtonClick() {
                                    mAmountInput.setText("");
                                }
                            });
                            Warning.show(getFragmentManager(), "Warning");


                        } else {
                            String amount = mAmountInput.getText().toString();
                            if (amount.equals("")){
                                amount = "0";
                            }
                            mCurrentAccount.withdraw(Double.parseDouble(amount));
                            mAmountDisplay.setText("Balance is " + mCurrentAccount.getBalance());

                        }
                        mAmountInput.setText("");
                    }
                });
          }
        });

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
