package hu.uni.miskolc.mobilprogbeadando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import hu.uni.miskolc.mobilprogbeadando.databinding.ActivityEmployeeDetailsBinding;
import hu.uni.miskolc.mobilprogbeadando.service.EmployeeDTO;


public class EmployeeDetailsActivity extends AppCompatActivity {

    private ActivityEmployeeDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EmployeeDTO employee = (EmployeeDTO) getIntent().getSerializableExtra("employee");
        if(employee != null){
            binding.idCell.setText(String.valueOf(employee.getID()));
            binding.nameCell.setText(String.valueOf(employee.getName()));
            binding.salaryCell.setText(String.valueOf(employee.getSalary()));
            binding.ageCell.setText(String.valueOf(employee.getAge()));
        }
    }
}