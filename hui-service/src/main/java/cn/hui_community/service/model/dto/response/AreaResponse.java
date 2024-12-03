package cn.hui_community.service.model.dto.response;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AreaResponse {

    private String id;

    private String parentId;

    private String code;

    private Integer level;

    private String name;

    private String pinyin;

    private String pinyinPrefix;
}
