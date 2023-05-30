package com.robotdreams.hotelbooking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.robotdreams.hotelbooking.domain.Room;
import com.robotdreams.hotelbooking.dto.ReservationRequestDto;
import com.robotdreams.hotelbooking.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomService roomService;

    private enum Type {
        a, b, c;
    }

    @Test
    void testProvideWrongDatesReturns404Code() throws Exception {

        final MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(
                "/api/reservations"
        );
        mockMvc.perform(builder).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }

    @Test
    public void createReservationTest() throws Exception
    {
        int roomNumber = 100400500;

        // add room for our tests if not presented
        if(!roomService.findByRoomNumber(roomNumber).isPresent()) {
            Room.Type type = Room.Type.a;
            Room room = Room.builder().roomNumber(100400500).type(type).build();

            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/room/add")
                            .content(asJsonString(room))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }

        Optional <Room> roomContainer = roomService.findByRoomNumber(roomNumber);

        Room room = roomContainer.get();

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");

        sdformat.setLenient(false);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        Date current = sdformat.parse(dtf.format(now));

        System.out.println(sdformat.format(current));

        Calendar c = Calendar.getInstance();
        c.setTime(current);
        c.add(Calendar.DATE, 5);
        String endDate = sdformat.format(c.getTime());
        String startDate = sdformat.format(current);

        ReservationRequestDto requestDto =
                ReservationRequestDto.
                        builder().
                        dob("1985-06-27").
                        email("fake_user@gmail.com").
                        firstName("Fake").
                        lastName("Dude").
                        roomId(room.getId().toString()).
                        fromDate(startDate).
                        toDate(endDate).passport("AX29384739").build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/add/reservation")
                        .content(asJsonString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
