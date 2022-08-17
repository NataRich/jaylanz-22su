package com.jaylanz.service.data.impl;

import com.jaylanz.domain.dto.WhitelistDTO;
import com.jaylanz.domain.dto.repeository.WhitelistRepository;
import com.jaylanz.domain.po.WhitelistPO;
import com.jaylanz.domain.po.mapper.WhitelistMapper;
import com.jaylanz.service.data.WhitelistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableAsync
@Service
public class WhitelistServiceImpl implements WhitelistService {
    private final WhitelistMapper whitelistMapper;
    private final WhitelistRepository whitelistRepository;

    @Autowired
    public WhitelistServiceImpl(WhitelistMapper whitelistMapper, WhitelistRepository whitelistRepository) {
        this.whitelistMapper = whitelistMapper;
        this.whitelistRepository = whitelistRepository;
    }

    @Override
    public boolean exists(String ipAddress) {
        return whitelistRepository.findByIpAddress(ipAddress).isPresent();
    }

    @Async
    @Scheduled(fixedDelay = 86400000 )  // every 24h
    @Override
    public void loadAll() {
        List<WhitelistPO> pos = whitelistMapper.selectList(null);
        for (WhitelistPO po : pos) {
            WhitelistDTO dto = new WhitelistDTO(po.getId(), po.getIpAddress());
            whitelistRepository.save(dto);
        }
    }
}
