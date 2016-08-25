package com.szysky.note.androiddevseek_12.photowall;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DrawableUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.szysky.note.androiddevseek_12.R;
import com.szysky.note.androiddevseek_12.load.ImageLoader;

import java.util.ArrayList;

public class PhotoWallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_wall);

        ArrayList<String> urls = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            urls.add("http://img9.dzdwl.com/img/11543935W-1.jpg");
            urls.add("http://img02.tooopen.com/images/20160408/tooopen_sy_158723161481.jpg");
            urls.add("http://img02.tooopen.com/images/20160404/tooopen_sy_158262392146.jpg");
            urls.add("http://img02.tooopen.com/images/20160318/tooopen_sy_156339294124.jpg");
            urls.add("http://img06.tooopen.com/images/20160823/tooopen_sy_176393394325.jpg");
            urls.add("http://img06.tooopen.com/images/20160821/tooopen_sy_176144979595.jpg");
            urls.add("http://img06.tooopen.com/images/20160723/tooopen_sy_171462742667.jpg");
            urls.add("http://img05.tooopen.com/images/20150417/tooopen_sy_119014046478.jpg");
            urls.add("http://img02.tooopen.com/images/20150318/tooopen_sy_82853534894.jpg");
            urls.add("http://img05.tooopen.com/images/20150204/tooopen_sy_80359399983.jpg");
            urls.add("http://img01.tooopen.com/Downs/images/2010/4/9/sy_20100409135808693051.jpg");
            urls.add("http://pics.sc.chinaz.com/files/pic/pic9/201410/apic7065.jpg");
            urls.add("http://www.yiren001.com/uploads/allimg/150315/114AJ1c-1.jpg");
            urls.add("http://www.yiren001.com/uploads/allimg/150315/114AH0B-0.jpg");
            urls.add("http://www.yiren001.com/uploads/allimg/150315/114AJB9-6.jpg");
            urls.add("http://www.yiren001.com/uploads/allimg/150315/114AG0I-7.jpg");
        }




        GridView gv_main = (GridView) findViewById(R.id.gv_main);
        ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), urls);
        gv_main.setAdapter(imageAdapter);


    }


    /**
     * 给GridView创建一个适配器
     */
    private static class ImageAdapter extends BaseAdapter{

        private final ArrayList<String> mUrls;
        private Context mContext;
        private final ImageLoader mImageLoader;

        public ImageAdapter(Context context, ArrayList<String> mUrls){
            mContext = context;
            this.mUrls = mUrls;
            mImageLoader =  ImageLoader.build(context);
        }

        @Override
        public int getCount() {
            return mUrls.size();
        }

        @Override
        public String getItem(int position) {
            return mUrls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null){
                convertView = View.inflate(mContext, R.layout.item_photo_wall, null);
                holder = new ViewHolder();
                holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_square);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ImageView mImageView = holder.mImageView;
            mImageView.setImageResource(android.R.drawable.screen_background_dark_transparent);

            // 加载图片
            mImageLoader.setImageView(mImageView).url(mUrls.get(position));


            return convertView;
        }

        class ViewHolder{
            private ImageView mImageView;
        }
    }



}
