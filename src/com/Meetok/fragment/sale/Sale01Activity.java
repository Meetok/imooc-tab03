package com.Meetok.fragment.sale;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Meetok.Activity.LogActivity;
import com.Meetok.Activity.OrderQueRen;
import com.Meetok.Activity.Sale_xiangqing;
import com.Meetok.Activity.Serach_shouye;
import com.Meetok.Custom.CustomDialog;
import com.Meetok.Entity.OrderEntity;
import com.Meetok.Entity.ParseJSONTools;
import com.Meetok.Entity.PurchaseEntity;
import com.Meetok.Entity.SaleEntity;
import com.Meetok.Tab.MainActivity;
import com.Meetok.adapter.Adapter_already_zhu;
import com.Meetok.adapter.Adapter_sale01;
import com.Meetok.adapter.Adapter_search;
import com.ab.http.AbHttpUtil;
import com.ab.http.AbRequestParams;
import com.ab.http.AbStringHttpResponseListener;
import com.ab.util.AbToastUtil;
import com.imooc.tab03.R;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class Sale01Activity extends Fragment{
	
	private static AbHttpUtil httpUtil = null;
	private TextView sousuo_01;
	private ListView shou_listView;
	private EditText EditText_1;
	private EditText EditText_2;
	private String msg_s;
	public String Status_sale ;
	public int yema_sale;
	private Adapter_sale01 adapter_sale01;
	List<SaleEntity> list_sale1 = new ArrayList<SaleEntity>();
	
	private View mFooterView;
	private Handler handler = new Handler();
	public int lastItem;
	private RelativeLayout loading;
	private static String BASE_ACCESS;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.layout_1, null);
		
		httpUtil = AbHttpUtil.getInstance(getActivity());
		httpUtil.setTimeout(10000);
		BASE_ACCESS = LogActivity.loadDataFromLocalXML(getActivity(), "accesstoken");
		
		initview(view);
		getdata(msg_s);
		return view;
	}

	private String getmsg() {
		//订单状态   空：所有   dfshg：待发送海关、dhwfx：待货物放行、thdcl: 退回待处理、fhljsb：发货拦截失败
		String edit_A = EditText_1.getText().toString().trim();
		String edit_B = EditText_2.getText().toString().trim();
		 msg_s = "\""+"HGStatus"+"\""+":"+"\""+"dfshg"+"\""+","+"\""+"searchparaA"+"\""+":"+"\""+edit_A+"\""+","+"\""+"searchProduct"+"\""+":"+"\""+edit_B+"\"";
	return msg_s;
	}

	private void initview(View view) {
		// TODO Auto-generated method stub
		mFooterView = LayoutInflater.from(getActivity()).inflate(
				R.layout.listview_footer, null);
		loading = (RelativeLayout) mFooterView.findViewById(R.id.loading);
		
		shou_listView = (ListView) view.findViewById(R.id.layout1_list);
		EditText_1= (EditText) view.findViewById(R.id.sou_1);
		EditText_2 = (EditText) view.findViewById(R.id.sou_2);
		sousuo_01 = (TextView) view.findViewById(R.id.sousuo_01);
		sousuo_01.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				list_sale1.clear();
				getdata(msg_s);
			}
		});
		// 添加listview滚动监听
		shou_listView.setOnScrollListener(new OnScrollListener() {
			// AbsListView view 这个view对象就是listview
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:
					System.out.println("已经停止：SCROLL_STATE_IDLE");
					if (view.getLastVisiblePosition() == view.getCount() - 1) {
						System.out.println("页码" + Status_sale + "总商品数"
								+ yema_sale);
						if (Status_sale.equals("F")) {
							System.out.println("listview滑动判断当前页码"
									+ yema_sale);
							loadData();

						}
					}
					break;
				case OnScrollListener.SCROLL_STATE_FLING:
					System.out.println("开始滚动：SCROLL_STATE_FLING");
					break;
				case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
					System.out.println("正在滚动：SCROLL_STATE_TOUCH_SCROLL");
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lastItem = firstVisibleItem + visibleItemCount - 1;
			}
		});
	}

	protected void loadData() {
		loading.setVisibility(View.VISIBLE);
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				loadmore();
				loading.setVisibility(View.GONE);
				adapter_sale01.notifyDataSetChanged();
			}
		}, 5000);
	}
	protected void loadmore() {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "sendlist.sendlist");
		params.put("Accesstoken", BASE_ACCESS);
		String editmsg = getmsg();
		int ysma = yema_sale+1;
		String msgsearch = "{" + "\"" + "actpage" + "\"" + ":" + "\"" + ysma + "\""+ ","+editmsg+"}";
		params.put("Msg", msgsearch);

		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						AbToastUtil.showToast(getActivity(),
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							
							JSONObject data = json.optJSONObject("data");
							
							Object actpage_a = data.opt("actpage");
							String yema = String.valueOf(actpage_a);
							yema_sale = Integer.parseInt(yema);
							Object Status = data.opt("Status");
							Status_sale = String.valueOf(Status);
							
							
							JSONArray data1 = data.optJSONArray("data");
							
								for (int i = 0; i < data1.length(); i++) {
									JSONObject itemObj = data1.optJSONObject(i);

									SaleEntity s1 = (SaleEntity) ParseJSONTools
											.getInstance().fromJsonToJava(
													itemObj, SaleEntity.class);
									s1.Tid = itemObj.optString("Tid");
									s1.Created = itemObj.optString("Created");
									s1.ReceiverName = itemObj.optString("ReceiverName");
                                    s1.SalesOrderStatus = itemObj.optString("SalesOrderStatus");
									JSONArray jsonarray1 = itemObj.optJSONArray("dataitem");
									s1.itemarray1 = jsonarray1;
									
									list_sale1.add(s1);
								}
								
								adapter_sale01 = new Adapter_sale01(
										getActivity(), list_sale1);
								shou_listView.setAdapter(adapter_sale01);
						
							// adapter_search.notifyDataSetChanged();
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

	private void getdata(String msg_s) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "sendlist.sendlist");
		params.put("Accesstoken",BASE_ACCESS);
		String editmsg = getmsg();
		String msgsearch = "{" + "\"" + "actpage" + "\"" + ":" + "\"" + "1" + "\""+ ","+editmsg+"}";
		params.put("Msg", msgsearch);

		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						AbToastUtil.showToast(getActivity(),
								error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							Object co = json.opt("code");
							String mcode = String.valueOf(co);//session lose errmsg
							if(mcode.equals("session lose")){
								Object er = json.opt("errmsg");
								String errmsg = String.valueOf(er);
								CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());  
							    builder.setMessage(errmsg);  
							    builder.setTitle("提示");  
							    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
							        public void onClick(DialogInterface dialog, int which) { 
							        	Intent intent = new Intent(getActivity(),LogActivity.class);
							        	startActivityForResult(intent, 0);							        	
							        	
							        }  
							    });  
							    builder.setNegativeButton("取消",  
							            new android.content.DialogInterface.OnClickListener() {  
							                public void onClick(DialogInterface dialog, int which) {  
							                	Intent intent = new Intent(getActivity(),MainActivity.class);
									        	startActivityForResult(intent, 0);	
							                    dialog.dismiss();  
							                    getActivity().finish();
							                }  
							            });  

							    builder.create().show(); 
 
							}else{
							JSONObject data = json.optJSONObject("data");
							
							Object actpage_a = data.opt("actpage");
							String yema = String.valueOf(actpage_a);
							yema_sale = Integer.parseInt(yema);
							Object Status = data.opt("Status");
							Status_sale = String.valueOf(Status);
							
							
							JSONArray data1 = data.optJSONArray("data");
							
								for (int i = 0; i < data1.length(); i++) {
									JSONObject itemObj = data1.optJSONObject(i);

									SaleEntity s1 = (SaleEntity) ParseJSONTools
											.getInstance().fromJsonToJava(
													itemObj, SaleEntity.class);
									s1.Tid = itemObj.optString("Tid");
									s1.Created = itemObj.optString("Created");
									s1.ReceiverName = itemObj.optString("ReceiverName");
                                    s1.SalesOrderStatus = itemObj.optString("SalesOrderStatus");
									JSONArray jsonarray1 = itemObj.optJSONArray("dataitem");
									s1.itemarray1 = jsonarray1;
									
									list_sale1.add(s1);
								}
							}
								adapter_sale01 = new Adapter_sale01(
										getActivity(), list_sale1);
								shou_listView.setAdapter(adapter_sale01);
						
							// adapter_search.notifyDataSetChanged();
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
	 * 2、销售订单发送接口
	 * @param iDID
	 * @param context 
	 */

	public static void getfasong(String iDID, final Context context) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "sendlist.sendtohg");
		params.put("Accesstoken", BASE_ACCESS);
		
		String msg = "{" + "\"" + "id" + "\"" + ":" + "\"" + iDID + "\""+"}";
		params.put("Msg", msg);

		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						//AbToastUtil.showToast(Sale01Activity.this,error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							JSONObject data = json.optJSONObject("data");
							Object statuscode = data.opt("statuscode");
							String scode = String.valueOf(statuscode);
							Object money = data.opt("money");
							String my = String.valueOf(money);
							Object msg = data.opt("msg");
							String allmsg = String.valueOf(msg);
							if(scode.equals("2")){
								System.out.println("提示缺少金额:"+my+"元");
								CustomDialog.Builder builder = new CustomDialog.Builder(context);  
							    builder.setMessage("提示缺少金额:"+my+"元");  
							    builder.setTitle("http://m.meetok.com");  
							    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
							        public void onClick(DialogInterface dialog, int which) { 

							            dialog.dismiss(); 
							        }  
							    });  
							    builder.create().show();
								
							}else{
							System.out.println("提示:"+allmsg);
							CustomDialog.Builder builder = new CustomDialog.Builder(context);  
						    builder.setMessage("提示:"+allmsg);  
						    builder.setTitle("http://m.meetok.com");  
						    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
						        public void onClick(DialogInterface dialog, int which) { 

						            dialog.dismiss(); 
						        }  
						    });  
						    builder.create().show();
							
							}
							// adapter_search.notifyDataSetChanged();
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
	 * 销售订单作废
	 * @param position
	 * @param zhuid
	 */

	public static void zuofei(int position, String zhuid) {
		// TODO Auto-generated method stub
		AbRequestParams params = new AbRequestParams();
		params.put("Method", "sendlist.cancelsalesorder");
		params.put("Accesstoken",BASE_ACCESS);
		
		String msg = "{" + "\"" + "id" + "\"" + ":" + "\"" + zhuid + "\""+"}";
		params.put("Msg", msg);

		httpUtil.post(com.Meetok.config.Config.F_BASE_URL, params,
				new AbStringHttpResponseListener() {

					@Override
					public void onStart() {
						
					}

					@Override
					public void onFinish() {

					}

					@Override
					public void onFailure(int statusCode, String content,
							Throwable error) {
						//AbToastUtil.showToast(Sale01Activity.this,error.getMessage());
					}

					@Override
					public void onSuccess(int statusCode, String content) {
						// AbToastUtil.showToast(MainActivity.this, content);
						try {
							JSONObject json = new JSONObject(content);
							Object del = json.opt("data");
							String delete = (String) del;
							System.out.println("销售订单作废接口--------" + delete);
							// adapter_search.notifyDataSetChanged();
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
	public void onSaveInstanceState(Bundle outState) {//防止重叠
	// TODO Auto-generated method stub
	//super.onSaveInstanceState(outState);
	}


}
