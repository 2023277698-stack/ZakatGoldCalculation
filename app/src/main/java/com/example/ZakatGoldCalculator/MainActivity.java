package com.example.ZakatGoldCalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etGoldValue;
    RadioGroup radioGroupType;
    RadioButton rbKeep, rbWear;
    TextView totValue, payValue, totZakat;
    Button btnCalculate, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Inputs
        etWeight = findViewById(R.id.weight);
        etGoldValue = findViewById(R.id.goldValue);

        // Radio Buttons
        radioGroupType = findViewById(R.id.radioGroupType);
        rbKeep = findViewById(R.id.keep);
        rbWear = findViewById(R.id.wear);

        // Outputs
        totValue = findViewById(R.id.totValue);
        payValue = findViewById(R.id.payValue);
        totZakat = findViewById(R.id.totZakat);

        // Buttons
        btnCalculate = findViewById(R.id.btnCalculate);
        btnReset = findViewById(R.id.btnReset);

        // Toolbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // Calculate Button
        btnCalculate.setOnClickListener(v -> calculateZakat());

        // Reset Button
        btnReset.setOnClickListener(v -> resetAll());

        // Window Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets sysBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(sysBars.left, sysBars.top, sysBars.right, sysBars.bottom);
            return insets;
        });
    }

    private void calculateZakat() {
        try {
            double weight = Double.parseDouble(etWeight.getText().toString());
            double goldValue = Double.parseDouble(etGoldValue.getText().toString());

            int selectedId = radioGroupType.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Please select Keep or Wear", Toast.LENGTH_SHORT).show();
                return;
            }

            double uruf = (selectedId == R.id.keep) ? 85 : 200;

            double totalValue = weight * goldValue;
            double excessGold = weight - uruf;

            double zakatPayableValue = (excessGold > 0) ? excessGold * goldValue : 0;
            double totalZakatAmount = zakatPayableValue * 0.025;

            totValue.setText("Total Value: RM " + totalValue);
            payValue.setText("Zakat Payable Value: RM " + zakatPayableValue);
            totZakat.setText("Total Zakat Value: RM " + totalZakatAmount);

        } catch (Exception e) {
            Toast.makeText(this, "Please enter valid numbers!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetAll() {
        etWeight.setText("");
        etGoldValue.setText("");
        radioGroupType.clearCheck();

        totValue.setText("Total Value: RM 0.00");
        payValue.setText("Zakat Payable Value: RM 0.00");
        totZakat.setText("Total Zakat Value: RM 0.00");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuAbout) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.menuShare) {
            shareAppLink();
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareAppLink() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String url = "https://github.com/2023277698-stack/ZakatGoldCalculation";
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my Zakat Calculator App: " + url);

        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }
}
