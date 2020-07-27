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

package com.github.juanmbellini.pocs.quarkus.controllers;

import com.github.juanmbellini.pocs.quarkus.usecases.GetUsers;
import lombok.AllArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Rest endpoint for getting users from JSON Placeholder.
 */
@Path("/users")
@AllArgsConstructor
public class GetUsersResource {

    private final GetUsers getUsers;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        final var users = getUsers.get();
        return Response.ok(users).build();
    }
}
