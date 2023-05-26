package com.final_project.staselko.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.final_project.staselko.model.dto.CommentDto;
import com.final_project.staselko.model.dto.FeedbackDto;
import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.entity.Attachment;
import com.final_project.staselko.model.entity.Category;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.enums.State;
import com.final_project.staselko.model.enums.Urgency;
import com.final_project.staselko.security.JwtRequest;
import com.final_project.staselko.service.*;
import com.jayway.jsonpath.JsonPath;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.test.context.support.WithMockUser;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private HistoryService historyService;

    @Test
    void authentication() throws Exception {
        JwtRequest request = new JwtRequest("m1@yopmail.com", "Mm123*");
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(request.getUsername()));
    }

    @Test
    @WithMockUser(username = "m1@yopmail.com")
    void getCategories() throws Exception {
        List<Category> categories = categoriesService.getAllCategories();
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(categories.size())))
                .andExpect(jsonPath("$[0].name", Matchers.is(categories.get(0).getName())));
    }

    @Test
    @WithMockUser(username = "m1@yopmail.com")
    void getUrgencies() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(Urgency.CRITICAL.name());
        list.add(Urgency.HIGH.name());
        list.add(Urgency.AVERAGE.name());
        list.add(Urgency.LOW.name());

        MvcResult result = mockMvc.perform(get("/urgencies"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<String> urgencies = JsonPath.read(json, "$[*]");
        Assertions.assertTrue(list.containsAll(urgencies));
        Assertions.assertEquals(list.size(), urgencies.size());
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void createTicket() throws Exception {
        List<Category> categories = categoriesService.getAllCategories();
        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("someName");
        ticketDto.setState("DRAFT");
        ticketDto.setDescription("someDescription");
        ticketDto.setUrgency("CRITICAL");
        ticketDto.setDesired_date("");
        ticketDto.setCategory(categories.get(0));

        MvcResult result = mockMvc.perform(post("/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ticketDto)))
                .andExpect(status().isCreated())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        Integer i = JsonPath.read(json, "$");
        Ticket ticket = ticketService.getTicketById(Long.valueOf(i));
        Assertions.assertEquals(ticket.getName(),ticketDto.getName());
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void getTicketById() throws Exception {
        Long ticketId = createNewTicket();
        mockMvc.perform(get("/tickets/{id}",ticketId))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(ticketId))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.description").value("someDescription"))
                .andExpect(jsonPath("$.state").value(State.DRAFT.name()))
                .andExpect(jsonPath("$.urgency").value(Urgency.CRITICAL.name()));
    }
    @Test
    @WithUserDetails("m1@yopmail.com")
    void getTickets() throws Exception {
        mockMvc.perform(get("/tickets").param("pageNo", "100"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content").isEmpty());
    }
    @Test
    @WithUserDetails("m1@yopmail.com")
    void getTicketsSort() throws Exception {
        List<Category> categories = categoriesService.getAllCategories();
        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("a_name");
        ticketDto.setState("DRAFT");
        ticketDto.setDescription("someDescription");
        ticketDto.setUrgency("CRITICAL");
        ticketDto.setDesired_date("");
        ticketDto.setCategory(categories.get(0));
        Long ticketId = ticketService.createTicket(ticketDto).getId();
        mockMvc.perform(get("/tickets").param("sortBy", "name"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[0].id").value(ticketId));

    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void getTicketsFilter() throws Exception {
        mockMvc.perform(get("/tickets").param("filter_state", "d"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty());
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void changeStatusByTicket() throws Exception {
        Long ticketId = createNewTicket();
        mockMvc.perform(put("/tickets/{id}",ticketId).param("action", "Submit"))
                .andExpect(status().isOk());

        Ticket ticket = ticketService.getTicketById(ticketId);
        Assertions.assertEquals(ticket.getState().name(),"NEW");
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void updateTicket() throws Exception {
        Long ticketId = createNewTicket();
        List<Category> categories = categoriesService.getAllCategories();
        TicketDto ticketUpdate = new TicketDto();
        ticketUpdate.setName("name_new");
        ticketUpdate.setState("DRAFT");
        ticketUpdate.setDescription("someDescription");
        ticketUpdate.setUrgency("CRITICAL");
        ticketUpdate.setDesired_date("");
        ticketUpdate.setCategory(categories.get(0));
        mockMvc.perform(put("/tickets/{id}",ticketId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ticketUpdate)))
                .andExpect(status().isOk());

        Ticket ticket = ticketService.getTicketById(ticketId);
        Assertions.assertEquals(ticket.getName(),"name_new");
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void createComment() throws Exception {
        Long ticketId = createNewTicket();
        CommentDto commentDto = new CommentDto();
        commentDto.setText("someText");
        mockMvc.perform(post("/comments").param("ticketId", String.valueOf(ticketId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(commentDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void getComments() throws Exception {
        Long ticketId = createNewTicket();
        CommentDto commentDto = new CommentDto();
        commentDto.setText("someText");
        commentService.saveComment(commentDto, ticketId);
        mockMvc.perform(get("/comments").param("ticketId", String.valueOf(ticketId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isNotEmpty())
                .andExpect(jsonPath("$.content[0].text").value(commentDto.getText()));
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void updateComment() throws Exception {
        Long ticketId = createNewTicket();
        CommentDto commentDto = new CommentDto();
        commentDto.setText("someText");
        commentService.saveComment(commentDto, ticketId);
        List<CommentDto> list = commentService.getComment(ticketId, 0, 1).getContent();
        CommentDto updateComment = new CommentDto();
        updateComment.setText("text");

        mockMvc.perform(put("/comments/{id}", String.valueOf(list.get(0).getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateComment)))
                .andExpect(status().isOk());
        List<CommentDto> listUpdate = commentService.getComment(ticketId, 0, 1).getContent();
        Assertions.assertEquals(listUpdate.get(0).getText(), updateComment.getText());
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void deleteComment() throws Exception {
        Long ticketId = createNewTicket();
        CommentDto commentDto = new CommentDto();
        commentDto.setText("someText");
        commentService.saveComment(commentDto, ticketId);
        List<CommentDto> list = commentService.getComment(ticketId, 0, 1).getContent();

        mockMvc.perform(delete("/comments/{id}", String.valueOf(list.get(0).getId())))
                .andExpect(status().isOk());
        List<CommentDto> list1 = commentService.getComment(ticketId, 0, 1).getContent();
        Assertions.assertTrue(list1.isEmpty());
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void createFeedback() throws Exception {
        Long ticketId = createNewTicket();
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setText("someText");
        feedbackDto.setRate(4);
        mockMvc.perform(post("/feedbacks").param("ticketId", String.valueOf(ticketId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(feedbackDto)))
                .andExpect(status().isCreated());
        FeedbackDto feedback = feedbackService.getFeedbackByTicket(ticketId).get(0);
        Assertions.assertEquals(feedbackDto.getRate(), feedback.getRate());
        Assertions.assertEquals(feedbackDto.getText(), feedback.getText());
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void getFeedback() throws Exception {
        Long ticketId = createNewTicket();
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setText("text");
        feedbackDto.setRate(5);
        feedbackService.saveFeedback(feedbackDto, ticketId);
        MvcResult result = mockMvc.perform(get("/feedbacks").param("ticketId", String.valueOf(ticketId)))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        String text = JsonPath.read(json, "$[0].text");
        Integer rate = JsonPath.read(json, "$[0].rate");

        Assertions.assertEquals(feedbackDto.getRate(), rate);
        Assertions.assertEquals(feedbackDto.getText(), text);
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void saveFile() throws Exception {
        Long ticketId = createNewTicket();
        MockMultipartFile file =
                new MockMultipartFile(
                        "files",
                        "test.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/files?ticketId=" + ticketId )
                                .file(file))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void getFileById() throws Exception {
        Long ticketId = createNewTicket();
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
        MultipartFile[] files = {file};
        attachmentService.saveAllAttachment(files, ticketId);
        mockMvc.perform(get("/files").param("ticketId", String.valueOf(ticketId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("test.pdf"));
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void deleteFile() throws Exception {
        Long ticketId = createNewTicket();
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));
        MultipartFile[] files = {file};
        attachmentService.saveAllAttachment(files, ticketId);
        List<Attachment> list = attachmentService.getAllAttachment(ticketId);
        Long attachmentId = list.get(0).getId();

        mockMvc.perform(delete("/files/{id}", String.valueOf(attachmentId)))
                .andExpect(status().isOk());
        List<Attachment> list2 = attachmentService.getAllAttachment(ticketId);
        Assertions.assertTrue(list2.isEmpty());
    }

    @Test
    @WithUserDetails("m1@yopmail.com")
    void getHistory() throws Exception {
        Long ticketId = createNewTicket();
        mockMvc.perform(get("/histories").param("ticketId", String.valueOf(ticketId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].action").value("Ticket is edited"));

    }

    private Long createNewTicket(){
       List<Category> categories = categoriesService.getAllCategories();
       TicketDto ticketDto = new TicketDto();
       ticketDto.setName("name");
       ticketDto.setState("DRAFT");
       ticketDto.setDescription("someDescription");
       ticketDto.setUrgency("CRITICAL");
       ticketDto.setDesired_date("");
       ticketDto.setCategory(categories.get(0));
       return ticketService.createTicket(ticketDto).getId();
   }
























}
