package ir.maktab.homeservicespringboot.data.dto.mappers;

import ir.maktab.homeservicespringboot.data.dto.CommentDto;
import ir.maktab.homeservicespringboot.data.entity.Comment;

public class CommentMapper {

    public static Comment toComment(CommentDto commentDto){
        return Comment.builder().setExplanations(commentDto.getComment()).setPoint(commentDto.getPoint()).build();
    }
}
