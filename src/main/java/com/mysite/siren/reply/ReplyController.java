package com.mysite.siren.reply;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysite.siren.board.Board;
import com.mysite.siren.board.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/reply")
@RequiredArgsConstructor
@Controller
public class ReplyController {

	private final BoardService boardService;
	private final ReplyService replyService;
	
	@PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,@Valid ReplyForm ReplyForm, BindingResult bindingResult) {
        Board board = this.boardService.getBoard(id);
        if(bindingResult.hasErrors()) {
        	model.addAttribute("board",board);
        	return "board_detail";
        }
        this.replyService.create(board, ReplyForm.getContent());
        return String.format("redirect:/board/detail/%s", id);
    }
}
