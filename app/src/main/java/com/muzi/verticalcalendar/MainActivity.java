package com.muzi.verticalcalendar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.muzi.library.CalendarView;
import com.muzi.library.bean.DayBean;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Button button;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.btnClear);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                calendarView.resetState();
                calendarView.addUnableDays(Integer.parseInt(editText.getText().toString().trim()));
            }
        });

        calendarView.setOnCalendarChange(new CalendarView.OnCalendarChange() {
            @Override
            public void onStart(DayBean dayBean) {

            }

            @Override
            public void onEnd(DayBean dayBean) {

            }

            @Override
            public void onDays(int day) {
                super.onDays(day);
                Log.d("MainActivity", "day:" + day);
            }
        });
    }
}
