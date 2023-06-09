package com.socialnetwork.Infrastucture.Mapper;

import com.socialnetwork.Entity.Post;
import com.socialnetwork.Infrastucture.Dto.PostDto;
import com.socialnetwork.Intergration.DtoMapper;


public enum PostDtoMapper implements DtoMapper<PostDto, Post> {
    INSTANCE;

    @Override
    public PostDto apply(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setPostText(post.getPostText());
        postDto.setPostName(post.getPostName());
        return postDto;
    }
}
