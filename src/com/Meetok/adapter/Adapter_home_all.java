package com.Meetok.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.Meetok.Entity.ShouyeEntity;
import com.Meetok.adapter.Adapter_home1.HolderView;
import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_home_all extends BaseAdapter {

	private Context context;
	private List<ShouyeEntity> mlist;
	
	
	public Adapter_home_all(Context context,List<ShouyeEntity> list){
		
		this.context=context;
		this.mlist=list;
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
			if (currentView==null) {
				holderView=new HolderView();
				currentView=LayoutInflater.from(context).inflate(R.layout.adapter_home_all, null);
				holderView.iv_ProductPic=(ImageView) currentView.findViewById(R.id.iv_adapter_grid_ProductPic);
				holderView.iv_title = (TextView) currentView.findViewById(R.id.iv_adapter_grid_title);
				holderView.iv_price = (TextView) currentView.findViewById(R.id.iv_adapter_jiage);
				holderView.iv_sell = (TextView) currentView.findViewById(R.id.iv_adapter_kucuen);
				holderView.iv_ProductPic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				currentView.setTag(holderView);
			}else {
				holderView=(HolderView) currentView.getTag();
			}
			ShouyeEntity data1=mlist.get(position);
			String title = data1.Title;
			//String price = se.DisPurchasePrice;
			float price = data1.DisPurchasePrice;
			int sell = data1.SellNum;
			String p = String.valueOf(price);
			String s = String.valueOf(sell);
			//
			holderView.iv_title.setText(title);
			holderView.iv_price.setText(p);
			holderView.iv_sell.setText(s);
			//displayImage(data1.ProductPic,holderView.iv_ProductPic);
			//显示图片的配置  
	        DisplayImageOptions options = new DisplayImageOptions.Builder()  
	                //.showImageOnLoading(R.drawable.app_logo)  
	                //.showImageOnFail(R.drawable.icon_error)  
	                .cacheInMemory(true)  
	                .cacheOnDisk(true)  
	                .bitmapConfig(Bitmap.Config.RGB_565)  
	                .imageScaleType(ImageScaleType.EXACTLY)//
	                .build();  
	          
	        ImageLoader.getInstance().displayImage(data1.ProductPic, holderView.iv_ProductPic, options); 
			/*
	        ImageLoader.getInstance().loadImage(data1.ProductPic,  new SimpleImageLoadingListener() {  
			        @Override  
			        public void onLoadingStarted(String imageUri, View view) {  
			        	
			        }  

			        @Override  
			        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {  

			        }  

			        @Override  
			        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) { 
			        	Bitmap bmp = loadedImage;
			        	/*WindowManager wm = (WindowManager) context
					               .getSystemService(Context.WINDOW_SERVICE);
					       @SuppressWarnings("deprecation")
						   int screenWidth = wm.getDefaultDisplay().getWidth();
					       ViewGroup.LayoutParams lp = holderView.iv_ProductPic.getLayoutParams();
					       lp.width=screenWidth/2;				      
					       lp.height=(int)(screenWidth * bmp.getHeight() / bmp.getWidth())/2;  				     				    	  		       
					       holderView.iv_ProductPic.setLayoutParams(lp);
					      
				       holderView.iv_pic.setMaxWidth(screenWidth);
				       holderView.iv_pic.setMaxHeight(screenWidth * 10);
				       holderView.iv_pic.setImageBitmap(loadedImage);
					       *
					       
			        	//comp(bmp);
					       holderView.iv_ProductPic.setImageBitmap(comp(bmp));
				       
				       
			        }  
			    }); */
		return currentView;
	}
	private Bitmap comp(Bitmap image) {  
	      
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	    if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
	        baos.reset();//重置baos即清空baos  
	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中  
	    }  
	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	    //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
	    newOpts.inJustDecodeBounds = true;  
	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    newOpts.inJustDecodeBounds = false;  
	    int w = newOpts.outWidth;  
	    int h = newOpts.outHeight;  
	    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
	    float hh = 800f;//这里设置高度为800f  
	    float ww = 480f;//这里设置宽度为480f  
	    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
	    int be = 1;//be=1表示不缩放  
	    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
	        be = (int) (newOpts.outWidth / ww);  
	    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
	        be = (int) (newOpts.outHeight / hh);  
	    }  
	    if (be <= 0)  
	        be = 1;  
	    newOpts.inSampleSize = be;//设置缩放比例  
	    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
	    isBm = new ByteArrayInputStream(baos.toByteArray());  
	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
	}  
	private Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    } 
	public void displayImage(String imageURL, ImageView imageView) {
		ImageLoader.getInstance().displayImage(imageURL, imageView);
	}
	public class HolderView {
		
		private ImageView iv_ProductPic;
		private TextView  iv_title;
		private TextView iv_price;
		private TextView iv_sell;
		
		
	}
}
