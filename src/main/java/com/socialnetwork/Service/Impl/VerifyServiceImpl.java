package com.socialnetwork.Service.Impl;

import com.socialnetwork.Entity.VerifyAcc;
import com.socialnetwork.Infrastucture.Dto.ImageDto;
import com.socialnetwork.Infrastucture.Dto.VerifyDto;
import com.socialnetwork.Infrastucture.Mapper.UserMapper;
import com.socialnetwork.Infrastucture.Mapper.VerifyDtoMapper;
import com.socialnetwork.Infrastucture.Mapper.VerifyMapper;
import com.socialnetwork.Repository.VerifyRepository;
import com.socialnetwork.Service.PostService;
import com.socialnetwork.Service.UserService;
import com.socialnetwork.Service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Autowired
    VerifyRepository verifyRepository;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Override
    public VerifyDto createVerify(VerifyDto verifyDto, Long id) {
        VerifyAcc verifyAcc = VerifyMapper.INSTANCE.apply(verifyDto);
        verifyAcc.setUser( UserMapper.INSTANCE.apply( userService.userId(id) ));
        VerifyAcc verifyAccSave = verifyRepository.save(verifyAcc);
        return VerifyDtoMapper.INSTANCE.apply(verifyAcc);
    }

    @Override
    public Optional<VerifyAcc> verifyIdEntity(Long id) {
        return Optional.empty();
    }


    @Override
    public VerifyDto getVerifyById(Long id) {
        VerifyAcc verifyAcc = verifyRepository.findById(id).orElseThrow(()->new RuntimeException("no such"));
        return VerifyDtoMapper.INSTANCE.apply(verifyAcc);
    }

    @Override
    public List<VerifyDto> getAllVerify() {
        List<VerifyAcc> verifyAccs = verifyRepository.findAll();
        return verifyAccs.stream().map( vers -> VerifyDtoMapper.INSTANCE.apply(vers)).collect(Collectors.toList());
    }

    @Override
    public VerifyDto findBynameAccount(String nameAccount) {
        VerifyAcc verifyAcc = verifyRepository.findBynameAccount(nameAccount);
        return VerifyDtoMapper.INSTANCE.apply(verifyAcc);
    }

    @Override
    public VerifyDto addConnectedVerifyAcc(Long verifyAccId, Long connectedVerifyAccId) {
        VerifyAcc verifyAcc = verifyRepository.findById(verifyAccId).orElse(null);
        VerifyAcc connectedVerifyAcc = verifyRepository.findById(connectedVerifyAccId).orElse(null);

        if (verifyAcc != null && connectedVerifyAcc != null) {
            //korisnik A =  daj listu verifikonavih korisnika (getConnectedVerifyAccs()) i dodaj u tu listu  verifyUser(Korisnik B) (connectedVerifyAcc)
            verifyAcc.getConnectedVerifyAccs().add(connectedVerifyAcc);
            VerifyAcc savedVerifyAcc = verifyRepository.save(verifyAcc);
            return VerifyDtoMapper.INSTANCE.apply(savedVerifyAcc);
        }

        return null;
    }

    @Override
    public List<VerifyDto> getConnectedVerifyAccsById(Long verifyAccId) {
        VerifyAcc verifyAcc = verifyRepository.findById(verifyAccId).orElseThrow(() -> new RuntimeException("No such VerifyAcc"));
        //dohvacanje svi friends accounta koji su povezani sa verifyAcc (verifyAcc.getConnectedVerifyAccs)
        List<VerifyAcc> connectedVerifyAccs = verifyAcc.getConnectedVerifyAccs();
        return connectedVerifyAccs.stream()
                .map(vers -> VerifyDtoMapper.INSTANCE.apply(vers))
                .collect(Collectors.toList());
    }

}


