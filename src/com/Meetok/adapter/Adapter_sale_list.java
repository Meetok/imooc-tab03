package com.Meetok.adapter;

import java.util.List;

import com.Meetok.Activity.AddSaleOrder;
import com.Meetok.Entity.SaleXQEntity;
import com.Meetok.adapter.Adapter_sale_grid.HolderView;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_sale_list extends BaseAdapter {
	private Context context;
	private List<SaleXQEntity> mlist;

	public Adapter_sale_list(Context context, List<SaleXQEntity> list) {

		this.context = context;
		this.mlist = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View currentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		final HolderView holderView;
		if (currentView == null) {
			holderView = new HolderView();
			currentView = LayoutInflater.from(context).inflate(
					R.layout.adapter_sale_list, null);
			// holderView.iv_ProductPic=(ImageView)
			// currentView.findViewById(R.id.iv_adapter_grid_ProductPic);
			holderView.iv_title = (TextView) currentView
					.findViewById(R.id.sale_title);
			holderView.iv_price = (TextView) currentView
					.findViewById(R.id.iv_sale_jiage);
			holderView.iv_kucun = (TextView) currentView
					.findViewById(R.id.iv_sale_kucuen);
			holderView.iv_left = (ImageView) currentView
					.findViewById(R.id.left);
			holderView.iv_right = (ImageView) currentView
					.findViewById(R.id.right);
			holderView.iv_input = (TextView) currentView
					.findViewById(R.id.input);
			holderView.iv_sale_jiage = (EditText) currentView
					.findViewById(R.id.iv_adapter_jiage);
			holderView.iv_sanchu = (TextView) currentView
					.findViewById(R.id.sale_sanchu);
			// holderView.iv_ProductPic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			currentView.setTag(holderView);
		} else {
			holderView = (HolderView) currentView.getTag();
		}
		SaleXQEntity da = mlist.get(position);
		int num = da.StockQuantity;
		//int num = (int) num1;
		double prive = da.DisPurchasePrice;

		String numm = String.valueOf(num);
		holderView.iv_input.setText(numm);
		AddSaleOrder.Changejiagejian(num, prive);

		holderView.iv_title.setText(da.Title);
		holderView.iv_price.setText(String.valueOf(da.DisPurchasePrice));
		holderView.iv_kucun.setText(String.valueOf(da.StockQuantity));
		holderView.iv_sale_jiage.setText(String.valueOf(da.DisPurchasePrice));
		// displayImage(data1.ProductPic,holderView.iv_ProductPic);
		holderView.iv_left.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 起始的订购数量，----------有问题
				
				int num = mlist.get(position).StockQuantity;
				double prive = mlist.get(position).DisPurchasePrice;
				if (num != 0) {
					num = num - 1;
					String numm = String.valueOf(num);
					holderView.iv_input.setText(numm);
					AddSaleOrder.Changejiagejian(num, prive);
				}
			}
		});
		holderView.iv_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 起始的订购数量，----------有问题
				int num = mlist.get(position).StockQuantity;
				double prive = mlist.get(position).DisPurchasePrice;
				if (num != 0) {
					num = num + 1;
					String numm = String.valueOf(num);
					holderView.iv_input.setText(numm);
					AddSaleOrder.Changejiagejian(num, prive);
				}
			}
		});
		holderView.iv_sanchu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				mlist.remove(position);
				AddSaleOrder.getsanchu();
				Adapter_sale_list.this.notifyDataSetChanged();
			}
		});
		
		return currentView;
	}

	public class HolderView {

		private TextView iv_title;
		private TextView iv_price;
		private TextView iv_kucun;
		private EditText iv_sale_jiage;
		private ImageView iv_left;
		private ImageView iv_right;
		private TextView iv_input;
		private TextView iv_sanchu;
	}
}
