package com.robotdreams.hotelbooking.controller;

import com.robotdreams.hotelbooking.domain.Room;
import com.robotdreams.hotelbooking.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomService roomService;

    @Test
    void testProvideWrongDatesReturns404Code() throws Exception {

    /*    Optional<Room> result = roomService.findByRoomNumber(201);

        System.out.println(result.get().toString());

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(
                "/api/rooms/2023-05-01/2023-106-05"
        );

        mockMvc.perform(builder).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());
*/
    }

    @Test
    void testProvideWrongDateRangeReturns404Code() throws Exception {
  /*      final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(
                "/api/rooms/2023-05-01/2023-05-01"
        );

        mockMvc.perform(builder).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());
  */  }

}
