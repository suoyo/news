package com.example.news;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.bumptech.glide.Glide;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private List<News> newsLists;
	private ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.lv);
		// 准备Listview要显示的数据，去服务器取
		initListData();

	}

	// 准备ListView数据
	private void initListData() {
		new Thread(new Runnable() {

			public void run() {

				// 去服务器取数据 http://news.qq.com/newsgn/rss_newsgn.xml
				try {
					// 创建URL对象，指定要访问的路径
					String path = "http://news.qq.com/newsgn/rss_newsgn.xml";
					URL url = new URL(path);
					// 拿到httpURLconnection对象，用于发送或接收数据
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// 设置发送get请求
					connection.setRequestMethod("GET");
					// 设置请求超时时间
					connection.setConnectTimeout(5000);
					// 获取服务器返回的状态码
					int code = connection.getResponseCode();
					// 如果code等于200说明请求成功
					if (code == 200) {
						// 获取服务器返回的数据，以流的形式返回
						InputStream inputStream = connection.getInputStream();
						newsLists = XmlParserUtils.parserXml(inputStream);

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// 更新ui，把数据展示到listview上
								lv.setAdapter(new MyAdapter());

							}
						});

					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();

	}

	// 定义数据适配器
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return newsLists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(), R.layout.item, null);
			} else {
				view = convertView;
			}
			// 找到控件，显示集合里面的数据
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			TextView tv_description = (TextView) view.findViewById(R.id.tv_description);
			TextView tv_pubDate = (TextView) view.findViewById(R.id.tv_pubDate);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

			// 显示数据
			// News news = newsLists.get(position);
			tv_title.setText(newsLists.get(position).getTitle());
			tv_description.setText(newsLists.get(position).getDescription());
			tv_pubDate.setText(newsLists.get(position).getPubDate());
			Glide.with(getApplicationContext()).load("http://img.ithome.com/NewsUploadFiles/thumbnail/2015/7/163188_240.jpg").into(iv_icon);

			return view;
		}

	}

}
