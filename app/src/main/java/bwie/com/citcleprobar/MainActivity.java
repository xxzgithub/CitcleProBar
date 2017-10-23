package bwie.com.citcleprobar;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.czp.library.ArcProgress;
import com.czp.library.OnTextCenter;

public class MainActivity extends AppCompatActivity {

    ArcProgress mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        mProgress = (ArcProgress) findViewById(R.id.myProgress);
        //默认实现的类
        //new OnTextcenter 初始化 文字颜色和文字大小
        mProgress.setOnCenterDraw(new OnTextCenter(Color.BLUE, 30));
        addProrgress(mProgress);
    }

    //开启线程的方法
    public void addProrgress(ArcProgress progressBar) {
        Thread thread = new Thread(new ProgressThread(progressBar));
        thread.start();
    }

    //更新UI
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ArcProgress progressBar = (ArcProgress) msg.obj;
            progressBar.setProgress(msg.what);
        }
    };

    //子线程
    class ProgressThread implements Runnable {
        int i = 0;
        private ArcProgress progressBar;

        public ProgressThread(ArcProgress progressBar) {
            this.progressBar = progressBar;
        }

        @Override
        public void run() {
            for (; i <= 100; i++) {
                //判断结束
                if (isFinishing()) {
                    break;
                }
                //实例化消息对象
                Message msg = new Message();
                msg.what = i;
//                Log.e("DEMO","i == "+i);
                //发送进度条的进度
                msg.obj = progressBar;
                //系统时钟 睡 100 毫秒
                SystemClock.sleep(100);
                handler.sendMessage(msg);
            }
        }
    }
}