package casestudy.business.domain;

import java.sql.Date;

/**
 * This domain object represents a Purchaser.
 * The data attributes are all package-private to allow access to them
 * in the {@link BasketPurchaserServiceImpl} class.
 */
public class Purchaser {
	private int orderNum;
	private String place;
	private String memberID;
	private String name;
	private String address;
	private String tel;
	private String email;
	private String payType;
	private int amount;    
	private String payStatus;
	private Date purchaseDate;
	private String cardType;
	private String cardNumber;

    
    /** Creates a new instance of Purchaser */
    public Purchaser() {}
    
    public Purchaser(int orderNum) {
        this(orderNum, "", "", "", "", "", "", "", 0, "", null);
    }
    
    /**
     * This is the full constructor.
     */
    public Purchaser(int orderNum, String place, String memberID, String name, String address,
            String tel, String email, String payType, int amount, String payStatus, Date purchaseDate) {
        this.orderNum = orderNum;
        this.place = place;
        this.memberID = memberID;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.email = email;
        this.payType = payType;
        this.amount = amount;
        this.payStatus = payStatus;
        this.purchaseDate = purchaseDate;
    }
    
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
    public void setPlace(String place) {
        this.place = place;
    }
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPayType(String payType) {
        this.payType = payType;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public int getOrderNum() {
        return orderNum;
    }
    public String getPlace() {
        return place;
    }
    public String getMemberID() {
        return memberID;
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public String getTel() {
        return tel;
    }
    public String getEmail() {
        return email;
    }
    public String getPayType() {
        return payType;
    }
    public int getAmount() {
        return amount;
    }
    public String getPayStatus() {
        return payStatus;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public String getCardType() {
        return cardType;
    }
    public String getCardNumber() {
        return cardNumber;
    }

}
