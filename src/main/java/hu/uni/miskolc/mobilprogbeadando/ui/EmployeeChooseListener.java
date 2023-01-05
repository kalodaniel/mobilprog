package hu.uni.miskolc.mobilprogbeadando.ui;

import android.view.View;

public interface EmployeeChooseListener {
    void onEmployeeClick(int position, View v);

    void onEmployeeClickDelete(int position, View v);
}
