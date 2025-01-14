package com.example.calculator_;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private Double first, second;
    private String operation;
    private TextView textView;
    private Boolean isOperationClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textView = findViewById(R.id.text_view);
        isOperationClick = false;
    }

    public void onNumberClick(View view) {
        String text = ((MaterialButton) view).getText().toString();
        String currentText = textView.getText().toString();

        if (text.equals("AC")) {
            textView.setText("0");
            first = null;
            second = null;
            operation = null;
        } else if (currentText.equals("0") || isOperationClick) {
            textView.setText(text);
        }
        else {
            textView.append(text);
        }

        isOperationClick = false;
    }

    public void Operation_click(View view) {
        String currentText = textView.getText().toString();

        if (view.getId() == R.id.plus) {
            first = Double.valueOf(currentText);
            operation = "+";
        } else if (view.getId() == R.id.minus) {
            first = Double.valueOf(currentText);
            operation = "-";
        } else if (view.getId() == R.id.umnojenie) {
            first = Double.valueOf(currentText);
            operation = "×";
        } else if (view.getId() == R.id.delenie) {
            first = Double.valueOf(currentText);
            operation = "/";
        } else if (view.getId() == R.id.procent) {
            // Исправленная обработка процента
            if (!currentText.equals("0")) {
                double value = Double.parseDouble(currentText) / 100;
                textView.setText(formatResult(value));
                isOperationClick = true;
            }
            return;
        } else if (view.getId() == R.id.minus_plus) {
            if (!currentText.equals("0")) {
                if (currentText.startsWith("-")) {
                    textView.setText(currentText.substring(1));
                } else {
                    textView.setText("-" + currentText);
                }
            }
        }
        else if (view.getId() == R.id.ravno) {
            if (first == null || operation == null) {
                return;
            }
            second = Double.valueOf(currentText);
            Double result = calculate(first, second, operation);
            textView.setText(formatResult(result));
            first = result;  // Сохранение результата для дальнейших операций
        }
        isOperationClick = true;
    }

    private Double calculate(Double first, Double second, String operation) {
        switch (operation) {
            case "+":
                return first + second;
            case "-":
                return (first - second);
            case "×":
                return first * second;
            case "/":
                if (second == 0) {
                    return 0.0;
                }
                return first / second;
            default:
                return 0.0;
        }
    }

    private String formatResult(Double result) {
        if (result == Math.floor(result)) {
            return String.valueOf(result.intValue());
        } else {
            return result.toString();
        }
    }
}
