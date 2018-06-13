package com.niit.daoimpl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.dao.ForumMemberDao;
import com.niit.model.ForumMember;
@Repository(value="forumMemberDao")
@Transactional
public class ForumMemberDaoImpl implements ForumMemberDao {

	@Autowired
	private SessionFactory sessionFactory;

	public boolean addForumMember(ForumMember member) {
		boolean flag = false;
		try {
			Session session = sessionFactory.getCurrentSession();
			session.save(member);
			session.flush();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;

	}

	public boolean deleteForumMember(int id) {
		boolean flag = false;
		try {
			ForumMember forumMember = getMemberById(id);
			forumMember.setForumStatus("inactive");
			Session session = sessionFactory.getCurrentSession();
			session.update(forumMember);
			session.flush();
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;

	}

	private ForumMember getMemberById(int id) {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().get(ForumMember.class, id);
	}

	public boolean editForumMember(ForumMember member) {
		boolean flag;
		try {
			sessionFactory.getCurrentSession().update(member);
			flag = true;
		} catch (Exception e) {
			flag = false;
		}
		return flag;

	}

	public ForumMember getMemberByEmailId(String emailId) {
		try {
			return (ForumMember) sessionFactory.getCurrentSession().createQuery("from ForumMember where memberEmailId= :emailId").setParameter("emailId", emailId).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<ForumMember> getAllMembersByForumId(int forumId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from ForumMember where forumId = :forumid and forumStatus= :active");
			query.setParameter("forumid", forumId);
			query.setParameter("active", "active");
			return (List<ForumMember>) query.list();
		} catch (Exception ex) {
			return null;
		}
	}
}