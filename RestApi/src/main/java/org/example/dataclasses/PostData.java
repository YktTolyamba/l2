package org.example.dataclasses;

import lombok.*;

import java.util.Objects;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class PostData {
    @NonNull
    private int userId;
    private int id;
    @NonNull
    private String title;
    @NonNull
    private String body;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostData postData = (PostData) o;
        return userId == postData.userId && title.equals(postData.title) && body.equals(postData.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, title, body);
    }
}
