package com.Meetok.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Activity.OrderNew;
import com.Meetok.Activity.Serach_shouye;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.Tab.ADInfo;
import com.Meetok.Tab.ImageCycleView;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;
import com.jingchen.pulltorefresh.pullableview.MyListener;
import com.jingchen.pulltorefresh.pullableview.PullToRefreshLayout;
import com.jingchen.pulltorefresh.pullableview.PullableScrollView;
import com.Meetok.Tab.ImageCycleView.ImageCycleViewListener;
import com.Meetok.View.MyGridView;
import com.Meetok.adapter.Adapter_home1;
import com.Meetok.adapter.Adapter_home_all;
import com.Meetok.adapter.Utility;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;

public class HomeFragment extends Fragment implements OnItemClickListener,
		OnClickListener {

	private Adapter_home1 gridview_adapter;
	private Adapter_home_all adapter_grid_01;
	private Adapter_home_all adapter_grid_02;
	private Adapter_home_all adapter_grid_03;
	private Adapter_home_all adapter_grid_04;
	private Adapter_home_all adapter_grid_05;
	private Adapter_home_all adapter_grid_06;
	private MyGridView grid_more;
	private MyGridView grid_01;
	private MyGridView grid_02;
	private MyGridView grid_03;
	private MyGridView grid_04;
	private MyGridView grid_05;
	private MyGridView grid_06;
	private ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
	private PullableScrollView mPullScrollView;
	private AbHttpUtil httpUtil = null;

	private ViewPager adViewPager;
	private LinearLayout pagerLayout;
	private List<View> pageViews;
	private ImageView[] imageViews;
	private ImageView imageView;
	private ImageView img_sousuo;
	private AdPageAdapter adapter;
	private AtomicInteger atomicInteger = new AtomicInteger(0);
	private boolean isContinue = true;

	List<ShouyeEntity> mlistmore = new ArrayList<ShouyeEntity>();
	List<ShouyeEntity> mlist1 = new ArrayList<ShouyeEntity>();
	List<ShouyeEntity> mlist2 = new ArrayList<ShouyeEntity>();
	List<ShouyeEntity> mlist3 = new ArrayList<ShouyeEntity>();
	List<ShouyeEntity> mlist4 = new ArrayList<ShouyeEntity>();
	List<ShouyeEntity> mlist5 = new ArrayList<ShouyeEntity>();
	List<ShouyeEntity> mlist6 = new ArrayList<ShouyeEntity>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.tab01,
				null);
		
		img_sousuo = (ImageView) view.findViewById(R.id.sousuo1);
		img_sousuo.setOnClickListener(this);
		//mView = view.findViewById(R.id.content_view);
		
		mPullScrollView = (PullableScrollView) view
				.findViewById(R.id.content_view);
	
		httpUtil = AbHttpUtil.getInstance(getActivity());
		httpUtil.setTimeout(10000);
		getdata();
		initView();

		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.home_new, null);
		// mAdView = (ImageCycleView) view.findViewById(R.id.ad_view);
		ViewGroup group = (ViewGroup) view.findViewById(R.id.viewGroup);
		grid_more = (MyGridView) view.findViewById(R.id.grid_more);
		grid_01 = (MyGridView) view.findViewById(R.id.grid_01);
		grid_02 = (MyGridView) view.findViewById(R.id.grid_02);
		grid_03 = (MyGridView) view.findViewById(R.id.grid_03);
		grid_04 = (MyGridView) view.findViewById(R.id.grid_04);
		grid_05 = (MyGridView) view.findViewById(R.id.grid_05);
		grid_06 = (MyGridView) view.findViewById(R.id.grid_06);

		grid_more.setSelector(new BitmapDrawable());
		grid_01.setSelector(new BitmapDrawable());
		grid_more.setVerticalScrollBarEnabled(false);

		grid_more.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> person, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), Serach_shouye.class);
				int serid = mlistmore.get(position).id;
				String serachid = String.valueOf(serid);
				System.out.println("name=========" + serachid);
				i.putExtra("serachid", serachid);
				startActivityForResult(i, 0);
			}
		});
		grid_01.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> person, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), OrderNew.class);
				int int_code = mlist1.get(position).Code;
				String xq_code = String.valueOf(int_code);
				System.out.println("code=========" + xq_code);
				i.putExtra("shangpingcode", xq_code);
				startActivityForResult(i, 0);
			}
		});

		grid_02.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> person, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), OrderNew.class);
				int int_code = mlist2.get(position).Code;
				String xq_code = String.valueOf(int_code);
				System.out.println("code=========" + xq_code);
				i.putExtra("shangpingcode", xq_code);
				startActivityForResult(i, 0);
			}
		});
		grid_03.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> person, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), OrderNew.class);
				int int_code = mlist3.get(position).Code;
				String xq_code = String.valueOf(int_code);
				System.out.println("code=========" + xq_code);
				i.putExtra("shangpingcode", xq_code);
				startActivityForResult(i, 0);
			}
		});
		grid_04.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> person, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), OrderNew.class);
				int int_code = mlist4.get(position).Code;
				String xq_code = String.valueOf(int_code);
				System.out.println("code=========" + xq_code);
				i.putExtra("shangpingcode", xq_code);
				startActivityForResult(i, 0);
			}
		});
		grid_05.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> person, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), OrderNew.class);
				int int_code = mlist5.get(position).Code;
				String xq_code = String.valueOf(int_code);
				System.out.println("code=========" + xq_code);
				i.putExtra("shangpingcode", xq_code);
				startActivityForResult(i, 0);
			}
		});
		grid_06.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> person, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), OrderNew.class);
				int int_code = mlist6.get(position).Code;
				String xq_code = String.valueOf(int_code);
				System.out.println("code=========" + xq_code);
				i.putExtra("shangpingcode", xq_code);
				startActivityForResult(i, 0);
			}
		});

		// �Ӳ����ļ��л�ȡViewPager������
		pagerLayout = (LinearLayout) view.findViewById(R.id.view_pager_content);
		// ����ViewPager
		adViewPager = new ViewPager(getActivity());
		// ��ȡ��Ļ���������Ϣ
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		// ������Ļ��Ϣ����ViewPager��������Ŀ��
		adViewPager.setLayoutParams(new LayoutParams(dm.widthPixels,
				dm.heightPixels * 1 / 3));
		// ��ViewPager�������õ������ļ���������
		pagerLayout.addView(adViewPager);
		initPageAdapter();
		initCirclePoint(group);

		adViewPager.setAdapter(adapter);
		adViewPager.setOnPageChangeListener(new AdPageChangeListener());

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					if (isContinue) {
						viewHandler.sendEmptyMessage(atomicInteger.get());
						atomicOption();
					}
				}
			}
		}).start();

		mPullScrollView.addView(view);
	}

	private void getdata() {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "home.getproduct");
		params.put("Accesstoken", "");
		params.put("Msg", "");
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						// loading.showLoading();

					}

					@Override
					public void onFinish() {
						System.out.println("111111");

					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						AbToastUtil.showToast(getActivity(), error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONArray data = json.optJSONArray("data");
							// System.out.println("222222222222"+data);
							for (int i = 0; i < data.length(); i++) {
								JSONObject itemObj = data.optJSONObject(i);

								ShouyeEntity se = (ShouyeEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												ShouyeEntity.class);
								mlistmore.add(se);

								JSONArray jsonarray1 = itemObj
										.optJSONArray("data");
								if (i == 0) {
									for (int j = 0; j < jsonarray1.length(); j++) {
										// ShouyeEntity data1 = new
										// ShouyeEntity();
										JSONObject itemObj1 = jsonarray1
												.getJSONObject(j);
										ShouyeEntity data1 = (ShouyeEntity) ParseJSONTools
												.getInstance().fromJsonToJava(
														itemObj1,
														ShouyeEntity.class);
										data1.DisPurchasePrice = itemObj1
												.optInt("DisPurchasePrice");
										mlist1.add(data1);
									}
								} else if (i == 1) {
									for (int j = 0; j < jsonarray1.length(); j++) {
										// ShouyeEntity data1 = new
										// ShouyeEntity();
										JSONObject itemObj2 = jsonarray1
												.getJSONObject(j);
										ShouyeEntity data2 = (ShouyeEntity) ParseJSONTools
												.getInstance().fromJsonToJava(
														itemObj2,
														ShouyeEntity.class);
										data2.DisPurchasePrice = itemObj2
												.optInt("DisPurchasePrice");
										mlist2.add(data2);
									}
								} else if (i == 2) {
									for (int j = 0; j < jsonarray1.length(); j++) {
										// ShouyeEntity data1 = new
										// ShouyeEntity();
										JSONObject itemObj3 = jsonarray1
												.getJSONObject(j);
										ShouyeEntity data3 = (ShouyeEntity) ParseJSONTools
												.getInstance().fromJsonToJava(
														itemObj3,
														ShouyeEntity.class);
										data3.DisPurchasePrice = itemObj3
												.optInt("DisPurchasePrice");
										mlist3.add(data3);
									}
								} else if (i == 3) {
									for (int j = 0; j < jsonarray1.length(); j++) {
										// ShouyeEntity data1 = new
										// ShouyeEntity();
										JSONObject itemObj4 = jsonarray1
												.getJSONObject(j);
										ShouyeEntity data4 = (ShouyeEntity) ParseJSONTools
												.getInstance().fromJsonToJava(
														itemObj4,
														ShouyeEntity.class);
										data4.DisPurchasePrice = itemObj4
												.optInt("DisPurchasePrice");
										mlist4.add(data4);
									}
								} else if (i == 4) {
									for (int j = 0; j < jsonarray1.length(); j++) {
										// ShouyeEntity data1 = new
										// ShouyeEntity();
										JSONObject itemObj5 = jsonarray1
												.getJSONObject(j);
										ShouyeEntity data5 = (ShouyeEntity) ParseJSONTools
												.getInstance().fromJsonToJava(
														itemObj5,
														ShouyeEntity.class);
										data5.DisPurchasePrice = itemObj5
												.optInt("DisPurchasePrice");
										mlist5.add(data5);
									}
								} else if (i == 5) {
									for (int j = 0; j < jsonarray1.length(); j++) {
										// ShouyeEntity data1 = new
										// ShouyeEntity();
										JSONObject itemObj6 = jsonarray1
												.getJSONObject(j);
										ShouyeEntity data6 = (ShouyeEntity) ParseJSONTools
												.getInstance().fromJsonToJava(
														itemObj6,
														ShouyeEntity.class);
										data6.DisPurchasePrice = itemObj6
												.optInt("DisPurchasePrice");
										mlist6.add(data6);
									}
								}

							}
							adapter_grid_01 = new Adapter_home_all(
									getActivity(), mlist1);
							adapter_grid_02 = new Adapter_home_all(
									getActivity(), mlist2);
							adapter_grid_03 = new Adapter_home_all(
									getActivity(), mlist3);
							adapter_grid_04 = new Adapter_home_all(
									getActivity(), mlist4);
							adapter_grid_05 = new Adapter_home_all(
									getActivity(), mlist5);
							adapter_grid_06 = new Adapter_home_all(
									getActivity(), mlist6);

							gridview_adapter = new Adapter_home1(getActivity(),
									mlistmore);
							grid_01.setAdapter(adapter_grid_01);
							//Utility.setGridViewHeightBasedOnChildren(grid_01);
							grid_02.setAdapter(adapter_grid_02);
							//Utility.setGridViewHeightBasedOnChildren(grid_02);
							grid_03.setAdapter(adapter_grid_03);
							//Utility.setGridViewHeightBasedOnChildren(grid_03);
							grid_04.setAdapter(adapter_grid_04);
							//Utility.setGridViewHeightBasedOnChildren(grid_04);
							grid_05.setAdapter(adapter_grid_05);
							//Utility.setGridViewHeightBasedOnChildren(grid_05);
							grid_06.setAdapter(adapter_grid_06);
							//Utility.setGridViewHeightBasedOnChildren(grid_06);

							grid_more.setAdapter(gridview_adapter);
							//Utility.setListViewHeightBasedOnChildren(grid_more);

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});
	}

	private void atomicOption() {
		atomicInteger.incrementAndGet();
		if (atomicInteger.get() > imageViews.length - 1) {
			atomicInteger.getAndAdd(-5);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {

		}
	}

	/*
	 * ÿ���̶�ʱ���л������ͼƬ
	 */
	private final Handler viewHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			adViewPager.setCurrentItem(msg.what);
			super.handleMessage(msg);
		}

	};

	private void initPageAdapter() {
		pageViews = new ArrayList<View>();

		ImageView img1 = new ImageView(getActivity());
		img1.setBackgroundResource(R.drawable.banner1);
		pageViews.add(img1);

		ImageView img2 = new ImageView(getActivity());
		img2.setBackgroundResource(R.drawable.banner2);
		pageViews.add(img2);

		ImageView img3 = new ImageView(getActivity());
		img3.setBackgroundResource(R.drawable.banner3);
		pageViews.add(img3);

		ImageView img4 = new ImageView(getActivity());
		img4.setBackgroundResource(R.drawable.banner4);
		pageViews.add(img4);

		adapter = new AdPageAdapter(pageViews);
	}

	private void initCirclePoint(ViewGroup group) {

		imageViews = new ImageView[pageViews.size()];
		// �������СԲ��ͼ��
		for (int i = 0; i < pageViews.size(); i++) {
			// ����һ��ImageView, �����ÿ��. ���ö�����뵽������
			imageView = new ImageView(getActivity());
			imageView.setLayoutParams(new LayoutParams(10, 10));
			imageViews[i] = imageView;

			// ��ʼֵ, Ĭ�ϵ�0��ѡ��
			if (i == 0) {
				imageViews[i].setBackgroundResource(R.drawable.point_focused);
			} else {
				imageViews[i].setBackgroundResource(R.drawable.point_unfocused);
			}
			// ��СԲ����뵽������
			group.addView(imageViews[i]);
		}
	}

	/**
	 * ViewPager ҳ��ı������
	 */
	private final class AdPageChangeListener implements OnPageChangeListener {

		/**
		 * ҳ�����״̬�����ı��ʱ�򴥷�
		 */
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		/**
		 * ҳ�������ʱ�򴥷�
		 */
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		/**
		 * ҳ��ѡ�е�ʱ�򴥷�
		 */
		@Override
		public void onPageSelected(int arg0) {
			// ��ȡ��ǰ��ʾ��ҳ�����ĸ�ҳ��
			atomicInteger.getAndSet(arg0);
			// ��������ԭ�㲼�ּ���
			for (int i = 0; i < imageViews.length; i++) {
				imageViews[arg0]
						.setBackgroundResource(R.drawable.point_focused);
				if (arg0 != i) {
					imageViews[i]
							.setBackgroundResource(R.drawable.point_unfocused);
				}
			}
		}
	}

	private final class AdPageAdapter extends PagerAdapter {
		private List<View> views = null;

		/**
		 * ��ʼ������Դ, ��View����
		 */
		public AdPageAdapter(List<View> views) {
			this.views = views;
		}

		/**
		 * ��ViewPager��ɾ�������ж�Ӧ������View����
		 */
		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}

		/**
		 * ��ȡViewPager�ĸ���
		 */
		@Override
		public int getCount() {
			return views.size();
		}

		/**
		 * ��View�����л�ȡ��Ӧ������Ԫ��, ����ӵ�ViewPager��
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			((ViewPager) container).addView(views.get(position), 0);
			return views.get(position);
		}

		/**
		 * �Ƿ���ʾ��ViewPagerҳ����instantiateItem���صĶ�����й��� ��������Ǳ���ʵ�ֵ�
		 */
		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.sousuo1:
			Intent sintent = new Intent();
			sintent.setClass(getActivity(), Serach_shouye.class);

			startActivityForResult(sintent, 0);

			break;

		default:
			break;
		}
	}
}
