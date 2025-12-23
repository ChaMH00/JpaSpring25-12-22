package com.example.demo.service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements  BoardService{
    private final BoardRepository boardRepository;

    @Override
    public Long insert(BoardDTO boardDTO) {
        // save() : 저장
        // 저장 객체는 Entity (Board)
        // DTO => Entity 로 변환
        Board board = convertDtoToEntity(boardDTO); // 변환
        Long bno = boardRepository.save(board).getBno(); // 저장

        return bno;
    }


//    @Override
//    public List<BoardDTO> getList() {
//        /*DB에서 가져오는 return은 List<Board> => List<BoardDTO> 로 변환필요
//        * findAll() => 전체 값 리턴
//        * select * from board order by bno desc
//        * 정렬 : Sort.by(Sort.direction.DESC, "정렬기준 칼럼명")
//        * */
//
//        List<Board> boardList = boardRepository.findAll(
//                Sort.by(Sort.Direction.DESC, "bno"));
//
//        List<BoardDTO> boardDTOList = boardList.stream()
//                .map(board -> convertEntityToDto(board))
//                .toList();
//
//        return boardDTOList;
//    }

    @Override
    public Page<BoardDTO> getList(int pageNo) {
        // limit 시작번지, 개수 => 번지는 0부터 시작
        // pageNo = 1 => limit 0,10 => pageNo -1 => 0부터 인식되게 처리
        Pageable pageable = PageRequest.of(pageNo-1, 10, Sort.by("bno" ).descending());
        Page<Board> pageList = boardRepository.findAll(pageable);
        log.info(">>>> pageList >> {}", pageList);
        Page<BoardDTO> boardDTOPage = pageList.map(this::convertEntityToDto);
        return boardDTOPage;
    }

}
