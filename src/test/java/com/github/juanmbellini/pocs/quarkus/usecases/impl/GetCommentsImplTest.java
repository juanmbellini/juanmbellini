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

import com.github.juanmbellini.pocs.quarkus.gateways.jsonplaceholder.CommentsGateway;
import com.github.juanmbellini.pocs.quarkus.models.Comment;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetCommentsImplTest {

    @Test
    void whenNameIsNull_gatewayIsCalled() {
        // Given
        final var email = UUID.randomUUID().toString();
        final var list = List.<Comment>of();
        final var gateway = mock(CommentsGateway.class);
        when(gateway.getComments(null, email)).thenReturn(list);
        final var useCase = new GetCommentsImpl(gateway);
        // When
        final var returned = useCase.apply(null, email);
        // Then
        assertEquals(list, returned, "The returned list is not the expected");
        verify(gateway, only()).getComments(isNull(), eq(email));
    }

    @Test
    void whenEmailIsNull_gatewayIsCalled() {
        // Given
        final var name = UUID.randomUUID().toString();
        final var list = List.<Comment>of();
        final var gateway = mock(CommentsGateway.class);
        when(gateway.getComments(name, null)).thenReturn(list);
        final var useCase = new GetCommentsImpl(gateway);
        // When
        final var returned = useCase.apply(name, null);
        // Then
        assertEquals(list, returned, "The returned list is not the expected");
        verify(gateway, only()).getComments(eq(name), isNull());
    }

    @Test
    void whenBothArgsAreNull_gatewayIsCalled() {
        // Given
        final var list = List.<Comment>of();
        final var gateway = mock(CommentsGateway.class);
        when(gateway.getComments(null, null)).thenReturn(list);
        final var useCase = new GetCommentsImpl(gateway);
        // When
        final var returned = useCase.apply(null, null);
        // Then
        assertEquals(list, returned, "The returned list is not the expected");
        verify(gateway, only()).getComments(isNull(), isNull());
    }

    @Test
    void whenNoArgIsNull_gatewayIsCalled() {
        // Given
        final var name = UUID.randomUUID().toString();
        final var email = UUID.randomUUID().toString();
        final var list = List.<Comment>of();
        final var gateway = mock(CommentsGateway.class);
        when(gateway.getComments(name, email)).thenReturn(list);
        final var useCase = new GetCommentsImpl(gateway);
        // When
        final var returned = useCase.apply(name, email);
        // Then
        assertEquals(list, returned, "The returned list is not the expected");
        verify(gateway, only()).getComments(eq(name), eq(email));
    }
}
