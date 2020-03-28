package com.gaoxianglong.calculationtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaoxianglong.calculationtest.databinding.FragmentTitleBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class TitleFragment extends Fragment {

    public TitleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 获取MyViewModel实例
        MyViewModel viewModel = ViewModelProviders
                .of(requireActivity(),
                        new SavedStateViewModelFactory(requireActivity().getApplication(),
                                this))
                .get(MyViewModel.class);
        // 将布局和碎片绑定
        FragmentTitleBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false);
        binding.setData(viewModel); // 绑定数据
        binding.setLifecycleOwner(requireActivity()); // 观察者
        // 点击事件
        binding.button.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_titleFragment_to_questionFragment);
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_title, container, false);
    }
}
