package com.szysky.note.androiddevseek_12.load;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.LruCache;

import java.io.File;
import java.io.IOException;

/**
 * Author :  suzeyu
 * Time   :  2016-08-08  下午1:41
 * Blog   :  http://szysky.com
 * GitHub :  https://github.com/suzeyu1992
 *
 * ClassDescription : ImageLoad的主类
 */
public class ImageLoader {
    /**
     * 默认磁盘缓存的大小值
     */
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 50;

    /**
     * 本类实例对象
     */
    private static volatile ImageLoader ourInstance ;

    /**
     * 应用的上下文对象
     */
    private final Context mContext;

    /**
     * 内存区域的缓存
     */
    private final LruCache<String, Bitmap> mMemoryCache;

    /**
     * 磁盘缓存的对象
     */
    private DiskLruCache mDiskLruCache;

    /**
     *  返回本类的单例实例
     */
    public static ImageLoader getInstance(Context context) {
        if (ourInstance == null){
            synchronized (ImageLoader.class){
                if (ourInstance == null){
                    ourInstance = new ImageLoader(context);
                }
            }
        }
        return ourInstance;
    }

    private ImageLoader(Context context) {
        mContext = context.getApplicationContext();

        // 获得当前进程最大可用内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize =  maxMemory / 8 ;

        // 创建内存缓存的LruCache对象
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // 返回缓存的bitmap大小
                return value.getRowBytes() * value.getHeight() /1024;
            }
        };

        // 获得磁盘缓存的路径
        File diskCacheDir = getDiskCacheDir(mContext, "bitmap");

        if (!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }

        // 判断磁盘路径下可用的空间 是否达到预期大小, 如果达到, 那就创建磁盘缓存对象
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE){
            // 利用open函数来构建磁盘缓存对象
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     *  获得一个指定的文件夹路径的File对象
     * @param context   应用上下文
     * @param dirName   想要在SD卡的缓存路径下的哪一个子文件夹的对应名称
     * @return  返回一个路径对应的File对象
     */
    public File getDiskCacheDir(Context context, String dirName){
        // 判断SD卡是否被挂起
        boolean externalIsAlive = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        final String cachePath;

        if (externalIsAlive){
            cachePath = context.getExternalCacheDir().getPath();
        }else{
            cachePath = context.getCacheDir().getPath();
        }

        // 对想要的相对路径下的具体文件名进行创建并返回
        return new File(cachePath + File.separator + dirName);
    }


    // 指定文件对象可用的空间
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private long getUsableSpace(File path){
        // 大于等于android版本9
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return path.getUsableSpace();
        }

        StatFs statFs = new StatFs(path.getPath());
        return (long) statFs.getBlockSize() *  (long) statFs.getAvailableBlocks();
    }


}
