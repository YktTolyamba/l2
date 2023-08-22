package org.example.dataclasses;

import lombok.*;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter@Setter
public class Address {
    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Geo geo;
}
