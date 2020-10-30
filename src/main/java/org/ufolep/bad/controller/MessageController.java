package org.ufolep.bad.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.ufolep.bad.dto.MessageDto;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@Controller
public class MessageController {
	private List<MessageDto> messageList = new ArrayList<MessageDto>();
 
	@PostMapping("/message")
	public String postMessage(@ModelAttribute MessageDto newMessage) {
		messageList.add(newMessage);
		return "redirect:message";
	}

	@GetMapping("/message")
	public String showMessage(Model model) {
		model.addAttribute("messageList", messageList);
		model.addAttribute("newMessage", new MessageDto());
		return "message";
	}
}