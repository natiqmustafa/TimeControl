package com.natodriod.timecontrol.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.natodriod.timecontrol.R;
import com.natodriod.timecontrol.common.DATE;
import com.natodriod.timecontrol.data.ImDoingData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by natiqmustafa on 10.04.2017.
 */

public class PieCharFragment extends Fragment {
    private static final String TAG = "PieCharFragment";

    private  static final long MILLISECOND_TO_MINUTE = 60000;
    private static final long DAY_MINUTE = (17 * 60);

    private PieChart mChart;
    private Typeface mTfRegular;
    private Typeface mTfLight;

    private  EditText etDatetimePiechart;

    private int sday;
    private int smon;
    private int syear;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        mTfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_fragment, container, false);
        mChart  = (PieChart) view.findViewById(R.id.pie_char);
        addDateTime(view);
        addPieChart();

        return view;
    }

    private void addDateTime(View view) {
        etDatetimePiechart = (EditText) view.findViewById(R.id.et_datetime_piechart);

        etDatetimePiechart.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                addPieChart();
            }
        });

        etDatetimePiechart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getContext(), listener, syear, smon, sday).show();
            }
        });

        Calendar c = Calendar.getInstance();
        sday  = c.get(Calendar.DAY_OF_MONTH);
        smon  = c.get(Calendar.MONTH);
        syear = c.get(Calendar.YEAR);
        etDatetimePiechart.setText(DATE.SDF.format(c.getTime()));
    }


    DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            sday = dayOfMonth;
            smon = monthOfYear;
            syear = year;

            final Calendar c = Calendar.getInstance();
            c.set(syear, smon, sday);
            etDatetimePiechart.setText(DATE.SDF.format(c.getTime()));
        }
    };

    private void addPieChart() {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//        mChart.setOnChartValueSelectedListener(getContext());

        pieEntries();

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        // mChart.spin(2000, 0, 360);


        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.BLACK);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("TimeControl\ndeveloped by Natiq Mustafa");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 11, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 11, s.length() - 12, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 12, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 11, s.length() - 12, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 11, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 11, s.length(), 0);
        return s;
    }



    private void pieEntries(){

        String date = etDatetimePiechart.getText().toString().trim();
        if (date.equals(""))
            date = DATE.today();

        Map<String, Long> map = new ImDoingData(getContext()).getMap4Pie(date);
        if (map == null)
            return;

        long totalDuration = 0;
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (String key : map.keySet()){
            Log.d(TAG, "pieEntries: " + map.get(key));
            totalDuration += (map.get(key) / MILLISECOND_TO_MINUTE);
            float d = map.get(key) / MILLISECOND_TO_MINUTE;
            entries.add(new PieEntry(d, key));
        }
        if (entries.size() > 0) {
            long unknowTime = DAY_MINUTE - totalDuration;
            entries.add(new PieEntry(unknowTime, getString(R.string.unknown)));
        }
        setData(entries);
    }

    private void setData( ArrayList<PieEntry> entries) {
        PieDataSet dataSet = new PieDataSet(entries, getString(R.string.done_list_pie));

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(mTfLight);


        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }
}
