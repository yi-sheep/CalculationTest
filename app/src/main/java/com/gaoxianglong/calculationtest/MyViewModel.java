package com.gaoxianglong.calculationtest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import java.util.Random;

@SuppressWarnings("ConstantConditions")
public class MyViewModel extends AndroidViewModel {
    SavedStateHandle mHandle;
    private static final String KEY_HIGH_SCORE = "Key_high_score";
    private static final String KEY_LEFT_NUMBER = "Key_left_number";
    private static final String KEY_RIGHT_NUMBER = "Key_right_number";
    private static final String KEY_OPERATOR = "Key_operator";
    private static final String KEY_ANSWER = "Key_answer";
    private static final String KEY_CURRENT_SCORE = "Key_current_score";
    public static final String KEY_RANGE = "Key_range";
    private static final String SAVE_SHP_DATA_NAME = "save_shp_data_name";
    boolean win_flag = false; // 保存胜负

    public MyViewModel(@NonNull Application application, SavedStateHandle handle) {
        super(application);
        if (!handle.contains(KEY_HIGH_SCORE)) {
            // 判断handle中是否存在包含这个key的值，然后取反，如果没有就执行这里面的
            SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
            // 向handle中设置数据，数据从shp获取出来
            handle.set(KEY_HIGH_SCORE,shp.getInt(KEY_HIGH_SCORE,0));
            // 既然不包含这个key的值那肯定也不包含其他key,这里一起初始化
            handle.set(KEY_LEFT_NUMBER, 0);
            handle.set(KEY_RIGHT_NUMBER, 0);
            handle.set(KEY_OPERATOR, "+");
            handle.set(KEY_ANSWER, 0);
            handle.set(KEY_CURRENT_SCORE,0);
        }
        mHandle = handle;
    }

    /**
     * @return 左数值
     */
    public MutableLiveData<Integer> getLeftNumber() {
        return mHandle.getLiveData(KEY_LEFT_NUMBER);
    }

    /**
     * @return 右数值
     */
    public MutableLiveData<Integer> getRightNumber() {
        return mHandle.getLiveData(KEY_RIGHT_NUMBER);
    }

    /**
     * @return 符号
     */
    public MutableLiveData<String> getOperator() {
        return mHandle.getLiveData(KEY_OPERATOR);
    }

    /**
     * @return 答案
     */
    public MutableLiveData<Integer> getAnswer() {
        return mHandle.getLiveData(KEY_ANSWER);
    }

    /**
     * @return 当前分数
     */
    public MutableLiveData<Integer> getCurrentScore() {
        return mHandle.getLiveData(KEY_CURRENT_SCORE);
    }

    /**
     * @return 最高分数
     */
    public MutableLiveData<Integer> getHighScore() {
        return mHandle.getLiveData(KEY_HIGH_SCORE);
    }

    /**
     * 生成题目
     */
    void generator(){
        int LEVEL = 20; // 定义题目范围
        Random random = new Random(); // 实例化随机对象
        int x,y; // 定义两个数
        x = random.nextInt(LEVEL) + 1; // random.nextInt(LEVEL)生成0到19的随机数
        y = random.nextInt(LEVEL) + 1;
        if (random.nextInt(LEVEL) % 2 == 0) {
            // 再随机一个数如果是偶数就是+否则就是-
            getOperator().setValue("+");
            if (x > y) {
                // 处理结果范围超出题目范围太多，判断如果x>y那么把答案就设定为x
                getAnswer().setValue(x);
                // 然后把左数值设置为x-y
                getLeftNumber().setValue(x - y);
                // 右数值设置为y
                getRightNumber().setValue(y);
            } else {
                getAnswer().setValue(y);
                // 设置左数值为x
                getLeftNumber().setValue(x);
                // 设置右数值为y-x
                getRightNumber().setValue(y - x);
            }
        } else {
            getOperator().setValue("-");
            if (x > y) {
                getAnswer().setValue(y);
                // 左数值设置为x
                getLeftNumber().setValue(x);
                // 右数值设置为y
                getRightNumber().setValue(x - y);
            } else {
                getAnswer().setValue(x);
                // 设置左数值为y
                getLeftNumber().setValue(y);
                // 设置右数值为x
                getRightNumber().setValue(y - x);
            }
        }
    }

    /**
     * 保存最高纪录
     */
    void save() {
        // 获取共享首选项对象
        SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
        // 获取编辑对象
        SharedPreferences.Editor edit = shp.edit();
        // 写入数据
        edit.putInt(KEY_HIGH_SCORE,getHighScore().getValue());
        edit.apply();
    }

    /**
     * 回答正确
     */
    void answerCorrect() {
        // 将当前分数加一
        getCurrentScore().setValue(getCurrentScore().getValue() + 1);
        if (getCurrentScore().getValue() > getHighScore().getValue()) {
            // 如果当前分数超过了最高分数,就将最高分数设置为当前分数
            getHighScore().setValue(getCurrentScore().getValue());
            win_flag = true;
        }
        // 生成一道新的题目
        generator();
    }
}
