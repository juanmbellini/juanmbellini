package com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder.impl.dtos;

import com.github.juanmbellini.pocs.quarkus.models.Album;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
public class AlbumDto {

    private final Long id;
    private final String title;
    private final Long userId;

    public Album toAlbum() {
        return Album.builder()
                .id(id)
                .title(title)
                .userId(userId)
                .build();
    }
}
