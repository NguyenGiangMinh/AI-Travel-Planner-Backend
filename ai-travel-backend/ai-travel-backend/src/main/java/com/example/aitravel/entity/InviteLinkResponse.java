package com.example.aitravel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteLinkResponse {
    // Class này phải khớp với InviteLinkResponse.java
    // bên phía Android
    private String link;
}