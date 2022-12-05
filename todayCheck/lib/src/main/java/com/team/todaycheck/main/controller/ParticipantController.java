package com.team.todaycheck.main.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.security.auth.login.AccountNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.todaycheck.main.DTO.LoginRequestDTO;
import com.team.todaycheck.main.DTO.LoginResponseDTO;
import com.team.todaycheck.main.DTO.MissionDTO;
import com.team.todaycheck.main.DTO.ParticipantDTO;
import com.team.todaycheck.main.entity.RefreshToken;
import com.team.todaycheck.main.entity.UserEntity;
import com.team.todaycheck.main.repository.UserRepository;
import com.team.todaycheck.main.service.IMissionService;
import com.team.todaycheck.main.service.JwtService;
import com.team.todaycheck.main.service.LoginService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {
	
	private final IMissionService missionService;
	private final JwtService jwtService;
	private final UserRepository userRepos;
	
	@GetMapping(value = "")
	public ResponseEntity<String> list() {
		return ResponseEntity.ok("a");
	}
	
	@PostMapping(value = "/{id}")
    @ApiOperation(value = "�̼� ����", notes = "�־��� �̼� ���̵� ���� �̼ǿ� �����Ѵ�")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "���������� ������"),
    		@ApiResponse(code = 401, message = "���� ����"),
    		@ApiResponse(code = 409, message = "�̹� ������"),
    		@ApiResponse(code = 500, message = "���� ����"),
	})
    public ResponseEntity join(@PathVariable long id, @CookieValue(name = "refreshToken") String cookie) throws Exception {
    	MissionDTO mission = missionService.findById(id);
    	if (mission == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	
    	Optional<RefreshToken> o = jwtService.getRefreshToken(cookie);
    	
    	RefreshToken token = o.orElse(null);
    	if (token == null) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    	UserEntity user = userRepos.findById(token.getKeyEmail());
    	for (ParticipantDTO participant : mission.getParticipants()) {
    		if (participant.getEmail() == user.getId()) {
    			return ResponseEntity.status(HttpStatus.CONFLICT).build();
    		}
    	}
    	
    	ParticipantDTO participant = ParticipantDTO.builder()
    			.id(user.getUserId())
    			.build();
    	mission.getParticipants().add(participant);
    	
    	missionService.save(mission);
    	
        return ResponseEntity.ok().build();
    }
	
	@DeleteMapping(value = "/{id}")
	@ApiOperation(value = "�̼� Ż��", notes = "�־��� �̼� ���̵� ���� �̼ǿ��� Ż���Ѵ�")
    @ApiResponses(value = { 
    		@ApiResponse(code = 200, message = "���������� Ż����"),
    		@ApiResponse(code = 401, message = "���� ����"),
    		@ApiResponse(code = 500, message = "���� ����"),
	})
	public ResponseEntity leave(@PathVariable long id, @CookieValue(name = "refreshToken") String cookie) throws Exception {
    	MissionDTO mission = missionService.findById(id);
    	if (mission == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	
    	Optional<RefreshToken> o = jwtService.getRefreshToken(cookie);
    	
    	RefreshToken token = o.orElse(null);
    	if (token == null) {
    		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    	}
    	
    	UserEntity user = userRepos.findById(token.getKeyEmail());
    	for (ParticipantDTO participant : mission.getParticipants()) {
    		if (participant.getEmail() == user.getId()) {
    			mission.getParticipants().remove(participant);
    			break;
    		}
    	}
    	
    	missionService.save(mission);
    	
        return ResponseEntity.ok().build();
    }
}