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
import com.github.juanmbellini.pocs.quarkus.models.User;
import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.dsl.ResponseBuilder;
import io.specto.hoverfly.junit5.HoverflyExtension;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;
import javax.ws.rs.core.MediaType;

import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.serverError;
import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(HoverflyExtension.class)
class MicroProfileGetUsersTest {


    @Test
    void whenServiceReturnsServerError_thenGatewayExceptionIsThrownWithResponse(final Hoverfly hoverfly) {
        // Given
        final var baseUri = buildBaseUri();
        configureHoverfly(hoverfly, baseUri, serverError());
        final var client = new MicroProfileGetUsers(buildClient(baseUri));
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
        final var client = new MicroProfileGetUsers(buildClient(baseUri));
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
        configureSuccessHoverfly(hoverfly, baseUri, USERS_RESPONSE);
        final var getUsers = new MicroProfileGetUsers(buildClient(baseUri));
        // When
        final var users = getUsers.perform();
        // Then
        assertTrue(Objects.nonNull(users) && users.size() == 2, "The returned object must be a list of two elements");
        assertAll(
                "",
                () -> assertUser(
                        users.get(0),
                        1L,
                        "Leanne Graham",
                        "Bret",
                        "Sincere@april.biz",
                        "Kulas Light",
                        "Apt. 556",
                        "Gwenborough",
                        "92998-3874",
                        -37.3159,
                        81.1496,
                        "1-770-736-8031 x56442",
                        "hildegard.org",
                        "Romaguera-Crona",
                        "Multi-layered client-server neural-net",
                        "harness real-time e-markets"
                ),
                () -> assertUser(
                        users.get(1),
                        2L,
                        "Ervin Howell",
                        "Antonette",
                        "Shanna@melissa.tv",
                        "Victor Plains",
                        "Suite 879",
                        "Wisokyburgh",
                        "90566-7771",
                        -43.9509,
                        -34.4618,
                        "010-692-6593 x09125",
                        "anastasia.net",
                        "Deckow-Crist",
                        "Proactive didactic contingency",
                        "synergize scalable supply-chains"
                )
        );
        hoverfly.verifyAll();
    }


    private static String buildBaseUri() {
        return String.format(BASE_URI_TEMPLATE, UUID.randomUUID().toString());
    }

    private static MicroProfileGetUsers.MicroProfileGetUsersRestClient buildClient(final String baseUri) {
        return RestClientBuilder.newBuilder()
                .baseUri(URI.create(baseUri))
                .build(MicroProfileGetUsers.MicroProfileGetUsersRestClient.class);
    }

    private static void configureSuccessHoverfly(final Hoverfly hoverfly, final String baseUri, final String body) {
        configureHoverfly(hoverfly, baseUri, success(body, MediaType.APPLICATION_JSON));
    }

    private static void configureHoverfly(
            final Hoverfly hoverfly, final String baseUri, final ResponseBuilder responseBuilder) {
        hoverfly.simulate(dsl(
                service(baseUri)
                        .get("/users")
                        .willReturn(responseBuilder)
        ));
    }


    private static void assertUser(
            final User user,
            final Long expectedId,
            final String expectedName,
            final String expectedUsername,
            final String expectedEmail,
            final String expectedStreet,
            final String expectedSuite,
            final String expectedCity,
            final String expectedZipcode,
            final Double expectedLat,
            final Double expectedLon,
            final String expectedPhone,
            final String expectedWebsite,
            final String expectedCompanyName,
            final String expectedCompanyCatchPhrase,
            final String expectedCompanyBs) {
        assertNotNull(user, "The user must not be null");
        assertAll(
                "Some of the user's data is not the expected",
                () -> assertEquals(expectedId, user.getId(), "Id mismatch"),
                () -> assertEquals(expectedName, user.getName(), "Name mismatch"),
                () -> assertEquals(expectedUsername, user.getUsername(), "Username mismatch"),
                () -> assertEquals(expectedEmail, user.getEmail(), "Email mismatch"),
                () -> {
                    final var address = user.getAddress();
                    assertNotNull(address, "The user's address must not be null");
                    assertAll(
                            "Address mismatch",
                            () -> assertEquals(expectedStreet, address.getStreet(), "Street mismatch"),
                            () -> assertEquals(expectedSuite, address.getSuite(), "Suite mismatch"),
                            () -> assertEquals(expectedCity, address.getCity(), "City mismatch"),
                            () -> assertEquals(expectedZipcode, address.getZipcode(), "Zipcode mismatch"),
                            () -> {
                                final var geo = address.getGeoLocation();
                                assertNotNull(geo, "The user address' geo location must not be null");
                                assertAll(
                                        "Geolocation mismatch",
                                        () -> assertEquals(expectedLat, geo.getLatitude(), "Latitude mismatch"),
                                        () -> assertEquals(expectedLon, geo.getLongitude(), "Longitude mismatch")
                                );
                            }
                    );
                },
                () -> assertEquals(expectedPhone, user.getPhone(), "Phone mismatch"),
                () -> assertEquals(expectedWebsite, user.getWebsite(), "Webiste mismatch"),
                () -> {
                    final var company = user.getCompany();
                    assertNotNull(company, "The user's company must not be null");
                    assertAll(
                            "Company mismatch",
                            () -> assertEquals(expectedCompanyName, company.getName(), "Name mismatch"),
                            () -> assertEquals(expectedCompanyCatchPhrase, company.getCatchPhrase(), "Catchphrase mismatch"),
                            () -> assertEquals(expectedCompanyBs, company.getBs(), "Bs mismatch")
                    );

                }

        );
    }


    private static final String BASE_URI_TEMPLATE = "http://%s:8000";


    private static final String USERS_RESPONSE = "" +
            "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"Leanne Graham\",\n" +
            "    \"username\": \"Bret\",\n" +
            "    \"email\": \"Sincere@april.biz\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"Kulas Light\",\n" +
            "      \"suite\": \"Apt. 556\",\n" +
            "      \"city\": \"Gwenborough\",\n" +
            "      \"zipcode\": \"92998-3874\",\n" +
            "      \"geo\": {\n" +
            "        \"lat\": \"-37.3159\",\n" +
            "        \"lng\": \"81.1496\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"phone\": \"1-770-736-8031 x56442\",\n" +
            "    \"website\": \"hildegard.org\",\n" +
            "    \"company\": {\n" +
            "      \"name\": \"Romaguera-Crona\",\n" +
            "      \"catchPhrase\": \"Multi-layered client-server neural-net\",\n" +
            "      \"bs\": \"harness real-time e-markets\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2,\n" +
            "    \"name\": \"Ervin Howell\",\n" +
            "    \"username\": \"Antonette\",\n" +
            "    \"email\": \"Shanna@melissa.tv\",\n" +
            "    \"address\": {\n" +
            "      \"street\": \"Victor Plains\",\n" +
            "      \"suite\": \"Suite 879\",\n" +
            "      \"city\": \"Wisokyburgh\",\n" +
            "      \"zipcode\": \"90566-7771\",\n" +
            "      \"geo\": {\n" +
            "        \"lat\": \"-43.9509\",\n" +
            "        \"lng\": \"-34.4618\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"phone\": \"010-692-6593 x09125\",\n" +
            "    \"website\": \"anastasia.net\",\n" +
            "    \"company\": {\n" +
            "      \"name\": \"Deckow-Crist\",\n" +
            "      \"catchPhrase\": \"Proactive didactic contingency\",\n" +
            "      \"bs\": \"synergize scalable supply-chains\"\n" +
            "    }\n" +
            "  }" +
            "]";
}
