package com.project.urlshortner.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClickEventDTO {

    private Long count;
    private LocalDate createdDate;

}
