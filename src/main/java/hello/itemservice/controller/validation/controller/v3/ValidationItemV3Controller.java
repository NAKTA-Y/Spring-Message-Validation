package hello.itemservice.controller.validation.controller.v3;

import hello.itemservice.controller.validation.ItemValidator;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.check.SaveCheck;
import hello.itemservice.domain.item.check.UpdateCheck;
import hello.itemservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/validation/v3")
@RequiredArgsConstructor
@Slf4j
public class ValidationItemV3Controller {

    private final ItemRepository itemRepository;

    @GetMapping("/list")
    public String itemList(Model model) {

        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);

        return "/v3/list";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("item", new Item());

        return "/v3/register";
    }

    @PostMapping("/register")
    public String registerItem(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, Model model) {

        // Validation
        totalPriceValidation(item, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "/v3/register";
        }

        // Business Logic
        itemRepository.save(item);

        return "/v3/read";
    }

    @GetMapping("/read/{itemId}")
    public String readItem(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findOne(itemId);

        model.addAttribute("item", item);

        return "/v3/read";
    }

    @GetMapping("/modify/{itemId}")
    public String modifyForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findOne(itemId);

        model.addAttribute("item", item);

        return "/v3/modify";
    }

    @PostMapping("/modify")
    public String modify(@Validated(UpdateCheck.class) @ModelAttribute Item updateItem, BindingResult bindingResult, Model model) {

        totalPriceValidation(updateItem, bindingResult);

        // Validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult);
            return "/v3/modify";
        }

        // Business Logic
        itemRepository.modify(updateItem);

        return "/v3/read";
    }


    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable Long itemId) {

        itemRepository.delete(itemId);

        return "redirect:/validation/v3/list";
    }

    private void totalPriceValidation(Item item, BindingResult bindingResult) {
        Integer itemPrice = item.getPrice();
        Integer itemQuantity = item.getQuantity();

        if (itemPrice != null && itemQuantity != null) {
            int totalPrice = itemPrice * itemQuantity;
            if (totalPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, totalPrice}, null);
            }
        }
    }
}
