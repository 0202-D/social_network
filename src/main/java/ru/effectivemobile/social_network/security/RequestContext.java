package ru.effectivemobile.social_network.security;

import org.springframework.web.context.request.RequestContextHolder;
import ru.effectivemobile.social_network.model.User;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

public class RequestContext {

    private static final String CURRENT_USER = "CURRENT_USER";

    public static User getCurrentUser() {
        return (User) RequestContextHolder.currentRequestAttributes().getAttribute(CURRENT_USER, SCOPE_REQUEST);
    }

    public static void setCurrentUser(User user) {
        RequestContextHolder.currentRequestAttributes().setAttribute(CURRENT_USER, user, SCOPE_REQUEST);
    }
}
