package com.example.api.services;

import com.example.api.dto.CreateUserDto;
import com.example.api.dto.EditUserDto;
import com.example.api.dto.RecoveryUserDto;
import com.example.api.entities.User;
import com.example.api.exceptions.AWSException;
import com.example.api.exceptions.AuthenticationFailedException;
import com.example.api.exceptions.ResourceAlreadyExistsException;
import com.example.api.exceptions.ResourceNotFoundException;
import com.example.api.mappers.UserMapper;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.repositories.UserRepository;

import ai.onnxruntime.OrtException;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ImageService imageService;

    public RecoveryUserDto createUser(CreateUserDto createUserDto){

        if(userRepository.existsByName(createUserDto.name())){
            throw new ResourceAlreadyExistsException("Já existe um usuário com este nome");
        }

        if(userRepository.existsByEmail(createUserDto.email())){
            throw new ResourceAlreadyExistsException("Já existe um usuário com este email");
        }

        User user = User.builder()
                .name(createUserDto.name())
                .email(createUserDto.email())
                .password(passwordService.hashPassword(createUserDto.password()))
                .city(createUserDto.city())
                .build();

        User userSaved = userRepository.save(user);

        return userMapper.recoveryUserToDto(userSaved);

    }

    public RecoveryUserDto editUser(Long id, EditUserDto editUserDto){

        User user = userRepository.findById(id)
                                  .orElseThrow(()-> new ResourceNotFoundException("Usuário Não encontrado"));

        if(!editUserDto.name().equalsIgnoreCase(user.getName()) && userRepository.existsByName(editUserDto.name())){
            throw new ResourceAlreadyExistsException("Já existe um usuário com este nome");
        }

        if(!editUserDto.email().equalsIgnoreCase(user.getEmail()) && userRepository.existsByEmail(editUserDto.email())){
            throw new ResourceAlreadyExistsException("Já existe um usuário com este email");
        }

        
        if (!editUserDto.name().equalsIgnoreCase(user.getName())) {
            user.setName(editUserDto.name());
        }
        if (!editUserDto.email().equalsIgnoreCase(user.getEmail())) {
            user.setEmail(editUserDto.email());
        }
        if (!editUserDto.city().equalsIgnoreCase(user.getCity())) {
            user.setCity(editUserDto.city());
        }

        return userMapper.recoveryUserToDto(userRepository.save(user));

    }

    public List<RecoveryUserDto> getUsers(){
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> userMapper.recoveryUserToDto(user)).toList();
    }

    public RecoveryUserDto getUserByName(String username){
        User user = userRepository.findByName(username)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        return userMapper.recoveryUserToDto(user);
    }

    public RecoveryUserDto getUserById(Long id){
        User user = userRepository.findById(id)
                                  .orElseThrow(()-> new ResourceNotFoundException("Usuário não encontrado"));
        return userMapper.recoveryUserToDto(user);
    }

    public RecoveryUserDto authenticate(String name, String plainPassword){
        User user = userRepository.findByName(name)
                    .orElseThrow(() -> new AuthenticationFailedException("Credenciais inválidas"));

        if(!passwordService.verifyPassword(plainPassword, user.getPassword())){
            throw new AuthenticationFailedException("Credenciais inválidas");
        }
        return userMapper.recoveryUserToDto(user);
    }

    public RecoveryUserDto sendProfileImage(MultipartFile file, Long idUsuario) throws OrtException, IOException{

        User user = userRepository.findById(idUsuario)
                                  .orElseThrow(()-> new ResourceNotFoundException("Este usuário não foi encontrado."));

        String imageUrl = null;
        try {
            imageUrl = imageService.uploadProfileImage(file, idUsuario);
        } catch (Exception e) {
            throw new AWSException("Ocorreu um erro na comunicação com o serviço AWS, contate o administrador do sistema.");
        } finally{
            if(!imageUrl.isEmpty()){
                user.setProfileImage(imageUrl);
            }
            user = userRepository.save(user);
        }

        return userMapper.recoveryUserToDto(user);
    }

}
