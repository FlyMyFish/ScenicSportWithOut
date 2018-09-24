package com.shichen.scenicsport.sports;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.shichen.scenicsport.Injection;
import com.shichen.scenicsport.R;
import com.shichen.scenicsport.data.source.SportsRepository;
import com.shichen.scenicsport.data.source.local.ScenicSportDatabase;
import com.shichen.scenicsport.data.source.local.SportsLocalDataSource;
import com.shichen.scenicsport.data.source.remote.SportsRemoteDataSource;
import com.shichen.scenicsport.util.ActivityUtils;
import com.shichen.scenicsport.util.AppExecutors;

public class SportActivity extends AppCompatActivity {
    SportConstract.Presenter sportPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SportFragment sportFragment =
                (SportFragment) getSupportFragmentManager().findFragmentById(R.id.frame_content);
        if (sportFragment == null) {
            sportFragment = SportFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), sportFragment, R.id.frame_content);
        }

        sportPresenter = new SportPresenter(Injection.provideTasksRepository(getApplicationContext()), sportFragment);
    }
}
