package com.vsouza.processorders.dto.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Long userId;
    private String name;
}
