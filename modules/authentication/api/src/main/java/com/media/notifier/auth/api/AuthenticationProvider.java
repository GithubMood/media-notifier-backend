package com.media.notifier.auth.api;

import com.media.notifier.auth.api.exception.AuthenticationException;
import com.media.notifier.common.security.UserDetails;

public interface AuthenticationProvider {
    UserDetails authenticate(String login, String password) throws AuthenticationException;
}
