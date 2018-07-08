package com.example.chen.kechensj.bean;

import java.util.List;

public class MyOrderBean {
	

	private List<Data> data;

	public void setData(List<Data> data) {
		this.data = data;
	}

	public List<Data> getData() {
		return data;
	}

	public class Data {
	
		private String id;
		private int count;


		@Override
		public String toString() {
			return "Data [id=" + id + ", count=" + count + "]";
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
	}
}
