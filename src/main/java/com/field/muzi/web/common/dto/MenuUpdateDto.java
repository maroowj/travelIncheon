package com.field.muzi.web.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuUpdateDto {

    private int orders;
    private String title;
    private String eng;
    private String description;
    private String info;
    private String url;
    private int position;
    private int depth;
    private int cateorder;
}
