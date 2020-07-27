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

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * REST Client for the GET users endpoint of JSON Placeholder.
 */
@ApplicationScoped
@RegisterRestClient(baseUri = "https://jsonplaceholder.typicode.com")
public interface GetUsersRestClient {

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    List<UserDto> getUsers();


    // ================================================================================================================
    // DTOs
    // ================================================================================================================

    @Getter
    @NoArgsConstructor(force = true)
    class UserDto {
        private final Long id;
        private final String name;
        private final String username;
        private final String email;
        private final AddressDto address;
        private final String phone;
        private final String website;
        private final CompanyDto company;
    }

    @Getter
    @NoArgsConstructor(force = true)
    class GeoLocationDto {
        private final double lng;
        private final double lat;
    }

    @Getter
    @NoArgsConstructor(force = true)
    class CompanyDto {
        private final String name;
        private final String catchPhrase;
        private final String bs;
    }

    @Getter
    @NoArgsConstructor(force = true)
    class AddressDto {
        private final String street;
        private final String suite;
        private final String city;
        private final String zipcode;
        private final GeoLocationDto geo;
    }
}
