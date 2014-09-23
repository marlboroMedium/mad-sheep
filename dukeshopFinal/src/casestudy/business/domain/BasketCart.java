package casestudy.business.domain;

import java.util.*;

/**
 * 이 도메인 객체는 상품 한개의 정보를 담는 Basket 객체를 여러개 담을 수 있는 객체이다.
 */
public class BasketCart {
    private HashMap<String, Basket> items = null;
    private int numberOfItems = 0;

    /** Creates a new instance of BasketCart */
    public BasketCart() {
        items = new HashMap<String, Basket>();
    }
    
    /**
     * BasketCart의 HashMap에 basket객체를 더해 준다.
     * ProductId를 키로 하여 존재 유무를 확인하고
     * 존재하면 quantity의 수를 증가 시킨다.
     * 존재하지 않으면 basket객체를 저장한다.
     *
     * @param String productID,Basket basket
     */
    public void add(String productID, Basket basket) {
        if (items.containsKey(productID)) {
            //존재하면 수량을 증가시킨다
            basket = items.get(productID);
            basket.incrementQuantity();
        } else {
            //존재하지 않으면 basket객체를 저장한다.
            items.put(productID, basket);
            numberOfItems++;
        }
    }
    
    /**
     * BasketCart의 HashMap에 basket객체를 더해 준다.
     * ProductID를 키로 하여 존재를 유무를 확인하고
     * 존재할 때 quantity의 수를 parameter로 받아 증가 시킨다.
     *
     * @param String productID,Basket basket,int quantity
     */
    public void add(String productID, Basket basket,int quantity) {
        if (items.containsKey(productID)) {
            basket = items.get(productID);
            basket.incrementQuantity(quantity);
        } else {
            items.put(productID, basket);
            numberOfItems++;
        }        
    }
        
    /**
     * BasketCart의 HashMap에 basket객체를 삭제해준다.
     * ProductId를 키로 하여 존재 유무를 확인하고
     * 존재하면 HashMap에서 모두 삭제한다.
     *
     * @param String productID
     */
    public void removeAll(String productID) {
        if (items.containsKey(productID)) {
            items.remove(productID);
            numberOfItems--;
        }
    }
    
    /**
     * BasketCart의 HashMap에 basket객체를 삭제해준다.
     * ProductID를 키로 하여 존재 유무를 확인하고
     * 존재할때 quantity의 수를 감소 시킨다.
     *
     * @param String productID
     */
    public void remove(String productID) {
        if (items.containsKey(productID)) {
            Basket basket = items.get(productID);
            //System.out.println("삭제전 :" + basket.getQuantity() + " " + basket.getTotalPrice());
            basket.decrementQuantity();
            //System.out.println("삭제후 :" + basket.getQuantity() + " " + basket.getTotalPrice());
            
            if (basket.getQuantity() <= 0) {
                items.remove(productID);
                numberOfItems--;
            }
        }
    }

     /**
     * HashMap의 모든 Values를 반환한다
     */
    public Collection<Basket> getItems() {
        return items.values();
    }
    
    /**
     * HashMap의 ProductID key값들을 반환한다.
     * @return String[] keySet
     */
    public String[] keySet() {
        Set<String> set = items.keySet();
        String[] keys = set.toArray(new String[0]);
        
        return keys;
    }
    
    /**
     * HashMap의 값의 갯수를 리턴한다.
     */
    public int getNumberOfItems() {
        return numberOfItems;
    }
    
    public int getTotalPrice() {
        int totalPrice = 0;
        Collection<Basket> collection = items.values();
        Iterator<Basket> iterator = collection.iterator();
        Basket basket;
        while (iterator.hasNext()) {
            basket = iterator.next();
            totalPrice = totalPrice + basket.getTotalPrice();
        }
        return totalPrice;
    }
    
    /**
     * HashMap의 모든 내용을 삭제한다.
     */
    public void clear() {
        items.clear();
        numberOfItems = 0;
    }  
}
