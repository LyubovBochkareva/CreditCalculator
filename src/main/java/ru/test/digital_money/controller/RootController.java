package ru.test.digital_money.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static ru.test.digital_money.util.validation.PathValue.DEFAULT;
import static ru.test.digital_money.util.validation.PathValue.DEFAULT_HTML;

/**
 * @author Lyubov Bochkareva
 * @since 25.06.20.
 */

@Controller
public class RootController {

	@RequestMapping(value = DEFAULT, method = RequestMethod.GET)
	public String listNews() {
		return DEFAULT_HTML;
	}
}
