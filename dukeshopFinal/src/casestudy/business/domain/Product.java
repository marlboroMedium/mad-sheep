/**
 * 파일명 : Product.java
 * 작성일 : 2014. 2. 12.
 * 파일설명 : 
 */
package casestudy.business.domain;

import java.sql.Date;

/**
 * 상품과 관련한 정보를 저장하고 있는 객체를 정의한 도메인 클래스.<br/> 
 * 비즈니스 로직 층은 유스케이스로 표현되는 특정 업무나 특정 부서 처리의 통합인 서비스 및 도메인으로 구성된다.<br/>
 * 도메인은 서비스로부터 비즈니스를 실행하는데 있어 당연히 인식되는 클래스(고객이나 주문과 같은)의 집합으로
 * 자신이 무엇인지 나타내는 값과 그 값을 이용한 처리를 실현한다.<br/> 
 * 도메인이 로직을 포함하지 않고 단순히 값만 저장하기만 하는 객체일 경우 VO(Value Object: 값을 저장하는 객체)나
 * DTO(Data Transfer Object: 값을 전달하기만 하는 객체)라고 부르기도 한다. 
 *  
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public class Product {
	private String productID;
	private String mallID;
	private String productName;
	private String company;
	private int price1;
	private int price2;
	private String installment;
	private String keyword;
	private String detail;
	private Date productDate;
	private String photoDir;
	
	public Product() {
	}

	public Product(String productID, String mallID, String productName,
			String company, int price1, int price2, String installment,
			String keyword, String detail, Date productDate, String photoDir) {
		this.productID = productID;
		this.mallID = mallID;
		this.productName = productName;
		this.company = company;
		this.price1 = price1;
		this.price2 = price2;
		this.installment = installment;
		this.keyword = keyword;
		this.detail = detail;
		this.productDate = productDate;
		this.photoDir = photoDir;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getMallID() {
		return mallID;
	}

	public void setMallID(String mallID) {
		this.mallID = mallID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getPrice1() {
		return price1;
	}

	public void setPrice1(int price1) {
		this.price1 = price1;
	}

	public int getPrice2() {
		return price2;
	}

	public void setPrice2(int price2) {
		this.price2 = price2;
	}

	public String getInstallment() {
		return installment;
	}

	public void setInstallment(String installment) {
		this.installment = installment;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public String getPhotoDir() {
		return photoDir;
	}

	public void setPhotoDir(String photoDir) {
		this.photoDir = photoDir;
	}

	@Override
	public String toString() {
		return "Product [productID=" + productID + ", mallID=" + mallID
				+ ", productName=" + productName + ", company=" + company
				+ ", price1=" + price1 + ", price2=" + price2 + ", installment="
				+ installment + ", keyword=" + keyword + ", detail=" + detail
				+ ", productDate=" + productDate + ", photoDir=" + photoDir
				+ "]";
	}	
}
