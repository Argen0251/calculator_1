package com.example.calculator_;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.location.GnssStatusCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.content.ContextCompat;

import com.example.calculator_.databinding.ActivityMainBinding;
import com.example.calculator_.databinding.ActivitySecondBinding;
import com.google.android.material.slider.RangeSlider;

import java.util.List;

public class SecondActivity extends AppCompatActivity {
    private ActivitySecondBinding binding;
    String[] categories = {"one", "two", "three", "other"};

    private RangeSlider priceSlider;

    private RangeSlider accessibilitySlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // dropdown до
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteText);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, categories);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = parent.getItemAtPosition(position).toString();
        });

        String result = getIntent().getStringExtra("RESULT");
        binding.textMain.setText(result);

        //кнопка некст баттон для выхода из всех активити

        binding.nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(SecondActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finishAffinity();
        });

        // кнопка лайка изменение при нажатии
        var ref = new Object() {
            boolean isFavorite = false;
        };
        binding.favoriteButton.setOnClickListener(v -> {
            if (ref.isFavorite) {
                binding.favoriteButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
            } else {
                binding.favoriteButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
            }
            ref.isFavorite = !ref.isFavorite;
        });

        RangeSlider slider = findViewById(R.id.price);
        RangeSlider slider2 = findViewById(R.id.accessibility);
        slider.setValues(3000f,6000f);
        slider.setValues(2000f,3500f);
    }


}


