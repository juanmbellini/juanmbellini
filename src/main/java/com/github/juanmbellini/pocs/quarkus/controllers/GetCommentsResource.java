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

import com.github.juanmbellini.pocs.quarkus.usecases.GetComments;
import lombok.AllArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Rest endpoint for getting comments from JSON Placeholder.
 */
@Path("/comments")
@AllArgsConstructor
public class GetCommentsResource {

    private final GetComments getComments;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getComments(
            @QueryParam("name") final String name,
            @QueryParam("email") final String email) {
        final var users = getComments.apply(name, email);
        return Response.ok(users).build();
    }
}
