package com.infolinkstechnology.workmanagerexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TITLE = "title";
    public static final String DECS = "desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data data = new Data.Builder()
                .putString(MainActivity.TITLE, "Main Activity Title")
                .putString(MainActivity.DECS, "hi, I'm sending to worker thread data")
                .build();


        final OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWork.class)
                .setInputData(data)
                .build();

//        final PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(MyWork.class,5,
//                TimeUnit.SECONDS, 50, TimeUnit.SECONDS).build();


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance(MainActivity.this).enqueue(request);

            }
        });

        final TextView displayText= findViewById(R.id.display);

        WorkManager.getInstance(MainActivity.this).getWorkInfoByIdLiveData(request.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null){

                            if (workInfo.getState().isFinished()){
                                Data data1 = workInfo.getOutputData();
                                String finished = data1.getString(MyWork.finshed);
                                displayText.append(finished +"\n");
                            }

                            displayText.append(workInfo.getState().name() +"\n");
                        }
                    }
                });
    }

}
