package com.Meetok.Entity;

import java.io.Serializable;

public class OrderEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String typepic;
	public int TypeId;
	/**
	 * ��Ʒ��������
	 */
	public String name;	
	
	public int Stock;
	/**
	 * ��Ʒ����
	 */
	public String Title;
	/**
	 * ��ƷͼƬ
	 */
	public String ProductPic;
	/**
	 * �����ͼ
	 */
	
	public String Description;
	/**
	 * �ɹ���
	 */
	public float DisPurchasePrice;
	/**
	 * �г���
	 */
	public float RetailPrice;

	public String GUID;
	public int Code;
	public String SellNum;
	public String Weight;
	public float Rate;
	/**
	 * ��������
	 */
	public int count;
	public String data;
}
