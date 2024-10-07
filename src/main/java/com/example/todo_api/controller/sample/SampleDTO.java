package com.example.todo_api.controller.sample;

import lombok.Value;

import java.lang.reflect.Constructor;
import java.time.LocalDateTime;

@Value
public class SampleDTO {
    String content;
    LocalDateTime timestamp;
}
