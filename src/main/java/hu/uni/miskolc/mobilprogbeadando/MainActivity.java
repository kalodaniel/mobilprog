package hu.uni.miskolc.mobilprogbeadando;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;;import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import hu.uni.miskolc.mobilprogbeadando.model.Employee;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;
    private ImageView imageView;
    private Button camButton;
    private Button openButton;
    private Button saveToDBButton;
    private Button listData;
    private Button saveToFileButton;
    private Button openWeb;
    private Button httpReqButton;

    private EditText name;
    private EditText salary;
    private EditText age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        camButton = findViewById(R.id.cam);
        openButton = findViewById(R.id.open);
        saveToDBButton = findViewById(R.id.storeDB);
        listData = findViewById(R.id.listData);
        saveToFileButton = findViewById(R.id.storeFile);
        openWeb = findViewById(R.id.openWeb);
        httpReqButton = findViewById(R.id.httpReq);

        name = findViewById(R.id.name);
        salary = findViewById(R.id.salary);
        age = findViewById(R.id.age);

        mDatabaseHelper = new DatabaseHelper(this);

        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},101);
        }

        camButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 101);
            }
        });

        saveToDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup fields = findViewById(R.id.data);
                if(fieldsFilled(fields)){
                    Employee employee = new Employee(EditTextToString(name),EditTextToString(salary),EditTextToString(age));
                    addData(employee);
                    name.setText("");
                    salary.setText("");
                    age.setText("");
                }
            }
        });

        listData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });

        saveToFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewGroup fields = findViewById(R.id.data);
                if(fieldsFilled(fields)){
                    Employee employee = new Employee(EditTextToString(name),EditTextToString(salary),EditTextToString(age));
                    String content = employee.getName()+", "+employee.getSalary()+", "+employee.getAge();
                    writeToFile("employee.txt", content);
                }
            }
        });

        openWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                startActivity(intent);
            }
        });

        httpReqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmployeeHttpListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        openButton.setOnClickListener(view -> {
            ViewGroup fields = findViewById(R.id.data);
            if(fieldsFilled(fields)){
                Employee employee = new Employee(EditTextToString(name),EditTextToString(salary),EditTextToString(age));

                Intent intent = new Intent(MainActivity.this, OpenEmployeeActivity.class);

                intent.putExtra("employee", employee);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }
    }

    public void addData(Employee employee){
        boolean insertData = mDatabaseHelper.addData(employee);

        if(insertData){
            System.out.println("Siker");
        }else {
            System.out.println("Hiba");
        }
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

    private String EditTextToString(EditText text){
        return text.getText().toString();
    }

    private void writeToFile(String fileName, String content){
        File path = getApplicationContext().getExternalFilesDir(null);
        try(
                FileWriter fw = new FileWriter(path+"/"+fileName, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)){
                    out.println(content+"\n");
                }
        catch( IOException e ){
            e.getMessage();
        }
    }
}