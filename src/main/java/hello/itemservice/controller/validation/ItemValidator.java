package hello.itemservice.controller.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Item item = (Item) target;

        String itemName = item.getName();
        Integer itemPrice = item.getPrice();
        Integer itemQuantity = item.getQuantity();

        // bindingResult.getObjectName()으로 오브젝트 이름을 추출하여 MessageCodesResolver 호출
        if (!StringUtils.hasText(itemName)) {
//            bindingResult.addError((new FieldError("item", "name", itemName, false, new String[]{"required.item.name"}, null, null)));
            errors.rejectValue("name", "required");
        }
        if (itemPrice == null || itemPrice < 1000 || itemPrice > 1000000) {
//            bindingResult.addError((new FieldError("item", "price", itemPrice, false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null)));
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (itemQuantity == null || itemQuantity > 9999) {
//            bindingResult.addError((new FieldError("item", "quantity", itemQuantity, false, new String[]{"max.item.quantity"}, new Object[]{9999}, null)));
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        // Validation (복합 룰)
        if (itemPrice != null && itemQuantity != null) {
            int totalPrice = itemPrice * itemQuantity;
            if (totalPrice < 10000) {
//                bindingResult.addError(new ObjectError("item", new String[]{"global.totalPriceMin"}, new Object[]{10000, totalPrice}, null));
                errors.reject("totalPriceMin", new Object[]{10000, totalPrice}, null);
            }
        }
    }
}
