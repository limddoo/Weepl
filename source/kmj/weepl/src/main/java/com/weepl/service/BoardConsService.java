package com.weepl.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weepl.constant.Role;
import com.weepl.dto.BoardConsFormDto;
import com.weepl.dto.BoardConsListDto;
import com.weepl.dto.NoticeSearchDto;
import com.weepl.entity.BoardCons;
import com.weepl.entity.BoardConsNmem;
import com.weepl.entity.Member;
import com.weepl.entity.Notice;
import com.weepl.repository.BoardConsNmemRepository;
import com.weepl.repository.BoradConsRepository;
import com.weepl.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardConsService {

	private final BoradConsRepository boardConsRepository;
	private final BoardConsNmemRepository boardConsNmemRepository;
	private final MemberRepository memberRepository;

	public Long saveCons(BoardConsFormDto boardConsFormDto) throws Exception {
		BoardCons boardCons = boardConsFormDto.createCons();
		boardConsRepository.save(boardCons);

		return boardCons.getCd();
	}

	public Long saveNmCons(BoardConsFormDto boardConsFormDto) throws Exception {
		BoardCons boardCons = boardConsFormDto.createCons();

		BoardCons savedBoardCons = boardConsRepository.save(boardCons);
		BoardConsNmem boardConsNmem = boardConsFormDto.createConsNmem(boardConsFormDto, savedBoardCons);
		System.out.println(boardConsNmem + ", " + boardConsFormDto);
		boardConsNmemRepository.save(boardConsNmem);
		return boardCons.getCd();
	}

	@Transactional(readOnly = true)
	public Page<BoardConsListDto> getBoardConsPage(Pageable pageable){
		Map<String,Object> result = boardConsRepository.getBoardConsList(pageable);
		List<BoardCons> boardConsList = (List)result.get("content");
		List<BoardConsListDto> boardConsDtoList = new ArrayList<>();
		for(BoardCons boardCons : boardConsList) {
			BoardConsListDto boardConsDto = createBoardConsListDto(boardCons);
			boardConsDtoList.add(boardConsDto);
		}
		return new PageImpl<>(boardConsDtoList, pageable, (Long)result.get("total"));
	}
	
	@Transactional(readOnly = true)
	public List<BoardConsListDto> boardList() {

		List<BoardCons> boardCons = boardConsRepository.findAll();
		List<BoardConsListDto> boardConsList = new ArrayList<>();
		for (BoardCons board : boardCons) {
			BoardConsListDto boardConsDto = new BoardConsListDto();
			boardConsDto.setCd(board.getCd());
			boardConsDto.setTitle(board.getTitle());
			boardConsDto.setRegDt(board.getRegDt());
			if (board.getMember() == null) {
				BoardConsNmem Nmem = boardConsNmemRepository.findByBoardConsCd(board.getCd());
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
	public BoardCons BoardConsDtl(Long cd) {
		return boardConsRepository.findByCd(cd);

	}

	@Transactional
	public BoardCons ModConsForm(Long cd) {
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
	
	public BoardConsNmem findNmem(Long cd) {
		return boardConsNmemRepository.findByBoardConsCd(cd);
	}

	public BoardConsListDto createBoardConsListDto(BoardCons boardCons) {
		BoardConsListDto boardConsListDto = new BoardConsListDto();
		boardConsListDto.setCd(boardCons.getCd());
		boardConsListDto.setTitle(boardCons.getTitle());
		boardConsListDto.setRegDt(boardCons.getRegDt());
		if (boardCons.getMember() == null) {
			BoardConsNmem Nmem = boardConsNmemRepository.findByBoardConsCd(boardCons.getCd());
			if (Nmem != null) {
				boardConsListDto.setName(Nmem.getName());

			}
		} else {
			Member member = memberRepository.findByCd(boardCons.getMember().getCd());

			if (member != null) {
				boardConsListDto.setName(member.getName());

			}
		}
		return boardConsListDto;
	}
}
