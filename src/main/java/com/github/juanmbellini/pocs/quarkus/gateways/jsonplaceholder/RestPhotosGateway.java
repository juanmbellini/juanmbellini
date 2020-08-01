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

package com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder;

import com.github.juanmbellini.pocs.quarkus.models.Photo;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

/**
 * JSON Placeholder's REST photos gateway.
 */
@ApplicationScoped
@AllArgsConstructor
public class RestPhotosGateway implements PhotosGateway {

    @RestClient
    private final PhotosRestClient.GetPhotos getPhotos;
    @RestClient
    private final PhotosRestClient.GetAlbumPhotos getAlbumPhotos;


    @Override
    public List<Photo> getPhotos() {
        return getPhotos.perform();
    }

    @Override
    public List<Photo> getAlbumsPhotos(final List<Long> albumIds) {
        return getAlbumPhotos.perform(albumIds);
    }
}
