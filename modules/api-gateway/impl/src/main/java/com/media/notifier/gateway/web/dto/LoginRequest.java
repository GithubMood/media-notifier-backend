package com.media.notifier.gateway.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    String login;

    @ToString.Exclude
    String password;
}
