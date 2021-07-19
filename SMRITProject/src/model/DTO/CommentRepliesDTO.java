package model.DTO;

import java.util.List;

public class CommentRepliesDTO {
	private CommentDTO commentDTO;
	private List<ReplyDTO> replies;
	public CommentDTO getCommentDTO() {
		return commentDTO;
	}
	public void setCommentDTO(CommentDTO commentDTO) {
		this.commentDTO = commentDTO;
	}
	public List<ReplyDTO> getReplies() {
		return replies;
	}
	public void setReplies(List<ReplyDTO> replies) {
		this.replies = replies;
	}
	

}
