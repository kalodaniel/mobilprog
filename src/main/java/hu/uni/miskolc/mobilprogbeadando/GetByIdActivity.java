package hu.uni.miskolc.mobilprogbeadando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import hu.uni.miskolc.mobilprogbeadando.service.EmployeeDTO;
import hu.uni.miskolc.mobilprogbeadando.service.EmployeeHTTPService;
import hu.uni.miskolc.mobilprogbeadando.ui.EmployeeViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetByIdActivity extends AppCompatActivity {

    private String TAG = "GET_ID_BY_ACTIVITY";

    private EditText ID;
    private Button searchById;
    private EmployeeDTO employeeDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_by_id);

        ID = findViewById(R.id.textViewID);
        searchById = findViewById(R.id.searchByID);

    }

    @Override
    protected void onStart() {
        super.onStart();

        LinearLayout layout = findViewById(R.id.layout);

        TextView nameText = new TextView(this);
        nameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        nameText.setGravity(Gravity.CENTER);
        layout.addView(nameText);

        TextView salaryText = new TextView(this);
        salaryText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        salaryText.setGravity(Gravity.CENTER);
        layout.addView(salaryText);

        TextView ageText = new TextView(this);
        ageText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        ageText.setGravity(Gravity.CENTER);
        layout.addView(ageText);


        searchById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeViewModel viewModel = new EmployeeViewModel();
                if(isInt(ID)){
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://my-json-server.typicode.com/kalodaniel/dummyJSON/")
                            .addConverterFactory(GsonConverterFactory.create()).build();

                    EmployeeHTTPService employeeService = retrofit.create(EmployeeHTTPService.class);

                    Call<EmployeeDTO> employee = employeeService.getEmployeeById(Integer.parseInt(ID.getText().toString()));

                    employee.enqueue(new Callback<EmployeeDTO>() {
                        @Override
                        public void onResponse(Call<EmployeeDTO> call, Response<EmployeeDTO> response) {
                            if(response.body() == null){
                                Log.d(TAG, "There is no employee with this ID");
                            }else {
                                employeeDTO = response.body();
                                nameText.setText(employeeDTO.getName());
                                salaryText.setText(employeeDTO.getSalary());
                                ageText.setText(employeeDTO.getAge());
                                Log.d(TAG,"Name: " + employeeDTO.getName());
                            }
                        }

                        @Override
                        public void onFailure(Call<EmployeeDTO> call, Throwable t) {
                            Log.d(TAG,"Somethind went wrong");
                        }
                    });
                }else {
                    Log.d(TAG, "You should write a number");
                }
            }
        });
    }

    public boolean isInt(EditText text){
        String str = text.getText().toString();
        try {
            int result = Integer.parseInt(str);
        }catch (NumberFormatException nfe){
            return false;
        }
        return true;
    }
}