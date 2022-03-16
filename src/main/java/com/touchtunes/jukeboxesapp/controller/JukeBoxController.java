package com.touchtunes.jukeboxesapp.controller;

import com.touchtunes.jukeboxesapp.exception.BadRequestException;
import com.touchtunes.jukeboxesapp.exception.ResourceNotFoundException;
import com.touchtunes.jukeboxesapp.model.Juke;
import com.touchtunes.jukeboxesapp.model.Setting;
import com.touchtunes.jukeboxesapp.service.JukeService;
import com.touchtunes.jukeboxesapp.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JukeBoxController {

    @Autowired
    private JukeService jukeService;

    @Autowired
    private SettingService settingService;

    @GetMapping("/jukeboxes")
    Page<Juke> getJukeBoxes(@RequestParam String settingId,
                                  @RequestParam(required = false) String model,
                                  @RequestParam(required = false) Integer offset,
                                  @RequestParam(required = false) Integer limit) {

        if (settingId == null || settingId.isEmpty()) {
            throw new BadRequestException("settingId parameter must be required!");
        }

        Setting setting = settingService.findSettingBySettingId(settingId);

        if (setting == null) {
            throw new ResourceNotFoundException("setting not found:"+ settingId);
        }

        if (setting.getRequires() == null || setting.getRequires().length == 0) {
            throw new ResourceNotFoundException("Components must be required for setting:"+ settingId);
        }

        List<String> requiredComponents = Arrays.asList(setting.getRequires());
        List<Juke> jukes = jukeService.getJukes();

        if (CollectionUtils.isEmpty(jukes)) {
            throw new ResourceNotFoundException("Jukes data not found");
        }

        if (model != null) {
            List<Juke> filterJukesByModel = jukes.stream()
                    .filter(juke -> juke.getModel().equals(model) &&
                            Arrays.stream(juke.getComponents()).anyMatch(
                                    component -> requiredComponents.contains(component.getName())
                            ))
                    .collect(Collectors.toList());
            return getJukeByPage(offset, limit, filterJukesByModel);
        }

        List<Juke> filterJukes = jukes.stream()
                .filter(juke -> Arrays.stream(juke.getComponents()).anyMatch(
                                component -> requiredComponents.contains(component.getName())
                        ))
                .collect(Collectors.toList());
        return getJukeByPage(offset, limit, filterJukes);
    }

    private Page<Juke> getJukeByPage(Integer offset, Integer limit, List<Juke> jukesList) {
        offset = offset != null ? offset : 0;
        limit = limit != null ? limit : jukesList.size() == 0 ? 1 : jukesList.size();
        Pageable pageable = PageRequest.of(offset, limit);
        int start = (int) pageable.getOffset();
        int end = (int) ((start + pageable.getPageSize()) > jukesList.size() ? jukesList.size()
                : (start + pageable.getPageSize()));
        Page<Juke> page = new PageImpl<Juke>(
                jukesList.subList(start, end), pageable, jukesList.size());
        return page;
    }

}
