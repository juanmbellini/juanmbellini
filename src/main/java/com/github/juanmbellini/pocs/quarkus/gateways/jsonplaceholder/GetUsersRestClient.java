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

import com.github.juanmbellini.pocs.quarkus.models.Address;
import com.github.juanmbellini.pocs.quarkus.models.Company;
import com.github.juanmbellini.pocs.quarkus.models.GeoLocation;
import com.github.juanmbellini.pocs.quarkus.models.User;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
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
@RegisterForReflection
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
    @AllArgsConstructor
    class UserDto {
        private final Long id;
        private final String name;
        private final String username;
        private final String email;
        private final AddressDto address;
        private final String phone;
        private final String website;
        private final CompanyDto company;

        public User toUser() {
            return User.builder()
                    .id(id)
                    .name(name)
                    .username(username)
                    .email(email)
                    .address(address.toAddress())
                    .phone(phone)
                    .website(website)
                    .company(company.toCompany())
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    class GeoLocationDto {
        private final double lng;
        private final double lat;

        public GeoLocation toGeoLocation() {
            return GeoLocation.builder()
                    .latitude(lat)
                    .longitude(lng)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    class CompanyDto {
        private final String name;
        private final String catchPhrase;
        private final String bs;

        public Company toCompany() {
            return Company.builder()
                    .name(name)
                    .catchPhrase(catchPhrase)
                    .bs(bs)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    class AddressDto {
        private final String street;
        private final String suite;
        private final String city;
        private final String zipcode;
        private final GeoLocationDto geo;

        public Address toAddress() {
            return Address.builder()
                    .street(street)
                    .suite(suite)
                    .city(city)
                    .zipcode(zipcode)
                    .geoLocation(geo.toGeoLocation())
                    .build();
        }
    }
}
