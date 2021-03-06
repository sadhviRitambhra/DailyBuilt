package com.niit.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BlogCommentDao;
import com.niit.dao.BlogDao;
import com.niit.model.Blog;
import com.niit.model.BlogComments;

@RestController
@RequestMapping("api")
public class BlogCommentController {

	@Autowired
	
	private BlogComments blogComments;
	@Autowired
	private BlogCommentDao blogCommentsDao;
	
	@GetMapping("/allBlogComments/{id}")
	public ResponseEntity<List<BlogComments>> getAllComments(@PathVariable("id") int id){
		
		List<BlogComments> allBlogComments= blogCommentsDao.getAllComments(id);
		return new ResponseEntity<List<BlogComments>>(allBlogComments, HttpStatus.OK);
		
	}
	@PostMapping("/newBlogComment")
	public ResponseEntity<BlogComments> newBlogComment(@RequestBody BlogComments blogComments) {
		boolean flag = blogCommentsDao.addBlogComment(blogComments);
		if (flag) {
			return new ResponseEntity<BlogComments>(blogCommentsDao.getBlogCommentById(blogComments.getBlogCommentId()),HttpStatus.OK);
		} 
		
		else {
			return new ResponseEntity<BlogComments>(blogComments, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("blogComment/update")
	public ResponseEntity<BlogComments> updateBlogComment(@RequestBody BlogComments blogComment) {
		boolean flag = blogCommentsDao.editBlogComment(blogComment);
		if (flag) {
			return new ResponseEntity<BlogComments>(blogCommentsDao.getBlogCommentById(blogComment.getBlogCommentId()),HttpStatus.OK);
		} else {
			return new ResponseEntity<BlogComments>(blogComment, HttpStatus.BAD_REQUEST);
		}
	}

	/*@DeleteMapping(value = "deleteBlogComment/{id}")
	public ResponseEntity deleteBlogComments(@PathVariable("id") int blogCommentId) {
		blogCommentsDao.deleteBlogComment(blogCommentId);
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
*/

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal request, please verify your payload")
	public void handleClientErrors(Exception ex) {

	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
	public void handleServerErrors(Exception ex) {

	}
}
