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

import com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder.UsersGateway;
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
    private final UsersRestClient.GetUsers getUsersRestClient;


    @Override
    public List<User> getUsers() {
        return getUsersRestClient.perform()
                .stream()
                .map(UsersRestClient.UserDto::toUser)
                .collect(Collectors.toList());
    }
}
