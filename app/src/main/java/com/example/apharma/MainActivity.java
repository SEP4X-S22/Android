package com.example.apharma;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.apharma.adapters.SensorAdapter;
//import com.example.apharma.notifications.NotificationJobService;
import com.example.apharma.ui.signIn.SignInActivity;
import com.example.apharma.viewmodels.MainActivityViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.apharma.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    NavController navController;
    AppBarConfiguration appBarConfiguration;
//    public SensorAdapter sensorAdapter = SensorAdapter.getInstance();
    private static final int JOB_ID = 0;
    private int refreshInterval = 5 * 60 * 1000;
    private JobScheduler mScheduler;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        checkIfSignedIn();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//
//        ComponentName serviceName = new ComponentName(getPackageName(),
//                NotificationJobService.class.getName());
//        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
//                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//        JobInfo myJobInfo = builder.build();
//        mScheduler.schedule(myJobInfo);


//        sensorAdapter.isConditionsSurpassConstraints().observe(this, condition ->{
//            if (condition){
//                mScheduler.schedule(myJobInfo);
//            }
//        });





        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_profile)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                Log.v("Logging", "HELLO!!!" + viewModel.getCurrentUser().getValue().getEmail());
            } else
                startLoginActivity();
        });
    }

    private void startLoginActivity() {
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

}