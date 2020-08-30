package com.example.calculationcompetition.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calculationcompetition.MyViewModel;
import com.example.calculationcompetition.R;
import com.example.calculationcompetition.databinding.FragmentQuestionBinding;
import com.example.calculationcompetition.databinding.FragmentTitleBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends Fragment {


    public QuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final MyViewModel myViewModel;
        myViewModel = new ViewModelProvider(requireActivity(), new SavedStateViewModelFactory(requireActivity().getApplication(), requireActivity())).get(MyViewModel.class);
        myViewModel.reset();
        myViewModel.generator(20);
        final FragmentQuestionBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_question, container, false);
        binding.setData(myViewModel);
        binding.setLifecycleOwner(requireActivity());
        final StringBuilder builder = new StringBuilder();
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button0:
                        builder.append("0");
                        break;
                    case R.id.button1:
                        builder.append("1");
                        break;
                    case R.id.button2:
                        builder.append("2");
                        break;
                    case R.id.button3:
                        builder.append("3");
                        break;
                    case R.id.button4:
                        builder.append("4");
                        break;
                    case R.id.button5:
                        builder.append("5");
                        break;
                    case R.id.button6:
                        builder.append("6");
                        break;
                    case R.id.button7:
                        builder.append("7");
                        break;
                    case R.id.button8:
                        builder.append("8");
                        break;
                    case R.id.button9:
                        builder.append("9");
                        break;
                    case R.id.button_clear:
                        builder.setLength(0);
                        break;
                }

                if (builder.length() == 0) {
                    binding.textView9.setText(R.string.question_tv_indicator);
                } else {
                    binding.textView9.setText(builder.toString());
                }
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
        binding.buttonClear.setOnClickListener(listener);

        binding.buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //防止第一次不输入点确定按键
                if (builder.length() == 0){
                    builder.append("-1");
                }
                if (Integer.valueOf(builder.toString()).intValue() == myViewModel.getResult().getValue()) {
                    myViewModel.resultCorrect();
                    builder.setLength(0);
                    binding.textView9.setText(getString(R.string.question_answer_correct));
                } else {
                    NavController controller = Navigation.findNavController(view);
                    if (myViewModel.isFlag()) {
                        controller.navigate(R.id.action_questionFragment_to_successFragment);
                        myViewModel.setFlag(false);
                        myViewModel.save();
                    } else {
                        controller.navigate(R.id.action_questionFragment_to_failFragment);
                    }
                }
            }
        });
        return binding.getRoot();
    }

}
