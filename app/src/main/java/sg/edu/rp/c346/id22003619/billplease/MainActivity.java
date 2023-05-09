package sg.edu.rp.c346.id22003619.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
EditText etn;
EditText etpax;
ToggleButton svs;
ToggleButton gst;
RadioGroup rgpayment;
RadioButton rbcash;
RadioButton rbpaynow;
Button btnsplit;
Button btnreset;
TextView total;
TextView each;
Switch discount;
LinearLayout dropdown;
LinearLayout paynow;
EditText payNowNumber;
EditText discAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etn=findViewById(R.id.editTextNumber);
        etpax=findViewById(R.id.editTextPax);
        svs=findViewById(R.id.svs);
        gst=findViewById(R.id.gst);
        rgpayment=findViewById(R.id.radioGroupPayment);
        rbcash=findViewById(R.id.radioButtonCash);
        rbpaynow=findViewById(R.id.radioButtonPayNow);
        btnsplit=findViewById(R.id.buttonSplit);
        btnreset=findViewById(R.id.buttonReset);
        total=findViewById(R.id.totalBill);
        each=findViewById(R.id.eachPays);
        discount=findViewById(R.id.discountSwitch);
        dropdown=findViewById(R.id.dropdownLayout);
        paynow=findViewById(R.id.payNowLayout);
        payNowNumber=findViewById(R.id.editTextPaynow);
        discAmt=findViewById(R.id.editTextDiscount);

        btnsplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String etnString=etn.getText().toString();
                String etpaxString=etpax.getText().toString();

                if (TextUtils.isEmpty(etnString)){
                    etn.setError("The amount cannot be empty.");
                    return;
                }
                if (TextUtils.isEmpty(etpaxString)){
                    etpax.setError("The number of pax cannot be empty.");
                    return;
                }
                String amtString=etn.getText().toString();
                double amtInput=Double.parseDouble(amtString);
                String paxString=etpax.getText().toString();
                int paxInput=Integer.parseInt(paxString);
                double totalAmt=0.0;

                if(svs.isChecked()&& gst.isChecked()){
                    totalAmt+=amtInput * 1.1 * 1.08;
                }else if(svs.isChecked()&& !gst.isChecked()){
                    totalAmt+=amtInput * 1.1 ;
                }else if(!svs.isChecked()&& gst.isChecked()){
                    totalAmt+=amtInput * 1.08;
                }else if(!svs.isChecked()&& !gst.isChecked()){
                    totalAmt+=amtInput ;
                }

                String discString=discAmt.getText().toString();
                int discINT= Integer.parseInt(discString);
                if(discAmt.getText().toString().trim().length() != 0){
                    totalAmt *= 1-discINT/100;
                }
                String totalFinal=String.format("%.2f",totalAmt);
                double splitAmt=totalAmt/paxInput;

                String splitFinal=String.format("%.2f",splitAmt);
                String totalDisplay="Total Bill: $"+totalFinal;
                String eachDisplay ="";
                String payNowString=payNowNumber.getText().toString();
                if(rbpaynow.isChecked()){
                    eachDisplay="Each Pays: $"+splitFinal+" to PayNow number "+payNowString;
                }else {
                    eachDisplay = "Each Pays: $" + splitFinal;
                }
                total.setText(totalDisplay);
                each.setText(eachDisplay);

            }
        });

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etn.setText("");
                etpax.setText("");
                svs.setChecked(false);
                gst.setChecked(false);
                discount.setText("");
                rbcash.setChecked(true);
            }
        });
        discount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (discount.isChecked()) {
                    dropdown.setVisibility(View.VISIBLE);
                } else {
                    dropdown.setVisibility(View.GONE);
                }
            }
        });
        rbpaynow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(rbpaynow.isChecked()){
                    paynow.setVisibility(View.VISIBLE);
                } else if(rbcash.isChecked()){
                    paynow.setVisibility(View.GONE);
                }
            }
        });

    }
}