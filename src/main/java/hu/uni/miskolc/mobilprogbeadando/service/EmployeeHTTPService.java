package hu.uni.miskolc.mobilprogbeadando.service;

import java.util.List;

import hu.uni.miskolc.mobilprogbeadando.model.Employee;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EmployeeHTTPService {
    @GET("employee")
    Call<List<EmployeeDTO>> getAllEmployee();

    @GET("employee/{id}")
    Call<EmployeeDTO> getEmployeeById(@Path("id") int id);

    @POST("employee")
    Call<EmployeeDTO> createEmployee(@Body EmployeeDTO employee);
}
