package com.gaoxianglong.calculationtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaoxianglong.calculationtest.databinding.FragmentQuestionBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {

    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyViewModel viewModel;
        // 获取MyViewModel实例
        viewModel = ViewModelProviders.of(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(),
                        this))
                .get(MyViewModel.class);
        viewModel.generator(); // 生成题目
        FragmentQuestionBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        binding.setData(viewModel);
        binding.setLifecycleOwner(requireActivity());
        TextView textView = binding.textView9;
        StringBuilder sb = new StringBuilder();
        View.OnClickListener listener = v -> {
            switch (v.getId()) {
                case R.id.button0:
                    sb.append("0");
                    break;
                case R.id.button1:
                    sb.append("1");
                    break;
                case R.id.button2:
                    sb.append("2");
                    break;
                case R.id.button3:
                    sb.append("3");
                    break;
                case R.id.button4:
                    sb.append("4");
                    break;
                case R.id.button5:
                    sb.append("5");
                    break;
                case R.id.button6:
                    sb.append("6");
                    break;
                case R.id.button7:
                    sb.append("7");
                    break;
                case R.id.button8:
                    sb.append("8");
                    break;
                case R.id.button9:
                    sb.append("9");
                    break;
                case R.id.buttonDelete:
                    sb.setLength(0);
                    break;
            }
            if (sb.length() == 0) {
                textView.setText(getString(R.string.input_indicator));
            } else {
                textView.setText(sb.toString());
            }
        };
        binding.button0.setOnClickListener(listener);
        binding.button1.setOnClickListener(listener);
        binding.button2.setOnClickListener(listener);
        binding.button3.setOnClickListener(listener);
        binding.button4.setOnClickListener(listener);
        binding.button5.setOnClickListener(listener);
        binding.button6.setOnClickListener(listener);
        binding.button7.setOnClickListener(listener);
        binding.button8.setOnClickListener(listener);
        binding.button9.setOnClickListener(listener);
        binding.buttonDelete.setOnClickListener(listener);
        binding.buttonSubmit.setOnClickListener(v -> {
            //noinspection ConstantConditions
            if (Integer.valueOf(sb.toString()).intValue() == viewModel.getAnswer().getValue()) {
                viewModel.answerCorrect();
                sb.setLength(0);
                textView.setText(R.string.answer_correct_message);
            } else {
                NavController controller = Navigation.findNavController(v);
                if (viewModel.win_flag) {
                    controller.navigate(R.id.action_questionFragment_to_winFragment);
                    viewModel.win_flag = false; // 胜利了要把这个重新改回false
                    viewModel.save(); // 保存最高分数
                } else {
                    controller.navigate(R.id.action_questionFragment_to_loseFragment);
                }
            }
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_question, container, false);
    }
}
