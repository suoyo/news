package com.example.news;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

import android.net.PskKeyManager;
import android.util.Xml;

public class XmlParserUtils {

	// 解析xml业务的方法
	public static List<News> parserXml(InputStream in) throws Exception {
		
		List<News> newsLists = null;
		News news = null;

		// 获取xml解析器
		XmlPullParser parser = Xml.newPullParser();
		// 设置解析器要解析的内容
		parser.setInput(in, "gb2312");
		// 获取解析的事件类型
		int type = parser.getEventType();
		
		do {
			type = parser.next();
		} while (!"generator".equals(parser.getName()));
		
		
		// 不停的向下解析
		while (type != XmlPullParser.END_DOCUMENT) {
			
			// 具体判断解析的是开始结点还是结束结点
			switch (type) {
			case XmlPullParser.START_TAG: // 解析开始结点
				
				
//				具体判断解析的是哪个标签
				if ("generator".equals(parser.getName())) {
//					创建一个list集合
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
			case XmlPullParser.END_TAG: // 解析结束结点
				if ("item".equals(parser.getName())) {
					newsLists.add(news);
				}
				break;

			default:
				break;
			}
//			不停的向下解析 
			type = parser.next();
		}

		return newsLists;

	}

}
