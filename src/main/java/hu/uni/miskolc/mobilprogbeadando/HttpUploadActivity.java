package hu.uni.miskolc.mobilprogbeadando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import hu.uni.miskolc.mobilprogbeadando.model.Employee;
import hu.uni.miskolc.mobilprogbeadando.service.EmployeeDTO;
import hu.uni.miskolc.mobilprogbeadando.ui.EmployeeViewModel;

public class HttpUploadActivity extends AppCompatActivity {

    private EditText name;
    private EditText salary;
    private EditText age;
    private int ID;
    private Button httpUploadBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_upload);

        name = findViewById(R.id.httpUploadName);
        salary = findViewById(R.id.httpUploadSalary);
        age = findViewById(R.id.httpUploadAge);
        httpUploadBtn = findViewById(R.id.httpUploadButton);
        ID = 17;
        httpUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeViewModel viewModel = new EmployeeViewModel();
                EmployeeDTO employee = new EmployeeDTO(ID,name.getText().toString(),salary.getText().toString(),age.getText().toString());
                ViewGroup fields = findViewById(R.id.datas);
                if(fieldsFilled(fields)){
                    viewModel.createEmployee(employee);
                }
            }
        });
    }

    private boolean fieldsFilled(ViewGroup layout) {
        boolean result = true;
        int count = layout.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                if (!fieldsFilled((ViewGroup) child)) {
                    result = false;
                }
            } else if (child instanceof EditText) {
                EditText editText = (EditText) child;
                if (editText.getText().toString().trim().isEmpty()) {
                    result = false;
                    editText.setError("Kötelező");
                }
            }
        }
        return result;
    }
}