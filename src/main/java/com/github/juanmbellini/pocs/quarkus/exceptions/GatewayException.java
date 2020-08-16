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

package com.github.juanmbellini.pocs.quarkus.exceptions;

import lombok.Getter;
import lombok.NonNull;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class GatewayException extends RuntimeException {

    @Getter
    private final Response response;

    public GatewayException() {
        super();
        this.response = null;
    }

    public GatewayException(@NonNull final Response response) {
        super();
        this.response = response;
    }

    public GatewayException(final WebApplicationException webApplicationException) {
        super(webApplicationException);
        this.response = webApplicationException.getResponse();
    }

    public GatewayException(final Throwable cause) {
        super(cause);
        this.response = null;
    }

    public GatewayException(@NonNull final Response response, final Throwable cause) {
        super(cause);
        this.response = response;
    }


    public GatewayException(@NonNull final Response response, final String message) {
        super(message);
        this.response = response;
    }

    public GatewayException(final String message, final WebApplicationException webApplicationException) {
        super(message, webApplicationException);
        this.response = webApplicationException.getResponse();
    }

    public GatewayException(final String message, final Throwable cause) {
        super(message, cause);
        this.response = null;
    }

    public GatewayException(@NonNull final Response response, final String message, final Throwable cause) {
        super(message, cause);
        this.response = response;
    }
}
