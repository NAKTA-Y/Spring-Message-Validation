package hello.itemservice.controller.validation.controller.v2;

import hello.itemservice.controller.validation.ItemValidator;
import hello.itemservice.domain.item.Item;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/validation/v2")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemV2Controller {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }

    @GetMapping("/list")
    public String itemList(Model model) {

        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);

        return "/v2/list";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("item", new Item());

        return "/v2/register";
    }

    @PostMapping("/register")
    public String registerItemV2(@Validated @ModelAttribute Item item, BindingResult bindingResult, Model model) {

        // Validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "/v2/register";
        }

        // Business Logic
        itemRepository.save(item);

        return "/v2/read";
    }

    @GetMapping("/read/{itemId}")
    public String readItem(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findOne(itemId);

        model.addAttribute("item", item);

        return "/v2/read";
    }

    @GetMapping("/modify/{itemId}")
    public String modifyForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findOne(itemId);

        model.addAttribute("item", item);

        return "/v2/modify";
    }

    @PostMapping("/modify")
    public String modify(@ModelAttribute Item updateItem, BindingResult bindingResult, Model model) {

        // Validation
        if (itemValidator.supports(Item.class)) {
            itemValidator.validate(updateItem, bindingResult);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "/v2/modify";
        }

        // Business Logic
        itemRepository.modify(updateItem);

        return "/v2/read";
    }


    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable Long itemId) {

        itemRepository.delete(itemId);

        return "redirect:/validation/v2/list";
    }
}
