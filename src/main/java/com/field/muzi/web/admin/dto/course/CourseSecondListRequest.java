package com.field.muzi.web.admin.dto.course;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseSecondListRequest {

        private String type;
        private String keyword;



}
