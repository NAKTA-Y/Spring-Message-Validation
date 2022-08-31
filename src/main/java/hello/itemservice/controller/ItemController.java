package hello.itemservice.controller;

import hello.itemservice.domain.item.Item;
import hello.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ItemController {

    private final ItemRepository itemRepository = ItemRepository.getInstance();


    @GetMapping("list")
    public String itemList(Model model) {

        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);

        return "/list";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("item", new Item());

        return "/register";
    }

    @PostMapping("/register")
    public String registerItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {

        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/read/{itemId}";
    }

    @GetMapping("/read/{itemId}")
    public String readItem(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findOne(itemId);

        model.addAttribute("item", item);

        return "/read";
    }

    @GetMapping("/modify/{itemId}")
    public String modifyForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findOne(itemId);

        model.addAttribute("item", item);

        return "/modify";
    }

    @PostMapping ("/modify")
    public String modify(@ModelAttribute Item item) {

        itemRepository.modify(item);

        return "/read";
    }

    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable Long itemId) {

        itemRepository.delete(itemId);

        return "redirect:/";
    }
}
