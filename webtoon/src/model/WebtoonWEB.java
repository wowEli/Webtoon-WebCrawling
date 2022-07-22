package model;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebtoonWEB {
	public static void main(String[] args) {
		final String url="https://comic.naver.com/webtoon/weekdayList?week=mon"; // 읽어 오고 싶은 웹 url
		Document doc=null;
		
		try {
			doc = Jsoup.connect(url).get(); 
			// Jsoup외부 클래스사용 connect함수로 웹과 연결 get함수로 내용을 보여주는데 리턴타입이 Document임
			
			String str1 ="#content > div.list_area.daily_img > ul > li > dl > dt > a"; // 제목
			String str2 = "#content > div.list_area.daily_img > ul > li > dl > dd.desc > a"; // 작가
			String str3 = "#content > div.list_area.daily_img > ul > li > div > a > img"; // 이미지 주소
			Elements eles1 = doc.select(str1); // 위에서 따온 경로를 Elements 속성의 객체에 넣어준다.
			Elements eles2 = doc.select(str2);
			Elements eles3 = doc.select(str3);
			
			Iterator<Element> itr1 = eles1.iterator(); // 컬렉션 프레임워크인 Iterator 객체에 위의 주소에서 따온 값들을 하나씩 저장 
			Iterator<Element> itr2 = eles2.iterator();
			Iterator<Element> itr3 = eles3.iterator();
			
			WebtoonDAO model=new WebtoonDAO();
			
			while(itr1.hasNext()) { // 다음 데이터가 없을 때까지 실행
				WebtoonVO vo=new WebtoonVO();
				String title = itr1.next().text(); 
				// next함수는 데이터를 하나씩 보여줌 그러나 리턴타입이 Element라 출력이 안됨 그래서 text함수를 사용해서 String 타입으로 리턴되게 하기
				String author = itr2.next().text();
				String src = itr3.next().attr("src"); // attr함수는 주소값을 보여줌
				System.out.println(title);
				System.out.println(author);
				System.out.println(src);
				vo.setTitle(title);
				vo.setAuthor(author);
				vo.setImg(src);
				model.insert(vo);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
