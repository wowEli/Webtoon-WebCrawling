package controller;

import java.util.ArrayList;

import model.PersonDAO;
import model.PersonVO;
import model.RecentViewDAO;
import model.RecentViewVO;
import model.WebtoonDAO;
import model.WebtoonVO;
import view.WebtoonView;

public class WebtoonController {
	WebtoonDAO wDAO;
	PersonDAO pDAO;
	RecentViewDAO rDAO;
	WebtoonView view;

	public WebtoonController() {
		wDAO = new WebtoonDAO();
		pDAO = new PersonDAO();
		rDAO = new RecentViewDAO();
		view = new WebtoonView();
	}

	public void appStart() {
		while (true) {
			view.menu1(); // 1. 로그인 2. 회원가입 3. 종료
			int action = view.inputInt(); // 숫자 입력받기

			if (action == 1) { // 1. 로그인
				boolean flag1 = true; // 로그 아웃 사용 시 탈출용도
				while (flag1) {
					view.inputIdView(); // 아이디를 입력해주세요
					String id = view.inputString(); // 문자열 입력받기
					view.inputPasswordView(); // 비밀번호를 입력해주세요
					String pw = view.inputString(); // 문자열 입력받기

					PersonVO pvo1 = new PersonVO();
					pvo1.setId(id);
					pvo1.setPassword(pw);
					PersonVO pvo2 = pDAO.login(pvo1); // 로그인
					if (pvo2 == null) {
						view.failLogin();
						continue;
					}
					view.successLogin(pvo2);// 환영 멘트

					boolean flag2 = true; // 회원탈퇴시 탈출 용도

					while (flag2) {
						view.menu2(); // 웹툰 메뉴
						int action2 = view.inputInt(); // 숫자를 입력받기

						if (action2 == 1) { // 1. 웹툰 전체 보기
							WebtoonVO wvo = new WebtoonVO();
							view.allView(wDAO.selectAll(wvo)); // 전체 웹툰 배열 받아와서 보여주기
						} else if (action2 == 2) { // 2. 웹툰 검색
							while (true) {
								view.searchView(); // 웹툰 검색 메뉴
								String title = view.inputString(); // 문자열 입력받기
								WebtoonVO wvo = new WebtoonVO();
								wvo.setTitle(title);
								ArrayList<WebtoonVO> datas = new ArrayList<WebtoonVO>();
								datas = wDAO.selectLike(wvo);

								if (datas.size() == 0) { // datas.size가 0이면 ( 배열이 무조건 리턴되는데 없는 값일 경우 null이 아님)
									view.noninput(); // 웹툰 재입력 문구
									continue; // 아래 무시하고 다시 입력받기
								}

								int wid = view.likeView(datas); // sql LIKE 문 사용 후 배열리턴 사용자가 입력한 웹툰의 pk리턴

								WebtoonVO wvo1 = new WebtoonVO();
								wvo1.setWid(wid);
								WebtoonVO wvo2 = wDAO.selectOne(wvo1); // 같은 pk값을 가진 웹툰객체 리턴
								wDAO.update(wvo2); // 사용자가 고른 웹툰 조회수 +1

								WebtoonVO wvo3 = wDAO.selectOne(wvo2); // 사용자가 조회한 수도 올려서 보여주기 위해 코드 작성(완전 최신화 이 값이 곧
																		// 데이터베이스 데이터)
								view.oneView(wvo3); // 사용자가 고른 웹툰 정보 보여주기

								RecentViewVO rvo = new RecentViewVO();

								rvo.setPpk(pvo2.getId()); // 사용자의 아이디 넣어주고

								rvo.setWpk(wvo2.getWid()); // 웹툰 pk 도 넣어주기

								if (rDAO.search(rvo)) { // 중복된 웹툰이 있으면
									rDAO.searchDelete(rvo); // 최근 본 웹툰에서 중복된 데이터 삭제하고
								} // 새로 추가해서 사용자가 봤을 때 마치 봤었던 웹툰이 다시 맨 위로 올라온 것처럼 보이도록 함

								if (rDAO.recentViewCnt(rvo)) {
									rDAO.recentViewDelete(rvo); // 10개 초과데이터 삭제
									view.recentViewOver(); // 삭제 완료 문구

								}

								rDAO.recentViewInsert(rvo); // 최근 본 웹툰의 추가
								break;

							}
						} else if (action2 == 3) { // 3. 웹툰 삭제
							view.deleteView();
							WebtoonVO wvo = new WebtoonVO();
							ArrayList<WebtoonVO> datas = wDAO.selectAll(wvo);
							int wid = view.likeView(datas); // 선택한 객체의 pk 값을 리턴
							wvo.setWid(wid); // 빈객채에 pk 넣어주기
							wDAO.delete(wvo);// 웹툰 삭제 메서드
							view.successView(); // 성공하였습니다

							RecentViewVO rvo = new RecentViewVO();
							rvo.setWpk(wid); // rvo객체에 웹툰 pk값 저장
							rDAO.recentWebDelete(rvo); // 장바구니에서 삭제된 웹툰 삭제

						} else if (action2 == 4) { // 4. 마이페이지

							while (true) {
								view.mypage(); // 마이페이지 메뉴
								int action3 = view.inputInt(); // 숫자 입력받기

								if (action3 == 1) { // 1. 최근 웹툰보기
									RecentViewVO rvo = new RecentViewVO();
									rvo.setPpk(id); // 사용자의 id 넣어주기
									int wid = view.recentView(rDAO.joinTitle(rvo));// join을 통해서 최근 본 웹툰 제목과 pk배열 리턴받고
									// view에서 출력과 사용자에게 입력받기
									// 웹툰 검색에 있는 것과 같은 로직
									WebtoonVO wvo1 = new WebtoonVO();
									wvo1.setWid(wid);
									WebtoonVO wvo2 = wDAO.selectOne(wvo1); // 같은 pk값을 가진 웹툰객체 리턴
									wDAO.update(wvo2); // 사용자가 고른 웹툰 조회수 +1

									WebtoonVO wvo3 = wDAO.selectOne(wvo2); // 최신화된 조회수를 보여주기위한 코드
									view.oneView(wvo3); // 사용자가 고른 웹툰 정보 보여주기

									RecentViewVO rvo1 = new RecentViewVO();
									rvo1.setPpk(pvo2.getId()); // 사용자의 아이디 넣어주고
									rvo1.setWpk(wvo2.getWid()); // 웹툰 pk 도 넣어주기

									if (rDAO.search(rvo1)) { // 중복된 웹툰이 있으면
										rDAO.searchDelete(rvo1); // 최근 본 웹툰에서 중복된 데이터 삭제하고
									} // 새로 추가해서 사용자가 봤을 때 마치 봤었던 웹툰이 다시 맨 위로 올라온 것처럼 보이도록 함

									rDAO.recentViewInsert(rvo1); // 최근 본 웹툰의 추가
									break;
								} else if (action3 == 2) { // 2. 회원탈퇴
									// 회원탈퇴 과정
									view.personDeleteView(); // 회원탈퇴 메뉴 입니다
									view.inputIdView(); // 이름을 입력해주세요
									String id2 = view.inputString(); // 문자열 입력받기
									view.inputPasswordView(); // 비밀번호를 입력해주세요
									String pw2 = view.inputString(); // 문자열 입력받기

									PersonVO pvo = new PersonVO();
									pvo.setId(id2);
									pvo.setPassword(pw2);
									pDAO.delete(pvo);

									flag1 = false;
									flag2 = false;
									view.successView();
									break;
								} else if (action3 == 3) { // 3. 이전 메뉴
									break;
								}
							}
						} else if (action2 == 5) { // 5. 로그아웃
							flag1 = false;
							break;
						}
					}
				}
			} else if (action == 2) { // 2. 회원가입
				while (true) {
					PersonVO pvo = new PersonVO();
					view.signUpView(); // 회원가입 메뉴
					view.inputIdView();// 아이디를 입력해주세요
					String id = view.inputString(); // 문자열 입력받기
					pvo.setId(id);

					if (pDAO.signUpCheck(pvo)) { // 중복된 id가 없다면 그대로 회원가입 진행

						view.inputPasswordView(); // 비밀번호를 입력해주세요
						String pw = view.inputString(); // 문자열 입력받기

						view.inputNameView(); // 이름을 입력해주세요
						String name = view.inputString(); // 문자열 입력받기

						pvo.setPassword(pw);
						pvo.setName(name); // 새로운 person객체에 사용자의 입력값 넣기

						pDAO.signUp(pvo); // person db에 데이터 추가

						view.successView(); // 성공하였습니다
						break; // 정지
					}
					view.sameId(); // 중복된 아이디입니다 다시 입력해주세요

				}

			} else if (action == 3) { // 3. 종료
				view.endView(); // 종료합니다
				break;

			}

		}
	}
}
