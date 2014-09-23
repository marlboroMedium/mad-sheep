package casestudy.business.service;

import java.util.Collection;

import casestudy.business.domain.Basket;
import casestudy.business.domain.Purchaser;
import casestudy.business.domain.TheOrder;

/**
 * Basket(쇼핑카트)과 Purchaser(구매) 관련 데이터 액세스 처리를 
 * 담당할 객체의 규격을 정의한 인터페이스.
 */
public interface BasketPurchaserDao {

	/**
	 * Basket, Purchaser 객체와 memberID를 인자로 받아 Basket 테이블에 정보를 등록시킨다.
	 * @param Basket basket,Purchaser purchaser,String memberID
	 */
	public void insertBasketPurchaser(Basket basket, Purchaser purchaser, String memberID);

	/**
	 * 주문번호를 새로 추가하기 위해 주문번호 중 가장 큰 값을 조회하여 1 증가시킨 값을 반환한다.
	 * @param
	 * @return int
	 */
	public int orderMaxNo();

	/**
	 * 구매 완료 후에 memberID를 argument로 받아 Basket 테이블에서 정보를 삭제한다.
	 * @param String memberID
	 */
	public void deleteBasket(String memberID);

	/**
	 * memberID를 argument로 받아 하나의 주문정보를 나타내는 TheOrder 객체를 생성하고
	 * 완성된 TheOrder 객체들을 Collection에 담아 리턴한다.
	 * @param String memID
	 * @return Collection
	 */
	public Collection<TheOrder> listBasketPurchaser(String memberID);

}