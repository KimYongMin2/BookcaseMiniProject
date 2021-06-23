package bookcase;

import bookcase.crud.BookCRUD;
import bookcase.crud.ReviewCRUD;
import bookcase.object.Book;
import bookcase.object.Member;
import bookcase.object.Review;
import bookcase.show.Show;
import bookcase.util.CommonFunction;
import bookcase.util.JDBCconnecting;
import bookcase.util.ScannerUtil;

import java.sql.*;
import java.util.*;

public class ReviewAddPage implements Show {
	
	//
	private static Connection con = JDBCconnecting.connecting();
	private static ReviewCRUD reviewCrud = ReviewCRUD.getInstance();
	private static BookCRUD bookCrud = BookCRUD.getInstance();
	private ArrayList<Review> reviews = new ArrayList<Review>();
	private ArrayList<Book> books = new ArrayList<>();
	//
	
	private String rComment; // 한줄평
	private double rScore; // 별점
	private Member member;
	private Book book;
	private int temp;
	private int menuButton;
	public List<Review> getReviewList() {
		return reviews;
	}

	public void setReviewList(ArrayList<Review> reviewList) {
		this.reviews = reviewList;
	}

	public ReviewAddPage(Member member) {
		this.member = member;
		menuButton = 0;
	}

	public void reviewAddStart() {
		while (menuButton != 4) {
			try {
				reviews = reviewCrud.getReviewList(con); //오라클에서 리뷰 테이블 전체 받음
				books = bookCrud.getBookList(con); //오라클에서 북 테이블 전체 받음
				showReviewAddMenu();
				CommonFunction.setMenuButton("해당 메뉴를 선택해주세요", menuButton);

				switch (menuButton) {
				case 1:
					findBook();
					setReviewComment();
					System.out.println("리뷰 작성이 완료되었습니다");
					break;
				case 2:
					System.out.println("리뷰 목록을 출력합니다");
					for (int i = 0; i < reviews.size(); i++) {
						System.out.println(reviews.get(i));
					}
					break;
				case 3:
					System.out.println("전체 책 목록을 출력합니다");
					for (Book book : books) {
						System.out.println(book);
					}
					break;
				case 4:
					System.out.println("종료합니다");
					break;
				default:
					System.out.println("잘못 입력하셨습니다");
					break;
				}

			}catch (NumberFormatException e){
				System.out.println("숫자로 입력해주세요");
			}
		}
	}

	public void setReviewComment() {// 리뷰입력
		reviews = reviewCrud.getReviewList(con);
		setComment();
		setScore();
		/**
		 * 리뷰 DB에 넣게 처리
		 */
		reviewCrud.insertReview(con, new Review
				(0, member.getMemberCode(), 
						book.getBookCode(), 
						rScore, rComment));
		System.out.println("작성 완료되었습니다!"); 

	}

	private void setComment() {
		showCommentMenu();
		setStringToVariable("한줄평 : ", rComment);
		checkComment();

		System.out.println("한줄평이 입력되었습니다.");
	}

	private void setStringToVariable(String s, String v) {
		System.out.print(s);
		v = ScannerUtil.getInputString();
	}

	private void setScore() {
		showScoreMenu();
		System.out.print("평점 : ");
		rScore = ScannerUtil.getInputDouble();
		checkScore();
		System.out.println("별점이 입력되었습니다.");
	}

	private void checkScore() {
		boolean chk1 = true;
		if (rScore > 5 || rScore < 0) {
			while (chk1) {
				showScoreError();
				rScore = ScannerUtil.getInputDouble();
				if (rScore <= 5) {
					chk1 = false;
					break;
				}
			}
		}
	}

	private void checkComment() {
		boolean chk = true;
		if (rComment.length() > 40) {
			while (chk) {
				showCommentError();
				rComment = ScannerUtil.getInputString();
				if (rComment.length() <= 40) {
					chk = false;
					break;
				}
			}
		}
	}

	public void findBook() {// 책확인
		reviews = reviewCrud.getReviewList(con);
		books = bookCrud.getBookList(con);
		boolean check = false;
		while (!check) {
			for (Book book : books) {
				System.out.println(book);
			}
			System.out.print("리뷰하려는 책 이름을 작성해주세요 : ");
			String bName = ScannerUtil.getInputString();
			

			for (int i = 0; i < books.size(); i++) {
				if (bName.equals(books.get(i).getbName())) {
					temp = i;
					check = true;
				}
			}
			if (!check) {
				System.out.println("찾지못하였습니다.");
			} else {
				book = books.get(temp);
			}
		}
	}
}

// 멤버코드 -> 받아서 (memberCode)
// 책코드 -> 책 검색 (bookCode)

// review class
// 멤버코드get-책코드get-(String한줄평-float평점: 사용자가 직접입력)
//
// List<review> 만들어서 삽입
