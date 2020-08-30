package com.example.calculationcompetition;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

/**
 * @Author: Jack Ou
 * @CreateDate: 2020/8/30 10:53
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/8/30 10:53
 * @UpdateRemark: 更新说明
 */
public class MyViewModel extends AndroidViewModel {

    private SavedStateHandle handle;
    private static String KEY_HIGH_SCORE = "key_high_score";
    private static String KEY_CURRENT_SCORE = "key_current_score";
    private static String KEY_LEFT_NUMBER = "key_left_number";
    private static String KEY_RIGHT_NUMBER = "key_right_number";
    private static String KEY_OPERATOR = "key_operator";
    private static String KEY_RESULT = "key_result";
    private static String SHP_NAME = "shp_cal";
    private boolean flag = false;

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGH_SCORE)) {
            SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHP_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE, sharedPreferences.getInt(KEY_HIGH_SCORE, 0));
        }
        handle.set(KEY_CURRENT_SCORE, 0);
        handle.set(KEY_LEFT_NUMBER, 0);
        handle.set(KEY_RIGHT_NUMBER, 0);
        handle.set(KEY_OPERATOR, "+");
        handle.set(KEY_RESULT, 0);
        this.handle = handle;
    }

    public MutableLiveData<Integer> getCurrentScore() {
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }

    public MutableLiveData<Integer> getHighScore() {
        return handle.getLiveData(KEY_HIGH_SCORE);
    }

    public MutableLiveData<Integer> getLeftNumber() {
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }

    public MutableLiveData<Integer> getRightNumber() {
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }

    public MutableLiveData<String> getOperator() {
        return handle.getLiveData(KEY_OPERATOR);
    }

    public MutableLiveData<Integer> getResult() {
        return handle.getLiveData(KEY_RESULT);
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void generator(int level) {
        Random random = new Random();
        int x, y;
        x = random.nextInt(level) + 1;
        y = random.nextInt(level) + 1;
        if (x % 2 == 0) {
            getOperator().setValue("+");
            if (x >= y) {
                getResult().setValue(x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x - y);
            } else {
                getResult().setValue(y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y - x);
            }
        } else {
            getOperator().setValue("-");
            if (x >= y) {
                getResult().setValue(x - y);
                getLeftNumber().setValue(x);
                getRightNumber().setValue(y);
            } else {
                getResult().setValue(y - x);
                getLeftNumber().setValue(y);
                getRightNumber().setValue(x);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void save() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(SHP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_HIGH_SCORE, getHighScore().getValue());
        editor.apply();
    }

    @SuppressWarnings("ConstantConditions")
    public void resultCorrect() {
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHighScore().getValue()) {
            getHighScore().setValue(getCurrentScore().getValue());
            flag = true;
        }
        generator(20);
    }

    public void reset() {
        handle.set(KEY_CURRENT_SCORE, 0);
    }
}

