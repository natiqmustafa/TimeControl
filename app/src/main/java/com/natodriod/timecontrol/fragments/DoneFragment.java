package com.natodriod.timecontrol.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.adapter.DoneListAdapter;
import com.natodriod.timecontrol.common.DATE;
import com.natodriod.timecontrol.data.ImDoingData;

import java.util.Calendar;


/**
 * Created by natiqmustafa on 10.04.2017.
 */

public class DoneFragment extends Fragment {

    private int sday;
    private int smon;
    private int syear;

    private EditText etDonelistDatetime;
    private ListView lvDoneList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.done_fragment, container, false);
        lvDoneList = (ListView) view.findViewById(R.id.lv_done_list);
        addDateTime(view);
        loadListView();
        return view;
    }

    private void loadListView() {
        try {
            String date = etDonelistDatetime.getText().toString().trim();
            if (date.equals(""))
                date = DATE.today();

            DoneListAdapter adapter = new DoneListAdapter(getContext(), new ImDoingData(getContext()).getDoneList(date));
            lvDoneList.setAdapter(adapter);
        }catch (Exception ex){
            Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

    private void addDateTime(View view) {
        etDonelistDatetime = (EditText) view.findViewById(R.id.et_donelist_datetime);

        etDonelistDatetime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loadListView();
            }
        });

        etDonelistDatetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), listener, syear, smon, sday).show();
            }
        });

        Calendar c = Calendar.getInstance();
        sday  = c.get(Calendar.DAY_OF_MONTH);
        smon  = c.get(Calendar.MONTH);
        syear = c.get(Calendar.YEAR);
        etDonelistDatetime.setText(DATE.SDF.format(c.getTime()));
    }


    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sday = dayOfMonth;
            smon = monthOfYear;
            syear = year;

            final Calendar c = Calendar.getInstance();
            c.set(syear, smon, sday);
            etDonelistDatetime.setText(DATE.SDF.format(c.getTime()));
        }
    };
}
