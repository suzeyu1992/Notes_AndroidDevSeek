package com.szysky.note.androiddevseek_03.outside;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.szysky.note.androiddevseek_03.R;

import java.util.ArrayList;

/**
 *  针对外部拦截法进行 练习
 */
public class OutSideInterceptActivity extends AppCompatActivity {
    private int mScreenWidth;
    private int mScreenHeight;
    private OutSide2HorizontalScrollview myview_horizontal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outside_intercept);

        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;

        myview_horizontal = (OutSide2HorizontalScrollview) findViewById(R.id.myview_horizontal);

        addLayout(myview_horizontal);
    }

    private void addLayout(ViewGroup listContainer){
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) getLayoutInflater().inflate(R.layout.layout_out_content, listContainer, false );
            ViewGroup.LayoutParams layoutParams = layout.getLayoutParams();
            layoutParams.width = mScreenWidth;
            layoutParams.height = mScreenHeight;
            ((TextView)layout.findViewById(R.id.tv_main)).setText("第"+i+"页");
            layout.setBackgroundColor(Color.rgb(255/(i+1), 255/(i+1), 0));
            layout.setLayoutParams(layoutParams);

            createList(layout);
            listContainer.addView(layout);



        }
    }


    private void createList(ViewGroup layout){
        ListView lv_main = (ListView) layout.findViewById(R.id.lv_main);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("id "+i);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datas);
        lv_main.setAdapter(stringArrayAdapter);
    }
}
