package builder.fileFilter.imp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import builder.fileFilter.BasicFileFilter;

public class PositionInf  implements BasicFileFilter{
	private Logger log = Logger.getLogger(PositionInf.class.getName());
	@Override
	public Map<String, String> Filter(Element e,Map<String,String> inf) {
		if(e==null||inf==null){
			return new HashMap<String,String>();
		}
		log.info("=======================start table 3==============================");
		Map<String,String> map =inf;
		for (Iterator<Element> it = e.select("td").iterator(); it.hasNext();) {
			Element ie = it.next();
			String tmp = ie.outerHtml().replace("&nbsp;", "");
			if (-1 != tmp.indexOf(">发布日期：<")) {
				Element etmp = it.next();
				String itmp = etmp.text()
						.replace(Jsoup.parse("&nbsp;").text(), "").trim();
//				println("发布日期：" + itmp);
				map.put("PostingDate", itmp);
			} else if (-1 != tmp.indexOf(">工作地点：<")) {
				Element etmp = it.next();
				String itmp = etmp.text()
						.replace(Jsoup.parse("&nbsp;").text(), "").trim();
//				println("工作地点：" + itmp);
				map.put("Location", itmp);
			} else if (-1 != tmp.indexOf(">招聘人数：<")) {
				Element etmp = it.next();
				String itmp = etmp.text()
						.replace(Jsoup.parse("&nbsp;").text(), "").trim();
//				println("招聘人数：" + itmp);
				map.put("Number", itmp);
			} else if (-1 != tmp.indexOf(">工作年限：<")) {
				Element etmp = it.next();
				String itmp = etmp.text()
						.replace(Jsoup.parse("&nbsp;").text(), "").trim();
//				println("工作年限：" + itmp);
				map.put("Experience",itmp);
			} else if (-1 != tmp.indexOf(">学历：<")) {
				Element etmp = it.next();
				String itmp = etmp.text()
						.replace(Jsoup.parse("&nbsp;").text(), "").trim();
//				println("学历：" + itmp);
				map.put("Education", itmp);
			}else if (-1 != tmp.indexOf(">薪水范围：<")) {
					Element etmp = it.next();
					String itmp = etmp.text()
							.replace(Jsoup.parse("&nbsp;").text(), "").trim();
//					println("薪水范围：" + itmp);
				map.put("Salary", itmp);
			} else if (-1 != tmp.indexOf(">职位职能:<")) {
				tmp = ie.text().replace(Jsoup.parse("&nbsp;").text(), "")
						.trim();
				String itmp = tmp.split(":")[1].trim();
//				println("职位职能:" + itmp);
				map.put("Function", itmp);
			} else if (-1 != tmp.indexOf(">职位描述:<")) {
				tmp = ie.text().replace(Jsoup.parse("&nbsp;").text(), "")
						.trim();
				String itmp = tmp.split(":",2)[1].trim();
//				println("职位描述:" + itmp);
				map.put("Description", itmp);
			}
			else if (-1 != tmp.indexOf(">语言要求:<")) {
				tmp = ie.text().replace(Jsoup.parse("&nbsp;").text(), "")
						.trim();
				String itmp = tmp.split(":")[1].trim();
//				println("职位描述:" + itmp);
				map.put("Language", itmp);
			}
			// println("td:"+it.text());
		}
		log.info("======================end table 3===============================");
		return map;
	}

}
