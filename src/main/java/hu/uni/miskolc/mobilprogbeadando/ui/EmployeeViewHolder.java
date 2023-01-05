package hu.uni.miskolc.mobilprogbeadando.ui;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import hu.uni.miskolc.mobilprogbeadando.R;

public class EmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    protected TextView id;
    protected TextView name;
    protected TextView salary;
    protected TextView age;
    protected EmployeeChooseListener listener;
    protected ImageButton lookBtn;
    protected ImageButton deleteBtn;

    public EmployeeViewHolder(@NonNull View itemView, EmployeeChooseListener listener) {
        super(itemView);
        this.id = itemView.findViewById(R.id.idRow);
        this.name = itemView.findViewById(R.id.nameRow);
        this.salary = itemView.findViewById(R.id.salaryRow);
        this.age = itemView.findViewById(R.id.ageRow);
        this.lookBtn = itemView.findViewById(R.id.lookBtn);
        this.deleteBtn = itemView.findViewById(R.id.deleteBtn);
        this.listener = listener;
        itemView.setOnClickListener(this);
        this.deleteBtn.setOnClickListener(this);
        this.lookBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view == deleteBtn){
            listener.onEmployeeClickDelete(getAdapterPosition(), view);
        } else if(view == lookBtn){
            listener.onEmployeeClick(getAdapterPosition(),view);
        }else {
            if(this.deleteBtn.getVisibility() == View.INVISIBLE){
                this.deleteBtn.setVisibility(View.VISIBLE);
            }else {
                this.deleteBtn.setVisibility(View.INVISIBLE);
            }
        }
    }
}
