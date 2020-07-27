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
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON Placeholder's REST users gateway.
 */
@ApplicationScoped
@AllArgsConstructor
public class RestUsersGateway implements UsersGateway {

    @RestClient
    private final GetUsersRestClient getUsersRestClient;


    @Override
    public List<User> getUsers() {
        return getUsersRestClient.getUsers()
                .stream()
                .map(dto -> User.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .username(dto.getUsername())
                        .email(dto.getEmail())
                        .address(Address.builder()
                                .street(dto.getAddress().getStreet())
                                .suite(dto.getAddress().getSuite())
                                .city(dto.getAddress().getCity())
                                .zipcode(dto.getAddress().getZipcode())
                                .geoLocation(GeoLocation.builder()
                                        .latitude(dto.getAddress().getGeo().getLat())
                                        .longitude(dto.getAddress().getGeo().getLng())
                                        .build()
                                )
                                .build()
                        )
                        .phone(dto.getPhone())
                        .website(dto.getWebsite())
                        .company(Company.builder()
                                .name(dto.getCompany().getName())
                                .catchPhrase(dto.getCompany().getCatchPhrase())
                                .bs(dto.getCompany().getBs())
                                .build()
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }
}
