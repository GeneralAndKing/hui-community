package cn.hui_community.service.controller.admin;

import cn.hui_community.service.model.PaymentCategory;
import cn.hui_community.service.model.dto.PaymentCategoryResponse;
import cn.hui_community.service.repository.PaymentCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment-category")
public class PaymentCategoryController {
    private final PaymentCategoryRepository paymentCategoryRepository;

    @GetMapping("")
    public List<PaymentCategoryResponse> all() {
        return paymentCategoryRepository.findAll().stream().map(PaymentCategory::toResponse).toList();
    }
}
