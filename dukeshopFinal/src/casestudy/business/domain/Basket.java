package casestudy.business.domain;

/**
 * 이 도메인 객체는 상품 한개의 정보를 담을 수 있는 Basket 객체이다.
 */
public class Basket {
    private int orderNum;
    private String productID;
    private String mallID;
    private String productName;
    private String company;
    private int quantity;
    private int price;
    
    public Basket() {}
    
    /**
     * 주문번호를 아규먼트로 받는 생성자
     */
    public Basket(int orderNum) {
        this(orderNum, "", 0, 0);
    }
    
    /**
     * 주문번호, 상품ID, 수량, 상품가격을 아규먼트로 받는 생성자
     */
    public Basket(int orderNum, String productID, int quantity, int price) {
        this.orderNum = orderNum;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
    }
    
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
    public void setProductID(String productID) {
        this.productID = productID;
    }
    public void setMallID(String mallID) {
        this.mallID = mallID;
    }    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    
    public int getOrderNum() {
        return orderNum;
    }
    public String getProductID() {
        return productID;
    }
    public String getMallID() {
        return mallID;
    }
    public String getProductName() {
        return productName;
    }
    public String getCompany() {
        return company;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getPrice() {
        return price;
    }

    public int getTotalPrice() {
        int totalPrice = price * quantity;
        return totalPrice;
    }
    
    /**
     * quantity의 값을 증가 시켜 준다.
     */
    public void incrementQuantity() {
        quantity++;
    }
    /**
     * parameter로 받은 값으로 quantity의 값을 증가 시켜 준다.
     * @param int num
     */
    public void incrementQuantity(int num) {
        quantity = quantity + num;
    }
    
    /**
     * quantity의 값을 감소 시켜 준다.
     */
    public void decrementQuantity() {
        quantity--;
    }

}
