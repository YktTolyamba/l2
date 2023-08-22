package org.example.dataclasses;

import lombok.*;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter@Setter
public class Geo {
    private String lat;
    private String lng;
}
