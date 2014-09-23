/**
 * 파일명 : MemberServiceImpl.java
 * 작성일 : 2014. 2. 13.
 * 파일설명 : 
 */
package casestudy.business.service;

import casestudy.business.domain.Member;

/**
 * 회원 관련 비즈니스 로직을 구현할 서비스 클래스로 
 * 데이터 액세스 처리는 MemberDao 객체에게 위임하여 수행한다.
 * 
 * @author 고범석(kidmania@hotmail.com)
 *
 */
public class MemberServiceImpl implements MemberService {

    private MemberDao memberDataAccess;
    
    /* 
     * MemberDaoImpl 객체를 생성하여 memberDataAccess 인스턴스 변수 초기화  
     */
    public MemberServiceImpl() {
    	memberDataAccess = new casestudy.dataaccess.MemberDaoImpl();
    }
    
	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#registerMember(casestudy.business.domain.Member)
	 * 
	 * MemberDao 객체를 사용해 인자로 받은 회원 정보를 등록한다.
     *  1.1. 중복된 memberID를 가진 기존 회원이 존재할 경우 등록하지 않고 DataDuplicatedException을 발생시킨다.
     *      (MemberDao의 memberIDExists() 메소드 활용)
     *  1.2. 메소드 선언부에 DataDuplicatedException 발생 가능함을 표시한다.(throws DataDuplicatedException)
	 */
	@Override
	public void registerMember(Member member) throws DataDuplicatedException {
		if (memberDataAccess.memberIDExists(member.getMemberID())) {
			throw new DataDuplicatedException("동일한 memberID를 가진 회원이 있습니다.(" + member.getMemberID() + ")");
		}
		
        memberDataAccess.insertMember(member);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#findMember(java.lang.String)
	 * 
	 * MemberDao 객체를 사용해 인자로 받은 memberID에 해당하는 회원 정보를 찾아서 리턴한다.
     *  2.1. memberID에 해당하는 회원이 존재하지 않을 경우 DataNotFoundException을 발생시킨다.
     *      (MemberDao의 memberIDExists() 메소드 활용)
     *  2.2. 메소드 선언부에 DataNotFoundException 발생 가능함을 표시한다.(throws DataNotFoundException) 
	 */
	@Override
	public Member findMember(String memberID) throws DataNotFoundException {
		if (!memberDataAccess.memberIDExists(memberID)) {
			throw new DataNotFoundException("존재하지 않는 회원입니다.(" + memberID + ")");
		}
		
        return memberDataAccess.selectMember(memberID);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#updateMember(casestudy.business.domain.Member)
	 * 
	 * MemberDao 객체를 사용해 인자로 받은 회원 정보를 갱신한다.
	 *  3. 해당하는 회원이 존재하지 않을 경우 DataNotFoundException을 발생시킨다.
	 */
	@Override
	public void updateMember(Member member) throws DataNotFoundException {
		if (!memberDataAccess.memberIDExists(member.getMemberID())) {
			throw new DataNotFoundException("존재하지 않는 회원입니다.(" + member.getMemberID() + ")");
		}
		
        memberDataAccess.updateMember(member);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#removeMember(casestudy.business.domain.Member)
	 * 
	 * MemberDao 객체를 사용해 인자로 받은 회원 정보를 삭제한다.
	 *  4. 해당하는 회원이 존재하지 않을 경우 DataNotFoundException을 발생시킨다.
	 */
	@Override
	public void removeMember(Member member) throws DataNotFoundException {
		if (!memberDataAccess.memberIDExists(member.getMemberID())) {
			throw new DataNotFoundException("존재하지 않는 회원입니다.(" + member.getMemberID() + ")");
		}
		
        memberDataAccess.deleteMember(member);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#loginCheck(java.lang.String, java.lang.String)
	 * 
	 * MemberDao 객체를 사용해 인자로 받은 memberID, password로 
	 * 로그인 가능 여부를 확인하고, 해당 회원 정보를 리턴한다.
	 */
	@Override
	public Member loginCheck(String memberID, String password) {
        return memberDataAccess.checkMember(memberID, password);
	}

	/* (non-Javadoc)
	 * @see casestudy.business.service.MemberService#getAllMembers()
	 * 
	 * MemberDao 객체를 사용해 모든 회원 정보를 구해서 리턴한다.
	 */
	@Override
	public Member[] getAllMembers() {
		return memberDataAccess.selectAllMembers();
	}

}
