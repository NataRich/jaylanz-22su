package com.jaylanz.service.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jaylanz.common.exception.InvalidTokenException;
import com.jaylanz.common.util.JwtUtil;
import com.jaylanz.domain.dto.TokenDTO;
import com.jaylanz.domain.dto.repeository.TokenRepository;
import com.jaylanz.domain.po.TokenPO;
import com.jaylanz.domain.po.mapper.TokenMapper;
import com.jaylanz.service.data.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableAsync
@Service
public class TokenServiceImpl implements TokenService {
    private final JwtUtil jwtUtil;
    private final TokenMapper tokenMapper;
    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(JwtUtil jwtUtil, TokenMapper tokenMapper, TokenRepository tokenRepository) {
        this.jwtUtil = jwtUtil;
        this.tokenMapper = tokenMapper;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public TokenDTO get(Long userId, String ipAddress) {
        List<TokenDTO> tokens = tokenRepository.findAllByUserIdAndIpAddress(userId, ipAddress);
        return tokens.isEmpty() ? fetch(userId, ipAddress) : tokens.get(0);
    }

    @Override
    public boolean isExpired(Long userId, String ipAddress) {
        TokenDTO dto = get(userId, ipAddress);
        if (dto == null)
            return true;
        if (jwtUtil.isExpired(dto.getToken())) {
            delete(dto.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean isExpired(String token) {
        boolean expired = jwtUtil.isExpired(token);
        if (expired) {
            QueryWrapper<TokenPO> wrapper = new QueryWrapper<>();
            wrapper.eq("token", token);
            TokenPO po = tokenMapper.selectOne(wrapper);
            if (po != null)
                delete(po.getId());
            return true;
        } else {
            TokenDTO t = null;
            try {
                t = fetch(jwtUtil.getUserId(token), jwtUtil.getIpAddress(token));
            } catch (InvalidTokenException ignored) { }
            if (t == null)
                return true;
            if (jwtUtil.isExpired(t.getToken())) {
                delete(t.getId());
                return true;
            }
            return false;
        }
    }

    @Override
    public TokenDTO create(Long userId, String username, String ipAddress) {
        String token = jwtUtil.createToken(userId, username, ipAddress);
        if (fetch(userId, ipAddress) != null)
            return update(userId, username, ipAddress);

        TokenPO t = new TokenPO();
        t.setUserId(userId);
        t.setIpAddress(ipAddress);
        t.setToken(token);
        if (tokenMapper.insert(t) > 0) {
            TokenDTO td = new TokenDTO(t.getId(), t.getUserId(), t.getIpAddress(), t.getToken());
            tokenRepository.save(td);
            return td;
        }
        return null;
    }

    @Override
    public TokenDTO update(Long userId, String username, String ipAddress) {
        QueryWrapper<TokenPO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("ip_address", ipAddress);
        TokenPO po = tokenMapper.selectOne(wrapper);
        po.setToken(jwtUtil.createToken(userId, username, ipAddress));
        tokenMapper.updateById(po);
        TokenDTO dto = new TokenDTO(po.getId(), po.getUserId(), po.getIpAddress(), po.getToken());
        tokenRepository.save(dto);
        return dto;
    }

    @Override
    public void delete(Long tokenId) {
        tokenMapper.deleteById(tokenId);
        tokenRepository.deleteById(tokenId);
    }

    @Async
    @Scheduled(fixedDelay = 86400000) // every 24h
    @Override
    public void scheduledClear() {
        List<TokenPO> tokens = tokenMapper.selectList(null);
        for (TokenPO token : tokens)
            isExpired(token.getToken());
    }

    private TokenDTO fetch(Long userId, String ipAddress) {
        QueryWrapper<TokenPO> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId).eq("ip_address", ipAddress);
        TokenPO po = tokenMapper.selectOne(wrapper);
        if (po != null) {
            TokenDTO dto = new TokenDTO(po.getId(), po.getUserId(), po.getIpAddress(), po.getToken());
            tokenRepository.save(dto);
            return dto;
        }
        return null;
    }
}
