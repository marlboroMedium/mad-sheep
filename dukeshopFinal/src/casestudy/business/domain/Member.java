/**
 * 파일명 : Member.java
 * 작성일 : 2014. 2. 12.
 * 파일설명 : 
 */
package casestudy.business.domain;

import java.sql.Date;

/**
 * 회원과 관련한 정보를 저장하고 있는 객체를 정의한 도메인 클래스.<br/> 
 * 비즈니스 로직 층은 유스케이스로 표현되는 특정 업무나 특정 부서 처리의 통합인 서비스 및 도메인으로 구성된다.<br/>
 * 도메인은 서비스로부터 비즈니스를 실행하는데 있어 당연히 인식되는 클래스(고객이나 주문과 같은)의 집합으로
 * 자신이 무엇인지 나타내는 값과 그 값을 이용한 처리를 실현한다.<br/> 
 * 도메인이 로직을 포함하지 않고 단순히 값만 저장하기만 하는 객체일 경우 VO(Value Object: 값을 저장하는 객체)나
 * DTO(Data Transfer Object: 값을 전달하기만 하는 객체)라고 부르기도 한다. 
 *  
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public class Member {
	private String memberID;
	private String password;
	private String name;
	private String email;
	private String tel;
	private String zipcode;
	private String address;
	private int point;
	private Date memberDate;
	private int check;	// VALID_MEMBER or INVALID_ID or INVALID_PASSWORD
	
	/** 유효한 회원임을 나타내는 상수 */
	public static final int VALID_MEMBER = 1;
	/** memberID가 존재하지 않는 회원임을 나타내는 상수 */
	public static final int INVALID_ID = 0; 
	/** password가 일치하지 않는 회원임을 나타내는 상수 */
	public static final int INVALID_PASSWORD = -1;
	
	public Member() {
	}

	public Member(String memberID, String password) {
		this.memberID = memberID;
		this.password = password;
	}
	
	public Member(String memberID, String password, String name, String email,
			String tel, String zipcode, String address) {
		this.memberID = memberID;
		this.password = password;
		this.name = name;
		this.email = email;
		this.tel = tel;
		this.zipcode = zipcode;
		this.address = address;
	}
	
	public Member(String memberID, String password, String name, String email,
			String tel, String zipcode, String address, int point) {
		this(memberID, password, name, email, tel, zipcode, address);
		this.point = point;
	}
	
	public Member(String memberID, String password, String name, String email,
			String tel, String zipcode, String address, int point, Date memberDate) {
		this(memberID, password, name, email, tel, zipcode, address);
		this.point = point;
		this.memberDate = memberDate;
	}

	public String getMemberID() {
		return memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Date getMemberDate() {
		return memberDate;
	}

	public void setMemberDate(Date memberDate) {
		this.memberDate = memberDate;
	}

	public int getCheck() {
		return check;
	}

	/** 회원의 유효성 여부를 나타내는 check 값을 설정한다.
	 * check 인수의 값은 Member.VALID_MEMBER, Member.INVALID_ID, Member.INVALID_PASSWORD 중 하나여야 한다.
	 * 	
	 * @param check Member.VALID_MEMBER, Member.INVALID_ID or Member.INVALID_PASSWORD
	 */
	public void setCheck(int check) {
		this.check = check;
	}

	@Override
	public String toString() {
		return "Member [memberID=" + memberID + ", password=" + password
				+ ", name=" + name + ", email=" + email + ", tel=" + tel
				+ ", zipcode=" + zipcode + ", address=" + address + ", point="
				+ point + ", memberDate=" + memberDate + ", check=" + check
				+ "]";
	}
}
