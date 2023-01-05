package hu.uni.miskolc.mobilprogbeadando.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.uni.miskolc.mobilprogbeadando.R;
import hu.uni.miskolc.mobilprogbeadando.service.EmployeeDTO;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {

    private List<EmployeeDTO> employees;
    private EmployeeChooseListener listener;

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees = employees;
    }
    public void setListener(EmployeeChooseListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.employee_row, parent,
                false);
        EmployeeViewHolder vh = new EmployeeViewHolder(layout, listener);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        EmployeeDTO employee = employees.get(position);
        holder.id.setText(String.valueOf(employee.getID()));
        holder.name.setText(String.valueOf(employee.getName()));
        holder.salary.setText(String.valueOf(employee.getSalary()));
        holder.age.setText(String.valueOf(employee.getAge()));
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }
}
