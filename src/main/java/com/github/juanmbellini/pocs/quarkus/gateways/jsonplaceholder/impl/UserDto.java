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

import com.github.juanmbellini.pocs.quarkus.models.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
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
                .address(Optional.ofNullable(address).map(AddressDto::toAddress).orElse(null))
                .phone(phone)
                .website(website)
                .company(Optional.ofNullable(company).map(CompanyDto::toCompany).orElse(null))
                .build();
    }
}
