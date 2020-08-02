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
import com.github.juanmbellini.pocs.quarkus.usecases.GetUserPhotos;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
class GetUserPhotosImpl implements GetUserPhotos {

    private final AlbumsGateway albumsGateway;
    private final PhotosGateway photosGateway;


    @Override
    public List<Photo> apply(@NonNull final Long userId) {
        final var albums = albumsGateway.getUserAlbums(userId);
        return albums.isEmpty() ?
                Collections.emptyList() :
                albums.stream()
                        .map(Album::getId)
                        .collect(Collectors.collectingAndThen(Collectors.toList(), photosGateway::getAlbumsPhotos));
    }
}
