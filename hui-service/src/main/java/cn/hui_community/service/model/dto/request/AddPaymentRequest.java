package cn.hui_community.service.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddPaymentRequest {
    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "category cannot be null")
    private String categoryId;
}
