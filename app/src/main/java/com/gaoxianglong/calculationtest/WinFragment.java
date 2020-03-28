package com.gaoxianglong.calculationtest;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaoxianglong.calculationtest.databinding.FragmentWinBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class WinFragment extends Fragment {

    public WinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MyViewModel viewModel;
        viewModel = ViewModelProviders.of(requireActivity(),
                new SavedStateViewModelFactory(requireActivity().getApplication(),
                        this))
                .get(MyViewModel.class);
        FragmentWinBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_win, container, false);
        binding.setData(viewModel);
        binding.setLifecycleOwner(requireActivity());
        binding.button.setOnClickListener(v -> {
            NavController controller = Navigation.findNavController(v);
            controller.navigate(R.id.action_winFragment_to_titleFragment);
        });
        return binding.getRoot();
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_win, container, false);
    }
}
