package casestudy.business.service;

import java.util.Collection;

import casestudy.business.domain.Basket;
import casestudy.business.domain.Purchaser;
import casestudy.business.domain.TheOrder;

/**
 * 장바구니와 구매 처리와 관련한 서비스를 담당할 객체의 규격을 정의한 인터페이스.
 */
public interface BasketPurchaserService {

	/**
	 * 인자로 받은 Basket, Purchaser 객체와 memberID 정보를 가지고 구매 처리를 진행한다.
	 * @param Basket basket,Purchaser purchaser,String memberID
	 */
	public void insertBasketPurchaser(Basket basket, Purchaser purchaser, String memberID);

	/**
	 * 인자로 받은 memberID에 대한 장바구니 정보를 삭제한다.
	 * @param String memberID
	 */
	public void deleteBasket(String memberID);

	/**
	 * 인자로 받은 memberID에 대한 진행 중인 주문정보(TheOrder) 리스트를 구한다.
	 * @param String memID
	 * @return Collection
	 */
	public Collection<TheOrder> listBasketPurchaser(String memberID);

	/**
	 * 새로운 주문번호를 구한다.
	 * @param
	 * @return int
	 */
	public int orderMaxNo();

}