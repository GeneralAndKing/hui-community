package cn.hui_community.service.model.dto.response;

import cn.hui_community.service.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
public class MenuResponse {
    private String name;
    private String description;
    private Integer sort;
    private List<ProductResponse> products;


    public static List<MenuResponse> buildArray(List<ProductResponse> products) {
        Map<String, MenuResponse> menuMap = new HashMap<>();
        products.forEach(product -> {
            if (!menuMap.containsKey(product.getCategory().getId())) {
                menuMap.put(product.getCategory().getId(), MenuResponse.builder()
                        .name(product.getCategory().getName())
                        .description(product.getCategory().getDescription())
                        .sort(product.getCategory().getSort())
                        .products(new ArrayList<>())
                        .build());
            }
            menuMap.get(product.getCategory().getId()).getProducts().add(product);
        });
        return menuMap.values().stream()
                .sorted(Comparator.comparing(MenuResponse::getSort))
                .collect(Collectors.toList());
    }
}
