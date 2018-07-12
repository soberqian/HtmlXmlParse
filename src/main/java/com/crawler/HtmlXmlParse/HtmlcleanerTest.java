package com.crawler.HtmlXmlParse;

import java.io.IOException;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlcleanerTest {

	public static void main(String[] args) throws IOException, XPatherException {
		//这里笔者使用Jsoup获取html文件
		Document doc = Jsoup.connect("http://www.w3school.com.cn/b.asp").timeout(5000).get(); 
		String html =doc.html();  //转化成String格式
		//使用Htmlcleaner解析数据
		HtmlCleaner cleaner = new HtmlCleaner(); //初始化对象
//		System.out.println(html);
		TagNode node = cleaner.clean(html); //解析HTML文件
		//通过Xpath定位标题的位置,这里使用//h1和/h1的结果是一样的
        Object[]  ns = node.evaluateXPath("//div[@id='w3school']//h1");  
        System.out.println("HTML中的标题是:\t" + ((TagNode)ns[0]).getText());  
        Object[]  ns1 = node.evaluateXPath("//*[@id='w3school']/h1"); 
        System.out.println("HTML中的标题是:\t" + ((TagNode)ns1[0]).getText());  
        //遍历获取课程名以及课程地址
        Object[]  ns2 = node.evaluateXPath("//*[@id='course']/ul//a");  //这里使用//a表示不考虑位置,如果使用/a获取不到内容
        for(Object on : ns2) {  
            TagNode n = (TagNode) on;  
            System.out.println("课程名为:\t" + n.getText() + "\t地址为:\t" + n.getAttributeByName("href"));  
        } 
        //获取每个课程名称以及其对应的简介
        Object[]  ns3 = node.evaluateXPath("//*[@id='maincontent']//div");
        for (int i = 1; i < ns3.length; i++) {
        	TagNode n = (TagNode) ns3[i]; 
        	//获取课程名称
        	String courseName = n.findElementByName("h2", true).getText().toString();
        	//循环遍历所有的p节点获取课程简介
			Object[] objarrtr = n.evaluateXPath("//p");
			String summary = "";
			for(Object on : objarrtr) { 
				summary += ((TagNode) on).getText().toString();
			} 
			System.out.println(courseName + "\t" + summary);
		}
	}
}
