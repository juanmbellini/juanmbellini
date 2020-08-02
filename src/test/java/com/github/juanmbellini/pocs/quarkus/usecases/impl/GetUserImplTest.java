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

package com.github.juanmbellini.pocs.quarkus.usecases.impl;

import com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder.UsersGateway;
import com.github.juanmbellini.pocs.quarkus.models.User;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetUserImplTest {


    @Test
    void whenGetIsCalled_thenReturnListOfUsers() {
        // Given
        final var list = List.<User>of();
        final var gateway = mock(UsersGateway.class);
        when(gateway.getUsers()).thenReturn(list);
        final var useCase = new GetUserImpl(gateway);
        // When
        final var returned = useCase.get();
        // Then
        assertEquals(list, returned, "The returned list is not the expected");
        verify(gateway, only()).getUsers();
    }
}
