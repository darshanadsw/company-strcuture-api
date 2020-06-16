package com.company.structure.companystrcutureapi.service;

import com.company.structure.companystrcutureapi.command.mappings.UserData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserApiServiceTest {

    @Autowired
    private UserApiService userApiService;

    @Test
    void getUserData() {
        UserData data = userApiService.getUserData();
        assertNotNull(data);
        assertEquals(1,data.getData().size());
    }
}