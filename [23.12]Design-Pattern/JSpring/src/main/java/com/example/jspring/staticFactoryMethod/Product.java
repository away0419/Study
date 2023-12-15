package com.example.jspring.staticFactoryMethod;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
public class Product {
    private Long id;
    private String name;
}
