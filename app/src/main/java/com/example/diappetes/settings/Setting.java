package com.example.diappetes.settings;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class Setting {
    private final int id;
    private final String description;
    @Setter
    private boolean enabled = false;
}
