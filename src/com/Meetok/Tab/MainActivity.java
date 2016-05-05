package com.Meetok.Tab;

import java.util.Timer;
import java.util.TimerTask;

import com.Meetok.Activity.LogActivity;
import com.Meetok.Application.SysApplication;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Util.NetWorkUtil;
import com.Meetok.fragment.HomeFragment;
import com.Meetok.fragment.MeFragment;
import com.Meetok.fragment.PurchaseFragment;
import com.Meetok.fragment.SaleFragment;
import com.Meetok.fragment.ShoppingCartFragment;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main
 * 
 * @author
 * 
 */
public class MainActivity extends FragmentActivity implements OnClickListener,
		IBtnCallListener {

	// 界面底部的菜单按钮
	private ImageView[] bt_menu = new ImageView[5];
	private TextView[] tv = new TextView[5];
	// 界面底部的菜单按钮id
	private int[] bt_menu_id = { R.id.iv_menu_0, R.id.iv_menu_1,
			R.id.iv_menu_2, R.id.iv_menu_3, R.id.iv_menu_4 };
	private int[] tv_id = { R.id.t_0, R.id.t_1, R.id.t_2, R.id.t_3, R.id.t_4 };
	// 界面底部的选中菜单按钮资源
	private int[] select_on = { R.drawable.shouye1, R.drawable.gouwuche1,
			R.drawable.jinhuo1, R.drawable.fahuo1, R.drawable.mine1 };
	// 界面底部的未选中菜单按钮资源
	private int[] select_off = { R.drawable.shouye, R.drawable.gouwuche,
			R.drawable.jinhuo, R.drawable.fahuo, R.drawable.mine };
	private HomeFragment home_F;
	private ShoppingCartFragment tao_F;
	private PurchaseFragment discover_F;
	private SaleFragment cart_F;
	private MeFragment user_F;
	private static Boolean isQuit = false;
	Timer timer = new Timer();
	// private static TipsToast tipsToast;
	public static Activity home;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SysApplication.getInstance().addActivity(this);

		setContentView(R.layout.activity_main);

		home = this;

		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		initView();
		/*
		 * if (!NetWorkUtil.isNetworkConnected(MainActivity.this)) {
		 * showTips(R.drawable.tips_error, "网络未连接，请先连接网络..."); Intent intent =
		 * new Intent().setClass(MainActivity.this, NetWorkActivity.class);
		 * startActivityForResult(intent, 1); }
		 */

	}

	// 初始化组件
	private void initView() {
		// 找到底部菜单的按钮并设置监听
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
			bt_menu[i].setOnClickListener(this);
		}
		for (int i = 0; i < bt_menu.length; i++) {
			tv[i] = (TextView) findViewById(tv_id[i]);
			tv[i].setOnClickListener(this);
		}
		showDefaultFragment();
		// 获取从登入的activity传递过来的数据，并跳转到个人中心页面
		/*
		 * if (getIntent() != null) { Intent intent = getIntent(); Bundle bundle
		 * = intent.getExtras(); if (bundle != null) { if
		 * (bundle.getInt("fragment") == 5) { // 初始化显示的界面个人中心
		 * 
		 * if (user_F == null) { user_F = new MeFragment(); addFragment(user_F);
		 * showFragment(user_F);
		 * 
		 * } else { showFragment(user_F);
		 * 
		 * 
		 * } // 设置默认首页为点击时的图片 bt_menu[4].setImageResource(select_on[4]); }else {
		 * showDefaultFragment(); } }else { showDefaultFragment();
		 * 
		 * }
		 * 
		 * // }else { // showDefaultFragment(); // } // showDefaultFragment();
		 */
	}

	/*
	 * 初始化默认显示的界面方法
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	public void showDefaultFragment() {
		// 初始化默认显示的界面
		if (home_F == null) {
			home_F = new HomeFragment();
			addFragment(home_F);
			showFragment(home_F);

		} else {
			showFragment(home_F);

		}
		// 设置默认首页为点击时的图片
		bt_menu[0].setImageResource(select_on[0]);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_menu_0:
			// 主界面
			if (home_F == null) {
				home_F = new HomeFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				addFragment(home_F);
				showFragment(home_F);

			} else {
				if (home_F.isHidden()) {
					showFragment(home_F);

				}
			}

			break;
		case R.id.iv_menu_1:
			// 购物车界面
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {	
								Intent intent = new Intent(MainActivity.this,
										LogActivity.class);
								//MainActivity.this.finish();
								startActivityForResult(intent, 0);
								overridePendingTransition(R.anim.slide_right_in,
										R.anim.slide_right_out);
			} else {
				if (tao_F == null) {
					tao_F = new ShoppingCartFragment();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!tao_F.isHidden()) {
						addFragment(tao_F);
						showFragment(tao_F);

					} else {
						if (tao_F.isHidden()) {
						
							showFragment(tao_F);

						}
					}
				}
			}
			break;
		case R.id.iv_menu_2:
			// 采购进货界面
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {

				Intent intent = new Intent(MainActivity.this,
						LogActivity.class);
				//MainActivity.this.finish();
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			} else {
				if (discover_F == null) {
					discover_F = new PurchaseFragment();
					// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
					if (!discover_F.isHidden()) {
						addFragment(discover_F);
						showFragment(discover_F);

					}
				} else {
					if (discover_F.isHidden()) {
						showFragment(discover_F);

					}
				}
			}
			break;
		case R.id.iv_menu_3:
			// 销售发货界面
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {

				Intent intent = new Intent(MainActivity.this,
						LogActivity.class);
				//MainActivity.this.finish();
				startActivityForResult(intent, 0);
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			} else {
			if (cart_F == null) {
				cart_F = new SaleFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!cart_F.isHidden()) {
					addFragment(cart_F);
					showFragment(cart_F);

				}
			} else {
				if (cart_F.isHidden()) {
					showFragment(cart_F);

				}
			}
			}
			break;
		case R.id.iv_menu_4:
			// 会员中心界面
			if (user_F == null) {
				user_F = new MeFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!user_F.isHidden()) {
					addFragment(user_F);
					showFragment(user_F);
				}
			} else {
				if (user_F.isHidden()) {
					showFragment(user_F);

				}
			}

			break;
		case R.id.t_0:
			// 主界面
			if (home_F == null) {
				home_F = new HomeFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				addFragment(home_F);
				showFragment(home_F);

			} else {
				if (home_F.isHidden()) {
					showFragment(home_F);

				}
			}

			break;
		case R.id.t_1:
			// 购物车界面
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {	
								Intent intent = new Intent(MainActivity.this,
										LogActivity.class);
								//MainActivity.this.finish();
								startActivityForResult(intent, 0);
								overridePendingTransition(R.anim.slide_right_in,
										R.anim.slide_right_out);
			} else {
			if (tao_F == null) {
				tao_F = new ShoppingCartFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!tao_F.isHidden()) {
					addFragment(tao_F);
					showFragment(tao_F);

				}
			} else {
				if (tao_F.isHidden()) {
					
					showFragment(tao_F);

				}
			}
			}
			break;
		case R.id.t_2:
			// 采购进货界面
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {	
								Intent intent = new Intent(MainActivity.this,
										LogActivity.class);
								//MainActivity.this.finish();
								startActivityForResult(intent, 0);
								overridePendingTransition(R.anim.slide_right_in,
										R.anim.slide_right_out);
			} else {
			if (discover_F == null) {
				discover_F = new PurchaseFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!discover_F.isHidden()) {
					addFragment(discover_F);
					showFragment(discover_F);

				}
			} else {
				if (discover_F.isHidden()) {
					showFragment(discover_F);

				}
			}
			}
			break;
		case R.id.t_3:
			// 销售发货界面
			if (LogActivity.loadDataFromLocalXML(MainActivity.this,
					"accesstoken") == "") {	
								Intent intent = new Intent(MainActivity.this,
										LogActivity.class);
								//MainActivity.this.finish();
								startActivityForResult(intent, 0);
								overridePendingTransition(R.anim.slide_right_in,
										R.anim.slide_right_out);
			} else {
			if (cart_F == null) {
				cart_F = new SaleFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!cart_F.isHidden()) {
					addFragment(cart_F);
					showFragment(cart_F);

				}
			} else {
				if (cart_F.isHidden()) {
					showFragment(cart_F);

				}
			}
			}
			break;
		case R.id.t_4:
			// 会员中心界面
			if (user_F == null) {
				user_F = new MeFragment();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				if (!user_F.isHidden()) {
					addFragment(user_F);
					showFragment(user_F);

				}
			} else {
				if (user_F.isHidden()) {
					showFragment(user_F);

				}
			}

			break;
		}

		// 设置按钮的选中和未选中资源
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if ((v.getId() == bt_menu_id[i]) || (v.getId() == tv_id[i])) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}

	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.add(R.id.show_layout, fragment);
		ft.commit();
	}

	/** 删除Fragment **/
	public void removeFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	/** 显示Fragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		// 设置Fragment的切换动画
		// ft.setCustomAnimations(R.anim.cu_push_right_in,
		// R.anim.cu_push_left_out);

		// 判断页面是否已经创建，如果已经创建，那么就隐藏掉
		if (home_F != null) {
			ft.hide(home_F);
		}
		if (tao_F != null) {
			ft.hide(tao_F);
		}
		if (discover_F != null) {
			ft.hide(discover_F);
		}
		if (cart_F != null) {
			ft.hide(cart_F);
		}
		if (user_F != null) {
			ft.hide(user_F);
		}

		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getRepeatCount() == 0) {
			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(getBaseContext(), "再按一次返回键退出米到仓",
						Toast.LENGTH_SHORT).show();
				TimerTask task = null;
				task = new TimerTask() {
					@Override
					public void run() {
						isQuit = false;
					}
				};
				timer.schedule(task, 2000);

			} else {
				// finish();
				// SysApplication.getInstance().exit();
				// System.exit(0);
				moveTaskToBack(false);
			}
		}
		return true;
	}

	/** Fragment的回调函数 */
	@SuppressWarnings("unused")
	private IBtnCallListener btnCallListener;

	@Override
	public void onAttachFragment(Fragment fragment) {
		try {
			btnCallListener = (IBtnCallListener) fragment;
		} catch (Exception e) {
		}

		super.onAttachFragment(fragment);
	}

	/**
	 * 响应从Fragment中传过来的消息
	 */
	@Override
	public void transferMsg() {
		if (user_F == null) {
			user_F = new MeFragment();
			addFragment(user_F);
			showFragment(user_F);
		} else {
			showFragment(user_F);
		}
		bt_menu[3].setImageResource(select_off[3]);
		bt_menu[4].setImageResource(select_on[4]);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("onActivityResult==="+requestCode+resultCode+ data);
		if (resultCode == LogActivity.RESULT_OK_Log) {
			if (user_F == null) {
				user_F = new MeFragment();
				addFragment(user_F);
				showFragment(user_F);
			} else {
				showFragment(user_F);
			}
			bt_menu[4].setImageResource(select_on[4]);
		}
			switch (requestCode) {
			case 1:
				String keyStr = data.getStringExtra("key");
				if ("-1".equals(keyStr)) {
					// showTips(R.drawable.tips_error, "网络不可用...");

				} else {
					// showTips(R.drawable.tips_smile, "网络已恢复正常...");
				}
				break;
			case 2:
				if (home_F == null) {
					home_F = new HomeFragment();
					addFragment(home_F);
					showFragment(home_F);
				} else {
					showFragment(home_F);
				}
				bt_menu[0].setImageResource(select_on[0]);
				break;

			}
		}


	/*
	 * private void showTips(int iconResId, String tips) { if (tipsToast !=
	 * null) { if (Build.VERSION.SDK_INT <
	 * Build.VERSION_CODES.ICE_CREAM_SANDWICH) { tipsToast.cancel(); } } else {
	 * tipsToast = TipsToast.makeText(getApplication().getBaseContext(), tips,
	 * TipsToast.LENGTH_SHORT); } tipsToast.show();
	 * tipsToast.setIcon(iconResId); tipsToast.setText(tips); }
	 */
	@Override
	protected void onResume() {

		super.onResume();
	}

}
