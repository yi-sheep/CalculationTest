package com.gaoxianglong.calculationtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private NavController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this, mController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // 判断当前的碎片
        switch (mController.getCurrentDestination().getId()) {
            case R.id.questionFragment:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.quit_dialog_title));
                builder.setPositiveButton(R.string.dialog_positive_message, (dialog, which) -> {
                    mController.navigateUp();
                });
                builder.setNegativeButton(R.string.dialog_negative_message,((dialog, which) ->{}));
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case R.id.loseFragment:
                mController.navigate(R.id.action_loseFragment_to_titleFragment);
                break;
            case R.id.winFragment:
                mController.navigate(R.id.action_winFragment_to_titleFragment);
                break;
            case R.id.titleFragment:
                super.onBackPressed();
                break;
        }
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}
