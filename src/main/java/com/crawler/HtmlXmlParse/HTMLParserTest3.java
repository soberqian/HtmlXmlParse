package com.crawler.HtmlXmlParse;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HTMLParserTest3 {

	public static void main(String[] args) throws IOException, ParserException {
		//使用URLConnection请求数据
		URL url = new URL("http://www.w3school.com.cn/b.asp");
		URLConnection conn = url.openConnection();
		Parser parser = new Parser(conn);
		//通过css选择器解析内容 
		CssSelectorNodeFilter divFilter=new CssSelectorNodeFilter ("#course > ul > li");  
		NodeList list = parser.extractAllNodesThatMatch(divFilter);  //选择匹配到的内容
		//循环遍历
		for(int i=0; i<list.size();i++){
			//获取li的第一个子节点
			Node node = (Node)list.elementAt(i).getFirstChild(); 
			System.out.println( "链接为：" + ((LinkTag) node).getLink() +"\t标题为:" + node.toPlainTextString() );
		}
	}
}
