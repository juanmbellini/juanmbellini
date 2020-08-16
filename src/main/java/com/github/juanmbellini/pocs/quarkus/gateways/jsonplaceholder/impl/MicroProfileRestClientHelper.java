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
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;
import javax.ws.rs.WebApplicationException;

@UtilityClass
class MicroProfileRestClientHelper {

    /* package */ <T> T wrapForGatewayException(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (final WebApplicationException e) {
            throw new GatewayException(e);
        } catch (final Exception e) {
            throw new GatewayException("Unexpected exception", e);
        }
    }
}
