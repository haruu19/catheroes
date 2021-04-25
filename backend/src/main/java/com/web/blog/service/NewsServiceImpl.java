package com.web.blog.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.web.blog.model.News;

@Service
public class NewsServiceImpl implements NewsService{
	// 뉴스를 받아올 URL 지정
	private static String CAT_NEWS_URL = "https://search.naver.com/search.naver?sm=tab_sug.top&where=news&query=%EA%B8%B8%EA%B3%A0%EC%96%91%EC%9D%B4+%EC%B9%9C%ED%95%B4%EC%A7%80%EA%B8%B0&oquery=%EA%B3%A0%EC%96%91%EC%9D%B4&tqi=Uzcx6lp0Jy0ssvkcYYlssssstaN-425208&acq=%EA%B8%B8%EA%B3%A0%EC%96%91%EC%9D%B4+&acr=10&qdt=0";

	@Override
	@PostConstruct
	public Optional<List<News>> retrieveNews() {

		Document doc;
		News[] newsArr = new News[10];
		List<News> list = new ArrayList<>();
		Optional<List<News>> listOpt = null;
		int idx = 0;

		try {
			doc = Jsoup.connect(CAT_NEWS_URL).get(); // URL에 접근
			
			// 1. 제목, 링크 받아오기
			Elements contents1 = doc.select(".type01 ._sp_each_title");

			for(Element content : contents1) {
				newsArr[idx] = new News();
				newsArr[idx].setTitle(content.text());
				newsArr[idx].setUrl(content.attr("href"));
				
				idx++;
			}
			
			// 2. 이미지 받아오기
			Elements contents2 = doc.select(".type01 .sp_thmb img");
			idx = 0;
			for(Element content : contents2) {
				newsArr[idx].setImg(content.attr("src"));
				
				idx++;
			}
			
			// 3. 내용 받아오기
			Elements contents4 = doc.select(".type01 dd:nth-child(odd)");
			idx = 0;
			for(Element content : contents4) {
				newsArr[idx].setContent(content.text());
				
				idx++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		list = Arrays.asList(newsArr);
		listOpt = Optional.ofNullable(list);
		
		return listOpt;
	}
}
