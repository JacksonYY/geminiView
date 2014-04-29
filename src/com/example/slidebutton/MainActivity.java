package com.example.slidebutton;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.example.widget.OnChangedListener;
import com.example.widget.SlipButton;

public class MainActivity extends Activity implements OnSeekBarChangeListener {

	private SlipButton mSlipButton;
	private SlipButton mSlipButton1;
	private SlipButton mSlipButton2;
	private SeekBar mSeekBar;
	private Notification n;
	private NotificationManager nm;
	// Notification的标示ID
	private static final int ID = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// if (flag == 0) {
		// requestWindowFeature(Window.FEATURE_LEFT_ICON);
		// } else if (flag == 1) {
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// }
		setContentView(R.layout.activity_main);
		initNotification();
		mSlipButton = (SlipButton) this.findViewById(R.id.on1);
		mSlipButton1 = (SlipButton) this.findViewById(R.id.on2);
		mSlipButton2 = (SlipButton) this.findViewById(R.id.on3);

		StartListener startListener = new StartListener();
		mSlipButton.SetOnChangedListener(startListener);// 设置启动事件监听

		AutoConfigListener autoConfigListener = new AutoConfigListener();
		mSlipButton1.SetOnChangedListener(autoConfigListener);// 自动配置事件的监听

		TaskBarState taskBarState = new TaskBarState();
		mSlipButton2.SetOnChangedListener(taskBarState); // 状态栏事件的监听

		mSeekBar = (SeekBar) findViewById(R.id.seekBar1);
		mSeekBar.setOnSeekBarChangeListener(this); // 状态栏事件的监听

	}

	/**
	 * 
	 * @author jackson 　根据需要在监听器中决定是否开启任务栏的图标
	 */
	class StartListener implements OnChangedListener {

		@SuppressWarnings("deprecation")
		public void OnChanged(boolean CheckState) {
			if (CheckState) {
				// Toast.makeText(MainActivity.this.getApplicationContext(),
				// "打开了", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(MainActivity.this,
						MainActivity.class);
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0,
						intent, 0);
				// 设置事件信息
				n.setLatestEventInfo(MainActivity.this, "My Title", "My Content", pi);
				// 发出通知
				nm.notify(ID, n);
			} else {

				// Toast.makeText(MainActivity.this.getApplicationContext(),
				// "关闭了", Toast.LENGTH_LONG).show();
				nm.cancel(ID);
			}
		}
	}

	/**
	 * 自动配置的接口
	 * 
	 * @author jackson
	 * 
	 */
	class AutoConfigListener implements OnChangedListener {

		public void OnChanged(boolean CheckState) {
			if (CheckState) {
				Toast.makeText(MainActivity.this.getApplicationContext(),
						"自动配置打开２", Toast.LENGTH_LONG).show();

			} else {
				Toast.makeText(MainActivity.this.getApplicationContext(),
						"自动配置关闭２", Toast.LENGTH_LONG).show();
			}
		}

	}

	/**
	 * 任务栏的接口，实现任务栏的开启和关闭
	 * 
	 * @author jackson
	 * 
	 */
	class TaskBarState implements OnChangedListener {

		public void OnChanged(boolean CheckState) {
			if (CheckState) {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
				getWindow().setAttributes(lp);
				getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			} else {
				WindowManager.LayoutParams attr = getWindow().getAttributes();
				attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
				getWindow().setAttributes(attr);
				getWindow().clearFlags(
						WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
			}

		}

	}

	// 以下为手动配置滚动调的接口
	// SeekBar滚动时的回调函数
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		Toast.makeText(this, "滚动中" + mSeekBar.getProgress(),  Toast.LENGTH_SHORT).show();

	}

	// SeekBar开始滚动时的函数
	public void onStartTrackingTouch(SeekBar arg0) {
		Toast.makeText(this, "开始滚动了" + mSeekBar.getProgress(), Toast.LENGTH_SHORT).show();
	}

	// SeekBar停止滚动时的函数
	public void onStopTrackingTouch(SeekBar arg0) {
		Toast.makeText(this, "停止滚动" + mSeekBar.getProgress(),  Toast.LENGTH_SHORT).show();
	}

	private void initNotification() {
		nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		n = new Notification();
		int icon = R.drawable.images_off;
		long when = System.currentTimeMillis();
		n.icon = icon;
		n.when = when;
		n.flags = Notification.FLAG_NO_CLEAR;
		n.flags = Notification.FLAG_ONGOING_EVENT;
	}
}
