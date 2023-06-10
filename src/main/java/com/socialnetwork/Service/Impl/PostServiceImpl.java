package com.socialnetwork.Service.Impl;

import com.socialnetwork.Entity.Post;
import com.socialnetwork.Entity.User;
import com.socialnetwork.Infrastucture.Dto.PostDto;
import com.socialnetwork.Infrastucture.Mapper.PostDtoMapper;
import com.socialnetwork.Infrastucture.Mapper.PostMapper;
import com.socialnetwork.Infrastucture.Mapper.UserMapper;
import com.socialnetwork.Repository.PostRepository;
import com.socialnetwork.Service.PostService;
import com.socialnetwork.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Override
    public PostDto createPost(PostDto postDto, Long userId) {
        Post post = PostMapper.INSTANCE.apply(postDto);
        post.setUser(UserMapper.INSTANCE.apply(userService.userId(userId)));
        Post postSave = postRepository.save(post);
        return PostDtoMapper.INSTANCE.apply(postSave);
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map( posts -> PostDtoMapper.INSTANCE.apply(posts)).collect(Collectors.toList());
    }

    @Override
    public PostDto postId(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("not found id"));
        return PostDtoMapper.INSTANCE.apply(post);
    }
}