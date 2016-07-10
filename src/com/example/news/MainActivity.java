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
		// ׼��ListviewҪ��ʾ�����ݣ�ȥ������ȡ
		initListData();

	}

	// ׼��ListView����
	private void initListData() {
		new Thread(new Runnable() {

			public void run() {

				// ȥ������ȡ���� http://news.qq.com/newsgn/rss_newsgn.xml
				try {
					// ����URL����ָ��Ҫ���ʵ�·��
					String path = "http://news.qq.com/newsgn/rss_newsgn.xml";
					URL url = new URL(path);
					// �õ�httpURLconnection�������ڷ��ͻ��������
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
					// ���÷���get����
					connection.setRequestMethod("GET");
					// ��������ʱʱ��
					connection.setConnectTimeout(5000);
					// ��ȡ���������ص�״̬��
					int code = connection.getResponseCode();
					// ���code����200˵������ɹ�
					if (code == 200) {
						// ��ȡ���������ص����ݣ���������ʽ����
						InputStream inputStream = connection.getInputStream();
						newsLists = XmlParserUtils.parserXml(inputStream);

						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								// ����ui��������չʾ��listview��
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

	// ��������������
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
			// �ҵ��ؼ�����ʾ�������������
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			TextView tv_description = (TextView) view.findViewById(R.id.tv_description);
			TextView tv_pubDate = (TextView) view.findViewById(R.id.tv_pubDate);
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

			// ��ʾ����
			// News news = newsLists.get(position);
			tv_title.setText(newsLists.get(position).getTitle());
			tv_description.setText(newsLists.get(position).getDescription());
			tv_pubDate.setText(newsLists.get(position).getPubDate());
			Glide.with(getApplicationContext()).load("http://img.ithome.com/NewsUploadFiles/thumbnail/2015/7/163188_240.jpg").into(iv_icon);

			return view;
		}

	}

}
