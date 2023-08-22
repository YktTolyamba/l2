package org.example.dataclasses;

import lombok.*;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter@Setter
public class UserData implements Comparable<UserData> {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    @Override
    public int compareTo(UserData o) {
        return this.id - o.getId();
    }
}
