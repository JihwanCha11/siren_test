package com.mysite.siren.board;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.siren.reply.ReplyForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/list")
	public String list(Model model,@RequestParam(value="page", defaultValue="0") int page) {
		Page<Board> paging = this.boardService.getList(page);
		model.addAttribute("paging", paging);
		return "board_list";
	}
	@GetMapping(value = "/detail/{id}")
	public String detail(Model model, @PathVariable("id") Integer id,ReplyForm replyForm) {
		Board board = this.boardService.getBoard(id);
		model.addAttribute("board", board);
		return "board_detail";
	}
	@GetMapping("/create")
    public String questionCreate(BoardForm BoardForm) {
        return "board_form";
    }
	@PostMapping("/create")
    public String questionCreate(@Valid BoardForm boardForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "board_form";
		}
		this.boardService.create(boardForm.getSubject(),boardForm.getContent());
        return "redirect:/board/list"; // 질문 저장후 질문목록으로 이동
    }
	@GetMapping("/index")
    public String index() {
        return "index";
    }
}
