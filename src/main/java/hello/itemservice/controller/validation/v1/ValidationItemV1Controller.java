package hello.itemservice.controller.validation.v1;

import hello.itemservice.domain.item.Item;
import hello.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/validation/v1")
@Slf4j
public class ValidationItemV1Controller {

    ItemRepository itemRepository = ItemRepository.getInstance();

    @GetMapping("/list")
    public String itemList(Model model) {

        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);

        return "/v1/list";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {

        model.addAttribute("item", new Item());

        return "/v1/register";
    }

    @PostMapping("/register")
    public String registerItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {

        log.info("item.price = {}", item.getPrice());
        log.info("item.quantity = {}", item.getQuantity());

        // 검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();

        // Validation (특정 필드)
        String itemName = item.getName();
        Integer itemPrice = item.getPrice();
        Integer itemQuantity = item.getQuantity();

        if (!StringUtils.hasText(itemName)) {
            errors.put("name", "상품 이름은 필수입니다.");
        }
        if (itemPrice == null || itemPrice < 1000 || itemPrice > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000원 까지 입력 가능합니다.");
        }
        if (itemQuantity == null || itemQuantity > 9999) {
            errors.put("quantity", "수량은 최대 9999개 까지 등록 가능합니다.");
        }

        // Validation (복합 룰)
        if (itemPrice != null && itemQuantity != null) {
            int totalPrice = itemPrice * itemQuantity;
            if (totalPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. (현재 값 = " + totalPrice + "원)");
            }
        }

        // 검증 실패 시 다시 입력 폼으로
        if (isNoErrors(errors)) {
            model.addAttribute("errors", errors);
            return "/v1/register";
        }

        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/validation/v1/read/{itemId}";
    }

    @GetMapping("/read/{itemId}")
    public String readItem(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findOne(itemId);

        model.addAttribute("item", item);

        return "/v1/read";
    }

    @GetMapping("/modify/{itemId}")
    public String modifyForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findOne(itemId);

        model.addAttribute("item", item);

        return "/v1/modify";
    }

    @PostMapping ("/modify")
    public String modify(@ModelAttribute Item item) {

        itemRepository.modify(item);

        return "/v1/read";
    }

    @GetMapping("/delete/{itemId}")
    public String delete(@PathVariable Long itemId) {

        itemRepository.delete(itemId);

        return "redirect:/validation/v1/list";
    }

    private boolean isNoErrors(Map<String, String> error) {
        return !error.isEmpty();
    }
}
