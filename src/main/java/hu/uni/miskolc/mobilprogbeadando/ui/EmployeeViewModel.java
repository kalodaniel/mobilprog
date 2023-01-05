package hu.uni.miskolc.mobilprogbeadando.ui;

import android.nfc.Tag;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;
import java.util.List;

import hu.uni.miskolc.mobilprogbeadando.model.Employee;
import hu.uni.miskolc.mobilprogbeadando.service.EmployeeDTO;
import hu.uni.miskolc.mobilprogbeadando.service.EmployeeHTTPService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmployeeViewModel extends ViewModel {

    private static final String TAG = "EmployeeViewModel";

    private MutableLiveData<List<EmployeeDTO>> employees;
    private EmployeeDTO employeeDTO;

    public LiveData<List<EmployeeDTO>> getEmployees(){
        if(employees == null){
            employees = new MutableLiveData<>();
            loadEmployees();
        }
        return employees;
    }

    public EmployeeDTO getEmployeeSearchResult(int id){
        if(employeeDTO == null){
            employeeDTO = new EmployeeDTO();
            getEmployeeByID(id);
        }
        return employeeDTO;
    }

    public void deleteEmployee(EmployeeDTO employeeDTO){
        employees.getValue().remove(employeeDTO);
        employees.postValue(employees.getValue());
    }

    public void loadEmployees(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/kalodaniel/dummyJSON/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        EmployeeHTTPService employeeService = retrofit.create(EmployeeHTTPService.class);

        Call<List<EmployeeDTO>> employeeList = employeeService.getAllEmployee();
        employeeList.enqueue(new Callback<List<EmployeeDTO>>() {
            @Override
            public void onResponse(Call<List<EmployeeDTO>> call, Response<List<EmployeeDTO>> response) {
                employees.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<EmployeeDTO>> call, Throwable t) {
                Log.d(TAG, "Failure");
            }
        });
    }

    public void createEmployee(EmployeeDTO employee){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/kalodaniel/dummyJSON/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        EmployeeHTTPService employeeService = retrofit.create(EmployeeHTTPService.class);

        Call<EmployeeDTO> addEmployee = employeeService.createEmployee(employee);
        addEmployee.enqueue(new Callback<EmployeeDTO>() {
            @Override
            public void onResponse(Call<EmployeeDTO> call, Response<EmployeeDTO> response) {
                if(response.code() == 200 || response.code() == 201){
                    EmployeeDTO postEmployee = response.body();
                    Log.d(TAG, response.code() + "\n" +
                            postEmployee.getID() + "\n" +
                            postEmployee.getName() + "\n" +
                            postEmployee.getSalary() + "\n" +
                            postEmployee.getAge());
                }else if(response.errorBody() != null){
                    try {
                        Log.d(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<EmployeeDTO> call, Throwable t) {

            }
        });
    }



    public void getEmployeeByID(int id){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/kalodaniel/dummyJSON/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        EmployeeHTTPService employeeService = retrofit.create(EmployeeHTTPService.class);

        Call<EmployeeDTO> employee = employeeService.getEmployeeById(id);

        employee.enqueue(new Callback<EmployeeDTO>() {
            @Override
            public void onResponse(Call<EmployeeDTO> call, Response<EmployeeDTO> response) {
                if(response.body() == null){
                    Log.d(TAG, "There is no employee with this ID");
                }else {
                    employeeDTO = response.body();
                    Log.d(TAG,"Name: " + employeeDTO.getName());
                }
            }

            @Override
            public void onFailure(Call<EmployeeDTO> call, Throwable t) {
                Log.d(TAG,"Somethind went wrong");
            }
        });
    }
}
