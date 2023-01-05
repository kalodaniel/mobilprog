package hu.uni.miskolc.mobilprogbeadando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import hu.uni.miskolc.mobilprogbeadando.model.Employee;
import hu.uni.miskolc.mobilprogbeadando.service.EmployeeDTO;
import hu.uni.miskolc.mobilprogbeadando.service.EmployeeHTTPService;
import hu.uni.miskolc.mobilprogbeadando.ui.EmployeeAdapter;
import hu.uni.miskolc.mobilprogbeadando.ui.EmployeeChooseListener;
import hu.uni.miskolc.mobilprogbeadando.ui.EmployeeViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeHttpListActivity extends AppCompatActivity {

    private static final String TAG = "EmployeeHttpListAvtivity";

    private RecyclerView recyclerView;
    private Button httpUploadBtn;
    private Button getByIdBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_http_list);
        recyclerView = findViewById(R.id.recyclerView);

        httpUploadBtn = findViewById(R.id.httpUploadBtn);
        getByIdBtn = findViewById(R.id.httpGetById);

        httpUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeHttpListActivity.this, HttpUploadActivity.class);
                startActivity(intent);
            }
        });

        getByIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeHttpListActivity.this, GetByIdActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        EmployeeViewModel vm = new ViewModelProvider(this).get(EmployeeViewModel.class);

        EmployeeAdapter adapter = new EmployeeAdapter();
        adapter.setEmployees(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        vm.getEmployees().observe(this, employees -> {
            adapter.setEmployees(employees);
            adapter.setListener(new EmployeeChooseListener() {
                @Override
                public void onEmployeeClick(int position, View v) {
                    Intent intent = new Intent(
                            EmployeeHttpListActivity.this, EmployeeDetailsActivity.class);
                    EmployeeDTO emplyee = employees.get(position);
                    intent.putExtra("employee", emplyee);
                    startActivity(intent);
                }

                @Override
                public void onEmployeeClickDelete(int position, View v) {
                    vm.deleteEmployee(employees.get(position));
                }
            });
            recyclerView.setAdapter(adapter);
        });
    }
}