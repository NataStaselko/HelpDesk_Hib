package com.final_project.staselko.service.Impl;

import com.final_project.staselko.converter.TicketConverter;
import com.final_project.staselko.converter.UserConverter;
import com.final_project.staselko.dao.TicketDao;
import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.Action;
import com.final_project.staselko.model.enums.State;
import com.final_project.staselko.model.exception.ResourceNotFoundException;
import com.final_project.staselko.model.response.TicketResponse;
import com.final_project.staselko.service.HistoryService;
import com.final_project.staselko.service.TicketService;
import com.final_project.staselko.service.mail.MailService;
import com.final_project.staselko.utils.ActionsList;
import com.final_project.staselko.utils.DescriptionHistoryCreator;
import com.final_project.staselko.utils.StateByTicket;
import com.final_project.staselko.utils.UserProvider;
import com.final_project.staselko.utils.filter.StateFilter;
import com.final_project.staselko.utils.filter.UrgencyFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketDao ticketDao;
    private final TicketConverter ticketConverter;
    private final UserProvider userProvider;
    private final MailService mailService;
    private final UserConverter userConverter;
    private final HistoryService historyService;
    private final DescriptionHistoryCreator descriptionCreator;
    private final StateByTicket stateByTicket;
    private final ActionsList actionsList;
    private final UrgencyFilter urgencyFilter;
    private final StateFilter stateFilter;

    @Transactional
    @Override
    public Ticket createTicket(TicketDto ticketDto) {
        User user = getCurrentUser();
        ticketDto.setOwner(userConverter.toUserDto(user));
        Long id = ticketDao.save(ticketConverter.toTicket(ticketDto));
        Ticket ticket = getTicketById(id);
        ticket.setOwner(user);
        mailService.sendMailToUser(user, ticket);
        getHistory(ticket, null, null);
        return ticket;
    }
    @Transactional
    @Override
    public Ticket getTicketById(Long ticketId) {
        return ticketDao.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketId));
    }
    @Transactional
    @Override
    public TicketResponse getTicketList(int pageNo, int pageSize, String sortBy, String orderBy,
                                        Long filter_id, String filter_name, String filter_date,
                                        String filter_urgency, String filter_state, String flag) {
        User user = getCurrentUser();

        List<Ticket> list = new ArrayList<>();

        long total = 0;

        String conditionByTotal = "";

        String filter = getFilterParam(filter_id, filter_name, filter_date,
                                       filter_urgency, filter_state);

        String condition = getCondition(filter, sortBy, orderBy,
                                        filter_id, filter_name,
                                        filter_date, filter_urgency,
                                        filter_state);
        if (filter != null){
            conditionByTotal = condition;
        }
        if (user.isEmployee()) {
            list = ticketDao.getTickets(pageNo, pageSize, condition,"owner", user);
            total = ticketDao.getTotalTickets("owner", user, conditionByTotal);
        }
        if (user.isManager()) {
            if (flag == null) {
                list = ticketDao.getTickets(pageNo, pageSize, condition,
                        "owner", user, "manager", user, "state", State.NEW);
                total = ticketDao.getTotalTickets("owner", user,
                        "manager", user, "state", State.NEW, conditionByTotal);
            }else {
                list = ticketDao.getTickets(pageNo, pageSize, condition,
                        "owner", user, "manager", user);
                total = ticketDao.getTotalTickets("owner", user,
                        "manager", user, conditionByTotal);
            }
        }
        if (user.isEngineer()) {
            if (flag == null) {
                list = ticketDao.getTickets(pageNo, pageSize, condition,
                        "engineer", user, "state", State.APPROVED);
                total = ticketDao.getTotalTickets("engineer", user,
                        "state", State.APPROVED, conditionByTotal);
            }else {
                list = ticketDao.getTickets(pageNo, pageSize, condition, "engineer", user);
                total = ticketDao.getTotalTickets("engineer", user, conditionByTotal);
            }
        }
        return createResponse(list, user, pageNo, pageSize, total);
    }


    @Transactional
    @Override
    public TicketDto getTicketDtoById(Long ticketDtoId) {
        return ticketDao.findById(ticketDtoId).map(ticketConverter::toTicketDto)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketDtoId));
    }

    @Transactional
    @Override
    public void updateOrChangeAction(Long ticketId, Action action, TicketDto ticketDto) {
        if (action != null) {
            changeStatus(ticketId, action);
        }
        if (ticketDto != null) {
            updateTicket(ticketId, ticketDto);
        }
    }


    private void changeStatus(Long ticketId, Action action) {
        Ticket ticket = getTicketById(ticketId);
        State oldState = ticket.getState();
        User user = getCurrentUser();
        State newState = stateByTicket.getNewState(ticket, action);
        ticket.setState(newState);
        if (user.isManager() && !(Objects.equals(ticket.getOwner().getId(), user.getId()))) {
            ticket.setManager(user);
        }
        if (user.isEngineer()) {
            ticket.setEngineer(user);
        }
        ticketDao.updateTicket(ticket);
        mailService.sendMailToUser(user, ticket);
        getHistory(ticket, oldState, newState);
    }


    private void updateTicket(Long ticketId, TicketDto ticketDto) {
        Ticket ticket = ticketDao.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketId));
        State oldState = ticket.getState();
        ticketDto.setOwner(userConverter.toUserDto(getCurrentUser()));
        Ticket ticketUpdate = ticketConverter.toTicket(ticketDto);
        ticket.setCategory(ticketUpdate.getCategory());
        ticket.setName(ticketUpdate.getName());
        ticket.setDescription(ticketUpdate.getDescription());
        ticket.setUrgency(ticketUpdate.getUrgency());
        ticket.setDesired(ticketUpdate.getDesired());
        ticket.setState(ticketUpdate.getState());
        ticketDao.updateTicket(ticket);
        mailService.sendMailToUser(ticket.getOwner(), ticket);
        if (oldState != ticketUpdate.getState()) {
            getHistory(ticket, null, null);
        }
    }


    private TicketResponse createResponse(List<Ticket> list, User user, int pageNo, int pageSize, long total) {
        List<TicketDto> content = list.stream()
                .map(ticketConverter::toTicketDto)
                .peek(ticketDto -> ticketDto.setActions(actionsList.getActions(user, ticketDto)))
                .collect(Collectors.toList());

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setContent(content);
        ticketResponse.setPageNo(pageNo);
        ticketResponse.setPageSize(pageSize);
        ticketResponse.setTotalElements(total);
        int totalPages = 0;
        if (pageSize != 0) {
            if (total % pageSize == 0) {
                totalPages = (int) (total / pageSize);
            } else {
                totalPages = (int) (total / pageSize + 1);
            }
        }
        ticketResponse.setTotalPages(totalPages);
        return ticketResponse;
    }

    private User getCurrentUser() {
        return userProvider.getCurrentUser();
    }

    private String getFilterParam(Long filter_id, String filter_name, String filter_date,
                                  String filter_urgency, String filter_state){
        if(filter_id != null){
            return "id";
        }
        if(filter_name != null){
            return "name";
        }
        if(filter_date != null){
            return "desired";
        }
        if(filter_urgency != null){
            return "urgency";
        }
        if(filter_state != null){
            return "state";
        }
        return null;
    }

    private String getCondition(String filter, String sortBy, String orderBy,
                                Long filter_id, String filter_name, String filter_date,
                                String filter_urgency, String filter_state){
        if (filter == null){
            if(sortBy == null){
                return  " order by urgency asc, desired desc";
            }else {
                if (orderBy.equals("desc")) {
                    return " order by " + sortBy + " desc";
                } else {
                    return " order by " + sortBy + " asc";
                }
            }
        }else if (filter.equals("id")){
            return " and id >= " + filter_id;
        }else if (filter.equals("name")){
            return " and name like " + "'" + filter_name + "%'";
        } else if (filter.equals("desired")) {
            return " and desired like " + "'" + filter_date + "%'";
        } else if (filter.equals("urgency")) {
            if (urgencyFilter.getUrgency(filter_urgency) != null) {
                return " and urgency = " + urgencyFilter.getUrgency(filter_urgency).ordinal();
            }else {
                return "";
            }
        } else if (filter.equals("state")) {
            if (stateFilter.getState(filter_state) != null) {
                return " and state = " + stateFilter.getState(filter_state).ordinal();
            }else {
                return "";
            }
        }else {
            return "";
        }
    }
    private void getHistory(Ticket ticket, State oldState, State newState) {
        if (ticket.getState() == State.NEW) {
            historyService.addHistory(ticket, ticket.getOwner(),
                    descriptionCreator.actionCreate(),
                    descriptionCreator.descriptionCreate());
        } else if (ticket.getState() == State.DRAFT) {
            historyService.addHistory(ticket, ticket.getOwner(),
                    descriptionCreator.actionEdit(),
                    descriptionCreator.descriptionEdit());
        } else {
            historyService.addHistory(ticket, getCurrentUser(),
                    descriptionCreator.actionChangeState(),
                    descriptionCreator.descriptionChangeState(oldState.name(), newState.name()));
        }
    }
}
