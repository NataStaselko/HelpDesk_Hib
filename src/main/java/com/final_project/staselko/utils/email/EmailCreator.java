package com.final_project.staselko.utils.email;

import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.Role;
import com.final_project.staselko.model.enums.State;
import com.final_project.staselko.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EmailCreator {

    private final UserService userService;

    @Value("${href_by_ticketID}")
    private String href_by_ticketID;

    public Optional<EmailTemplate> getEmailTemplate(User user, Ticket ticket) {
        return Optional.ofNullable(emailMap(user, ticket).get(ticket.getState()));
    }


    private EnumMap<State, EmailTemplate> emailMap(User user, Ticket ticket) {
        String href = href_by_ticketID + ticket.getId() + "/history";
        EnumMap<State, EmailTemplate> emailMap = new EnumMap<>(State.class);

        if (user.getId().equals(ticket.getOwner().getId())) {

            emailMap.put(State.NEW, new EmailTemplate("New ticket for approval",
                    "Dear Managers ",
                    "New ticket ", href,
                    " is waiting for your approval.", getArrayByEmail(Role.MANAGER, ticket)));

            if (ticket.getEngineer() != null) {
                emailMap.put(State.DONE, new EmailTemplate("Feedback was provided",
                        "Dear " + ticket.getEngineer().getFirst_name() + " " + ticket.getEngineer().getLast_name(),
                        "The feedback was provided on the ticket ", href, ".",
                        new String[]{ticket.getEngineer().getEmail()}));
            }
        }

        if (ticket.getManager() != null && user.getId().equals(ticket.getManager().getId())) {

            emailMap.put(State.APPROVED, new EmailTemplate("Ticket was approved",
                    "Dear Users,",
                    "Ticket ", href,
                    " was approved by the Manager.",
                    getArrayByEmail(Role.ENGINEER, ticket)));

            emailMap.put(State.DECLINED, new EmailTemplate("Ticket was declined",
                    "Dear " + ticket.getOwner().getFirst_name() + " " + ticket.getOwner().getLast_name(),
                    "Ticket ", href,
                    " was declined by the Manager.",
                    new String[]{ticket.getOwner().getEmail()}));

            emailMap.put(State.CANCELED, new EmailTemplate("Ticket was cancelled",
                    "Dear " + ticket.getOwner().getFirst_name() + " " + ticket.getOwner().getLast_name(),
                    "Ticket ", href,
                    " was cancelled by the Manager.",
                    new String[]{ticket.getOwner().getEmail()}));
        }

        if (ticket.getEngineer() != null && user.getId().equals(ticket.getEngineer().getId())) {

            emailMap.put(State.CANCELED, new EmailTemplate("Ticket was cancelled",
                    "Dear Users,",
                    "Ticket ", href,
                    " was cancelled by the Engineer.",
                    new String[]{ticket.getOwner().getEmail(), ticket.getManager().getEmail()}));

            emailMap.put(State.DONE, new EmailTemplate("Ticket was done",
                    "Dear " + ticket.getOwner().getFirst_name() + " " + ticket.getOwner().getLast_name(),
                    "Ticket ", href,
                    " was done by the Engineer. Please provide your feedback by clicking on the ticket ID.",
                    new String[]{ticket.getOwner().getEmail()}));
        }
        return emailMap;
    }

    private String[] getArrayByEmail(Role role, Ticket ticket) {
        List<String> list = new ArrayList<>();
        if (role == Role.MANAGER) {
            list = userService.getAllUsersByRole(role)
                    .stream().filter(user -> !user.getId().equals(ticket.getOwner().getId()))
                    .map(User::getEmail).collect(Collectors.toList());
        }
        if (role == Role.ENGINEER) {
            list = userService.getAllUsersByRole(role)
                    .stream()
                    .map(User::getEmail)
                    .collect(Collectors.toList());
            list.add(ticket.getOwner().getEmail());
        }
        String[] array = new String[list.size()];
        list.toArray(array);
        return array;
    }
}