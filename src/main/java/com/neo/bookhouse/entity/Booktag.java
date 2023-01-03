package com.neo.bookhouse.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Booktag {

    private String bookIsbn;

    private Integer bookKind;

    private Integer educationSum;//教育类数量
    
    private Integer literatureArtSum;//文学类数量
    
    private Integer comicHumorSum; //漫画幽默类数量
    
    private Integer youthSum; //青春类
    
    private Integer childrenSum; //童书类
    
    private Integer socialScienceSum; //社会科学类
    
    private Integer lifeSum; //生活类
    
    private Integer technologySum; //科技类
    
    private Integer fictionSum; //科幻小说类
}

