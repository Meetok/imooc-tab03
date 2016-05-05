package com.Meetok.Activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.DingDanEntity;
import com.Meetok.Entity.OrderEntity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.View.MyListView;
import com.Meetok.adapter.Adapter_order;
import com.Meetok.adapter.Adapter_orderqueren;
import com.Meetok.fragment.HomeFragment;
import com.Meetok.fragment.Pur.AlreadyPaid_P;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.alipay.sdk.pay.PayDemoActivity;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderQueRen extends Activity implements OnClickListener {

	private ImageView button_back;
	private ImageView fanhui;
	private TextView sum_zongjia;
	private TextView submit_order;
	private String order_guid;
	private ListView myListView;
	private AbHttpUtil httpUtil = null;
	private float sum;
	private static String BASE_ACCESS;
	
	private Adapter_orderqueren adapter_orderqueren;
	List<DingDanEntity> mlistdd = new ArrayList<DingDanEntity>();
	protected String zfb_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_queren);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		View view = LayoutInflater.from(OrderQueRen.this).inflate(
				R.layout.order_queren, null);

		button_back = (ImageView) findViewById(R.id.button_back);
		fanhui = (ImageView) findViewById(R.id.fanhui);

		button_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				OrderQueRen.this.finish();
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});

		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(OrderQueRen.this, HomeFragment.class));
			}
		});
		 BASE_ACCESS = LogActivity.loadDataFromLocalXML(OrderQueRen.this, "accesstoken");
		Intent intent = getIntent();
		order_guid = intent.getStringExtra("ordernew_guid");
		
		// System.out.println("sddddsssddssd"+order_guid);
		String msgs = "{" + "\"" +"guids" +"\"" + ":" + "\""+ order_guid +"\""+ "}";
		
		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(10000);
		

		getdata(msgs);
		getinit();
	}

	private float getdata(String msgs) {
		// {"code":"success","data":{"accesstoken":"44f92642-0794-4a57-9f18-ee7860da02d0","Name":"ayumizrw"}}
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "order.createorderfromcart");
		params.put("Accesstoken",BASE_ACCESS);

		params.put("Msg", msgs);
		// params.put("Msg", "code","code_order");
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						// loading.showLoading();

					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						AbToastUtil.showToast(OrderQueRen.this,
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							
							JSONObject data = json.optJSONObject("data");
							
							// JSONArray data = json_main.optJSONArray("main");
							JSONArray pro = data.optJSONArray("product");

							JSONArray red = data.optJSONArray("redpaper");
							for (int i = 0; i < pro.length(); i++) {
								JSONObject itemObj = pro.optJSONObject(i);

								DingDanEntity pr = (DingDanEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												DingDanEntity.class);
								pr.Title = itemObj.optString("Title");
								pr.ProductPic = itemObj.optString("ProductPic");
								pr.DisPurchasePrice = itemObj
										.optInt("DisPurchasePrice");
								pr.AllPrice = itemObj.getString("AllPrice");
								pr.Num = itemObj.optInt("Num");
								
								zfb_title = pr.Title;
								float ap = Float.parseFloat(pr.AllPrice);
								sum  =sum+ap;
								System.out.println("sum=="+sum);
								mlistdd.add(pr);
							}
							
							for (int j = 0; j < red.length(); j++) {
								JSONObject redObj = red.optJSONObject(j);
								DingDanEntity re = (DingDanEntity) ParseJSONTools
										.getInstance().fromJsonToJava(redObj,
												DingDanEntity.class);
								// oimg.ProductPic = oimg.;
								re.Name = redObj.optString("Name");
							}
							
							String sum1 = String.valueOf(sum);
							sum_zongjia.setText(sum1);
							adapter_orderqueren = new Adapter_orderqueren(OrderQueRen.this,
									mlistdd);
							myListView.setAdapter(adapter_orderqueren);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});
		
		return sum;
	}

	private void getinit() {
		// TODO Auto-generated method stub

		sum_zongjia = (TextView) findViewById(R.id.sum_zongjia);
		submit_order = (TextView) findViewById(R.id.submit_order);
		myListView =  (ListView) findViewById(R.id.mylist_order);
		
		submit_order.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submit_order:
			
			Intent intent = new Intent();
			intent.setClass(OrderQueRen.this, PayDemoActivity.class);
			String guids = order_guid;			
			String sum_z =String.valueOf(sum);//�ܼ۸�
			System.out.println("guids-------------" + guids);
			
			intent.putExtra("zfb_title", zfb_title);
			intent.putExtra("order_guid", guids);
			intent.putExtra("zongjiage", sum_z);
			startActivityForResult(intent, 0);
			break;

		default:
			break;
		}
	}
}
