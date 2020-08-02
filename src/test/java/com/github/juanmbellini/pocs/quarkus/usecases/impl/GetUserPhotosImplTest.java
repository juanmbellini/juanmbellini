/*
 * Copyright 2020 Juan Marcos Bellini
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.juanmbellini.pocs.quarkus.usecases.impl;

import com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder.AlbumsGateway;
import com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder.PhotosGateway;
import com.github.juanmbellini.pocs.quarkus.models.Album;
import com.github.juanmbellini.pocs.quarkus.models.Photo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class GetUserPhotosImplTest {


    @Test
    void whenUserIdIsNull_thenThrowNullPointerException() {
        // Given
        final var useCase = new GetUserPhotosImpl(null, null);
        // When
        final Executable executable = () -> useCase.apply(null);
        // Then
        assertThrows(
                NullPointerException.class,
                executable,
                "When passing a null user id, the use case must throw a NullPointerException"
        );
    }

    @Test
    void whenThereAreNoAlbumsForUser_thenReturnEmptyList() {
        // Given
        final var userId = new Random().nextLong();
        final var albumsGateway = mock(AlbumsGateway.class);
        when(albumsGateway.getUserAlbums(userId)).thenReturn(List.of());
        final var photosGateway = mock(PhotosGateway.class);
        final var useCase = new GetUserPhotosImpl(albumsGateway, photosGateway);
        // When
        final var returned = useCase.apply(userId);
        // Then
        assertEquals(Collections.emptyList(), returned, "The returned list is not the expected");
        verify(albumsGateway, only()).getUserAlbums(userId);
        verifyNoInteractions(photosGateway);
    }

    @Test
    void whenUserIdIsNotNull_thenReturnListOfAlbums() {
        // Given
        final var userId = new Random().nextLong();
        final var album1 = getAlbum();
        final var album2 = getAlbum();
        final var album3 = getAlbum();
        final var albums = List.of(album1, album2, album3);
        final var albumsGateway = mock(AlbumsGateway.class);
        when(albumsGateway.getUserAlbums(userId)).thenReturn(albums);
        final var photosGateway = mock(PhotosGateway.class);
        final var albumIds = albums.stream().map(Album::getId).collect(Collectors.toList());
        final var list = List.<Photo>of();
        when(photosGateway.getAlbumsPhotos(albumIds)).thenReturn(list);
        final var useCase = new GetUserPhotosImpl(albumsGateway, photosGateway);
        // When
        final var returned = useCase.apply(userId);
        // Then
        assertEquals(list, returned, "The returned list is not the expected");
        verify(albumsGateway, only()).getUserAlbums(userId);
        verify(photosGateway, only()).getAlbumsPhotos(albumIds);
    }


    private static Album getAlbum() {
        return Album.builder().id(new Random().nextLong()).build();
    }
}
