package cn.hui_community.service.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AreaResponse{

    private String id;

    private String parentId;

    private String code;

    private Integer level;

    private String name;

    private String pinyin;

    private String pinyinPrefix;
}
