package org.example.dataclasses;

import lombok.*;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter@Setter
public class Company {
    private String name;
    private String catchPhrase;
    private String bs;
}
