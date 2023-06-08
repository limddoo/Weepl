package com.weepl.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.dto.BoardConsDto;
import com.weepl.dto.BoardConsFormDto;
import com.weepl.dto.BoardConsReplyDto;
import com.weepl.entity.BoardCons;
import com.weepl.entity.BoardConsNmem;
import com.weepl.entity.BoardConsReply;
import com.weepl.entity.Member;
import com.weepl.repository.BoardConsNmemRepository;
import com.weepl.repository.BoardConsReplyRepository;
import com.weepl.repository.BoardConsRepository;
import com.weepl.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardConsService {

	private final BoardConsRepository boardConsRepository;
	private final BoardConsNmemRepository boardConsNmemRepository;
	private final MemberRepository memberRepository;
	private final BoardConsReplyRepository boardConsReplyRepository;

	public Long saveCons(BoardConsFormDto boardConsFormDto) throws Exception {
		BoardCons boardCons = boardConsFormDto.createCons();
		boardCons.setDel_yn("N");
		boardCons.setRes_yn("N");
		boardConsRepository.save(boardCons);

		System.out.println("asdjfhsakdjfbskadbfkusdhfkusdhf" + boardCons.getCd());
		return boardCons.getCd();
	}

//	@Transactional
//	public void saveBoardConsNmem(BoardConsNmem boardConsNmem) {
//	    // BoardCons 객체 생성
//	    BoardCons boardCons = new BoardCons();
//	    // 필요한 속성 설정
//	    boardCons.setTitle("제목");
//	    boardCons.setContent("내용");
//	    // 다른 속성 설정...
//	    
//	    // BoardConsNmem 객체와 BoardCons 객체의 관계 설정
////	    boardConsNmem.setBoardCons(boardCons);
//	    
//	    // BoardConsNmem 객체 저장
//	    boardConsNmemRepository.save(boardConsNmem);
//	}

	public Long saveNmCons(BoardConsFormDto boardConsFormDto) throws Exception {
		BoardCons boardCons = boardConsFormDto.createCons();
		boardCons.setDel_yn("N");
		boardCons.setRes_yn("N");
		BoardConsNmem boardConsNmem = boardConsFormDto.createConsNmem(boardConsFormDto, boardCons);
		BoardConsNmem savedBoardConsNmem = boardConsNmemRepository.save(boardConsNmem);
		boardCons.setBoardConsNmem(savedBoardConsNmem);
		BoardCons savedBoardCons = boardConsRepository.save(boardCons);
		return savedBoardCons.getCd();
	}

	@Transactional(readOnly = true)
	public Page<BoardConsDto> getBoardConsPage(Pageable pageable) {
		Map<String, Object> result = boardConsRepository.getBoardConsList(pageable);
		List<BoardCons> boardConsList = (List) result.get("content");
		List<BoardConsDto> boardConsDtoList = new ArrayList<>();
		for (BoardCons boardCons : boardConsList) {
			BoardConsDto boardConsDto = createBoardConsListDto(boardCons);
			boardConsDtoList.add(boardConsDto);
		}
		return new PageImpl<>(boardConsDtoList, pageable, (Long) result.get("total"));
	}

	@Transactional(readOnly = true)
	public List<BoardConsDto> boardList() {

		List<BoardCons> boardCons = boardConsRepository.findAll();
		List<BoardConsDto> boardConsList = new ArrayList<>();
		for (BoardCons board : boardCons) {
			BoardConsDto boardConsDto = new BoardConsDto();
			boardConsDto.setCd(board.getCd());
			boardConsDto.setTitle(board.getTitle());
			boardConsDto.setReg_dt(board.getRegDt());
			if (board.getMember() == null) {

				BoardConsNmem Nmem = board.getBoardConsNmem();
				if (Nmem != null) {
					boardConsDto.setName(Nmem.getName());

				}
			} else {
				Member member = memberRepository.findByCd(board.getMember().getCd());

				if (member != null) {
					boardConsDto.setName(member.getName());

				}
			}
			boardConsList.add(boardConsDto);
		}
		return boardConsList;
	}

	@Transactional(readOnly = true)
	public BoardCons getBoardConsDtl(Long cd) {
//	    BoardConsFormDto boardConsFormDto = boardConsRepository.findByCd(cd);
//	    BoardCons boardCons = boardConsFormDto.toBoardCons();
		return boardConsRepository.findByCd(cd);
	}

	@Transactional
	public BoardCons ModConsForm(Long cd) {
//	    BoardConsFormDto boardConsFormDto = boardConsRepository.findByCd(cd);
//	    BoardCons boardCons = boardConsFormDto.toBoardCons();
		return boardConsRepository.findByCd(cd);
	}

	public Long updateCons(BoardConsFormDto boardConsFormDto) throws Exception {
		BoardCons boardCons = boardConsRepository.findByCd(boardConsFormDto.getCd());
		boardCons.updateCons(boardConsFormDto);

		return boardCons.getCd();
	}

	public Long deleteCons(Long cd) {
		return boardConsRepository.deleteByCd(cd);

	}

	public Member findMember(String id) {
		return memberRepository.findById(id);
	}

	public Member findBoardMember(Long boardCd) {
		BoardCons boardCons = boardConsRepository.getById(boardCd);
		if (boardCons != null) {
			return memberRepository.findByCd(boardCons.getMember().getCd());
		}
		return null;
	}

	public String confirmPwd(Long boardConsCd, String pwd) {
		BoardCons boardCons = boardConsRepository.findByCd(boardConsCd);
		if (boardCons != null) {
			if (pwd.equals(boardCons.getPwd())) {
				return "ENTER";
			}
		}
		return null;
	}

	public BoardConsDto createBoardConsListDto(BoardCons boardCons) {
		BoardConsDto boardConsDto = new BoardConsDto();
		boardConsDto.setCd(boardCons.getCd());
		boardConsDto.setTitle(boardCons.getTitle());
		boardConsDto.setReg_dt(boardCons.getRegDt());
		boardConsDto.setRes_yn(boardCons.getRes_yn());
		if (boardCons.getMember() == null) {
			BoardConsNmem Nmem = boardCons.getBoardConsNmem();
			if (Nmem != null) {
				boardConsDto.setName(Nmem.getName());
			}
		} else {
			Member member = memberRepository.findByCd(boardCons.getMember().getCd());

			if (member != null) {
				boardConsDto.setName(member.getName());

			}
		}
		return boardConsDto;
	}

	public BoardCons replyBoardCons(BoardConsReplyDto boardConsReplyDto) {
		BoardCons boardCons = boardConsReplyDto.getBoardCons();
		boardCons.updateConsResYn();

		BoardCons savedBoardCons = boardConsRepository.save(boardCons);
		BoardConsReply boardConsReply = BoardConsReply.createBoardReply(boardConsReplyDto);
		boardConsReplyRepository.save(boardConsReply);

		return savedBoardCons;
	}

	public BoardConsReply getBoardConsReply(BoardCons boardCons) {
		BoardConsReply savedBoardConsReply = boardConsReplyRepository.findByBoardCons(boardCons);

		return savedBoardConsReply;
	}
}
