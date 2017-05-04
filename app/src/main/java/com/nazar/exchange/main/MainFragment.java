package com.nazar.exchange.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.nazar.exchange.R;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment implements MainContract.View {

    ArrayAdapter<String> spinAdapter;
    Spinner mSpinnerFrom, mSpinnerTo;
    Integer mIndexSpinnerFrom = 0, mIndexSpinnerTo = 0;
    EditText mInpit;
    TextView mResult;
    MainContract.Presenter mPresenter;
    private boolean enable;

    public MainFragment() {
        //empty
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //spinAdapter
        spinAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>());
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    //init handlers and listeners
    private void init() {
        //spinners
        mSpinnerFrom = (Spinner) getActivity().findViewById(R.id.spinnerFrom);
        mSpinnerFrom.setAdapter(spinAdapter);

        //spinner listener
        mSpinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mIndexSpinnerFrom = position;
                mPresenter.setValuteFrom(mIndexSpinnerFrom);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerTo = (Spinner) getActivity().findViewById(R.id.spinnerTo);
        mSpinnerTo.setAdapter(spinAdapter);

        //spinner listener
        mSpinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mIndexSpinnerTo = position;
                mPresenter.setValuteTo(mIndexSpinnerTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //title
        mSpinnerFrom.setPrompt(getString(R.string.spinner_title));
        mSpinnerTo.setPrompt(getString(R.string.spinner_title));

        //select default
        mSpinnerFrom.setSelection(mIndexSpinnerFrom);
        mSpinnerTo.setSelection(mIndexSpinnerTo);

        mInpit = (EditText) getActivity().findViewById(R.id.inputValue);
        mInpit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                mPresenter.convertRequest(mInpit.getText().toString());
                return false;
            }
        });

        /*
        // set Autoconvertation
        mInpit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.convertRequest( s.toString() );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        */

        FloatingActionButton fabConvert = (FloatingActionButton) getActivity().findViewById(R.id.fab_convert);
        fabConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInpit.clearFocus();
                mPresenter.convertRequest(mInpit.getText().toString());
                hideKeyboard();
            }
        });

        FloatingActionButton fabClear = (FloatingActionButton) getActivity().findViewById(R.id.fab_clear);
        fabClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.clearInputText();
                showKeyboard();
            }
        });

        FloatingActionButton fabSwap = (FloatingActionButton) getActivity().findViewById(R.id.fab_swap);
        fabSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer oldFrom = mIndexSpinnerFrom;
                Integer oldTo = mIndexSpinnerTo;
                mSpinnerTo.setSelection(oldFrom);
                mSpinnerFrom.setSelection(oldTo);
            }
        });

        mResult = (TextView) getActivity().findViewById(R.id.resultValue);
    }

    public void toggleKeyboard() {
        mInpit.requestFocus();
        mInpit.post(
                new Runnable() {
                    public void run() {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInputFromWindow(mInpit.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
                        mInpit.requestFocus();
                    }
                });
    }

    public void showKeyboard() {
        mInpit.requestFocus();
        mInpit.post(
                new Runnable() {
                    public void run() {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.showSoftInput(mInpit, 0);
                        mInpit.requestFocus();
                    }
                });
    }

    public void hideKeyboard() {
        mInpit.requestFocus();
        mInpit.post(
                new Runnable() {
                    public void run() {
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(mInpit.getWindowToken(), 0);
                        mInpit.requestFocus();
                    }
                });
    }

    public void setInputText(String text) {
        mInpit.setText(text);
        mInpit.requestFocus();
    }

    @Override
    public void showErrorMessage() {
        showMessage(getString(R.string.message_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    public void setResultText(String text) {
        mResult.setText(text);
    }

    @Override
    public void showValutes(List<String> valutes) {
        spinAdapter.clear();
        spinAdapter.addAll(valutes);
        spinAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment, container, false);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void lockUI(boolean enable) {
        //logical invert
        setEnableUI(!enable, (CoordinatorLayout) getActivity().findViewById(R.id.fragmentLayout));
    }

    private void setEnableUI(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                setEnableUI(enable, (ViewGroup) child);
            }
        }
    }
}
