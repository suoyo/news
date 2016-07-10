package com.example.news;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

import android.net.PskKeyManager;
import android.util.Xml;

public class XmlParserUtils {

	// ����xmlҵ��ķ���
	public static List<News> parserXml(InputStream in) throws Exception {
		
		List<News> newsLists = null;
		News news = null;

		// ��ȡxml������
		XmlPullParser parser = Xml.newPullParser();
		// ���ý�����Ҫ����������
		parser.setInput(in, "gb2312");
		// ��ȡ�������¼�����
		int type = parser.getEventType();
		
		do {
			type = parser.next();
		} while (!"generator".equals(parser.getName()));
		
		
		// ��ͣ�����½���
		while (type != XmlPullParser.END_DOCUMENT) {
			
			// �����жϽ������ǿ�ʼ��㻹�ǽ������
			switch (type) {
			case XmlPullParser.START_TAG: // ������ʼ���
				
				
//				�����жϽ��������ĸ���ǩ
				if ("generator".equals(parser.getName())) {
//					����һ��list����
					newsLists = new ArrayList<News>();
				}else if ("item".equals(parser.getName())) {
					news = new News();
				}else if ("title".equals(parser.getName())) {
					news.setTitle(parser.nextText());
				}else if ("link".equals(parser.getName())) {
					news.setLink(parser.nextText());
				}else if ("pubDate".equals(parser.getName())) {
					news.setPubDate(parser.nextText());
				}else if ("description".equals(parser.getName())) {
					news.setDescription(parser.nextText());
				}
				break;
			case XmlPullParser.END_TAG: // �����������
				if ("item".equals(parser.getName())) {
					newsLists.add(news);
				}
				break;

			default:
				break;
			}
//			��ͣ�����½��� 
			type = parser.next();
		}

		return newsLists;

	}

}
