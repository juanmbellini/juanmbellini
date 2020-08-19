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

import com.github.juanmbellini.pocs.quarkus.exceptions.GatewayException;
import com.github.juanmbellini.pocs.quarkus.models.Album;
import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.dsl.ResponseBuilder;
import io.specto.hoverfly.junit5.HoverflyExtension;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.Objects;
import java.util.UUID;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.serverError;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(HoverflyExtension.class)
class MicroProfileGetAlbumsTest {


    @Test
    void whenServiceReturnsServerError_thenGatewayExceptionIsThrownWithResponse(final Hoverfly hoverfly) {
        // Given
        final var baseUri = buildBaseUri();
        configureHoverfly(hoverfly, baseUri, serverError());
        final var client = new MicroProfileGetAlbums(buildClient(baseUri));
        // When
        final Executable executable = client::perform;
        // Then
        final var gatewayException = assertThrows(GatewayException.class, executable);
        assertEquals(
                500,
                gatewayException.getResponse().getStatus(),
                "The thrown GatewayException must contain a response with status code 500"
        );
        hoverfly.verifyAll();
    }

    @Test
    void whenServiceReturnsInvalidJson_thenGatewayExceptionIsThrown(final Hoverfly hoverfly) {
        // Given
        final var baseUri = buildBaseUri();
        configureSuccessHoverfly(hoverfly, baseUri, "{This is an invalid Json]");
        final var client = new MicroProfileGetAlbums(buildClient(baseUri));
        // When
        final Executable executable = client::perform;
        // Then
        assertThrows(GatewayException.class, executable);
        hoverfly.verifyAll();
    }

    @Test
    void whenServiceReturnsData_thenClientReturnsExpected(final Hoverfly hoverfly) {
        // Given
        final var baseUri = buildBaseUri();
        configureSuccessHoverfly(hoverfly, baseUri, ALBUMS_RESPONSE);
        final var getAlbums = new MicroProfileGetAlbums(buildClient(baseUri));
        // When
        final var albums = getAlbums.perform();
        // Then
        assertTrue(Objects.nonNull(albums) && albums.size() == 3, "The returned object must be a list of three elements");
        assertAll(
                "",
                () -> assertAlbum(
                        albums.get(0),
                        100L,
                        "quidem molestiae enim",
                        1L
                ),
                () -> assertAlbum(
                        albums.get(1),
                        200L,
                        "sunt qui excepturi placeat culpa",
                        3L
                ),
                () -> assertAlbum(
                        albums.get(2),
                        300L,
                        "Harry Potter",
                        3L
                )
        );
        hoverfly.verifyAll();
    }


    private static String buildBaseUri() {
        return String.format(BASE_URI_TEMPLATE, UUID.randomUUID().toString());
    }

    private static MicroProfileGetAlbums.MicroProfileGetAlbumsRestClient buildClient(final String baseUri) {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create(baseUri))
                .build(MicroProfileGetAlbums.MicroProfileGetAlbumsRestClient.class);
    }

    private static void configureSuccessHoverfly(final Hoverfly hoverfly, final String baseUri, final String body) {
        configureHoverfly(hoverfly, baseUri, success(body, MediaType.APPLICATION_JSON));
    }

    private static void configureHoverfly(
            final Hoverfly hoverfly, final String baseUri, final ResponseBuilder responseBuilder) {
        hoverfly.simulate(dsl(
                service(baseUri)
                        .get("/albums")
                        .willReturn(responseBuilder)
        ));
    }


    private static void assertAlbum(
            final Album album,
            final Long expectedId,
            final String expectedTitle,
            final Long expectedUserId) {
        assertNotNull(album, "The album must not be null");
        assertAll(
                "Some of the album's data is not the expected",
                () -> assertEquals(expectedId, album.getId(), "Id mismatch"),
                () -> assertEquals(expectedTitle, album.getTitle(), "Title mismatch"),
                () -> assertEquals(expectedUserId, album.getUserId(), "User id mismatch")
        );
    }


    private static final String BASE_URI_TEMPLATE = "http://%s:8000";


    private static final String ALBUMS_RESPONSE = "" +
            "[\n" +
            "    {\n" +
            "        \"id\": 100,\n" +
            "        \"title\": \"quidem molestiae enim\",\n" +
            "        \"userId\": 1\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 200,\n" +
            "        \"title\": \"sunt qui excepturi placeat culpa\",\n" +
            "        \"userId\": 3\n" +
            "    },\n" +
            "    {\n" +
            "        \"id\": 300,\n" +
            "        \"title\": \"Harry Potter\",\n" +
            "        \"userId\": 3\n" +
            "    }\n" +
            "]";
}
