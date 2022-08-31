package hello.itemservice.controller.validation.controller.v4;

import hello.itemservice.web.form.ItemSaveForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

    @PostMapping("/add")
    public Object addItem(@Validated @RequestBody ItemSaveForm form, BindingResult bindingResult) {

        // Validation
        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors = {}", bindingResult);
            return bindingResult.getAllErrors();
        }

        // Business Logic
        return form;
    }
}
