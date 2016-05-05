package com.Meetok.Activity;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.OrderEntity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.Tab.ImmersionBar;
import com.Meetok.Tab.MainActivity;
import com.Meetok.Util.StringUtils;
import com.Meetok.View.MyGridView;
import com.Meetok.adapter.Adapter_home1;
import com.Meetok.adapter.Adapter_home_all;
import com.Meetok.adapter.Adapter_order;
import com.Meetok.fragment.HomeFragment;
import com.Meetok.fragment.ShoppingCartFragment;
import com.Meetok.fragment.Pur.AlreadyPaid_P;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;
import com.imooc.tab03.R.id;
import com.jingchen.pulltorefresh.pullableview.MyListener;
import com.jingchen.pulltorefresh.pullableview.PullToRefreshLayout;
import com.jingchen.pulltorefresh.pullableview.PullableScrollView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.R.integer;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderNew extends Activity implements OnClickListener,
		OnItemClickListener {

	private ImageView xq_ProductPic;
	private TextView xq_title;
	private TextView xq_name;
	private TextView xq_DisPurchasePrice;
	private TextView xq_RetailPrice;
	private TextView xq_huoli;
	private TextView xq_Weight;
	private TextView xq_SellNum;
	private TextView xq_goumai;// 购买
	private TextView xq_gouwuche;// 购物车ﳵ
	private TextView xq_addgwc;// 加入购物车ﳵ
	private ImageView icon, left, right;
	private ImageView shouye, button;
	private EditText count;
	private LinearLayout xq_tuwen;
	public static String addcount;
	public String text;
	public String str_code;
	private MyGridView grid_xq;
	private Adapter_order adapter_grid_xq;
	private PullableScrollView mPullScrollView;
	private AbHttpUtil httpUtil = null;
	List<OrderEntity> mlistxq = new ArrayList<OrderEntity>();
	private String guid;
	private String adddate;
	private static String BASE_ACCESS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commodity);
		ImmersionBar.setImmersionBar(this, R.color.statusbar_bg);
		View view = LayoutInflater.from(OrderNew.this).inflate(
				R.layout.commodity, null);
		mPullScrollView = (PullableScrollView) findViewById(R.id.content_view);
		

		button = (ImageView) findViewById(R.id.button_back);
		shouye = (ImageView) findViewById(R.id.shouye);
		button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				OrderNew.this.finish();
				overridePendingTransition(R.anim.slide_right_in,
						R.anim.slide_right_out);
			}
		});
		shouye.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// startActivity(new Intent(OrderNew.this,HomeFragment.class));
				Intent i = new Intent(OrderNew.this, MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivityForResult(i, RESULT_OK);
				// OrderNew.this.finish();
			}
		});
		BASE_ACCESS = LogActivity.loadDataFromLocalXML(OrderNew.this,
				"accesstoken");

		Intent intent = getIntent();
		String code_order = intent.getStringExtra("shangpingcode");
		str_code = "{" + "\"code\"" + ":" + "\"" + code_order + "\"" + "}";

		httpUtil = AbHttpUtil.getInstance(this);
		httpUtil.setTimeout(10000);

		getdata(str_code);

		initview();
	}

	private void initview() {
		// TODO Auto-generated method stub
		// View view = View.inflate(OrderNew.this, R.layout.commodity_new,

		xq_ProductPic = (ImageView) findViewById(R.id.xiangqing_pic);
		xq_title = (TextView) findViewById(R.id.xq_title);
		xq_name = (TextView) findViewById(R.id.xq_name);
		xq_DisPurchasePrice = (TextView) findViewById(R.id.xq_DisPurchasePrice);
		xq_RetailPrice = (TextView) findViewById(R.id.xq_RetailPrice);
		xq_huoli = (TextView) findViewById(R.id.xq_huoli);
		xq_Weight = (TextView) findViewById(R.id.xq_Weight);
		xq_SellNum = (TextView) findViewById(R.id.xq_SellNum);// count
		grid_xq = (MyGridView) findViewById(R.id.grid_xq);
		left = (ImageView) findViewById(R.id.left);
		right = (ImageView) findViewById(R.id.right);
		count = (EditText) findViewById(R.id.input);
		xq_tuwen = (LinearLayout) findViewById(R.id.xq_tuwen);
		xq_goumai = (TextView) findViewById(R.id.xq_goumai);
		xq_gouwuche = (TextView) findViewById(R.id.gwc);
		xq_addgwc = (TextView) findViewById(R.id.add_gwc);
		addcount = "2";
		count.setText(addcount);
		count.clearFocus();
		text = xq_SellNum.getText().toString().trim();

		left.setOnClickListener(this);
		right.setOnClickListener(this);
		xq_tuwen.setOnClickListener(this);
		xq_goumai.setOnClickListener(this);
		xq_gouwuche.setOnClickListener(this);
		xq_addgwc.setOnClickListener(this);
		count.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			public void afterTextChanged(Editable s) {
				String text = xq_SellNum.getText().toString().trim();
				int num = Integer.parseInt(text);
				if (!StringUtils.isEmpty(s.toString())) {
					if (Integer.parseInt(s.toString()) > num) {
						Toast toast = Toast.makeText(OrderNew.this, "商品起批量为2件",
								Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						count.setText(num);
					}
				}
			}
		});

		grid_xq.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> person, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i = new Intent(OrderNew.this, OrderNew.class);
				int int_code = mlistxq.get(position).Code;
				String xq_code = String.valueOf(int_code);
				System.out.println("code=========" + xq_code);
				i.putExtra("shangpingcode", xq_code);
				startActivityForResult(i, 0);
			}
		});

	}

	private String getdata(String str_code) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "product.getproductinfo");
		params.put("Accesstoken", "");

		params.put("Msg", str_code);
		// params.put("Msg", "code","code_order");
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

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
						AbToastUtil.showToast(OrderNew.this, error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);

							JSONObject json_main = json.optJSONObject("data");
							// JSONArray data = json_main.optJSONArray("main");
							JSONArray img = json_main.optJSONArray("relation");

							JSONArray data = json_main.optJSONArray("main");
							for (int i = 0; i < data.length(); i++) {
								JSONObject itemObj = data.optJSONObject(i);

								OrderEntity or = (OrderEntity) ParseJSONTools
										.getInstance().fromJsonToJava(itemObj,
												OrderEntity.class);
								or.Title = itemObj.optString("Title");
								or.ProductPic = itemObj.optString("ProductPic");
								or.name = itemObj.optString("Name");
								or.DisPurchasePrice = itemObj
										.optInt("DisPurchasePrice");
								or.RetailPrice = itemObj.optInt("RetailPrice");
								Float huoli = or.RetailPrice
										- or.DisPurchasePrice;
								or.Weight = itemObj.optString("Weight");
								or.SellNum = itemObj.optString("SellNum");
								or.GUID = itemObj.getString("GUID");

								guid = or.GUID;
								String DisPurchasePrice = "￥"
										+ String.valueOf(or.DisPurchasePrice);
								String RetailPrice = "￥"
										+ String.valueOf(or.RetailPrice);
								String d_huoli = "￥" + String.valueOf(huoli);
								String weight = String.valueOf(or.Weight)
										+ "kg";

								displayImage(or.ProductPic, xq_ProductPic);
								xq_title.setText(or.Title);
								xq_name.setText(or.name);
								xq_DisPurchasePrice.setText(DisPurchasePrice);
								xq_RetailPrice.setText(RetailPrice);
								xq_huoli.setText(d_huoli);
								xq_Weight.setText(weight);
								xq_SellNum.setText(or.SellNum);

							}
							for (int j = 0; j < img.length(); j++) {
								JSONObject imgObj = img.optJSONObject(j);
								OrderEntity oimg = (OrderEntity) ParseJSONTools
										.getInstance().fromJsonToJava(imgObj,
												OrderEntity.class);
								// oimg.ProductPic = oimg.;
								oimg.Title = imgObj.optString("Title");
								oimg.DisPurchasePrice = imgObj
										.optInt("DisPurchasePrice");
								mlistxq.add(oimg);
							}
							adapter_grid_xq = new Adapter_order(OrderNew.this,
									mlistxq);
							grid_xq.setAdapter(adapter_grid_xq);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				});
		return guid;
	}

	public void displayImage(String imageURL, ImageView imageView) {
		ImageLoader.getInstance().displayImage(imageURL, imageView);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.xq_tuwen:
			Intent intent1 = new Intent();
			intent1.setClass(OrderNew.this, TuWen_xiangqing.class);
			String xq_code = str_code;
			System.out.println("tuwen-------------" + xq_code);
			intent1.putExtra("tuwenxq_code", xq_code);
			startActivityForResult(intent1, 0);
			break;
		case R.id.left:
			int i = Integer.parseInt(addcount);
			// if(i==2){
			// addcount=String.valueOf(2);
			// }else
			if (i > 2) {
				i--;
				addcount = String.valueOf(i);
			} else if (i <= 2) {
				CustomDialog.Builder builder = new CustomDialog.Builder(this);
				builder.setMessage("采购起拍数量2件");
				builder.setTitle("起拍量不足");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						});
				builder.create().show();
				count.setText("2");
			}
			count.setText(addcount);
			break;
		case R.id.right:
			int i1 = Integer.parseInt(addcount);

			i1++;
			addcount = String.valueOf(i1);

			count.setText(addcount);
			break;
		case R.id.xq_goumai:

			BASE_ACCESS = LogActivity.loadDataFromLocalXML(OrderNew.this,
					"accesstoken");
			System.out.println(BASE_ACCESS);
			if (BASE_ACCESS == "") {
				CustomDialog.Builder builder = new CustomDialog.Builder(
						OrderNew.this);
				builder.setMessage("请先登录再操作");
				builder.setTitle("http://m.meetok.com");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(OrderNew.this,
										LogActivity.class);
								// startActivity(intent);
								OrderNew.this.finish();
								startActivityForResult(intent, 0);

								dialog.dismiss();
							}
						});
				builder.create().show();
			} else {
				String num = count.getText().toString();
				addgwc2(guid, num);
				Intent intent3 = new Intent();
				intent3.setClass(OrderNew.this, OrderQueRen.class);
				String guids = guid;
				System.out.println("guids-------------" + guids);
				intent3.putExtra("ordernew_guid", guids);
				startActivityForResult(intent3, 0);
			}
			break;
		case R.id.add_gwc:
			// Intent intent4 = new Intent();
			String shul = count.getText().toString().trim();
			String shu = "\"" + shul + "\"";
			// 加入购物车ﳵ
			addgwc(guid, shu);
			break;
		case R.id.gwc:
			Intent intent4 = new Intent();
			intent4.setClass(OrderNew.this, ShoppingCart2.class);
			startActivityForResult(intent4, 0);
			overridePendingTransition(R.anim.slide_right_in,
					R.anim.slide_right_out);
			break;
		default:
			break;
		}
	}

	/**
	 * 加入购物车ﳵ
	 * 
	 * @param guid2
	 * @param shul
	 */
	private void addgwc(String guid2, String shul) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "product.putintocart");
		params.put("Accesstoken", BASE_ACCESS);
		String msg = "{" + "\"" + "guid" + "\"" + ":" + "\"" + guid2 + "\""
				+ "," + "\"" + "num" + "\"" + ":" + shul + "}";
		params.put("Msg", msg);
		// params.put("Msg", "code","code_order");
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

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
						AbToastUtil.showToast(OrderNew.this, error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							Object ret = json.opt("data");
							adddate = (String) ret;

							Toast.makeText(getBaseContext(), adddate,
									Toast.LENGTH_SHORT).show();
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
	/**
	 * 加入购物车,不显示弹窗ﳵ
	 * 
	 * @param guid2
	 * @param shul
	 */
	private void addgwc2(String guid2, String shul) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();

		params.put("Method", "product.putintocart");
		params.put("Accesstoken", BASE_ACCESS);
		String msg = "{" + "\"" + "guid" + "\"" + ":" + "\"" + guid2 + "\""
				+ "," + "\"" + "num" + "\"" + ":" + shul + "}";
		params.put("Msg", msg);
		// params.put("Msg", "code","code_order");
		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

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
						AbToastUtil.showToast(OrderNew.this, error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							Object ret = json.opt("data");
							adddate = (String) ret;

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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub haishi

	}
}
