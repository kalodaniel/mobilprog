package hu.uni.miskolc.mobilprogbeadando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import hu.uni.miskolc.mobilprogbeadando.model.Employee;

public class OpenEmployeeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_employee);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();

        LinearLayout layout = findViewById(R.id.data);
        Employee employee = (Employee) intent.getSerializableExtra("employee");
        layout.removeAllViews();
        TextView name = new TextView(this);
        name.setText(employee.getName());
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        name.setGravity(Gravity.CENTER);
        layout.addView(name);

        TextView salary = new TextView(this);
        salary.setText(employee.getSalary());
        salary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        salary.setGravity(Gravity.CENTER);
        layout.addView(salary);

        TextView age = new TextView(this);
        age.setText(employee.getAge());
        age.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        age.setGravity(Gravity.CENTER);
        layout.addView(age);

    }
}
