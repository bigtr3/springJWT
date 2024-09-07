package com.example.springJWT.service;


import com.example.springJWT.dto.JoinDTO;
import com.example.springJWT.entity.UserEntity;
import com.example.springJWT.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder){

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    // 원래는 return 값이 boolean
    public void joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        Boolean isExist = userRepository.existsByUsername(username);

        if (isExist){
            // 사용자가 이미 등록되어 있으면 실행 안함
            return;
        }

        UserEntity data = new UserEntity();

        data.setUsername(username);
        // 암호화하여 password를 넣음
        data.setPassword(bCryptPasswordEncoder.encode(password));
        // 강제로 admin 권한을 줌
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);

    }
}
