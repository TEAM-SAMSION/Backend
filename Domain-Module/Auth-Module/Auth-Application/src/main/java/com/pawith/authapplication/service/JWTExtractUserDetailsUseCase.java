package com.pawith.authapplication.service;

public interface JWTExtractUserDetailsUseCase<T> {
    T extract(final String token);
}
