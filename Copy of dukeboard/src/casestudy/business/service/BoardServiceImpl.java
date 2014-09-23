package casestudy.business.service;

import java.util.Map;

import casestudy.business.domain.Board;

public class BoardServiceImpl implements BoardService {
	
	private BoardDao boardDataAccess;
	 public BoardServiceImpl() {
	    	boardDataAccess = new casestudy.dataaccess.BoardDaoImpl();
	    }
	@Override
	public Board readBoard(int num) throws DataNotFoundException {
		// TODO Auto-generated method stub
		boardDataAccess.addReadCount(num);
		return boardDataAccess.selectBoard(num);
		
	}

	@Override
	public Board findBoard(int num) throws DataNotFoundException {
		// TODO Auto-generated method stub
		return boardDataAccess.selectBoard(num);
	}

	@Override
	public Board[] getBoardList(Map<String, Object> searchInfo) {
		return boardDataAccess.selectBoardList(searchInfo).toArray(new Board[0]);
	}

	@Override
	public int getBoardCount(Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return 	boardDataAccess.selectBoardCount(searchInfo);
	}

	@Override
	public void writeBoard(Board board) {
		boardDataAccess.insertBoard(board);
	}

	@Override
	public void updateBoard(Board board) throws DataNotFoundException {
		boardDataAccess.updateBoard(board);

	}

	@Override
	public void removeBoard(int num) throws DataNotFoundException {
		boardDataAccess.deleteBoard(num);

	}

}
