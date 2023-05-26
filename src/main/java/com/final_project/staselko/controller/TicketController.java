package com.final_project.staselko.controller;
import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.enums.Action;
import com.final_project.staselko.model.response.TicketResponse;
import com.final_project.staselko.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @Value("${pagination.page_size}")
    private String page_size;

    @PostMapping
    public ResponseEntity<Long> createTicket(@RequestBody @Valid TicketDto ticketDto){
         Ticket ticket = ticketService.createTicket(ticketDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticket.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable(value = "id") Long id){
        TicketDto ticketDto = ticketService.getTicketDtoById(id);
        return ResponseEntity.ok(ticketDto);
    }

    @GetMapping
    public ResponseEntity<TicketResponse> getAllTickets(@RequestParam(value = "flag", required = false) String flag,
                                                        @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                        @RequestParam(value = "sortBy", required = false) String sortBy,
                                                        @RequestParam(value = "orderBy", defaultValue = "asc", required = false) String orderBy,
                                                        @RequestParam(value = "filter_id", required = false) Long filter_id,
                                                        @RequestParam(value = "filter_name", required = false) String filter_name,
                                                        @RequestParam(value = "filter_date", required = false) String filter_date,
                                                        @RequestParam(value = "filter_urgency", required = false) String filter_urgency,
                                                        @RequestParam(value = "filter_state", required = false) String filter_state) {
        TicketResponse ticketResponse = ticketService.getTicketList(pageNo, Integer.parseInt(page_size),
                sortBy, orderBy, filter_id, filter_name, filter_date, filter_urgency, filter_state, flag);
        return ResponseEntity.ok(ticketResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> changeStatusByTicket(@PathVariable(value = "id") Long ticketId,
                                                     @RequestParam(value = "action", required = false) String action,
                                                     @RequestBody(required = false) @Valid TicketDto ticketDto){
        ticketService.updateOrChangeAction(ticketId, Action.getAction(action), ticketDto);
        return ResponseEntity.ok().build();
    }





















}
