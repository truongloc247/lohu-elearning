package com.tanloc.lohu.lohuelearninguserapp.service;

import com.tanloc.lohu.lohuelearninguserapp.dto.LearningFolderCreationRequest;
import com.tanloc.lohu.lohuelearninguserapp.entity.LearningFolder;
import com.tanloc.lohu.lohuelearninguserapp.entity.User;
import com.tanloc.lohu.lohuelearninguserapp.exception.LearningFolderNotFoundException;
import com.tanloc.lohu.lohuelearninguserapp.exception.UserNotFoundException;
import com.tanloc.lohu.lohuelearninguserapp.mapper.LearningFolderMapper;
import com.tanloc.lohu.lohuelearninguserapp.repository.LearningFolderRepository;
import com.tanloc.lohu.lohuelearninguserapp.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class LearningFolderService {
    LearningFolderRepository learningFolderRepository;
    UserRepository userRepository;
    LearningFolderMapper learningFolderMapper;

    public Page<LearningFolder> getByUserId(Long userId, int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
        return learningFolderRepository.findByUserId(userId, pageable);
    }

    public Page<LearningFolder> getUserIdAndByNameContaining(Long userId, String name, int pageNumber, int pageSize, String sortBy) {
        if (pageNumber < 1) pageNumber = 1;
        if (pageSize < 1) pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
        return learningFolderRepository.findByUserIdAndNameContaining(userId, name, pageable);
    }

    public LearningFolder getById(Long id) {
        return learningFolderRepository.findById(id).orElseThrow(() -> new LearningFolderNotFoundException("Không tồn tại thư mục học tập có id là " + id));
    }

    public LearningFolder add(User user, LearningFolderCreationRequest learningFolderCreationRequest) {
        User foundUser = userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException("Không tồn tại user nào có id là " + user.getId()));
        LearningFolder learningFolder = learningFolderMapper.toLearningFolder(learningFolderCreationRequest, foundUser);
        return learningFolderRepository.save(learningFolder);
    }

}
