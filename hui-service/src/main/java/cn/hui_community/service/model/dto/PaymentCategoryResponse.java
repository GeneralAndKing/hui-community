package cn.hui_community.service.model.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCategoryResponse {

    private String id;

    private String name;

    private String icon;
}
