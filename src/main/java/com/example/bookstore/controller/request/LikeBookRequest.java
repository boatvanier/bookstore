package com.example.bookstore.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LikeBookRequest {
    @NotNull
    Long userId;
}
