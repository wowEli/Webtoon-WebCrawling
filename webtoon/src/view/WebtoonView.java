package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.PersonVO;
import model.RecentViewVO;
import model.WebtoonVO;

public class WebtoonView {
	Scanner sc = new Scanner(System.in);
	
	public String inputString() { // 제목, 아이디 , 비밀번호 입력받기
		System.out.print("입력: ");
		String str = sc.next();
		return str;
	}
	
	public int inputInt() { // 메뉴에서 사용자의 액션 입력받기
		System.out.print("입력: ");
		int num = sc.nextInt();
		return num;
	}
	
	public void menu1() {
		System.out.println();
		System.out.println("===개발의 민족 웹툰===");
		System.out.println("1. 로그인");
		System.out.println("2. 회원가입");
		System.out.println("3. 종료");
	}
	public void menu2() {
		System.out.println();
		System.out.println("1. 웹툰 전체 보기");
		System.out.println("2. 웹툰 검색");
		System.out.println("3. 웹툰 삭제");
		System.out.println("4. 마이페이지");
		System.out.println("5. 로그아웃");
	}
	
	public void mypage() {
		System.out.println();
		System.out.println("1. 최근 조회한 웹툰");
		System.out.println("2. 회원탈퇴");
		System.out.println("3. 이전 메뉴");
	}
	
	public void oneView(WebtoonVO vo) { // 웹툰객체 1개의 정보를 출력
		System.out.println();
		System.out.println("제목: "+vo.getTitle());
		System.out.println("작가: "+vo.getAuthor());
		System.out.println("조회수: "+vo.getCnt());
		System.out.println("사진경로: "+vo.getImg());
	}
	
	public void allView(ArrayList<WebtoonVO> datas) { // 웹툰 객체배열의 모든 정보를 출력
		System.out.println();
		System.out.println("===웹툰 전체 보기===");
		for(WebtoonVO vo : datas) {
			System.out.println();
			System.out.println("제목: "+vo.getTitle());
			System.out.println("작가: "+vo.getAuthor());
			System.out.println("조회수: "+vo.getCnt());
			System.out.println("사진경로: "+vo.getImg());
		}
	}
	
	public int likeView(ArrayList<WebtoonVO> datas) { // like문에서 받은 배열에서 입력받아 그 객체의 pk를 리턴
		System.out.println();
		System.out.println("===웹툰 목록===");
		for(int i=0;i<datas.size();i++) {
			System.out.println((i+1)+"번 "+datas.get(i).getTitle());
		}
		System.out.print("웹툰 번호 입력: ");
		int action = sc.nextInt();
		return datas.get(action-1).getWid();
	}
	
	public int recentView(ArrayList<RecentViewVO> datas) { // 최근 조회한 웹툰 객체배열의 모든 정보를 출력
		System.out.println();
		System.out.println("====최근 조회한 웹툰 목록===");
		for(int i=0;i<datas.size();i++) {
			System.out.println((i+1)+"번 제목: "+datas.get(i).getTitle());
		}
		System.out.println("보고싶은 웹툰 번호 입력: ");
		int action = sc.nextInt();
		return datas.get(action-1).getWpk();
	}

	// 단순 출력 함수
	public void inputTitleView() {
		System.out.println();
		System.out.println("제목을 입력해주세요.");
	}
	
	public void inputIdView() {
		System.out.println();
		System.out.println("아이디를 입력해주세요.");
	}
	
	public void inputPasswordView() {
		System.out.println();
		System.out.println("비밀번호를 입력해주세요.");
	}
	public void inputNameView() {
		System.out.println();
		System.out.println("이름을 입력해주세요.");
	}
	
	public void searchView() {
		System.out.println();
		System.out.println("===웹툰 검색===");
	}

	public void deleteView() {
		System.out.println();
		System.out.println("===웹툰 삭제 메뉴===");
	}
	public void signUpView() {
		System.out.println();
		System.out.println("===회원가입 메뉴===");
	}
	public void personDeleteView() {
		System.out.println();
		System.out.println("===회원탈퇴 메뉴===");
	}

	public void logoutView() {
		System.out.println();
		System.out.println("로그아웃합니다.");
	}
	public void sameId() {
		System.out.println();
		System.out.println("중복된 아이디 입니다. 다시 입력해주세요.");
	}
	public void failLogin() {
		System.out.println();
		System.out.println("아이디나 비밀번호가 틀렸습니다.");
	}
	
	public void successView() {
		System.out.println();
		System.out.println("성공하였습니다.");
	}
	
	public void failView() {
		System.out.println();
		System.out.println("실하였습니다.");
	}
	
	public void endView() {
		System.out.println();
		System.out.println("종료합니다.");
	}
	
	public void successLogin(PersonVO vo) {
		System.out.println();
		System.out.println(vo.getName()+"님 환영합니다!");		
	}
	
	public void recentViewOver() {
		System.out.println();
		System.out.println("최근 본 웹툰이 10개가 초과되어 마지막 웹툰이 삭제됩니다.");
	}
	
	public void noninput() {
		System.out.println();
		System.out.println("검색하신 웹툰이 없습니다.다시 입력해주세요");
	}
	
	
	
	
	
	
	
}
