package com.szysky.note.androiddevseek_12.load;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.util.Log;
import android.util.LruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    private static final int DISK_CACHE_IDEX = 0;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final String TAG = ImageLoader.class.getSimpleName();

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
     * 标记是否已经启动了磁盘缓存
     */
    private boolean mIsDiskLruCacheCreated;

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

    /**********************给内存缓存添加操作方法**********************/
    /**
     *  添加bitmap对象到内存缓存中
     * @param key   根据图片的url生成的32md5值
     * @param bitmap    需要缓存的bitmap对象
     */
    private void addBitmapToMemoryCache(String key, Bitmap bitmap){
        // 如果内存缓存中不存在, 那么才进行添加的动作
        if (null == getBitmapFromMemoryCache(key)){
            mMemoryCache.put(key, bitmap);
        }
    }



    /**
     *  根据key值获取在内存缓存中保存的bitmap
     * @param key 根据图片的url生成的32md5值
     * @return  如果内存缓存中有对应的值, 那么就返回bitmap, 没有返回值就为null
     */
    private Bitmap getBitmapFromMemoryCache(String key){
        return mMemoryCache.get(key);
    }



    /**********************给磁盘缓存添加操作方法**********************/
    private Bitmap loadBitmapFromHttp(String url, int reqWidth, int reqHeight) throws IOException{
        // 因为从网络下载, 不允许操作线程是主线正
        if (Looper.myLooper() == Looper.getMainLooper()){
            throw  new RuntimeException("不能再主线程中发起网络请求");
        }

        // 因为本实例 是先下载先保存在磁盘, 然后从磁盘获取 所以如果磁盘无效那么就停止.
        if (mDiskLruCache == null){
            return null;
        }

        // 根据url算出md5值
        String key = keyFormUrl(url);

        // 开始对磁盘缓存的一个存储对象进行操作
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        // 如果==null说明这个editor对象正在被使用
        if (null != editor){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_IDEX);
            if (downLoadUrlToStream(url, outputStream)){
                //加载成功进行 提交操作
                editor.commit();
            }else{
                // 进行数据回滚
                editor.abort();
            }
            mDiskLruCache.flush();
        }

        // 从磁盘缓存获取, 并在内部添加到内存中去.
        return loadBitmapFromDiskCache(url, reqWidth, reqHeight);

    }


    private Bitmap loadBitmapFromDiskCache(String url, int reqWidth, int reqHeight) throws IOException {
        // 因为从网络下载, 不允许操作线程是主线正
        if (Looper.myLooper() == Looper.getMainLooper()){
            throw  new RuntimeException("不能再主线程中发起网络请求");
        }
        if (mDiskLruCache == null){
            return null;
        }

        Bitmap bitmap = null;
        String key = keyFormUrl(url);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (null != snapshot){
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(DISK_CACHE_IDEX);
            // 由于文件流属于一种有序的文件流, 所以无法进行两次decode. 这里通过获得文件描述符的方法解决
            FileDescriptor fd = fileInputStream.getFD();
            bitmap = ImageCompression.decodeFixedSizeForFileDescription(fd, reqWidth, reqHeight);

            if (bitmap != null){
                addBitmapToMemoryCache(key, bitmap);
            }
        }


        return null;
    }

    /**
     *  通过一个网络路径来下载文件
     * @param urlStr           要下载的地址
     * @param outputStream  需要把下载的流写入到传入的此流中
     * @return              是写入成功
     */
    public  boolean downLoadUrlToStream(String urlStr, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        try {
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();

            // 获得网络连接获得的输入流
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);

            // 创建Buffer并指定要写入的磁盘缓存输出流
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            // 开始把输入流的数据写入到磁盘缓存输出流
            while ((b = in.read()) != -1){
                out.write(b);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection == null) {
                urlConnection.disconnect();
            }
            CloseUtil.close(in);
            CloseUtil.close(out);

        }

        return false;
    }


    /**
     *  接收一个url地址, 对其转换成md5值并返回
     *   转成一个32md5值
     */
    public String keyFormUrl(String url){
        String cacheKey;
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1){
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }


    /**
     * 对外提供的同步加载方法
     * @param uriStr  传入图片对应的网络路径
     * @param reqWidth  需要目标的宽度
     * @param reqHeight 需要目标的高度
     * @return 返回Bitmap对象
     */
    public Bitmap loadBitmap(String uriStr, int reqWidth, int reqHeight){
        long entry = System.currentTimeMillis();
        // 1.从内存中读取
        Bitmap bitmap = getBitmapFromMemoryCache(uriStr);
        if (bitmap != null) {
            Log.d(TAG, "loadBitmap --> 图片从内存中加载成功 uri="+uriStr+"\r\n消耗时间="+(System.currentTimeMillis()-entry)+"s");
            return bitmap;
        }

        // 2.从磁盘缓存加载
        try {
            bitmap = loadBitmapFromDiskCache(uriStr, reqWidth, reqHeight);
            if (bitmap != null) {
                Log.d(TAG, "loadBitmap --> 图片从磁盘中加载成功 uri="+uriStr+"\r\n消耗时间="+(System.currentTimeMillis()-entry)+"s");
                return bitmap;
            }

            // 3. 磁盘缓存也没有那么直接从网络下载
            bitmap = loadBitmapFromHttp(uriStr, reqWidth, reqHeight);
            Log.d(TAG, "loadBitmap --> 图片从网络中加载成功 uri="+uriStr+"\r\n消耗时间="+(System.currentTimeMillis()-entry)+"s");

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap == null && !mIsDiskLruCacheCreated){
            Log.w(TAG, " 磁盘缓存没有创建, 准备从网络下载" );
            bitmap = downloadBitmapFromUrl(uriStr);
        }

        return bitmap;
    }

    private Bitmap downloadBitmapFromUrl(String uriStr) {

        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in= null;

        try {
            URL url = new URL(uriStr);
             urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);

        } catch (IOException e) {
            Log.e(TAG, "网络下载错误" );
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            CloseUtil.close(in);
        }


        return bitmap;
    }


}
