package com.final_project.staselko.service.Impl;

import com.final_project.staselko.converter.HistoryConverter;
import com.final_project.staselko.dao.HistoryDao;
import com.final_project.staselko.dao.TicketDao;
import com.final_project.staselko.model.dto.HistoryDto;
import com.final_project.staselko.model.entity.History;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.exception.ResourceNotFoundException;
import com.final_project.staselko.model.response.HistoryResponse;
import com.final_project.staselko.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryDao historyDao;
    private final TicketDao ticketDao;
    private final HistoryConverter historyConverter;
    @Transactional
    @Override
    public void addHistory(Ticket ticket, User user, String action, String description) {
        History history = new History();
        history.setTicket(ticket);
        history.setUser(user);
        history.setAction(action);
        history.setDescription(description);
        historyDao.save(history);
    }
    @Transactional
    @Override
    public HistoryResponse getHistoriesByTicket(Long ticketId, int pageNo, int pageSize) {
        Ticket ticket = ticketDao.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketId));
        String condition = " order by date desc";
        List<History> list = historyDao.findAllHistories(pageNo, pageSize, condition, "ticket", ticket);
        long total = historyDao.getTotalHistories("ticket", ticket, "");
        List<HistoryDto> content = list.stream()
                .map(historyConverter::toHistoryDto)
                .collect(Collectors.toList());
        HistoryResponse historyResponse = new HistoryResponse();
        historyResponse.setContent(content);
        historyResponse.setPageNo(pageNo);
        historyResponse.setPageSize(pageSize);
        historyResponse.setTotalElements(total);
        int totalPages = 0;
        if (pageSize != 0) {
            if (total % pageSize == 0) {
                totalPages = (int) (total / pageSize);
            } else {
                totalPages = (int) (total / pageSize + 1);
            }
        }
        historyResponse.setTotalPages(totalPages);

        return historyResponse;
    }
}
