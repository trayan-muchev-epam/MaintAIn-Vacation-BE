package com.maintain.vacation.service;

import com.maintain.vacation.dto.VacationRequest;
import com.maintain.vacation.dto.VacationResponse;
import com.maintain.vacation.model.User;
import com.maintain.vacation.model.Vacation;
import com.maintain.vacation.model.VacationStatus;
import com.maintain.vacation.repository.UserRepository;
import com.maintain.vacation.repository.VacationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationService {

    private final VacationRepository vacationRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public VacationService(VacationRepository vacationRepository, UserRepository userRepository, UserService userService) {
        this.vacationRepository = vacationRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public List<VacationResponse> getAllVacations(String username) {
        User user = userService.findByUsername(username);
        return vacationRepository.findByUser(user).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public VacationResponse createVacation(String username, VacationRequest request) {
        User user = userService.findByUsername(username);

        LocalDate startDate = LocalDate.parse(request.getStartDate());
        LocalDate endDate = LocalDate.parse(request.getEndDate());

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be before or equal to endDate");
        }

        int days = (int) (ChronoUnit.DAYS.between(startDate, endDate) + 1);

        if (days > user.getRemainingVacationDays()) {
            throw new IllegalArgumentException("Not enough remaining vacation days. Available: "
                    + user.getRemainingVacationDays() + ", Requested: " + days);
        }

        Vacation vacation = new Vacation();
        vacation.setStartDate(startDate);
        vacation.setEndDate(endDate);
        vacation.setDays(days);
        vacation.setStatus(VacationStatus.PENDING);
        vacation.setUser(user);

        user.setRemainingVacationDays(user.getRemainingVacationDays() - days);
        userRepository.save(user);

        Vacation saved = vacationRepository.save(vacation);
        return toResponse(saved);
    }

    @Transactional
    public void deleteVacation(String username, String vacationId) {
        User user = userService.findByUsername(username);
        Vacation vacation = vacationRepository.findById(vacationId)
                .orElseThrow(() -> new RuntimeException("Vacation not found"));

        if (!vacation.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Vacation not found");
        }

        // Restore vacation days when deleting
        user.setRemainingVacationDays(user.getRemainingVacationDays() + vacation.getDays());
        userRepository.save(user);

        vacationRepository.delete(vacation);
    }

    private VacationResponse toResponse(Vacation vacation) {
        return new VacationResponse(
                vacation.getId(),
                vacation.getStartDate().toString(),
                vacation.getEndDate().toString(),
                vacation.getDays(),
                vacation.getStatus().name()
        );
    }
}
