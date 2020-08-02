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

import com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder.PhotosGateway;
import com.github.juanmbellini.pocs.quarkus.models.Photo;
import com.github.juanmbellini.pocs.quarkus.usecases.GetPhotos;
import lombok.AllArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
@AllArgsConstructor
class GetPhotosImpl implements GetPhotos {

    private final PhotosGateway photosGateway;


    @Override
    public List<Photo> get() {
        return photosGateway.getPhotos();
    }
}
