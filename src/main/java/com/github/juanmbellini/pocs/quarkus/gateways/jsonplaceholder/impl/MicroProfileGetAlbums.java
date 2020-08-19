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

package com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder.impl;

import com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder.impl.dtos.AlbumDto;
import com.github.juanmbellini.pocs.quarkus.models.Album;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
class MicroProfileGetAlbums {

    @RestClient
    private final MicroProfileGetAlbumsRestClient microProfileGetAlbumsRestClient;


    List<Album> perform() {
        return MicroProfileRestClientHelper.wrapForGatewayException(
                () -> microProfileGetAlbumsRestClient.perform()
                        .stream()
                        .map(AlbumDto::toAlbum)
                        .collect(Collectors.toList())
        );
    }


    @ApplicationScoped
    @RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com")
    public interface MicroProfileGetAlbumsRestClient {

        @GET
        @Path("/albums")
        @Produces(MediaType.APPLICATION_JSON)
        List<AlbumDto> perform();
    }
}
